package gg.jte.generated.ondemand;
import io.github.jonjohnsontc.whattoread.model.PaperList;
import io.github.jonjohnsontc.whattoread.model.PaperListEntry;
@SuppressWarnings("unchecked")
public final class JtepaperListGenerated {
	public static final String JTE_NAME = "paperList.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,2,6,6,6,8,8,10,10,12,12,12,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, PaperList paperList) {
		jteOutput.writeContent("\n<div class=\"list-container\">\n    <ul class=\"paper-list\">\n        ");
		for (PaperListEntry entry : paperList.getPapers()) {
			jteOutput.writeContent("\n            <li class=\"paper-list-entry-list-item\">\n                ");
			gg.jte.generated.ondemand.JtepaperListEntryGenerated.render(jteOutput, jteHtmlInterceptor, entry);
			jteOutput.writeContent("\n            </li>\n        ");
		}
		jteOutput.writeContent(" \n    </ul>\n</div>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		PaperList paperList = (PaperList)params.get("paperList");
		render(jteOutput, jteHtmlInterceptor, paperList);
	}
}
