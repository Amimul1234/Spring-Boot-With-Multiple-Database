package com.shopKpr.admin.controller;

import com.shopKpr.admin.model.AdminAuthenticate;
import com.shopKpr.admin.model.ShopKprAdmin;
import com.shopKpr.admin.service.AdminRegistrationService;
import com.shopKpr.security.model.AuthenticationResponse;
import com.shopKpr.shopKprRegister.service.AzureBlobService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/admin")
public class AdminRegistrationController {

    private final AdminRegistrationService adminRegistrationService;
    private final AzureBlobService azureBlobService;

    public AdminRegistrationController( AdminRegistrationService adminRegistrationService,
                                        AzureBlobService azureBlobService ) {
        this.adminRegistrationService = adminRegistrationService;
        this.azureBlobService = azureBlobService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("registerNewAdmin")
    public void registerNewAdmin( @RequestBody ShopKprAdmin shopKprAdmin ) {
        adminRegistrationService.registerNewAdmin(shopKprAdmin);
    }

    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateAdmin(
            @RequestBody AdminAuthenticate adminAuthenticate ) {

        return adminRegistrationService.authenticateAdmin(adminAuthenticate);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("uploadProfileImage")
    public void uploadProfileImage( @RequestParam MultipartFile multipartFile,
                                    Authentication authentication ) {
        azureBlobService.uploadAdminProfileImage(multipartFile, authentication.getName());
    }
}
