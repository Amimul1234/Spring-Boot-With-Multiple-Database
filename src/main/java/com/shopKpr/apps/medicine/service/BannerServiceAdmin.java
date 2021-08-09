package com.shopKpr.apps.medicine.service;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.shopKpr.apps.medicine.model.BannerAdmin;
import com.shopKpr.apps.medicine.model.BannerModifiedShopKpr;
import com.shopKpr.apps.medicine.repositories.jpa.BannerRepo;
import com.shopKpr.exceptions.BannerCreationException;
import com.shopKpr.exceptions.BannerEnableException;
import com.shopKpr.exceptions.ImageCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@EnableAsync
public class BannerServiceAdmin {
    private final BannerRepo bannerRepo;
    private final CloudBlobContainer cloudBlobContainer;

    public BannerServiceAdmin( BannerRepo bannerRepo, CloudBlobContainer cloudBlobContainer ) {
        this.bannerRepo = bannerRepo;
        this.cloudBlobContainer = cloudBlobContainer;
    }

    public void addNewBanner( BannerAdmin bannerAdmin ) {
        try {
            System.out.println(bannerAdmin);
            bannerRepo.save(bannerAdmin);
        } catch (Exception e) {
            log.error("Can not create bannerAdmin, error is: " + e.getMessage());
            throw new BannerCreationException("Can not create bannerAdmin, error is: " + e.getMessage());
        }
    }

    public List<BannerAdmin> getAllEnabledBanners() {
        List<BannerAdmin> bannerAdminList = bannerRepo.findAll();

        return bannerAdminList.parallelStream()
                .filter(BannerAdmin::isEnabled)
                .collect(Collectors.toList());
    }

    public List<BannerAdmin> getAllDisabledBanner() {
        List<BannerAdmin> bannerAdminList = bannerRepo.findAll();

        return bannerAdminList.parallelStream()
                .filter(bannerAdmin -> !bannerAdmin.isEnabled())
                .collect(Collectors.toList());
    }

    public List<BannerAdmin> getAllBanners() {
        return bannerRepo.findAll();
    }

    public void enableBanner( Long bannerId ) {

        Optional<BannerAdmin> bannerOptional = bannerRepo.findById(bannerId);

        if (bannerOptional.isPresent()) {
            BannerAdmin bannerAdmin = bannerOptional.get();
            bannerAdmin.setEnabled(true);
            bannerRepo.save(bannerAdmin);
        } else {
            log.info("Can not enable banner, please try again");
            throw new BannerEnableException("Can not enable banner");
        }
    }

    public void disableBanner( Long bannerId ) {

        Optional<BannerAdmin> bannerOptional = bannerRepo.findById(bannerId);

        if (bannerOptional.isPresent()) {
            BannerAdmin bannerAdmin = bannerOptional.get();
            bannerAdmin.setEnabled(false);
            bannerRepo.save(bannerAdmin);
        } else {
            log.info("Can not disable banner, please try again");
            throw new BannerEnableException("Can not enable banner");
        }
    }

    public ResponseEntity<String> uploadBannerImage( MultipartFile multipartFile ) {

        URI uri;
        CloudBlockBlob blob;

        try {
            blob = cloudBlobContainer.
                    getBlockBlobReference("BannerImage/" + UUID.randomUUID() +
                            Objects.requireNonNull(multipartFile.getOriginalFilename()));

            blob.getProperties().setContentType(multipartFile.getContentType());
            blob.upload(multipartFile.getInputStream(), -1);
            uri = blob.getUri();
            return ResponseEntity.ok(uri.toString());
        } catch (URISyntaxException | StorageException | IOException e) {
            log.error("Error uploading image, Error is: " + e.getMessage());
            throw new ImageCreationException("Can not upload new image");
        }
    }

    public List<BannerModifiedShopKpr> getAllBannersShopKpr() {

        List<BannerAdmin> bannerAdminList = bannerRepo.findAll();

        return bannerAdminList.parallelStream()
                .filter(BannerAdmin::isEnabled)
                .map(bannerAdmin -> {
                    BannerModifiedShopKpr bannerModifiedShopKpr = new BannerModifiedShopKpr();
                    bannerModifiedShopKpr.setBannerImage(bannerAdmin.getBannerImage());
                    bannerModifiedShopKpr.setHyperLinkId(bannerAdmin.getHyperLinkId());
                    return bannerModifiedShopKpr;
                })
                .collect(Collectors.toList());
    }

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public PlatformTransactionManager jpaTransactionManager() {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
