package src.com.like.user;

import src.like.server.core.Request;
import src.like.server.core.Response;
import src.like.server.core.Servelt;

public class LoginServlet implements Servelt {
    @Override
    public void service(Request request, Response response) {
        response.print("<html>");
        response.print("<head>");
        response.print("<title>");
        response.print("第一个servlet");
        response.print("</title>");
        response.print("</head>");
        response.print("<body>");
        response.print("终于回来了"+request.getParameter("uname"));
        response.print("</body>");
        response.print("</html>");    }
}
