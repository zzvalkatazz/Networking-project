package bg.coincatalog.web;

import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/img")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        if (name == null || name.trim().isEmpty()) { resp.sendError(400); return; }

        
        if (name.contains("..") || name.contains("/") || name.contains("\\")) { resp.sendError(400); return; }

        File dir = new File(System.getProperty("user.dir"), "uploaded-images");
        File file = new File(dir, name);
        if (!file.exists()) { resp.sendError(404); return; }

        String lower = name.toLowerCase();
        if (lower.endsWith(".png")) resp.setContentType("image/png");
        else if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) resp.setContentType("image/jpeg");
        else if (lower.endsWith(".gif")) resp.setContentType("image/gif");
        else if (lower.endsWith(".webp")) resp.setContentType("image/webp");
        else resp.setContentType("application/octet-stream");

        try (InputStream in = new FileInputStream(file);
             OutputStream out = resp.getOutputStream()) {

            byte[] buf = new byte[8192];
            int r;
            while ((r = in.read(buf)) != -1) out.write(buf, 0, r);
        }
    }
}
