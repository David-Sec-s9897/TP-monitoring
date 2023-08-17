package com.secdavid.tpmonitoring.model.entsoe;

import java.util.Date;
import java.util.List;

public class DefaultMarketDocument {

    public String mRID;
    public int revisionNumber;
    public String type;
    public String processProcessType;
    public Sender_MarketParticipantMRID sender_MarketParticipantMRID;
    public String sender_MarketParticipantMarketRoleType;
    public Receiver_MarketParticipantMRID receiver_MarketParticipantMRID;
    public String receiver_MarketParticipantMarketRoleType;
    public Date createdDateTime;
    public ControlArea_DomainMRID controlArea_DomainMRID;
    public PeriodTimeInterval periodTimeInterval;
    public List<TimeSeries> TimeSeries;

    public String getmRID() {
        return mRID;
    }

    public void setmRID(String mRID) {
        this.mRID = mRID;
    }

    public int getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(int revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProcessProcessType() {
        return processProcessType;
    }

    public void setProcessProcessType(String processProcessType) {
        this.processProcessType = processProcessType;
    }

    public Sender_MarketParticipantMRID getSender_MarketParticipantMRID() {
        return sender_MarketParticipantMRID;
    }

    public void setSender_MarketParticipantMRID(Sender_MarketParticipantMRID sender_MarketParticipantMRID) {
        this.sender_MarketParticipantMRID = sender_MarketParticipantMRID;
    }

    public String getSender_MarketParticipantMarketRoleType() {
        return sender_MarketParticipantMarketRoleType;
    }

    public void setSender_MarketParticipantMarketRoleType(String sender_MarketParticipantMarketRoleType) {
        this.sender_MarketParticipantMarketRoleType = sender_MarketParticipantMarketRoleType;
    }

    public Receiver_MarketParticipantMRID getReceiver_MarketParticipantMRID() {
        return receiver_MarketParticipantMRID;
    }

    public void setReceiver_MarketParticipantMRID(Receiver_MarketParticipantMRID receiver_MarketParticipantMRID) {
        this.receiver_MarketParticipantMRID = receiver_MarketParticipantMRID;
    }

    public String getReceiver_MarketParticipantMarketRoleType() {
        return receiver_MarketParticipantMarketRoleType;
    }

    public void setReceiver_MarketParticipantMarketRoleType(String receiver_MarketParticipantMarketRoleType) {
        this.receiver_MarketParticipantMarketRoleType = receiver_MarketParticipantMarketRoleType;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public ControlArea_DomainMRID getControlArea_DomainMRID() {
        return controlArea_DomainMRID;
    }

    public void setControlArea_DomainMRID(ControlArea_DomainMRID controlArea_DomainMRID) {
        this.controlArea_DomainMRID = controlArea_DomainMRID;
    }

    public PeriodTimeInterval getPeriodTimeInterval() {
        return periodTimeInterval;
    }

    public void setPeriodTimeInterval(PeriodTimeInterval periodTimeInterval) {
        this.periodTimeInterval = periodTimeInterval;
    }

    public List<TimeSeries> getTimeSeries() {
        return TimeSeries;
    }

    public void setTimeSeries(List<TimeSeries> timeSeries) {
        TimeSeries = timeSeries;
    }

    @Override
    public String toString() {
        return "DefaultMarketDocument{" +
                "mRID='" + mRID + '\'' +
                ", revisionNumber=" + revisionNumber +
                ", type='" + type + '\'' +
                ", processProcessType='" + processProcessType + '\'' +
                ", sender_MarketParticipantMRID=" + sender_MarketParticipantMRID +
                ", sender_MarketParticipantMarketRoleType='" + sender_MarketParticipantMarketRoleType + '\'' +
                ", receiver_MarketParticipantMRID=" + receiver_MarketParticipantMRID +
                ", receiver_MarketParticipantMarketRoleType='" + receiver_MarketParticipantMarketRoleType + '\'' +
                ", createdDateTime=" + createdDateTime +
                ", controlArea_DomainMRID=" + controlArea_DomainMRID +
                ", periodTimeInterval=" + periodTimeInterval +
                ", TimeSeries=" + TimeSeries +
                '}';
    }

    public class Sender_MarketParticipantMRID {
        public String codingScheme;
        public String text;
    }

    public class Receiver_MarketParticipantMRID {
        public String codingScheme;
        public String text;
    }

    public class ControlArea_DomainMRID {
        public String codingScheme;
        public String text;
    }

}
