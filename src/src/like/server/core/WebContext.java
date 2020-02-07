package src.like.server.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebContext {
    private List<Entity> entities = null;
    private List<Mapping>mappings = null;

    //key->servlet-name value->selvlet-calss
    private static Map<String,String> entityMap = new HashMap<String,String>();
    //key->url-pattern value->servlet-name
    private static Map<String,String> mappingMap = new HashMap<String, String>();

    public WebContext(List<Entity> entities, List<Mapping> mappings) {
        this.entities = entities;
        this.mappings = mappings;
        //将entity转成Map
        for(Entity entity:entities){
            entityMap.put(entity.getName(),entity.getClz());
        }
        //将Mapping转成Map
        for(Mapping mapping:mappings){
            for(String pattern:mapping.getPatterns()){
                mappingMap.put(pattern,mapping.getName());
            }
        }
    }

    public static String getClz(String pattern){
        String name = mappingMap.get(pattern);
        return entityMap.get(name);
    }
}
