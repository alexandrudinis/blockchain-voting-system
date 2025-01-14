package org.blockchain.producers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import org.blockchain.dto.OptionCountDTO;
import org.blockchain.dto.StoredOptionDTO;
import org.blockchain.dto.StoredTopicDTO;
import org.blockchain.dto.VoteDTO;
import org.blockchain.dto.VoterDTO;
import org.blockchain.dto.VotingResultDTO;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.model.VotingSystem;
import org.web3j.protocol.Web3j;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class VotingSystemProducer {
    private static final Logger LOG = LoggerFactory.getLogger(VotingSystemProducer.class);
    @ConfigProperty(name = "ethereum.contract.address")
    String contractAddress;

    @ConfigProperty(name = "ethereum.client.public.key")
    String publicKey;

    volatile VotingSystem votingSystem;

    final Web3j web3j;

    public VotingSystemProducer(Web3j web3j) {
        this.web3j = web3j;
    }

    @Singleton
    public VotingSystem getVotingSystem() {
        if (votingSystem == null) {
           synchronized (this) {
               if (votingSystem == null) {
                   LOG.debug("First usage of the voting system generated class. Singleton used to generate it, will be only one shared");
                   ClientTransactionManager transactionManager = new ClientTransactionManager(web3j, publicKey);
                   votingSystem = VotingSystem.load(contractAddress, web3j, transactionManager, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
               }
           }
        }
        return votingSystem;
    }

    public VotingSystem getVotingSystemBasedOnPublicAddress(VoterDTO voterDTO) {
        LOG.debug("Will interact to the contract using the public key {}", voterDTO.getPublicAddress());
        ClientTransactionManager transactionManager = new ClientTransactionManager(web3j, voterDTO.getPublicAddress());

        return VotingSystem.load(contractAddress, web3j, transactionManager, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
    }

    public VotingSystem getVotingSystemBasedOnPublicAddress(VoteDTO voterDTO) {
        LOG.debug("Will interact to the contract using the public key {}", voterDTO.getVoterPublicAddress());
        ClientTransactionManager transactionManager = new ClientTransactionManager(web3j, voterDTO.getVoterPublicAddress());

        return VotingSystem.load(contractAddress, web3j, transactionManager, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
    }

    public List<StoredTopicDTO> getTopics() throws Exception {
        VotingSystem votingSystemNonStatic = getVotingSystem();

        BigInteger numberOfTopics = votingSystemNonStatic.getTopicCount().send();

        List<StoredTopicDTO> result = new ArrayList<>();

        for (int i = 0; i < numberOfTopics.intValue(); i++ ) {
            Tuple4<String, BigInteger, Boolean, Boolean> currentTopic = votingSystemNonStatic.getTopic(BigInteger.valueOf(i)).send();

            StoredTopicDTO storedTopic = new StoredTopicDTO(BigInteger.valueOf(i), currentTopic.component1(), currentTopic.component3(), currentTopic.component4());

            result.add(storedTopic);
        }

        return result;
    }

    public List<StoredOptionDTO> getOptions(BigInteger topicId) throws Exception {
        VotingSystem votingSystemNonStatic = getVotingSystem();

        List<StoredOptionDTO> result = new ArrayList<>();

        Tuple4<String, BigInteger, Boolean, Boolean> currentTopic = votingSystemNonStatic.getTopic(topicId).send();

        for (int i = 0; i < currentTopic.getValue2().intValue(); i++ ) {
            String option = votingSystemNonStatic.getOption(topicId, BigInteger.valueOf(i)).send();

            StoredOptionDTO storedOptionDTO = new StoredOptionDTO(BigInteger.valueOf(i), option);

            result.add(storedOptionDTO);
        }

        return result;
    }

    public List<VotingResultDTO> getAllVotingResults() throws Exception {
        VotingSystem votingSystemNonStatic = getVotingSystem();

        List<VotingResultDTO> result = new ArrayList<>();

        BigInteger numberOfTopics = votingSystemNonStatic.getTopicCount().send();

        for (int i = 0; i < numberOfTopics.intValue(); i++ ) {
            BigInteger topicId = BigInteger.valueOf(i);
            Tuple4<String, BigInteger, Boolean, Boolean> currentTopic = votingSystemNonStatic.getTopic(topicId).send();

            VotingResultDTO votingResultDTO = new VotingResultDTO();

            votingResultDTO.setTopicId(topicId.toString());
            votingResultDTO.setTitle(currentTopic.component1());
            votingResultDTO.setVotingStarted(currentTopic.component3());
            votingResultDTO.setVotingFinished(currentTopic.component4());

            List<OptionCountDTO> optionsCount = new ArrayList<>();

            for (int j = 0; j < currentTopic.getValue2().intValue(); j++ ) {
                BigInteger optionId = BigInteger.valueOf(j);
                String option = votingSystemNonStatic.getOption(topicId, optionId).send();

                OptionCountDTO optionCountDTO = new OptionCountDTO();
                optionCountDTO.setOption(option);
                optionCountDTO.setOptionId(optionId.toString());
                optionCountDTO.setNumberOfVotes(votingSystemNonStatic.getVoteCount(topicId, option).send().toString());
                optionsCount.add(optionCountDTO);
            }

            votingResultDTO.setVotedOptions(optionsCount);

            votingResultDTO.setTotalVotes(String.valueOf(optionsCount.stream().mapToInt(item-> Integer.parseInt(item.getNumberOfVotes())).sum()));

            result.add(votingResultDTO);
        }

        return result;
    }
}
