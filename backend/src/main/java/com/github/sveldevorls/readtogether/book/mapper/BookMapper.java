package com.github.sveldevorls.readtogether.book.mapper;

import java.text.Normalizer;
import java.util.Locale;

import com.github.sveldevorls.readtogether.book.dto.BookResponse;
import com.github.sveldevorls.readtogether.book.entity.Book;
import com.github.sveldevorls.readtogether.book.entity.BookData;

public class BookMapper {
    
    public static Book toEntity(BookData bookData) {
        Book book = new Book();
        book.setBookData(bookData);
        book.setSlug(generateSlug(bookData.getTitle()));
        return book;
    }

    public static BookResponse toResponse(Book book) {
        return new BookResponse(
            book.getId(),
            book.getSlug(),
            book.getCreatedAt(),
            book.getUpdatedAt(),
            book.getReviewStatus(),
            book.getBookData()
        );
    }

    // util
    public static String generateSlug(String authorName) {
        return Normalizer
                .normalize(authorName, Normalizer.Form.NFD)
                .toLowerCase(Locale.ENGLISH)
                .replaceAll("[^A-Za-z0-9]", "-");
    }
}
