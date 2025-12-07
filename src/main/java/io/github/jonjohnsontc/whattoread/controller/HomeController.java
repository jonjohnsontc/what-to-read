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
    public String home(@RequestParam(required = false, defaultValue = "false") boolean showRead) {
        final String templateName = "home.jte";
        var page = showRead ? paperService.getAllPapers() : paperService.getUnreadPapers();

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
        TemplateOutput output = new StringOutput();
        templateEngine.render("newPaper.jte", null, output);
        return output.toString();
    }

    @PostMapping("/paper/new")
    public String createPaper(@RequestParam String title, @RequestParam String authors,
                              @RequestParam(required = false, defaultValue = "") String tags, @RequestParam String url,
                              @RequestParam int year, @RequestParam(required = false) Optional<Integer> rating,
                              @RequestParam boolean read, @RequestParam(required = false, defaultValue = "") String notes) {
        // Convert authors and tags from comma-separated strings to arrays
        String[] authorsArray = authors.split(",");
        paperService.createPaper(title, url, year, rating, authorsArray, tags, read, notes);
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

    @PostMapping("/paper/{id}")
    public String updatePaper(@PathVariable String id,
                              @RequestParam String title,
                              @RequestParam String url,
                              @RequestParam int year,
                              @RequestParam(required = false, defaultValue = "") String authors,
                              @RequestParam(required = false, defaultValue = "") String tags,
                              @RequestParam(required = false, defaultValue = "false") boolean read,
                              @RequestParam(required = false, defaultValue = "") String notes) {
        try {
            UUID paperId = UUID.fromString(id);

            // Convert authors from comma-separated string to array
            String[] authorsArray = authors.isEmpty()
                ? new String[0]
                : authors.split(",");

            paperService.updatePaper(paperId, title, url, year, authorsArray, tags, read, notes);

            return "redirect:/paper/" + id;
        } catch (IllegalArgumentException e) {
            return "redirect:/";
        } catch (PaperNotFoundException e) {
            return "redirect:/";
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
