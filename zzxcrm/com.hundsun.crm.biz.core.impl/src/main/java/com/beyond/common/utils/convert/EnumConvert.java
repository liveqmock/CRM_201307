package com.beyond.common.utils.convert;

import org.dozer.CustomConverter;
import org.springframework.stereotype.Component;

@Component
public class EnumConvert implements CustomConverter {
	
	@SuppressWarnings("unchecked")
	public Object convert(Object destination, Object source, Class destClass,
			Class sourceClass) {
		if (source == null) {
			return null;
		}
		
		if(Enum.class.isAssignableFrom(sourceClass) && String.class == destClass){
			return source.toString();
		}
		
		if(String.class == sourceClass && Enum.class.isAssignableFrom(destClass)){
			return Enum.valueOf(destClass, source.toString());
		}
		
		return null;
	}
}
