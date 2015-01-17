package de.hhn.labswps.wefactor.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.BaseWebTest;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;

/**
 * The Class UserControllerTest.
 */
public class UserControllerTest extends BaseWebTest {

    /** The user profile repository. */
    @Autowired
    UserProfileRepository userProfileRepository;

    /**
     * Show sign in page.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void showSignInPage() throws Exception {

        getMockMvc().perform(get("/signin")).andExpect(status().isOk())
                .andExpect(view().name("signin"));

    }

}
