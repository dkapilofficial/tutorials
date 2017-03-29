package com.baeldung.jackson.serialization;

import java.io.IOException;

import com.baeldung.jackson.entities.MyPair;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

public class MyPairDeserializer extends KeyDeserializer {

	@Override
	public MyPair deserializeKey(String key, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		return new MyPair(key);
	}
}