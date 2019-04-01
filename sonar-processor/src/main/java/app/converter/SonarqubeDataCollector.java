package app.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SonarqubeDataCollector {

    private static String BASE_PATH = "http://192.168.99.100:9000";

    private RestTemplate sonarRestTemplate;

    @Autowired
    public SonarqubeDataCollector(@Qualifier("sonarRestTemplate") RestTemplate sonarRestTemplate) {
        this.sonarRestTemplate = sonarRestTemplate;
    }

    public String collect(String projectName) {
        ResponseEntity<String> response = sonarRestTemplate.exchange(BASE_PATH + "/api/measures/component?metricKeys=ncloc,complexity,violations,coverage&component=" + projectName,
                HttpMethod.GET,
                new HttpEntity<>(""),
                String.class);
        return response.getBody();
    }
}
