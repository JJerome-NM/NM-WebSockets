package com.jjerome.domain;

import com.jjerome.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class MappingsStorage {

    private final Map<String, Mapping> mappings;

    private final List<Mapping> connectMappings;

    private final List<Mapping> disconnectMappings;

    public boolean containsMapping(String path){
        return mappings.containsKey(path);
    }

    public Mapping getMappingByPath(String path){
        return mappings.get(path);
    }
}
