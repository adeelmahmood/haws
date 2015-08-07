package com.haws.projects.haws.communicator.model;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import kafka.serializer.Decoder;

public class MessageDecoder implements Decoder<Object> {

	@Override
	public Object fromBytes(byte[] bs) {
		try {
			ObjectInputStream bais = new ObjectInputStream(new ByteArrayInputStream(bs));
			AbstractMessage<?> message = (AbstractMessage<?>) bais.readObject();
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
