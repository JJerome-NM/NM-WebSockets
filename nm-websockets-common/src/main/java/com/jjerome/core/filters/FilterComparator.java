package com.jjerome.core.filters;

import java.util.Comparator;

public class FilterComparator<F extends Filter> implements Comparator<F> {

    /**
     * @param filter1 the first {@link Filter} to be compared.
     * @param filter2 the second {@link Filter} to be compared.
     * @return Returns the difference between the order of the first {@link Filter} and
     * the second {@link Filter}
     */
    @Override
    public int compare(F filter1, F filter2) {
        if (filter1.equals(filter2)){
            return 0;
        }
        int result = filter1.getOrder() - filter2.getOrder();
        return result != 0 ? result : -1;
    }

    public static FilterComparator<Filter> buildComparator(){
        return new FilterComparator<>();
    }
}
