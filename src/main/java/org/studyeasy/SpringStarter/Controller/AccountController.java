package org.studyeasy.SpringStarter.Controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.studyeasy.SpringStarter.Model.Account;
import org.studyeasy.SpringStarter.Service.accountService;
import org.studyeasy.SpringStarter.util.Constant.AppUtils;

@Controller
public class AccountController {

    @Autowired
    private accountService accountService;

    // @Value("${spring.mvc.static-path-pattern}")
    // private String photo_prefix;

    @GetMapping("/register")
    public String register(Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        return "account-view/register";
    }

    @PostMapping("/register")
    public String register_user(@Valid @ModelAttribute Account account, BindingResult result) {
        if (result.hasErrors()) {
            return "account-view/register";
        }
        accountService.save(account);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "account-view/login";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model, Principal principle) {
        String authUser = "email";
        if (principle != null) {
            authUser = principle.getName();
        }
        Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            model.addAttribute("account", account);
            model.addAttribute("photo", account.getPhoto());
            return "account-view/profile";
        } else {
            return "redirect:/ ? error";
        }

    }

    @PostMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String updateProfile(@Valid @ModelAttribute Account account, BindingResult bindingResult,
            Principal principal) {

        if (bindingResult.hasErrors()) {
            return "account-view/profile";
        }
        String authUser = "email";
        if (principal != null) {
            authUser = principal.getName();
        }
        Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);
        if (optionalAccount.isPresent()) {
            Account account_by_id = accountService.findById(account.getId()).get();
            account_by_id.setAge(account.getAge());
            account_by_id.setDate_of_birth(account.getDate_of_birth());
            account_by_id.setFirstName(account.getFirstName());
            account_by_id.setLastName(account.getLastName());
            account_by_id.setGender(account.getGender());
            account_by_id.setPassword(account.getPassword());

            accountService.save(account_by_id);
            SecurityContextHolder.clearContext();
            return "redirect:/";
        } else {
            return "redirect:/ ? error";
        }
    }

    @PostMapping("/update_photo")
    @PreAuthorize("isAuthenticated()")  
    public String update_Photo(@RequestParam("file") MultipartFile file,RedirectAttributes attributes,Principal principal){
        if(file.isEmpty()){
            attributes.addFlashAttribute("error","No File Uploaded");
            return "redirect:/profile";
        }else{
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            try{

                int length = 10;
                boolean useLetter = true;
                boolean useNumber = true;

                String geneatedString = RandomStringUtils.random(length,useLetter,useNumber);
                String final_photo_name = geneatedString + fileName;
                String absolute_fileLocation = AppUtils.get_upload_path(final_photo_name);

                Path path = Paths.get(absolute_fileLocation);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                attributes.addFlashAttribute("message","You Successfully Uploaded");

                String authUser = "email";
                if(principal != null){
                    authUser = principal.getName();
                }

                Optional<Account> optionaAccount = accountService.findOneByEmail(authUser);
                if(optionaAccount.isPresent()){
                    Account account = optionaAccount.get();
                    Account account_by_id = accountService.findById(account.getId()).get();
                    String relative_fileLocation =  ("/upload/"+ final_photo_name);
                    account_by_id.setPhoto(relative_fileLocation);
                    accountService.save(account_by_id);
                }
                 try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                return "redirect:/profile";
                
            }catch(Exception e){
                e.printStackTrace();
            }

        }
        return "redirect:/profile?error";
    }  

}
