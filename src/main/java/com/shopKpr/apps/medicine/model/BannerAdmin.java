package com.shopKpr.apps.medicine.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table
@Entity
public class BannerAdmin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bannerId;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy", timezone = "Asia/Dhaka")
    private Date bannerStartDate;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy", timezone = "Asia/Dhaka")
    private Date bannerEndDate;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String bannerImage;
    @Column(nullable = false)
    private boolean enabled;
    private String hyperLinkId;
}
