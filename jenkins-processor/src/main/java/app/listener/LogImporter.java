package app.listener;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class LogImporter {

    @Async
    public void fetchLogsFromURL(String url){
        //fetch logs for each builds
    }
}
