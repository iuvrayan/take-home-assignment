package com.marionete.rest.rest.service;

import org.springframework.context.annotation.Bean;
import services.AccountInfo;
import services.UserAccount;
import services.UserInfo;

import java.util.HashMap;

public class UserAccountRepository {
    private HashMap<String, UserAccount> accountRepo = new HashMap<>();
    private HashMap<String, String> tokenRepo = new HashMap<>();

    public UserAccountRepository() {
        // The constructor is used here for creating UserAccount entity objects.
        // In real world scenario, a separate Entity object will be used.

        addUserAccount("bla", new UserAccount(new AccountInfo("12345-3346-3335-4456"),
                new UserInfo("John", "Doe", "male", 32)));

        addUserAccount("regi", new UserAccount(new AccountInfo("45321-6433-5333-6544"),
                new UserInfo("Regina", "Cassandra", "female", 28)));
    }

    public void addUserAccount(String userName, UserAccount userAccount) {
        if (userName != null && !userName.isEmpty() && !userName.isBlank() && userAccount != null) {
            accountRepo.put(userName, userAccount);
        }
    }

    public UserAccount getUserAccount(String userName) {
        if (userName != null && !userName.isEmpty() && !userName.isBlank()) {
            return accountRepo.get(userName);
        } else {
            return null;
        }
    }

    public String getToken(String userName) {
        return tokenRepo.get(userName);
    }

    public void setToken(String userName, String token) {
        tokenRepo.put(userName, token);
    }
}
