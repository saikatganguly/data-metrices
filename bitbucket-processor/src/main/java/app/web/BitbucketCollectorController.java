package app.web;

import app.collector.BitbucketCollector;
import app.model.CommitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@RestController
public class BitbucketCollectorController {

    private BitbucketCollector bitbucketCollector;

    @Autowired
    public BitbucketCollectorController(BitbucketCollector bitbucketCollector) {
        this.bitbucketCollector = bitbucketCollector;
    }

    @RequestMapping("/collect")
    public List<Map<String, CommitInfo>> collect() throws MalformedURLException {
        return bitbucketCollector.collect();
    }
}