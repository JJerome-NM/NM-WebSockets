package com.jjerome.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UnifiedStorage {

    ControllersStorage controllersStorage;

    MappingsStorage mappingStorage;
}
