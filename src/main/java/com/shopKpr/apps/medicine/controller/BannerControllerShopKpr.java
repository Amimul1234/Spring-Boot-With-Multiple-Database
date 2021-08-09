package com.shopKpr.apps.medicine.controller;

import com.shopKpr.apps.medicine.model.BannerModifiedShopKpr;
import com.shopKpr.apps.medicine.service.BannerServiceAdmin;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@PreAuthorize("hasRole('ROLE_SHOPKPR_MEDICINE')")
@RestController
@RequestMapping("/api/v1/shopkpr/banner")
public class BannerControllerShopKpr {

    private final BannerServiceAdmin bannerServiceAdmin;

    public BannerControllerShopKpr( BannerServiceAdmin bannerServiceAdmin ) {
        this.bannerServiceAdmin = bannerServiceAdmin;
    }

    @GetMapping("getAllBanners")
    public List<BannerModifiedShopKpr> getAllBanners() {
        return bannerServiceAdmin.getAllBannersShopKpr();
    }
}
