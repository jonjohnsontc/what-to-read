package io.github.jonjohnsontc.whattoread.repository;

import io.github.jonjohnsontc.whattoread.model.PaperListEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PaperListQ extends CrudRepository<PaperListEntry, String> {
    /**
     * Retrieves all papers from the paper list in a paginated format.
     * This method is used to fetch the complete list of papers with pagination support.
     *
     * @return A Page containing all papers
     */
    @Query("SELECT p FROM PaperListEntry p")
    Page<PaperListEntry> getAllPapers(Pageable pageable);


    /**
     * Retrieves a paginated list of paper entries that match a specific search term.
     * This method is used to search papers by title, authors, or tags.
     *
     * @param term     The search term to filter papers by
     * @param pageable Pagination information
     * @return Page of PaperListEntry containing papers that match the search term
     */
    @Query(value = """
                SELECT * FROM paper.paper_list p
                WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :term, '%'))
                OR p.authors::text LIKE LOWER(CONCAT('%', :term, '%'))
                OR p.tags::text LIKE LOWER(CONCAT('%"', :term, '"%'))
            """,
            nativeQuery = true)
    Page<PaperListEntry> getPapersBySearchTerm(
            @Param(value = "term") String term,
            Pageable pageable
    );

    /**
     * Retrieves a paginated list of paper entries that match a specific tag.
     * This method is used to filter papers based on tags.
     *
     * @param pageable Pagination information
     * @param tag      The tag to filter papers by
     * @return List of PaperListEntry containing papers that match the specified tag
     */
    Page<PaperListEntry> getPaperListEntriesByTagsContains(Pageable pageable, String tag);
}
