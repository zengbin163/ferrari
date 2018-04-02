package com.home.ferrari.util.access;

import java.util.Map;

public class ParamMapUtil {
    public static Integer getInitParameter(Map<String,Object> map,String key,Integer defaultValue){
        if(null==map){
            return defaultValue;
        }
       
        int value = 0;
        try{
             value = (int)map.get(key);
         }catch(Exception e){
             value = defaultValue;
         }
        return value;
    }
    
    
    public static Long getLongParameter(Map<String,Object> map,String key,Long defaultValue){
        if(null==map){
            return defaultValue;
        }
        String value = (String)map.get(key);
        Long retValue = null;
        if(value!=null && !"".equals(value)){
            try{
                retValue = Long.parseLong(value);
            }catch(Exception e){
                retValue = defaultValue;
            }
        }else{
            retValue = defaultValue;  
        }
        
        return retValue;
    }
    
    public static String getStrParameter(Map<String,Object> map,String key,String defaultValue){
        if(null==map){
            return defaultValue;
        }
        String value = (String)map.get(key);
        String retValue = null;
        if(value!=null && !"".equals(value)){
            retValue = value;
        }else{
            retValue = defaultValue;  
        }
        
        return retValue;
    }
}

