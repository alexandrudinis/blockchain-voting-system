package org.blockchain.dto;

import java.math.BigInteger;

/**
 * @author Alexandru Dinis
 */
public class VoterDTO {
    private BigInteger topicId;
    private String publicAddress;

    public VoterDTO() {

    }

    public VoterDTO(BigInteger topicId, String publicAddress) {
        this.topicId = topicId;
        this.publicAddress = publicAddress;
    }

    public BigInteger getTopicId() {
        return topicId;
    }

    public void setTopicId(BigInteger topicId) {
        this.topicId = topicId;
    }

    public String getPublicAddress() {
        return publicAddress;
    }

    public void setPublicAddress(String publicAddress) {
        this.publicAddress = publicAddress;
    }
}
