package com.github.sveldevorls.readtogether.book.entity;

import java.time.LocalDate;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class BookData {

    @Size(max = 255, message = "Title must be at most 255 characters long")
    private String title;

    @Pattern(regexp = "\\d+", message = "ISBN must contain only numbers")
    @Pattern(regexp = "^(.{10}|.{13})$", message = "ISBN must be 10 or 13 numbers long")
    private String isbn;

    private String bookDescription;

    @Size(max = 100, message = "Publisher name must be at most 255 characters long")
    private String publisherName;

    @PastOrPresent(message = "Publication date cannot be in the future")
    private LocalDate publishedDate;

    @Size(max = 500, message = "Cover URL must be at most 500 characters long")
    @URL(message = "Link to the books's cover image must be a valid URL")
    private String coverUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

}
