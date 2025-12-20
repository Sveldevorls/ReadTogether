package com.github.sveldevorls.readtogether.author.mapper;

import java.text.Normalizer;
import java.util.Locale;

import com.github.sveldevorls.readtogether.author.dto.AuthorResponse;
import com.github.sveldevorls.readtogether.author.entity.Author;
import com.github.sveldevorls.readtogether.author.entity.AuthorData;

public class AuthorMapper {

    public static Author toEntity(AuthorData authorData) {
        Author author = new Author();
        author.setAuthorData(authorData);
        author.setSlug(generateSlug(authorData.getAuthorName()));
        return author;
    }

    public static AuthorResponse toResponse(Author author) {
        return new AuthorResponse(
            author.getId(),
            author.getSlug(),
            author.getCreatedAt(),
            author.getUpdatedAt(),
            author.getAuthorData()
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
