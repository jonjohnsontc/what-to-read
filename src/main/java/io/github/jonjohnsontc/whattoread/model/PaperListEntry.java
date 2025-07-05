package io.github.jonjohnsontc.whattoread.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaperListEntry {
    private String id;
    private String title;
    private List<String> authors;
    private List<String> tags;
    private int year;
    private int rating;
    private boolean read;
}
