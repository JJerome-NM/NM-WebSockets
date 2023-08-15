package com.jjerome;

import com.jjerome.core.Mapping;
import com.jjerome.exeption.FilterChainNotFound;
import com.jjerome.filter.FilterChain;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class FiltersStorage {

    private final Map<String, FilterChain> filters;

    public FilterChain getFilterByName(String name){
        return filters.get(name);
    }


    public FilterChain buildMappingFilterChain(Mapping mapping){
        return new MappingFilterChain(collectAllFiltersByName(mapping.getMappingAnnotation().filters()));
    }

    public Set<FilterChain> collectAllFiltersByName(String... names){
        Set<FilterChain> filterChainSet = new TreeSet<>(FilterChainComparator.buildFilterChainComparator());

        Stream.of(names)
                .forEach(name -> filterChainSet.add(getFilterByNameOrThrow(name)));

        return filterChainSet;
    }

    public FilterChain getFilterByNameOrThrow(String name) throws FilterChainNotFound{
        return getFilterByNameOrElseThrow(name,
                () -> new FilterChainNotFound(String.format("Filter chain with name {%s} not found", name)));
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
