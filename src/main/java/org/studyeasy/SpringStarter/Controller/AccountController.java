package org.studyeasy.SpringStarter.Controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.studyeasy.SpringStarter.Service.EmailService;
import org.studyeasy.SpringStarter.Service.accountService;
import org.studyeasy.SpringStarter.util.Constant.AppUtils;
import org.studyeasy.SpringStarter.util.Email.EmailDetails;

import jakarta.validation.Valid;

@Controller
public class AccountController {

    @Autowired
    private accountService accountService;

    // @Value("${spring.mvc.static-path-pattern}")
    // private String photo_prefix;

    @Autowired
    private EmailService emailService;

    @Value("${site.domain}")
    private String site_domain;

    @Value("${password.token.reset.timeout.minutes}")
    private int password_token_timeout;

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

    @GetMapping("/forgot-password")
    public String forgot_password(Model model) {
        return "account-view/forgot_password";
    }

    @PostMapping("/reset-password")
    public String reset_password(@RequestParam("email") String _email,RedirectAttributes attributes,Model model){
        Optional<Account> optionalAccount = accountService.findOneByEmail(_email);
        if(optionalAccount.isPresent()){
            Account account = accountService.findById(optionalAccount.get().getId()).get();
            String reset_token = UUID.randomUUID().toString();
            account.setToken(reset_token);
            account.setPassword_reset_token_expiry(LocalDateTime.now().plusMinutes(password_token_timeout));
            accountService.save(account);

            String reset_message = "This is reset password link : "+site_domain+"change-password?token="+reset_token;
            EmailDetails emailDetails =new EmailDetails(account.getEmail(),reset_message,"Reset password Email Demo");
            if(emailService.sendSimpleEmail(emailDetails) == false){
                attributes.addFlashAttribute("error","Error while sending email,contact admin");
                return "redirect:/forgot-password";
            }

            attributes.addFlashAttribute("message","Password reset email send");
            return "redirect:/login";
        }
        else{
            attributes.addFlashAttribute("error","No User found with the email supplied");
            return "redirect:/forgot-password";
        }
    }

    @GetMapping("/change-password")
    public String change_password(Model model,@RequestParam("token") String token, RedirectAttributes attributes){
         Optional<Account> optional_account = accountService.findByToken(token);
         if(optional_account.isPresent()){
            Account account  = accountService.findById(optional_account.get().getId()).get();
            LocalDateTime now = LocalDateTime.now();
            if(now.isAfter(optional_account.get().getPassword_reset_token_expiry())){
                attributes.addFlashAttribute("error","Token Expiry");
                return "redirect:/forgot-password";
            }
            model.addAttribute("account", account);
            return "account-view/change_password";
         }

            attributes.addFlashAttribute("error","Token Expiry");
            return "redirect:/forgot-password";
    }

    @PostMapping("/change-password")
    public String post_change_password(@ModelAttribute Account account,RedirectAttributes attributes){
        Account account_by_id = accountService.findById(account.getId()).get();
        account_by_id.setPassword(account.getPassword());
        account_by_id.setToken(account.getToken());
        accountService.save(account_by_id);
        attributes.addFlashAttribute("message","password update");
        return "redirect:/login";
    }
    
}
