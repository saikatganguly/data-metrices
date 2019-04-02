package app.reference;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@Service
public class ReferenceData {

    public static final List<String> REPOS1 = asList("sampleRepo");
    public static final List<String> REPOS2 = asList("sampleRepo");
    public static final List<String> PROJECTS1 = asList("CardNG");
    public static final List<String> PROJECTS2 = asList("CL");
    public static final List<String> GEOGRAPHY1 = asList("CardNG");
    public static final List<String> GEOGRAPHY2 = asList("CL");
    public static final List<String> TRANSACTION_CYCLE1 = asList("Card");
    public static final List<String> TRANSACTION_CYCLE2 = asList("Payment");

    public List<String> getReposByProject(String project) {
        return REPOS1;
    }

    public Map<String, List<String>> getReposByGeography(String geography) {
        Map<String, List<String>> response = new HashMap<>();
        response.put("CardNG", REPOS1);
        response.put("CL", REPOS2);
        return response;
    }

    public List<String> getProjectsByGeography(String geography) {
        Map<String, List<String>> response = new HashMap<>();
        response.put("US", PROJECTS1);
        response.put("UK", PROJECTS2);
        return PROJECTS1;
    }

    public List<String> getGeographyByTransactionCycle(String transactionCycle) {
        return GEOGRAPHY1;
    }

    public List<String> getTransactionCyclesByOrg(String org) {
        return TRANSACTION_CYCLE1;
    }
}
