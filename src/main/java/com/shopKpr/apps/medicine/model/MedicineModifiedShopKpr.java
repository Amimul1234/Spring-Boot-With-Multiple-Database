package com.shopKpr.apps.medicine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MedicineModifiedShopKpr implements Serializable {
    private String id;
    private String imageUrl;
    private String brandName;
    private String generics;
}
