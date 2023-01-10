package com.xhadl.yournotion.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="email_auth")
public class MailAuthEntity {

    public MailAuthEntity(String email, String auth_key){
        this.email = email;
        this.is_auth = "false";
        this.auth_key = auth_key;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String email;
    String is_auth;
    String auth_key;


}
