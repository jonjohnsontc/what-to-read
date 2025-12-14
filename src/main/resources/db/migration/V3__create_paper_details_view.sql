-- Creating a view to list all papers with all their details
CREATE OR REPLACE VIEW paper.paper_details AS
SELECT
    cast(p.id AS text) AS id,
    p.name AS title,
    ARRAY_AGG(DISTINCT a.name) AS authors,
    ARRAY_AGG(DISTINCT t.name) AS tags,
    p.url AS url,
    p.year AS year,
    r.rating AS rating,
    r.review AS review,
    p.read AS read,
    n.content as notes
FROM paper.papers p
         LEFT JOIN paper.paper_authors pa ON p.id = pa.paper_id
         LEFT JOIN paper.authors a ON pa.author_id = a.id
         LEFT JOIN paper.paper_tags pt ON p.id = pt.paper_id
         LEFT JOIN paper.reviews r ON p.id = r.paper_id
         LEFT JOIN paper.tags t ON pt.tag_id = t.id
         LEFT JOIN paper.notes n ON p.id = n.paper_id
GROUP BY p.id, p.name, p.year, r.rating, r.review, p.url, p.read, notes