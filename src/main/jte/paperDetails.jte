@import io.github.jonjohnsontc.whattoread.model.PaperDetails
@param PaperDetails paper

<div class="container">
    <div class="navigation">
        <a href="/">&larr; Back to Papers</a>
</div>

    <div class="paper-header">
        <h1 id="paperTitle">${paper.getTitle()}</h1>
        <div class="paper-actions">
            <button id="editToggle" class="edit-btn">Edit</button>
            <button id="saveBtn" class="save-btn" style="display: none;">Save</button>
            <button id="cancelBtn" class="cancel-btn" style="display: none;">Cancel</button>
        </div>
    </div>

    <form id="paperForm" action="/paper/${paper.getId()}" method="post" style="display: none;">
        <input type="hidden" name="_method" value="PUT">

        <div class="paper-details">
            <!-- Title Section -->
            <div class="detail-group">
                <label class="detail-label">Title:</label>
                <div class="detail-value">
                    <span id="titleDisplay">${paper.getTitle()}</span>
                    <input type="text" id="titleEdit" name="title" value="${paper.getTitle()}" class="edit-input" style="display: none;" required>
                </div>
            </div>

            <!-- URL Section -->
            <div class="detail-group">
                <label class="detail-label">URL:</label>
                <div class="detail-value">
                    <span id="urlDisplay"><a href="${paper.getUrl()}" target="_blank" rel="noopener noreferrer">${paper.getUrl()}</a></span>
                    <input type="url" id="urlEdit" name="url" value="${paper.getUrl()}" class="edit-input" style="display: none;" required>
                </div>
            </div>

            <!-- Year Section -->
            <div class="detail-group">
                <label class="detail-label">Year:</label>
                <div class="detail-value">
                    <span id="yearDisplay">${paper.getYear()}</span>
                    <input type="number" id="yearEdit" name="year" value="${paper.getYear()}" class="edit-input" style="display: none;" required>
                </div>
            </div>

            <!-- Authors Section -->
            <div class="detail-group">
                <label class="detail-label">Authors:</label>
                <div class="detail-value">
                    <span id="authorsDisplay">
                        @if(paper.getAuthors() != null && !paper.getAuthors().isEmpty())
                            @for(var author : paper.getAuthors())
                                <span class="author-tag">${author}</span>
                            @endfor
                        @else
                            <em>No authors specified</em>
                        @endif
                    </span>
                    <input type="text" id="authorsEdit" name="authors"
                           value="@if(paper.getAuthors() != null)${String.join(", ", paper.getAuthors())}@endif"
                           class="edit-input" style="display: none;" placeholder="Comma-separated authors">
                </div>
            </div>

            <!-- Tags Section -->
            <div class="detail-group">
                <label class="detail-label">Tags:</label>
                <div class="detail-value">
                    <span id="tagsDisplay">
                        @if(paper.getTags() != null && !paper.getTags().isEmpty())
                            @for(var tag : paper.getTags())
                                <span class="tag">${tag}</span>
                            @endfor
                        @else
                            <em>No tags</em>
                        @endif
                    </span>
                    <input type="text" id="tagsEdit" name="tags"
                           value="@if(paper.getTags() != null)${paper.getTags().stream().map(t -> t).collect(java.util.stream.Collectors.joining(", "))}@endif"
                           class="edit-input" style="display: none;" placeholder="Comma-separated tags">
                </div>
            </div>

            <!-- Read Status Section -->
            <div class="detail-group">
                <label class="detail-label">Status:</label>
                <div class="detail-value">
                    <span id="readDisplay" class="status-badge ${paper.isRead() ? "read" : "unread"}">
                        ${paper.isRead() ? "Read" : "Not Read"}
                    </span>
                    <div id="readEdit" style="display: none;">
                        <input type="checkbox" id="readCheckbox" name="read" ${paper.isRead() ? "checked" : ""}>
                        <label for="readCheckbox">Mark as read</label>
                    </div>
                </div>
            </div>

            <!-- Notes Section -->
            <div class="detail-group">
                <label class="detail-label">Notes:</label>
                <div class="detail-value">
                    <div id="notesDisplay" class="notes-content">
                        @if(paper.getNotes() != null && !paper.getNotes().trim().isEmpty())
                            ${paper.getNotes()}
                        @else
                            <em>No notes</em>
                        @endif
                    </div>
                    <textarea id="notesEdit" name="notes" class="edit-input notes-textarea" style="display: none;" rows="6" placeholder="Add your notes here...">${paper.getNotes() != null ? paper.getNotes() : ""}</textarea>
                </div>
            </div>
        </div>
    </form>

    <!-- View-only details (shown by default) -->
    <div id="viewMode" class="paper-details">
        <!-- Title Section -->
        <div class="detail-group">
            <label class="detail-label">Title:</label>
            <div class="detail-value">${paper.getTitle()}</div>
        </div>

        <!-- URL Section -->
        <div class="detail-group">
            <label class="detail-label">URL:</label>
            <div class="detail-value">
                <a href="${paper.getUrl()}" target="_blank" rel="noopener noreferrer" class="paper-link">${paper.getUrl()}</a>
            </div>
        </div>

        <!-- Year Section -->
        <div class="detail-group">
            <label class="detail-label">Year:</label>
            <div class="detail-value">${paper.getYear()}</div>
        </div>

        <!-- Authors Section -->
        <div class="detail-group">
            <label class="detail-label">Authors:</label>
            <div class="detail-value">
                @if(paper.getAuthors() != null && !paper.getAuthors().isEmpty())
                    @for(var author : paper.getAuthors())
                        <span class="author-tag">${author}</span>
                    @endfor
                @else
                    <em>No authors specified</em>
                @endif
            </div>
        </div>

        <!-- Tags Section -->
        <div class="detail-group">
            <label class="detail-label">Tags:</label>
            <div class="detail-value">
                @if(paper.getTags() != null && !paper.getTags().isEmpty())
                    @for(var tag : paper.getTags())
                        <span class="tag">${tag}</span>
                    @endfor
                @else
                    <em>No tags</em>
                @endif
            </div>
        </div>

        <!-- Read Status Section -->
        <div class="detail-group">
            <label class="detail-label">Status:</label>
            <div class="detail-value">
                <span class="status-badge ${paper.isRead() ? 'read' : 'unread'}">
                    ${paper.isRead() ? 'Read' : 'Not Read'}
                </span>
            </div>
        </div>

        <!-- Notes Section -->
        <div class="detail-group">
            <label class="detail-label">Notes:</label>
            <div class="detail-value notes-content">
                @if(paper.getNotes() != null && !paper.getNotes().trim().isEmpty())
                    ${paper.getNotes()}
                @else
                    <em>No notes</em>
                @endif
            </div>
        </div>
    </div>
