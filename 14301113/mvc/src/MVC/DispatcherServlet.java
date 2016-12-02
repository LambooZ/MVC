package MVC;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import test.test;
import dev.edu.javaee.spring.factory.BeanFactory;
import dev.edu.javaee.spring.factory.Scanning;
import dev.edu.javaee.spring.factory.XMLBeanFactory;
import dev.edu.javaee.spring.resource.LocalFileResource;
import annotation.RequestMapping;

public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public DispatcherServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	    
		String action = request.getServletPath(); //hello
		ModelAndView mav=new ModelAndView();
		Enumeration<?> enu=request.getParameterNames(); //获得页面输入
		String url = null;
		while(enu.hasMoreElements()){  
			String name=(String)enu.nextElement();  
			String value = request.getParameter(name);
			mav.putMap(name,value);
		}
		LocalFileResource resource = new LocalFileResource(this.getClass().getResource("/").getPath() + "/bean.xml");
		BeanFactory beanFactory = null;
		try {
			beanFactory = new XMLBeanFactory(resource,"test");
		} catch (NoSuchFieldException | SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		test test = (test) beanFactory.getBean("test");

		Method[] methods = test.getClass().getDeclaredMethods();//方法
		for (Method method : methods){
					RequestMapping RM = method.getAnnotation(RequestMapping.class);//找到
					String value = RM.value(); //hello		
					if(value.equals(action)){
						ModelAndView mav1;
						try {
							mav1 = (ModelAndView) method.invoke(test,mav);
						url = mav1.getViewName();
						List<String> keys = new ArrayList<String>(mav1.map2.keySet());
						for(String key : keys){
							request.setAttribute(key, mav1.map2.get(key));
						}
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			
	    
	    request.getRequestDispatcher(url+".jsp").forward(request,response);
	}


	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
