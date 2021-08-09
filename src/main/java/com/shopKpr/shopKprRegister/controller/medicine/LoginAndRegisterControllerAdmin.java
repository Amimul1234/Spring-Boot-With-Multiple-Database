package com.shopKpr.shopKprRegister.controller.medicine;

import com.shopKpr.shopKprRegister.model.AdminUserResponse;
import com.shopKpr.shopKprRegister.service.ShopKprRegistrationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("api/v1/admin/medicine")
public class LoginAndRegisterControllerAdmin {

    private final ShopKprRegistrationService shopKprRegistrationService;

    public LoginAndRegisterControllerAdmin( ShopKprRegistrationService shopKprRegistrationService ) {
        this.shopKprRegistrationService = shopKprRegistrationService;
    }

    @GetMapping("getAllRegisteredShopKpr")
    public AdminUserResponse getAllRegisteredShopKpr( @RequestParam(value = "page", defaultValue = "0") int page ) {
        return shopKprRegistrationService.getAllRegisteredShopKpr(page);
    }

    @GetMapping("getAllEnabledShopKpr")
    public AdminUserResponse getAllEnabledShopKpr( @RequestParam(value = "page", defaultValue = "0") int page ) {
        return shopKprRegistrationService.getAllEnabledShopKpr(page);
    }

    @GetMapping("getAllDisabledShopKpr")
    public AdminUserResponse getAllDisabledUser( @RequestParam(value = "page", defaultValue = "0") int page ) {
        return shopKprRegistrationService.getAllDisabledUser(page);
    }


    @PutMapping("enableAnUser")
    public void enableAnUser( @RequestParam(name = "mobileNumber") String mobileNumber ) {
        shopKprRegistrationService.enableUser(mobileNumber);
    }

    @PutMapping("disableAnUser")
    public void disableAnUser( @RequestParam(name = "mobileNumber") String mobileNumber ) {
        shopKprRegistrationService.disableUser(mobileNumber);
    }

    @PutMapping("changeShopKprPin")
    public void changeShopKprPinNumber( @RequestParam(name = "mobileNumber") String mobileNumber,
                                        @RequestParam(name = "newPinNumber") String pinNumber ) {
        shopKprRegistrationService.changeShopPinNumber(mobileNumber, pinNumber);
    }

    @GetMapping("searchUserByPhoneNumber")
    public AdminUserResponse getShopKprViaPhone( @RequestParam("mobileNumber") String mobileNumber ) {
        return shopKprRegistrationService.findShopKprByPhoneNumber(mobileNumber);
    }

    @GetMapping("searchUserByName")
    public AdminUserResponse getShopKprViaName( @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam("name") String name ) {
        return shopKprRegistrationService.findByName(page, name);
    }

}
