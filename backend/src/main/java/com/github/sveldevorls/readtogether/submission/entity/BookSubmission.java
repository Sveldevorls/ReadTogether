package com.github.sveldevorls.readtogether.submission.entity;

import com.github.sveldevorls.readtogether.book.entity.BookData;

public class BookSubmission extends BaseSubmission {

    // Submission data
    private int bookId;

    // Book data
    private BookData bookData;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public BookData getBookData() {
        return bookData;
    }

    public void setBookData(BookData bookData) {
        this.bookData = bookData;
    }

}
