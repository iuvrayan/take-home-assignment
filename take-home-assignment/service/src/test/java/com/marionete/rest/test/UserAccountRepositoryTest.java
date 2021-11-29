package com.marionete.rest.test;

import com.marionete.rest.rest.service.UserAccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.unitils.reflectionassert.ReflectionAssert;
import services.AccountInfo;
import services.UserAccount;
import services.UserInfo;

public class UserAccountRepositoryTest {
    UserAccountRepository repo = new UserAccountRepository();

    @Test
    void emptyUserAccount() {
        Assertions.assertNull(repo.getUserAccount(""));
        Assertions.assertNull(repo.getUserAccount(" "));
        Assertions.assertNull(repo.getUserAccount(""));
    }

    @Test
    void validUserAccount() {
        ReflectionAssert.assertReflectionEquals(repo.getUserAccount("bla"), new UserAccount(new AccountInfo("12345-3346-3335-4456"),
                new UserInfo("John", "Doe", "male", 32)));
        ReflectionAssert.assertReflectionEquals(repo.getUserAccount("regi"), new UserAccount(new AccountInfo("45321-6433-5333-6544"),
                new UserInfo("Regina", "Cassandra", "female", 28)));
    }
}
