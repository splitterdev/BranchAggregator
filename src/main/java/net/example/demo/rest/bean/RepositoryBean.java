package net.example.demo.rest.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RepositoryBean implements Serializable {

    public RepositoryBean() {
        // default constructor
    }

    private String repositoryName;
    private String ownerLogin;
    private final List<BranchBean> branches = new ArrayList<>();


    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public List<BranchBean> getBranches() {
        return branches;
    }

    public void addBranch(BranchBean object) {
        branches.add(object);
    }
}
