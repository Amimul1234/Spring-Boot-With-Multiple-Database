package com.shopKpr.apps.medicine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateDosageFormAdmin implements Serializable {
    private String medicineId;
    private String dosageForm;
    private String strength;
    private DosageFormAdmin dosage;
}
