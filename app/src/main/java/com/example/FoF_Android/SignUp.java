package com.example.FoF_Android;

public class SignUp {
    private final String email;
    private final String password;
    private final String nickname;


    public String getEmail() {  return email;  }

    public String getPassword() {   return password; }

    public String getNickname() {  return nickname; }

    public SignUp(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
