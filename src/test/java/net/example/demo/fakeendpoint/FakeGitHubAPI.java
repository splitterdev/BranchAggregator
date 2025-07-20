package net.example.demo.fakeendpoint;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("test")
public class FakeGitHubAPI {

    @GetMapping("/fakeGitHub/users/{user}/repos")
    public String getRepositories(@PathVariable(name = "user") String user) {
        return "[{"
                + "\"name\": \"fakeRepository\","
                + "\"full_name\": \"" + user + "/fakeRepository\","
                + "\"owner\": {\"login\": \"" + user + "\"},"
                + "\"fork\": false"
                + "}, {"
                + "\"name\": \"fakeForkedRepository2\","
                + "\"full_name\": \"" + user + "/fakeForkedRepository2\","
                + "\"owner\": {\"login\": \"" + user + "\"},"
                + "\"fork\": true"
                + "}, {"
                + "\"name\": \"fakeForkedRepository3\","
                + "\"full_name\": \"" + user + "/fakeForkedRepository3\","
                + "\"owner\": {\"login\": \"" + user + "\"},"
                + "\"fork\": true"
                + "}]";
    }

    @GetMapping("/fakeGitHub/repos/{userName}/{repositoryName}/branches")
    public String getBranches(
            @PathVariable(name = "userName") String userName,
            @PathVariable(name = "repositoryName") String repositoryName
    ) {
        return "[{"
                + "\"name\": \"master\","
                + "\"commit\": {\"sha\": \"shaValue\"}"
                + "}]";
    }

}
