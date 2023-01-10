package com.xhadl.yournotion.Service;

public interface MailAuthService {
    public String setMailAuth(String mail) throws Exception;
    public Boolean key_auth(String key, String email);
}
