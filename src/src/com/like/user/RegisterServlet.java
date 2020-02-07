package src.com.like.user;

import src.like.server.core.Request;
import src.like.server.core.Response;
import src.like.server.core.Servelt;

public class RegisterServlet implements Servelt {
    @Override
    public void service(Request request, Response response) {
        response.print("注册成功");
    }
}
