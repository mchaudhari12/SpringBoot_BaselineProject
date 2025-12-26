package org.studyeasy.SpringStarter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.studyeasy.SpringStarter.Model.Account;
import org.studyeasy.SpringStarter.Service.accountService;


@Controller
public class AccountController {

    @Autowired
    private accountService accountService;

    @PostMapping("/register")
    public String register_user(@ModelAttribute Account account){
        accountService.save(account);
        return "account-view/redirect:/";
    }

   @GetMapping("/register")
   public String register(Model model){
        Account account = new Account();
        model.addAttribute("account", account);
        return "account-view/register";
   } 

   @GetMapping("/login")
   public String login(Model model){
       return "account-view/login";
   }
   
   @GetMapping("/profile")
   public String profile(Model model){
       return "account-view/profile";
   }


}
