package com.metrices.data.services;

import com.metrices.data.model.DataMetricsProject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataMetricsService {

    private List<DataMetricsProject> projects = new ArrayList<>();

    public List<DataMetricsProject> add(DataMetricsProject project) {
        projects.add(project);

        return projects;
    }
}
