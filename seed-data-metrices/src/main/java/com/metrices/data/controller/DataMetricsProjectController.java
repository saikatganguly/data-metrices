package com.metrices.data.controller;

import com.metrices.data.model.AjaxResponseBody;
import com.metrices.data.model.DataMetricsProject;
import com.metrices.data.services.DataMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DataMetricsProjectController {

    private DataMetricsService dataMetricsService;

    @Autowired
    public DataMetricsProjectController(DataMetricsService dataMetricsService) {
        this.dataMetricsService = dataMetricsService;
    }

    @PostMapping("/api/add")
    public ResponseEntity<?> addProject(@RequestBody DataMetricsProject projectData, Errors errors) {

        AjaxResponseBody result = new AjaxResponseBody();

        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {

            result.setMsg(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);
        }

        List<DataMetricsProject> dataMetricsProjects = dataMetricsService.add(projectData);
        if (dataMetricsProjects.isEmpty()) {
            result.setMsg("no project found!");
        } else {
            result.setMsg("success");
        }
        result.setResult(dataMetricsProjects);

        return ResponseEntity.ok(result);

    }

}
