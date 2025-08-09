package io.github.jonjohnsontc.whattoread.exception;

public class PaperNotFoundException extends RuntimeException {
    public PaperNotFoundException(String id) {
        super("Paper with ID '" + id + "' was not found. It may have been removed or never existed.");
    }
}
