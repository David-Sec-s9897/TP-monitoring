package com.secdavid.tpmonitoring.services;

import com.secdavid.tpmonitoring.builder.ProcessBuilder;
import com.secdavid.tpmonitoring.enums.Category;
import com.secdavid.tpmonitoring.model.TpProcess;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class TPProcessService implements Serializable {

    private static final long serialVersionUID = -5451417222777142919L;

    List<TpProcess> processes;
    int runs = 0;
    public static final String CONTROL_AREA_DOMAIN = "10YCH-SWISSGRIDZ";

    @PostConstruct
    public void init() {
        this.processes = new ArrayList<>();
        processes.add(new ProcessBuilder().name("17.1.E - Frequency Containment Reserve (FCR)").documentType("A83").processType("A16").businessType("A95").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).build());
        processes.add(new ProcessBuilder().name("17.1.E - Automatic Frequency Restoration Reserve (aFRR)").documentType("A83").processType("A16").businessType("A96").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).build());
        processes.add(new ProcessBuilder().name("17.1.E - Replacement Reserve (RR)").documentType("A83").processType("A16").businessType("A98").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).build());
        processes.add(new ProcessBuilder().name("17.1.D - Frequency Containment Reserve (FCR)").documentType("A82").processType("A34").businessType("A95").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).build());
        processes.add(new ProcessBuilder().name("17.1.D - Automatic Frequency Restoration Reserve (aFRR)").documentType("A82").processType("A34").businessType("A96").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).build());
        processes.add(new ProcessBuilder().name("17.1.D - Replacement Reserve (RR)").documentType("A82").processType("A34").businessType("A98").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).build());
        processes.add(new ProcessBuilder().name("12.3.A - Current balancing state").documentType("A86").processType("A16").businessType("B33").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).build());
        processes.add(new ProcessBuilder().name("17.1.F - Automatic Frequency Restoration Reserve (aFRR)").documentType("A84").processType("A60").businessType("A96").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).build());
        processes.add(new ProcessBuilder().name("12.3.E - Automatic frequency restoration reserve (aFRR)").documentType("A24").processType("A51").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).balancingDirection("UP").build());
        processes.add(new ProcessBuilder().name("12.3.E - Automatic frequency restoration reserve (aFRR)").documentType("A24").processType("A51").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).balancingDirection("DOWN").build());
        processes.add(new ProcessBuilder().name("12.3.E - Manual Frequency Restoration Reserve scheduled activation (mFRR SA)").documentType("A24").processType("A60").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).balancingDirection("UP").build());
        processes.add(new ProcessBuilder().name("12.3.E - Manual Frequency Restoration Reserve scheduled activation (mFRR SA)").documentType("A24").processType("A60").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).balancingDirection("DOWN").build());
        processes.add(new ProcessBuilder().name("12.3.E - Manual Frequency Restoration Reserve direct activation (mFRR DA)").documentType("A24").processType("A61").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).balancingDirection("UP").build());
        processes.add(new ProcessBuilder().name("12.3.E - Manual Frequency Restoration Reserve direct activation (mFRR DA)").documentType("A24").processType("A61").controlAreaDomain(CONTROL_AREA_DOMAIN).category(Category.BALANCING).balancingDirection("DOWN").build());

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

    public List<TpProcess> getProcesses() {
        return processes;
    }

    public int getRuns() {
        return runs;
    }

    public void inrementRuns() {
        runs++;
    }
}
