package com.shopKpr.security.service;

import com.shopKpr.admin.model.ShopKprAdmin;
import com.shopKpr.admin.repository.AdminRegisterRepo;
import com.shopKpr.shopKprRegister.repository.jpaRepo.ShopKprRegistrationRepository;
import com.shopKpr.shopKprRegister.model.ShopKpr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Optional;

@Slf4j
@Service
public class ShopKprDetailsService implements UserDetailsService {

    private final ShopKprRegistrationRepository shopKprRegistrationRepository;
    private final AdminRegisterRepo adminRegisterRepo;

    public ShopKprDetailsService( ShopKprRegistrationRepository shopKprRegistrationRepository,
                                  AdminRegisterRepo adminRegisterRepo ) {
        this.shopKprRegistrationRepository = shopKprRegistrationRepository;
        this.adminRegisterRepo = adminRegisterRepo;
    }


    @Override
    public UserDetails loadUserByUsername( String s ) throws UsernameNotFoundException {

        if (isValidEmailAddress(s)) {
            Optional<ShopKprAdmin> shopKprAdminOptional = adminRegisterRepo.findByEmailAddress(s);

            if (shopKprAdminOptional.isPresent())
                return shopKprAdminOptional.get();
            else {
                log.info("Admin with email : " + s + " does not exists");
                throw new BadCredentialsException("Admin Does not exists");
            }
        } else {

            Optional<ShopKpr> shopKprOptional = shopKprRegistrationRepository.findByMobileNumber(s);

            if (shopKprOptional.isPresent())
                return shopKprOptional.get();
            else {
                log.info("User with mobile : " + s + " does not exists");
                throw new BadCredentialsException("User Does not exists");
            }
        }
    }

    public static boolean isValidEmailAddress( String email ) {

        boolean result = true;

        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }

        return result;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
