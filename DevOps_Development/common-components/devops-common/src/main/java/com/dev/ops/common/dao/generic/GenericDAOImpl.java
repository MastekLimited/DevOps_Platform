package com.dev.ops.common.dao.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.dev.ops.common.domain.ContextInfo;
import com.dev.ops.logger.service.DiagnosticLogger;
import com.dev.ops.logger.service.LoggerFactory;
import com.dev.ops.logger.types.LoggingPoint;

@Service
public abstract class GenericDAOImpl<T> implements GenericDAO<T> {

	private static final DiagnosticLogger DIAGNOSTIC_LOGGER = LoggerFactory.getDiagnosticLogger(GenericDAOImpl.class);

	private EntityManager entityManager;

	private final Class<T> type;

	@SuppressWarnings({"rawtypes", "unchecked"})
	public GenericDAOImpl() {
		final Type t = this.getClass().getGenericSuperclass();
		final ParameterizedType pt = (ParameterizedType) t;
		this.type = (Class) pt.getActualTypeArguments()[0];
	}

	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	@Override
	public T save(final T object, final ContextInfo contextInfo) {
		DIAGNOSTIC_LOGGER.info(LoggingPoint.START.toString(), contextInfo, new Object[] {"[save]"});
		this.getEntityManager().persist(object);
		DIAGNOSTIC_LOGGER.debug("Object persisted:" + object + " of type:" + object.getClass(), contextInfo);
		DIAGNOSTIC_LOGGER.info(LoggingPoint.END.toString(), contextInfo, new Object[] {"[save]"});
		return object;
	}

	@Override
	public T save(final T object) {
		return this.save(object, null);
	}

	@Override
	public T update(final T object, final ContextInfo contextInfo) {
		DIAGNOSTIC_LOGGER.info(LoggingPoint.START.toString(), contextInfo, new Object[] {"[update]"});
		final T updatedObject = this.getEntityManager().merge(object);
		DIAGNOSTIC_LOGGER.debug("Object updated:" + object + " of type:" + object.getClass(), contextInfo);
		DIAGNOSTIC_LOGGER.info(LoggingPoint.END.toString(), contextInfo, new Object[] {"[update]"});
		return updatedObject;
	}

	@Override
	public T update(final T object) {
		return this.update(object, null);
	}

	@Override
	public Collection<T> update(final Collection<T> objects, final ContextInfo contextInfo) {
		DIAGNOSTIC_LOGGER.info(LoggingPoint.START.toString(), contextInfo, new Object[] {"[bulk update]"});
		Collection<T> updatedObjects = null;

		if(objects instanceof List) {
			updatedObjects = new ArrayList<T>();
		} else {
			updatedObjects = new HashSet<T>();
		}

		for(final T object : objects) {
			updatedObjects.add(this.getEntityManager().merge(object));
		}
		DIAGNOSTIC_LOGGER.debug("Objects bulk updated:" + updatedObjects + " of type:", contextInfo);
		DIAGNOSTIC_LOGGER.info(LoggingPoint.END.toString(), contextInfo, new Object[] {"[bulk update]"});
		return updatedObjects;
	}

	@Override
	public Collection<T> update(final Collection<T> objects) {
		return this.update(objects, null);
	}

	@Override
	public void delete(final Object id, final ContextInfo contextInfo) {
		DIAGNOSTIC_LOGGER.info(LoggingPoint.START.toString(), contextInfo, new Object[] {"[delete]"});
		this.getEntityManager().remove(this.getEntityManager().getReference(this.type, id));
		DIAGNOSTIC_LOGGER.debug("Object Deleted:<" + id + ">", contextInfo);
		DIAGNOSTIC_LOGGER.info(LoggingPoint.END.toString(), contextInfo, new Object[] {"[delete]"});
	}

	@Override
	public void delete(final Object id) {
		this.delete(id, null);
	}

	@Override
	public T findByPrimaryKey(final Object id, final ContextInfo contextInfo) {
		DIAGNOSTIC_LOGGER.info(LoggingPoint.START.toString(), contextInfo, new Object[] {"[findByPrimaryKey]"});
		final T object = this.getEntityManager().find(this.type, id);
		DIAGNOSTIC_LOGGER.debug("Find entity by primary key:<" + id + ">", contextInfo);
		DIAGNOSTIC_LOGGER.info(LoggingPoint.END.toString(), contextInfo, new Object[] {"[findByPrimaryKey]"});
		return object;
	}

	@Override
	public T findByPrimaryKey(final Object id) {
		return this.findByPrimaryKey(id, null);
	}
}