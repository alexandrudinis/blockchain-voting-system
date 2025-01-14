package org.blockchain.dto;

import java.math.BigInteger;

public class VoteDTO {
    private BigInteger topicIndex;
    private BigInteger optionIndex;
    private String voterPublicAddress;

    public VoteDTO() {

    }

    public VoteDTO(BigInteger topicIndex, BigInteger optionIndex, String voterPublicAddress) {
        this.topicIndex = topicIndex;
        this.optionIndex = optionIndex;
        this.voterPublicAddress = voterPublicAddress;
    }

    public BigInteger getTopicIndex() {
        return topicIndex;
    }

    public void setTopicIndex(BigInteger topicIndex) {
        this.topicIndex = topicIndex;
    }

    public BigInteger getOptionIndex() {
        return optionIndex;
    }

    public void setOptionIndex(BigInteger optionIndex) {
        this.optionIndex = optionIndex;
    }

    public String getVoterPublicAddress() {
        return voterPublicAddress;
    }

    public void setVoterPublicAddress(String voterPublicAddress) {
        this.voterPublicAddress = voterPublicAddress;
    }
}
