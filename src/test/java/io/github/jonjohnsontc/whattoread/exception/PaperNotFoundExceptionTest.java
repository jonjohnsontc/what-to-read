package io.github.jonjohnsontc.whattoread.exception;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaperNotFoundExceptionTest {

    @Test
    void constructor_WithValidId_ShouldCreateExceptionWithCorrectMessage() {
        // Arrange
        String testId = UUID.randomUUID().toString();
        String expectedMessage = "Paper with ID '" + testId + "' was not found. It may have been removed or never existed.";

        // Act
        PaperNotFoundException exception = new PaperNotFoundException(testId);

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void constructor_WithNullId_ShouldCreateExceptionWithNullInMessage() {
        // Arrange
        String expectedMessage = "Paper with ID 'null' was not found. It may have been removed or never existed.";

        // Act
        PaperNotFoundException exception = new PaperNotFoundException(null);

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void constructor_WithEmptyId_ShouldCreateExceptionWithEmptyStringInMessage() {
        // Arrange
        String expectedMessage = "Paper with ID '' was not found. It may have been removed or never existed.";

        // Act
        PaperNotFoundException exception = new PaperNotFoundException("");

        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void exception_ShouldBeRuntimeException() {
        // Arrange & Act
        PaperNotFoundException exception = new PaperNotFoundException("test-id");

        // Assert
        assertInstanceOf(RuntimeException.class, exception);
    }
}
