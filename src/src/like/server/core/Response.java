package src.like.server.core;
/*
1.动态添加内容print
2.累加字节数长度
3.根据状态码拼接响应头协议
4.根据状态码统一推送出去
 */
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Response {
    private BufferedWriter bw;
    //正文
    private StringBuilder content;
    //协议头(状态行，请求头，回车）信息
    private StringBuilder headinfo;
    private int len = 0;//正文字节数
    private final String BLANK = " ";
    private final String CRLF = "\r\n";
    public Response(){
        content = new StringBuilder();
        headinfo = new StringBuilder();
        len = 0;
    }
    public Response(Socket client){
        try {
            bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            headinfo = null;
        }
    }
    public Response(OutputStream os){
        bw = new BufferedWriter(new OutputStreamWriter(os));
    }

    //动态添加内容
    public Response print(String info){
        content.append(info);
        len+=info.getBytes().length;
        return this;
    }
    public Response println(String info){
        content.append(info).append(CRLF);
        len+=(info+CRLF).getBytes().length;
        return this;
    }

    //推送头信息
    public void pushToBrowser(int code) throws IOException {
        if(null ==headinfo)
            code=505;
        createHeadInfo(code);
        bw.append(headinfo);
        bw.append(content);
        bw.flush();
    }
    //构建头信息
    private void createHeadInfo(int code){
        //1.响应的状态行：HTTP/1.1 200 OK
        headinfo.append("HTTP/1.1").append(BLANK);
        headinfo.append(code).append(BLANK);
        switch (code){
            case 200:
                headinfo.append("OK").append(CRLF);
                break;
            case 404:
                headinfo.append("Not Found").append(CRLF);
                break;
            case 505:
                headinfo.append("Server Error").append(CRLF);
                break;
        }
        //2.响应头（最后一行存在空行—）
        headinfo.append("Date:").append(new Date()).append(CRLF);
        headinfo.append("Server").append("shsxt Server/0.0.1;charset=GBK").append(CRLF);
        headinfo.append("Content-type:text/html").append(CRLF);
        headinfo.append("Conten-length:").append(len).append(CRLF);
        headinfo.append(CRLF);
    }
}
