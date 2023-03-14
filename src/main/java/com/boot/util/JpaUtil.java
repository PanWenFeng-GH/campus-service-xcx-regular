package com.boot.util;
import java.beans.PropertyDescriptor;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

import com.boot.entity.User;


public class JpaUtil {
    public static void copyNotNullProperties(Object src,Object target){
        BeanUtils.copyProperties(src,target,getNotNullPropertyNames(target));
    }

    public static void main(String[] args) {
    	User u = new User();
    	u.setId(2);
    	String[] s = getNullPropertyNames(u);
    	for(String d:s) {
    		System.out.println(d);
    	}
    	User u2 = new User();
    	u2.setId(2);
    	u2.setName("22");
    	
    	BeanUtils.copyProperties(u2,u,getNotNullPropertyNames(u));
    	System.out.println("==================");
    	System.out.println(u.getName());
	}
    
    private static String[] getNullPropertyNames(Object object) {
        final BeanWrapperImpl wrapper = new BeanWrapperImpl(object);
        return Stream.of(wrapper.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(propertyName -> wrapper.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
    
    private static String[] getNotNullPropertyNames(Object object) {
        final BeanWrapperImpl wrapper = new BeanWrapperImpl(object);
        return Stream.of(wrapper.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(propertyName -> wrapper.getPropertyValue(propertyName) != null)
                .toArray(String[]::new);
    }
}