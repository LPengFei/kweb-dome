package com.cnksi.taglib;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据缓存工具，用于{@link KValueTag}中缓存字段关联查询出来的值
 * Created by xyl on 2016/12/9, 009.
 */
public class KDataCache {
    private static final Map<Object, Object> map = new HashMap<>();

    @SuppressWarnings("unchecked")
	public static <T> T getOrAdd(Object key, Object value) {
        Object valueCached = map.get(key);
        return valueCached != null ? (T) valueCached : null;
    }

    @SuppressWarnings("unchecked")
	public static <T> T get(Object key){
        return (T) map.get(key);
    }

    public static void put(Object key, Object value){
        map.put(key, value);
    }

    @SuppressWarnings("rawtypes")
	public static Map getMap(){
        return map;
    }

    public static void clear(){
        map.clear();
    }

    /*static class Element{
        private Object key;
        private Object value;

        public Element() { }

        public Element(Object key, Object value) {
            this.value = value;
            this.key = key;
        }

        public Object getKey() {
            return key;
        }

        public void setKey(Object key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }*/
}
