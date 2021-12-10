package com.example.music.user.verfication;

import java.util.Optional;

import com.example.music.user.verfication.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long>{

    Optional<Verification> findByToken(String token);

    @Query(value="select * from verify_account where account_id=?1 order by id desc limit 1",nativeQuery = true)
    Verification findToken(Long id);

}
