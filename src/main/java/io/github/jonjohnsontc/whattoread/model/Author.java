package io.github.jonjohnsontc.whattoread.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(schema = "paper", name = "authors")
public class Author {
    @Id
    private UUID id;

    @Column(nullable = false, length = 255)
    private String name;

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
