package com.secdavid.tpmonitoring.parsers;

import com.secdavid.tpmonitoring.enums.Category;
import com.secdavid.tpmonitoring.model.entsoe.DefaultMarketDocument;
import com.secdavid.tpmonitoring.model.entsoe.TimeSeriesJob;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class TimeSeriesJobParser extends ParserBase {

    public static List<TimeSeriesJob> parseDocument(InputStream inputStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputStream);

        Element root = doc.getDocumentElement();
        DefaultMarketDocument defaultMarketDocument = new DefaultMarketDocument();
        List<TimeSeriesJob> timeSeriesArrayList = new ArrayList<>();

        NodeList timeseriesList = root.getElementsByTagName("timeSeriesJobs");

        if(timeseriesList != null) {
            for (int i = 0; i < timeseriesList.getLength(); i++) {
                TimeSeriesJob timeSeriesJob = new TimeSeriesJob();
                Element timeSeriesElement = (Element) timeseriesList.item(i);

                String name = getElementValue(timeSeriesElement, "timeSeriesName");
                Category category = getCategory(timeSeriesElement, "timeSeriesCategory");
                String cron = getElementValue(timeSeriesElement, "cronExpression");
                List<String> keys = getElementsValueAsList(timeSeriesElement, "timeseriesKeys");
                String startCron = getElementValue(timeSeriesElement, "startCron");
                String duration = getElementValue(timeSeriesElement, "duration");
                String nextStart = getElementValue(timeSeriesElement, "nextStart");
                Boolean active = Boolean.parseBoolean(getElementValue(timeSeriesElement, "active"));
                Boolean state = Boolean.parseBoolean(getElementValue(timeSeriesElement, "state"));
                String businessKey = getElementValue(timeSeriesElement, "businessKey");
                Boolean suspended = Boolean.parseBoolean(getElementValue(timeSeriesElement, "suspended"));
                Boolean fileUpload = Boolean.parseBoolean(getElementValue(timeSeriesElement, "fileUpload"));
                String mappingFile = getElementValue(timeSeriesElement, "mappingFile");
                String masterDataFile = getElementValue(timeSeriesElement, "masterDataFile");

                timeSeriesJob.setName(name);
                timeSeriesJob.setCategory(category);
                timeSeriesJob.setCron(cron);
                timeSeriesJob.setTimeseriesKeys(keys);
                timeSeriesJob.setStartCron(startCron);
                timeSeriesJob.setDuration(duration);
                timeSeriesJob.setNextStart(nextStart);
                timeSeriesJob.setActive(active);
                timeSeriesJob.setState(state);
                timeSeriesJob.setBusinessKey(businessKey);
                timeSeriesJob.setSuspended(suspended);
                timeSeriesJob.setFileUpload(fileUpload);
                timeSeriesJob.setMappingFile(mappingFile);
                timeSeriesJob.setMasterDataFile(masterDataFile);

                timeSeriesArrayList.add(timeSeriesJob);
            }
        }else{
            handleNoParentElement((Element) root.getElementsByTagName("Reason").item(0));
        }
        return timeSeriesArrayList;

    }

}





