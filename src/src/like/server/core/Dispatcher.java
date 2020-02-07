package src.like.server.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Dispatcher implements Runnable {
    private Socket client;
    private Response response;
    private Request request;
    public Dispatcher(Socket client){
        this.client=client;
        try {
            //获取请求协议
            request = new Request(client);
            //获取响应协议
            response = new Response(client);
        } catch (IOException e) {
            e.printStackTrace();
            this.release();
        }
    }
    @Override
    public void run() {
        try {
            if(null == request.getUrl() || request.getUrl().equals("")){
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("index.html");
                response.print(new String(is.readAllBytes()));
                response.pushToBrowser(200);
                is.close();
                return;
            }
            Servelt servelt = WebApp.getServletFromUrl(request.getUrl());
            if(null!=servelt){
                servelt.service(request,response);
                //关注了状态码
                response.pushToBrowser(200);
            }else{
                //错误
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("error.html");
                response.print(new String(is.readAllBytes()));
                response.pushToBrowser(404);
            }
        }catch (Exception e){
            try {
                response.pushToBrowser(505);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        release();
    }
    //释放资源
    private void release(){
        try {
            client.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
