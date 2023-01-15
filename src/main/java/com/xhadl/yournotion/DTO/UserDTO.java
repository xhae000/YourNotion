package com.xhadl.yournotion.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    int id;
    String username;
    String email;
    String pw;
    String nickname;
    String gender;
    String age;
}
