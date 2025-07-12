<div class="container">
    <h1>Add a Paper</h1>
    <form action="/newPaper" method="post">
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" required>
        </div>
        <div class="form-group">
            <label for="url">URL:</label>
            <input type="url" id="url" name="url" required>
        </div>
        <div class="form-group">
            <label for="authors">Authors (comma-separated):</label>
            <input type="text" id="authors" name="authors" required>
        </div>
        <div class="form-group">
            <label for="year">Year:</label>
            <input type="number" id="year" name="year" required>
        </div>
        <div class="form-group">
            <label for="rating">Rating (1-5):</label>
            <input type="number" id="rating" name="rating" min="1" max="5" required>
        </div>
        <button type="submit">Add Paper</button>
    </form>
</div>