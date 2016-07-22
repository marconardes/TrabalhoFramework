package org.cbsoft.framework;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.spi.TransactionalWriter;

public class FileSerializer {

	private PostProcessor pp;
	private DataFormatter df;
	
	
	
	public FileSerializer(PostProcessor pp, DataFormatter df) {
		super();
		this.pp = pp;
		this.df = df;
	}

	public void generateFile(String filename, Object obj) {
		byte[] bytes = df.formatData( getPropertiesList(obj));
		
	    try {
	    	bytes = pp.postProcess(bytes);
			FileOutputStream fileout = new FileOutputStream(filename);
			fileout.write(bytes);
			fileout.close();
		} catch (Exception e) {
			throw new RuntimeException("Problems writing the file",e);
		}
	}
	
	private Map<String,Object> getPropertiesList(Object obj)
	{
		Map<String,Object> props = new HashMap<String, Object>();
		
		Class<?> clazz = obj.getClass();
		
		for(Method method: clazz.getMethods())
		{
			if(isAllowedGetter(method))
			{
				try {
					Object value = method.invoke(obj);
					String getterName = method.getName();
					String propName = getterName.substring(3, 4).toLowerCase()+
							getterName.substring(4);
					props.put(propName, value);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new RuntimeException("Cannot retrieve properties",e);
				}
			}
		}
		
		return props;
	}

	private boolean isAllowedGetter(Method method) {
		return method.getName().startsWith("get")&&
				method.getParameterTypes().length ==0 &&
				method.getReturnType()!=void.class&&
				!method.getName().equals("getClass")&&
				!method.isAnnotationPresent(DontIncludeOnFile.class);
	}
}