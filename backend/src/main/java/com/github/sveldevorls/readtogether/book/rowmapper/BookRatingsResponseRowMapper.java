package com.github.sveldevorls.readtogether.book.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.github.sveldevorls.readtogether.book.dto.BookRatingsResponse;


public class BookRatingsResponseRowMapper implements RowMapper<BookRatingsResponse> {

    public BookRatingsResponse mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        Map<Integer, Integer> distributions = new HashMap<>();
        distributions.put(1, rs.getInt("ones"));
        distributions.put(2, rs.getInt("twos"));
        distributions.put(3, rs.getInt("threes"));
        distributions.put(4, rs.getInt("fours"));
        distributions.put(5, rs.getInt("fives"));
    
        return new BookRatingsResponse(
            rs.getFloat("average"),
            rs.getInt("total"),
            distributions
        );
    }

}
