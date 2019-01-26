package marketplaceserverapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * RedirectController is redirects users from to the market.
 *
 * @author Jason Liu
 */
@RestController
@RequestMapping("/")
public class RedirectController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getProducts() {
        return new ModelAndView("redirect:/market");
    }
}