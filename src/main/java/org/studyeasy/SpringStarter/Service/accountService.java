package org.studyeasy.SpringStarter.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.studyeasy.SpringStarter.Model.Account;
import org.studyeasy.SpringStarter.Model.Authority;
import org.studyeasy.SpringStarter.Repository.accountRepository;
import org.studyeasy.SpringStarter.util.Constant.Roles;

@Service
public class accountService implements UserDetailsService {
    
    @Autowired
    private accountRepository accountrepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // @Value("${spring.mvc.static-path-pattern}")
    // private String photo_prefix;

    public Account save(Account account){
        // Hash the password before saving to ensure security
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        if(account.getRole() == null){
          account.setRole(Roles.USER.getRole());
        }
        if(account.getPhoto() == null){
            String path = "/images/IMG_0061.JPG";
            account.setPhoto(path);
        }
        return accountrepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Optional<Account> optionalAccount = accountrepository.findOneByEmailIgnoreCase(email);
        if(!optionalAccount.isPresent()){
            throw new UsernameNotFoundException("Account is Not present");
        }
        Account account = optionalAccount.get();

        List<GrantedAuthority> grantAuthority = new ArrayList<>();
        grantAuthority.add(new SimpleGrantedAuthority(account.getRole()));
        
        Set<Authority> authorities =  account.getAuthorities();
        for(Authority _auth: authorities){
            grantAuthority.add(new SimpleGrantedAuthority(_auth.getName()));
        }

        return new User(account.getEmail(),account.getPassword(),grantAuthority);
    }

    public Optional<Account> findOneByEmail(String email) {
      return accountrepository.findOneByEmailIgnoreCase(email);     
    }

    public Optional<Account> findById(Long id){
        return accountrepository.findById(id);
    }

    public Optional<Account> findByToken(String token){
        return accountrepository.findByToken(token);
    }

}
