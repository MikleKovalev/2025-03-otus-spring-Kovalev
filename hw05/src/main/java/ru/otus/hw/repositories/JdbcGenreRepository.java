package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class JdbcGenreRepository implements GenreRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Genre> findAll() {
        return jdbc.query("select id, name from genres", new GenreRowMapper());
    }

    @Override
    public List<Genre> findAllByIds(Set<Long> ids) {
        return ids.stream()
                .map(id ->
                        jdbc.queryForObject(
                                "select id, name from genres where id = :id",
                                Map.of("id", id),
                                new GenreRowMapper()))
                .toList();
    }

    private static class GenreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Genre(id, name);
        }
    }
}
