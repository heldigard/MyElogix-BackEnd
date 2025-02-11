package com.tarapaca.api.shared.infraestructure.helpers.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class SortOrderMapper {
    private static final String DEFAULT_SORT_PROPERTY = "id";
    private static final Direction DEFAULT_DIRECTION = Direction.DESC;

    private SortOrderMapper() {
        // Private constructor to prevent instantiation
    }

    public static List<Sort.Order> createSortOrders(List<String> properties, List<String> directions) {
        if (properties == null || properties.isEmpty()) {
            return List.of(new Sort.Order(DEFAULT_DIRECTION, DEFAULT_SORT_PROPERTY));
        }

        List<Sort.Order> sortOrders = IntStream.range(0, properties.size())
            .filter(i -> properties.get(i) != null && !properties.get(i).trim().isEmpty())
            .mapToObj(i -> new Sort.Order(
                getDirection(directions, i),
                properties.get(i).trim()
            ))
            .toList();

        return sortOrders.isEmpty()
            ? List.of(new Sort.Order(DEFAULT_DIRECTION, DEFAULT_SORT_PROPERTY))
            : sortOrders;
    }

    private static Direction getDirection(List<String> directions, int index) {
        if (directions == null || index >= directions.size()) {
            return DEFAULT_DIRECTION;
        }

        String directionStr = directions.get(index);
        if (directionStr == null || directionStr.trim().isEmpty()) {
            return DEFAULT_DIRECTION;
        }

        try {
            return Direction.fromString(directionStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return DEFAULT_DIRECTION;
        }
    }
}
