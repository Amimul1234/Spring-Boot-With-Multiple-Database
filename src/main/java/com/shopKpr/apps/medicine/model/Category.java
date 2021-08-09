package com.shopKpr.apps.medicine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.annotation.Id
    private Long categoryId;
    @Column(nullable = false)
    @Field(type = FieldType.Text)
    private String categoryName;
    @Column(columnDefinition = "TEXT")
    @Field(type = FieldType.Keyword)
    private String categoryImageUrl;
}
