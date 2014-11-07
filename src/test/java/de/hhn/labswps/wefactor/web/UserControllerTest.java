package de.hhn.labswps.wefactor.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.BaseTest;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;

public class UserControllerTest extends BaseTest {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Test
    public void showSignInPage() throws Exception {

        getMockMvc().perform(get("/signin")).andExpect(status().isOk())
                .andExpect(view().name("signin"));

    }

    // @Test
    // public void signIn() throws Exception {
    // UserProfile userProfile = new UserProfile("1", "name", "firstName",
    // "secondName", "mail@mail.de", "testuser");
    //
    // userProfile.setPassword("123");
    //
    // userProfileRepository.save((UserProfile) userProfile);
    //
    // getMockMvc()
    // .perform(
    // post("/login/authenticate").param("username",
    // "testuser").param("password", "123"))
    //
    // .andExpect(status().isOk()).andExpect(view().name("/"));
    //
    // }

}
