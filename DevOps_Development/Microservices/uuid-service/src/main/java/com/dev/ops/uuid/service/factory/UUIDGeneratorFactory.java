package com.dev.ops.uuid.service.factory;

import com.dev.ops.exceptions.impl.DefaultWrappedException;
import com.dev.ops.uuid.service.domain.EntityType;
import com.dev.ops.uuid.service.factory.generators.UUIDGenerator;

public interface UUIDGeneratorFactory {

	UUIDGenerator getUUIDGenerator(final EntityType entityType) throws DefaultWrappedException;
}
