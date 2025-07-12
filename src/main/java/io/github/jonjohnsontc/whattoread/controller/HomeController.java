package io.github.jonjohnsontc.whattoread.controller;

import gg.jte.TemplateEngine;
import gg.jte.TemplateOutput;
import gg.jte.output.StringOutput;
import io.github.jonjohnsontc.whattoread.model.PaperList;
import io.github.jonjohnsontc.whattoread.service.PaperService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        TemplateOutput output = new StringOutput();
        templateEngine.render("newPaper.jte", null, output);
        return output.toString();
    }

    @PostMapping("/paper/new")
    @ResponseBody
    public String createPaper(@RequestParam String title, @RequestParam String authors,
                              @RequestParam String tags, @RequestParam String url,
                              @RequestParam int year, @RequestParam(required = false) Integer rating,
                              @RequestParam boolean read) {
        // Convert authors and tags from comma-separated strings to arrays
        String[] authorsArray = authors.split(",");
        String[] tagsArray = tags.split(",");
        paperService.createPaper(title, url, year, rating, authorsArray, tagsArray, read);
        return "redirect:/";
    }
}
