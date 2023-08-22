package com.jjerome;

import com.jjerome.core.Mapping;
import com.jjerome.exeption.FilterNotFound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FiltersStorage {

    private final Map<String, Filter> filters;

    private final Map<String, FilterComponent> filterComponents;


    public FiltersStorage() {
        this.filterComponents = new HashMap<>();
        this.filters = new HashMap<>();
    }

    public FiltersStorage(Map<String, Filter> filters, List<FilterComponent> filterComponents) {
        this.filters = filters;
        this.filterComponents = buildFilterComponentsMap(filterComponents);
    }

    private Map<String, FilterComponent> buildFilterComponentsMap(List<FilterComponent> filterComponents){
        return filterComponents.stream()
                .collect(Collectors.toMap(c -> c.getClass().getSimpleName(), Function.identity()));
    }

    public FilterChain buildMappingFilterChain(Mapping mapping){
        return new MappingFilterChain(collectAllFiltersByLabel(mapping.getComponentAnnotation().filters()));
    }

    public <T extends FilterChain> Set<T> collectAllFiltersByLabel(String... labels){
        return Stream.of(labels)
                .map(label -> (T) getFilterByLabelOrThrow(label))
                .collect(Collectors.toCollection(() -> new TreeSet<>(FilterChainComparator.buildComparator())));
    }

    public <T extends FilterChain> T getFilterByLabelOrThrow(String label) throws FilterNotFound {
        return getFilterByNameOrElseThrow(label,
                () -> new FilterNotFound(String.format("Filter with name {%s} not found", label)));
    }

    /**
     * @param label name of the filter you want to get
     * @param e exception that will be applied if the {@link Filter} is not found
     * @return {@link Filter} - method filter chain
     * @throws X Exception type
     */
    public <X extends Throwable, T extends FilterChain> T getFilterByNameOrElseThrow(String label, Supplier<? extends X> e) throws X{
        T filterChain = getFilterByLabel(label);
        if (filterChain != null){
            return filterChain;
        } else {
            throw e.get();
        }
    }

    public <T extends FilterChain> T getFilterByLabel(String label){
        return (T) filters.get(label);
    }
}
