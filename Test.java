/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author User
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map<String,String> myMap = new HashMap<String,String>();
        for (int i=0;i<1000;i++) 
          myMap.put(String.valueOf(i), "value");
        System.out.println(expandedToString(myMap));
    }
    
    public static<T,V> String expandedToString(Map<T,V> map) {
        String toReturn = "";
        try {
            Field f = map.getClass().getDeclaredField("table");
            f.setAccessible(true);
            Entry[] t = (Entry[])f.get(map);
            Class.forName("java.util.HashMap$Node").getDeclaredFields()[3].setAccessible(true);
            for (Entry e: t) {
                Entry tempEntry = e;
                while (tempEntry!=null) {
                    toReturn+=tempEntry+", ";
                    Field next = Class.forName("java.util.HashMap$Node").getDeclaredFields()[3];
                    next.setAccessible(true);
                    tempEntry = (Entry)next.get(tempEntry);
                }
                if (e!=null) toReturn+="\n";
            }
        }
        catch (NoSuchFieldException nse) {
           System.out.println(nse.getMessage());
        }
        catch (ClassNotFoundException cne) {
           System.out.println(cne.getMessage());     
        }
        catch (IllegalAccessException iae) {
           System.out.println(iae.getMessage()); 
        }
        return toReturn;
    }
}
