package src.like.server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 目标：多线程处理加入分发器
 */
public class Server {
    private ServerSocket serverSocket;
    private boolean isRunning;
    public static void main(String[] args) {
        Server server=new Server();
        server.start();
    }
    //启动服务
    public void start(){
        try {
            serverSocket = new ServerSocket(8888);
            isRunning = true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }

    }
    //接受连接处理
    public void receive(){
        while (isRunning){
            try {
                Socket client=serverSocket.accept();
                System.out.println("一个客户端建立了连接");
                //多线程处理
                new Thread(new Dispatcher(client)).start();
                //获取请求协议
                //Request request = new Request(client);
                Request request = new Request(client);
                //获取响应协议
                Response response = new Response(client);
                Servelt servelt = WebApp.getServletFromUrl(request.getUrl());
                if(null!=servelt){
                    servelt.service(request,response);
                    //关注了状态码
                    response.pushToBrowser(200);
                }else{
                    //错误
                    response.pushToBrowser(404);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("客户端错误");
            }
        }


    }
    //停止连接
    public void stop(){
        isRunning = false;
        try {
            this.serverSocket.close();
            System.out.println("服务器已停止");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
