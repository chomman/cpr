package sk.peterjurkovic.cpr.web.sitemeth;

import com.opensymphony.module.sitemesh.Config;
import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.DecoratorMapper;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.mapper.AbstractDecoratorMapper;
import com.opensymphony.module.sitemesh.mapper.ConfigLoader;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

/**
 * 
 * Default implementation of DecoratorMapper. Class is an improvement of Sitemesh ConfigDecoratorMapper.java for purposes of using Sitemesh
 * in projects with MVC architecture. Uses Spring Framework.
 * 
 * @version 1.0 2006/12/12
 * @since Sitemesh 2.4
 * 
 * @author <a href="joe@truemesh.com">Joe Walnes</a>
 * @author <a href="mcannon@internet.com">Mike Cannon-Brookes</a>
 * @version 2.1.1 2004/10/08
 * 
 * @see com.opensymphony.module.sitemesh.mapper.ConfigDecoratorMapper
 * @see com.opensymphony.module.sitemesh.DecoratorMapper
 * @see com.opensymphony.module.sitemesh.mapper.DefaultDecorator
 * @see com.opensymphony.module.sitemesh.mapper.ConfigLoader
 * 
 */

public class ConfigDecoratorMapperForMVCProjects extends AbstractDecoratorMapper {

    private ConfigLoader configLoader = null;


    /** Create new ConfigLoader using '/WEB-INF/decorators.xml' file. */
    public void init(Config config, Properties properties, DecoratorMapper parent) throws InstantiationException {

        super.init(config, properties, parent);

        try {
            String fileName = properties.getProperty("config", "/WEB-INF/decorators.xml");
            configLoader = new ConfigLoader(fileName, config);
        } catch (Exception e) {
            throw new InstantiationException(e.toString());
        }

    }


    /**
     * Retrieve {@link com.opensymphony.module.sitemesh.Decorator} based on 'pattern' tag.
     */
    public Decorator getDecorator(HttpServletRequest request, Page page) {

        /**
         * Spring Framework helper class
         */
        UrlPathHelper urlPathHelper = new UrlPathHelper();

        /**
         * Is a part of URL from project context to the end of the URL
         * 
         * (instead of a part of URL, which was returned by request.getServletPath() in a original distribution of Sitemesh)
         */
        String thisPath = urlPathHelper.getPathWithinApplication(request);

        if (thisPath == null) {
            String requestURI = request.getRequestURI();
            if (request.getPathInfo() != null) {
                thisPath = requestURI.substring(0, requestURI.indexOf(request.getPathInfo()));
            } else {
                thisPath = requestURI;
            }
        }

        String name = null;
        try {
            name = configLoader.getMappedName(thisPath);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        Decorator result = getNamedDecorator(request, name);

        return result == null ? super.getDecorator(request, page) : result;

    }


    /**
     * Retrieve Decorator named in 'name' attribute. Checks the role if specified.
     */
    public Decorator getNamedDecorator(HttpServletRequest request, String name) {
        Decorator result = null;
        try {
            result = configLoader.getDecoratorByName(name);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        if (result == null || (result.getRole() != null && !request.isUserInRole(result.getRole()))) {
            // if the result is null or the user is not in the role
            return super.getNamedDecorator(request, name);
        } else {
            return result;
        }
    }

}
