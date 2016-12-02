package dev.edu.javaee.spring.factory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import annotation.Autowired;
import dev.edu.javaee.spring.bean.BeanDefinition;
import annotation.Autowired;
import annotation.Component;
import dev.edu.javaee.spring.bean.BeanDefinition;
import dev.edu.javaee.spring.bean.BeanUtil;
import dev.edu.javaee.spring.bean.PropertyValue;
import dev.edu.javaee.spring.bean.PropertyValues;
import dev.edu.javaee.spring.resource.Resource;

public class Injection {
	public static void injectAurowired(List<String> idList,
			Map<String, BeanDefinition> beanDefinitionMap) {
		for (int i = 0; i < idList.size(); i++) {
			String name = idList.get(i);
			BeanDefinition bean = beanDefinitionMap.get(name);
			//实例
			Object beanInstance = bean.getBean();
			Field[] field = beanInstance.getClass().getDeclaredFields();

			if (field.length > 0) {
				for (Field beanField : field) {
					Autowired autowired = beanField
							.getAnnotation(Autowired.class);
					if (autowired != null) {
						Class<?> FieldClass = beanField.getType();
						Object FieldInstance = beanDefinitionMap.get(
								FieldClass.getSimpleName()).getBean();//map中获取
						if (FieldInstance != null) {
							beanField.setAccessible(true);
							try {
								beanField.set(beanInstance, FieldInstance); // 注入
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

}
