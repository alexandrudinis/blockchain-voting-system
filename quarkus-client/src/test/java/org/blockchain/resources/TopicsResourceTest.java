package org.blockchain.resources;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.blockchain.dto.OptionDTO;
import org.blockchain.dto.StoredOptionDTO;
import org.blockchain.dto.StoredTopicDTO;
import org.blockchain.dto.TopicDTO;
import org.blockchain.dto.VoteDTO;
import org.blockchain.dto.VotingResultDTO;
import org.blockchain.service.TopicsService;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@QuarkusTest
public class TopicsResourceTest {

    @InjectMock
    TopicsService topicsService;

    @Test
    public void testCreateTopic() throws Exception {
        TopicDTO topicDTO = new TopicDTO();
        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash("sampleHash");

        when(topicsService.createTopic(any(TopicDTO.class))).thenReturn(transactionReceipt);

        given()
                .contentType(ContentType.JSON)
                .body(topicDTO)
                .when()
                .post("/topics")
                .then()
                .statusCode(RestResponse.StatusCode.OK)
                .body("message", org.hamcrest.Matchers.equalTo("sampleHash"));
    }

    @Test
    public void testGetTopics() throws Exception {
        StoredTopicDTO storedTopicDTO = new StoredTopicDTO();
        List<StoredTopicDTO> topics = Collections.singletonList(storedTopicDTO);

        when(topicsService.getTopics()).thenReturn(topics);

        given()
                .when()
                .get("/topics")
                .then()
                .statusCode(RestResponse.StatusCode.OK)
                .body("$.size()", org.hamcrest.Matchers.is(1));
    }

    @Test
    public void testGetOptions() throws Exception {
        BigInteger topicId = BigInteger.ONE;
        StoredOptionDTO storedOptionDTO = new StoredOptionDTO();
        List<StoredOptionDTO> options = Collections.singletonList(storedOptionDTO);

        when(topicsService.getOptions(eq(topicId))).thenReturn(options);

        given()
                .pathParam("id", topicId)
                .when()
                .get("/topics/options/{id}")
                .then()
                .statusCode(RestResponse.StatusCode.OK)
                .body("$.size()", org.hamcrest.Matchers.is(1));
    }

    @Test
    public void testGetAllResults() throws Exception {
        VotingResultDTO votingResultDTO = new VotingResultDTO();
        List<VotingResultDTO> results = Collections.singletonList(votingResultDTO);

        when(topicsService.getAllVotingResults()).thenReturn(results);

        given()
                .when()
                .get("/topics/results")
                .then()
                .statusCode(RestResponse.StatusCode.OK)
                .body("$.size()", org.hamcrest.Matchers.is(1));
    }

    @Test
    public void testEnableStartVoting() throws Exception {
        BigInteger topicId = BigInteger.ONE;
        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash("sampleHash");

        when(topicsService.enableStartVoting(eq(topicId))).thenReturn(transactionReceipt);

        given()
                .pathParam("id", topicId)
                .when()
                .put("/topics/startVoting/{id}")
                .then()
                .statusCode(RestResponse.StatusCode.OK)
                .body("message", org.hamcrest.Matchers.equalTo("sampleHash"));
    }

    @Test
    public void testEndVoting() throws Exception {
        BigInteger topicId = BigInteger.ONE;
        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash("sampleHash");

        when(topicsService.endVoting(any())).thenReturn(transactionReceipt);

        given()
                .pathParam("id", topicId)
                .when()
                .put("/topics/finishVoting/{id}")
                .then()
                .statusCode(RestResponse.StatusCode.OK)
                .body("message", org.hamcrest.Matchers.equalTo("sampleHash"));
    }

    @Test
    public void testAddOption() throws Exception {
        OptionDTO optionDTO = new OptionDTO();
        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash("sampleHash");

        when(topicsService.addOption(any(OptionDTO.class))).thenReturn(transactionReceipt);

        given()
                .contentType(ContentType.JSON)
                .body(optionDTO)
                .when()
                .post("/topics/option")
                .then()
                .statusCode(RestResponse.StatusCode.OK)
                .body("message", org.hamcrest.Matchers.equalTo("sampleHash"));
    }

    @Test
    public void testVote() throws Exception {
        VoteDTO voteDTO = new VoteDTO();
        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash("sampleHash");

        when(topicsService.vote(any())).thenReturn(transactionReceipt);

        given()
                .contentType(ContentType.JSON)
                .body(voteDTO)
                .when()
                .post("/topics/vote")
                .then()
                .statusCode(RestResponse.StatusCode.OK)
                .body("message", org.hamcrest.Matchers.equalTo("sampleHash"));
    }
}
