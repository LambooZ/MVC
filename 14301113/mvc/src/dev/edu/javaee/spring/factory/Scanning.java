package dev.edu.javaee.spring.factory;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import annotation.Autowired;

public class Scanning {
	//private static Set<Class<?>> Class_Set;
	//搜索包下面所有的类
	public static Set<Class<?>> GetClass(String packname) {
		Set<Class<?>> classset = new HashSet<>();
		String packagename = packname;
		String packageDirName = packagename.replace(".", "/");
		Enumeration<URL> dirs = null;

		try {
			dirs = Thread.currentThread().getContextClassLoader()
					.getResources(packageDirName);
			// 迭代出Enumeration
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				//String root = System.getProperty("user.dir") + "\\src\\"+ packname;
				String root = "B:\\编程\\WorkPlace\\mvc\\src\\" +packname; //包路径
				File file = new File(root);
				// 把此目录下所有文件列出
				String[] classes = file.list();
				// 循环此数组，并把.class去掉
				for (String classname : classes) {
					classname = classname.substring(0, classname.length() - 5);
					// 拼接上包名
					String Name = packagename + "." + classname;
					// 如有需要，把每个类生成一个实例
					Class<?> cls = Class.forName(Name);
					// 加入
					classset.add(cls);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return classset;
	}

}
