package damian.kcal.dzienniczek.controller;

import damian.kcal.dzienniczek.model.Makro;
import damian.kcal.dzienniczek.model.User;
import damian.kcal.dzienniczek.model.Weight;
import damian.kcal.dzienniczek.service.UserService;
import damian.kcal.dzienniczek.service.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
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
    public ModelAndView weight() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        Weight weight = new Weight();

        List<Weight> weightList = new ArrayList<Weight>();
        weightList = weightService.findByUser(user);

        model.addObject("userName", user.getFirstname() + " " + user.getLastname());
        model.addObject("userWeights", weightList);
        model.addObject("weight", weight);
        model.setViewName("user/weight");
        return model;
    }


    @RequestMapping(value= {"/user/weight"}, method=RequestMethod.POST)
    public ModelAndView addWeight(@Valid Weight weight, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        User userExists = userService.findUserByEmail(user.getEmail());

        if(userExists != null) {

            //weight.setWeight(80);
            weight.setUser(user);
            weightService.saveWeight(weight);

            List<Weight> weightList = new ArrayList<Weight>();
            weightList = weightService.findByUser(user);

            model.addObject("userName", user.getFirstname() + " " + user.getLastname());
            model.addObject("userWeights", weightList);
            model.addObject("weight", weight);
            model.setViewName("user/weight");
        }
        return model;
    }


    @RequestMapping(value= {"/user/weight/{weightId}"}, method=RequestMethod.DELETE)
    @ResponseBody
    public String deleteWeight(HttpServletResponse response, @PathVariable int weightId) {

       // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       // User user = userService.findUserByEmail(auth.getName());

        //User userExists = userService.findUserByEmail(user.getEmail());

        weightService.deleteById(weightId);
        System.out.println("delete method");

        //if(userExists != null) {

            //weight.setWeight(80);
            //weight.setUser(user);

            //weightService.deleteById(weightId);
            //System.out.println("delete method");

            //List<Weight> weightList = new ArrayList<Weight>();
            //weightList = weightService.findByUser(user);

            //model.addObject("userName", user.getFirstname() + " " + user.getLastname());
            //model.addObject("userWeights", weightList);
            //model.addObject("weight", weight);
            //model.setViewName("user/weight");
        //}
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        return "TEXT";
    }





    @RequestMapping(value= {"/user/weight1"}, method=RequestMethod.GET)
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


    @RequestMapping(value= {"/user/weight2"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Weight> getAllWeights2() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        return weightService.findAllWeights();
    }

    @RequestMapping(value= {"/user/weights"}, method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Weight> getAllWeights3() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        return weightService.findByUser(user);
    }

}
