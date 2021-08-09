package com.shopKpr.shopKprRegister.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Shop implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "TEXT")
    private String shopLogo;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String shopKprNid;
    @Column(columnDefinition = "TEXT")
    private String shopKprTradeLicense;
    @Column(nullable = false)
    private String shopName;
    private String shopAddress;
    private boolean shopApproved = false;

    @OneToOne(mappedBy = "shop")
    @JsonBackReference
    private ShopKpr shopKpr;
}
