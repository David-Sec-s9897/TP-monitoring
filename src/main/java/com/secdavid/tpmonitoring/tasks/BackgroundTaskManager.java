package com.secdavid.tpmonitoring.tasks;

import com.secdavid.tpmonitoring.entsoe.EntsoeRestClient;
import com.secdavid.tpmonitoring.mail.MailService;
import com.secdavid.tpmonitoring.model.TpProcess;
import com.secdavid.tpmonitoring.model.entsoe.DefaultMarketDocument;
import com.secdavid.tpmonitoring.model.entsoe.TimeInterval;
import com.secdavid.tpmonitoring.services.TPProcessService;
import com.secdavid.tpmonitoring.util.EmailUtils;
import com.secdavid.tpmonitoring.util.TimeSeriesUtils;
import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;

import java.time.LocalDate;
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
    private String recipients = System.getProperty("tp.mail.recipients");

    @Inject
    TPProcessService processService;

    @Inject
    EntsoeRestClient restClient;

    @Inject
    MailService mailService;

    @Inject
    MissingIntervalsService missingIntervalsService;


    @Schedule(hour = "*", minute = "*/30", info = "Every 30 minutes timer")
    public void load() throws InterruptedException {
        List<TpProcess> processes = processService.getProcesses();
        ZonedDateTime end = LocalDateTime.now().withHour(22).withMinute(0).withSecond(0).atZone(ZoneId.of("UTC"));
        ZonedDateTime start = end.minusDays(processService.getRuns() > 0 ? 1 : 5);


        for (TpProcess process : processes) {
            try {
                DefaultMarketDocument receivedDocument = null;

                switch (process.getCategory()) {
                    case BALANCING:
                        ProcessBalancingMarketDocument processBalancing = new ProcessBalancingMarketDocument(restClient);
                        receivedDocument = processBalancing.process(process.getMasterData(), start, end);
                        break;
                    case TRANSMISSION:
                        ProcessTransmissionMarketDocument processTransmission = new ProcessTransmissionMarketDocument(restClient);
                        receivedDocument = processTransmission.process(process.getMasterData(), start, end);
                        break;
                    case GENERATION:
                        ProcessGenerationMarketDocument processGeneration = new ProcessGenerationMarketDocument(restClient);
                        receivedDocument = processGeneration.process(process.getMasterData(), start, end);
                        break;
                    case LOAD:
                        ProcessLoadDocument processLoad = new ProcessLoadDocument(restClient);
                        receivedDocument = processLoad.process(process.getMasterData(), start, end);
                        break;
                    default:
                        LOGGER.log(Level.SEVERE, String.format("Process %s has unsupported category %s", process.getName(), process.getCategory()));
                        break;
                }
                if (receivedDocument != null) {
                    List<TimeInterval> data = receivedDocument.getTimeSeries().stream().map(timeSeries -> timeSeries.getPeriod().getTimeInterval()).toList();
                    process.setAvailableTimeIntervals(TimeSeriesUtils.mergeTimeIntervals(process.getAvailableTimeIntervals(), data));

                    if (process.getAvailableTimeIntervals().size() > 1) {
                        missingIntervalsService.addToMissingIntervals(process.getName(), TimeSeriesUtils.getMissingIntervals(process.getAvailableTimeIntervals()));
                    }
                }

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Process failed: {0}", process);
                e.printStackTrace();
            }
            Thread.sleep(5000);
        }


        processService.inrementRuns();
        LOGGER.log(Level.INFO, "Process information download finished.");
    }

    @Schedule(minute = "05", hour = "9", info = "Every day at 11:05AM")
    public void generateReportMail() {
        if (missingIntervalsService.areSomeNewIntervals()) {
            mailService.send(getAddresses(), getSubject(), EmailUtils.buildEmailText(missingIntervalsService.getMissingTimeIntervalsMap()));
            missingIntervalsService.updateLastMissingIntervals();
        } else {
            mailService.send(getAddresses(), getSubject(), "<h1>Everything is up to date :-)</h1>");
        }
    }

    private String getSubject() {
        return String.format("TP-monitoring report (%s)", LocalDate.now().minusDays(1));
    }


    private String getAddresses() {
        if (recipients.isEmpty()) {
            LOGGER.log(Level.SEVERE, "Could not load list of recipients from properties.");
            return "david.sec@uhk.cz";
        }
        return recipients;
    }


}