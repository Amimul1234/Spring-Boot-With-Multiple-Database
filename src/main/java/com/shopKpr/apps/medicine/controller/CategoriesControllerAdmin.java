package com.shopKpr.apps.medicine.controller;

import com.shopKpr.apps.medicine.service.CategoryServiceAdmin;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/admin")
public class CategoriesControllerAdmin {

    private final CategoryServiceAdmin categoryServiceAdmin;

    public CategoriesControllerAdmin( CategoryServiceAdmin categoryServiceAdmin ) {
        this.categoryServiceAdmin = categoryServiceAdmin;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("addNewCategory/{categoryName}")
    public void addNewCategory( @PathVariable(name = "categoryName") String categoryName,
                                @RequestPart MultipartFile multipartFile ) {
        categoryServiceAdmin.addNewCategory(categoryName, multipartFile);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("changeCategoryImage/{categoryId}")
    public void changeCategoryImage( @PathVariable(name = "categoryId") Long categoryId,
                                     @RequestPart MultipartFile multipartFile ) {
        categoryServiceAdmin.changeCategoryImage(categoryId, multipartFile);
    }
}
