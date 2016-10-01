package com.baeldung.patterns.data;

import java.util.Map;

public interface Order {
    String getUsername();

    Map<Book, Integer> getItems();

    void add(Book item, Integer quantity);
}
