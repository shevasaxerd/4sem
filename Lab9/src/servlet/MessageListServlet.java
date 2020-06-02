package servlet;

import entity.ChatMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MessageListServlet extends ChatServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        getServletContext().setAttribute("first", true);

        int j = (int) getServletContext().getAttribute("i");
        if (j >= 10) {
            response.sendRedirect("/lab9/chat/sendJoke");
        }

        response.setCharacterEncoding("utf8");

        PrintWriter pw = response.getWriter();

        pw.println("<html><head><meta http-equiv='Content-Type'" +
                " content='text/html; charset=utf-8'/><meta http-equiv='refresh'" +
                " content='10'><title>chat: messages</title>" +
                "<link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">" +
                "</head>" +
                "<body class=\"w3-light-grey\">\n" +
                "<div class=\"w3-container w3-blue-grey w3-opacity w3-center-align\">\n" +
                "    <h1>Super chat!</h1>\n" +
                "</div>" +
                "<div class=\"w3-container w3-center w3-margin-bottom w3-padding\">\n" +
                "    <div class=\"w3-card-4\">\n" +
                "        <div class=\"w3-container w3-light-blue\">\n" +
                "            <h2>Messages</h2>\n" +
                "        </div>");


        for (int i = messages.size() - 1; i >= 0; i--) {
            ChatMessage aMessage = messages.get(i);
            pw.println("<ul class=\"w3-ul\">");
            pw.println("<li class=\"w3-hover-sand\">" + aMessage.getAuthor().getName() + ": "
                    + aMessage.getMessage() + "</li>");
            pw.println("</ul>");
        }

        pw.println("</div>\n" +
                "</div>" +
                        "<div class=\"w3-card-4\">\n" +
                        "        <div class=\"w3-container w3-center w3-green\">\n" +
                        "        </div>\n" +
                        "        <form action='/lab9/chat/sendMessage' method=\"post\" class=\"w3-selection w3-light-grey w3-padding\">\n" +
                        "            <label>Message:\n" +
                        "                <input type=\"text\" name=\"message\" class=\"w3-input w3-animate-input w3-border w3-round-large\" style=\"width: 30%\"><br />\n" +
                        "            </label>\n" +
                        "            <button type=\"submit\" class=\"w3-btn w3-green w3-round-large w3-margin-bottom\">Send message</button>\n" +
                        "        </form>\n" +
                        "    </div>");

        pw.println("<div class=\"w3-container w3-grey w3-opacity w3-right-align w3-padding\">\n" +
                "    <button class=\"w3-btn w3-round-large\" onclick=\"location.href='/lab9/chat/exit'\">Exit</button>\n" +
                "</div>");

        pw.println("</body></html>");
    }
}
