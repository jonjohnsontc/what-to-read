# Paper A Day Design

I'd like the interface to offer me the following, to be used on a computer or mobile device:

- Create an entry
- Update an entry
    - Review a Paper
- "Mark as Read" functionality from the main page
- User management? (e.g., login) (no)

I will need to use a database, I think I'm just going to use SQLite. Although, if I can somehow get free Postgres going, maybe I should. It looks like Neon offers free postgres storage up to 0.5 GB. I'm not sure what the compute limits mean, but it should be easy to understand with a LLM (lol)

Actually, it seems pretty easy to understand:

- Autoscaling to 2 CU (2vCPU, 8 GB RAM)
- 0.5 GB-month storage

- I think that I can run it on locally via the computer I'm developing on
- Eventually, I can worry about hosting

I think that it would be nice to use postgres, because we use it at work, and that I am partially through that Postgres course in educative.

### Data Schema

papers
id: uuid
name: str
url: str  
read: bool
created_at: timestamp
updated_at: timestamp

reviews:
paper_id: uuid
rating: int nullable
review: text
created_at: timestamp
updated_at: timestamp

paper_authors:
paper_id: uuid
author_id: uuid

authors:
id: uuid
name: str

#### Data Model

I am going to query the data directly from the database, likely extending a `CrudRepository` via Spring Data JPA. I'm doing this to mimic a setup at work, and want to get more familiar with the Spring Data JPA API.

#### Setup & Migrations

I'm going to use the `flyway` library to manage migrations. This will allow me to version control the database schema and apply changes incrementally.

### Spring Boot Service Design

I think I'm going to use spring boot 3.5.2

### Frontend Views

I need to represent the following views somewhere in the frontend:

- Add a paper /paper/new
- View a paper /paper/{id}
- View all papers /
- Filter papers (part of view all papers) /search?q={query}
- Update a paper /paper/{id}/edit

In my model, I have a review section, so each paper can potentially have a "score" from 1 - 5, and a text review, but I don't have a way to expose that to the user yet.

#### Related Templates

JTE is going to be the template engine building the views. The above views can all leverage similar templates:

- container.jte
  - This is the main container for the page, it will include the header and footer.
- paper-list.jte
  - Going to be used for the main page and search results.
  - This will include a list of papers, with links to view each paper, quick actions to mark as read, add 1 to 5 stars, and a link to edit the paper.
- paper-details.jte
- search-bar.jte

## Directory Structure

### Random Notes

- `src/main/jte/.jteroot` exists to let IntelliJ know how to resolve JTE templates.