package net.example.demo;

import net.example.demo.rest.action.DataFetcher;
import net.example.demo.rest.bean.RepositoryBean;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Assertions;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class DemoApplicationTests {

	@LocalServerPort
	private int port;

	@Test
	void givenFakeUserAndNoForks_whenFetchRepositoryList_thenSingleBranchDataCorrect() throws IOException {

		String baseUrl = "http://localhost:" + port + "/fakeGitHub";
		String userName = "fakeUser";
		boolean includeForks = false;
		String fakeApiKey = "abc";

		DataFetcher fetcher = new DataFetcher(baseUrl, fakeApiKey);
		List<RepositoryBean> beans = fetcher.fetchRepositoryList(userName, includeForks);

		Assertions.assertEquals(1, beans.size(), "Fetched repository count must be 1");
		Assertions.assertEquals(userName, beans.get(0).getOwnerLogin(), "User name must be \"" + userName + "\"");
		Assertions.assertEquals("fakeRepository", beans.get(0).getRepositoryName(), "Repository name must be \"fakeRepository\"");
		Assertions.assertEquals(1, beans.get(0).getBranches().size(), "Fetched branch count must be 1");
		Assertions.assertEquals("master", beans.get(0).getBranches().get(0).getBranchName(), "Fetched branch must have a name \"master\"");
		Assertions.assertEquals("shaValue", beans.get(0).getBranches().get(0).getLastCommitSHA(), "Fetched branch must have SHA \"shaValue\"");
	}

}
