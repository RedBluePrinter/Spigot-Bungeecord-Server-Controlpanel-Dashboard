package tk.snapz.util.htmlutils;

import tk.snapz.util.ThreadSafeList;
import tk.snapz.util.TwoPartObject;
import tk.snapz.util.htmlutils.js.JavaScriptModule;

public class HtmlBuilder {
    private ThreadSafeList<String> styles = new ThreadSafeList();
    private ThreadSafeList<String> javascripts = new ThreadSafeList();
    private String head = "";
    private String body = "";
    public String htmlStyle = "";
    public String headStyle = "";
    public String bodyStyle = "";
    private String libraries = "";
    public String build() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html style\"" + htmlStyle + "\">");

        html.append("<head style=\"" + headStyle + "\">");
        html.append(libraries);
        html.append(head);
        html.append("<style>");
        for(String style : styles) {
            html.append(style);
        }
        html.append("</style>");
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
    public void addLibrary(JavascriptLibrary library) {
        if(library.equals(JavascriptLibrary.JQuery)) {
            libraries += "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\n";
        }
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
    public void addJavascriptModule(JavaScriptModule module) {
        for (TwoPartObject function : module.getFunctions()) {
            this.addJavascript(function.part2.toString());
        }
    }
    public void addStylesheet(String style) {
        this.styles.add(style);
    }
    public String newJSButton(String name, String displayText,String javascript) {
        jsBtnNum++;
        name = name.replace(" ", "").trim();
        this.addJavascript("function onClick" + jsBtnNum + name + "() {" + javascript + "}");
        return "<button onclick=\"onClick" + jsBtnNum + name + "();\">" + displayText + "</button>";
    }

    public String getJSButton(String c, String name, String displayText, String javascript) {
        jsBtnNum++;
        name = name.replace(" ", "").trim();
        return "<button class=\"" + c + "\" onclick='" + javascript + "'>" + displayText + "</button>";
    }

    int jsBtnNum = 0;

    public void addSleepFunction() {
        addJavascript("function sleep(n) { return new Promise(resolve=>setTimeout(resolve,n)); }");
    }

    public enum JavascriptLibrary {
        JQuery
    }
}
