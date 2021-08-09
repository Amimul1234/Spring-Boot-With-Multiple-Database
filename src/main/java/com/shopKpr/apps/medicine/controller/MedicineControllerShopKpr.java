package com.shopKpr.apps.medicine.controller;

import com.shopKpr.apps.medicine.model.MedicineAdmin;
import com.shopKpr.apps.medicine.model.MedicineModifiedShopKpr;
import com.shopKpr.apps.medicine.service.ShopKprMedicineService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ROLE_SHOPKPR_MEDICINE')")
@RequestMapping("api/v1/shopKpr/category/medicine")
public class MedicineControllerShopKpr {

    private final ShopKprMedicineService shopKprMedicineService;

    public MedicineControllerShopKpr( ShopKprMedicineService shopKprMedicineService ) {
        this.shopKprMedicineService = shopKprMedicineService;
    }

    @GetMapping("getAllMedicine")
    public List<MedicineModifiedShopKpr> getAllMedicine( @RequestParam("pageNumber") Integer pageNumber ) {
        return shopKprMedicineService.getAllMedicine(pageNumber);
    }

    @GetMapping("getMedicine")
    public MedicineAdmin getSpecifiedMedicine( @RequestParam("medicineId") String medicineId ) {
        return shopKprMedicineService.getMedicine(medicineId);
    }


}
