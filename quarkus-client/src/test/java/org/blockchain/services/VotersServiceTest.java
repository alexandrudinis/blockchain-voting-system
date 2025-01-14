package org.blockchain.services;

import org.blockchain.dto.VoterDTO;
import org.blockchain.producers.VotingSystemProducer;
import org.blockchain.service.VotersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.web3j.model.VotingSystem;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.RemoteFunctionCall;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class VotersServiceTest {

    @Mock
    VotingSystemProducer votingSystemProducer;

    @Mock
    VotingSystem votingSystem;

    @Mock
    RemoteFunctionCall<TransactionReceipt> remoteFunctionCall;

    @InjectMocks
    VotersService votersService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(votingSystemProducer.getVotingSystem()).thenReturn(votingSystem);
    }

    @Test
    public void testRegisterVoter() throws Exception {
        VoterDTO voterDTO = new VoterDTO();
        voterDTO.setPublicAddress("0x123");
        voterDTO.setTopicId(BigInteger.ONE);

        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash("sampleHash");

        when(votingSystem.registerVoter(eq(BigInteger.ONE), eq("0x123"))).thenReturn(remoteFunctionCall);
        when(remoteFunctionCall.send()).thenReturn(transactionReceipt);

        TransactionReceipt result = votersService.registerVoter(voterDTO);
        assertEquals("sampleHash", result.getTransactionHash());
    }

    @Test
    public void testRegisterVoterException() throws Exception {
        VoterDTO voterDTO = new VoterDTO();
        voterDTO.setPublicAddress("0x123");
        voterDTO.setTopicId(BigInteger.ONE);

        when(votingSystem.registerVoter(eq(BigInteger.ONE), eq("0x123"))).thenReturn(remoteFunctionCall);
        when(remoteFunctionCall.send()).thenThrow(new RuntimeException("Exception"));

        Exception exception = assertThrows(Exception.class, () -> {
            votersService.registerVoter(voterDTO);
        });

        assertEquals("Exception", exception.getMessage());
    }
}