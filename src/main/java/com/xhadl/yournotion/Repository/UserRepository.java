package com.xhadl.yournotion.Repository;

import com.xhadl.yournotion.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findByUsername(String username);
    @Query("select u.id from UserEntity u where u.username = :username")
    public Integer getUserId(@Param("username") String username);
    public UserEntity save(UserEntity userEntity);

    public UserEntity findByEmail(String email);

    public UserEntity findById(int id);

}
