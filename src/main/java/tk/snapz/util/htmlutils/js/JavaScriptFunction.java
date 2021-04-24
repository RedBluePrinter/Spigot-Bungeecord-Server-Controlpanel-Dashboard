package tk.snapz.util.htmlutils.js;

import tk.snapz.util.ThreadSafeList;

public class JavaScriptFunction {
    public ThreadSafeList<String> jsfLines = null;
    public String name = "jsFun";
    public JavaScriptFunction(String name) {
        jsfLines = new ThreadSafeList<>();
    }

    private boolean isAsync = false;
    private String arguments = "";

    public void setAsync(boolean value) {
        isAsync = value;
    }

    public void addVariableAlert(String content) {
        jsfLines.add("alert(" + content + ");\n");
    }

    public void addStringAlert(String content) {
        jsfLines.add("alert(\"" + content + "\");\n");
    }

    public void addAjaxRequest(String url, String onSuccess) {
        jsfLines.add("$.ajax({ url: \"" + url + "\"}).done(function(response) {" + onSuccess + "});\n");
    }

    public String documentReWrite(String content) {
        return "document.open(); document.write(" + content + "); document.close();\n";
    }

    public String ajaxResponse() {
        return "response";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(isAsync) {
            sb.append("async ");
        }
        sb.append("function " + name +"(");
        sb.append(arguments);
        sb.append(") { ");
        for (String line : jsfLines) {
            sb.append(line);
        }
        sb.append("}");
        return sb.toString();
    }
}
