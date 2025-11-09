package io.github.jonjohnsontc.whattoread.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class PaperJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public PaperJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UUID insertPaper(String title, String url, int year, boolean read) {
        UUID paperId = UUID.randomUUID();
        jdbcTemplate.update(
                "INSERT INTO paper.papers (id, name, url, year, read) VALUES (?, ?, ?, ?, ?)",
                paperId, title, url, year, read);
        return paperId;
    }

    public UUID findOrInsertAuthor(String name) {
        String insert = """
                INSERT INTO paper.authors (id, name)
                VALUES (gen_random_uuid(), ?)
                ON CONFLICT (name) DO NOTHING
                RETURNING id
                """;
        try {
            return jdbcTemplate.queryForObject(insert, UUID.class, name);
        } catch (EmptyResultDataAccessException e) {
            String select = "SELECT id FROM paper.authors WHERE name = ?";
            return jdbcTemplate.queryForObject(select, UUID.class, name);
        }
    }

    public void insertPaperAuthors(UUID paperId, List<UUID> authors) {
        for (var i = 0; i < authors.size(); i++) {
            jdbcTemplate.update(
                    "INSERT INTO paper.paper_authors (paper_id, author_id) VALUES (?, ?)",
                    paperId, authors.get(i));
        }
    }

    public List<UUID> findOrInsertTags(List<String> tags) {
        var results = new ArrayList<UUID>();
        for (var i = 0; i < tags.size(); i++) {
            String insert = """
                        INSERT INTO paper.tags (id, name)
                        VALUES (gen_random_uuid(), ?)
                        ON CONFLICT (name) DO NOTHING
                        RETURNING id
                    """;
            UUID result;
            try {
                result = jdbcTemplate.queryForObject(insert, UUID.class, tags.get(i));
            } catch (EmptyResultDataAccessException e) {
                result = jdbcTemplate.queryForObject(
                        "SELECT id FROM paper.tags WHERE name = ?",
                        UUID.class,
                        tags.get(i));
            }
            results.add(result);
        }
        return results;
    }

    public void insertPaperTags(UUID paperId, List<UUID> tags) {
        for (var i = 0; i < tags.size(); i++) {
            jdbcTemplate.update(
                    "INSERT INTO paper.paper_tags (paper_id, tag_id) VALUES (?,  ?)", paperId, tags.get(i));
        }
    }

    public void insertPaperReview(UUID paperId, int rating, String reviewText) {
        jdbcTemplate.update(
                "INSERT INTO paper.reviews (paper_id, rating, review) VALUES (?, ?, ?)",
                paperId, rating, reviewText);

    }

    public void insertNotes(UUID paperId, String notes) {
        jdbcTemplate.update(
                "INSERT INTO paper.notes (id, paper_id, content) VALUES (gen_random_uuid(), ?, ?)",
                paperId, notes
        );
    }

    public void updatePaper(UUID paperId, String title, String url, int year, boolean read) {
        jdbcTemplate.update(
                "UPDATE paper.papers SET name = ?, url = ?, year = ?, read = ? WHERE id = ?",
                title, url, year, read, paperId);
    }

    public void deletePaperAuthors(UUID paperId) {
        jdbcTemplate.update(
                "DELETE FROM paper.paper_authors WHERE paper_id = ?",
                paperId);
    }

    public void deletePaperTags(UUID paperId) {
        jdbcTemplate.update(
                "DELETE FROM paper.paper_tags WHERE paper_id = ?",
                paperId);
    }

    public void updateNotes(UUID paperId, String notes) {
        jdbcTemplate.update(
                "UPDATE paper.notes SET content = ? WHERE paper_id = ?",
                notes, paperId);
    }
}
