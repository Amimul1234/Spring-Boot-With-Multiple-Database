package com.shopKpr.apps.medicine.service;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.shopKpr.apps.medicine.model.DosageFormAdmin;
import com.shopKpr.apps.medicine.model.MedicineAdmin;
import com.shopKpr.apps.medicine.model.UpdateDosageFormAdmin;
import com.shopKpr.apps.medicine.repositories.elastic.ElasticRepositoryAdmin;
import com.shopKpr.apps.medicine.repositories.mongo.MedicineRepoAdmin;
import com.shopKpr.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Service
@Slf4j
public class MedicineServiceAdmin {

    private final MedicineRepoAdmin medicineMongoRepo;
    private final ElasticRepositoryAdmin medicineElasticRepo;
    private final CloudBlobContainer cloudBlobContainer;

    public MedicineServiceAdmin( MedicineRepoAdmin medicineMongoRepo, ElasticRepositoryAdmin medicineElasticRepo,
                                 CloudBlobContainer cloudBlobContainer ) {
        this.medicineMongoRepo = medicineMongoRepo;
        this.medicineElasticRepo = medicineElasticRepo;
        this.cloudBlobContainer = cloudBlobContainer;
    }


    @Transactional(transactionManager = "transactionManager")
    public void addNewMedicine( MedicineAdmin medicineAdmin ) {

        medicineElasticRepo.findByBrandName(medicineAdmin.getBrandName())
                .stream().findFirst()
                .ifPresentOrElse(
                        medicineAdmin1 -> {
                            log.info("MedicineAdmin already exists, medicineAdmin name is: " + medicineAdmin.getBrandName());
                            throw new AlreadyExists("MedicineAdmin already exists");
                        },
                        () -> {
                            medicineAdmin.setCreationDate(new Date(System.currentTimeMillis()));

                            try {
                                medicineMongoRepo.save(medicineAdmin);
                                medicineElasticRepo.save(medicineAdmin);
                            } catch (Exception exception) {
                                log.info("Error while saving medicineAdmin, Error is: - " + exception.getMessage());
                                throw new ValidationFailed("Error while saving medicineAdmin, Error is: - " + exception.getMessage());
                            }
                        }
                );
    }

    @Transactional(transactionManager = "transactionManager")
    public void deleteMedicine( String brandName ) {

        medicineElasticRepo.findByBrandName(brandName)
                .ifPresentOrElse(
                        medicineAdmin -> {

                            medicineMongoRepo.deleteById(medicineAdmin.getId());
                            medicineElasticRepo.deleteById(medicineAdmin.getId());

                            deleteBlob(medicineAdmin.getImageUrl());
                        },
                        () -> {
                            log.info("MedicineAdmin with brand name: " + brandName + " not found");
                            throw new MedicineNotFound("MedicineAdmin with brand name: " + brandName + " not found");
                        }
                );

    }

