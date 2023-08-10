package com.secdavid.tpmonitoring.services;

import com.secdavid.tpmonitoring.builder.ProcessBuilder;
import com.secdavid.tpmonitoring.model.MasterData;
import com.secdavid.tpmonitoring.model.TpProcess;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class TPProcessService {

    List<TpProcess> processes;
    public static final String CONTROL_AREA_DOMAIN="10YCH-SWISSGRIDZ";

    @PostConstruct
    public void init(){
        this.processes = new ArrayList<>();
        TpProcess process = new TpProcess("17.1.E - Frequency Containment Reserve (FCR)");
        process.setMasterData(new MasterData("A83", CONTROL_AREA_DOMAIN, "A95", "A16"));
        TpProcess process2 = new TpProcess("17.1.E - Automatic Frequency Restoration Reserve (aFRR)");
        process2.setMasterData(new MasterData("A83", CONTROL_AREA_DOMAIN, "A96", "A16"));
        TpProcess process3 = new TpProcess("17.1.E - Replacement Reserve (RR)");
        process3.setMasterData(new MasterData("A83", CONTROL_AREA_DOMAIN, "A98", "A16"));
        //processes.add( ProcessBuilder.newProcess().name().....build());
        this.processes.add(process);
        this.processes.add(process2);
        this.processes.add(process3);

    }

    public List<TpProcess> getProcesses() {
        return processes;
    }
}
