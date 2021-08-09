package com.shopKpr.apps.medicine.service;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.shopKpr.apps.medicine.model.Category;
import com.shopKpr.apps.medicine.repositories.elastic.ElasticSearchCategoryRepo;
import com.shopKpr.apps.medicine.repositories.jpa.CategoryRepository;
import com.shopKpr.exceptions.CategoryCreationException;
import com.shopKpr.exceptions.CategoryNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Service
@Slf4j
public class CategoryServiceAdmin {

    private final CategoryRepository categoryRepository;
    private final CloudBlobContainer cloudBlobContainer;
    private final ElasticSearchCategoryRepo elasticSearchCategoryRepo;

    public CategoryServiceAdmin( CategoryRepository categoryRepository, CloudBlobContainer cloudBlobContainer,
                                 ElasticSearchCategoryRepo elasticSearchCategoryRepo ) {
        this.categoryRepository = categoryRepository;
        this.cloudBlobContainer = cloudBlobContainer;
        this.elasticSearchCategoryRepo = elasticSearchCategoryRepo;
    }

    @Transactional
    public void addNewCategory( String categoryName, MultipartFile multipartFile ) {
        Category category = new Category();
        category.setCategoryName(categoryName);

        uploadCategoryImage(category, multipartFile);
    }

    public void uploadCategoryImage( Category category, MultipartFile multipartFile ) {
        URI uri;
        CloudBlockBlob blob;

        try {
            blob = cloudBlobContainer.
                    getBlockBlobReference("CategoryImage/" + UUID.randomUUID() +
                            Objects.requireNonNull(multipartFile.getOriginalFilename()));

            blob.getProperties().setContentType(multipartFile.getContentType());
            blob.upload(multipartFile.getInputStream(), -1);
            uri = blob.getUri();

            category.setCategoryImageUrl(uri.toString());

            categoryRepository.save(category);
            elasticSearchCategoryRepo.save(category);
        } catch (URISyntaxException | StorageException | IOException e) {
            log.error("Error saving category entity, Error is: " + e.getMessage());
            throw new CategoryCreationException("Can not create new category");
        }
    }

    @Transactional
    public void changeCategoryImage( Long categoryId, MultipartFile multipartFile ) {

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isPresent()) {

            Category category = categoryOptional.get();
            URI uri;
            CloudBlockBlob blob;

            try {
                blob = cloudBlobContainer.
                        getBlockBlobReference("CategoryImage/" + UUID.randomUUID() +
                                Objects.requireNonNull(multipartFile.getOriginalFilename()));

                blob.getProperties().setContentType(multipartFile.getContentType());
                blob.upload(multipartFile.getInputStream(), -1);
                uri = blob.getUri();

                String previousImageUrl = category.getCategoryImageUrl();
                category.setCategoryImageUrl(uri.toString());
                categoryRepository.save(category);
                deleteBlob(previousImageUrl);
            } catch (URISyntaxException | StorageException | IOException e) {
                log.error("Error saving category entity, Error is: " + e.getMessage());
                throw new CategoryCreationException("Can not create new category");
            }
        } else {
            log.info("Category id mismatch, no category for id: " + categoryId);
            throw new CategoryNotFoundException("Category with id: " + categoryId + " not found");
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

}
