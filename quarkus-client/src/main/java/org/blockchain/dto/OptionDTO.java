package org.blockchain.dto;

import java.math.BigInteger;

/**
 * @author Alexandru Dinis
 */
public class OptionDTO {
    private BigInteger topicIndex;
    private String title;

    public OptionDTO() {
    }

    public OptionDTO(BigInteger topicIndex, String name) {
        this.topicIndex = topicIndex;
        this.title = name;
    }

    public BigInteger getTopicIndex() {
        return topicIndex;
    }

    public void setTopicIndex(BigInteger topicIndex) {
        this.topicIndex = topicIndex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
