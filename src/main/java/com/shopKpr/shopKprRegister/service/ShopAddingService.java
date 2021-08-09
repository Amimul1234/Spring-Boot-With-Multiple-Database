package com.shopKpr.shopKprRegister.service;

import com.shopKpr.exceptions.ShopNotApproved;
import com.shopKpr.exceptions.ShopAlreadyExists;
import com.shopKpr.exceptions.ShopKeeperUserNotFount;
import com.shopKpr.exceptions.ShopNotFoundException;
import com.shopKpr.shopKprRegister.model.Shop;
import com.shopKpr.shopKprRegister.model.ShopKpr;
import com.shopKpr.shopKprRegister.repository.jpaRepo.ShopKprRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ShopAddingService {

    private final ShopKprRegistrationRepository shopKprRegistrationRepository;

    public ShopAddingService( ShopKprRegistrationRepository shopKprRegistrationRepository ) {
        this.shopKprRegistrationRepository = shopKprRegistrationRepository;
    }


    public void addShopToUser( Shop shop, String name ) {

        Optional<ShopKpr> shopKprOptional = shopKprRegistrationRepository.findByMobileNumber(name);

        if (shopKprOptional.isPresent()) {

            ShopKpr shopKpr = shopKprOptional.get();

            if (shopKpr.getShop() == null) {
                shopKpr.setShop(shop);
                shop.setShopKpr(shopKpr);
                shopKprRegistrationRepository.save(shopKpr);
            } else {
                if (!shopKpr.getShop().isShopApproved()) {
                    log.info("Shop is not approved for mobile number : " + name);
                    throw new ShopNotApproved("Shop is not approved");
                } else {
                    log.error("Shop already exists for shopKpr, mobile: " + name);
                    throw new ShopAlreadyExists("Shop already exists for shopKpr");
                }
            }

        } else {
            log.info("ShopKpr with mobile number: " + name + "not found");
            throw new ShopKeeperUserNotFount("ShopKpr with mobile number: " + name + "not found");
        }
    }

    public Shop getShopInfo( String name ) {

        Optional<ShopKpr> shopKprOptional = shopKprRegistrationRepository.findByMobileNumber(name);

        if (shopKprOptional.isPresent()) {

            Shop shop = shopKprOptional.get().getShop();

            if (shop != null) {
                return shop;
            } else {
                log.info("Shop not found for mobile number: " + name);
                throw new ShopNotFoundException("Shop not found for mobile number: " + name);
            }
        } else {
            log.info("ShopKpr with mobile number: " + name + "not found");
            throw new ShopKeeperUserNotFount("ShopKpr with mobile number: " + name + "not found");
        }
    }
}
