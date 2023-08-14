package com.jjerome;

import com.jjerome.filter.FilterChain;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class FiltersStorage {

    private final Map<String, FilterChain> filters;

    public FilterChain getFilterByName(String name){
        return filters.get(name);
    }


    /**
     * @param name name of the filter you want to get
     * @param e exception that will be applied if the {@link FilterChain} is not found
     * @return {@link FilterChain} - method filter chain
     * @throws X Exception type
     */
    public <X extends Throwable> FilterChain getFilterByNameOrElseThrow(String name, Supplier<? extends X> e) throws X{
        FilterChain filterChain = getFilterByName(name);
        if (filterChain != null){
            return filterChain;
        } else {
            throw e.get();
        }
    }
}
