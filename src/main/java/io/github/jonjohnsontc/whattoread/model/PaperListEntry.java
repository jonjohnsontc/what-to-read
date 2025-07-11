package io.github.jonjohnsontc.whattoread.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Convert;
import lombok.NoArgsConstructor;
import lombok.Getter;

import java.util.List;
import io.github.jonjohnsontc.whattoread.converter.StringListConverter;

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
}
