package tk.snapz.util.htmlutils;

import tk.snapz.util.ThreadSafeList;

public class HtmlBuilder {
    private ThreadSafeList<String> styles = new ThreadSafeList();
    private ThreadSafeList<String> javascripts = new ThreadSafeList();
    private String head = "";
    private String body = "";
    private String htmlStyle = "";
    private String headStyle = "";
    private String bodyStyle = "";
    public String build() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html style\"" + htmlStyle + "\">");

        html.append("<head style=\"" + headStyle + "\">");
        html.append(head);
        html.append("</head>");

        html.append("<body style=\"" + bodyStyle + "\">");
        html.append(body);
        for (String javascript : javascripts) {
            html.append("<script>");
            html.append(javascript);
            html.append("</script>");
        }
        html.append("</body>");

        html.append("</html>");
        return html.toString();
    }
    public void setHead(String head) {
        this.head = head;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public void addJavascript(String javascript) {
        this.javascripts.add(javascript);
    }
    public void addStylesheet(String style) {
        this.styles.add(style);
    }
    public String newJSButton(String name, String displayText,String javascript) {
        name = name.replace(" ", "").trim();
        this.addJavascript("function onClick" + name + "() {" + javascript + "}");
        return "<button onclick=\"onClick" + name + "();\">" + displayText + "</button>";
    }
}
