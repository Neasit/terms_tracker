package com.skrf.backend.odatamodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriParameter;

import com.skrf.backend.model.Eventrule;
import com.skrf.backend.model.User;
import com.skrf.backend.service.SKRFEdmProvider;

public class EventruleEntitySet extends EntityReader {
	private String key1Name = "eventtype";
	private String key2Name = "rulesnmb";
	
	public EventruleEntitySet(EntityManagerFactory emf, EdmEntitySet es) {
		super(emf, es);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EntityCollection readEntitySetData() throws ODataApplicationException {
		// TODO Auto-generated method stub
		return getAllEventRules();
	}

	@Override
	public Entity readEntityData(List<UriParameter> keyPredicates) throws ODataApplicationException {
		// TODO Auto-generated method stub
		String strEventtype = DataManipulatorHelper.getURIKeyValueByName(this.key1Name, keyPredicates, this.EntityType);
		String strRulenmb = DataManipulatorHelper.getURIKeyValueByName(this.key2Name, keyPredicates, this.EntityType);
		Integer rulenmb = 0;
		try {
			rulenmb = Integer.parseInt(strRulenmb);
		} catch (NumberFormatException e) {
			rulenmb = 1;
		}
		return getEventruleByKey(strEventtype, rulenmb);
	}
	
	@SuppressWarnings("unchecked")
	private Entity getEventruleByKey(String eventtype, Integer rulenmb) {
		Entity EventruleEntity = null;
		Eventrule Eventrule_bd = null;
		EntityManager em = createEntityManager();
		
		try {
			Query mq = em.createNamedQuery("Eventrule.findByKey");
			mq.setParameter("eventtype", eventtype);
			mq.setParameter("rulenmb", rulenmb);
			Eventrule_bd = (Eventrule) mq.getSingleResult();
		} catch (Exception e) {
			logger.error("Error by selecting data: {}", e.getMessage());
		} finally {
			em.close();
		}
		if (Eventrule_bd != null) {
			EventruleEntity = this.convertToEntity(Eventrule_bd);
		}
		return EventruleEntity;
	}

	@SuppressWarnings("unchecked")
	private EntityCollection getAllEventRules() {
		EntityCollection retEntitySet = new EntityCollection();
		List<Eventrule> Eventrules_bd = new ArrayList<Eventrule>();
		EntityManager em = createEntityManager();
		
		try {
			Eventrules_bd = em.createNamedQuery("Eventrule.findAll").getResultList();
		} catch (Exception e) {
			logger.error("Error by selecting data: {}", e.getMessage());
		} finally {
			em.close();
		}

		for (Eventrule er : Eventrules_bd) {
			retEntitySet.getEntities().add(this.convertToEntity(er));
		}

		return retEntitySet;
	}
	
	@SuppressWarnings("unchecked")
	private EntityCollection getByType(String keyType) {
		EntityCollection retEntitySet = new EntityCollection();
		List<Eventrule> Eventrules_bd = null;
		EntityManager em = createEntityManager();
		
		try {
			Query mq = em.createNamedQuery("Eventrule.findByType");
			mq.setParameter("eventtype", keyType);
			Eventrules_bd = mq.getResultList();
		} catch (Exception e) {
			logger.error("Error by selecting data: {}", e.getMessage());
		} finally {
			em.close();
		}

		for (Eventrule er : Eventrules_bd) {
			retEntitySet.getEntities().add(this.convertToEntity(er));
		}

		return retEntitySet;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public EntityCollection readRealatedFor(Entity filterEntity, List<UriParameter> keyPredicates)
			throws ODataApplicationException {
		// TODO Auto-generated method stub
		EntityCollection retEC = new EntityCollection();
		if(SKRFEdmProvider.ET_EVENTTYPE_FQN.getFullQualifiedNameAsString().equals(filterEntity.getType())) {
			if(!keyPredicates.isEmpty()) {
				retEC.getEntities().add(this.readEntityData(keyPredicates));
			} else {
				retEC = this.getByType((String)filterEntity.getProperty("eventtype").getValue());
			}
			return retEC;
		} else {
			logger.error("Not implemented link from: {}", filterEntity.getType());
			throw new ODataApplicationException("Not implemented.", HttpStatusCode.BAD_REQUEST.getStatusCode(), Locale.ROOT);
		}
	}
	
	@Override
	protected Entity convertToEntity(Object dataBD) {
		User usr = null;
		Integer userId = null;
		
		Eventrule erBD = (Eventrule) dataBD;
		Entity EventruleEntity = new Entity();
		EventruleEntity.addProperty(new Property(null, "eventtype", ValueType.PRIMITIVE, erBD.getId().getEventtype()));
		EventruleEntity.addProperty(new Property(null, "rulesnmb", ValueType.PRIMITIVE, erBD.getId().getRulesnmb()));
		EventruleEntity.addProperty(new Property(null, "action", ValueType.PRIMITIVE, erBD.getAction()));
		EventruleEntity.addProperty(new Property(null, "intvalue", ValueType.PRIMITIVE, erBD.getIntvalue()));
		usr = erBD.getRefUser();
		if (usr != null) {
			userId = usr.getId();
		}
		EventruleEntity.addProperty(new Property(null, "user", ValueType.PRIMITIVE, userId));
		EventruleEntity.setType(this.EntityType.getFullQualifiedName().getFullQualifiedNameAsString());
		
		return EventruleEntity;
	}

}
