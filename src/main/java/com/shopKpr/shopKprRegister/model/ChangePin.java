package com.shopKpr.shopKprRegister.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePin {
    private String mobileNumber;
    private String pin;
}
