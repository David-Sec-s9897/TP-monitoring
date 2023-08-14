package com.secdavid.tpmonitoring.model;

import com.secdavid.tpmonitoring.builder.ProcessBuilder;
import com.secdavid.tpmonitoring.enums.Category;
import com.secdavid.tpmonitoring.model.entsoe.TimeSeries;

import java.util.ArrayList;
import java.util.List;

public class TpProcess {
    private String name;
    private String documentType;
    private String controlAreaDomain;
    private String businessType;
    private String processType;
    private Category category;
    private String balancingDirection;
    private String productionType;
    private String inArea;
    private String outArea;
    private List<TimeSeries> timeSeriesList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getControlAreaDomain() {
        return controlAreaDomain;
    }

    public String getBusinessType() {
        return businessType;
    }

    public String getProcessType() {
        return processType;
    }

    public Category getCategory() {
        return category;
    }

    public String getBalancingDirection() {
        return balancingDirection;
    }

    public String getProductionType() {
        return productionType;
    }

    public String getInArea() {
        return inArea;
    }

    public String getOutArea() {
        return outArea;
    }

    public List<TimeSeries> getTimeSeriesList() {
        return timeSeriesList;
    }

    public TpProcess (ProcessBuilder processBuilder){
        this.name = processBuilder.name;
        this.documentType = processBuilder.documentType;
        this.controlAreaDomain = processBuilder.controlAreaDomain;
        this.businessType = processBuilder.businessType;
        this.processType = processBuilder.processType;
        this.category = processBuilder.category;
        this.balancingDirection = processBuilder.balancingDirection;
        this.productionType = processBuilder.productionType;
        this.inArea = processBuilder.inArea;
        this.outArea = processBuilder.outArea;
    }
}

