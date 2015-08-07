package com.haws.projects.haws.communicator.model;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import kafka.serializer.Encoder;

public class MessageEncoder implements Encoder<Object> {

	@Override
	public byte[] toBytes(Object object) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.flush();
			oos.close();
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "".getBytes();
	}

}
