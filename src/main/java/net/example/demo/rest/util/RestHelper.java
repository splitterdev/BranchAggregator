package net.example.demo.rest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.example.demo.rest.util.exception.RestException;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class RestHelper {

    private static final Logger logger = LogManager.getLogger(RestHelper.class);

    public RestHelper(String baseUrl, String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("X-COM-PERSIST", "NO");
        headers.set("Accept", "application/vnd.github+json");
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("X-GitHub-Api-Version", "2022-11-28");
        this.entity = new HttpEntity<>(headers);
        this.baseUrl = baseUrl;
    }

    private final HttpEntity<String> entity;
    private final String baseUrl;

    private ResponseEntity<String> callGitHubApi(RestTemplate restTemplate, String url) {
        logger.info("Call GitHub API: {}", url);
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );
    }

    private JsonNode convertResponse(ResponseEntity<String> response) throws IOException {
        try {
            return new ObjectMapper().readTree(response.getBody());
        } catch (JsonProcessingException e) {
            logger.error(e);
            throw new IOException("Unexpected JSON data");
        }
    }

    /**
     * Fetches all repositories of given user.
     * @param user username on GitHub
     * @return JSON response from GitHub, or 404 if user was not found
     * @throws IOException if user was not found, {@link RestException} is thrown.
     * In case of invalid JSON response, {@link IOException} is thrown.
     */
    public JsonNode fetchRepositories(String user) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestErrorHandler());
        ResponseEntity<String> response = callGitHubApi(
                restTemplate,
                baseUrl + "/users/" + user + "/repos"
        );
        if (response.getStatusCode().value() == 404) {
            throw new RestException("404", "This user does not exist");
        }
        return convertResponse(response);
    }

    /**
     * Fetches all branches of given repository.
     * @param repositoryPath repository path (e.g. user/repoName) on GitHub
     * @return JSON response from GitHub
     * @throws IOException in case of invalid JSON response
     */
    public JsonNode fetchBranchesForRepository(String repositoryPath) throws IOException {
        ResponseEntity<String> response = callGitHubApi(
                new RestTemplate(),
                baseUrl + "/repos/" +  repositoryPath + "/branches"
        );
        return convertResponse(response);
    }

}
