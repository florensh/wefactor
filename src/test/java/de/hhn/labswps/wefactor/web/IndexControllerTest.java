package de.hhn.labswps.wefactor.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;

import de.hhn.labswps.wefactor.BaseWebTest;

/**
 * The Class IndexControllerTest.
 */
public class IndexControllerTest extends BaseWebTest {

    /**
     * Find index page.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void findIndexPage() throws Exception {
        getMockMvc().perform(get("/").principal(getTestPrincipal()))
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/entries/all"));

    }

}
