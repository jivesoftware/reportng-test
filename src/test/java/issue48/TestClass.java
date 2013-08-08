package issue48;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class TestClass {

    @Test(dataProvider = "dataProvider1")
    public void validateAsTrue(boolean b) {
        assertTrue(b);
    }

    /**
     * Provide a user-specified amount of data to Arithmetic.sum(), which lets us trigger the specific
     * pass/fail rates that reproduce issue 48.
     */
    @DataProvider(name = "dataProvider1")
    public Object[][] provideData() {

        // hand-picked numbers to demonstrate the rounding bug
        final int numTestsTotal = 47;
        final int numTestsToPass = 46;

        // goodDatum causes test to pass, badDatum causes test to fail
        Object[] goodDatum = new Object[]{true};
        Object[] badDatum = new Object[]{false};

        // prep a structure to hold ALL the data we'll provide
        Object[][] data = new Object[numTestsTotal][1];

        // provide good data until the very last datum
        for (int i = 0; i < numTestsToPass; i++) {
            data[i] = goodDatum;
        }

        // provide bad data at the very end
        data[numTestsTotal - 1] = badDatum;

        return data;
    }

    /**
     * This test should fail, causing shouldSkip() to be skipped.
     */
    @Test
    public void shouldFail() {
        assertTrue(false);
    }

    /**
     * TestNG should skip this test, because it depends on shouldFail(), which should fail.
     */
    @Test(dependsOnMethods = { "shouldFail" })
    public void shouldSkip() {
    }
}
