package src.like.server.core;
/*
服务器小脚本接口
 */
public interface Servelt {
    void service(Request request, Response response);
    //void doPost(Request request,Response response);
    //void doGet(Request request,Response response);
}
