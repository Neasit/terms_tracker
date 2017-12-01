package com.skrf.backend.odatamodel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class EntityReader implements DataManipulator{
	
	protected EntityManagerFactory emf;
	protected EdmEntitySet EntitySet;
	protected EdmEntityType EntityType;
	
	protected static Logger logger = LoggerFactory.getLogger(events_settings.class);
	
	public EntityReader(EntityManagerFactory emf, EdmEntitySet es) {
		this.EntitySet = es;
		this.EntityType = es.getEntityType();
		this.emf = emf;
	}
	
	protected EntityManager createEntityManager() {
		return emf.createEntityManager();
	}
	
}
