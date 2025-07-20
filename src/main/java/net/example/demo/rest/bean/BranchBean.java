package net.example.demo.rest.bean;

import java.io.Serializable;

public class BranchBean implements Serializable {

    public BranchBean() {
        // default constructor
    }

    private String branchName;
    private String lastCommitSHA;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getLastCommitSHA() {
        return lastCommitSHA;
    }

    public void setLastCommitSHA(String lastCommitSHA) {
        this.lastCommitSHA = lastCommitSHA;
    }
}
