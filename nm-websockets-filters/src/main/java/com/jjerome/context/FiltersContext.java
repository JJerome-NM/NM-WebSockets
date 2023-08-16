package com.jjerome.context;

import com.jjerome.Filter;
import com.jjerome.core.Request;
import com.jjerome.core.UndefinedBody;
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

        FilterChain chain = new Filter(null, null, null, null, null, null);
        filterChains.put("filter1", chain);
        filterChains.put("filter2", chain);

//        var beans = context.getBeansWithAnnotation(WSFiltersComponent.class);
//
//        System.out.println(beans.size());
//        System.out.println(beans);

        return filterChains;
    }
}
