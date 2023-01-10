package com.xhadl.yournotion.Repository;

import com.xhadl.yournotion.Entity.MailAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailAuthRepository extends JpaRepository<MailAuthEntity, Long> {

    public MailAuthEntity save(MailAuthEntity mailAuthEntity);
    public List<MailAuthEntity> findByEmail(String email);

}
