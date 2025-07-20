package net.example.demo.rest.action;

import com.fasterxml.jackson.databind.JsonNode;
import net.example.demo.rest.bean.BranchBean;
import net.example.demo.rest.bean.RepositoryBean;
import net.example.demo.rest.util.RestHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public final class DataFetcher {

    private static final Logger logger = LogManager.getLogger(DataFetcher.class);

    public DataFetcher(String baseURL, String apiKey) {
        this.baseURL = baseURL;
        this.apiKey = apiKey;
    }

    private final String baseURL;
    private final String apiKey;

    /**
     * Aggregates repository data of given user, using GitHub API.<br>
     * Each repository info has included:<br>
     * <ul>
     *     <li>repository name,</li>
     *     <li>username,</li>
     *     <li>a list of branches.</li>
     * </ul>
     * From the branch list, each branch info has included:<br>
     * <ul>
     *     <li>branch name,</li>
     *     <li>last commit SHA on this branch.</li>
     * </ul>
     * @param user username on GitHub
     * @param includeForks should we include fork repositories
     * @return a list of RepositoryBean
     * @throws IOException RestException (if user was not found); server exception in other cases
     */
    public List<RepositoryBean> fetchRepositoryList(String user, boolean includeForks) throws IOException {
        logger.info("Fetch repositories for user \"{}\" / includeForks: {}", user, includeForks);
        RestHelper restHelper = new RestHelper(baseURL, apiKey);
        JsonNode gitRepositoryList = restHelper.fetchRepositories(user);
        List<RepositoryBean> objects = new LinkedList<>();
        for (int i = 0; i < gitRepositoryList.size(); i++) {
            JsonNode repositoryNode = gitRepositoryList.get(i);
            if (repositoryNode.get("fork").asBoolean() != includeForks) {
                continue;
            }
            RepositoryBean repositoryBean = createRepositoryBean(repositoryNode);
            String repositoryPath = repositoryNode.get("full_name").asText();
            JsonNode gitRepoBranchList = restHelper.fetchBranchesForRepository(repositoryPath);
            for (int j = 0; j < gitRepoBranchList.size(); j++) {
                JsonNode branchNode = gitRepoBranchList.get(j);
                BranchBean branchBean = createBranchBean(branchNode);
                repositoryBean.addBranch(branchBean);
            }
            objects.add(repositoryBean);
        }
        logger.info("Fetched {} repositories for user {}", objects.size(), user);
        return objects;
    }

    private RepositoryBean createRepositoryBean(JsonNode sourceNode) {
        RepositoryBean bean = new RepositoryBean();
        bean.setRepositoryName(sourceNode.get("name").asText());
        bean.setOwnerLogin(sourceNode.get("owner").get("login").asText());
        return bean;
    }

    private BranchBean createBranchBean(JsonNode sourceNode) {
        BranchBean bean = new BranchBean();
        bean.setBranchName(sourceNode.get("name").asText());
        bean.setLastCommitSHA(sourceNode.get("commit").get("sha").asText());
        return bean;
    }

}
