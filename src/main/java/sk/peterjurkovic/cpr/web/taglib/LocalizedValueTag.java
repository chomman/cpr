package sk.peterjurkovic.cpr.web.taglib;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import sk.peterjurkovic.cpr.context.ContextHolder;
import sk.peterjurkovic.cpr.utils.CodeUtils;

public class LocalizedValueTag extends TagSupport{

	private Logger logger = Logger.getLogger(getClass());
	
	private static final long serialVersionUID = 1L;

	private Object object;
	
	private String fieldName;

	@Override
	public int doStartTag() throws JspException {
		if(fieldName.equalsIgnoreCase("")) {
			throw new JspException("fieldName may not be Empty");
		}
		if(object instanceof String) {
			throw new JspException("really, asking me for a value and dont give me an Object? Kidding?");
		}

		if(object == null ) {
			throw new JspException("really, asking me for a value and dont give me an Object? Kidding?");
		}

		String retvalue = "";               
			try {
				Method method = object.getClass().getMethod( buildMethodName() );   
				retvalue = (String) method.invoke(object);       
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
	
	private String buildMethodName(){
		StringBuilder sb = new StringBuilder("get");
		sb.append(CodeUtils.firstCharacterUp(fieldName));
		sb.append(ContextHolder.getLangName());
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
