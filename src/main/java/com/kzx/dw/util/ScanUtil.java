/**
* @Title: ScanUtil.java
* @Description: TODO(扫描指定路径下面的文件)
* @author tovin/xutaota2003@163.com 
* @date 2014年8月11日 下午4:23:00 
 */
package com.kzx.dw.util;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ScanUtil {
	/**
	* Class File 过滤器
	*/
	public static interface ClassFileFilter {
	 
	/**
	* 过滤磁盘文件
	*/
	public boolean accept(String klassName, File file, ClassLoader loader);
	 
	/**
	* 过滤 Jar 包中的文件
	*/
	public boolean accept(String klassName, JarFile jar, JarEntry entry, ClassLoader loader);
	}
	 

	 
	/**
	* 从指定 package 中获取所有的 Class
	*
	* @param packageName 包名
	* @param recursive 是否递归查找
	* @param filter class 过滤器
	* @return 所有找到的 Class
	*/
	public static Set<Class<?>> getClasses(String packageName, boolean recursive, ClassFileFilter filter) {
		if (packageName == null || packageName.length() == 0) {
			throw new IllegalArgumentException("packageName is empty.");
		}
	 
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String packageDirName = packageName.replace('.', '/');
		Collection<URL> urls;
		try {
			Enumeration<URL> dirs = loader.getResources(packageDirName);
			urls = Collections.list(dirs);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	 
		Set<Class<?>> klasses = new LinkedHashSet<Class<?>>();
		for (URL url : urls) {
			String protocol = url.getProtocol();
			if ("file".equals(protocol)) {
				lookupClassesInFileSystem(packageName, new File(url.getFile()), recursive, loader, filter, klasses);
			} else if ("jar".equals(protocol)) {
				lookupClassesInJar(packageName, url, recursive, loader, filter, klasses);
			}
		}
		return klasses;
	}
	 
	/**
	* 以文件的形式来获取包下的所有 Class
	*
	* @param packageName JAVA包名
	* @param packagePath 包所在的文件目录
	* @param recursive 是否递归查找
	* @param loader 负责该包的 ClassLoader
	* @param filter class 过滤器
	* @param klasses 返回找到的 class
	*/
	private static void lookupClassesInFileSystem(String packageName, File packagePath, final boolean recursive, ClassLoader loader, ClassFileFilter filter, Set<Class<?>> klasses) {
		if (!packagePath.exists() || !packagePath.isDirectory()) {
			return;
		}
		File[] dirfiles = packagePath.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
		});
	 
		String packageNamePrefix = "";
		if (packageName != null && packageName.length() > 0) {
			packageNamePrefix = packageName + '.';
		}
		for (File file : dirfiles) {
			if (file.isDirectory()) {
				lookupClassesInFileSystem(packageNamePrefix + file.getName(), file, recursive, loader, filter, klasses);
			} else {
				// 去掉后面的 .class 只留下类名
				String klassName = packageNamePrefix + file.getName().substring(0, file.getName().length() - 6);
				try {
					if (filter == null || filter.accept(klassName, file, loader)) {
						Class<?> klass = loader.loadClass(klassName);
						klasses.add(klass);
					}
				} catch (Throwable e) {
				}
			}
		}
	}
	 
	/**
	* 以在 Jar 包中获取指定包下的所有 Class
	*
	* @param packageName JAVA包名
	* @param jarUrl Jar 包文件对应的 URL
	* @param recursive 是否递归查找
	* @param loader 负责该包的 ClassLoader
	* @param filter class 过滤器
	* @param klasses 返回找到的 class
	*/
	private static void lookupClassesInJar(String packageName, URL jarUrl, boolean recursive, ClassLoader loader, ClassFileFilter filter, Set<Class<?>> klasses) {
		String packageDirName = "";
		if (packageName != null && packageName.length() > 0) {
			packageDirName = packageName.replace('.', '/') + '/';
		}
		 
		JarFile jar = null;
		try {
			if ("jar".equals(jarUrl.getProtocol())) {
				jar = ((JarURLConnection) jarUrl.openConnection()).getJarFile();
			} else {
				jar = new JarFile(jarUrl.getFile());
			}
			Enumeration<JarEntry> entries = jar.entries();
			while (entries.hasMoreElements()) {
			// 获取jar里的一个实体 可以是目录和一些jar包里的其他文件 如META-INF等文件
				JarEntry entry = entries.nextElement();
				if (entry.isDirectory()) {
					continue;
				}
			 
				String name = entry.getName();
				if (name.charAt(0) == '/') {
					name = name.substring(1);
				}
				if (name.startsWith(packageDirName) && name.endsWith(".class")) {
					if (name.lastIndexOf('/') > packageDirName.length()) {
					// 在子包内
						if (!recursive) continue;
					}
					// 去掉后面的 .class 只留下类名
					String klassName = name.substring(0, name.length() - 6);
					klassName = klassName.replace('/', '.');
					try {
						if (filter == null || filter.accept(klassName, jar, entry, loader)) {
							Class<?> klass = loader.loadClass(klassName);
							klasses.add(klass);
							}
						} catch (Throwable e) {
					}
				}
			}
		} catch (IOException e) {
			
		} finally {
					
		}
	}
	
	public static void main(String[] args) {
		Set<Class<?>> classSet = ScanUtil.getClasses("com.kzx.dw.controller", false, null);
		for(Class cl : classSet)
		{
			String name = cl.getSimpleName();
			String action = cl.getSimpleName().substring(0, cl.getSimpleName().length()-10).toLowerCase();
			
			System.out.println("action=" + action);
		}
	}
}
