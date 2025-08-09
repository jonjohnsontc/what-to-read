package io.github.jonjohnsontc.whattoread.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tags", schema = "paper")
@Getter
@NoArgsConstructor
public class Tag {
    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;
}