package MVC;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
	private String ViewName;
	Map<String,String> map1 = new HashMap<String,String>();
	Map<String,String> map2 = new HashMap<String,String>();
	public void setViewName(String vname) {
		ViewName = vname;
	}
	public String getViewName(){
		return ViewName;
	}

	public String getMap(String key) {
		return map1.get(key);
	}
	public void putMap(String key, String value){
		map1.put(key, value);
	}
	public void addObject(String key, String value) {
		map2.put(key, value);
	}

}
