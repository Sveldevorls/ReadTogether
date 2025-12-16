package com.github.sveldevorls.readtogether.author.entity;

import java.time.LocalDate;

public class Author {

    private int id;
    private String slug;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private boolean isPending;

    private AuthorData authorData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean isPending) {
        this.isPending = isPending;
    }

    public AuthorData getAuthorData() {
        return authorData;
    }

    public void setAuthorData(AuthorData authorData) {
        this.authorData = authorData;
    }

}
