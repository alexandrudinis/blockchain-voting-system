package org.blockchain.dto;

import java.math.BigInteger;

/**
 * @author Alexandru Dinis
 */
public class TopicDTO {

    public TopicDTO() {

    }

    private String title;

    public TopicDTO(BigInteger duration, String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
