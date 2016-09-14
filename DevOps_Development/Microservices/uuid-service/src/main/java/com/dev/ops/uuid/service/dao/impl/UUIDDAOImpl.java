package com.dev.ops.uuid.service.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.dev.ops.common.dao.generic.GenericDAOImpl;
import com.dev.ops.uuid.service.dao.UUIDDAO;

@Component
public class UUIDDAOImpl extends GenericDAOImpl<Object> implements UUIDDAO {

	@Override
	public String getUUIDSequenceValue(final String sequenceName) {
		final EntityManager entityManager = this.getEntityManager();
		final Query query = entityManager.createNativeQuery("SELECT nextval('" + sequenceName + "') as num");
		return query.getSingleResult().toString();
	}
}