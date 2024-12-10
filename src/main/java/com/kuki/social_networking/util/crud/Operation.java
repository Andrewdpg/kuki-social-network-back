package com.kuki.social_networking.util.crud;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public enum  Operation {
    CREATE,
    READ,
    UPDATE,
    DELETE,
    ADD;

    public static List<UUID> filterByOperation(Map<UUID, Operation> map, Operation operation) {
        return map.entrySet().stream()
            .filter(entry -> entry.getValue() == operation)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
}
