@import io.github.jonjohnsontc.whattoread.model.PaperList
@param PaperList paperList

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WhatToRead</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>

<div class="container">
    <a href="/paper/new">Add a Paper</a>
     <div class="logo-container">
        @template.components.logo()
        <h1>What to Read</h1>
    </div>
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

</body>
</html>