package de.hhn.labswps.wefactor.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;

import de.hhn.labswps.wefactor.BaseTest;

public class IndexControllerTest extends BaseTest {

    @Test
    public void findIndexPage() throws Exception {
        getMockMvc().perform(get("/").principal(getTestPrincipal()))
                .andExpect(status().isOk())
                .andExpect(view().name("forward:/entries/all"));

    }

}
