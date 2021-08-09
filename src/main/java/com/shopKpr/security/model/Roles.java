package com.shopKpr.security.model;

import com.shopKpr.admin.model.ShopKprAdmin;
import com.shopKpr.shopKprRegister.model.ShopKpr;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table
@Entity
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;
    @Column(nullable = false)
    private String role;

    @ManyToOne
    private ShopKpr shopKpr;

    @ManyToOne
    private ShopKprAdmin shopKprAdmin;
}
