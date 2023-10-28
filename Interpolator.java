package Interpolation;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import RingsPackage.Polynomial;
import RingsPackage.PolynomialRing;
import RingsPackage.Ring;

public class Interpolator {
    
    //method to return polynomial that interpolates input data list
    public <T> Polynomial<T> interpolatingPolynomial(List<T> dataList, Ring<T> ring) {
        //check that input list not null
        Objects.requireNonNull(dataList, "dataList must not be null");
        Objects.requireNonNull(ring, "ring must not be null");
        //initialize new polynomial ring
        PolynomialRing<T> polyRing = PolynomialRing.instance(ring);
        
        //handle case where input list is empty
        if (dataList.isEmpty())
            return polyRing.zero();
        
        //initialize polynomial to store result
        Polynomial<T> result = polyRing.identity();

        /**
         * For each xi in input list
         * check that xi not null
         * initialze new Polynomial to store x - xi
         * assign result * binomial to result field
         */
        for (T element : dataList) {
            Objects.requireNonNull(element, "Input data values must not be null");
            //binomial <-- x - element   ; x represented by coefficient of 1 (identity), -element represented as product of -1*elmement
            Polynomial<T> binomial = Polynomial.from(Arrays.asList(ring.identity(), ring.product(ring.negative(), element)));
            result = polyRing.product(binomial, result);
        }
        return result;
    }
}
