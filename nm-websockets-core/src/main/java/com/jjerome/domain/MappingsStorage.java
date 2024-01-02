package com.jjerome.domain;

import com.jjerome.core.Mapping;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

public class MappingsStorage {

    private final Map<String, Mapping> mappings;
    private final List<Mapping> connectMappings;
    private final List<Mapping> disconnectMappings;

    public MappingsStorage(Map<String, Mapping> mappings, List<Mapping> connectMappings, List<Mapping> disconnectMappings) {
        this.mappings = mappings;
        this.connectMappings = connectMappings;
        this.disconnectMappings = disconnectMappings;
    }

    public String containsMapping(String requestPath, boolean shit) {
        if (mappings.containsKey(requestPath)) {
            return requestPath;
        }

        // /test/foo/{id}/smth
        // /test/foo/(.+)/smth
        // /test/foo/1/smth
        // 1, 434
        for (Map.Entry<String, Mapping> entry : mappings.entrySet()) {
            String fullPath = entry.getKey();
            Matcher matcher = entry.getValue().getRegexPathPattern().matcher(requestPath);
            if (matcher.matches()) {
                return fullPath;
            }
        }
        throw new RuntimeException("Not found the suitable pattern!");
    }

    public String containsMapping(String path) {
        if (mappings.containsKey(path)) {
            return path;
        }

        String[] pathArgs = path.split("\\/");
        /* 'test', '1111', 'good' */
        Set<String> allPaths = new HashSet<>(mappings.keySet());
        Set<String> allPathsCopy = new HashSet<>(mappings.keySet());
        Iterator<String> pathsIterator = allPaths.iterator();
        while (pathsIterator.hasNext()) {
            String value = pathsIterator.next();
            String[] valueArgs = value.split("\\/");
            if (valueArgs.length != pathArgs.length) {
                allPathsCopy.remove(value);
                continue;
            }

            boolean ok = true;
            for (int i = 0; i < valueArgs.length; ++i) {
                String val1 = pathArgs[i];
                String val2 = valueArgs[i];
                if (val2.contains("{") && val2.contains("}")) {
                    continue;
                }
                if (!val1.contains(val2)) {
                    ok = false;
                    break;
                }
            }
            if (!ok) {
                allPathsCopy.remove(value);
            }
        }
        if (allPathsCopy.size() > 1) {
            throw new RuntimeException("NO");
        }

        return !allPathsCopy.isEmpty() ? allPathsCopy.iterator().next() : null;
    }

    public Mapping getMappingByPath(String path){
        return mappings.get(path);
    }

    public List<Mapping> getConnectMappings() {
        return connectMappings;
    }

    public List<Mapping> getDisconnectMappings() {
        return disconnectMappings;
    }
}
