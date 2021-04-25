package tk.snapz.server.rest;

import com.ericrabil.yamlconfiguration.configuration.file.YamlConfiguration;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import tk.snapz.server.minecraft.spigot.SpigotServer;
import tk.snapz.server.minecraft.spigot.SpigotServers;
import tk.snapz.util.ThreadSafeList;
import tk.snapz.util.htmlutils.HtmlBuilder;
import tk.snapz.util.htmlutils.js.JavaScriptFunction;
import tk.snapz.util.htmlutils.js.JavaScriptModule;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@ComponentScan
@RestController
public class ControlPanel {

    @RequestMapping(value = "/srvl")
    public Object controlpanelRM(HttpServletRequest request, HttpServletResponse response, @Nullable @CookieValue String token) {
        HtmlBuilder builder = new HtmlBuilder();
        StringBuilder sb = new StringBuilder();
        JavaScriptModule module = new JavaScriptModule("sub");
        JavaScriptFunction function = new JavaScriptFunction("btnFun");
        function.setAsync(true);
        for (SpigotServer spigotServer : SpigotServers.servers) {
            spigotServer.check();
            if(!spigotServer.isTerminated()) {
                String status = "Offline";
                if (spigotServer.isOnline()) {
                    status = "Online";
                }
                sb.append(spigotServer.serverName + " | Port: " + spigotServer.getPort() + " | Status: " + status + builder.getJSButton("rby", "reloadserver", "Reload Server", function.getAjaxRequest("/reloadserver?id=" + spigotServer.identifier, "")) + builder.getJSButton("sbr", "stopserver", "Stop Server", function.getAjaxRequest("/stopserver?id=" + spigotServer.identifier, "")) + "<br>");
            }
        }
        builder.setBody(sb.toString());
        module.addFunction(function);
        return builder.build();
    }

    public static ThreadSafeList<String> sessionTokens = new ThreadSafeList<>();

