package com.secdavid.tpmonitoring.tasks;

import com.secdavid.tpmonitoring.entsoe.EntsoeRestClient;
import com.secdavid.tpmonitoring.model.MasterData;
import com.secdavid.tpmonitoring.model.TpProcess;
import com.secdavid.tpmonitoring.model.entsoe.*;
import com.secdavid.tpmonitoring.model.entsoe.Period;
import com.secdavid.tpmonitoring.services.TPProcessService;
import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import okhttp3.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import retrofit2.Call;
import retrofit2.Response;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BackgroundTaskManager {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");


    @Inject
    TPProcessService processService;

    @Inject
    EntsoeRestClient restClient;

    @Schedule(hour="*" , minute = "*/15", info = "Every 15 minutes timer")
    public void load() {
        List<TpProcess> processes = processService.getProcesses();
        ZonedDateTime end =  LocalDateTime.now().withHour(22).withMinute(0).withSecond(0).atZone(ZoneId.of("UTC"));
        ZonedDateTime start = end.minusDays(2);

        for (TpProcess process: processes){
            BalancingMarketDocument balancingMarketDocument = callEntroe(process.getDocumentType(),process.getControlAreaDomain(), process.getBusinessType(), process.getProcessType(), start, end);
            System.out.println(balancingMarketDocument);
            process.getTimeSeriesList().addAll(balancingMarketDocument.getTimeSeries());

        }
    }

    private BalancingMarketDocument callEntroe(String documentType, String controlAreaDomain, String bussinessType, String processType, ZonedDateTime start, ZonedDateTime end) {
        Call<ResponseBody> call = restClient.getService().getDocument(documentType,
                controlAreaDomain, start.format(formatter), end.format(formatter), bussinessType, processType,"39cc0b75-19ef-430e-8f98-3bdfec62079f");
        try {
            Response<ResponseBody> response = call.execute();
            if (response.code() != 200) {
                System.out.println("code:" + response.code());
                return null;
            }
            ResponseBody responseBody = response.body();
            InputStream is = responseBody.byteStream();
            BalancingMarketDocument balancingMarketDocument = parseDocument(is);
            return balancingMarketDocument;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private BalancingMarketDocument parseDocument(InputStream inputStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputStream);

        Element root = doc.getDocumentElement();

        BalancingMarketDocument balancingMarketDocument = new BalancingMarketDocument();
        List<TimeSeries> timeSeriesArrayList = new ArrayList<>();
        Element periodTimeInterval = (Element) root.getElementsByTagName("period.timeInterval").item(0);
        String periodTimeIntervalStart = getElementValue(periodTimeInterval, "start");
        String periodTimeIntervalEnd = getElementValue(periodTimeInterval, "start");
        PeriodTimeInterval periodTimeInterval1 = new PeriodTimeInterval();
        periodTimeInterval1.setStart(ZonedDateTime.parse(periodTimeIntervalStart, TimeInterval.FORMAT));
        periodTimeInterval1.setEnd(ZonedDateTime.parse(periodTimeIntervalEnd, TimeInterval.FORMAT));
        balancingMarketDocument.setPeriodTimeInterval(periodTimeInterval1);

        NodeList timeSeriesList = root.getElementsByTagName("TimeSeries");
        for (int i = 0; i < timeSeriesList.getLength(); i++) {
            TimeSeries timeSeries = new TimeSeries();
            Element timeSeriesElement = (Element) timeSeriesList.item(i);

            String mRID = getElementValue(timeSeriesElement, "mRID");
            String businessType = getElementValue(timeSeriesElement, "businessType");
            String mktPSRTypePsrType = getElementValue(timeSeriesElement, "mktPSRType.psrType");
            String flowDirection = getElementValue(timeSeriesElement, "flowDirection.direction");
            String quantityMeasureUnitName = getElementValue(timeSeriesElement, "quantity_Measure_Unit.name");
            String curveType = getElementValue(timeSeriesElement, "curveType");

            timeSeries.setmRID(mRID);
            timeSeries.setBusinessType(businessType);
            timeSeries.setMktPSRTypePsrType(mktPSRTypePsrType);
            timeSeries.setFlowDirectionDirection(flowDirection);
            timeSeries.setQuantity_Measure_UnitName(quantityMeasureUnitName);
            timeSeries.setQuantity_Measure_UnitName(quantityMeasureUnitName);
            timeSeries.setCurveType(curveType);

            Period period = new Period();

            Element periodElement = (Element) timeSeriesElement.getElementsByTagName("Period").item(0);
            if (periodElement != null) {
                // Extract data from the Period element
                String start = getElementValue(periodElement, "start");
                String end = getElementValue(periodElement, "end");
                String resolution = getElementValue(periodElement, "resolution");
                Duration duration = Duration.parse(resolution);

                TimeInterval timeInterval = new TimeInterval();
                timeInterval.setStart(ZonedDateTime.parse(start, TimeInterval.FORMAT));
                timeInterval.setEnd(ZonedDateTime.parse(end, TimeInterval.FORMAT));
                period.setTimeInterval(timeInterval);
                period.setResolution(resolution);

                NodeList pointList = periodElement.getElementsByTagName("Point");
                List<Point> points = new ArrayList<>();
                for (int j = 0; j < pointList.getLength(); j++) {
                    Point point = new Point();
                    Element pointElement = (Element) pointList.item(j);
                    String position = getElementValue(pointElement, "position");
                    String quantity = getElementValue(pointElement, "quantity");

                    point.setPosition(Integer.valueOf(position));
                    point.setDate(timeInterval.getStart().plus(duration.multipliedBy(point.getPosition()-1)));
                    point.setQuantity(Double.valueOf(quantity));
                    points.add(point);
                }
                period.setPoint(points);
                timeSeries.setPeriod(period);
                timeSeriesArrayList.add(timeSeries);

            }
        }
        balancingMarketDocument.setTimeSeries(timeSeriesArrayList);
        return balancingMarketDocument;
    }

    private static String getElementValue(Element parentElement, String tagName) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }
}
