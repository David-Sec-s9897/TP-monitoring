package com.secdavid.tpmonitoring.services;

import com.secdavid.tpmonitoring.builder.ProcessBuilder;
import com.secdavid.tpmonitoring.enums.Category;
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
        TpProcess process = new ProcessBuilder()
                .name("17.1.E - Frequency Containment Reserve (FCR)")
                .processType("A16")
                .areaDomain(CONTROL_AREA_DOMAIN)
                .businessType("A95")
                .documentType("A83")
                .category(Category.BALANCING)
                .build();
        TpProcess process2 = new ProcessBuilder()
                .name("17.1.E - Automatic Frequency Restoration Reserve (aFRR)")
                .processType("A16")
                .areaDomain(CONTROL_AREA_DOMAIN)
                .businessType("A96")
                .documentType("A83")
                .category(Category.BALANCING)
                .build();
        TpProcess process3 =  new ProcessBuilder()
                .name("17.1.E - Replacement Reserve (RR)")
                .processType("A16")
                .areaDomain(CONTROL_AREA_DOMAIN)
                .businessType("A98")
                .documentType("A83")
                .category(Category.BALANCING)
                .build();
        this.processes.add(process);
        this.processes.add(process2);
        this.processes.add(process3);

    }

    public List<TpProcess> getProcesses() {
        return processes;
    }
}
