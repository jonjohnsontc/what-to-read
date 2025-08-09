@import io.github.jonjohnsontc.whattoread.model.PaperList
@param PaperList paperList

<div class="container">
    <div>
        <a href="/paper/new">Add a Paper</a>
    </div>
    <h1>What to Read</h1>
    <input type="text" id="searchBar" placeholder="Search papers..."/>
    <div class="filter-container">
        <label for="filterRead">Show Read Papers</label>
        <input type="checkbox" id="filterRead"/>
    </div>
    <div id="paperListContainer" class="list-container">
        @template.paperList(paperList = paperList)
    </div>
</div>

<script>
    let searchTimeout;

    function performSearch() {
        const searchTerm = document.getElementById('searchBar').value;
        fetch('/search?term=' + encodeURIComponent(searchTerm))
            .then(response => response.text())
            .then(html => {
                document.getElementById('paperListContainer').innerHTML = html;
            });
    }

    document.getElementById('searchBar').addEventListener('input', function () {
        clearTimeout(searchTimeout);
        searchTimeout = setTimeout(performSearch, 300);
    });
</script>