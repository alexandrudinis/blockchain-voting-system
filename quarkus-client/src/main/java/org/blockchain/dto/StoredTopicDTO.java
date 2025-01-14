package org.blockchain.dto;

import java.math.BigInteger;

public class StoredTopicDTO {
    private BigInteger index;
    private String title;
    private boolean votingStarted;
    private boolean votingFinished;

    public StoredTopicDTO() {

    }

    public StoredTopicDTO(BigInteger id, String title, boolean votingStarted, boolean votingFinished) {
        this.index = id;
        this.title = title;
        this.votingStarted = votingStarted;
        this.votingFinished = votingFinished;
    }

    public BigInteger getIndex() {
        return index;
    }

    public void setIndex(BigInteger index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVotingStarted() {
        return votingStarted;
    }

    public void setVotingStarted(boolean votingStarted) {
        this.votingStarted = votingStarted;
    }

    public boolean isVotingFinished() {
        return votingFinished;
    }

    public void setVotingFinished(boolean votingFinished) {
        this.votingFinished = votingFinished;
    }
}
