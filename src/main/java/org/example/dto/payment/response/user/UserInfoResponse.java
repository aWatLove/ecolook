package org.example.dto.payment.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponse {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
}
