package Interpolation;

import org.junit.*;

import RingsPackage.BigIntegerRing;
import RingsPackage.DoubleRing;
import RingsPackage.IntegerRing;
import RingsPackage.Polynomial;
import RingsPackage.PolynomialRing;
import RingsPackage.Ring;
import static org.junit.Assert.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class InterpolatorTest {

    //interger ring for computations
    private IntegerRing integerRing = new IntegerRing();
    //big integer ring for computations
    private BigIntegerRing bigIntegerRing = new BigIntegerRing();
    //interpolator for computations
    private Interpolator interpolator = new Interpolator();
    //polynomial to hold inner test classes
    private Polynomial<Integer> container = Polynomial.from(Arrays.asList());

    //TESTS FOR INTERPOLATOR

    /**
     * Interpolator Test Condition 1: dataList is empty
     */
    @Test
    public void testInterpolatorDataListEmpty() {
        List<Integer> dataList = Arrays.asList();
        PolynomialRing<Integer> polyRing = PolynomialRing.instance(integerRing);
        assertEquals(0, interpolator.interpolatingPolynomial(dataList, integerRing).getCoefficients().size());
    }

    /**
     * Interpolator Test Condition 2: dataList is very large
     */
    @Test
    public void testInterpolatorLargeDataList() {
        List<BigInteger> dataList = Arrays.asList(BigInteger.valueOf(1), BigInteger.valueOf(3), BigInteger.valueOf(5), 
        BigInteger.valueOf(6), BigInteger.valueOf(10), BigInteger.valueOf(21), BigInteger.valueOf(30),
        BigInteger.valueOf(52), BigInteger.valueOf(101), BigInteger.valueOf(2563));
        String output = "[1, -2792, 605939, -49481354, 1947182891, -40446213740, 458258027533, -2847656481738, 9376974622260," +
        " -14581344951000, 7632316692000]";
        assertEquals(output, interpolator.interpolatingPolynomial(dataList, bigIntegerRing).toString());
    }

    /**
     * Interpolator Test Condition 3: dataList is null
     */
    @Test
    public void testInterpolatorNullDataList() {
        List<Integer> dataList = null;
        assertThrows(NullPointerException.class, () -> interpolator.interpolatingPolynomial(dataList, integerRing));
    }

    /**
     * Interpolator Test Condition 4: ring is null
     */
    @Test
    public void testInterpolatorNullRing() {
        List<Integer> dataList = Arrays.asList(1, 2, 3);
        assertThrows(NullPointerException.class, () -> interpolator.interpolatingPolynomial(dataList, null));
    }

    /**
     * Interpolator Test Condition 5: data value is null is null
     */
    @Test
    public void testInterpolatorNullDataValue() {
        List<Integer> dataList = Arrays.asList(1, null, 3);
        assertThrows(NullPointerException.class, () -> interpolator.interpolatingPolynomial(dataList, integerRing));
    }


    //TESTS FOR POLYNOMIAL TIMES

    /**
     * Polynomial Test Condition 1: iteration < size
     */
    @Test
    public void testTimesIterationLessSize() {
        Polynomial<Integer> poly1 = Polynomial.from(Arrays.asList(1, 2, 3));
        Polynomial<Integer> poly2 = Polynomial.from(Arrays.asList(4, 5));
        String output = "[4, 13, 22, 15]";
        assertEquals(output, poly1.times(poly2, integerRing).toString());
    }

    /**
     * Polynomial Times Test Condition 2: this and other Polynomial empty
     */
    @Test
    public void testTimesPolynomialsEmpty() {
        Polynomial<Integer> poly1 = Polynomial.from(Arrays.asList());
        Polynomial<Integer> poly2 = Polynomial.from(Arrays.asList());
        assertEquals(0, poly1.times(poly2, integerRing).getCoefficients().size());
    }

    /**
     * Polynomial Times Test Condition 3: other Polynomial null
     */
    @Test
    public void testTimesOtherPolynomialNull() {
        Polynomial<Integer> poly1 = Polynomial.from(Arrays.asList(1, 2, 3));
        Polynomial<Integer> poly2 = Polynomial.from(Arrays.asList(4, 5));
        assertThrows(NullPointerException.class, () -> poly1.times(poly2, null));
    }

    /**
     * Polynomial Times Test Condition 4: ring is null
     */
    @Test
    public void testTimesRingNull() {
        Polynomial<Integer> poly1 = Polynomial.from(Arrays.asList(1, 2, 3));
        Polynomial<Integer> poly2 = null;
        assertThrows(NullPointerException.class, () -> poly1.times(poly2, integerRing));
    }


    //TESTS FOR POLYNOMIAL PRIVATE HELPER CALCULATEOFFSET

    /**
     * Polynomial CalculateOffset Test Condition 1: iteration > listSize, listSize = 0
     */
    @Test
    public void testCalculateOffsetIterationGreater() {
        Polynomial.TestClass test = container.new TestClass();
        assertEquals(3, test.calculateOffset(5, 3));
    }

    /**
     * Polynomial CalculateOffset Test Condition 4: iteration = listSize
     */
    @Test
    public void testCalculateOffsetIterationLesser() {
        Polynomial.TestClass test = container.new TestClass();
        assertEquals(0, test.calculateOffset(3, 5));
    }

    /**
     * Polynomial CalculateOffset Test Condition 3: iteration = listSize
     */
    @Test
    public void testCalculateOffsetSizeEqual() {
        Polynomial.TestClass test = container.new TestClass();
        assertEquals(1, test.calculateOffset(5, 5));
    }

    /**
     * Polynomial CalculateOffset Test Condition 4: iteration = listSize
     */
    @Test
    public void testCalculateOffsetNegativeSize() {
        Polynomial.TestClass test = container.new TestClass();
        assertEquals(11, test.calculateOffset(5, -5));
    }

    /**
     * Polynomial CalculateOffset Test Condition 5: iteration = listSize
     */
    @Test
    public void testCalculateOffsetNegativeIteration() {
        Polynomial.TestClass test = container.new TestClass();
        assertEquals(0, test.calculateOffset(-5, 5));
    }


    //TESTS FOR POLYNOMIAL PRIVATE HELPER CANINCREMENTTIMES

    /**
     * Polynomial CanIncrementTimes Test Condition 1: forward hasNext
     */
    @Test
    public void testCanIncrementTimesForwardNext() {
        Polynomial<Integer> poly = Polynomial.from(Arrays.asList(1,2,3,4,5));
        ListIterator<Integer> listIterator = poly.listIterator(1);
        ListIterator<Integer> backIterator = poly.listIterator(1);
        Polynomial.TestClass test = container.new TestClass();
        assertTrue(test.canIncrementTimes(listIterator, backIterator));
    }

    /**
     * Polynomial CanIncrementTimes Test Condition 2: forward does not have next
     */
    @Test
    public void testCanIncrementTimesForwardNotNext() {
        Polynomial<Integer> poly = Polynomial.from(Arrays.asList(1,2,3,4,5));
        ListIterator<Integer> listIterator = poly.listIterator(5);
        ListIterator<Integer> backIterator = poly.listIterator(1);
        Polynomial.TestClass test = container.new TestClass();
        assertTrue(!test.canIncrementTimes(listIterator, backIterator));
    }

    /**
     * Polynomial CanIncrementTimes Test Condition 3: forward is null
     */
    @Test
    public void testCanIncrementTimesForwardNull() {
        Polynomial<Integer> poly = Polynomial.from(Arrays.asList(1,2,3,4,5));
        ListIterator<Integer> backIterator = poly.listIterator(1);
        Polynomial.TestClass test = container.new TestClass();
        assertThrows(NullPointerException.class, () -> test.canIncrementTimes(null, backIterator));
    }

    /**
     * Polynomial CanIncrementTimes Test Condition 4: backward is null
     */
    @Test
    public void testCanIncrementTimesBackwardNull() {
        Polynomial<Integer> poly = Polynomial.from(Arrays.asList(1,2,3,4,5));
        ListIterator<Integer> listIterator = poly.listIterator(1);
        Polynomial.TestClass test = container.new TestClass();
        assertThrows(NullPointerException.class, () -> test.canIncrementTimes(listIterator, null));
    }


    //STRESS TEST
    /**
     * Generate a set of random length between 0 and 25, with first entry the integer 1, 
     * and all subsequent entries of random integers of value between -1000 and 1000. 
     * Interpolate the data set, and check that the sum of the coefficients of the output = 0. 
     * Repeat 50 times.
     */

     @Test
     public void stressTestInterpolation() {
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int length = random.nextInt(25);   //new random integer between 0 and 25 (bound exclusive)
            List<BigInteger> list = new ArrayList();
            list.add(BigInteger.valueOf(1));
            for (int j = 0; j < length; j++) {
                list.add(BigInteger.valueOf(random.nextInt(1000)));
            }
            Polynomial interpolation = interpolator.interpolatingPolynomial(list, bigIntegerRing);
            List<BigInteger> coefficients = interpolation.getCoefficients();
            int sum = 0;
            for (BigInteger value : coefficients) {
                sum += value.intValue();
            }
            assertEquals(0, sum);
        }
     }


    //LEGACY GOOD DATA TESTS


    @Test
    public void legacyTestInterpolatingPolynomial() {
        Ring<Integer> ring = new IntegerRing();
        Interpolator interpol = new Interpolator();
        //test empty input list
        List<Integer> list1 = Arrays.asList();
        assertEquals("[]", interpol.interpolatingPolynomial(list1, ring).toString());
        //test one element long input list
        List<Integer> list2 = Arrays.asList(2);
        assertEquals("[1, -2]", interpol.interpolatingPolynomial(list2, ring).toString());
        //test multiple elements input list
        List<Integer> list3 = Arrays.asList(1, 3, 4, 7);
        assertEquals("[1, -15, 75, -145, 84]", interpol.interpolatingPolynomial(list3, ring).toString());
    }

    //LEGACY GOOD DATA POLYNOMIAL MuLTIPLICATION TESTS
    //test zero
    @Test
    public void testZero() {
        //test BigInteger
        PolynomialRing<BigInteger> bigRing = PolynomialRing.instance(new BigIntegerRing());
        assertEquals(true, bigRing.zero().getCoefficients().isEmpty());

        //test Double
        PolynomialRing<Double> doubleRing = PolynomialRing.instance(new DoubleRing());
        assertEquals(true, doubleRing.zero().getCoefficients().isEmpty());

        //test Polynomial<Integer>
        PolynomialRing<Polynomial<Integer>> polyInteger = PolynomialRing.instance(PolynomialRing.instance(new IntegerRing()));
        assertEquals(true, polyInteger.zero().getCoefficients().isEmpty());
    }

    //test identity
    @Test
    public void testIdentity() {
        //test BigInteger
        PolynomialRing<BigInteger> bigRing = PolynomialRing.instance(new BigIntegerRing());
        assertEquals(1, bigRing.identity().getCoefficients().size());                      //should be one long
        assertEquals(BigInteger.ONE, bigRing.identity().getCoefficients().get(0));

        //test Double
        PolynomialRing<Double> doubleRing = PolynomialRing.instance(new DoubleRing());
        assertEquals(1, doubleRing.identity().getCoefficients().size());                      //should be one long
        assertEquals(Double.valueOf(1), doubleRing.identity().getCoefficients().get(0));

        //test Polynomial<Integer>
        PolynomialRing<Polynomial<Integer>> polyInteger = PolynomialRing.instance(PolynomialRing.instance(new IntegerRing()));
        assertEquals(1, polyInteger.identity().getCoefficients().size());                      //should be one long
        assertEquals(Integer.valueOf(1), polyInteger.identity().getCoefficients().get(0).getCoefficients().get(0));
    }
    
    //test sum
    @Test
    public void testSum() {
        //test BigInteger
        PolynomialRing<BigInteger> bigRing = PolynomialRing.instance(new BigIntegerRing());
        //test 0 long
        Polynomial<BigInteger> p1 = Polynomial.from(Arrays.asList());
        Polynomial<BigInteger> q1 = Polynomial.from(Arrays.asList());
        assertEquals("[]", bigRing.sum(p1, q1).toString());
        //test 1 long
        Polynomial<BigInteger> p2 = Polynomial.from(Arrays.asList(BigInteger.ONE));
        Polynomial<BigInteger> q2 = Polynomial.from(Arrays.asList(BigInteger.valueOf(2)));
        assertEquals(BigInteger.valueOf(3), bigRing.sum(p2, q2).getCoefficients().get(0));
        assertEquals(1, bigRing.sum(p2, q2).getCoefficients().size());
        //test many long same size
        Polynomial<BigInteger> p3 = Polynomial.from(Arrays.asList(BigInteger.ONE, BigInteger.valueOf(2), BigInteger.valueOf(3)));
        Polynomial<BigInteger> q3 = Polynomial.from(Arrays.asList(BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4)));
        assertEquals(BigInteger.valueOf(3), bigRing.sum(p3, q3).getCoefficients().get(0));
        assertEquals(BigInteger.valueOf(5), bigRing.sum(p3, q3).getCoefficients().get(1));
        assertEquals(BigInteger.valueOf(7), bigRing.sum(p3, q3).getCoefficients().get(2));
        assertEquals(3, bigRing.sum(p3, q3).getCoefficients().size());
        //test first larger
        Polynomial<BigInteger> p4 = Polynomial.from(Arrays.asList(BigInteger.ONE, BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4)));
        assertEquals(BigInteger.valueOf(3), bigRing.sum(p4, q3).getCoefficients().get(0));
        assertEquals(BigInteger.valueOf(5), bigRing.sum(p4, q3).getCoefficients().get(1));
        assertEquals(BigInteger.valueOf(7), bigRing.sum(p4, q3).getCoefficients().get(2));
        assertEquals(BigInteger.valueOf(4), bigRing.sum(p4, q3).getCoefficients().get(3));
        assertEquals(4, bigRing.sum(p4, q3).getCoefficients().size());
        //test second larger
        assertEquals(BigInteger.valueOf(3), bigRing.sum(q3, p4).getCoefficients().get(0));
        assertEquals(BigInteger.valueOf(5), bigRing.sum(q3, p4).getCoefficients().get(1));
        assertEquals(BigInteger.valueOf(7), bigRing.sum(q3, p4).getCoefficients().get(2));
        assertEquals(BigInteger.valueOf(4), bigRing.sum(q3, p4).getCoefficients().get(3));
        assertEquals(4, bigRing.sum(q3, p4).getCoefficients().size());

        //test Double
        PolynomialRing<Double> doubleRing = PolynomialRing.instance(new DoubleRing());
        //test 0 long
        Polynomial<Double> x1 = Polynomial.from(Arrays.asList());
        Polynomial<Double> y1 = Polynomial.from(Arrays.asList());
        assertEquals("[]", doubleRing.sum(x1, y1).toString());
        //test 1 long
        Polynomial<Double> x2 = Polynomial.from(Arrays.asList(1.));
        Polynomial<Double> y2 = Polynomial.from(Arrays.asList(2.));
        assertEquals(Double.valueOf(3), doubleRing.sum(x2, y2).getCoefficients().get(0));
        assertEquals(1, doubleRing.sum(x2, y2).getCoefficients().size());
        //test many long same size
        x2 = Polynomial.from(Arrays.asList(1., 2., 3.));
        y2 = Polynomial.from(Arrays.asList(2., 3., 4.));
        assertEquals(Double.valueOf(3), doubleRing.sum(x2, y2).getCoefficients().get(0));
        assertEquals(Double.valueOf(5), doubleRing.sum(x2, y2).getCoefficients().get(1));
        assertEquals(Double.valueOf(7), doubleRing.sum(x2, y2).getCoefficients().get(2));
        assertEquals(3, doubleRing.sum(x2, y2).getCoefficients().size());
        //test first larger
        x2 = Polynomial.from(Arrays.asList(1., 2., 3., 4.));
        assertEquals(Double.valueOf(3), doubleRing.sum(x2, y2).getCoefficients().get(0));
        assertEquals(Double.valueOf(5), doubleRing.sum(x2, y2).getCoefficients().get(1));
        assertEquals(Double.valueOf(7), doubleRing.sum(x2, y2).getCoefficients().get(2));
        assertEquals(Double.valueOf(4), doubleRing.sum(x2, y2).getCoefficients().get(3));
        assertEquals(4, doubleRing.sum(x2, y2).getCoefficients().size());
        //test second larger
        assertEquals(Double.valueOf(3), doubleRing.sum(y2, x2).getCoefficients().get(0));
        assertEquals(Double.valueOf(5), doubleRing.sum(y2, x2).getCoefficients().get(1));
        assertEquals(Double.valueOf(7), doubleRing.sum(y2, x2).getCoefficients().get(2));
        assertEquals(Double.valueOf(4), doubleRing.sum(y2, x2).getCoefficients().get(3));
        assertEquals(4, doubleRing.sum(y2, x2).getCoefficients().size());   
        
        //test Polynomial<Integer>
        PolynomialRing<Polynomial<Integer>> polyIntRing = PolynomialRing.instance(PolynomialRing.instance(new IntegerRing()));
        //test 0 long
        Polynomial<Polynomial<Integer>> a1 = Polynomial.from(Arrays.asList());
        Polynomial<Polynomial<Integer>> b1 = Polynomial.from(Arrays.asList());
        assertEquals("[]", polyIntRing.sum(a1, b1).toString());
        //test 1 long
        a1 = Polynomial.from(Arrays.asList(Polynomial.from(Arrays.asList(Integer.valueOf(1)))));
        b1 = Polynomial.from(Arrays.asList(Polynomial.from(Arrays.asList(Integer.valueOf(2)))));
        assertEquals(Integer.valueOf(3), polyIntRing.sum(a1, b1).getCoefficients().get(0).getCoefficients().get(0));
        assertEquals(1, polyIntRing.sum(a1, b1).getCoefficients().size());
        //test many long same size
        a1 = Polynomial.from(Arrays.asList(Polynomial.from(Arrays.asList(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3)))));
        b1 = Polynomial.from(Arrays.asList(Polynomial.from(Arrays.asList(Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4)))));
        assertEquals("[[3, 5, 7]]", polyIntRing.sum(a1, b1).toString());
        //test first larger
        a1 = Polynomial.from(Arrays.asList(Polynomial.from(Arrays.asList(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4)))));;
        assertEquals("[[3, 5, 7, 4]]", polyIntRing.sum(a1, b1).toString());
        //test sceond larger
        assertEquals("[[3, 5, 7, 4]]", polyIntRing.sum(b1, a1).toString());
    }

    //test product
    @Test
    public void testProduct() {
        //test BigInteger
        PolynomialRing<BigInteger> bigRing = PolynomialRing.instance(new BigIntegerRing());
        //test 0 long
        Polynomial<BigInteger> p1 = Polynomial.from(Arrays.asList());
        Polynomial<BigInteger> q1 = Polynomial.from(Arrays.asList());
        assertEquals("[]", bigRing.product(p1, q1).toString());
        //test 1 long
        p1 = Polynomial.from(Arrays.asList(BigInteger.valueOf(1)));
        q1 = Polynomial.from(Arrays.asList(BigInteger.valueOf(4)));
        assertEquals(BigInteger.valueOf(4), bigRing.product(p1, q1).getCoefficients().get(0));
        assertEquals(1, bigRing.product(p1, q1).getCoefficients().size());
        //test many long same size
        p1 = Polynomial.from(Arrays.asList(BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3)));
        q1 = Polynomial.from(Arrays.asList(BigInteger.valueOf(4), BigInteger.valueOf(5), BigInteger.valueOf(6)));
        assertEquals(BigInteger.valueOf(4), bigRing.product(p1, q1).getCoefficients().get(0));
        assertEquals(BigInteger.valueOf(13), bigRing.product(p1, q1).getCoefficients().get(1));
        assertEquals(BigInteger.valueOf(28), bigRing.product(p1, q1).getCoefficients().get(2));
        assertEquals(BigInteger.valueOf(27), bigRing.product(p1, q1).getCoefficients().get(3));
        assertEquals(BigInteger.valueOf(18), bigRing.product(p1, q1).getCoefficients().get(4));
        assertEquals(5, bigRing.product(p1, q1).getCoefficients().size());
        //test first larger
        p1 = Polynomial.from(Arrays.asList(BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4)));
        assertEquals(BigInteger.valueOf(4), bigRing.product(p1, q1).getCoefficients().get(0));
        assertEquals(BigInteger.valueOf(13), bigRing.product(p1, q1).getCoefficients().get(1));
        assertEquals(BigInteger.valueOf(28), bigRing.product(p1, q1).getCoefficients().get(2));
        assertEquals(BigInteger.valueOf(43), bigRing.product(p1, q1).getCoefficients().get(3));
        assertEquals(BigInteger.valueOf(38), bigRing.product(p1, q1).getCoefficients().get(4));
        assertEquals(BigInteger.valueOf(24), bigRing.product(p1, q1).getCoefficients().get(5));
        assertEquals(6, bigRing.product(p1, q1).getCoefficients().size());
        //test second larger
        assertEquals(BigInteger.valueOf(4), bigRing.product(q1, p1).getCoefficients().get(0));
        assertEquals(BigInteger.valueOf(13), bigRing.product(q1, p1).getCoefficients().get(1));
        assertEquals(BigInteger.valueOf(28), bigRing.product(q1, p1).getCoefficients().get(2));
        assertEquals(BigInteger.valueOf(43), bigRing.product(q1, p1).getCoefficients().get(3));
        assertEquals(BigInteger.valueOf(38), bigRing.product(q1, p1).getCoefficients().get(4));
        assertEquals(BigInteger.valueOf(24), bigRing.product(q1, p1).getCoefficients().get(5));
        assertEquals(6, bigRing.product(q1, p1).getCoefficients().size());

        //test Double
        PolynomialRing<Double> doubleRing = PolynomialRing.instance(new DoubleRing());
        //test 0 long
        Polynomial<Double> x1 = Polynomial.from(Arrays.asList());
        Polynomial<Double> y1 = Polynomial.from(Arrays.asList());
        assertEquals("[]", doubleRing.product(x1, y1).toString());
        //test 1 long
         x1 = Polynomial.from(Arrays.asList(Double.valueOf(1)));
         y1 = Polynomial.from(Arrays.asList(Double.valueOf(4)));
        assertEquals(Double.valueOf(4), doubleRing.product(x1, y1).getCoefficients().get(0));
        assertEquals(1, doubleRing.product(x1, y1).getCoefficients().size());
        //test many long same size
        x1 = Polynomial.from(Arrays.asList(Double.valueOf(1), Double.valueOf(2), Double.valueOf(3)));
        y1 = Polynomial.from(Arrays.asList(Double.valueOf(4), Double.valueOf(5), Double.valueOf(6)));
        assertEquals(Double.valueOf(4), doubleRing.product(x1, y1).getCoefficients().get(0));
        assertEquals(Double.valueOf(13), doubleRing.product(x1, y1).getCoefficients().get(1));
        assertEquals(Double.valueOf(28), doubleRing.product(x1, y1).getCoefficients().get(2));
        assertEquals(Double.valueOf(27), doubleRing.product(x1, y1).getCoefficients().get(3));
        assertEquals(Double.valueOf(18), doubleRing.product(x1, y1).getCoefficients().get(4));
        assertEquals(5, doubleRing.product(x1, y1).getCoefficients().size());
        //test first larger
        x1 = Polynomial.from(Arrays.asList(Double.valueOf(1), Double.valueOf(2), Double.valueOf(3), Double.valueOf(4)));
        assertEquals(Double.valueOf(4), doubleRing.product(x1, y1).getCoefficients().get(0));
        assertEquals(Double.valueOf(13), doubleRing.product(x1, y1).getCoefficients().get(1));
        assertEquals(Double.valueOf(28), doubleRing.product(x1, y1).getCoefficients().get(2));
        assertEquals(Double.valueOf(43), doubleRing.product(x1, y1).getCoefficients().get(3));
        assertEquals(Double.valueOf(38), doubleRing.product(x1, y1).getCoefficients().get(4));
        assertEquals(Double.valueOf(24), doubleRing.product(x1, y1).getCoefficients().get(5));
        assertEquals(6, doubleRing.product(x1, y1).getCoefficients().size());
        //test second larger
        assertEquals(Double.valueOf(4), doubleRing.product(y1, x1).getCoefficients().get(0));
        assertEquals(Double.valueOf(13), doubleRing.product(y1, x1).getCoefficients().get(1));
        assertEquals(Double.valueOf(28), doubleRing.product(y1, x1).getCoefficients().get(2));
        assertEquals(Double.valueOf(43), doubleRing.product(y1, x1).getCoefficients().get(3));
        assertEquals(Double.valueOf(38), doubleRing.product(y1, x1).getCoefficients().get(4));
        assertEquals(Double.valueOf(24), doubleRing.product(y1, x1).getCoefficients().get(5));
        assertEquals(6, doubleRing.product(y1, x1).getCoefficients().size());

        //test Polynomial<Integer>
        PolynomialRing<Polynomial<Integer>> polyIntRing = PolynomialRing.instance(PolynomialRing.instance(new IntegerRing()));
        //test 0 long
        Polynomial<Polynomial<Integer>> a1 = Polynomial.from(Arrays.asList());
        Polynomial<Polynomial<Integer>> b1 = Polynomial.from(Arrays.asList());
        assertEquals("[]", polyIntRing.sum(a1, b1).toString());
        //test 1 long
        a1 = Polynomial.from(Arrays.asList(Polynomial.from(Arrays.asList(Integer.valueOf(1)))));
        b1 = Polynomial.from(Arrays.asList(Polynomial.from(Arrays.asList(Integer.valueOf(4)))));
        assertEquals(Integer.valueOf(4), polyIntRing.product(a1, b1).getCoefficients().get(0).getCoefficients().get(0));
        assertEquals(1, polyIntRing.product(a1, b1).getCoefficients().size());
        // test many long same size
        a1 = Polynomial.from(Arrays.asList(Polynomial.from(Arrays.asList(Integer.valueOf(1))), Polynomial.from(Arrays.asList(Integer.valueOf(2))), Polynomial.from(Arrays.asList(Integer.valueOf(3)))));
        b1 = Polynomial.from(Arrays.asList(Polynomial.from(Arrays.asList(Integer.valueOf(4))), Polynomial.from(Arrays.asList(Integer.valueOf(5))), Polynomial.from(Arrays.asList(Integer.valueOf(6)))));
        assertEquals(Integer.valueOf(4), polyIntRing.product(a1, b1).getCoefficients().get(0).getCoefficients().get(0));
        assertEquals(Integer.valueOf(13), polyIntRing.product(a1, b1).getCoefficients().get(1).getCoefficients().get(0));
        assertEquals(Integer.valueOf(28), polyIntRing.product(a1, b1).getCoefficients().get(2).getCoefficients().get(0));
        assertEquals(Integer.valueOf(27), polyIntRing.product(a1, b1).getCoefficients().get(3).getCoefficients().get(0));
        assertEquals(Integer.valueOf(18), polyIntRing.product(a1, b1).getCoefficients().get(4).getCoefficients().get(0));
        assertEquals(5, polyIntRing.product(a1, b1).getCoefficients().size());
        //test first larger
        a1 = Polynomial.from(Arrays.asList(Polynomial.from(Arrays.asList(Integer.valueOf(1))), Polynomial.from(Arrays.asList(Integer.valueOf(2))), Polynomial.from(Arrays.asList(Integer.valueOf(3))), Polynomial.from(Arrays.asList(Integer.valueOf(4)))));
        assertEquals(Integer.valueOf(4), polyIntRing.product(a1, b1).getCoefficients().get(0).getCoefficients().get(0));
        assertEquals(Integer.valueOf(13), polyIntRing.product(a1, b1).getCoefficients().get(1).getCoefficients().get(0));
        assertEquals(Integer.valueOf(28), polyIntRing.product(a1, b1).getCoefficients().get(2).getCoefficients().get(0));
        assertEquals(Integer.valueOf(43), polyIntRing.product(a1, b1).getCoefficients().get(3).getCoefficients().get(0));
        assertEquals(Integer.valueOf(38), polyIntRing.product(a1, b1).getCoefficients().get(4).getCoefficients().get(0));
        assertEquals(Integer.valueOf(24), polyIntRing.product(a1, b1).getCoefficients().get(5).getCoefficients().get(0));
        assertEquals(6, polyIntRing.product(a1, b1).getCoefficients().size());
        //test second larger
        assertEquals(Integer.valueOf(4), polyIntRing.product(b1, a1).getCoefficients().get(0).getCoefficients().get(0));
        assertEquals(Integer.valueOf(13), polyIntRing.product(b1, a1).getCoefficients().get(1).getCoefficients().get(0));
        assertEquals(Integer.valueOf(28), polyIntRing.product(b1, a1).getCoefficients().get(2).getCoefficients().get(0));
        assertEquals(Integer.valueOf(43), polyIntRing.product(b1, a1).getCoefficients().get(3).getCoefficients().get(0));
        assertEquals(Integer.valueOf(38), polyIntRing.product(b1, a1).getCoefficients().get(4).getCoefficients().get(0));
        assertEquals(Integer.valueOf(24), polyIntRing.product(b1, a1).getCoefficients().get(5).getCoefficients().get(0));
        assertEquals(6, polyIntRing.product(b1, a1).getCoefficients().size());
    }
}
