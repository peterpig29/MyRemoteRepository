package peter.tool;

public class ContinuousPictureHTMLFileGeneratorConstant {
	public final static String HTML_FROM_BEGIN_TO_TITLE_OF_WEB_PAGE = new StringBuilder("<!DOCTYPE html>\n")
		.append("<html>\n")
		.append("<head>\n")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n")
		.append("<title>").toString();
	public final static String HTML_FROM_TITLE_OF_WEB_PAGE_TO_AMOUNT_OF_PICTURE = new StringBuilder("</title>\n")
		.append("<style>\n")
		.append(".putCenterByTextAlign{\n")
		.append("text-align:center;\n")
		.append("}\n")
		.append(".putCenterByDisplayAndMargin{\n")
		.append("display: block;\n")
		.append("margin: 0 auto;\n")
		.append("}\n")
		.append(".background1kkk{\n")
		.append("background-color: #b8d8e8;\n")
		.append("}\n")
		.append(".backgroundMangaMeeyaCE{\n")
		.append("background-color: #000000;\n")
		.append("}\n")
		.append(".backgroundCDisplayEx{\n")
		.append("background-color: #464646;\n")
		.append("}\n")
		.append("</style>\n")
		.append("<script type=\"text/javascript\">\n")
		.append("var body;\n")
		.append("\n")
		.append("window.onload = function () {\n")
		.append("body = document.getElementsByTagName(\"body\")[0];\n")
		.append("\n")
		.append("document.getElementById(\"background1kkkButton\").onclick = changeBackgroundTo1kkk;\n")
		.append("document.getElementById(\"backgroundMangaMeeyaCEButton\").onclick = changeBackgroundToMangaMeeyaCE;\n")
		.append("document.getElementById(\"backgroundCDisplayExButton\").onclick = changeBackgroundToCDisplayEx;\n")
		.append("document.getElementById(\"backgroundWhiteButton\").onclick = changeBackgroundToWhite;\n")
		.append("}\n")
		.append("function changeBackgroundTo1kkk(){\n")
		.append("body.className = \"background1kkk putCenterByTextAlign\";\n")
		.append("}\n")
		.append("function changeBackgroundToMangaMeeyaCE(){\n")
		.append("body.className = \"backgroundMangaMeeyaCE putCenterByTextAlign\";\n")
		.append("}\n")
		.append("function changeBackgroundToCDisplayEx(){\n")
		.append("body.className = \"backgroundCDisplayEx putCenterByTextAlign\";\n")
		.append("}\n")
		.append("function changeBackgroundToWhite(){\n")
		.append("body.className = \"putCenterByTextAlign\";\n")
		.append("}\n")
		.append("</script>\n")
		.append("</head>\n")
		.append("<body class=\"background1kkk putCenterByTextAlign\">\n")
		.append("<span>共").toString();
	public final static String HTML_FROM_AMOUNT_OF_PICTURE_TO_PICTURE = new StringBuilder("張圖</span>\n")
		.append("<br>\n")
		.append("<input type=\"button\" id=\"background1kkkButton\" value=\"淺藍色(1kkk)\">\n")
		.append("<input type=\"button\" id=\"backgroundMangaMeeyaCEButton\" value=\"黑色(MangaMeeyaCE)\">\n")
		.append("<input type=\"button\" id=\"backgroundCDisplayExButton\" value=\"灰色(CDisplayEx)\">\n")
		.append("<input type=\"button\" id=\"backgroundWhiteButton\" value=\"白色\">\n").toString();
	public final static String HTML_FROM_IMG_TAG_BEGIN_TO_SRC_PROPERTY = "<img class=\"putCenterByDisplayAndMargin\" src=\"";
	public final static String HTML_FROM_IMG_SRC_PROPERTY_TO_IMG_TAG_END = "\">\n";
	public final static String HTML_FROM_PICTURE_END_TO_HTML_END = new StringBuilder("</body>\n").append("</html>").toString();	
}