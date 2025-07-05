@import io.github.jonjohnsontc.whattoread.model.PaperList
@import io.github.jonjohnsontc.whattoread.model.PaperListEntry
@param PaperList paperList

<div class="list-container">
    <ul class="paper-list">
        @for(PaperListEntry entry : paperList.getPapers())
            <li class="paper-list-entry-list-item">
                @template.paperListEntry(entry = entry)
            </li>
        @endfor 
    </ul>
</div>