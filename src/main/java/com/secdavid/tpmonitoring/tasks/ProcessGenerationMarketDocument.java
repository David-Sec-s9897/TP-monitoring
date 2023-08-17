package com.secdavid.tpmonitoring.tasks;

import com.secdavid.tpmonitoring.entsoe.EntsoeRestClient;
import com.secdavid.tpmonitoring.enums.Category;

public class ProcessGenerationMarketDocument extends ProcessDocument {

    public ProcessGenerationMarketDocument(EntsoeRestClient restClient) {
        super();
        super.restClient = restClient;
        this.category = Category.GENERATION;
    }

}
