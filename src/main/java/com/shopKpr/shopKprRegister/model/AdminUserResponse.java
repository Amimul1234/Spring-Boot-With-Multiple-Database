package com.shopKpr.shopKprRegister.model;


import java.io.Serializable;
import java.util.List;

public class AdminUserResponse implements Serializable {
    private final List<ShopKprUser> shopKprUserList;
    private final int totalPageNumbers;
    private final long totalNumberOfUser;

    public AdminUserResponse( List<ShopKprUser> shopKprUserList, int totalPageNumbers,
                              long totalNumberOfUser ) {
        this.shopKprUserList = shopKprUserList;
        this.totalPageNumbers = totalPageNumbers;
        this.totalNumberOfUser = totalNumberOfUser;
    }

    public List<ShopKprUser> getShopKprUserList() {
        return shopKprUserList;
    }

    public int getTotalPageNumbers() {
        return totalPageNumbers;
    }

    public long getTotalNumberOfUser() {
        return totalNumberOfUser;
    }
}
