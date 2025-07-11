package gg.jte.generated.ondemand;
import io.github.jonjohnsontc.whattoread.model.PaperList;
@SuppressWarnings("unchecked")
public final class JtehomeGenerated {
	public static final String JTE_NAME = "home.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,1,10,10,10,26,26,26,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, PaperList paperList) {
		jteOutput.writeContent("\n<div class=\"container\">\n    <h1>What to Read</h1>\n    <input type=\"text\" id=\"searchBar\" placeholder=\"Search papers...\" />\n    <div class=\"filter-container\">\n        <label for=\"filterRead\">Show Read Papers</label>\n        <input type=\"checkbox\" id=\"filterRead\" />\n    </div>\n    ");
		gg.jte.generated.ondemand.JtepaperListGenerated.render(jteOutput, jteHtmlInterceptor, paperList);
		jteOutput.writeContent("\n</div>\n\n<script>\n    let searchTimeout;\n    document.getElementById('searchBar').addEventListener('input', function() {\n        clearTimeout(searchTimeout);\n        searchTimeout = setTimeout(() => {\n            const query = this.value.toLowerCase();\n            const papers = document.querySelectorAll('.paper-item');\n            papers.forEach(paper => {\n                const title = paper.querySelector('.paper-title').textContent.toLowerCase();\n                paper.style.display = title.includes(query) ? '' : 'none';\n            });\n        }, 300);\n    });\n</script>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		PaperList paperList = (PaperList)params.get("paperList");
		render(jteOutput, jteHtmlInterceptor, paperList);
	}
}
