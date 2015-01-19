package de.hhn.labswps.wefactor.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;

import de.hhn.labswps.wefactor.BaseWebTest;
import de.hhn.labswps.wefactor.domain.UserProfile;

/**
 * The Class UserControllerTest.
 */
public class UserControllerTest extends BaseWebTest {

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

    @Test
    public void showProfilePage() throws Exception {
        UserProfile up = getTestProfile();

        getMockMvc()
                .perform(
                        get("/user/profile/details?id=" + up.getId())
                                .principal(getTestPrincipal()))
                .andExpect(status().isOk()).andExpect(view().name("profile"));

    }

}
