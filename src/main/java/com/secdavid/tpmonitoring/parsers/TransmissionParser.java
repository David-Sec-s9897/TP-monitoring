package com.secdavid.tpmonitoring.parsers;

import com.secdavid.tpmonitoring.model.entsoe.*;
import com.secdavid.tpmonitoring.util.DateTimeUtils;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class TransmissionParser extends ParserBase {

    public static DefaultMarketDocument parseDocument(InputStream inputStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputStream);

        Element root = doc.getDocumentElement();

        DefaultMarketDocument defaultMarketDocument = new DefaultMarketDocument();
        List<TimeSeries> timeSeriesArrayList = new ArrayList<>();

        Element periodTimeInterval = (Element) root.getElementsByTagName("time_Period.timeInterval").item(0);

        if (periodTimeInterval != null) {
            String periodTimeIntervalStart = getElementValue(periodTimeInterval, "start");
            String periodTimeIntervalEnd = getElementValue(periodTimeInterval, "end");
            PeriodTimeInterval periodTimeInterval1 = new PeriodTimeInterval();
            periodTimeInterval1.setStart(ZonedDateTime.parse(periodTimeIntervalStart, DateTimeUtils.getZonedDateTimeFormatter()));
            periodTimeInterval1.setEnd(ZonedDateTime.parse(periodTimeIntervalEnd, DateTimeUtils.getZonedDateTimeFormatter()));
            defaultMarketDocument.setPeriodTimeInterval(periodTimeInterval1);

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
                    timeInterval.setStart(ZonedDateTime.parse(start, DateTimeUtils.getZonedDateTimeFormatter()));
                    timeInterval.setEnd(ZonedDateTime.parse(end, DateTimeUtils.getZonedDateTimeFormatter()));
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
                        point.setDate(timeInterval.getStart().plus(duration.multipliedBy(point.getPosition() - 1)));
                        if (quantity == null) {
                            point.setQuantity(0d);
                        } else {
                            point.setQuantity(Double.valueOf(quantity));
                        }
                        points.add(point);
                    }
                    period.setPoint(points);
                    timeSeries.setPeriod(period);
                    timeSeriesArrayList.add(timeSeries);

                }
            }
        } else {
            handleNoParentElement((Element) root.getElementsByTagName("Reason").item(0));
        }
        defaultMarketDocument.setTimeSeries(timeSeriesArrayList);
        return defaultMarketDocument;

    }
}
