package io.github.jonjohnsontc.whattoread.controller;

import gg.jte.TemplateEngine;
import io.github.jonjohnsontc.whattoread.exception.PaperNotFoundException;
import io.github.jonjohnsontc.whattoread.model.PaperListEntry;
import io.github.jonjohnsontc.whattoread.model.ErrorPage;
import io.github.jonjohnsontc.whattoread.service.PaperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HomeControllerErrorHandlingTest {

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private PaperService paperService;

    private HomeController homeController;
    private UUID validUuid;
    private PaperListEntry mockPaper;

    @BeforeEach
    void setUp() {
        homeController = new HomeController(templateEngine, null, paperService);
        validUuid = UUID.randomUUID();
        mockPaper = PaperListEntry.builder()
                .id(validUuid.toString())
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
    void viewPaper_WithValidUuidAndExistingPaper_ShouldRenderPaperDetails() {
        // Arrange
        when(paperService.getPaperById(validUuid)).thenReturn(mockPaper);

        // Act
        String result = homeController.viewPaper(validUuid.toString());

        // Assert
        verify(paperService).getPaperById(validUuid);
        verify(templateEngine).render(eq("paperDetails.jte"), eq(mockPaper), any());
        assertNotNull(result);
    }

    @Test
    void viewPaper_WithInvalidUuidFormat_ShouldRenderErrorPage() {
        // Arrange
        String invalidUuid = "not-a-valid-uuid";

        // Act
        String result = homeController.viewPaper(invalidUuid);

        // Assert
        verify(paperService, never()).getPaperById(any());
        verify(templateEngine).render(eq("error/paperNotFound.jte"), any(ErrorPage.class), any());
        assertNotNull(result);
    }

    @Test
    void viewPaper_WithValidUuidButNonExistentPaper_ShouldRenderErrorPage() {
        // Arrange
        UUID nonExistentUuid = UUID.randomUUID();

        when(paperService.getPaperById(nonExistentUuid))
                .thenThrow(new PaperNotFoundException(nonExistentUuid.toString()));

        // Act
        String result = homeController.viewPaper(nonExistentUuid.toString());

        // Assert
        verify(paperService).getPaperById(nonExistentUuid);
        verify(templateEngine).render(eq("error/paperNotFound.jte"), any(ErrorPage.class), any());
        assertNotNull(result);
    }

    @Test
    void viewPaper_WithEmptyString_ShouldRenderErrorPage() {
        // Arrange
        String emptyUuid = "";

        // Act
        String result = homeController.viewPaper(emptyUuid);

        // Assert
        verify(paperService, never()).getPaperById(any());
        verify(templateEngine).render(eq("error/paperNotFound.jte"), any(ErrorPage.class), any());
        assertNotNull(result);
    }

    @Test
    void viewPaper_WithNullString_ShouldRenderErrorPage() {
        // Act
        String result = homeController.viewPaper(null);

        // Assert
        verify(paperService, never()).getPaperById(any());
        verify(templateEngine).render(eq("error/paperNotFound.jte"), any(ErrorPage.class), any());
        assertNotNull(result);
    }

    @Test
    void renderErrorPage_ShouldCreateErrorPageWithCorrectMessage() {
        // Act
        homeController.viewPaper("invalid-uuid");

        // Assert
        verify(templateEngine).render(eq("error/paperNotFound.jte"), any(ErrorPage.class), any());
    }
}
