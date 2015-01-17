package de.hhn.labswps.wefactor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.security.Principal;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithSecurityContextTestExcecutionListener;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import de.hhn.labswps.wefactor.domain.AccountRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;

/**
 * The Class BaseWebTest.
 */
@WebAppConfiguration
@TestExecutionListeners(listeners = { ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, BeforeClassHook.class,
        WithSecurityContextTestExcecutionListener.class })
@WithUserDetails("weFactor_testuser")
@Transactional
public class BaseWebTest extends BaseTest {

    /** The account repository. */
    @Autowired
    AccountRepository accountRepository;

    /** The user profile repository. */
    @Autowired
    UserProfileRepository userProfileRepository;

    /**
     * Gets the session.
     *
     * @return the session
     */
    public MockHttpSession getSession() {
        return this.session;
    }

    /**
     * Gets the request.
     *
     * @return the request
     */
    public MockHttpServletRequest getRequest() {
        return this.request;
    }

    /**
     * Gets the mock mvc.
     *
     * @return the mock mvc
     */
    public MockMvc getMockMvc() {
        return this.mockMvc;
    }

    /** The wac. */
    @Autowired
    private WebApplicationContext wac;

    /** The session. */
    @Autowired
    private MockHttpSession session;

    /** The request. */
    @Autowired
    private MockHttpServletRequest request;

    /** The mock mvc. */
    private MockMvc mockMvc;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /*
     * (non-Javadoc)
     * @see de.hhn.labswps.wefactor.BaseTest#testSomething()
     */
    @Override
    @Test
    public void testSomething() throws Exception {
        this.getMockMvc().perform(get("/").principal(this.getTestPrincipal()))
        .andExpect(status().isOk())
        .andExpect(view().name("forward:/entries/all"));

    }

    /**
     * Gets the test profile.
     *
     * @return the test profile
     */
    protected UserProfile getTestProfile() {
        return this.userProfileRepository.findByUsername("weFactor_testuser");

    }

    /**
     * Gets the test principal.
     *
     * @return the test principal
     */
    protected Principal getTestPrincipal() {
        return new Principal() {
            @Override
            public String getName() {
                return "weFactor_testuser";
            }
        };

    }

}
