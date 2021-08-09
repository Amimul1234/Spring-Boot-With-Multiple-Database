package com.shopKpr.shopKprRegister.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAuthenticate {
    private String mobileNumber;
    private String pin;
    private String fullName;
}