    @RequestMapping(value = "/rqtok")
    public Object requestToken(HttpServletResponse response, @Nullable @CookieValue String token, @Nullable @RequestParam String t) {
        if(t == null) {
            Cookie cookie = new Cookie("user", null); // Not necessary, but saves bandwidth.
            cookie.setPath("/MyApplication");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0); // Don't set to -1 or it will become a session cookie!
            response.addCookie(cookie);
            return new RedirectView("/rqtok?t=rqtl");
        } else {
            String newToken = RandomStringUtils.random(128, true, true);
            sessionTokens.add("TK-" + newToken + "-");
            Cookie cookie = new Cookie("token", newToken);
            cookie.setMaxAge(300);
            response.addCookie(cookie);
            return new RedirectView("/cp/");
        }
    }

    @RequestMapping(value = "/stopserver")
    public Object serverStop(HttpServletRequest request, HttpServletResponse response, @RequestParam String id) {
        for (SpigotServer spigotServer : SpigotServers.servers) {
            if(spigotServer.identifier.equals(id)) {
                YamlConfiguration command = new YamlConfiguration();
                command.set("cmd", "stop");
                spigotServer.client.send(command.saveToString());
                return null;
            }
        }
        return null;
    }

    @RequestMapping(value = "/reloadserver")
    public Object serverReload(HttpServletRequest request, HttpServletResponse response, @RequestParam String id) {
        for (SpigotServer spigotServer : SpigotServers.servers) {
            if(spigotServer.identifier.equals(id)) {
                YamlConfiguration command = new YamlConfiguration();
                command.set("cmd", "reload");
                spigotServer.client.send(command.saveToString());
                return null;
            }
        }
        return null;
    }

    @RequestMapping(value = "/cp/")
    public Object testMapping () {
        HtmlBuilder builder = new HtmlBuilder();
        builder.addSleepFunction();
        //builder.setBody(builder.newJSButton("alertbutton", "Button","alert(\"Hey\");") +
        builder.setHead("<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\">\n" +
                "<link href=\"https://fonts.googleapis.com/css2?family=Rubik&display=swap\" rel=\"stylesheet\"> ");
        builder.addStylesheet(
                "p {" +
                "font-family: 'Rubik', sans-serif; font-size: 36; padding: 3px; margin: 3px; background-color: #c6d3d4; color: #474747; line-height: 1.2;}" +
                "ul {" +
                "display:table-row;\n" +
                "  list-style-type: none;\n" +
                "  margin: 0;\n" +
                "  padding: 0;\n" +
                "  overflow: hidden;\n" +
                "  background-color: #333;\n" +
                "}\n" +
                "li {\n" +
                "  float: left;\n" +
                "}\n" +
                "li a {\n" +
                "  font-family: 'Rubik', sans-serif;" +
                "  display: block;\n" +
                "  color: white;\n" +
                "  text-align: center;\n" +
                "  padding: 14px 16px;\n" +
                "  text-decoration: none;\n" +
                "}\n" +
                "li a:hover:not(.active) {\n" +
                "  background-color: #111;\n" +
                "}\n" +
                ".active {\n" +
                "  background-color: #4CAF50;\n" +
                "}" +
                        ".i {" +
                        "padding: 5px;" +
                        "top: 55px;" +
                        "bottom: 0px;" +
                        "right: 0px;" +
                        "left: 0px;" +
                        "position: absolute;" +
                        "display: flex;" +
                        "width: 100%;" +
                        "margin-left: 15%;" +
                        "}" +
                        ".sbr {" +
                        "margin: 0%;" +
                        "position: relative;" +
                        "max-height: 5%;" +
                        "transform: translate(5px, 0px);\n" +
                        "border-radius: 4px;" +
                        "background-color: #e65f5f;" +
                        "border-color: #e65f5f;" +
                        "}" +
                ".rby {" +
                        "margin: 0%;" +
                        "position: relative;" +
                        "max-height: 5%;" +
                        "transform: translate(5px, 0px);\n" +
                        "border-radius: 4px;" +
                        "background-color: #d8e857;" +
                        "border-color: #d8e857;" +
                        "}" +
                ".left {\n" +
                        "  float: left;\n" +
                        "  width: 30%;\n" +
                        "}\n" +
                        ".main {\n" +
                        "  float: left;\n" +
                        "  width: 70%;\n" +
                        "}" +
                        " /* I like the w3schools design! IM GONNA KEEP IT! */\n" +
                        " /* The side navigation menu */\n" +
                        ".sidebar {\n" +
                        "  margin: 0;\n" +
                        "  padding: 0;\n" +
                        "  width: 200px;\n" +
                        "  background-color: #f1f1f1;\n" +
                        "  position: fixed;\n" +
                        "  height: 100%;\n" +
                        "  overflow: auto;\n" +
                        "}\n" +
                        "\n" +
                        "/* Sidebar links */\n" +
                        ".sidebar a {\n" +
                        "  display: block;\n" +
                        "  color: black;\n" +
                        "  padding: 16px;\n" +
                        "  text-decoration: none;\n" +
                        "}\n" +
                        "\n" +
                        "/* Active/current link */\n" +
                        ".sidebar a.active {\n" +
                        "  background-color: #4CAF50;\n" +
                        "  color: white;\n" +
                        "}\n" +
                        "\n" +
                        "/* Links on mouse-over */\n" +
                        ".sidebar a:hover:not(.active) {\n" +
                        "  background-color: #555;\n" +
                        "  color: white;\n" +
                        "}\n" +
                        "\n" +
                        "/* Page content. The value of the margin-left property should match the value of the sidebar's width property */\n" +
                        "div.content {\n" +
                        "  margin-left: 200px;\n" +
                        "  padding: 1px 16px;\n" +
                        "  height: 1000px;\n" +
                        "}\n" +
                        "\n" +
                        "/* On screens that are less than 700px wide, make the sidebar into a topbar */\n" +
                        "@media screen and (max-width: 700px) {\n" +
                        "  .sidebar {\n" +
                        "    width: 100%;\n" +
                        "    height: auto;\n" +
                        "    position: relative;\n" +
                        "  }\n" +
                        "  .sidebar a {float: left;}\n" +
                        "  div.content {margin-left: 0;}\n" +
                        "}\n" +
                        "\n" +
                        "/* On screens that are less than 400px, display the bar vertically, instead of horizontally */\n" +
                        "@media screen and (max-width: 400px) {\n" +
                        "  .sidebar a {\n" +
                        "    text-align: center;\n" +
                        "    float: none;\n" +
                        "  }\n" +
                        "} "
        );
        builder.setBody(
                        "<div class=\"sidebar\">\n" +
                        "<a class=\"active\" href=\"/cp/\">Servers</a>\n" +
                        "<a href=\"/cp/acc/\">Account</a>\n" +
                        "<a href=\"/cp/log/\">Log</a>\n" +
                        "<a href=\"/cp/conf/\">Settings</a>\n" +
                        "</div>\n" +
                        "<div class=\"content\">\n" +
                        "<p id=\"tID\"></p>\n" +
                        "</div>"
        );
        builder.bodyStyle = "margin: 0px; padding: 0px; height: 100%; display:table; width:100%;";
        builder.addLibrary(HtmlBuilder.JavascriptLibrary.JQuery);
        JavaScriptModule module = new JavaScriptModule("main");
        JavaScriptFunction function = new JavaScriptFunction("testRedirect");
        function.setAsync(true);
        function.addRepeatingTask(function.getAjaxRequest("/srvl/", "" + function.getChangeInnerHtml("tID", function.ajaxResponse())), 3000);
        module.addFunction(function);
        builder.addJavascriptModule(module);
        builder.addJavascript("testRedirect();");
        return builder.build();
    }
}
