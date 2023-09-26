package com.secdavid.tpmonitoring.views;

import com.secdavid.tpmonitoring.enums.Category;
import com.secdavid.tpmonitoring.model.TpProcess;
import com.secdavid.tpmonitoring.services.TPProcessService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class TpProcessView implements Serializable {

    @Inject
    TPProcessService service;
    private List<TpProcess> processes;
    private List<TpProcess> filteredProcesses;

    @PostConstruct
    public void init() {
        processes = service.getProcesses();
    }

    public List<TpProcess> getProcesses() {
        return processes;
    }

    public List<TpProcess> getFilteredProcesses() {
        return filteredProcesses;
    }

    public void setFilteredProcesses(List<TpProcess> filteredProcesses) {
        this.filteredProcesses = filteredProcesses;
    }

    public List<String> getCategories() {
        return Arrays.stream(Category.values()).map(category -> category.name()).collect(Collectors.toList());
    }
}
