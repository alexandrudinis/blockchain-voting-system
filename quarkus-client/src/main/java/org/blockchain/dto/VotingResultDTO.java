package org.blockchain.dto;

import java.util.List;

public class VotingResultDTO {
    private String topicId;
    private String title;
    private boolean votingStarted;
    private boolean votingFinished;
    private String totalVotes;
    private List<OptionCountDTO> votedOptions;

    public VotingResultDTO() {
        super();
    }

    public VotingResultDTO(String topicId, String title, boolean votingStarted, boolean votingFinished, String totalVotes, List<OptionCountDTO> votedOptions) {
        this.topicId = topicId;
        this.title = title;
        this.votingStarted = votingStarted;
        this.votingFinished = votingFinished;
        this.totalVotes = totalVotes;
        this.votedOptions = votedOptions;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(String totalVotes) {
        this.totalVotes = totalVotes;
    }

    public List<OptionCountDTO> getVotedOptions() {
        return votedOptions;
    }

    public void setVotedOptions(List<OptionCountDTO> votedOptions) {
        this.votedOptions = votedOptions;
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
