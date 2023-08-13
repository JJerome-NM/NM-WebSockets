package com.jjerome;

import com.jjerome.domain.FilterChain;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class FilterChainComparator<F extends FilterChain> implements Comparator<F> {

    /**
     * @param filter1 the first filter to be compared.
     * @param filter2 the second filter to be compared.
     * @return Returns the difference between the order of the first filter and the second filter
     */
    @Override
    public int compare(F filter1, F filter2) {
        if (filter1 == filter2){
            return 0;
        }
        int result = filter1.getOrder() - filter2.getOrder();
        return result != 0 ? result : -1;
    }
}
