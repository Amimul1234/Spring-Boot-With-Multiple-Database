package com.shopKpr.shopKprRegister.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shopKpr.security.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class ShopKpr implements UserDetails {
    @Id
    private String mobileNumber;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String pin;
    @Column(nullable = false)
    private String fullName;
    private String gender;
    @Column(columnDefinition = "TEXT")
    private String imageUrl;
    private boolean enabled;
    private Date userCreationDate;

    @OneToMany(mappedBy = "shopKpr", orphanRemoval = true, fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Roles> roles = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Shop shop;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();

        roles.forEach(roles -> simpleGrantedAuthorityList.add(
                new SimpleGrantedAuthority(roles.getRole())));

        return simpleGrantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return pin;
    }

    @Override
    public String getUsername() {
        return mobileNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void addNewRole( Roles roles ) {
        this.roles.add(roles);
    }
}
