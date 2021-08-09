package com.shopKpr.shopKprRegister.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "shopkpr_user")
public class ShopKprUser {
    @Id
    @Field(type = FieldType.Keyword)
    private String mobileNumber;
    @Field(type = FieldType.Keyword)
    private String fullName;
    @Field(type = FieldType.Keyword)
    private String imageUrl;
    @Field(type = FieldType.Keyword)
    private String gender;
    @Field(type = FieldType.Boolean)
    private boolean enabled;
    @Field(type = FieldType.Date, format = DateFormat.date)
    private Date userCreationDate;
}
