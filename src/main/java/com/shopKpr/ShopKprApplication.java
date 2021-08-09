package com.shopKpr;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableTransactionManagement

@EnableElasticsearchRepositories(basePackages = {"com.shopKpr.shopKprRegister.repository.elasticSearchRepo",
        "com.shopKpr.apps.medicine.repositories.elastic"})
@EnableJpaRepositories(basePackages = {"com.shopKpr.shopKprRegister.repository.jpaRepo", "com.shopKpr.admin.repository",
        "com.shopKpr.apps.medicine.repositories.jpa"})
@EnableMongoRepositories(basePackages = {"com.shopKpr.apps.medicine.repositories.mongo",})

public class ShopKprApplication {

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource("push-notification-credential.json").getInputStream());

        FirebaseOptions firebaseOptions = FirebaseOptions
                .builder()
                .setCredentials(googleCredentials)
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "my-app");

        return FirebaseMessaging.getInstance(app);
    }


    public static void main( String[] args ) {
        SpringApplication.run(ShopKprApplication.class, args);
    }
}
