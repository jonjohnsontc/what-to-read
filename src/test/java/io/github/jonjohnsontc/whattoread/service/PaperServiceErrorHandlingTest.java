package io.github.jonjohnsontc.whattoread.service;

import io.github.jonjohnsontc.whattoread.exception.PaperNotFoundException;
import io.github.jonjohnsontc.whattoread.model.PaperListEntry;
import io.github.jonjohnsontc.whattoread.repository.PaperJdbcRepository;
import io.github.jonjohnsontc.whattoread.repository.PaperListQ;
import io.github.jonjohnsontc.whattoread.repository.PaperDetailsQ;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaperServiceErrorHandlingTest {

    @Mock
    private PaperListQ paperListQ;

    @Mock
    private PaperDetailsQ paperDetailsQ;

    @Mock
    private PaperJdbcRepository paperRepository;

    private PaperService paperService;
    private UUID validUuid;
    private PaperListEntry mockPaper;

    @BeforeEach
    void setUp() {
        paperService = new PaperService(paperListQ, paperDetailsQ, paperRepository);
        validUuid = UUID.randomUUID();
        mockPaper = PaperListEntry.builder()
                .id(validUuid)
                .title("Test Paper")
                .authors(List.of("Test Author"))
                .tags(List.of("test"))
                .year(2023)
                .rating(4)
                .read(false)
                .url("https://example.com")
                .notes("Test notes")
                .build();
    }

    @Test
    void getPaperById_WithExistingPaper_ShouldReturnPaper() {
        // Arrange
        when(paperListQ.findById(validUuid.toString())).thenReturn(Optional.of(mockPaper));

        // Act
        PaperListEntry result = paperService.getPaperById(validUuid);

        // Assert
        assertNotNull(result);
        assertEquals(mockPaper.getId(), result.getId());
        assertEquals(mockPaper.getTitle(), result.getTitle());
        verify(paperListQ).findById(validUuid.toString());
    }

    @Test
    void getPaperById_WithNonExistentPaper_ShouldThrowPaperNotFoundException() {
        // Arrange
        UUID nonExistentUuid = UUID.randomUUID();
        when(paperListQ.findById(nonExistentUuid.toString())).thenReturn(Optional.empty());

        // Act & Assert
        PaperNotFoundException exception = assertThrows(
                PaperNotFoundException.class,
                () -> paperService.getPaperById(nonExistentUuid)
        );

        String expectedMessage = "Paper with ID '" + nonExistentUuid + "' was not found. It may have been removed or never existed.";
        assertEquals(expectedMessage, exception.getMessage());
        verify(paperListQ).findById(nonExistentUuid.toString());
    }

    @Test
    void getPaperById_WithValidUuid_ShouldCallRepositoryWithStringId() {
        // Arrange
        when(paperListQ.findById(validUuid.toString())).thenReturn(Optional.of(mockPaper));

        // Act
        paperService.getPaperById(validUuid);

        // Assert
        verify(paperListQ).findById(validUuid.toString());
        verifyNoMoreInteractions(paperListQ);
    }

    @Test
    void getPaperById_WhenRepositoryReturnsNull_ShouldThrowPaperNotFoundException() {
        // Arrange
        UUID testUuid = UUID.randomUUID();
        when(paperListQ.findById(testUuid.toString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PaperNotFoundException.class, () -> paperService.getPaperById(testUuid));
        verify(paperListQ).findById(testUuid.toString());
    }
}
