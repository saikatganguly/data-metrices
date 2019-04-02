package app.reference.pojo;

import java.util.List;


public class TransactionCycle {
    public String transactionCycleName;
    public List<Geography> geographies;

    public TransactionCycle() {
    }

    public TransactionCycle(String transactionCycleName, List<Geography> geographies) {
        this.transactionCycleName = transactionCycleName;
        this.geographies = geographies;
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
        return "TransactionCycle{" +
                "geographyName='" + transactionCycleName + '\'' +
                ", geographies=" + geographies +
                '}';
    }
}
