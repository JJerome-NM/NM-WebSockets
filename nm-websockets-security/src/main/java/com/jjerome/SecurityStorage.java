package com.jjerome;

import com.jjerome.core.filters.Filter;
import com.jjerome.core.filters.FilterComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class SecurityStorage {

    private final Map<String, Set<Filter>> filters;


    public SecurityStorage(Map<String, Set<Filter>> filters) {
        this.filters = filters;
    }

    public SecurityStorage() {
        this.filters = new HashMap<>();
    }

    public void addFilterForMapping(String mappingUrl, Filter filter){
        filters.computeIfAbsent(mappingUrl, key -> new TreeSet<>(FilterComparator.buildComparator())).add(filter);
    }

    public Set<Filter> getFiltersForMapping(String mappingUrl){
        return filters.get(mappingUrl);
    }

    public List<Filter> getFiltersListForMapping(String mappingUrl){
        return new ArrayList<>(filters.get(mappingUrl));
    }
}
