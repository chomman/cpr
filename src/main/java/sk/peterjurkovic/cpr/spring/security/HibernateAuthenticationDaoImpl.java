package sk.peterjurkovic.cpr.spring.security;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import sk.peterjurkovic.cpr.entities.User;
import sk.peterjurkovic.cpr.services.UserService;

/**
 * Trida ziska uzivatele z databaze, pokud existuje s odpovidajicimi prihlasovacimi udaji
 * 
 * @author Petr Matulik, Stanislav Hybasek
 */

@Repository("hibernateAuthenticationDaoImpl")
public class HibernateAuthenticationDaoImpl implements UserDetailsService {

    /** logger */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    UserService userService;

    /**
     * Na zaklade uzivatelskeho jmena vrati implemantaci rozhrani UserDetails. V nasi aplikaci toto rozhrani implementuje trida
     * <code>User</code>, ktera predstavuje uzivatele systemu.
     * 
     * @param username
     *            {@link String}: uzivatelske jmeno
     * @return nalezena reprezentace uzivatele podle uzivatelskeho jmena.
     * @throws UsernameNotFoundException
     *             pokud uzivatelske jmeno neni v databazi nebo daby uzivatel nema potrebne bezpecnostne opravneni
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        if (user.getAuthorities().isEmpty()) {
            throw new UsernameNotFoundException("User has no GrantedAuthority");
        }

        return (UserDetails)user;
    }
   
}
