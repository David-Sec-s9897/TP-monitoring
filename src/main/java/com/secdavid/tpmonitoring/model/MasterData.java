package com.secdavid.tpmonitoring.model;

public class MasterData {

    String documentType;
    String controlAreaDomain;
    String businessType;
    String processType;


    public MasterData(String documentType, String controlAreaDomain, String businessType, String processType) {
        this.documentType = documentType;
        this.controlAreaDomain = controlAreaDomain;
        this.businessType = businessType;
        this.processType = processType;
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

    @Override
    public String toString() {
        return "MasterData{" +
                "documentType='" + documentType + '\'' +
                ", controlAreaDomain='" + controlAreaDomain + '\'' +
                ", businessType='" + businessType + '\'' +
                '}';
    }
}
