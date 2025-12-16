package com.github.sveldevorls.readtogether.author.entity;

import java.text.Normalizer;
import java.util.Locale;

public class AuthorMapper {

    public static Author toEntity(AuthorData authorData) {
        Author author = new Author();
        author.setAuthorData(authorData);
        author.setSlug(generateSlug(authorData.getAuthorName()));
        return author;
    }

    public static String generateSlug(String authorName) {
        return Normalizer
                .normalize(authorName, Normalizer.Form.NFD)
                .toLowerCase(Locale.ENGLISH)
                .replaceAll("[^A-Za-z0-9]", "-");
    }
}
