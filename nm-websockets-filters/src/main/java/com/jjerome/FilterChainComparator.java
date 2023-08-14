package com.jjerome;

import com.jjerome.filter.FilterChain;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class FilterChainComparator<F extends FilterChain> implements Comparator<F> {

    /**
     * @param filter1 the first {@link FilterChain} to be compared.
     * @param filter2 the second {@link FilterChain} to be compared.
     * @return Returns the difference between the order of the first {@link FilterChain} and
     * the second {@link FilterChain}
     */
    @Override
    public int compare(F filter1, F filter2) {
        if (filter1.equals(filter2)){
            return 0;
        }
        int result = filter1.getOrder() - filter2.getOrder();
        return result != 0 ? result : -1;
    }
}
