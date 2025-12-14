CREATE SCHEMA IF NOT EXISTS paper;

CREATE TABLE paper.authors
(
    id   UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT authors_name_unique UNIQUE (name)
);

CREATE TABLE paper.papers
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    url        TEXT         NOT NULL,
    year       INTEGER      NOT NULL,
    read       BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE paper.tags
(
    id   UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE paper.paper_tags
(
    paper_id UUID NOT NULL,
    tag_id   UUID NOT NULL,
    PRIMARY KEY (paper_id, tag_id),
    FOREIGN KEY (paper_id) REFERENCES paper.papers (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES paper.tags (id) ON DELETE CASCADE
);

CREATE TABLE paper.paper_authors
(
    paper_id  UUID NOT NULL,
    author_id UUID NOT NULL,
    PRIMARY KEY (paper_id, author_id),
    FOREIGN KEY (paper_id) REFERENCES paper.papers (id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES paper.authors (id) ON DELETE CASCADE
);

CREATE TABLE paper.reviews
(
    id         SERIAL PRIMARY KEY,
    paper_id   UUID      NOT NULL,
    rating     INTEGER,
    review     TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (paper_id) REFERENCES paper.papers (id) ON DELETE CASCADE
);

CREATE TABLE paper.notes
(
    id         UUID PRIMARY KEY,
    paper_id   UUID NOT NULL,
    content    TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (paper_id) REFERENCES paper.papers (id) ON DELETE CASCADE
);