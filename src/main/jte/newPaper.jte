<div class="container">
    <h1>Add a Paper</h1>
    <form id="paperForm" action="/paper/new" method="post">
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" required>
        </div>
        <div class="form-group">
            <label for="url">URL:</label>
            <input type="url" id="url" name="url" required>
        </div>
        <div class="form-group" id="authorsTextGroup">
            <label for="authors">Authors (comma-separated):</label>
            <input type="text" id="authors" name="authors">
        </div>
        <div class="form-group" id="jsAuthorsGroup" style="display:none;">
            <label for="numAuthors">Number of Authors:</label>
            <input type="number" id="numAuthors" min="1" max="20" value="1">
            <button type="button" id="generateAuthors">Add Author Fields</button>
            <div id="authorsContainer"></div>
            <input type="hidden" id="authorsHidden" name="authors">
        </div>
        <div class="form-group">
            <label for="year">Year:</label>
            <input type="number" id="year" name="year" required>
        </div>
        <div class="form-group">
            <label for="read">
                <input type="checkbox" id="read" name="read" value="true">
                Already Read
            </label>
            <input type="hidden" name="read" value="false">
        </div>
        <div class="form-group">
            <label for="rating">Rating (1-5):</label>
            <input type="number" id="rating" name="rating" min="1" max="5">
        </div>
        <div class="form-group">
            <label for="tags">Tags (comma-separated):</label>
            <input type="text" id="tags" name="tags" required>
        </div>
        <div class="form-group">
            <label for="notes">Notes:</label>
            <textarea id="notes" name="notes" rows="6"></textarea>
        </div>
        <button type="submit">Add Paper</button>
    </form>
</div>
<script>
    document.getElementById('authorsTextGroup').style.display = 'none';
    document.getElementById('jsAuthorsGroup').style.display = 'block';

    document.getElementById('generateAuthors').addEventListener('click', function () {
        const num = parseInt(document.getElementById('numAuthors').value, 10);
        const container = document.getElementById('authorsContainer');
        container.innerHTML = '';
        for (let i = 0; i < num; i++) {
            const div = document.createElement('div');
            div.className = 'form-group';
            // Use concatenation instead of template literals
            div.innerHTML = '<label for="author' + i + '">Author ' + (i + 1) + ':</label>' +
                '<input type="text" id="author' + i + '" class="authorField" required>';
            container.appendChild(div);
        }
    });


    document.getElementById('paperForm').addEventListener('submit', function (e) {
        e.preventDefault();

        if (document.getElementById('jsAuthorsGroup').style.display === 'block') {
            const authorFields = document.querySelectorAll('.authorField');
            const authors = Array.from(authorFields).map(f => f.value.trim()).filter(v => v).join(',');
            document.getElementById('authorsHidden').value = authors;
        }

        this.submit();
    });
</script>

<style>
    body {
        font-family: 'Courier New', 'Monaco', 'Consolas', monospace;
    }

    .container {
        max-width: 600px;
        margin: 0 auto;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }
</style>