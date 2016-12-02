package dev.edu.javaee.spring.bean;

public class PropertyValue {
	public PropertyValue(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public PropertyValue(String name,String ref){
		this.name=name;
		this.ref=ref;
	}
	
	
	private String name;
	
	private Object value;
	
	private String ref="";

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
}
