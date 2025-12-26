package org.studyeasy.SpringStarter.Config;

import java.time.LocalDate;
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
             account01.setAge(25);
             account01.setDate_of_birth(LocalDate.parse("1990-01-01"));
             account01.setGender("Male");
     
     
             account02.setEmail("account02@gamil.com");
             account02.setPassword("Manish@451");
             account02.setFirstName("Akshay");
             account02.setLastName("Pathak");
             account02.setRole(Roles.ADMIN.getRole());
             account02.setAge(25);
             account02.setDate_of_birth(LocalDate.parse("1990-01-01"));
             account02.setGender("Female");
     
             account03.setEmail("account03@gamil.com"); 
             account03.setPassword("password03");
             account03.setFirstName("Pratik");
             account03.setLastName("Wadile");
             account03.setRole(Roles.EDITOR.getRole());
             account03.setAge(55);
             account03.setDate_of_birth(LocalDate.parse("1975-01-01"));
             account03.setGender("Male");
     
             account04.setEmail("account04@gamil.com"); 
             account04.setPassword("password04");
             account04.setFirstName("Vishal");
             account04.setLastName("Patil");
             account04.setRole(Roles.EDITOR.getRole());
             account04.setAge(40);
             account04.setDate_of_birth(LocalDate.parse("1980-01-01"));
             account04.setGender("Female");

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
            post01.setName("About Git");
            post01.setBody("""
               Git (/ɡɪt/)[8] is a distributed version control system: tracking changes in any set of files, usually used for coordinating work among programmers collaboratively developing source code during software development. Its goals include speed, data integrity, and support for distributed, non-linear workflows (thousands of parallel branches running on different systems).[9][10][11]

               Git was originally authored by Linus Torvalds in 2005 for development of the Linux kernel, with other kernel developers contributing to its initial development.[12] Since 2005, Junio Hamano has been the core maintainer. As with most other distributed version control systems, and unlike most client–server systems, every Git directory on every computer is a full-fledged repository with complete history and full version-tracking abilities, independent of network access or a central server.[13] Git is free and open-source software distributed under the GPL-2.0-only license.   
            
            """);
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
