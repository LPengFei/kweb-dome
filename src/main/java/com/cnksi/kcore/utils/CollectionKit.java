package com.cnksi.kcore.utils;

import java.util.Collection;
import java.util.Map;

public class CollectionKit {

	private CollectionKit() {}

	public static boolean empty(Collection<?> coll) {
		return !notEmpty(coll);
	}
	public static boolean notEmpty(Collection<?> coll) {
		return coll != null && !coll.isEmpty();
	}
	
	public static boolean notEmpty(Map<?, ?> map){
		return map != null && !map.isEmpty(); 
	}

}
