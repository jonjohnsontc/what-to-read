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

    public PaperListEntry(String id, String title, List<String> authors, List<String> tags, String url, int year, Integer rating, boolean read) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.tags = tags;
        this.url = url;
        this.year = year;
        this.rating = rating;
        this.read = read;
    }
}
