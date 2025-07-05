package io.github.jonjohnsontc.whattoread.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Used in JTE templates to render a list of papers.
 */
@Component
@Data
public class PaperList {
    private ArrayList<PaperListEntry> papers;

    // TODO: Remove this method when using a real data source
    public void generateDummyData() {
        papers = new ArrayList<>();
        papers.add(new PaperListEntry("1", "Paper 1", List.of("Author A", "Author B"), List.of("Tag1", "Tag2"), 2023, 5, false));
        papers.add(new PaperListEntry("2", "Paper 2", List.of("Author C"), List.of("Tag3"), 2022, 4, true));
        papers.add(new PaperListEntry("3", "Paper 3", List.of("Author D", "Author E"), List.of("Tag1"), 2021, 3, false));
    }
}
