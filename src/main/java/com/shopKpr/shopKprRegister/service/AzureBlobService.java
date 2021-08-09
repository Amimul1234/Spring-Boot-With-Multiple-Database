package com.shopKpr.shopKprRegister.service;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import com.shopKpr.admin.model.ShopKprAdmin;
import com.shopKpr.admin.repository.AdminRegisterRepo;
import com.shopKpr.exceptions.EmailAddressMismatch;
import com.shopKpr.exceptions.ImageCreationException;
import com.shopKpr.exceptions.MobileNumberMisMatch;
import com.shopKpr.shopKprRegister.model.ShopKpr;
import com.shopKpr.shopKprRegister.model.ShopKprUser;
import com.shopKpr.shopKprRegister.repository.elasticSearchRepo.ShopKprRegElasticRepo;
import com.shopKpr.shopKprRegister.repository.jpaRepo.ShopKprRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AzureBlobService {

    private final CloudBlobContainer cloudBlobContainer;
    private final AdminRegisterRepo adminRegisterRepo;
    private final ShopKprRegistrationRepository shopKprRegistrationRepository;
    private final ShopKprRegElasticRepo shopKprRegElasticRepo;

    public AzureBlobService( CloudBlobContainer cloudBlobContainer, AdminRegisterRepo adminRegisterRepo,
                             ShopKprRegistrationRepository shopKprRegistrationRepository, ShopKprRegElasticRepo shopKprRegElasticRepo ) {
        this.cloudBlobContainer = cloudBlobContainer;
        this.adminRegisterRepo = adminRegisterRepo;
        this.shopKprRegistrationRepository = shopKprRegistrationRepository;
        this.shopKprRegElasticRepo = shopKprRegElasticRepo;
    }


    @Transactional
    public void uploadProfileImage( MultipartFile multipartFile, String mobileNumber ) {

        URI uri;
        CloudBlockBlob blob;
        ShopKpr shopKpr;

        Optional<ShopKpr> shopKprOptional =
                shopKprRegistrationRepository.findByMobileNumber(mobileNumber);

        Optional<ShopKprUser> shopKprUserOptional =
                shopKprRegElasticRepo.findByMobileNumber(mobileNumber);

        if (shopKprOptional.isPresent()) {
            shopKpr = shopKprOptional.get();
        } else {
            log.info("Wrong mobile number given by user");
            throw new MobileNumberMisMatch("Wrong mobile number provided");
        }

        try {
            blob = cloudBlobContainer.
                    getBlockBlobReference("UserProfileImage/" + UUID.randomUUID() +
                            Objects.requireNonNull(multipartFile.getOriginalFilename()));

            blob.getProperties().setContentType(multipartFile.getContentType());
            blob.upload(multipartFile.getInputStream(), -1);
            uri = blob.getUri();

            if (shopKpr.getImageUrl() != null) {
                deleteBlob(shopKpr.getImageUrl());
            }

            shopKpr.setImageUrl(uri.toString());

            if (shopKprUserOptional.isPresent()) {
                ShopKprUser shopKprUser = shopKprUserOptional.get();
                shopKprUser.setImageUrl(uri.toString());
                shopKprRegElasticRepo.save(shopKprUser);
            } else {
                log.error("ShopKpr Elastic User Not found for mobile: " + mobileNumber);
            }

            shopKprRegistrationRepository.save(shopKpr);

        } catch (URISyntaxException | StorageException | IOException e) {
            log.error("Error occurred on profile image uploading, Error is: " + e.getMessage());
            throw new ImageCreationException("Can not upload profile image");
        }
    }

    @Async
    public void deleteBlob( String blobName ) {
        try {
            CloudBlockBlob cloudBlockBlob = new CloudBlockBlob(new URI(blobName));
            cloudBlobContainer.getBlockBlobReference(cloudBlockBlob.getName()).delete();
        } catch (URISyntaxException | StorageException e) {
            log.error("Blob can not be deleted, Error is: " + e.getMessage());
        }
    }

    @Transactional
    public void uploadAdminProfileImage( MultipartFile multipartFile, String emailAddress ) {
        URI uri;
        CloudBlockBlob blob;
        ShopKprAdmin shopKprAdmin;

        Optional<ShopKprAdmin> shopKprAdminOptional = adminRegisterRepo.findByEmailAddress(emailAddress);

        if (shopKprAdminOptional.isPresent()) {
            shopKprAdmin = shopKprAdminOptional.get();
        } else {
            log.info("Wrong email address given by user");
            throw new EmailAddressMismatch("Wrong email address provided");
        }


        try {
            blob = cloudBlobContainer.
                    getBlockBlobReference("AdminProfilePicture/" + UUID.randomUUID() +
                            Objects.requireNonNull(multipartFile.getOriginalFilename()));

            blob.getProperties().setContentType(multipartFile.getContentType());
            blob.upload(multipartFile.getInputStream(), -1);
            uri = blob.getUri();

            if (shopKprAdmin.getImageUrl().length() > 0) {
                deleteBlob(shopKprAdmin.getImageUrl());
            }

            shopKprAdmin.setImageUrl(uri.toString());
            adminRegisterRepo.save(shopKprAdmin);

        } catch (URISyntaxException | StorageException | IOException e) {
            log.error("Error occurred on profile image uploading, Error is: " + e.getMessage());
            throw new ImageCreationException("Can not upload profile image");
        }
    }

    public String uploadShopLogo( MultipartFile multipartFile ) {

        URI uri;
        CloudBlockBlob blob;

        try {
            blob = cloudBlobContainer.
                    getBlockBlobReference("ShopLogo/" + UUID.randomUUID() +
                            Objects.requireNonNull(multipartFile.getOriginalFilename()));

            blob.getProperties().setContentType(multipartFile.getContentType());
            blob.upload(multipartFile.getInputStream(), -1);
            uri = blob.getUri();
        } catch (URISyntaxException | StorageException | IOException e) {
            log.error("Error occurred on profile image uploading, Error is: " + e.getMessage());
            throw new ImageCreationException("Can not upload profile image");
        }

        return uri.toString();
    }

    public String uploadNidPicture( MultipartFile multipartFile ) {
        URI uri;
        CloudBlockBlob blob;

        try {
            blob = cloudBlobContainer.
                    getBlockBlobReference("ShopKprNid/" + UUID.randomUUID() +
                            Objects.requireNonNull(multipartFile.getOriginalFilename()));

            blob.getProperties().setContentType(multipartFile.getContentType());
            blob.upload(multipartFile.getInputStream(), -1);
            uri = blob.getUri();
        } catch (URISyntaxException | StorageException | IOException e) {
            log.error("Error occurred on profile image uploading, Error is: " + e.getMessage());
            throw new ImageCreationException("Can not upload profile image");
        }

        return uri.toString();
    }

    public String uploadTradeLicense( MultipartFile multipartFile ) {
        URI uri;
        CloudBlockBlob blob;

        try {
            blob = cloudBlobContainer.
                    getBlockBlobReference("ShopKprTradeLicense/" + UUID.randomUUID() +
                            Objects.requireNonNull(multipartFile.getOriginalFilename()));

            blob.getProperties().setContentType(multipartFile.getContentType());
            blob.upload(multipartFile.getInputStream(), -1);
            uri = blob.getUri();
        } catch (URISyntaxException | StorageException | IOException e) {
            log.error("Error occurred on profile image uploading, Error is: " + e.getMessage());
            throw new ImageCreationException("Can not upload profile image");
        }

        return uri.toString();
    }
}
