package com.shopKpr.admin.service;

import com.shopKpr.admin.model.AdminAuthenticate;
import com.shopKpr.admin.model.ShopKprAdmin;
import com.shopKpr.admin.repository.AdminRegisterRepo;
import com.shopKpr.exceptions.AlreadyExists;
import com.shopKpr.security.model.AuthenticationResponse;
import com.shopKpr.security.model.Roles;
import com.shopKpr.security.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

import static com.shopKpr.security.constants.ApplicationUserRole.*;

@Service
@Slf4j
public class AdminRegistrationService{

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final AdminRegisterRepo adminRegisterRepo;

    public AdminRegistrationService( PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                                     JwtUtils jwtUtils, AdminRegisterRepo adminRegisterRepo ) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.adminRegisterRepo = adminRegisterRepo;
    }

    public void registerNewAdmin( ShopKprAdmin shopKprAdmin ) {

        Optional<ShopKprAdmin> shopKprAdminOptional =
                adminRegisterRepo.findByEmailAddress(shopKprAdmin.getEmailAddress());

        if (shopKprAdminOptional.isEmpty()) {
            Roles roles = new Roles();
            roles.setRole(ROLE_ADMIN.name());

            roles.setShopKprAdmin(shopKprAdmin);

            shopKprAdmin.addNewRole(roles);
            shopKprAdmin.setPassword(passwordEncoder.encode(shopKprAdmin.getPassword()));
            shopKprAdmin.setEnabled(true);
            shopKprAdmin.setAdminCreatedAt(new Date(System.currentTimeMillis()));

            adminRegisterRepo.save(shopKprAdmin);
        } else {
            log.info("User with email address: " + shopKprAdmin.getEmailAddress() + " already exists");
            throw new AlreadyExists("User with email: " + shopKprAdmin.getEmailAddress() + " already exists");
        }
    }

    public ResponseEntity<AuthenticationResponse> authenticateAdmin( AdminAuthenticate adminAuthenticate ) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(adminAuthenticate.getEmailAddress(), adminAuthenticate.getPassword());

        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (AuthenticationException authenticationException) {
            log.info("Admin with email address: " + adminAuthenticate.getEmailAddress() + " not found");
            throw new BadCredentialsException("Admin with email address: " + adminAuthenticate.getEmailAddress() + " not found");
        }

        Optional<ShopKprAdmin> shopKprAdminOptional =
                adminRegisterRepo.findByEmailAddress(adminAuthenticate.getEmailAddress());

        if (shopKprAdminOptional.isPresent()) {
            final UserDetails userDetails = shopKprAdminOptional.get();
            String token = jwtUtils.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(token));
        } else {
            log.info("Admin with email: " + adminAuthenticate.getEmailAddress() + " not found");
            throw new BadCredentialsException("Admin not found");
        }
    }
}
