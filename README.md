# Branch Aggregator (Demo)

---

Demo REST service that aggregates repository data using GitHub REST API.

The default endpoint takes username from path variable, then looks for all public repositories that are not forks.
For each repository, additional REST call is performed to get all branches.

Returned data is in JSON format.

---

## Configuration

In `application.properties`:

| Variable            | Description                                                | Default                 |
|---------------------|------------------------------------------------------------|-------------------------|
| `gitHubBaseUrl`     | Base url for GitHub REST API                               | https://api.github.com  |
| `gitHubSecureToken` | Fine-grained personal access token from GitHub (generated) | <your_secure_token>     |

You must provide your own `gitHubSecureToken`;

For how-to obtain one, refer to [GitHub Docs](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-fine-grained-personal-access-token).

---

## Endpoint usage

### Example request

```bash
curl "http://localhost:8080/repodata/octocat"
```
### Example response (JSON format)

```json lines
[
  {
    "repositoryName": "someExampleRepository",
    "ownerLogin": "userName",
    "branches": [
      {
        "branchName": "exampleBranch",
        "lastCommitSHA": "123abc"
      }
    ]
  }
]
```
### Error handling
If the user could not be found, an error message is returned:
```bash
curl "http://localhost:8080/repodata/nonExistingUser"
```

```json
{
  "status": "404",
  "message": "This user does not exist"
}
```

---

## Running Tests

```bash
./gradlew test
```

---

## Tech Stack

- Java 19 +
- Spring Boot 3.5
- Gradle

---

## License

This demo application runs under MIT License.
