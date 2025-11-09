package io.github.jonjohnsontc.whattoread.service;

import io.github.jonjohnsontc.whattoread.model.PaperDetails;
import io.github.jonjohnsontc.whattoread.repository.PaperDetailsQ;
import io.github.jonjohnsontc.whattoread.repository.PaperJdbcRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import io.github.jonjohnsontc.whattoread.repository.PaperListQ;
import io.github.jonjohnsontc.whattoread.model.PaperListEntry;
import io.github.jonjohnsontc.whattoread.exception.PaperNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaperService {
    private final PaperListQ paperListQ;
    private final PaperDetailsQ paperDetailsQ;
    private final PaperJdbcRepository paperRepository;

    public PaperService(PaperListQ paperListQ, PaperDetailsQ paperDetailsQ, PaperJdbcRepository paperRepository) {
        this.paperListQ = paperListQ;
        this.paperDetailsQ = paperDetailsQ;
        this.paperRepository = paperRepository;
    }

    /**
     * Retrieves a paginated list of papers that match a specific search term.
     * This method is used to search papers by title, authors, or tags.
     *
     * @param term The search term to filter papers by
     * @return Page of PaperListEntry containing papers that match the search term
     */
    public Page<PaperListEntry> getPapersBySearchTerm(String term) {
        var pageable = PageRequest.of(0, 100); // Adjust page size as needed
        return paperListQ.getPapersBySearchTerm(term, pageable);
    }

    /**
     * Retrieves a paginated list of papers that match a specific tag.
     * This method is used to filter papers based on tags.
     *
     * @param term The tag to filter papers by
     * @return Page of PaperListEntry containing papers that match the specified tag
     */
    public Page<PaperListEntry> getPapersByTag(String term) {
        var pageable = PageRequest.of(0, 100); // Adjust page size as needed
        return paperListQ.getPaperListEntriesByTagsContains(pageable, term);
    }

    /**
     * Retrieves all papers from the paper list.
     * This method is used to fetch the complete list of papers.
     *
     * @return Page of PaperListEntry containing all papers
     */
    public Page<PaperListEntry> getAllPapers() {
        var pageable = PageRequest.of(0, 100); // Adjust page size as needed
        return paperListQ.getAllPapers(pageable);
    }

    /**
     * Creates a new paper entry in the db
     * This method is used to add a new paper with its details.
     *
     * @param title   The title of the paper
     * @param url     The URL of the paper
     * @param year    The year of publication
     * @param rating  The rating of the paper (0-5)
     * @param authors An array of authors of the paper
     * @param tags    An array of tags associated with the paper
     * @param read    Indicates whether the paper has been read
     */
    @Transactional
    public void createPaper(String title, String url, int year, Optional<Integer> rating, String[] authors,
                            String tags, boolean read, String notes) {

        var paperId = paperRepository.insertPaper(title, url, year, read);

        var authorIds = new ArrayList<UUID>();
        for (var i = 0; i < authors.length; i++) {
            authorIds.add(paperRepository.findOrInsertAuthor(authors[i]));
        }
        paperRepository.insertPaperAuthors(paperId, authorIds);

        if (!tags.isEmpty()) {
            var tagsArray = tags.split(",");
            var tagIds = paperRepository.findOrInsertTags(List.of(tagsArray));
            paperRepository.insertPaperTags(paperId, tagIds);
        }

        if (rating.isPresent()) {
            // we don't give the option to add review-text in the current create-paper screen
            paperRepository.insertPaperReview(paperId, rating.get(), null);

        }

        if (!notes.isEmpty()) {
            paperRepository.insertNotes(paperId, notes);
        }

    }

    /**
     * Retrieves a paper by its ID.
     *
     * @param id The UUID of the paper to retrieve
     * @return PaperListEntry containing the paper details
     * @throws PaperNotFoundException if the paper is not found
     */
    public PaperListEntry getPaperById(UUID id) {
        return paperListQ.findById(id.toString())
                .orElseThrow(() -> new PaperNotFoundException(id.toString()));
    }

    public PaperDetails getPaperDetailsById(UUID id) {
        return paperDetailsQ.findById(id.toString())
                .orElseThrow(() -> new PaperNotFoundException(id.toString()));
    }

    /**
     * Updates an existing paper with new details.
     *
     * @param id      The UUID of the paper to update
     * @param title   The updated title
     * @param url     The updated URL
     * @param year    The updated year
     * @param authors The updated list of authors
     * @param tags    The updated tags (comma-separated)
     * @param read    The updated read status
     * @param notes   The updated notes
     * @throws PaperNotFoundException if the paper is not found
     */
    @Transactional
    public void updatePaper(UUID id, String title, String url, int year, String[] authors,
                            String tags, boolean read, String notes) {
        // Verify the paper exists
        getPaperDetailsById(id);

        // Update basic paper fields
        paperRepository.updatePaper(id, title, url, year, read);

        // Update authors - delete old associations and insert new ones
        paperRepository.deletePaperAuthors(id);
        var authorIds = new ArrayList<UUID>();
        for (String author : authors) {
            authorIds.add(paperRepository.findOrInsertAuthor(author.trim()));
        }
        paperRepository.insertPaperAuthors(id, authorIds);

        // Update tags - delete old associations and insert new ones
        paperRepository.deletePaperTags(id);
        if (!tags.isEmpty()) {
            var tagsArray = tags.split(",");
            var trimmedTags = new ArrayList<String>();
            for (String tag : tagsArray) {
                trimmedTags.add(tag.trim());
            }
            var tagIds = paperRepository.findOrInsertTags(trimmedTags);
            paperRepository.insertPaperTags(id, tagIds);
        }

        // Update notes
        if (notes != null && !notes.isEmpty()) {
            paperRepository.updateNotes(id, notes);
        }
    }
}
