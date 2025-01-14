package org.blockchain.dto;

public class OptionCountDTO {
    private String optionId;
    private String option;
    private String numberOfVotes;

    public OptionCountDTO() {

    }

    public OptionCountDTO(String optionId, String option, String numberOfVotes) {
        this.optionId = optionId;
        this.option = option;
        this.numberOfVotes = numberOfVotes;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(String numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }
}
