package com.secdavid.tpmonitoring.tasks;

import com.secdavid.tpmonitoring.entsoe.EntsoeRestClient;
import com.secdavid.tpmonitoring.enums.Category;
import com.secdavid.tpmonitoring.model.MasterData;
import com.secdavid.tpmonitoring.model.entsoe.DefaultMarketDocument;
import com.secdavid.tpmonitoring.parsers.BalancingParser;
import com.secdavid.tpmonitoring.parsers.TransmissionParser;
import com.secdavid.tpmonitoring.util.DateTimeUtils;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public abstract class ProcessDocument {
    private static final String SECURITY_TOKEN = "39cc0b75-19ef-430e-8f98-3bdfec62079f";
    private static final Logger LOGGER = Logger.getLogger(ProcessDocument.class.getName());
    @Inject
    EntsoeRestClient restClient;
    Category category;
    DateTimeFormatter formatter = DateTimeUtils.getEntsoeRequestDateFormat();

    public DefaultMarketDocument process(MasterData masterData, ZonedDateTime start, ZonedDateTime end) throws Exception {
        InputStream is = callEntroe(masterData, start, end);
        return parse(is, category);
    }

    private InputStream callEntroe(MasterData masterData, ZonedDateTime start, ZonedDateTime end) {
        Call<ResponseBody> call;
        String documentType = masterData.getDocumentType();
        switch (category) {
            case BALANCING:
                if (documentType.equals("A83") || documentType.equals("A82") || documentType.equals("A84")) {
                    call = restClient.getService().getDocumentDefault(documentType,
                            masterData.getControlAreaDomain(), start.format(formatter), end.format(formatter), masterData.getBusinessType(), masterData.getProcessType(), SECURITY_TOKEN);
                } else {
                    call = restClient.getService().getDocumentBalancing(documentType,
                            masterData.getControlAreaDomain(), start.format(formatter), end.format(formatter), masterData.getBusinessType(), masterData.getProcessType(), SECURITY_TOKEN);
                }
                break;
            case LOAD:
                call = restClient.getService().getDocumentLoad(documentType,
                        masterData.getProcessType(), masterData.getControlAreaDomain(), start.format(formatter), end.format(formatter), masterData.getBusinessType(), SECURITY_TOKEN);
                break;
            case GENERATION:
                call = restClient.getService().getDocumentGeneration(documentType,
                        masterData.getControlAreaDomain(), start.format(formatter), end.format(formatter), masterData.getBusinessType(), masterData.getProcessType(), SECURITY_TOKEN);
                break;
            case TRANSMISSION:
                call = restClient.getService().getDocumentTransmission(documentType,
                        masterData.getControlAreaDomain(), masterData.getOutDomain(), start.format(formatter), end.format(formatter), masterData.getBusinessType(), masterData.getProcessType(), SECURITY_TOKEN);
                break;
            default:
                call = restClient.getService().getDocumentDefault(documentType,
                        masterData.getControlAreaDomain(), start.format(formatter), end.format(formatter), masterData.getBusinessType(), masterData.getProcessType(), SECURITY_TOKEN);
                break;
        }
        try {
            Response<ResponseBody> response = call.execute();
            if (response.code() != 200) {
                LOGGER.log(Level.SEVERE, "Code {}", response.code());
                return null;
            }
            return response.body().byteStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private DefaultMarketDocument parse(InputStream is, Category category) throws Exception {
        switch (category) {
            case LOAD:
            case GENERATION:
                return TransmissionParser.parseDocument(is);
            default:
                return BalancingParser.parseDocument(is);
        }

    }


}
