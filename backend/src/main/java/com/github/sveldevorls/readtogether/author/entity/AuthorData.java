package com.github.sveldevorls.readtogether.author.entity;

import java.time.LocalDate;

import org.hibernate.validator.constraints.URL;

import com.github.sveldevorls.readtogether.author.validation.ValidDobAndDod;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@ValidDobAndDod
public class AuthorData {

    @NotBlank(message = "Author name is required")
    @Size(max = 255, message= "Author name must be at most 255 characters long")
    private String authorName;

    private LocalDate dateOfBirth;

    private LocalDate dateOfDeath;

    @URL(message = "Link to the author's image must be a valid URL")
    private String authorImageUrl;

    @Size(max = 500, message= "Author biography must be at most 500 characters long")
    private String biography;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(LocalDate dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

}
