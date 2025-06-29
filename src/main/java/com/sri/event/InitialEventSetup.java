package com.sri.event;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.sri.event.Entity.AuthoritiesEntity;
import com.sri.event.Entity.OwnerEntity;
import com.sri.event.Entity.RoleEntity;
import com.sri.event.Entity.UserEntity;
import com.sri.event.repo.AuthoritiesRepo;
import com.sri.event.repo.OwnerRepo;
import com.sri.event.repo.RoleRepo;
import com.sri.event.repo.UserRepo;
import com.sri.event.shared.Authority;
import com.sri.event.shared.Role;
import com.sri.event.shared.Utills;

import jakarta.transaction.Transactional;

@Component
public class InitialEventSetup {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private OwnerRepo ownerRepo;

    @Autowired
    private AuthoritiesRepo authoritiesRepo;

    @Autowired
    private Utills utils;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        // Create authorities
        AuthoritiesEntity read = createAuthority(Authority.READ.name());
        AuthoritiesEntity write = createAuthority(Authority.WRITE.name());
        AuthoritiesEntity delete = createAuthority(Authority.DELETE.name());

        // Create roles
        createRole(Role.ROLE_USER.name(), Arrays.asList(read, write));
        RoleEntity ownerRole =	createRole(Role.ROLE_OWNER.name(),Arrays.asList(read, write));
        RoleEntity adminRole = createRole(Role.ROLE_ADMIN.name(), Arrays.asList(read, write, delete));

        if (adminRole == null) return;

        // Create default admin user
        UserEntity adminUser = new UserEntity();
        adminUser.setFname("Admin");
        adminUser.setLname("User");
        adminUser.setEmail("admin@event.com");
        adminUser.setUserId(utils.generateUserId(20));
        adminUser.setEncrypassword(passwordEncoder.encode("admin123"));
        adminUser.setRoles(Arrays.asList(adminRole));

        // Save only if not exists
        if (userRepo.findByEmail("admin@event.com") == null) {
            userRepo.save(adminUser);
        }
        
        
        if(ownerRole==null) return;
        
        //Create default-- admin as owner
        OwnerEntity adminowner= new OwnerEntity();
        adminowner.setFname("Admin");
        adminowner.setLname("Owner");
        adminowner.setEmail("admin@event.com");
        adminowner.setOwnerId(utils.generateOwnerId(20));
        adminowner.setEncrypassword(passwordEncoder.encode("admin123"));
        adminowner.setRoles(Arrays.asList(ownerRole));
        
        if(ownerRepo.findByEmail("admin@event.com")==null){
        	ownerRepo.save(adminowner);
        }
    }

    @Transactional
    private AuthoritiesEntity createAuthority(String name) {
        AuthoritiesEntity existing = authoritiesRepo.findByName(name);
        if (existing == null) {
            AuthoritiesEntity authority = new AuthoritiesEntity(name);
            return authoritiesRepo.save(authority);
        }
        return existing;
    }

    @Transactional
    private RoleEntity createRole(String name, Collection<AuthoritiesEntity> authorities) {
        RoleEntity existing = roleRepo.findByName(name);
        if (existing == null) {
            RoleEntity role = new RoleEntity(name);
            role.setAuthorities(authorities);
            return roleRepo.save(role);
        }
        return existing;
    }
}

