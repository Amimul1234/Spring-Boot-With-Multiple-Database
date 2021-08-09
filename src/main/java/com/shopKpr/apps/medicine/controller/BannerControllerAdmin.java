package com.shopKpr.apps.medicine.controller;

import com.shopKpr.apps.medicine.model.BannerAdmin;
import com.shopKpr.apps.medicine.service.BannerServiceAdmin;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/api/v1/admin/banner")
public class BannerControllerAdmin {

    private final BannerServiceAdmin bannerServiceAdmin;

    public BannerControllerAdmin( BannerServiceAdmin bannerServiceAdmin ) {
        this.bannerServiceAdmin = bannerServiceAdmin;
    }

    @PostMapping("uploadBannerImage")
    public ResponseEntity<String> uploadBannerImage( @RequestPart MultipartFile multipartFile ) {
        return bannerServiceAdmin.uploadBannerImage(multipartFile);
    }

    @PostMapping("addNewBanner")
    public void addNewBanner( @RequestBody BannerAdmin bannerAdmin ) {
        bannerServiceAdmin.addNewBanner(bannerAdmin);
    }

    @GetMapping("getAllBanners")
    public List<BannerAdmin> getAllBanners() {
        return bannerServiceAdmin.getAllBanners();
    }

    @GetMapping("getAllEnabledBanner")
    public List<BannerAdmin> getAllEnabledBanner() {
        return bannerServiceAdmin.getAllEnabledBanners();
    }

    @GetMapping("getAllDisabledBanners")
    public List<BannerAdmin> getAllDisabledBanner() {
        return bannerServiceAdmin.getAllDisabledBanner();
    }

    @PutMapping("enableBanner")
    public void enableBanner( @RequestParam("bannerId") Long bannerId ) {
        bannerServiceAdmin.enableBanner(bannerId);
    }

    @PutMapping("disableBanner")
    public void disableBanner( @RequestParam("bannerId") Long bannerId ) {
        bannerServiceAdmin.disableBanner(bannerId);
    }


}
