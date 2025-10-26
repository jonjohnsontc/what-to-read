package io.github.jonjohnsontc.whattoread.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public class PaperJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public PaperJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UUID insertPaper(String title, String url, int year) {
        UUID paperId = UUID.randomUUID();
        jdbcTemplate.update(
            "INSERT INTO paper.papers (id, name, url, year) VALUES (?, ?, ?, ?)",
            paperId, title, url, year
        );
        return paperId;
    }

    public UUID findOrInsertAuthor(String name) {
        jdbcTemplate.update(
               """
                   
                   """
        )
    }

}
