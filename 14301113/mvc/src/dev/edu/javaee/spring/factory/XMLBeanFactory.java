package dev.edu.javaee.spring.factory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import annotation.Autowired;
import annotation.Component;
import dev.edu.javaee.spring.bean.BeanDefinition;
import dev.edu.javaee.spring.bean.BeanUtil;
import dev.edu.javaee.spring.bean.PropertyValue;
import dev.edu.javaee.spring.bean.PropertyValues;
import dev.edu.javaee.spring.resource.Resource;

public class XMLBeanFactory extends AbstractBeanFactory {

	// private String xmlPath;
	List<String> idList = new ArrayList<String>();

	public XMLBeanFactory(Resource resource, String packname)
			throws NoSuchFieldException, SecurityException {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		//classSet = Scanning.GetClass(packname);
		/*
		 * for (Iterator<Class<?>> it = classSet.iterator();it.hasNext();){
		 * Class beanClass = it.next(); System.out.println(beanClass.getName());
		 * }
		 */

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			Document document = dbBuilder.parse(resource.getInputStream());
			NodeList beanList = document.getElementsByTagName("bean");

			for (int i = 0; i < beanList.getLength(); i++) {
				Node bean = beanList.item(i);
				BeanDefinition beandef = new BeanDefinition();
				String beanClassName = bean.getAttributes()
						.getNamedItem("class").getNodeValue();
				String beanName = bean.getAttributes().getNamedItem("id")
						.getNodeValue(); // id
				idList.add(beanName);

				beandef.setBeanClassName(beanClassName);

				try {
					Class<?> beanClass = Class.forName(beanClassName);
					beandef.setBeanClass(beanClass);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				PropertyValues propertyValues = new PropertyValues();

				NodeList propertyList = bean.getChildNodes();
				for (int j = 0; j < propertyList.getLength(); j++) {
					Node property = propertyList.item(j);
					if (property instanceof Element) {
						Element ele = (Element) property;

						String name = ele.getAttribute("name");

						if (ele.getAttribute("ref").equals("")) { 

							Class<?> type;
							try {
								type = beandef.getBeanClass()
										.getDeclaredField(name).getType();
								Object value = ele.getAttribute("value");

								if (type == Integer.class) {
									value = Integer.parseInt((String) value);
								}

								propertyValues
										.AddPropertyValue(new PropertyValue(
												name, value));

							} catch (NoSuchFieldException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SecurityException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else {
							String RefBeanName = ele.getAttribute("ref");
							propertyValues.AddPropertyValue(new PropertyValue(
									name, RefBeanName));

						}

					}

				}
				beandef.setPropertyValues(propertyValues);

				this.registerBeanDefinition(beanName, beandef); // beanName id

				
			}
			/**
			 **
			 **
			 **
			 **
			 **
			 **/
			/*********** 实现ref的方法 ********/
			//ImplementRef.ImplementRef(idList, beanDefinitionMap);
			/**
			 **
			 **
			 **
			 **
			 **
			 **/

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ImplementComponent.ImplementComponent(classSet, idList,beanDefinitionMap);
		Injection.injectAurowired(idList, beanDefinitionMap); // 实现ref的注入
	}

	@Override
	protected BeanDefinition GetCreatedBean(BeanDefinition beanDefinition) {

		try {
			// set BeanClass for BeanDefinition

			Class<?> beanClass = beanDefinition.getBeanClass();
			// set Bean Instance for BeanDefinition
			Object bean = beanClass.newInstance();

			List<PropertyValue> fieldDefinitionList = beanDefinition
					.getPropertyValues().GetPropertyValues();
			for (PropertyValue propertyValue : fieldDefinitionList) {
				if (propertyValue.getRef() == null
						|| propertyValue.getRef().equals("")) {
					BeanUtil.invokeSetterMethod(bean, propertyValue.getName(),
							propertyValue.getValue());
				}

			}

			beanDefinition.setBean(bean);

			return beanDefinition;

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
