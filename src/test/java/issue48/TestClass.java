package issue48;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests and data providers to trigger four different use cases:
 * - Normal case of rounding up
 * - Normal case of rounding down
 * - Corner case of rounding up to 1%
 * - Corner case of rounding down to 99%
 */
public class TestClass {

    /* ===============================
     *            TESTS
     * ===============================
     */

    @Test(dataProvider = "roundUpTo96DataProvider")
    public void roundUpTo96(Boolean b) {
        validateTrue(b);
    }

    @Test(dataProvider = "roundUpTo1DataProvider")
    public void roundUpTo1(Boolean b) {
        validateTrue(b);
    }

    @Test(dataProvider = "roundDownTo64DataProvider")
    public void roundDownTo64(Boolean b) {
        validateTrue(b);
    }

    @Test(dataProvider = "roundDownTo99DataProvider")
    public void roundDownTo99(Boolean b) {
        validateTrue(b);
    }

    /**
     * Actual test logic. Pass on true, fail on false, skip on null.
     */
    private void validateTrue(Boolean b) {
        if (b == null) {
            throw new SkipException("deliberately skipped");
        }
        else {
            assertTrue(b);
        }
    }


    /* ===============================
     *        DATA PROVIDERS
     * ===============================
     */

    /* goodDatum causes test to pass */
    static Object[] goodDatum = new Object[]{true};

    /* badDatum causes test to fail */
    static Object[] badDatum = new Object[]{false};

    /* skipDatum causes test to skip */
    static Object[] skipDatum =new Object[]{null};

    /**
     * Provide data that tests the normal case of rounding up.
     * 46 pass / 48 total = 95.83% pass, so should round up to 96% in the report
     */
    @DataProvider(name = "roundUpTo96DataProvider")
    public Object[][] roundUpTo96DataProvider() {
        int passCount = 46;
        int failCount = 1;
        int skipCount = 1;
        return generateData(passCount, failCount, skipCount);
    }

    /**
     * Provide data that tests the normal case of rounding down.
     * 9 pass / 14 total = 64.29% pass, so should round down to 64% in the report
     */
    @DataProvider(name = "roundDownTo64DataProvider")
    public Object[][] roundDownTo64DataProvider() {
        int passCount = 9;
        int failCount = 3;
        int skipCount = 2;
        return generateData(passCount, failCount, skipCount);
    }

    /**
     * Provide data that tests the corner case of rounding down to 99%.
     * 499 pass / 500 total = 99.8% pass, so should round down to 99% in the report
     */
    @DataProvider(name = "roundDownTo99DataProvider")
    public Object[][] roundDownTo99DataProvider() {
        int passCount = 499;
        int failCount = 1;
        int skipCount = 0;
        return generateData(passCount, failCount, skipCount);
    }

    /**
     * Provide data that tests the corner case of rounding up to 1%.
     * 1 pass / 500 total = 0.2% pass, so should round up to 1% in the report
     */
    @DataProvider(name = "roundUpTo1DataProvider")
    public Object[][] roundUpTo1DataProvider() {
        int passCount = 1;
        int failCount = 499;
        int skipCount = 0;
        return generateData(passCount, failCount, skipCount);
    }

    /**
     * Helper method for DataProviders
     */
    private Object[][] generateData(int passCount, int failCount, int skipCount) {

        /* prep a structure to hold ALL the data we'll provide */
        Object[][] data = new Object[passCount + failCount + skipCount][1];

        /* generate data to cause passes */
        for (int i = 0; i < passCount; i++) {
            data[i] = goodDatum;
        }

        /* generate data to cause failures */
        for (int i = passCount; i < passCount + failCount; i++) {
            data[i] = badDatum;
        }

        /* generate data to cause skips */
        for (int i = passCount + failCount; i < passCount + failCount + skipCount; i++) {
            data[i] = skipDatum;
        }

        return data;
    }
}
