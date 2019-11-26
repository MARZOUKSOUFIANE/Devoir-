package com.sid.security_service;

import com.sid.security_service.dao.AppRoleRepository;
import com.sid.security_service.dao.AppUserRepository;
import com.sid.security_service.entities.AppRole;
import com.sid.security_service.entities.AppUser;
import com.sid.security_service.service.AccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest

public class AccountServiceImplTest {

    @Mock
    AppRoleRepository appRoleRepository;
    @Mock
    AppUserRepository appUserRepository;
    @InjectMocks
    AccountServiceImpl accountService;


    @Test
    public void TestSaveRoleTest(){
        AppRole appRole=new AppRole((long) 1,"ADMIN");
        when(appRoleRepository.save(appRole)).thenReturn(appRole);
        assertEquals(appRole,accountService.saveRole(appRole));
    }

    @Test
    public void testAddUserRole(){
        String username="user";
        String rolename="ADMIN";
        when(appRoleRepository.findByRoleName(rolename)).thenReturn(new AppRole((long)1, "ADMIN"));
        when(appUserRepository.findByUsername(username)).thenReturn(new AppUser(username,"1234"));
        accountService.addUserRole(username,rolename);
        verify(appRoleRepository,times(1)).findByRoleName(rolename);
        verify(appUserRepository,times(1)).findByUsername(username);
    }






}
