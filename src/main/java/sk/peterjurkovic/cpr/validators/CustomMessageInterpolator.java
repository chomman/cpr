package sk.peterjurkovic.cpr.validators;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;

public class CustomMessageInterpolator extends	ResourceBundleMessageInterpolator {
	
	private static final Pattern MESSAGE_PARAMETER_PATTERN = Pattern.compile( "(\\{[^\\}]+?\\})" );

    @Override
    public String interpolate(String message, Context context) {
        String resolvedMessage = super.interpolate(message, context);
        resolvedMessage = replacePropertyNameWithPropertyValues(resolvedMessage, context.getValidatedValue());
        return resolvedMessage;
    }

    private String replacePropertyNameWithPropertyValues(String resolvedMessage, Object validatedValue) {
        Matcher matcher = MESSAGE_PARAMETER_PATTERN.matcher( resolvedMessage );
        StringBuffer sb = new StringBuffer();

        while ( matcher.find() ) {
            String parameter = matcher.group( 1 );

            String propertyName = parameter.replace("{", "");
            propertyName = propertyName.replace("}", "");

            PropertyDescriptor desc = null;
            try {
                desc = new PropertyDescriptor(propertyName, validatedValue.getClass());
            } catch (IntrospectionException ignore) {
                matcher.appendReplacement( sb, parameter );
                continue;
            }

            try {
                Object propertyValue = desc.getReadMethod().invoke(validatedValue);
                matcher.appendReplacement( sb, propertyValue.toString() );
            } catch (Exception ignore) {
                matcher.appendReplacement( sb, parameter );
            }
        }
        matcher.appendTail( sb );
        return sb.toString();
    }
    
}
