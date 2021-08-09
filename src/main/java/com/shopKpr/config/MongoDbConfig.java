package com.shopKpr.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MongoDbConfig extends AbstractMongoClientConfiguration {

    @Bean
    MongoTransactionManager transactionManager( MongoDatabaseFactory dbFactory ) {
        return new MongoTransactionManager(dbFactory);
    }

    @NotNull
    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(
                "mongodb+srv://shopKpr:ShopKpr-2021@cluster0.amkd0.mongodb.net/shopKpr?retryWrites=true&w=majority");
    }

    @NotNull
    @Override
    protected String getDatabaseName() {
        return mongoClient().getDatabase("shopkpr").getName();
    }

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener( LocalValidatorFactoryBean factory ) {
        return new ValidatingMongoEventListener(factory);
    }
}
