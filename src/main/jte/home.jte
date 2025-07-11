@import io.github.jonjohnsontc.whattoread.model.PaperList
@param PaperList paperList

<div class="container">
    <h1>What to Read</h1>
    <input type="text" id="searchBar" placeholder="Search papers..." />
    <div class="filter-container">
        <label for="filterRead">Show Read Papers</label>
        <input type="checkbox" id="filterRead" />
    </div>
    @template.paperList(paperList = paperList)
</div>

<script>
    let searchTimeout;
    document.getElementById('searchBar').addEventListener('input', function() {
        clearTimeout(searchTimeout);
        searchTimeout = setTimeout(() => {
            const query = this.value.toLowerCase();
            const papers = document.querySelectorAll('.paper-item');
            papers.forEach(paper => {
                const title = paper.querySelector('.paper-title').textContent.toLowerCase();
                paper.style.display = title.includes(query) ? '' : 'none';
            });
        }, 300);
    });
</script>