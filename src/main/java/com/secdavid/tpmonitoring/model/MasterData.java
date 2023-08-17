package com.secdavid.tpmonitoring.model;

public class MasterData {

    private String documentType;
    private String controlAreaDomain;
    private String businessType;
    private String processType;
    private String balancingDirection;
    private String productionType;
    private String inArea;
    private String outArea;
    private String outDomain;


    public MasterData(String documentType, String controlAreaDomain, String businessType, String processType) {
        this.documentType = documentType;
        this.controlAreaDomain = controlAreaDomain;
        this.businessType = businessType;
        this.processType = processType;
    }

    public MasterData(String documentType, String controlAreaDomain, String businessType, String balancingDirection, String processType, String productionType, String inArea, String outArea) {
        this.documentType = documentType;
        this.controlAreaDomain = controlAreaDomain;
        this.businessType = businessType;
        this.processType = processType;
        this.balancingDirection = balancingDirection;
        this.productionType = productionType;
        this.inArea = inArea;
        this.outArea = outArea;
    }

    public MasterData(String documentType, String controlAreaDomain, String businessType, String balancingDirection, String processType, String productionType, String inArea, String outArea, String outDomain) {
        this.documentType = documentType;
        this.controlAreaDomain = controlAreaDomain;
        this.businessType = businessType;
        this.processType = processType;
        this.balancingDirection = balancingDirection;
        this.productionType = productionType;
        this.inArea = inArea;
        this.outArea = outArea;
        this.outDomain = outDomain;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getControlAreaDomain() {
        return controlAreaDomain;
    }

    public void setControlAreaDomain(String controlAreaDomain) {
        this.controlAreaDomain = controlAreaDomain;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getBalancingDirection() {
        return balancingDirection;
    }

    public void setBalancingDirection(String balancingDirection) {
        this.balancingDirection = balancingDirection;
    }

    public String getProductionType() {
        return productionType;
    }

    public void setProductionType(String productionType) {
        this.productionType = productionType;
    }

    public String getInArea() {
        return inArea;
    }

    public void setInArea(String inArea) {
        this.inArea = inArea;
    }

    public String getOutArea() {
        return outArea;
    }

    public void setOutArea(String outArea) {
        this.outArea = outArea;
    }

    public String getOutDomain() {
        return outDomain;
    }

    public void setOutDomain(String outDomain) {
        this.outDomain = outDomain;
    }

    @Override
    public String toString() {
        return "MasterData{" +
                "documentType='" + documentType + '\'' +
                ", controlAreaDomain='" + controlAreaDomain + '\'' +
                ", businessType='" + businessType + '\'' +
                ", processType='" + processType + '\'' +
                ", balancingDirection='" + balancingDirection + '\'' +
                ", productionType='" + productionType + '\'' +
                ", inArea='" + inArea + '\'' +
                ", outArea='" + outArea + '\'' +
                '}';
    }
}
