package app.web;

import app.converter.SonarqubeDataCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SonarqubeDataCollectorController {

    private SonarqubeDataCollector sonarqubeDataCollector;

    @Autowired
    public SonarqubeDataCollectorController(SonarqubeDataCollector sonarqubeDataCollector) {
        this.sonarqubeDataCollector = sonarqubeDataCollector;
    }

    @RequestMapping("/collect/{projectName}")
    public String collect(@PathVariable(name = "projectName") String projectName) {
        return sonarqubeDataCollector.collect(projectName);
    }
}