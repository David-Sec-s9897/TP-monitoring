package com.secdavid.tpmonitoring.tasks;

import com.secdavid.tpmonitoring.entsoe.EntsoeRestClient;
import com.secdavid.tpmonitoring.model.TpProcess;
import com.secdavid.tpmonitoring.model.entsoe.DefaultMarketDocument;
import com.secdavid.tpmonitoring.services.TPProcessService;
import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BackgroundTaskManager {

    public static final Logger LOGGER = Logger.getLogger(BackgroundTaskManager.class.getName());

    @Inject
    TPProcessService processService;

    @Inject
    EntsoeRestClient restClient;


    @Schedule(hour = "*", minute = "*/30", info = "Every 30 minutes timer")
    public void load() throws InterruptedException {
        List<TpProcess> processes = processService.getProcesses();
        ZonedDateTime end = LocalDateTime.now().withHour(22).withMinute(0).withSecond(0).atZone(ZoneId.of("UTC"));
        ZonedDateTime start = end.minusDays(processService.getRuns()>0?1:5);

        for (TpProcess pr : processes) {
            try {
                DefaultMarketDocument defDoc = null;

                switch (pr.getCategory()) {
                    case BALANCING:
                        ProcessBalancingMarketDocument processBalancing = new ProcessBalancingMarketDocument(restClient);
                        defDoc = processBalancing.process(pr.getMasterData(), start, end);
                        break;
                    case TRANSMISSION:
                        ProcessTransmissionMarketDocument processTransmission = new ProcessTransmissionMarketDocument(restClient);
                        defDoc = processTransmission.process(pr.getMasterData(), start, end);
                        break;
                    case GENERATION:
                        ProcessGenerationMarketDocument processGeneration = new ProcessGenerationMarketDocument(restClient);
                        defDoc = processGeneration.process(pr.getMasterData(), start, end);
                        break;
                    case LOAD:
                        ProcessLoadDocument processLoad = new ProcessLoadDocument(restClient);
                        defDoc = processLoad.process(pr.getMasterData(), start, end);
                        break;
                }
                pr.getTimeSeriesList().addAll(defDoc.getTimeSeries());
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Process failed: {0}", pr);
                e.printStackTrace();
            }
            Thread.sleep(10000);
        }
        processService.inrementRuns();
    }


}
