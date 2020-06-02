package servlet;

import com.sun.xml.internal.ws.addressing.WsaActionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class PhotoServlet extends HttpServlet {
    private JFileChooser fileChooser;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = new PhotoFrame().go();
        System.out.println(path);

        getServletContext().setAttribute("photo", path);

        System.out.println(1);

        resp.sendRedirect("/lab9/updateAd.jsp");
    }

    public class PhotoFrame extends JFrame {
        public PhotoFrame() {
        }

        public String go() {

            if (fileChooser == null) {
                fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
            }
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                return fileChooser.getSelectedFile().toString();
            }

            return "";
        }
    }
}