package com.shopKpr.shopKprRegister.controller.medicine;

import com.shopKpr.security.model.AuthenticationResponse;
import com.shopKpr.shopKprRegister.model.ChangePin;
import com.shopKpr.shopKprRegister.model.Shop;
import com.shopKpr.shopKprRegister.model.ShopKpr;
import com.shopKpr.shopKprRegister.model.UserAuthenticate;
import com.shopKpr.shopKprRegister.service.AzureBlobService;
import com.shopKpr.shopKprRegister.service.ShopAddingService;
import com.shopKpr.shopKprRegister.service.ShopKprRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.shopKpr.security.constants.ApplicationUserRole.ROLE_SHOPKPR_MEDICINE;

@RestController
@RequestMapping("api/v1/shopKpr/medicine")
public class LoginAndRegisterController {

    private final ShopKprRegistrationService shopKprRegistrationService;
    private final ShopAddingService shopAddingService;
    private final AzureBlobService azureBlobService;

    public LoginAndRegisterController( ShopKprRegistrationService shopKprRegistrationService,
                                       ShopAddingService shopAddingService, AzureBlobService azureBlobService ) {
        this.shopKprRegistrationService = shopKprRegistrationService;
        this.shopAddingService = shopAddingService;
        this.azureBlobService = azureBlobService;
    }

    @PostMapping("registerNewUser")
    public void registerShopKpr( @RequestBody ShopKpr shopKpr ) {
        shopKprRegistrationService.registerNewUser(shopKpr, ROLE_SHOPKPR_MEDICINE.name());
    }

    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateShopKpr(
            @RequestBody UserAuthenticate userAuthenticate ) {

        return shopKprRegistrationService.authenticate(userAuthenticate);

    }

    @PostMapping("uploadProfileImage")
    @PreAuthorize("hasRole('ROLE_SHOPKPR_MEDICINE')")
    public void uploadProfileImage( @RequestParam MultipartFile multipartFile,
                                    Authentication authentication ) {
        azureBlobService.uploadProfileImage(multipartFile, authentication.getName());
    }

    @PutMapping("changePinNumber")
    public void changePinNumber( @RequestBody ChangePin changePin ) {
        shopKprRegistrationService.changePinNumber(changePin.getPin(), changePin.getMobileNumber());
    }

    @PostMapping("uploadShopLogo")
    @PreAuthorize("hasRole('ROLE_SHOPKPR_MEDICINE')")
    public String uploadLogo( @RequestParam MultipartFile multipartFile ) {
        return azureBlobService.uploadShopLogo(multipartFile);
    }

    @PostMapping("uploadNidPicture")
    @PreAuthorize("hasRole('ROLE_SHOPKPR_MEDICINE')")
    public String uploadNidPicture( @RequestParam MultipartFile multipartFile ) {
        return azureBlobService.uploadNidPicture(multipartFile);
    }

    @PostMapping("uploadTradeLicense")
    @PreAuthorize("hasRole('ROLE_SHOPKPR_MEDICINE')")
    public String uploadTradeLicense( @RequestParam MultipartFile multipartFile ) {
        return azureBlobService.uploadTradeLicense(multipartFile);
    }

    @PostMapping("registerNewShop")
    @PreAuthorize("hasRole('ROLE_SHOPKPR_MEDICINE')")
    public void addShopToShopKpr( @RequestBody Shop shop, Authentication authentication ) {
        shopAddingService.addShopToUser(shop, authentication.getName());
    }

    @GetMapping("getShopInfo")
    @PreAuthorize("hasRole('ROLE_SHOPKPR_MEDICINE')")
    public Shop getShopInfo( Authentication authentication ) {
        return shopAddingService.getShopInfo(authentication.getName());
    }
}
