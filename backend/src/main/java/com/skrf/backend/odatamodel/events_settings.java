package com.skrf.backend.odatamodel;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skrf.backend.model.Eventtype;
import com.skrf.backend.service.SKRFEdmProvider;

public class events_settings extends entity_manipulator {

	private static Logger logger = LoggerFactory.getLogger(events_settings.class);
	
	public events_settings() {
		super();
	}
	
	@Override
	public EntityCollection readEntitySetData(EdmEntitySet edmEntitySet) throws ODataApplicationException {

		// actually, this is only required if we have more than one Entity Sets
		//if (edmEntitySet.getName().equals(SKRFEdmProvider.ES_EVENTSETTINGS_NAME)) {
			return getAllEventTypes();
		//}

		//return super.readEntitySetData(edmEntitySet);
	}

	@Override
	public Entity readEntityData(EdmEntitySet edmEntitySet, List<UriParameter> keyParams)
			throws ODataApplicationException {

		return super.readEntityData(edmEntitySet, keyParams);
	}

	// Intern

	@SuppressWarnings("unchecked")
	private EntityCollection getAllEventTypes() {
		EntityCollection retEntitySet = new EntityCollection();
		List<Eventtype> Eventtypes_bd = null;
		EntityManager em = createEntityManager();
		
		try {
			Eventtypes_bd = em.createNamedQuery("Eventtype.findAll").getResultList();
		}
		catch (Exception e) { 
			e.printStackTrace();
		}
		 finally {
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
