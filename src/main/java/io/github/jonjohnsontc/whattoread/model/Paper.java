package io.github.jonjohnsontc.whattoread.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "papers", schema = "paper")
public class Paper {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private boolean read;

    @ManyToMany
    @JoinTable(
        schema = "paper",
        name = "paper_tags",
        joinColumns = @JoinColumn(name = "paper_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @ManyToMany
    @JoinTable(
        schema = "paper",
        name = "paper_authors",
        joinColumns = @JoinColumn(name = "paper_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    @Column(name = "notes")
    private String notes;

}
