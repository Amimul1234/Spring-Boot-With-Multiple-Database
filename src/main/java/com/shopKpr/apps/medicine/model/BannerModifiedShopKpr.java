package com.shopKpr.apps.medicine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BannerModifiedShopKpr implements Serializable {
    private String bannerImage;
    private String hyperLinkId;
}
