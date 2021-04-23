package tk.snapz.server.rest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RestController
public class PublicRestMedia implements ErrorController {

    @RequestMapping(value = "/error")
    public Object htmlError() {
        return "<h1>404</h1><hr><p>The requested Page was not Found!</p>";
    }

    @GetMapping(value = "/public/media/**")
    public Object getHttpMedia(HttpServletRequest request, HttpServletResponse response) {
        File fileResource = new File(request.getServletPath());
        if(fileResource.exists()) {
            if(request.getServletPath().endsWith(".png")) {
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
            }
        } else {
            return "<h1>404</h1><hr><p>The requested File was not Found!</p>";
        }
        return null;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
