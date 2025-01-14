package org.blockchain.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.blockchain.dto.OptionDTO;
import org.blockchain.dto.StoredOptionDTO;
import org.blockchain.dto.StoredTopicDTO;
import org.blockchain.dto.TopicDTO;
import org.blockchain.dto.VoteDTO;
import org.blockchain.dto.VotingResultDTO;
import org.blockchain.producers.VotingSystemProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Alexandru Dinis
 */
@ApplicationScoped
public class TopicsService {

    private static final Logger LOG = LoggerFactory.getLogger(TopicsService.class);

    final VotingSystemProducer votingSystemProducer;

    public TopicsService(VotingSystemProducer votingSystemProducer) {
        this.votingSystemProducer = votingSystemProducer;
    }

    public TransactionReceipt createTopic(TopicDTO topicDTO) throws Exception {
        try {
            LOG.debug("Will create the topic out of the following dto: {}", topicDTO);
            return votingSystemProducer.getVotingSystem().addTopic(topicDTO.getTitle()).send();
        } catch (Exception ex) {
            LOG.error("Exception was thrown while create the topic out of dto {}", topicDTO, ex);
            throw ex;
        }
    }

    public TransactionReceipt enableStartVoting(BigInteger topicId) throws Exception {
        try {
            LOG.debug("Will enable the start of voting for the topic {}", topicId);
            return votingSystemProducer.getVotingSystem().startVoting(topicId).send();
        } catch (Exception ex) {
            LOG.error("Exception was thrown while enabling start voting for topic {}", topicId, ex);
            throw ex;
        }
    }

    public TransactionReceipt endVoting(BigInteger topicId) throws Exception {
        try {
            LOG.debug("Will end the voting for the topic {}", topicId);
            return votingSystemProducer.getVotingSystem().finishVoting(topicId).send();
        } catch (Exception ex) {
            LOG.error("Exception was thrown while ending voting for topic {}", topicId, ex);
            throw ex;
        }
    }

    public TransactionReceipt addOption(OptionDTO optionDTO) throws Exception {
        try {
            LOG.debug("Will add option {}", optionDTO);
            LOG.debug("Will add option {}", optionDTO.getTitle());
            LOG.debug("Will add option {}", optionDTO.getTopicIndex());
            return votingSystemProducer.getVotingSystem().addOption(optionDTO.getTopicIndex(), optionDTO.getTitle()).sendAsync().get();
        } catch (Exception ex) {
            LOG.error("Exception was thrown while adding option {}", optionDTO, ex);
            throw ex;
        }
    }

    public TransactionReceipt vote(VoteDTO voteDTO) throws Exception {
        try {
            return votingSystemProducer.getVotingSystemBasedOnPublicAddress(voteDTO).vote(voteDTO.getTopicIndex(), voteDTO.getOptionIndex()).sendAsync().get();
        } catch (Exception ex) {
            LOG.error("Exception was thrown while adding option {}", voteDTO, ex);
            throw ex;
        }
    }

    public List<StoredTopicDTO> getTopics() throws  Exception {
        try {
            LOG.debug("Will get all topics");
            return votingSystemProducer.getTopics();
        } catch (Exception ex) {
            LOG.error("Exception was thrown while getting all topics", ex);
            throw ex;
        }
    }

    public List<StoredOptionDTO> getOptions(BigInteger topicId) throws  Exception {
        try {
            LOG.debug("Will get all topics");
            return votingSystemProducer.getOptions(topicId);
        } catch (Exception ex) {
            LOG.error("Exception was thrown while getting all topics", ex);
            throw ex;
        }
    }

    public List<VotingResultDTO> getAllVotingResults() throws Exception {
        try {
            LOG.debug("Will get all voting results");
            return  votingSystemProducer.getAllVotingResults();
        } catch (Exception ex) {
            LOG.error("Exception was thrown while getting all voting results", ex);
            throw ex;
        }
    }
}
