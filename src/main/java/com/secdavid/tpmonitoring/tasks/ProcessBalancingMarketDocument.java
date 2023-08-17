package com.secdavid.tpmonitoring.tasks;

import com.secdavid.tpmonitoring.entsoe.EntsoeRestClient;
import com.secdavid.tpmonitoring.enums.Category;
import com.secdavid.tpmonitoring.model.entsoe.DefaultMarketDocument;
import com.secdavid.tpmonitoring.parsers.BalancingParser;

import java.io.InputStream;

public class ProcessBalancingMarketDocument extends ProcessDocument {

    public ProcessBalancingMarketDocument(EntsoeRestClient restClient) {
        super();
        super.restClient = restClient;
        this.category = Category.BALANCING;
    }

    private DefaultMarketDocument parse(InputStream is) throws Exception {
       return BalancingParser.parseDocument(is);
    }

}
