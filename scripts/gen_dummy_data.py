# NOTE: I'm not currently using fake data in dev mode, so this script may have
# diverged from the actual data model
import sys
import uuid
import random
from faker import Faker
from datetime import datetime

fake = Faker()

def gen_authors(n):
    authors = []
    names = set()
    while len(names) < n:
        names.add(fake.name())
    for name in names:
        authors.append((uuid.uuid4(), name))
    return authors

def gen_papers(n):
    papers = []
    current_year = datetime.now().year
    for _ in range(n):
        paper_id = str(uuid.uuid4())
        name = fake.sentence(nb_words=4)
        url = fake.url()
        year = random.randint(2000, current_year)
        read = random.choice([True, False])
        now = datetime.now().isoformat(sep=' ', timespec='seconds')
        papers.append((paper_id, name, url, year, read, now, now))
    return papers

def gen_tags(n):
    potential_tags = set()
    while len(potential_tags) < n:
        potential_tags.add(fake.word())
    return [(str(uuid.uuid4()), potential_tags.pop()) for _ in range(n)]

def gen_paper_tags(papers, tags, min_tags=1, max_tags=3):
    links = []
    for paper_id, *_ in papers:
        tag_sample = random.sample(tags, random.randint(min_tags, min(max_tags, len(tags))))
        for tag_id, _ in tag_sample:
            links.append((paper_id, tag_id))
    return links

def gen_paper_authors(papers, authors):
    links = []
    for paper_id, *_ in papers:
        author_id = random.choice(authors)[0]
        links.append((paper_id, author_id))
    return links

def gen_reviews(papers, n):
    reviews = []
    paper_ids = [(paper[0]) for paper in papers]
    random.shuffle(paper_ids)
    for _ in range(n // 2): # Generate half as many reviews as papers
        paper_id = paper_ids.pop()
        rating = random.randint(1, 5)
        review = fake.text(max_nb_chars=100)
        now = datetime.now().isoformat(sep=' ', timespec='seconds')
        reviews.append((paper_id, rating, review, now, now))
    return reviews

def main(n):
    authors = gen_authors(n)
    papers = gen_papers(n)
    tags = gen_tags(n)
    paper_tags = gen_paper_tags(papers, tags)
    paper_authors = gen_paper_authors(papers, authors)
    reviews = gen_reviews(papers, n)

    print("-- Authors")
    for a in authors:
        print(f"INSERT INTO paper.authors (id, name) VALUES ('{a[0]}', '{a[1].replace('\'', '\'\'')}');")
    print("-- Papers")
    for p in papers:
        print(f"INSERT INTO paper.papers (id, name, url, year, read, created_at, updated_at) VALUES ('{p[0]}', '{p[1].replace('\'', '\'\'')}', '{p[2]}', {p[3]}, {str(p[4]).lower()}, '{p[5]}', '{p[6]}');")
    print("-- Tags")
    for t in tags:
        print(f"INSERT INTO paper.tags (id, name) VALUES ('{t[0]}', '{t[1].replace('\'', '\'\'')}');")
    print("-- Paper Tags")
    for pt in paper_tags:
        print(f"INSERT INTO paper.paper_tags (paper_id, tag_id) VALUES ('{pt[0]}', '{pt[1]}');")
    print("-- Paper Authors")
    for pa in paper_authors:
        print(f"INSERT INTO paper.paper_authors (paper_id, author_id) VALUES ('{pa[0]}', '{pa[1]}');")
    print("-- Reviews")
    for r in reviews:
        print(f"INSERT INTO paper.reviews (paper_id, rating, review, created_at, updated_at) VALUES ('{r[0]}', {r[1]}, '{r[2].replace('\'', '\'\'')}', '{r[3]}', '{r[4]}');")

if __name__ == "__main__":
    if len(sys.argv) != 2 or not sys.argv[1].isdigit():
        print("Usage: python gen_dummy_data.py <num_rows>")
        sys.exit(1)
    main(int(sys.argv[1]))