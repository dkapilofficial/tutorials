package com.baeldung.couchbase.service;

import com.couchbase.client.java.document.JsonDocument;

public interface JsonDocumentConverter<T> {

    JsonDocument toDocument(T t);
    
    T fromDocument(JsonDocument doc);
}
