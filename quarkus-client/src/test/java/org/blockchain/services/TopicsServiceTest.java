package org.blockchain.services;

import org.blockchain.dto.*;
import org.blockchain.producers.VotingSystemProducer;
import org.blockchain.service.TopicsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.web3j.model.VotingSystem;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class TopicsServiceTest {

    @Mock
    VotingSystemProducer votingSystemProducer;

    @Mock
    VotingSystem votingSystem;

    @Mock
    RemoteFunctionCall<TransactionReceipt> remoteFunctionCall;

    @InjectMocks
    TopicsService topicsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(votingSystemProducer.getVotingSystem()).thenReturn(votingSystem);
    }

    @Test
    public void testCreateTopic() throws Exception {
        TopicDTO topicDTO = new TopicDTO();
        topicDTO.setTitle("Sample Topic");

        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash("sampleHash");

        when(votingSystem.addTopic(eq("Sample Topic"))).thenReturn(remoteFunctionCall);
        when(remoteFunctionCall.send()).thenReturn(transactionReceipt);

        TransactionReceipt result = topicsService.createTopic(topicDTO);
        assertEquals("sampleHash", result.getTransactionHash());
    }

    @Test
    public void testEnableStartVoting() throws Exception {
        BigInteger topicId = BigInteger.ONE;

        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash("sampleHash");

        when(votingSystem.startVoting(eq(topicId))).thenReturn(remoteFunctionCall);
        when(remoteFunctionCall.send()).thenReturn(transactionReceipt);

        TransactionReceipt result = topicsService.enableStartVoting(topicId);
        assertEquals("sampleHash", result.getTransactionHash());
    }

    @Test
    public void testEndVoting() throws Exception {
        BigInteger topicId = BigInteger.ONE;

        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash("sampleHash");

        when(votingSystem.finishVoting(eq(topicId))).thenReturn(remoteFunctionCall);
        when(remoteFunctionCall.send()).thenReturn(transactionReceipt);

        TransactionReceipt result = topicsService.endVoting(topicId);
        assertEquals("sampleHash", result.getTransactionHash());
    }

    @Test
    public void testAddOption() throws Exception {
        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setTopicIndex(BigInteger.ONE);
        optionDTO.setTitle("Option 1");

        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash("sampleHash");

        when(votingSystem.addOption(eq(BigInteger.ONE), eq("Option 1"))).thenReturn(remoteFunctionCall);
        when(remoteFunctionCall.sendAsync()).thenReturn(CompletableFuture.completedFuture(transactionReceipt));

        TransactionReceipt result = topicsService.addOption(optionDTO);
        assertEquals("sampleHash", result.getTransactionHash());
    }

    @Test
    public void testVote() throws Exception {
        VoteDTO voteDTO = new VoteDTO();
        voteDTO.setTopicIndex(BigInteger.ONE);
        voteDTO.setOptionIndex(BigInteger.ONE);

        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash("sampleHash");

        when(votingSystemProducer.getVotingSystemBasedOnPublicAddress(eq(voteDTO))).thenReturn(votingSystem);
        when(votingSystem.vote(eq(BigInteger.ONE), eq(BigInteger.ONE))).thenReturn(remoteFunctionCall);
        when(remoteFunctionCall.sendAsync()).thenReturn(CompletableFuture.completedFuture(transactionReceipt));

        TransactionReceipt result = topicsService.vote(voteDTO);
        assertEquals("sampleHash", result.getTransactionHash());
    }

    @Test
    public void testGetTopics() throws Exception {
        StoredTopicDTO storedTopicDTO = new StoredTopicDTO();
        List<StoredTopicDTO> topics = Collections.singletonList(storedTopicDTO);

        when(votingSystemProducer.getTopics()).thenReturn(topics);

        List<StoredTopicDTO> result = topicsService.getTopics();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetOptions() throws Exception {
        BigInteger topicId = BigInteger.ONE;
        StoredOptionDTO storedOptionDTO = new StoredOptionDTO();
        List<StoredOptionDTO> options = Collections.singletonList(storedOptionDTO);

        when(votingSystemProducer.getOptions(eq(topicId))).thenReturn(options);

        List<StoredOptionDTO> result = topicsService.getOptions(topicId);
        assertEquals(1, result.size());
    }

    @Test
    public void testGetAllVotingResults() throws Exception {
        VotingResultDTO votingResultDTO = new VotingResultDTO();
        List<VotingResultDTO> results = Collections.singletonList(votingResultDTO);

        when(votingSystemProducer.getAllVotingResults()).thenReturn(results);

        List<VotingResultDTO> result = topicsService.getAllVotingResults();
        assertEquals(1, result.size());
    }

    @Test
    public void testCreateTopicException() throws Exception {
        TopicDTO topicDTO = new TopicDTO();
        topicDTO.setTitle("Sample Topic");

        when(votingSystem.addTopic(eq("Sample Topic"))).thenReturn(remoteFunctionCall);
        when(remoteFunctionCall.send()).thenThrow(new RuntimeException("Exception"));

        Exception exception = assertThrows(Exception.class, () -> {
            topicsService.createTopic(topicDTO);
        });

        assertEquals("Exception", exception.getMessage());
    }
}