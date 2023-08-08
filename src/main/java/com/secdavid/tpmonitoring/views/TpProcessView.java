package com.secdavid.tpmonitoring.views;

import com.secdavid.tpmonitoring.model.TpProcess;
import com.secdavid.tpmonitoring.services.TPProcessService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class TpProcessView implements Serializable {

    private List<TpProcess> processes;

    @Inject
    TPProcessService service;

    @PostConstruct
    public void init() {
        processes = service.getProcesses();
    }

    public List<TpProcess> getProcesses() {
        return processes;
    }
}
