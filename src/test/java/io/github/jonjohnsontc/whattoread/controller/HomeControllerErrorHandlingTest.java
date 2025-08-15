package io.github.jonjohnsontc.whattoread.controller;

import gg.jte.TemplateEngine;
import io.github.jonjohnsontc.whattoread.exception.PaperNotFoundException;
import io.github.jonjohnsontc.whattoread.model.PaperDetails;
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
    private PaperDetails mockPaper;

    @BeforeEach
    void setUp() {
        homeController = new HomeController(templateEngine, null, paperService);
        validUuid = UUID.randomUUID();
        mockPaper = new PaperDetails(
                validUuid.toString(),
                "Test Paper",
                List.of("Test Author"),
                List.of("test"),
                "https://example.com",
                2023,
                4,
                "Test review",
                false,
                "Test notes"
        );
    }

    @Test
    void viewPaper_WithValidUuidAndExistingPaper_ShouldRenderPaperDetails() {
        // Arrange
        when(paperService.getPaperDetailsById(validUuid)).thenReturn(mockPaper);

        // Act
        String result = homeController.viewPaper(validUuid.toString(), false);

        // Assert
        verify(paperService).getPaperDetailsById(validUuid);
        verify(templateEngine).render(eq("paperDetails.jte"), eq(mockPaper), any());
        assertNotNull(result);
    }

    @Test
    void viewPaper_WithInvalidUuidFormat_ShouldRenderErrorPage() {
        // Arrange
        String invalidUuid = "not-a-valid-uuid";

        // Act
        String result = homeController.viewPaper(invalidUuid, false);

        // Assert
        verify(paperService, never()).getPaperDetailsById(any());
        verify(templateEngine).render(eq("error/paperNotFound.jte"), any(ErrorPage.class), any());
        assertNotNull(result);
    }

    @Test
    void viewPaper_WithValidUuidButNonExistentPaper_ShouldRenderErrorPage() {
        // Arrange
        UUID nonExistentUuid = UUID.randomUUID();

        when(paperService.getPaperDetailsById(nonExistentUuid))
                .thenThrow(new PaperNotFoundException(nonExistentUuid.toString()));

        // Act
        String result = homeController.viewPaper(nonExistentUuid.toString(), false);

        // Assert
        verify(paperService).getPaperDetailsById(nonExistentUuid);
        verify(templateEngine).render(eq("error/paperNotFound.jte"), any(ErrorPage.class), any());
        assertNotNull(result);
    }

    @Test
    void viewPaper_WithEmptyString_ShouldRenderErrorPage() {
        // Arrange
        String emptyUuid = "";

        // Act
        String result = homeController.viewPaper(emptyUuid, false);

        // Assert
        verify(paperService, never()).getPaperDetailsById(any());
        verify(templateEngine).render(eq("error/paperNotFound.jte"), any(ErrorPage.class), any());
        assertNotNull(result);
    }

    @Test
    void viewPaper_WithNullString_ShouldRenderErrorPage() {
        // Act
        String result = homeController.viewPaper(null, false);

        // Assert
        verify(paperService, never()).getPaperDetailsById(any());
        verify(templateEngine).render(eq("error/paperNotFound.jte"), any(ErrorPage.class), any());
        assertNotNull(result);
    }

    @Test
    void renderErrorPage_ShouldCreateErrorPageWithCorrectMessage() {
        // Act
        homeController.viewPaper("invalid-uuid", false);

        // Assert
        verify(templateEngine).render(eq("error/paperNotFound.jte"), any(ErrorPage.class), any());
    }
}