</div>

<style>
.container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
}

.navigation {
    margin-bottom: 20px;
}

.navigation a {
    color: #007bff;
    text-decoration: none;
    font-size: 16px;
}

.navigation a:hover {
    text-decoration: underline;
}

.paper-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30px;
    padding-bottom: 15px;
    border-bottom: 2px solid #eee;
}

.paper-header h1 {
    margin: 0;
    color: #333;
}

.paper-actions {
    display: flex;
    gap: 10px;
}

.edit-btn, .save-btn, .cancel-btn {
    padding: 8px 16px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
}

.edit-btn {
    background-color: #007bff;
    color: white;
}

.save-btn {
    background-color: #28a745;
    color: white;
}

.cancel-btn {
    background-color: #6c757d;
    color: white;
}

.paper-details {
    background: #f8f9fa;
    padding: 20px;
    border-radius: 8px;
}

.detail-group {
    margin-bottom: 20px;
    display: flex;
    align-items: flex-start;
}

.detail-label {
    font-weight: bold;
    min-width: 120px;
    color: #555;
    margin-right: 15px;
    margin-top: 5px;
}

.detail-value {
    flex: 1;
    line-height: 1.5;
}

.edit-input {
    width: 100%;
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 14px;
}

.notes-textarea {
    resize: vertical;
    min-height: 120px;
}

.author-tag, .tag {
    display: inline-block;
    background: #e9ecef;
    padding: 4px 8px;
    margin-right: 8px;
    margin-bottom: 4px;
    border-radius: 4px;
    font-size: 12px;
}

.tag {
    background: #007bff;
    color: white;
}

.status-badge {
    padding: 4px 12px;
    border-radius: 4px;
    font-size: 12px;
    font-weight: bold;
}

.status-badge.read {
    background: #d4edda;
    color: #155724;
}

.status-badge.unread {
    background: #f8d7da;
    color: #721c24;
}

.paper-link {
    color: #007bff;
    text-decoration: none;
    word-break: break-all;
}

.paper-link:hover {
    text-decoration: underline;
}

.notes-content {
    white-space: pre-wrap;
    background: white;
    padding: 12px;
    border-radius: 4px;
    border: 1px solid #ddd;
    min-height: 60px;
}
</style>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const editToggle = document.getElementById('editToggle');
    const saveBtn = document.getElementById('saveBtn');
    const cancelBtn = document.getElementById('cancelBtn');
    const paperForm = document.getElementById('paperForm');
    const viewMode = document.getElementById('viewMode');

    let isEditing = false;

    editToggle.addEventListener('click', function() {
        isEditing = true;
        toggleEditMode();
    });

    cancelBtn.addEventListener('click', function() {
        isEditing = false;
        toggleEditMode();
    });

    function toggleEditMode() {
        if (isEditing) {
            // Switch to edit mode
            viewMode.style.display = 'none';
            paperForm.style.display = 'block';
            editToggle.style.display = 'none';
            saveBtn.style.display = 'inline-block';
            cancelBtn.style.display = 'inline-block';
        } else {
            // Switch to view mode
            viewMode.style.display = 'block';
            paperForm.style.display = 'none';
            editToggle.style.display = 'inline-block';
            saveBtn.style.display = 'none';
            cancelBtn.style.display = 'none';
        }
    }
});
</script>
