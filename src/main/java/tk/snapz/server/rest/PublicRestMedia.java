package tk.snapz.server.rest;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
            if(request.getServletPath().endsWith(".jpg")) {
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            }
            if(request.getServletPath().endsWith(".mp3")) {
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            }
            try {
                IOUtils.copy(new FileReader(fileResource), response.getOutputStream());
            } catch (IOException ioException) {
                return "<h1>404</h1><hr><p>The requested File was not Found!</p>";
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
