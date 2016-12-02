package dev.edu.javaee.spring.factory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import annotation.Component;
import dev.edu.javaee.spring.bean.BeanDefinition;

public class ImplementComponent {
	//实现@Component
	public static void ImplementComponent(Set<Class<?>> classSet,
			List<String> idList, Map<String, BeanDefinition> beanDefinitionMap) {
		Set<Class<?>> ComponentClass = new HashSet<Class<?>>();
		for (Class<?> cls : classSet) {
			// 找出有@Component的类
			try {
				Class<?> c = Class.forName(cls.getName());
				Component component = c.getAnnotation(Component.class);
				if (component != null) {
					ComponentClass.add(cls);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		for (Class<?> classes : ComponentClass) {//遍历
			Class<?> beanClass = classes;
			BeanDefinition beanDefine = new BeanDefinition();
			beanDefine.setBeanClass(beanClass); // 放入class
			beanDefine.setBeanClassName(beanClass.getName()); // 放入class地址

			try {
				beanDefine.setBean(Class
						.forName(beanClass.getName().toString()).newInstance()); // 放入bean的实例
				beanDefinitionMap.put(beanClass.getSimpleName(), beanDefine); // 放入Map
				idList.add(beanClass.getSimpleName());

			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
