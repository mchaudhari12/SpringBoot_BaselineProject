package org.studyeasy.SpringStarter.Config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.studyeasy.SpringStarter.Model.Account;
import org.studyeasy.SpringStarter.Model.Authority;
import org.studyeasy.SpringStarter.Model.Post;
import org.studyeasy.SpringStarter.Service.AuthorityService;
import org.studyeasy.SpringStarter.Service.accountService;
import org.studyeasy.SpringStarter.Service.postService;
import org.studyeasy.SpringStarter.util.Constant.Privillages;
import org.studyeasy.SpringStarter.util.Constant.Roles;

@Component
public class seedData implements CommandLineRunner{

    @Autowired
    private postService postservice;

    @Autowired
    private accountService accountservice;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public void run(String... args) throws Exception  {

        for(Privillages auth : Privillages.values()){
            Authority authority = new Authority();
            authority.setId(auth.getId());
            authority.setName(auth.getPrivillage());
            authorityService.save(authority);

        }
        
        Account account01 = new Account();
        Account account02 = new Account(); 
        Account account03 = new Account();
        Account account04 = new Account();    
     
             account01.setEmail("account01@gamil.com"); 
             account01.setPassword("Manish@4511");
             account01.setFirstName("manish");
             account01.setLastName("chaudhari");
     
     
             account02.setEmail("account02@gamil.com");
             account02.setPassword("Manish@451");
             account02.setFirstName("Akshay");
             account02.setLastName("Pathak");
             account02.setRole(Roles.ADMIN.getRole());
     
             account03.setEmail("account03@gamil.com"); 
             account03.setPassword("password03");
             account03.setFirstName("Pratik");
             account03.setLastName("Wadile");
             account03.setRole(Roles.EDITOR.getRole());
     
             account04.setEmail("account04@gamil.com"); 
             account04.setPassword("password04");
             account04.setFirstName("Vishal");
             account04.setLastName("Patil");
             account04.setRole(Roles.EDITOR.getRole());
             Set<Authority> authorities = new HashSet<>();
             authorityService.findById(Privillages.ACCESS_ADMIN_PANEL.getId()).ifPresent(authorities::add);
             authorityService.findById(Privillages.RESET_ANY_USER_PASSWORD.getId()).ifPresent(authorities::add);
             account04.setAuthorities(authorities);
     
             accountservice.save(account01);
             accountservice.save(account02);
             accountservice.save(account03);
             accountservice.save(account04);
        

        List<Post> posts = postservice.getAll();

        if(posts.size() == 0){

            Post post01 = new Post();
            post01.setName("Post 01");
            post01.setBody("Post 01 Body....");
            post01.setAccount(account01);
            postservice.save(post01);

            Post post02 = new Post();
            post02.setName("Post 02");
            post02.setBody("Post 02 Body....");
            post02.setAccount(account02);
            postservice.save(post02);
        }
        
    }
    
}
