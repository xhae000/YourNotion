package com.xhadl.yournotion.Service;

import com.xhadl.yournotion.Entity.UserEntity;
import com.xhadl.yournotion.Repository.MailAuthRepository;
import com.xhadl.yournotion.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
public class MailService {
    @Autowired
    private  JavaMailSender emailSender;
    private String ePw;
    private Random random = new Random();
    @Autowired
    MailAuthRepository mailAuthRepository;
    @Autowired
    UserRepository userRepository;

    /* 대상 메일은 @ 이전만 받음*/
    public MimeMessage creatMessage(String to) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();


        message.addRecipients(RecipientType.TO, to); // 메일 받을 사용자
        message.setSubject("[Your notion] 회원가입을 위한 이메일 인증코드 입니다"); // 이메일 제목

        String msgg = "";
        msgg += "<h1>안녕하세요</h1>";
        msgg += "<h1>Your Notion 입니다.</h1>";
        msgg += "<br>";
        msgg += "<p>아래 인증코드를 회원가입 페이지에 입력해주세요</p>";
        msgg += "<br>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black'>";
        msgg += "<h3 style='color:blue'>회원가입 인증코드 입니다</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "<strong>" + ePw + "</strong></div><br/>" ; // 메일에 인증번호 ePw 넣기
        msgg += "</div>";

        message.setText(msgg, "utf-8", "html"); // 메일 내용, charset타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        message.setFrom(new InternetAddress("xhadl000@naver.com", "우진"));


        return message;
    }

    // 랜덤 인증코드 생성
    public String createKey() {
        String key = "";

        for (int i=0;i<6;i++){
            key += Integer.toString(random.nextInt(10));
        }

        return key;
    }

    // 메일 발송
    // sendSimpleMessage 의 매개변수 to는 이메일 주소가 되고,
    // MimeMessage 객체 안에 내가 전송할 메일의 내용을 담는다
    // bean으로 등록해둔 javaMail 객체를 사용하여 이메일을 발송한다
    public String sendSimpleMessage(String to) throws Exception {

        UserEntity user = userRepository.findByEmail(to);

        // 이 이메일로 만들어진 계정이 있으면 "using"
        if(user != null){
            return "using";
        }

        ePw = createKey(); // 랜덤 인증코드 생성
        MimeMessage message = creatMessage(to+"@naver.com"); // "to"에게 메일 발송

        try { // 예외처리
            emailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }

        return ePw; // 메일로 사용자에게 보낸 인증코드를 서버로 반환! 인증코드 일치여부를 확인하기 위함
    }

}
