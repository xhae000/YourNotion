package com.xhadl.yournotion.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class LoginDTO {
    String username;
    String pw;
    String redirect;
}
