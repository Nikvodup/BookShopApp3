package com.example.data;



public class SearchAuthorDto {

    private String example;

    public SearchAuthorDto(String example) {
        this.example = example;
    }

    public SearchAuthorDto(){}

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }
}