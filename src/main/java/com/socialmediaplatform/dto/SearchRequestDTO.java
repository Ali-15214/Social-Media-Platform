package com.socialmediaplatform.dto;

public class SearchRequestDTO {
    private String keyword; // Keyword to search in post content
    private int page ;   // Page number (default: 0)
    private int size ;  // Number of items per page (default: 10)
    private String sortBy; // Field to sort by (default: timestamp)
    private String sortDirection ; // Sort direction (default: desc)

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}
