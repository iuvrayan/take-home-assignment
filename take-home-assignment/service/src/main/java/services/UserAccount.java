package services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserAccount {
    private AccountInfo accountInfo;
    private UserInfo userInfo;
}
