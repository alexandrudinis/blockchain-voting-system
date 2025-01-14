package org.blockchain.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.blockchain.dto.VoterDTO;
import org.blockchain.producers.VotingSystemProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * @author Alexandru Dinis
 */
@ApplicationScoped
public class VotersService {
    private static final Logger LOG = LoggerFactory.getLogger(VotersService.class);

    final VotingSystemProducer votingSystemProducer;

    public VotersService(VotingSystemProducer votingSystemProducer) {
        this.votingSystemProducer = votingSystemProducer;
    }

    public TransactionReceipt registerVoter(VoterDTO voterDTO) throws Exception {
        try {
            LOG.debug("Will register the voter with address {} to topic with id {}",
                    voterDTO.getPublicAddress(), voterDTO.getTopicId());
            return votingSystemProducer.getVotingSystem().
                    registerVoter(voterDTO.getTopicId(), voterDTO.getPublicAddress()).send();
        } catch (Exception ex) {
            LOG.error("Exception was thrown while register the voter with address {} to topic with id {}",
                    voterDTO.getPublicAddress(), voterDTO.getTopicId(), ex);
            throw ex;
        }
    }

}
