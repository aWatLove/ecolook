package org.example.dto.payment.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoUpdateRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
}
