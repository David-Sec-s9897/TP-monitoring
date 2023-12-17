import com.secdavid.tpmonitoring.builder.ProcessBuilder;
import com.secdavid.tpmonitoring.entsoe.EntsoeRestClient;
import com.secdavid.tpmonitoring.enums.Category;
import com.secdavid.tpmonitoring.mail.MailService;
import com.secdavid.tpmonitoring.model.TpProcess;
import com.secdavid.tpmonitoring.model.entsoe.TimeInterval;
import com.secdavid.tpmonitoring.services.TPProcessService;
import com.secdavid.tpmonitoring.tasks.BackgroundTaskManager;
import com.secdavid.tpmonitoring.tasks.MissingIntervalsService;
import com.secdavid.tpmonitoring.util.EmailUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class BackgroundTaskManagerTest {

    @InjectMocks
    BackgroundTaskManager backgroundTaskManager;

    @Mock
    MailService mailService;

    @Mock
    MissingIntervalsService missingIntervalsService;

    @Mock
    TPProcessService processService;


    @Mock
    EntsoeRestClient restClient;

    List<TpProcess> processes = new ArrayList<>();
    public static final String CONTROL_AREA_DOMAIN = "10YCH-SWISSGRIDZ";



    @Before
    public void setUp(){
        addProcesses();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void mailSummaryTest() throws InterruptedException {
        Mockito.when(missingIntervalsService.getMissingTimeIntervalsMapFilterIgnored()).thenReturn(Collections.emptyMap());
        Mockito.when(processService.getProcesses()).thenReturn(processes);
        System.setProperty("tp.mail.recipients", "");
        backgroundTaskManager.setRecipients("");
        backgroundTaskManager.generateReportMail();
        Assert.assertEquals(processes.size(), processService.getProcesses().size());
        Mockito.verify(processService,Mockito.times(2)).getProcesses();
    }

    @Test
    public void mailMissingIntervalsTest() throws InterruptedException {
        Map<String, List<TimeInterval>> timeIntervalMap = new HashMap<>();
        timeIntervalMap.put("Test1", List.of(new TimeInterval(ZonedDateTime.now().minusDays(1), ZonedDateTime.now()),
                new TimeInterval(ZonedDateTime.now().minusDays(3), ZonedDateTime.now().minusDays(2))));
        Mockito.when(missingIntervalsService.getMissingTimeIntervalsMapFilterIgnored()).thenReturn(timeIntervalMap);
        Mockito.when(missingIntervalsService.getMissingTimeIntervalsMap()).thenReturn(timeIntervalMap);
        Mockito.when(processService.getProcesses()).thenReturn(processes);
        System.setProperty("tp.mail.recipients", "");
        backgroundTaskManager.setRecipients("");
        backgroundTaskManager.generateReportMail();
        Assert.assertEquals(processes.size(), processService.getProcesses().size());
        Mockito.verify(processService,Mockito.times(1)).getProcesses();
        Mockito.verify(missingIntervalsService,Mockito.times(1)).getMissingTimeIntervalsMap();
        Map<String, List<TimeInterval>> map = missingIntervalsService.getMissingTimeIntervalsMap();
        String s = EmailUtils.buildUnavailabilityEmailText(map);
        System.out.println(s);
    }


    private void addProcesses(){
        this.processes = new ArrayList<>();
        processes.add(new ProcessBuilder().name("17.1.E - Frequency Containment Reserve (FCR)").documentType("A83").processType("A16").businessType("A95").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).build());
        processes.add(new ProcessBuilder().name("17.1.E - Automatic Frequency Restoration Reserve (aFRR)").documentType("A83").processType("A16").businessType("A96").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).build());
        processes.add(new ProcessBuilder().name("17.1.E - Replacement Reserve (RR)").documentType("A83").processType("A16").businessType("A98").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).build());
        processes.add(new ProcessBuilder().name("17.1.D - Frequency Containment Reserve (FCR)").documentType("A82").processType("A34").businessType("A95").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).build());
        processes.add(new ProcessBuilder().name("17.1.D - Automatic Frequency Restoration Reserve (aFRR)").documentType("A82").processType("A34").businessType("A96").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).build());
        processes.add(new ProcessBuilder().name("17.1.D - Replacement Reserve (RR)").documentType("A82").processType("A34").businessType("A98").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).build());
        processes.add(new ProcessBuilder().name("12.3.A - Current balancing state").documentType("A86").processType("A16").businessType("B33").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).build());
        processes.add(new ProcessBuilder().name("17.1.F - Automatic Frequency Restoration Reserve (aFRR)").documentType("A84").processType("A16").businessType("A96").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).build());

        //processes.add(new ProcessBuilder().name("12.3.E - Automatic frequency restoration reserve (aFRR)").documentType("A24").processType("A51").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).balancingDirection("UP").build());
        //processes.add(new ProcessBuilder().name("12.3.E - Automatic frequency restoration reserve (aFRR)").documentType("A24").processType("A51").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).balancingDirection("DOWN").build());
        //processes.add(new ProcessBuilder().name("12.3.E - Manual Frequency Restoration Reserve scheduled activation (mFRR SA)").documentType("A24").processType("A60").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).balancingDirection("UP").build());
        //processes.add(new ProcessBuilder().name("12.3.E - Manual Frequency Restoration Reserve scheduled activation (mFRR SA)").documentType("A24").processType("A60").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).balancingDirection("DOWN").build());
        //processes.add(new ProcessBuilder().name("12.3.E - Manual Frequency Restoration Reserve direct activation (mFRR DA)").documentType("A24").processType("A61").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).balancingDirection("UP").build());
        //processes.add(new ProcessBuilder().name("12.3.E - Manual Frequency Restoration Reserve direct activation (mFRR DA)").documentType("A24").processType("A61").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).balancingDirection("DOWN").build());

        processes.add(new ProcessBuilder().name("6.1.A - Actual Total Load").documentType("A65").processType("A16").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.LOAD).build());
        processes.add(new ProcessBuilder().name("6.1.B - Day-ahead Total Load Forecast").documentType("A65").processType("A01").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.LOAD).build());

        processes.add(new ProcessBuilder().name("14.1.D - Day-ahead Generation Forecasts for Wind and Solar").documentType("A69").processType("A01").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.GENERATION).productionType("Solar").build());

        processes.add(new ProcessBuilder().name("12.1.D - Day-ahead Prices").documentType("A44").controlAreaDomain(CONTROL_AREA_DOMAIN).outDomain(CONTROL_AREA_DOMAIN).category(Category.TRANSMISSION).build());
        processes.add(new ProcessBuilder().name("11.1 - Forecasted Day-ahead Transfer Capacities DE/LU - CH").documentType("A61").controlAreaDomain("10YBE----------2").outDomain("10YGB----------A").category(Category.TRANSMISSION).inArea("BZN|DE-LU").outArea("BZN|CH").businessType("A01").build());
        processes.add(new ProcessBuilder().name("11.1 - Forecasted Day-ahead Transfer Capacities CH - DE/LU").documentType("A61").controlAreaDomain("10YBE----------2").outDomain("10YGB----------A").category(Category.TRANSMISSION).inArea("BZN|CH").outArea("BZN|DE-LU").businessType("A01").build());
        processes.add(new ProcessBuilder().name("11.1 - Forecasted Day-ahead Transfer Capacities AT - CH").documentType("A61").controlAreaDomain("10YBE----------2").outDomain("10YGB----------A").category(Category.TRANSMISSION).inArea("CTA|AT").outArea("CTA|CH").businessType("A01").build());
        processes.add(new ProcessBuilder().name("11.1 - Forecasted Day-ahead Transfer Capacities CH - AT ").documentType("A61").controlAreaDomain("10YBE----------2").outDomain("10YGB----------A").category(Category.TRANSMISSION).inArea("CTA|CH").outArea("CTA|AT").businessType("A01").build());
        processes.add(new ProcessBuilder().name("11.1 - Forecasted Day-ahead Transfer Capacities CH - FR").documentType("A61").controlAreaDomain("10YBE----------2").outDomain("10YGB----------A").category(Category.TRANSMISSION).inArea("CTA|CH").outArea("CTA|FR").businessType("A01").build());
        processes.add(new ProcessBuilder().name("11.1 - Forecasted Day-ahead Transfer Capacities CH - IT").documentType("A61").controlAreaDomain("10YBE----------2").outDomain("10YGB----------A").category(Category.TRANSMISSION).inArea("CTA|CH").outArea("CTA|IT").businessType("A01").build());
        processes.add(new ProcessBuilder().name("11.1 - Forecasted Day-ahead Transfer Capacities FR - CH").documentType("A61").controlAreaDomain("10YBE----------2").outDomain("10YGB----------A").category(Category.TRANSMISSION).inArea("CTA|FR").outArea("CTA|CH").businessType("A01").build());
        processes.add(new ProcessBuilder().name("11.1 - Forecasted Day-ahead Transfer Capacities IT - CH").documentType("A61").controlAreaDomain("10YBE----------2").outDomain("10YGB----------A").category(Category.TRANSMISSION).inArea("CTA|IT").outArea("CTA|CH").businessType("A01").build());
        processes.add(new ProcessBuilder().name("12.1.G - Physical Flows AT - CH").documentType("A11").controlAreaDomain(CONTROL_AREA_DOMAIN).outDomain("10YDE-RWENET---I").category(Category.TRANSMISSION).inArea("CTA|AT").outArea("CTA|CH").build());
        processes.add(new ProcessBuilder().name("12.1.G - Physical Flows CH - AT").documentType("A11").controlAreaDomain(CONTROL_AREA_DOMAIN).outDomain("10YDE-RWENET---I").category(Category.TRANSMISSION).inArea("CTA|CH").outArea("CTA|AT").build());
        processes.add(new ProcessBuilder().name("12.1.G - Physical Flows CH - FR").documentType("A11").controlAreaDomain(CONTROL_AREA_DOMAIN).outDomain("10YDE-RWENET---I").category(Category.TRANSMISSION).inArea("CTA|CH").outArea("CTA|FR").build());
        processes.add(new ProcessBuilder().name("12.1.G - Physical Flows CH - IT").documentType("A11").controlAreaDomain(CONTROL_AREA_DOMAIN).outDomain("10YDE-RWENET---I").category(Category.TRANSMISSION).inArea("CTA|CH").outArea("CTA|IT").build());
        processes.add(new ProcessBuilder().name("12.1.G - Physical Flows FR - CH").documentType("A11").controlAreaDomain(CONTROL_AREA_DOMAIN).outDomain("10YDE-RWENET---I").category(Category.TRANSMISSION).inArea("CTA|FR").outArea("CTA|CH").build());
        processes.add(new ProcessBuilder().name("12.1.G - Physical Flows IT - CH").documentType("A11").controlAreaDomain(CONTROL_AREA_DOMAIN).outDomain("10YDE-RWENET---I").category(Category.TRANSMISSION).inArea("CTA|IT").outArea("CTA|CH").build());
        processes.add(new ProcessBuilder().name("12.1.F - Total Commercial Schedules DE/LU - CH").documentType("A09").controlAreaDomain(CONTROL_AREA_DOMAIN).outDomain("10Y1001A1001A82H").category(Category.TRANSMISSION).inArea("BZN|DE-LU").outArea("BZN|CH").build());
        processes.add(new ProcessBuilder().name("12.1.F - Total Commercial Schedules AT - CH").documentType("A09").controlAreaDomain(CONTROL_AREA_DOMAIN).outDomain("10Y1001A1001A82H").category(Category.TRANSMISSION).inArea("BZN|AT").outArea("BZN|CH").build());
        processes.add(new ProcessBuilder().name("12.1.F - Total Commercial Schedules CH - DE/LU").documentType("A09").controlAreaDomain(CONTROL_AREA_DOMAIN).outDomain("10Y1001A1001A82H").category(Category.TRANSMISSION).inArea("BZN|CH").outArea("BZN|DE-LU").build());
        processes.add(new ProcessBuilder().name("12.1.F - Total Commercial Schedules CH - AT").documentType("A09").controlAreaDomain(CONTROL_AREA_DOMAIN).outDomain("10Y1001A1001A82H").category(Category.TRANSMISSION).inArea("BZN|CH").outArea("BZN|AT").build());
        processes.add(new ProcessBuilder().name("12.1.F - Total Commercial Schedules CH - FR").documentType("A09").controlAreaDomain(CONTROL_AREA_DOMAIN).outDomain("10Y1001A1001A82H").category(Category.TRANSMISSION).inArea("CTA|CH").outArea("CTA|FR").build());
        processes.add(new ProcessBuilder().name("12.1.F - Total Commercial Schedules CH - IT").documentType("A09").controlAreaDomain(CONTROL_AREA_DOMAIN).outDomain("10Y1001A1001A82H").category(Category.TRANSMISSION).inArea("CTA|CH").outArea("CTA|IT").build());
        processes.add(new ProcessBuilder().name("12.1.F - Total Commercial Schedules FR - CH").documentType("A09").controlAreaDomain(CONTROL_AREA_DOMAIN).outDomain("10Y1001A1001A82H").category(Category.TRANSMISSION).inArea("CTA|FR").outArea("CTA|CH").build());
        processes.add(new ProcessBuilder().name("12.1.F - Total Commercial Schedules IT - CH").documentType("A09").controlAreaDomain(CONTROL_AREA_DOMAIN).outDomain("10Y1001A1001A82H").category(Category.TRANSMISSION).inArea("CTA|IT").outArea("CTA|CH").build());
    }

}
