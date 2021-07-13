package com.example.data.rating;




public class RatingCount {

    private Integer total;
    private Integer average;

    public RatingCount(Integer total, Integer average) {
        this.total = total;
        this.average = average;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getAverage() {
        return average;
    }

    public void setAverage(Integer average) {
        this.average = average;
    }
}
