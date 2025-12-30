package org.studyeasy.SpringStarter.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.studyeasy.SpringStarter.Model.Account;

@Repository
public interface accountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findOneByEmailIgnoreCase(String email);

    Optional<Account> findByToken(String token);
  
}
