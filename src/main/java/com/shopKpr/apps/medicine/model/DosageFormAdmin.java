package com.shopKpr.apps.medicine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DosageFormAdmin {
    @Field(type = FieldType.Keyword)
    private String dosageForm;
    @Field(type = FieldType.Keyword)
    private String strength;
    @Field(type = FieldType.Keyword)
    private String priceTag;
    @Field(type = FieldType.Keyword)
    private String priceLastTag;
    @Field(type = FieldType.Double)
    private Double buyingPrice;
    @Field(type = FieldType.Double)
    private Double sellingPrice;
    @Field(type = FieldType.Integer)
    private Integer availableQuantity;
}
