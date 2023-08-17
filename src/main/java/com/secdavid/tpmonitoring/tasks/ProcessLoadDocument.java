package com.secdavid.tpmonitoring.tasks;

import com.secdavid.tpmonitoring.entsoe.EntsoeRestClient;
import com.secdavid.tpmonitoring.enums.Category;
import com.secdavid.tpmonitoring.model.entsoe.DefaultMarketDocument;
import com.secdavid.tpmonitoring.parsers.TransmissionParser;

import java.io.InputStream;

public class ProcessLoadDocument extends ProcessDocument {

    public ProcessLoadDocument(EntsoeRestClient restClient) {
        super();
        super.restClient = restClient;
        this.category = Category.LOAD;
    }

    private DefaultMarketDocument parse(InputStream is) throws Exception {
        return TransmissionParser.parseDocument(is);
    }

}
