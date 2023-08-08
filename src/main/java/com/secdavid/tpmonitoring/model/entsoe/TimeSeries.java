package com.secdavid.tpmonitoring.model.entsoe;

public class TimeSeries {
    public String mRID;
    public String businessType;
    public String mktPSRTypePsrType;
    public String flowDirectionDirection;
    public String quantity_Measure_UnitName;
    public String curveType;
    public Period Period;
    public boolean complete;

    public String getmRID() {
        return mRID;
    }

    public void setmRID(String mRID) {
        this.mRID = mRID;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getMktPSRTypePsrType() {
        return mktPSRTypePsrType;
    }

    public void setMktPSRTypePsrType(String mktPSRTypePsrType) {
        this.mktPSRTypePsrType = mktPSRTypePsrType;
    }

    public String getFlowDirectionDirection() {
        return flowDirectionDirection;
    }

    public void setFlowDirectionDirection(String flowDirectionDirection) {
        this.flowDirectionDirection = flowDirectionDirection;
    }

    public String getQuantity_Measure_UnitName() {
        return quantity_Measure_UnitName;
    }

    public void setQuantity_Measure_UnitName(String quantity_Measure_UnitName) {
        this.quantity_Measure_UnitName = quantity_Measure_UnitName;
    }

    public String getCurveType() {
        return curveType;
    }

    public void setCurveType(String curveType) {
        this.curveType = curveType;
    }

    public Period getPeriod() {
        return Period;
    }

    public void setPeriod(Period period) {
        Period = period;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSeries)) return false;

        TimeSeries that = (TimeSeries) o;
        if (!businessType.equals(that.businessType)) return false;
        if (!flowDirectionDirection.equals(that.flowDirectionDirection)) return false;
        if (!quantity_Measure_UnitName.equals(that.quantity_Measure_UnitName)) return false;
        return curveType.equals(that.curveType);
    }

    @Override
    public int hashCode() {
        int result = 31 + businessType.hashCode();
        result = 31 * result + flowDirectionDirection.hashCode();
        result = 31 * result + quantity_Measure_UnitName.hashCode();
        result = 31 * result + curveType.hashCode();
        return result;
    }
}