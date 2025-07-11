package gg.jte.generated.ondemand;
import io.github.jonjohnsontc.whattoread.model.PaperListEntry;
@SuppressWarnings("unchecked")
public final class JtepaperListEntryGenerated {
	public static final String JTE_NAME = "paperListEntry.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,1,4,4,4,4,5,5,5,6,6,6,7,7,7,8,8,8,8,9,9,9,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, PaperListEntry entry) {
		jteOutput.writeContent("\n<div class=\"paper-list-entry\">\n    <h2>");
		jteOutput.setContext("h2", null);
		jteOutput.writeUserContent(entry.getTitle());
		jteOutput.writeContent("</h2>\n    <span>Authors: ");
		jteOutput.setContext("span", null);
		jteOutput.writeUserContent(String.join(",", entry.getAuthors()));
		jteOutput.writeContent("</span>\n    <span>Year: ");
		jteOutput.setContext("span", null);
		jteOutput.writeUserContent(entry.getYear());
		jteOutput.writeContent("</span>\n    <span>Rating: ");
		jteOutput.setContext("span", null);
		jteOutput.writeUserContent(entry.getRating());
		jteOutput.writeContent("</span>\n    <span><a href=\"/paper/");
		jteOutput.setContext("a", "href");
		jteOutput.writeUserContent(entry.getId());
		jteOutput.setContext("a", null);
		jteOutput.writeContent("\">Read More</a></span>\n</div>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		PaperListEntry entry = (PaperListEntry)params.get("entry");
		render(jteOutput, jteHtmlInterceptor, entry);
	}
}
