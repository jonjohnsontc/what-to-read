package io.github.jonjohnsontc.whattoread.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Convert;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;

import java.util.List;
import io.github.jonjohnsontc.whattoread.converter.StringListConverter;

/**
 * Represents a paper in the home page of the UI.
 * It's also the entity that represents a paper when a user adds a new paper.
 * I don't know if this is the best way to do it, a user may want to add more attributes
 * to the paper, but these are concerns for later.
 */

@Builder
@NoArgsConstructor
@Entity
@Getter
@Table(name = "paper_list", schema = "paper")
public class PaperListEntry {
    @Id
    private String id;
    private String title;
    @Convert(converter = StringListConverter.class)
    private List<String> authors;
    @Convert(converter = StringListConverter.class)
    private List<String> tags;
    private String url;
    private int year;
    private Integer rating;
    private boolean read;
    private String notes;

    public PaperListEntry(String id, String title, List<String> authors, List<String> tags, String url, int year, Integer rating, boolean read, String notes) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.tags = tags;
        this.url = url;
        this.year = year;
        this.rating = rating;
        this.read = read;
        this.notes = notes;
    }
}
