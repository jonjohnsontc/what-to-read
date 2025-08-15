package io.github.jonjohnsontc.whattoread.model;

import java.util.List;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import io.github.jonjohnsontc.whattoread.converter.StringListConverter;

@Entity
@Table(name = "paper_details", schema = "paper")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaperDetails {
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
    private String review;
    private boolean read;
    private String notes;
}
