package de.hhn.labswps.wefactor.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.BaseWebTest;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;

public class UserControllerTest extends BaseWebTest {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Test
    public void showSignInPage() throws Exception {

        getMockMvc().perform(get("/signin")).andExpect(status().isOk())
                .andExpect(view().name("signin"));

    }

}
