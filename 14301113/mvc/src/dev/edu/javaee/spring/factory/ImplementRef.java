package dev.edu.javaee.spring.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.edu.javaee.spring.bean.BeanDefinition;
import dev.edu.javaee.spring.bean.BeanUtil;
import dev.edu.javaee.spring.bean.PropertyValue;

public class ImplementRef {
	public static void ImplementRef(List<String> idList,Map<String, BeanDefinition> beanDefinitionMap) {
		for (int i = 0; i < idList.size(); i++) {
			BeanDefinition bean = beanDefinitionMap.get(idList.get(i));
			List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
			propertyValues = bean.getPropertyValues().GetPropertyValues();

			for (int j = 0; j < propertyValues.size(); j++) {
				PropertyValue property = propertyValues.get(j);
				// 属性中有ref
				if (property.getRef() != "") {
					String refBeanName = property.getRef();

					Object ref = beanDefinitionMap.get(refBeanName).getBean();
					// 注入
					BeanUtil.invokeSetterMethod(bean.getBean(),
							property.getName(), ref);
				}
			}
		}
	}
}
