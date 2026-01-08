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
     
             account01.setEmail("chaudharimanish919@gmail.com"); 
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
            // account02.setRole(Roles.ADMIN.getRole());
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
        

             List<Post> posts = postservice.findAll();
             if (posts.size() == 0){
                  Post post01 = new Post();
                  post01.setName("About Git");
                  post01.setBody("""
                     Git (/ɡɪt/)[8] is a distributed version control system: tracking changes in any set of files, usually used for coordinating work among programmers collaboratively developing source code during software development. Its goals include speed, data integrity, and support for distributed, non-linear workflows (thousands of parallel branches running on different systems).[9][10][11]
      
                     Git was originally authored by Linus Torvalds in 2005 for development of the Linux kernel, with other kernel developers contributing to its initial development.[12] Since 2005, Junio Hamano has been the core maintainer. As with most other distributed version control systems, and unlike most client–server systems, every Git directory on every computer is a full-fledged repository with complete history and full version-tracking abilities, independent of network access or a central server.[13] Git is free and open-source software distributed under the GPL-2.0-only license.   
                  
                  """);
                  post01.setAccount(account01);
                  postservice.save(post01);
      
                  Post post02 = new Post();
                  post02.setName("Spring Boot Model–view–controller framework");
                  post02.setBody("""
                    Each strategy interface above has an important responsibility in the overall framework. The abstractions offered by these interfaces are powerful, so to allow for a set of variations in their implementations, Spring MVC ships with implementations of all these interfaces and together offers a feature set on top of the Servlet API. However, developers and vendors are free to write other implementations. Spring MVC uses the Java java.util.Map interface as a data-oriented abstraction for the Model where keys are expected to be string values.The ease of testing the implementations of these interfaces seems one important advantage of the high level of abstraction offered by Spring MVC. DispatcherServlet is tightly coupled to the Spring inversion of control container for configuring the web layers of applications. However, web applications can use other parts of the Spring Framework—including the container—and choose not to use Spring MVC.

                  """);
                  
                  post02.setAccount(account02);
                  postservice.save(post02);
      
                  Post post03 = new Post();
                  post03.setName("third post");
                  post03.setBody("""
                     Git (/ɡɪt/)[8] is a distributed version control system: tracking changes in any set of files, usually used for coordinating work among programmers collaboratively developing source code during software development. Its goals include speed, data integrity, and support for distributed, non-linear workflows (thousands of parallel branches running on different systems).[9][10][11]
      
                     Git was originally authored by Linus Torvalds in 2005 for development of the Linux kernel, with other kernel developers contributing to its initial development.[12] Since 2005, Junio Hamano has been the core maintainer. As with most other distributed version control systems, and unlike most client–server systems, every Git directory on every computer is a full-fledged repository with complete history and full version-tracking abilities, independent of network access or a central server.[13] Git is free and open-source software distributed under the GPL-2.0-only license.   
                  
                  """);
                  post03.setAccount(account03);
                  postservice.save(post03);
      
                  Post post04 = new Post();
                  post04.setName("Fouth post");
                  post04.setBody("""
                     Each strategy interface above has an important responsibility in the overall framework. The abstractions offered by these interfaces are powerful, so to allow for a set of variations in their implementations, Spring MVC ships with implementations of all these interfaces and together offers a feature set on top of the Servlet API. However, developers and vendors are free to write other implementations. Spring MVC uses the Java java.util.Map interface as a data-oriented abstraction for the Model where keys are expected to be string values.The ease of testing the implementations of these interfaces seems one important advantage of the high level of abstraction offered by Spring MVC. DispatcherServlet is tightly coupled to the Spring inversion of control container for configuring the web layers of applications. However, web applications can use other parts of the Spring Framework—including the container—and choose not to use Spring MVC.
                  """);
                  
                  post04.setAccount(account04);
                  postservice.save(post04);
      
                  Post post05 = new Post();
                  post05.setName("Fifth post");
                  post05.setBody("""
                     Git (/ɡɪt/)[8] is a distributed version control system: tracking changes in any set of files, usually used for coordinating work among programmers collaboratively developing source code during software development. Its goals include speed, data integrity, and support for distributed, non-linear workflows (thousands of parallel branches running on different systems).[9][10][11]
      
                     Git was originally authored by Linus Torvalds in 2005 for development of the Linux kernel, with other kernel developers contributing to its initial development.[12] Since 2005, Junio Hamano has been the core maintainer. As with most other distributed version control systems, and unlike most client–server systems, every Git directory on every computer is a full-fledged repository with complete history and full version-tracking abilities, independent of network access or a central server.[13] Git is free and open-source software distributed under the GPL-2.0-only license.   
                  
                  """);
                  post05.setAccount(account01);
                  postservice.save(post05);
      
                  Post post06 = new Post();
                  post06.setName("Sixth post");
                  post06.setBody("""
                     REST stands for Representational State Transfer. A REST API is a web service that allows communication between a client and a server using HTTP protocol.
                  REST APIs expose resources through URLs and perform operations using standard HTTP methods. REST is an architectural style, not a framework or protocol.
                  
                  REST ARCHITECTURAL CONSTRAINTS
                  
                  1. CLIENT–SERVER
                  Client and server are independent. Client handles UI, server handles business logic and data.
                  
                  2.STATELESS
                  Server does not store client session. Every request contains all required information.
                  
                  3.CACHEABLE
                  Responses should specify whether they are cacheable.
                  
                  4.UNIFORM INTERFACE
                  Consistent and standard way of accessing resources.
                  
                  5.LAYERED SYSTEM
                  Client does not know whether it is talking to actual server or intermediary.
                  
                  6.CODE ON DEMAND (OPTIONAL)
                  Server can send executable code (rarely used).
                  
                  """);
                  
                  post06.setAccount(account02);
                  postservice.save(post06);
      
             }
              
          }
          
      }
      