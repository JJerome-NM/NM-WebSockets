package com.jjerome.domain;

import com.jjerome.core.Controller;
import com.jjerome.core.Mapping;
import com.jjerome.core.Request;
import com.jjerome.core.UndefinedBody;
import com.jjerome.exception.MappingException;
import com.jjerome.exception.MappingNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;

import static java.util.stream.Collectors.toList;


public class MappingsStorage {
    private final Map<String, UUID> mappingsUUIDByPath;
    private final Map<UUID, Mapping> mappings;
    private final List<Mapping> connectMappings;
    private final List<Mapping> disconnectMappings;
    private final List<Controller> controllers;

    public MappingsStorage(List<Mapping> mappings) {
        this.mappings = new HashMap<>();
        this.connectMappings = new ArrayList<>();
        this.disconnectMappings = new ArrayList<>();
        this.mappingsUUIDByPath = new HashMap<>();
        this.controllers = mappings.stream().map(Mapping::getController).distinct().collect(toList());

        initMappings(mappings);
    }

    private void initMappings(List<Mapping> mappings) {
        mappings.forEach(mapping -> {
            switch (mapping.getType()) {
                case CONNECT -> addConnectMapping(mapping);
                case DISCONNECT -> addDisconnectMapping(mapping);
                case METHOD -> addMapping(mapping);
                default -> throw new MappingException("Mapping type '%s' not supported".formatted(mapping.getType()));
            }
        });
    }

    private void addMapping(Mapping mapping) {
        UUID uuid = UUID.randomUUID();

        mappingsUUIDByPath.put(mapping.buildFullPath(), uuid);
        mappings.put(uuid, mapping);
    }

    private void addConnectMapping(Mapping mapping) {
        connectMappings.add(mapping);
    }

    private void addDisconnectMapping(Mapping mapping) {
        disconnectMappings.add(mapping);
    }

    public UUID containsMapping(String requestPath) {
        if (mappingsUUIDByPath.containsKey(requestPath)) {
            return mappingsUUIDByPath.get(requestPath);
        }

        // /test/foo/{id}/smth
        // /test/foo/(.+)/smth
        // /test/foo/1/smth
        // 1, 434
        for (Map.Entry<UUID, Mapping> entry : mappings.entrySet()) {
            Matcher matcher = entry.getValue().getRegexPathPattern().matcher(requestPath);
            if (matcher.matches()) {
                return mappingsUUIDByPath.get(entry.getValue().buildFullPath());
            }
        }
        throw new MappingNotFoundException(requestPath + " - mapping not found");
    }

    public Mapping getMappingByRequest(Request<UndefinedBody> request) {
        if (Objects.nonNull(request.getMappingID())) {
            Mapping mapping = mappings.get(request.getMappingIDAsUUID());

            System.out.println(mapping);
        }

        UUID mappingId = containsMapping(request.getPath());
        return mappings.get(mappingId);
    }

    public Map<String, UUID> getMappingsPathToUUIDMap() {
        return mappingsUUIDByPath;
    }

    public List<Controller> getControllers() {
        return controllers;
    }

    public List<Mapping> getConnectMappings() {
        return connectMappings;
    }

    public List<Mapping> getDisconnectMappings() {
        return disconnectMappings;
    }
}
