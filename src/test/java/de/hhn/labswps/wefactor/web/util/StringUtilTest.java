package de.hhn.labswps.wefactor.web.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.hhn.labswps.wefactor.BaseWebTest;

/**
 * @author Patrick Wohlgemuth
 *         The Class StringUtilTest.
 */
public class StringUtilTest extends BaseWebTest {

    @Autowired
    private StringUtil stringUtil;

    /**
     * Test string cut.
     */
    @Test
    public void CutStringTest() {
        String s = "testEmailAdresse@test.de";
        int i = 17;

        String result = this.stringUtil.getCutString(s, i);
        assertEquals("testEmailAdresse@...", result);
    }

    /**
     * Test string uncutet
     */
    @Test
    public void UncutetCutStringTest() {
        String s = "testmail@test.de";
        int i = 17;

        String result = this.stringUtil.getCutString(s, i);
        assertEquals("testmail@test.de", result);
    }

    /**
     * Test string empty
     */
    @Test
    public void EmptyStringTest() {
        String s = "";
        int i = 17;

        String result = this.stringUtil.getCutString(s, i);
        assertEquals("", result);

    }

}
