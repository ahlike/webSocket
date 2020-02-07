package src.like.server.core;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class WebApp {
    private static WebContext webContext;
    static {
        try {
            //SAX解析
            //1.获取解析工厂
            SAXParserFactory factory=SAXParserFactory.newInstance();
            //2.从解析工厂获取解析器
            SAXParser parse = factory.newSAXParser();
            //3.编写处理器
            //4.加载文档Document注册处理器
            WebHandler handler = new WebHandler();
            //5.解析
            parse.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("web.xml"),handler);
            //获取数据
            webContext = new WebContext(handler.getEntitys(),handler.getMappings());
        }catch (Exception e){
            System.out.println("解析配置文件错误");
        }
    }
    //通过url或许配置文件对应的Servlet
    public static Servelt getServletFromUrl(String url){
        //假设输入/login
        String classname = WebContext.getClz("/"+url);
        Class clz = null;
        try {
            clz = Class.forName(classname);
            Servelt servelt=(Servelt)clz.getConstructor().newInstance();
            return servelt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
