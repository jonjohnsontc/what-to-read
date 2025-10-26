package io.github.jonjohnsontc.whattoread.controller;

import gg.jte.TemplateEngine;
import gg.jte.TemplateOutput;
import gg.jte.output.StringOutput;
import io.github.jonjohnsontc.whattoread.model.PaperList;
import io.github.jonjohnsontc.whattoread.model.ErrorPage;
import io.github.jonjohnsontc.whattoread.service.PaperService;
import io.github.jonjohnsontc.whattoread.exception.PaperNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.Optional;

@Controller
public class HomeController {

    private final TemplateEngine templateEngine;
    private final PaperList paperList;
    private final PaperService paperService;

    public HomeController(TemplateEngine templateEngine, PaperList paperList, PaperService paperService) {
        this.templateEngine = templateEngine;
        this.paperList = paperList;
        this.paperService = paperService;
    }

    @GetMapping("/")
    @ResponseBody
    public String home() {
        final String templateName = "home.jte";
        var page = paperService.getAllPapers();

        paperList.setPapers(page.getContent());
        paperList.setCurrentPage(page.getNumber());
        paperList.setTotalPages(page.getTotalPages());
        paperList.setTotalItems(page.getTotalElements());

        TemplateOutput output = new StringOutput();
        templateEngine.render(templateName, paperList, output);
        return output.toString();
    }

    @GetMapping("/search")
    @ResponseBody
    public String search(@RequestParam(required = false) String term) {
        var page = term != null && !term.isBlank()
                ? paperService.getPapersBySearchTerm(term)
                : paperService.getAllPapers();

        paperList.setPapers(page.getContent());
        paperList.setCurrentPage(page.getNumber());
        paperList.setTotalPages(page.getTotalPages());
        paperList.setTotalItems(page.getTotalElements());

        TemplateOutput output = new StringOutput();
        templateEngine.render("paperList.jte", paperList, output);
        return output.toString();
    }

    @GetMapping("/paper/new")
    @ResponseBody
    public String newPaper() {
        System.out.println("Retrieving /paper/new details");
        TemplateOutput output = new StringOutput();
        templateEngine.render("newPaper.jte", null, output);
        return output.toString();
    }

    @PostMapping("/paper/new")
    public String createPaper(@RequestParam String title, @RequestParam String authors,
                              @RequestParam(required = false) String tags, @RequestParam String url,
                              @RequestParam int year, @RequestParam(required = false) Optional<Integer> rating,
                              @RequestParam boolean read, @RequestParam String notes) {
        System.out.println("Entering /paper/new");
        // Convert authors and tags from comma-separated strings to arrays
        String[] authorsArray = authors.split(",");
        String[] tagsArray = tags.split(",");
        paperService.createPaper(title, url, year, rating, authorsArray, tagsArray, read, notes);
        return "redirect:/";
    }

    @GetMapping("/paper/{id}")
    @ResponseBody
    public String viewPaper(@PathVariable String id, @RequestParam(required = false, defaultValue = "false") boolean edit) {
        try {
            // Check for null or empty ID first
            if (id == null || id.trim().isEmpty()) {
                return renderErrorPage("The paper ID is missing. Please check the URL and try again.");
            }

            // Parse the UUID - this will throw IllegalArgumentException if invalid
            UUID paperId = UUID.fromString(id);

            // If edit is true, we use edit template
            String templateName = "paperDetails.jte";

            // Get the paper - this will throw PaperNotFoundException if not found
            var paper = paperService.getPaperDetailsById(paperId);

            // Render the paper details template
            TemplateOutput output = new StringOutput();
            templateEngine.render(templateName, paper, output);
            return output.toString();

        } catch (IllegalArgumentException e) {
            // Handle invalid UUID format
            return renderErrorPage("The paper ID format is invalid. Please check the URL and try again.");
        } catch (PaperNotFoundException e) {
            // Handle paper not found
            return renderErrorPage(e.getMessage());
        }
    }

    /**
     * Helper method to render error pages consistently
     */
    private String renderErrorPage(String message) {
        var errorPage = new ErrorPage(message);
        TemplateOutput output = new StringOutput();
        templateEngine.render("error/paperNotFound.jte", errorPage, output);
        return output.toString();
    }
}
