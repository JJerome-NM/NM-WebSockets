package com.jjerome;

import com.jjerome.core.Mapping;
import com.jjerome.exeption.FilterChainNotFound;
import com.jjerome.filter.ApplicationFilterChain;
import com.jjerome.filter.FilterChain;
import com.jjerome.core.Ordered;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultApplicationFilterChain implements ApplicationFilterChain {

    private final FilterChainComparator<FilterChain> filterChainComparator;

    private final Map<String, Set<FilterChain>> filters;

    private final Map<String, Set<FilterChain>> connectFilters;

    private final FiltersStorage filtersStorage;


    public DefaultApplicationFilterChain(
            FilterChainComparator<FilterChain> filterChainComparator,
            FiltersStorage filtersStorage) {
        this.filterChainComparator = filterChainComparator;
        this.filtersStorage = filtersStorage;

        this.filters = new HashMap<>();
        this.connectFilters = new HashMap<>();
    }

    @Override
    public void doConnectFilter() {

        System.out.println("ApplicationFilterChain.doConnectFilter()");

//        for (FilterChain filterChain : connectFilters){
//            Assert.notNull(filterChain, "FilterChain cant be null");
//
//            filterChain.doFilter();
//        }
    }

    @Override
    public void doFilter() {
        System.out.println("ApplicationFilterChain.doFilter()");
    }

    /**
     * @param mapping
     * @param filtersNames
     */
    @Override
    public Mapping addFilterForMapping(Mapping mapping, String... filtersNames) {
        if (filtersNames.length != 0){
             var filterChain = filtersStorage.getFilterByNameOrElseThrow(filtersNames[0],
                    () -> new FilterChainNotFound(String.format("Filter chain with name %s not found", filtersNames[0])));

             return new MappingFilterProxy(filterChain, mapping);

//            Set<FilterChain> mappingFilterChain = new TreeSet<>(filterChainComparator);

//            Stream.of(filtersNames)
//                    .map(name -> filtersStorage.getFilterByNameOrElseThrow(name,
//                            () -> new FilterChainNotFound(String.format("Filter chain with name %s not found", name))))
//                    .forEach(mappingFilterChain::add);
//
//            filters.put(mappingId, mappingFilterChain);
        }
        return mapping;
    }

    @Override
    public int getOrder() {
        return Ordered.APPLICATION_PRECEDENCE;
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }
}
