package com.jjerome.domain;

import com.jjerome.core.Mapping;

import java.util.List;
import java.util.Map;

public class MappingsStorage {

    private final Map<String, Mapping> mappings;

    private final List<Mapping> connectMappings;

    private final List<Mapping> disconnectMappings;

    public MappingsStorage(Map<String, Mapping> mappings, List<Mapping> connectMappings, List<Mapping> disconnectMappings) {
        this.mappings = mappings;
        this.connectMappings = connectMappings;
        this.disconnectMappings = disconnectMappings;
    }

    public boolean containsMapping(String path){
        return mappings.containsKey(path);
    }

    public Mapping getMappingByPath(String path){
        return mappings.get(path);
    }
}
