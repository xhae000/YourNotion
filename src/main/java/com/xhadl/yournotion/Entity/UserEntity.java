package com.xhadl.yournotion.Entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Table(name = "user")
@Entity
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String username;
    String pw;
    String email;
    String nickname;
    String gender;
    String role = "ROLE_USER";
    String age;


}
