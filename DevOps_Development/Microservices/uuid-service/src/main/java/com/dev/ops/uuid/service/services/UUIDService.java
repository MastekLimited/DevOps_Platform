package com.dev.ops.uuid.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.dev.ops.exceptions.impl.DefaultWrappedException;
import com.dev.ops.uuid.service.domain.EntityType;
import com.dev.ops.uuid.service.factory.UUIDGeneratorFactory;
import com.dev.ops.uuid.service.factory.generators.UUIDGenerator;

@Component
public class UUIDService {

	@Autowired
	@Qualifier("uuidGeneratorFactory")
	private UUIDGeneratorFactory uuidGeneratorFactory;

	public String generateUUID(final EntityType entityType) throws DefaultWrappedException {
		final UUIDGenerator uuidGenerator = this.uuidGeneratorFactory.getUUIDGenerator(entityType);
		return uuidGenerator.generateUUID();
	}
}