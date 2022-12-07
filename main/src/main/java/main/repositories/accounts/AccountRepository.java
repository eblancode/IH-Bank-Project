package main.repositories.accounts;


import jakarta.transaction.Transactional;
import main.modules.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByName(String username);

    @Modifying
    @Transactional
    @Query("delete from Account where id=:id")
    void deleteAccount(@Param(value = "id") Long id);

}
