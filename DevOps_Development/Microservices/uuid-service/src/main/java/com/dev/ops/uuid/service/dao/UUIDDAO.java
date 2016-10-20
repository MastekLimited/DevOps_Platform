package com.dev.ops.uuid.service.dao;

import com.dev.ops.common.dao.generic.GenericDAO;

public interface UUIDDAO extends GenericDAO<Object> {
	String getUUIDSequenceValue(String sequenceName);
}
