package app.reference.pojo;

import java.util.List;


public class TransactionCycle {
    private String id;
    public String transactionCycleName;
    public List<Geography> geographies;

    public TransactionCycle() {
    }

    public TransactionCycle(String id, String transactionCycleName, List<Geography> geographies) {
        this.id = id;
        this.transactionCycleName = transactionCycleName;
        this.geographies = geographies;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionCycleName() {
        return transactionCycleName;
    }

    public void setTransactionCycleName(String transactionCycleName) {
        this.transactionCycleName = transactionCycleName;
    }

    public List<Geography> getGeographies() {
        return geographies;
    }

    public void setGeographies(List<Geography> geographies) {
        this.geographies = geographies;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", transactionCycleName='" + transactionCycleName + '\'' +
                ", geographies=" + geographies +
                '}';
    }
}
