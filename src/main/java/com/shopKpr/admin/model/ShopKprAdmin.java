package com.shopKpr.admin.model;

import com.shopKpr.security.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table
@Entity
public class ShopKprAdmin implements UserDetails {
    @Id
    private String emailAddress;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String password;
    @Column(nullable = false)
    private String fullName;
    private String imageUrl;
    private boolean enabled;
    private Date adminCreatedAt;

    @OneToMany(mappedBy = "shopKprAdmin", orphanRemoval = true, fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Roles> rolesList = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();

        rolesList.forEach(roles -> simpleGrantedAuthorityList.add(
                new SimpleGrantedAuthority(roles.getRole())));

        return simpleGrantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return emailAddress;
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
        this.rolesList.add(roles);
    }
}
