package com.github.sveldevorls.readtogether.submission.entity;

import com.github.sveldevorls.readtogether.author.entity.AuthorData;

public class AuthorSubmission extends BaseSubmission {

    // Submission data
    private int authorId;

    // Author data
    private AuthorData authorData;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public AuthorData getAuthorData() {
        return authorData;
    }

    public void setAuthorData(AuthorData authorData) {
        this.authorData = authorData;
    }

}
