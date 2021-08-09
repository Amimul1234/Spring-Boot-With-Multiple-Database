package com.shopKpr.scheduler;

import com.shopKpr.apps.medicine.model.BannerAdmin;
import com.shopKpr.apps.medicine.repositories.jpa.BannerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class TasksScheduler {

    private final BannerRepo bannerRepo;

    public TasksScheduler( BannerRepo bannerRepo ) {
        this.bannerRepo = bannerRepo;
    }

    //sec, min, hour
    @Scheduled(cron = "0 41  17 * * *", zone = "Asia/Dacca") //Schedule at night 1 AM
    @Transactional(transactionManager = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public void schedulerTask() {
        updateOfferState();
    }


    public void updateOfferState() {

        java.sql.Date today = new java.sql.Date(new java.util.Date().getTime());

        List<BannerAdmin> bannerAdminList = bannerRepo.findAll();

        bannerAdminList.stream()
                .filter(bannerAdmin -> today.after(bannerAdmin.getBannerStartDate()))
                .forEach(bannerAdmin -> {
                    if (!bannerAdmin.isEnabled()) {
                        bannerAdmin.setEnabled(true);
                        bannerRepo.save(bannerAdmin);
                    }
                });

        bannerAdminList.stream()
                .filter(bannerAdmin -> today.after(bannerAdmin.getBannerEndDate()))
                .forEach(bannerAdmin -> bannerRepo.deleteById(bannerAdmin.getBannerId()));
    }
}
