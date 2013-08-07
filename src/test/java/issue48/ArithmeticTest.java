package issue48;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ArithmeticTest {

    /**
     * Add a bunch of numbers, as provided by the data provider.
     * If there are n pieces of data provided, n-1 should pass and 1 should fail.
     */
    @Test(dataProvider = "dataProviderForSum")
    public void sum(int providedInt1, int providedInt2, int providedSum) {
        Arithmetic math = new Arithmetic();
        int calculatedSum = math.sum(providedInt1, providedInt2);
        assertEquals(calculatedSum, providedSum);
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
        assertTrue(true);
    }

    /**
     * Provide a user-specified amount of data to Arithmetic.sum(), which lets us trigger the specific
     * pass/fail rates that reproduce issue 48.
     */
    @DataProvider(name = "dataProviderForSum")
    public Object[][] provideDataForSum() {

        // these are hand-picked numbers to demonstrate the rounding bug
        final int numTestsTotal = 47;
        final int numTestsToPass = 46;

        // prep a structure to hold the data we'll provide
        // each datum consists of a triple: addend1, addend2, expected sum
        Object[][] data = new Object[numTestsTotal][3];

        final int addend1 = 1;
        final int addend2 = 1;
        final int correctExpectedSum = 2;
        final int incorrectExpectedSum = 999;

        // provide good data (i.e., it causes the test to pass) up until the very last datum
        Object[] goodDatum = new Object[]{addend1, addend2, correctExpectedSum};
        for (int i = 0; i < numTestsToPass; i++) {
            data[i] = goodDatum;
        }

        // provide bad data (i.e., it causes the test to fail) at the very end
        Object[] badDatum = new Object[] {addend1, addend2, incorrectExpectedSum};
        data[numTestsTotal - 1] = badDatum;

        return data;
    }
}
