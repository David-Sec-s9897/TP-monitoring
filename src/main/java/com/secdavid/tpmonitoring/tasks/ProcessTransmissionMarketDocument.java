package com.secdavid.tpmonitoring.tasks;

import com.secdavid.tpmonitoring.entsoe.EntsoeRestClient;
import com.secdavid.tpmonitoring.enums.Category;

public class ProcessTransmissionMarketDocument extends ProcessDocument {

    public ProcessTransmissionMarketDocument(EntsoeRestClient restClient) {
        super();
        super.restClient = restClient;
        this.category = Category.TRANSMISSION;
    }

}
