package com.secdavid.tpmonitoring.builder;

import com.secdavid.tpmonitoring.enums.Category;
import com.secdavid.tpmonitoring.model.TpProcess;

public class ProcessBuilder {

    public String name;
    public String documentType;
    public String controlAreaDomain;
    public String businessType;
    public String processType;
    public Category category;
    public String productionType;
    public String balancingDirection;
    public String outArea;
    public String inArea;

    public ProcessBuilder() {

    }

    public ProcessBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProcessBuilder documentType(String documentType) {
        this.documentType = documentType;
        return this;
    }

    public ProcessBuilder areaDomain(String controlAreaDomain) {
        this.controlAreaDomain = controlAreaDomain;
        return this;
    }

    public ProcessBuilder businessType(String businessType) {
        this.businessType = businessType;
        return this;
    }

    public ProcessBuilder processType(String processType) {
        this.processType = processType;
        return this;
    }

    public ProcessBuilder category(Category category) {
        this.category = category;
        return this;
    }

    public ProcessBuilder balancingDirection(String balancingDirection) {
        this.balancingDirection = balancingDirection;
        return this;
    }

    public ProcessBuilder productionType(String productionType) {
        this.productionType = productionType;
        return this;
    }

    public ProcessBuilder inArea(String inArea) {
        this.inArea = inArea;
        return this;
    }

    public ProcessBuilder outArea(String outArea) {
        this.outArea = outArea;
        return this;
    }

    public TpProcess build(){
        TpProcess tpProcess = new TpProcess(this);
        return tpProcess;
    }
}
