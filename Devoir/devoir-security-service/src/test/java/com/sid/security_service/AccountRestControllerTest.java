package com.sid.security_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.security_service.dao.AppRoleRepository;
import com.sid.security_service.entities.AppRole;
import com.sid.security_service.entities.AppUser;
import com.sid.security_service.service.AccountServiceImpl;
import com.sid.security_service.web.AccountRestController;
import com.sid.security_service.web.RoleForm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AccountRestControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    AccountRestController accountRestController;
    @Mock
    AccountServiceImpl accountService;
    @Mock
    AppRoleRepository appRoleRepository;

    ObjectMapper om=new ObjectMapper();

    @Before
    public void setUp() throws Exception{
        mockMvc= MockMvcBuilders.standaloneSetup(accountRestController).build();
    }

    @Test
    public void testSendMail() throws Exception{
        mockMvc.perform(
                post("/sendMail")
        )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("mail sent successfuly"));
    }

    @Test
    public void testAddRole() throws Exception{
        RoleForm roleForm=new RoleForm("ADMIN","user");

        AppUser appUser=new AppUser(roleForm.getUsername(),"1234");
        appUser.addRole(new AppRole((long) 1,"USER"));

        AppRole appRole=new AppRole((long) 1,roleForm.getRole());

        when(accountService.findUserByUsername(roleForm.getUsername())).thenReturn(appUser);
        when(appRoleRepository.findByRoleName(roleForm.getRole())).thenReturn(appRole);

        String jsonRequest=om.writeValueAsString(roleForm);
        MvcResult result=mockMvc.perform(post("/addRole").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent=result.getResponse().getContentAsString();
        AppUser user=om.readValue(resultContent,AppUser.class);

        Assert.assertEquals(roleForm.getUsername(),user.getUsername());
        Assert.assertEquals(2,user.getRoles().size());
        verify(accountService,times(1)).findUserByUsername(roleForm.getUsername());
        verify(appRoleRepository,times(1)).findByRoleName(roleForm.getRole());
    }


}
