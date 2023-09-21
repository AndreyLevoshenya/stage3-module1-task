package com.mjc.school.repository.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class NewsModel implements Comparable<NewsModel> {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;
    private Long authorId;

    public NewsModel(Long id, String title, String content, LocalDateTime createdDate, LocalDateTime lastUpdatedDate, Long authorId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.authorId = authorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedTime(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ";title=" + title +
                ";content=" + content +
                ";createdDate=" + createdDate +
                ";lastUpdatedTime=" + lastUpdatedDate +
                ";authorId=" + authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsModel news = (NewsModel) o;
        return id.equals(news.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(NewsModel o) {
        return id.compareTo(o.id);
    }
}
