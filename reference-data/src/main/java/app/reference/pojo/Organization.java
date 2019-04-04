package app.reference.pojo;

import java.util.List;


public class Organization {
    public String organizationName;
    public List<TransactionCycle> transactionCycles;

    public Organization() {
    }

    public Organization(String organizationName, List<TransactionCycle> transactionCycles) {
        this.organizationName = organizationName;
        this.transactionCycles = transactionCycles;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public List<TransactionCycle> getTransactionCycles() {
        return transactionCycles;
    }

    public void setTransactionCycles(List<TransactionCycle> transactionCycles) {
        this.transactionCycles = transactionCycles;
    }

    @Override
    public String toString() {
        return "{" +
                "organizationName='" + organizationName + '\'' +
                ", transactionCycles=" + transactionCycles +
                '}';
    }
}
