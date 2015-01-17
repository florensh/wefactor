package de.hhn.labswps.wefactor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * The Class BaseTest.
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class BaseTest {

    /**
     * Test something.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testSomething() throws Exception {
        System.out.println("hello");

    }

}
