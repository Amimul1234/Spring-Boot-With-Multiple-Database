package com.shopKpr.apps.medicine.controller;

import com.shopKpr.apps.medicine.model.DosageFormAdmin;
import com.shopKpr.apps.medicine.model.MedicineAdmin;
import com.shopKpr.apps.medicine.model.UpdateDosageFormAdmin;
import com.shopKpr.apps.medicine.service.MedicineServiceAdmin;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/admin/category/medicine")
public class MedicineControllerAdmin {

    private final MedicineServiceAdmin medicineServiceAdmin;

    public MedicineControllerAdmin( MedicineServiceAdmin medicineServiceAdmin ) {
        this.medicineServiceAdmin = medicineServiceAdmin;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("uploadMedicineImage")
    public ResponseEntity<String> uploadMedicineImage( @RequestPart MultipartFile multipartFile ) {
        return medicineServiceAdmin.uploadMedicineImage(multipartFile);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("addNewMedicine")
    public void addNewMedicine( @RequestBody MedicineAdmin medicineAdmin ) {
        medicineServiceAdmin.addNewMedicine(medicineAdmin);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("deleteMedicine")
    public void deleteMedicine( @RequestParam String brandName ) {
        medicineServiceAdmin.deleteMedicine(brandName);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("addNewDosageForm/{brandName}")
    public void addNewDosageForm( @PathVariable("brandName") String brandName,
                                  @RequestBody DosageFormAdmin dosageFormAdmin ) {
        medicineServiceAdmin.addDosageForm(brandName, dosageFormAdmin);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("getMedicine/{brandName}")
    public MedicineAdmin getMedicine( @PathVariable(name = "brandName") String brandName ) {
        return medicineServiceAdmin.getMedicine(brandName);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("updateDosageForm")
    public void updateDosageForm( @RequestBody UpdateDosageFormAdmin updateDosageFormAdmin ) {
        medicineServiceAdmin.updateDosageForm(updateDosageFormAdmin);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("deleteDosageForm")
    public void deleteDosageForm( @RequestBody UpdateDosageFormAdmin updateDosageFormAdmin ) {
        medicineServiceAdmin.deleteDosageForm(updateDosageFormAdmin);
    }
}
