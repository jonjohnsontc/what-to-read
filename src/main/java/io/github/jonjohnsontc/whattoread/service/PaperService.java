package io.github.jonjohnsontc.whattoread.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import io.github.jonjohnsontc.whattoread.repository.PaperListQ;
import io.github.jonjohnsontc.whattoread.model.PaperListEntry;

import java.util.List;

@Service
public class PaperService {
    private final PaperListQ paperListQ;

    public PaperService(PaperListQ paperListQ) {
        this.paperListQ = paperListQ;
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
     * Creates a new paper entry in the paper list.
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
    public void createPaper(String title, String url, int year, int rating, String[] authors, String[] tags, boolean read) {
        var paperListEntry = PaperListEntry.builder()
                .id(java.util.UUID.randomUUID().toString())
                .title(title)
                .url(url)
                .rating(rating)
                .read(read)
                .authors(List.of(authors))
                .tags(List.of(tags))
                .year(year)
                .build();
        paperListQ.save(paperListEntry);
    }
}
