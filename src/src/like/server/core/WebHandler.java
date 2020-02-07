package src.like.server.core;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/*
处理器
 */
public class WebHandler extends DefaultHandler {
    private List<Entity> entitys;
    private List<Mapping> mappings;
    private Entity entity;
    private Mapping mapping;
    private String tag;//存储操作标签
    private boolean isMapping = false;
    @Override
    public void startDocument() throws SAXException {
        entitys = new ArrayList<Entity>();
        mappings = new ArrayList<Mapping>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //System.out.println(qName+"-->解析开始");
        if(qName!=null){
            tag=qName;//存储标签名
            if (tag.equals("servlet")) {
                entity = new Entity();
                isMapping = false;
            }else if(tag.equals("servlet-mapping")){
                mapping = new Mapping();
                isMapping = true;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String contents = new String(ch, start, length).trim();
        if (tag != null) {
            if(isMapping){//操作Mapping
                if (tag.equals("servlet-name")) {
                    mapping.setName(contents);
                } else if (tag.equals("url-pattern")) {
                    mapping.addPattern(contents);
                }
            }else {//操作Servelt
                if (tag.equals("servlet-name")) {
                    entity.setName(contents);
                } else if (tag.equals("servlet-class")) {
                    entity.setClz(contents);
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //System.out.println(qName+"->>解析结束");
        if(qName!=null) {
            if (qName.equals("servlet")) {
                entitys.add(entity);
            }else if(qName.equals("servlet-mapping")){
                mappings.add(mapping);
            }
            tag = null;
        }
    }

    public List<Entity> getEntitys() {
        return entitys;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }

}