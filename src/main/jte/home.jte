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