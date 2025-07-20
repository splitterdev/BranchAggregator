package net.example.demo.controller;

import net.example.demo.rest.bean.error.ErrorBean;
import net.example.demo.rest.action.DataFetcher;
import net.example.demo.rest.util.exception.RestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MainController {

    private static final Logger logger = LogManager.getLogger(MainController.class);

    @Value("${gitHubBaseUrl}")
    private String gitHubUrl;

    @Value("${gitHubSecureToken}")
    private String gitHubSecureToken;

    /**
     * Aggregates repository data of given user. Forked repositories are omitted.<br>
     * @see DataFetcher#fetchRepositoryList(String, boolean)
     * @param user username on GitHub
     * @return a bean object to be formatted to JSON
     */
    @GetMapping("/repodata/{user}")
    public Object aggregateRepositoriesNotFork(@PathVariable(name = "user") String user) {
        try {
            DataFetcher fetcher = new DataFetcher(gitHubUrl, gitHubSecureToken);
            return fetcher.fetchRepositoryList(user, false);
        } catch (RestException e) {
            logger.error(e);
            return new ErrorBean(e);
        } catch (IOException ioe) {
            logger.error(ioe);
            throw new RuntimeException(ioe);
        }
    }

}
