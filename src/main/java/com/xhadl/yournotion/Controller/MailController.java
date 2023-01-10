package com.xhadl.yournotion.Controller;

import com.xhadl.yournotion.Service.MailAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    @Autowired
    MailAuthService mailAuthService;


    @PostMapping("/mail/sendMail")
    public String sendMail_ToJoin(@RequestParam(value="email") String mail) throws Exception {

        //using or ok
        return mailAuthService.setMailAuth(mail);
    }

    @PostMapping("/mail/mailAuth")
    public String mailAuth(
            @RequestParam(value="key")String key,
            @RequestParam(value="email")String email){

        return mailAuthService.key_auth(key,email)?"ok" : "false";
    }

}
