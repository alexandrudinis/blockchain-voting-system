package org.blockchain.dto;

import java.math.BigInteger;

public class StoredOptionDTO {
    private BigInteger index;
    private String title;

    public StoredOptionDTO() {

    }

    public StoredOptionDTO(BigInteger index, String title) {
        this.index = index;
        this.title = title;
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
}
