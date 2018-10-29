package damian.kcal.dzienniczek.controller;

import damian.kcal.dzienniczek.model.User;
import damian.kcal.dzienniczek.model.Weight;
import damian.kcal.dzienniczek.service.UserService;
import damian.kcal.dzienniczek.service.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class WeightController {

//    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
//    public List<User> getAllUsers() {return userService.findAllUsers();}

    //@RequestMapping(value = "/user/weight", method = RequestMethod.GET, produces = "application/json")
    //public List<User> getAllUsers() {return userService.findAllUsers();}

    @Autowired
    private UserService userService;

    @Autowired
    private WeightService weightService;

    @RequestMapping(value= {"/user/weight"}, method=RequestMethod.GET)
    @ResponseBody
    public String plaintext(HttpServletResponse response) {

//       User user = new User();
//       user.setFirstname("Damian");
//       user.setLastname("Tracz");
//       user.setEmail("damian@damian.pl");
//       user.setPassword("1qazXSW@");
//       userService.saveUser(user);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        Weight weight = new Weight();
        weight.setWeight(80);
        weight.setUser(user);
        weightService.saveWeight(weight);



        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return "TEXT";
    }

}
