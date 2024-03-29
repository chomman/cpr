package cz.nlfnorm.web.taglib;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cz.nlfnorm.context.ContextHolder;
import cz.nlfnorm.enums.SystemLocale;
import cz.nlfnorm.utils.CodeUtils;

public class LocalizedValueTag extends TagSupport{

	private Logger logger = Logger.getLogger(getClass());
	
	private Object object;
	
	private String fieldName;

	@Override
	public int doStartTag() throws JspException {
		
		if(fieldName.equalsIgnoreCase("")) {
			logger.error("fieldName may not be Empty");
			return SKIP_BODY;
		}
		
		if(object instanceof String) {
			logger.error("Given object can not be instance of String");
			return SKIP_BODY;
		}

		if(object == null ) {
			return SKIP_BODY;
		}

		String retvalue = "";               
			try {
				retvalue = getMethodValue( buildMethodName() );
				if(StringUtils.isBlank(retvalue) && ContextHolder.getLang().equals(SystemLocale.EN.getCode())){
					retvalue = getMethodValue( buildCzechMethodName() );
				}
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1);
			} catch (Exception e) {
				e.printStackTrace();
			}               
			try {
				JspWriter out = this.pageContext.getOut();
				out.println(retvalue);
			} catch (IOException e) {
				this.logger.error(e);
			}
			
			return SKIP_BODY;
	}
	
	private String getMethodValue(final String methodName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Method method = object.getClass().getMethod( methodName );
		Object value =  method.invoke(object);
		if(value == null){
			return "";
		}else if(!(value instanceof String)){
			logger.error("JSP error. Objects " + object.getClass().getName() + " methods: " + methodName + " returns invalid type.");
			return "";
		}
		return (String)value;
	}
	
	private String buildMethodName(){
		StringBuilder sb = new StringBuilder("get");
		sb.append(CodeUtils.firstCharacterUp(fieldName));
		sb.append(ContextHolder.getLangName());
		return sb.toString();
	}
	
	private String buildCzechMethodName(){
		StringBuilder sb = new StringBuilder("get");
		sb.append(CodeUtils.firstCharacterUp(fieldName));
		sb.append(SystemLocale.CZ.getLangName());
		return sb.toString();
	}
	
	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	
	
}
