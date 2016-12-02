package dev.edu.javaee.spring.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dev.edu.javaee.spring.bean.BeanDefinition;

public abstract class AbstractBeanFactory implements BeanFactory{
	public Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
	
	public Object getBean(String beanName)
	{
		return this.beanDefinitionMap.get(beanName).getBean();
	}
	
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
	{
		beanDefinition = GetCreatedBean(beanDefinition); //制造bean
		this.beanDefinitionMap.put(beanName, beanDefinition);
	}
	
	protected abstract BeanDefinition GetCreatedBean(BeanDefinition beanDefinition);
}
