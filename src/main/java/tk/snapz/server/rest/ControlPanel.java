package tk.snapz.server.rest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
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
        StringBuilder sb = new StringBuilder();
        for (SpigotServer spigotServer : SpigotServers.servers) {
            String status = "Offline";
            if(spigotServer.isOnline()) {
                status = "Online";
            }
            sb.append(spigotServer.serverName + " | Port: " + spigotServer.getPort() + " | Status: " + status);
        }
        return sb.toString();
    }

    @RequestMapping(value = "/test")
    public Object testMapping () {
        HtmlBuilder builder = new HtmlBuilder();
        builder.setBody(builder.newJSButton("alertbutton", "Button","alert(\"Hey\");"));
        builder.addLibrary(HtmlBuilder.JavascriptLibrary.JQuery);
        JavaScriptModule module = new JavaScriptModule("main");
        JavaScriptFunction function = new JavaScriptFunction("testRedirect");
        function.setAsync(true);
        function.addStringAlert("HI");
        function.addAjaxRequest("https://google.com", function.documentReWrite(function.ajaxResponse()));
        module.addFunction(function);
        builder.addJavascriptModule(module);
        return builder.build();
    }
}
