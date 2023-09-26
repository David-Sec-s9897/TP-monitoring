package com.secdavid.tpmonitoring.parsers;

import com.secdavid.tpmonitoring.model.entsoe.ParsedMasterData;
import com.secdavid.tpmonitoring.model.entsoe.TimeSeries;
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
public class MasterdataParser extends ParserBase {


    public static List<ParsedMasterData> parseMasterdata(InputStream inputStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputStream);

        Element root = doc.getDocumentElement();
        List<ParsedMasterData> masterdataArray = new ArrayList<>();
        ParsedMasterData parsedMasterData = new ParsedMasterData();
        Element masterDataElement = root;

        if (root != null) {
            String mRID = getElementValue(masterDataElement, "mRID");
            String type = getElementValue(masterDataElement, "type");
            String processType = getElementValue(masterDataElement, "process.processType");
            String created = getElementValue(masterDataElement, "createdDateTime");
            String cotrolArealDomain = getElementValue(masterDataElement, "controlArea_Domain.mRID");

            List<TimeSeries> parsedTimeSeriesList = new ArrayList<>();
            NodeList timeSeriesList = root.getElementsByTagName("TimeSeries");
            for (int j = 0; j < timeSeriesList.getLength(); j++) {
                TimeSeries timeSeries = new TimeSeries();
                Element timeSeriesElement = (Element) timeSeriesList.item(j);

                String tmRID = getElementValue(timeSeriesElement, "mRID");
                String businessType = getElementValue(timeSeriesElement, "businessType");
                String mktPSRTypePsrType = getElementValue(timeSeriesElement, "mktPSRType.psrType");
                String flowDirection = getElementValue(timeSeriesElement, "flowDirection.direction");
                String quantityMeasureUnitName = getElementValue(timeSeriesElement, "quantity_Measure_Unit.name");
                String curveType = getElementValue(timeSeriesElement, "curveType");

                timeSeries.setmRID(tmRID);
                timeSeries.setBusinessType(businessType);
                timeSeries.setMktPSRTypePsrType(mktPSRTypePsrType);
                timeSeries.setFlowDirectionDirection(flowDirection);
                timeSeries.setQuantity_Measure_UnitName(quantityMeasureUnitName);
                timeSeries.setQuantity_Measure_UnitName(quantityMeasureUnitName);
                timeSeries.setCurveType(curveType);

                parsedTimeSeriesList.add(timeSeries);
                parsedMasterData.setmRID(mRID);
                parsedMasterData.setType(type);
                parsedMasterData.setProcessType(processType);
                parsedMasterData.setCreated(created);
                parsedMasterData.setControlAreaDomain(cotrolArealDomain);
                parsedMasterData.setTimeSeriesList(parsedTimeSeriesList);
                masterdataArray.add(parsedMasterData);
            }
        } else {
            handleNoParentElement((Element) root.getElementsByTagName("Reason").item(0));
        }
        return masterdataArray;

    }
}
