package io.github.jonjohnsontc.whattoread.integration;

import io.github.jonjohnsontc.whattoread.model.PaperListEntry;
import io.github.jonjohnsontc.whattoread.repository.PaperListQ;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
class PaperViewErrorHandlingIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PaperListQ paperListQ;

    @AfterEach
    void cleanUp() {
        // Clean up any test data after each test
        paperListQ.deleteAll();
    }

    @Test
    void viewPaper_WithValidExistingPaper_ShouldReturnPaperDetails() {
        // Arrange - Create a test paper
        PaperListEntry testPaper = PaperListEntry.builder()
                .id(UUID.randomUUID())
                .title("Integration Test Paper")
                .authors(List.of("Test Author"))
                .tags(List.of("integration", "test"))
                .year(2023)
                .rating(5)
                .read(true)
                .url("https://example.com/test-paper")
                .notes("Test notes for integration")
                .build();

        paperListQ.save(testPaper);

        // Act
        ResponseEntity<String> response = this.restTemplate.getForEntity(
            "http://localhost:" + port + "/paper/" + testPaper.getId(), String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Integration Test Paper");
        assertThat(response.getBody()).contains("Test Author");
        assertThat(response.getBody()).contains("https://example.com/test-paper");
    }

    @Test
    void viewPaper_WithInvalidUuidFormat_ShouldReturn404ErrorPage() {
        // Act
        ResponseEntity<String> response = this.restTemplate.getForEntity(
            "http://localhost:" + port + "/paper/invalid-uuid-format", String.class);

        // Assert - Should return 200 OK with error page content, not a 404 HTTP status
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("404");
        assertThat(response.getBody()).contains("Paper Not Found");
        assertThat(response.getBody()).contains("ID format is invalid");
    }

    @Test
    void viewPaper_WithValidUuidButNonExistentPaper_ShouldReturn404ErrorPage() {
        // Arrange
        UUID nonExistentUuid = UUID.randomUUID();

        // Act
        ResponseEntity<String> response = this.restTemplate.getForEntity(
            "http://localhost:" + port + "/paper/" + nonExistentUuid, String.class);

        // Assert - Should return 200 OK with error page content, not a 500 error
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("404");
        assertThat(response.getBody()).contains("Paper Not Found");
        assertThat(response.getBody()).contains("was not found");
        assertThat(response.getBody()).contains("may have been removed or never existed");
    }

    @Test
    void viewPaper_ErrorPage_ShouldContainNavigationLinks() {
        // Act
        ResponseEntity<String> response = this.restTemplate.getForEntity(
            "http://localhost:" + port + "/paper/invalid-uuid", String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Browse All Papers");
        assertThat(response.getBody()).contains("Search Papers");
        assertThat(response.getBody()).contains("href=\"/\"");
        assertThat(response.getBody()).contains("href=\"/search\"");
    }

    @Test
    void viewPaper_ErrorPage_ShouldHaveCorrectStyling() {
        // Act
        ResponseEntity<String> response = this.restTemplate.getForEntity(
            "http://localhost:" + port + "/paper/invalid-uuid", String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("error-container");
        assertThat(response.getBody()).contains("error-code");
        assertThat(response.getBody()).contains("error-message");
        assertThat(response.getBody()).contains("btn-primary");
    }
}
