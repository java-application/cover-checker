package com.naver.nid.cover.github.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.naver.nid.cover.github.manager.GithubDiffManager;
import com.naver.nid.cover.github.manager.GithubPullRequestManager;
import com.naver.nid.cover.parser.diff.DiffParser;
import com.naver.nid.cover.parser.diff.model.Diff;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.egit.github.core.CommitFile;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@Slf4j
class GithubDiffParseTest {

	@Test
	public void parsedGithubDiff() throws IOException {
		GithubPullRequestManager mockPrManager = mock(GithubPullRequestManager.class);
		GithubDiffManager mockDiffManager = mock(GithubDiffManager.class);
		List<CommitFile> result = new Gson()
				.fromJson(new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("github_diff.diff")))
						, new TypeToken<List<CommitFile>>() {}.getType());
		doReturn(result).when(mockDiffManager).getFiles();
		doReturn(mockDiffManager).when(mockPrManager).diffManager();
		log.debug("original {}", result);
		GithubDiffReader githubDiffParser = new GithubDiffReader(mockPrManager);
		List<Diff> parsedResult = githubDiffParser.parse().collect(Collectors.toList());
		log.debug("parsed result {}", parsedResult);
		assertEquals(result.size(), parsedResult.size());

	}

}