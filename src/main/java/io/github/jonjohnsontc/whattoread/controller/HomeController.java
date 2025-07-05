package io.github.jonjohnsontc.whattoread.controller;

import gg.jte.TemplateEngine;
import gg.jte.TemplateOutput;
import gg.jte.output.StringOutput;
import io.github.jonjohnsontc.whattoread.model.Page;
import io.github.jonjohnsontc.whattoread.model.PaperList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    private final TemplateEngine templateEngine;
    private final Page page;
    private final PaperList paperList;

    public HomeController(TemplateEngine templateEngine, Page page, PaperList paperList) {
        this.templateEngine = templateEngine;
        this.page = page;
        this.paperList = paperList;
    }

    @GetMapping("/")
    @ResponseBody
    public String home() {
        final String templateName = "home.jte";
        TemplateOutput output = new StringOutput();
        paperList.generateDummyData();
        page.setTitle("What to Read");
        page.setDescription("A simple web app to help you decide what to read next.");
        templateEngine.render(templateName, paperList, output);
        return output.toString();
    }
}
