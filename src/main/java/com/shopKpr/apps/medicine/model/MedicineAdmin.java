package com.shopKpr.apps.medicine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

import static org.springframework.data.elasticsearch.annotations.FieldType.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "medicine")
@org.springframework.data.mongodb.core.mapping.Document(collection = "medicine")
public class MedicineAdmin {
    @Id
    private String id;
    @MultiField(mainField = @Field(type = Keyword),
            otherFields = {@InnerField(suffix = "search_as_you_type", type = Search_As_You_Type)})
    @NotEmpty(message = "Brand name can not be null")
    private String brandName;
    @MultiField(mainField = @Field(type = Keyword),
            otherFields = {@InnerField(suffix = "search_as_you_type", type = Search_As_You_Type)})
    @NotEmpty(message = "Generics name can not be null")
    private String generics;
    @Field(type = Keyword)
    @NotEmpty(message = "Company name can not be null")
    private String companyName;
    @Field(type = Keyword)
    private String category;
    @Field(type = FieldType.Date, format = DateFormat.date)
    private Date creationDate;
    @Field(type = Keyword)
    private String imageUrl;
    @Field(type = Text)
    private String productHighLights;
    @Field(type = Keyword)
    private String directions;
    @Field(type = Keyword)
    private String safetyInformation;
    @Field(type = Nested)
    private List<DosageFormAdmin> dosageFormAdminList;
}
