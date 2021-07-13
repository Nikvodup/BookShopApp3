package com.example.data;

//import lombok.Data;

//@Data
public class BookReviewLikeValue {
    private Short value;
    private Integer reviewid;

    public BookReviewLikeValue(Short value, Integer reviewid) {
        this.value = value;
        this.reviewid = reviewid;
    }

    public Short getValue() {
        return value;
    }

    public void setValue(Short value) {
        this.value = value;
    }

    public Integer getReviewid() {
        return reviewid;
    }

    public void setReviewid(Integer reviewid) {
        this.reviewid = reviewid;
    }
}
