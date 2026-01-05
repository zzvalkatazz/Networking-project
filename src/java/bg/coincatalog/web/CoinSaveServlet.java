package bg.coincatalog.web;

import bg.coincatalog.dao.CoinDAO;
import bg.coincatalog.model.Coin;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name="CoinSaveServlet", urlPatterns={"/coin-save"})
@MultipartConfig
public class CoinSaveServlet extends HttpServlet {

    private File uploadDir() {
        
        File dir = new File(System.getProperty("user.dir"), "uploaded-images");
        if (!dir.exists()) dir.mkdirs();
        return dir;
    }

    private String savePart(Part part) throws Exception {
        if (part == null || part.getSize() == 0) return null;

      
        String ct = part.getContentType();
        if (ct == null || !ct.toLowerCase().startsWith("image/")) return null;

        String submitted = part.getSubmittedFileName();
        String ext = "";
        if (submitted != null && submitted.lastIndexOf('.') > 0) {
            ext = submitted.substring(submitted.lastIndexOf('.')).toLowerCase();
        }
        
        if (!(ext.equals(".png") || ext.equals(".jpg") || ext.equals(".jpeg") || ext.equals(".gif") || ext.equals(".webp"))) {
            ext = ".jpg"; 
        }

        String fileName = UUID.randomUUID().toString().replace("-", "") + ext;
        File outFile = new File(uploadDir(), fileName);

        try (InputStream in = part.getInputStream()) {
            Files.copy(in, outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return fileName;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            Coin c = new Coin();
            c.setType(request.getParameter("type"));
            c.setCountry(request.getParameter("country"));
            c.setDenomination(request.getParameter("denomination"));
            c.setCurrency(request.getParameter("currency"));

            String yearStr = request.getParameter("coinYear");
            c.setCoinYear(Integer.parseInt(yearStr));
            c.setNotes(request.getParameter("notes"));

          
            String frontFile = savePart(request.getPart("frontFile"));
            String backFile  = savePart(request.getPart("backFile"));

            c.setImageFront(frontFile);
            c.setImageBack(backFile);
           

            new CoinDAO().insert(c);
            response.sendRedirect("coins");
        } catch (Exception ex) {
            throw new ServletException("Save error: " + ex.getMessage(), ex);
        }
    }
}
