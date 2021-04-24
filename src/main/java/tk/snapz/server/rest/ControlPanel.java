package tk.snapz.server.rest;

import com.ericrabil.yamlconfiguration.configuration.file.YamlConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.snapz.server.minecraft.spigot.SpigotServer;
import tk.snapz.server.minecraft.spigot.SpigotServers;
import tk.snapz.util.htmlutils.HtmlBuilder;
import tk.snapz.util.htmlutils.js.JavaScriptFunction;
import tk.snapz.util.htmlutils.js.JavaScriptModule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ComponentScan
@RestController
public class ControlPanel {

    @RequestMapping(value = "/cp")
    public Object controlpanelRM(HttpServletRequest request, HttpServletResponse response) {
        HtmlBuilder builder = new HtmlBuilder();
        StringBuilder sb = new StringBuilder();
        JavaScriptModule module = new JavaScriptModule("sub");
        JavaScriptFunction function = new JavaScriptFunction("btnFun");
        function.setAsync(true);
        for (SpigotServer spigotServer : SpigotServers.servers) {
            String status = "Offline";
            if(spigotServer.isOnline()) {
                status = "Online";
            }
            sb.append(spigotServer.serverName + " | Port: " + spigotServer.getPort() + " | Status: " + status + builder.getJSButton("stopserver", "Stop Server", function.getAjaxRequest("/stopserver?id=" + spigotServer.identifier, "")) +"<br>");
        }
        builder.setBody(sb.toString());
        module.addFunction(function);
        return builder.build();
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

    @RequestMapping(value = "/test")
    public Object testMapping () {
        HtmlBuilder builder = new HtmlBuilder();
        builder.addSleepFunction();
        builder.setBody(builder.newJSButton("alertbutton", "Button","alert(\"Hey\");") + "<p id=\"tID\"></p>");
        builder.addLibrary(HtmlBuilder.JavascriptLibrary.JQuery);
        JavaScriptModule module = new JavaScriptModule("main");
        JavaScriptFunction function = new JavaScriptFunction("testRedirect");
        function.setAsync(true);
        function.addRepeatingTask(function.getAjaxRequest("/cp", "" + function.getChangeInnerHtml("tID", function.ajaxResponse())), 3000);
        module.addFunction(function);
        builder.addJavascriptModule(module);
        builder.addJavascript("testRedirect();");
        return builder.build();
    }
}
