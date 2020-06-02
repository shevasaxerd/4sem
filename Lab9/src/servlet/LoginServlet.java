package servlet;

import entity.ChatUser;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class LoginServlet extends ChatServlet {
    private static final long serialVersionUID = 1L;
    private int sessionTimeout = 10 * 60;

    public void init() throws ServletException {
        super.init();

        String value = getServletConfig().getInitParameter("SESSION_TIMEOUT");
        System.out.println(value);

        if (value != null) {
            sessionTimeout = Integer.parseInt(value);
        }

        getServletContext().setAttribute("i", 0);
        getServletContext().setAttribute("first", false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    boolean first = (boolean) getServletContext().getAttribute("first");
                    while (first) {
                        int i = (int) getServletContext().getAttribute("i");
                        getServletContext().setAttribute("i", ++i);
                        System.out.println(i);
                        first = (boolean) getServletContext().getAttribute("first");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = (String) request.getSession().getAttribute("name");

        String errorMessage = (String) request.getSession().getAttribute("error");

        String previousSessionId = null;

        if (name != null && !"".equals(name)) {
            getServletContext().setAttribute("i", 0);
            errorMessage = processLogonAttempt(name, request, response);
        }

        response.setCharacterEncoding("utf8");

        PrintWriter pw = response.getWriter();

        pw.println("<html><head><title>chat: log-in</title><link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">" +
                "<meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head><body class=\"w3-light-grey\">\n" +
                "<div class=\"w3-container w3-blue-grey w3-opacity w3-center-align\">\n" +
                "    <h1>Super chat!</h1>\n" +
                "</div>");

        if (errorMessage != null) {
            pw.println("<div class=\"w3-panel w3-green w3-display-container w3-card-4 w3-round\">\n" +
                    "   <span onclick=\"this.parentElement.style.display='none'\"\n" +
                    "   class=\"w3-button w3-margin-right w3-display-right w3-round-large w3-hover-green w3-border w3-border-green w3-hover-border-grey\">Г—</span>\n" +
                    "   <h5>" + errorMessage + "</h5>\n" +
                    "</div>");
        }

        pw.println("<div class=\"w3-card-4\">\n" +
                "        <div class=\"w3-container w3-center w3-green\">\n" +
                "        </div>\n" +
                "        <form method=\"post\" class=\"w3-selection w3-light-grey w3-padding\">\n" +
                "            <label>Name:\n" +
                "                <input type=\"text\" name=\"name\" class=\"w3-input w3-animate-input w3-border w3-round-large\" style=\"width: 30%\"><br />\n" +
                "            </label>\n" +
                "            <button type=\"submit\" class=\"w3-btn w3-green w3-round-large w3-margin-bottom\">Submit</button>\n" +
                "        </form>\n" +
                "    </div>");

        pw.println("</form></body></html>");

        request.getSession().setAttribute("error", null);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = (String) request.getParameter("name");

        String errorMessage = null;

        if (name == null || "".equals(name)) {
            errorMessage = "Р�РјСЏ РїРѕР»СЊР·РѕРІР°С‚РµР»СЏ РЅРµ РјРѕР¶РµС‚ Р±С‹С‚СЊ РїСѓСЃС‚С‹Рј";
        } else {
            errorMessage = processLogonAttempt(name, request, response);
        }

        if (errorMessage != null) {
            request.getSession().setAttribute("name", null);
            request.getSession().setAttribute("error", errorMessage);
            response.sendRedirect(response.encodeRedirectURL("/lab9/chat/login"));
        }
    }

    String processLogonAttempt(String name, HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String sessionId = request.getSession().getId();

        ChatUser aUser = activeUsers.get(name);

        if (aUser == null) {
            aUser = new ChatUser(name, Calendar.getInstance().getTimeInMillis(), sessionId);
            synchronized (activeUsers) {
                activeUsers.put(aUser.getName(), aUser);
            }
        }

        if (aUser.getSessionId().equals(sessionId) ||
                aUser.getLastInteractionTime() < (Calendar.getInstance().getTimeInMillis() - sessionTimeout * 1000)) {

            request.getSession().setAttribute("name", name);

            aUser.setLastInteractionTime(Calendar.getInstance().getTimeInMillis());

            Cookie sessionIdCookie = new Cookie("sessionId", sessionId);

            sessionIdCookie.setMaxAge(60 * 60 * 24 * 365);

            response.addCookie(sessionIdCookie);

            response.sendRedirect(response.encodeRedirectURL("/lab9/chat/messages"));
            return null;

        } else {
            return "Р�Р·РІРёРЅРёС‚Рµ, РЅРѕ РёСЏРј <strong>" + name + "</strong> СѓР¶Рµ" +
                    "РєРµРј-С‚Рѕ Р·Р°РЅСЏС‚Рѕ. РџРѕР¶Р°Р»СѓР№СЃС‚Р° РІС‹Р±РµСЂРёС‚Рµ РґСЂСѓРіРѕРµ РёРјСЏ!";
        }
    }

}
