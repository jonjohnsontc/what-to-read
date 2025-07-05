@import io.github.jonjohnsontc.whattoread.model.PaperListEntry
@param PaperListEntry entry

<div class="paper-list-entry">
    <h2>${entry.getTitle()}</h2>
    <span>Authors: ${String.join(",", entry.getAuthors())}</span>
    <span>Year: ${entry.getYear()}</span>
    <span>Rating: ${entry.getRating()}</span>
    <span><a href="/paper/${entry.getId()}">Read More</a></span>
</div>