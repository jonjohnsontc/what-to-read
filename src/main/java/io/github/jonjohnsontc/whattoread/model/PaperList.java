package io.github.jonjohnsontc.whattoread.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Used in JTE templates to render a paginated list of papers.
 */
@Component
@Data
@NoArgsConstructor
public class PaperList {
    private List<PaperListEntry> papers;
    private int currentPage;
    private int totalPages;
    private long totalItems;

}