    public ResponseEntity<String> uploadMedicineImage( MultipartFile multipartFile ) {

        URI uri;
        CloudBlockBlob blob;

        try {
            blob = cloudBlobContainer.
                    getBlockBlobReference("MedicineImage/" + UUID.randomUUID() +
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

    @Async
    public void deleteBlob( String blobName ) {
        try {
            CloudBlockBlob cloudBlockBlob = new CloudBlockBlob(new URI(blobName));
            cloudBlobContainer.getBlockBlobReference(cloudBlockBlob.getName()).delete();
        } catch (URISyntaxException | StorageException e) {
            log.error("Blob can not be deleted, Error is: " + e.getMessage());
        }
    }

    @Transactional(transactionManager = "transactionManager")
    public void addDosageForm( String brandName, DosageFormAdmin dosageFormAdmin ) {

        Optional<MedicineAdmin> medicineOptional = medicineMongoRepo.findByBrandName(brandName);

        if (medicineOptional.isPresent()) {
            MedicineAdmin medicineAdmin = medicineOptional.get();
            medicineAdmin.getDosageFormAdminList().add(dosageFormAdmin);
            medicineMongoRepo.save(medicineAdmin);
            medicineElasticRepo.save(medicineAdmin);
        } else {
            log.info("MedicineAdmin with brand name: " + brandName + " not found");
            throw new MedicineNotFound("MedicineAdmin with brand name: " + brandName + " not found");
        }

    }

    public MedicineAdmin getMedicine( String brandName ) {

        Optional<MedicineAdmin> medicineOptional = medicineElasticRepo.findByBrandName(brandName);

        if (medicineOptional.isPresent()) {
            return medicineOptional.get();
        } else {
            log.info("MedicineAdmin with brand name: " + brandName + " not found");
            throw new MedicineNotFound("MedicineAdmin with brand name: " + brandName + " not found");
        }
    }

    @Transactional(transactionManager = "transactionManager")
    public void updateDosageForm( UpdateDosageFormAdmin updateDosageFormAdmin ) {

        Optional<MedicineAdmin> medicineOptional = medicineElasticRepo.findById(updateDosageFormAdmin.getMedicineId());

        if (medicineOptional.isPresent()) {

            MedicineAdmin medicineAdmin = medicineOptional.get();

            List<DosageFormAdmin> dosageFormAdminList = medicineAdmin.getDosageFormAdminList();

            dosageFormAdminList.parallelStream()
                    .filter(dosageFormAdmin -> dosageFormAdmin.getDosageForm().equals(updateDosageFormAdmin.getDosageForm()) &&
                            dosageFormAdmin.getStrength().equals(updateDosageFormAdmin.getStrength()))
                    .findFirst()
                    .ifPresentOrElse(

                            dosageFormAdmin -> {
                                dosageFormAdmin.setDosageForm(updateDosageFormAdmin.getDosage().getDosageForm());
                                dosageFormAdmin.setStrength(updateDosageFormAdmin.getDosage().getStrength());
                                dosageFormAdmin.setPriceTag(updateDosageFormAdmin.getDosage().getPriceTag());
                                dosageFormAdmin.setPriceLastTag(updateDosageFormAdmin.getDosage().getPriceLastTag());
                                dosageFormAdmin.setBuyingPrice(updateDosageFormAdmin.getDosage().getBuyingPrice());
                                dosageFormAdmin.setSellingPrice(updateDosageFormAdmin.getDosage().getSellingPrice());
                                dosageFormAdmin.setAvailableQuantity(updateDosageFormAdmin.getDosage().getAvailableQuantity());

                                medicineMongoRepo.save(medicineAdmin);
                                medicineElasticRepo.save(medicineAdmin);
                            },

                            () -> {
                                log.info("Dosage form not found for given dosage name and strength");
                                throw new DosageFormNotFound("Dosage form not found for given dosage name and strength");
                            }
                    );

        } else {
            log.info("MedicineAdmin not found with given id: " + updateDosageFormAdmin.getMedicineId());
            throw new MedicineNotFound("MedicineAdmin not found with given id: " + updateDosageFormAdmin.getMedicineId());
        }
    }

    @Transactional(transactionManager = "transactionManager")
    public void deleteDosageForm( UpdateDosageFormAdmin updateDosageFormAdmin ) {

        Optional<MedicineAdmin> medicineOptional = medicineElasticRepo.findById(updateDosageFormAdmin.getMedicineId());

        if (medicineOptional.isPresent()) {
            int flag = 0;
            MedicineAdmin medicineAdmin = medicineOptional.get();
            List<DosageFormAdmin> dosageFormAdminList = medicineAdmin.getDosageFormAdminList();

            for (DosageFormAdmin dosageFormAdmin : dosageFormAdminList) {

                if (dosageFormAdmin.getDosageForm().equals(updateDosageFormAdmin.getDosageForm()) &&
                        dosageFormAdmin.getStrength().equals(updateDosageFormAdmin.getStrength())) {

                    flag = 1;
                    dosageFormAdminList.remove(dosageFormAdmin);
                    break;
                }

            }

            if (flag == 0) {
                log.info("Dosage form not found for given dosage name and strength");
                throw new DosageFormNotFound("Dosage form not found for given dosage name and strength");
            } else {
                medicineMongoRepo.save(medicineAdmin);
                medicineElasticRepo.save(medicineAdmin);
            }
        } else {
            log.info("MedicineAdmin not found with given id: " + updateDosageFormAdmin.getMedicineId());
            throw new MedicineNotFound("MedicineAdmin not found with given id: " + updateDosageFormAdmin.getMedicineId());
        }
    }
}
