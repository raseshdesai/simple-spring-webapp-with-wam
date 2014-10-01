package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import weblogic.security.Security;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Set;

/**
 *  Created by Rasesh Desai on 3/26/14.
 */
@Controller
public class SimpleController {

     @RequestMapping (value = "/", method = RequestMethod.GET)
    public String greeting(Model model){
        String wamName = locateUserId();
        System.out.println("In simle-wam controller, WAM Name: " + wamName);
        model.addAttribute("name", wamName);
        return "greeting";
    }

    public String locateUserId() {
        Subject subject = getSubject();
        Set<Principal> principals = subject.getPrincipals();
        System.out.println("# of principals found: " + principals.size());

        // There may be more than one principal.  If so, the one we want
        // will be the first one.
        String userId = null;
        if (!principals.isEmpty()) {
            userId = principals.iterator().next().getName();
        }
        if (userId != null) {
            userId = userId.toUpperCase();
        }
        System.out.println("WAM User ID found: '" + userId + "'");
        return userId;
    }

    private Subject getSubject() {
        return Security.getCurrentSubject();
    }
}
