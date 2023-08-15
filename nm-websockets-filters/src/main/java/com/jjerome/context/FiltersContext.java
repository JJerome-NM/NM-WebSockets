package com.jjerome.context;

import com.jjerome.filter.FilterChain;
import com.jjerome.util.MergedAnnotationUtil;
import com.jjerome.util.MethodUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FiltersContext {

    private final ApplicationContext context;

    private final MethodUtil methodUtil;

    private final MergedAnnotationUtil mergedAnnotationUtil;

    public Map<String, FilterChain> findAllFilters(){
        Map<String, FilterChain> filterChains = new HashMap<>();

        filterChains.put("filter1", new WTFFilter());
        filterChains.put("filter2", new WTFFilter());
        filterChains.put("WTFFilter3", new WTFFilter());

//        var beans = context.getBeansWithAnnotation(WSFiltersComponent.class);
//
//        System.out.println(beans.size());
//        System.out.println(beans);

        return filterChains;
    }

    private class WTFFilter implements FilterChain{

        @Override
        public String getName() {
            return this.getClass().getName();
        }

        @Override
        public int getOrder() {
            return 0;
        }

        @Override
        public void doFilter() {
            System.out.println("WTF");
        }
    }
}
