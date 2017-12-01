package com.skrf.backend.odatamodel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlPropertyRef;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriParameter;

import com.skrf.backend.model.Eventtype;

public class EventtypeEntitySet extends EntityReader {
	
	
	private String keyName = "eventtype";
	

	public EventtypeEntitySet(EntityManagerFactory emf, EdmEntitySet es) {
		super(emf, es);
	}

	@Override
	public EntityCollection readEntitySetData() throws ODataApplicationException {
		// TODO Auto-generated method stub
		return getAllEventTypes();
	}

	@Override
	public Entity readEntityData(List<UriParameter> keyPredicates) throws ODataApplicationException {
		// TODO Auto-generated method stub
		String keyValue = DataManipulatorHelper.getURIKeyValueByName(this.keyName, keyPredicates, this.EntityType);

		return getEventtypeByKey(keyValue);
	}

	@SuppressWarnings("unchecked")
	private Entity getEventtypeByKey(String key) {
		Entity EventtypeEntity = null;
		Eventtype Eventtype_bd = null;
		EntityManager em = createEntityManager();

		try {
			Eventtype_bd = (Eventtype) em.createNamedQuery("Eventtype.findByKey").setParameter("key", key)
					.getSingleResult();
		} catch (Exception e) {
			logger.error("Error by selecting data: {}", e.getMessage());
		} finally {
			em.close();
		}
		if (Eventtype_bd != null) {
			EventtypeEntity = new Entity();
			EventtypeEntity
					.addProperty(new Property(null, "eventtype", ValueType.PRIMITIVE, Eventtype_bd.getEventtype()));
			EventtypeEntity.addProperty(new Property(null, "descr", ValueType.PRIMITIVE, Eventtype_bd.getDescr()));
		}
		return EventtypeEntity;
	}

	@SuppressWarnings("unchecked")
	private EntityCollection getAllEventTypes() {
		EntityCollection retEntitySet = new EntityCollection();
		List<Eventtype> Eventtypes_bd = null;
		EntityManager em = createEntityManager();

		try {
			Eventtypes_bd = em.createNamedQuery("Eventtype.findAll").getResultList();
		} catch (Exception e) {
			logger.error("Error by selecting data: {}", e.getMessage());
		} finally {
			em.close();
		}

		for (Eventtype et : Eventtypes_bd) {

			Entity EventtypeEntity = new Entity();
			EventtypeEntity.addProperty(new Property(null, "eventtype", ValueType.PRIMITIVE, et.getEventtype()));
			EventtypeEntity.addProperty(new Property(null, "descr", ValueType.PRIMITIVE, et.getDescr()));

			retEntitySet.getEntities().add(EventtypeEntity);
		}

		return retEntitySet;
	}

}
