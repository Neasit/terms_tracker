package com.skrf.backend.odatamodel;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriParameter;

import com.skrf.backend.model.Eventrule;
import com.skrf.backend.model.User;

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
			EventruleEntity = new Entity();
			EventruleEntity.addProperty(new Property(null, "eventtype", ValueType.PRIMITIVE, Eventrule_bd.getId().getEventtype()));
			EventruleEntity.addProperty(new Property(null, "rulesnmb", ValueType.PRIMITIVE, Eventrule_bd.getId().getRulesnmb()));
			EventruleEntity.addProperty(new Property(null, "action", ValueType.PRIMITIVE, Eventrule_bd.getAction()));
			EventruleEntity.addProperty(new Property(null, "intvalue", ValueType.PRIMITIVE, Eventrule_bd.getIntvalue()));
			User usr = Eventrule_bd.getRefUser();
			Integer userId = null;
			if (usr != null) {
				userId = usr.getId();
			}
			EventruleEntity.addProperty(new Property(null, "user", ValueType.PRIMITIVE, userId));
		}
		return EventruleEntity;
	}

	@SuppressWarnings("unchecked")
	private EntityCollection getAllEventRules() {
		EntityCollection retEntitySet = new EntityCollection();
		List<Eventrule> Eventrules_bd = null;
		EntityManager em = createEntityManager();
		User usr = null;
		Integer userId;
		
		try {
			Eventrules_bd = em.createNamedQuery("Eventrule.findAll").getResultList();
		} catch (Exception e) {
			logger.error("Error by selecting data: {}", e.getMessage());
		} finally {
			em.close();
		}

		for (Eventrule er : Eventrules_bd) {
			userId = null;
			usr = null;
			Entity EventruleEntity = new Entity();
			EventruleEntity.addProperty(new Property(null, "eventtype", ValueType.PRIMITIVE, er.getId().getEventtype()));
			EventruleEntity.addProperty(new Property(null, "rulesnmb", ValueType.PRIMITIVE, er.getId().getRulesnmb()));
			EventruleEntity.addProperty(new Property(null, "action", ValueType.PRIMITIVE, er.getAction()));
			EventruleEntity.addProperty(new Property(null, "intvalue", ValueType.PRIMITIVE, er.getIntvalue()));
			usr = er.getRefUser();
			if (usr != null) {
				userId = usr.getId();
			}
			EventruleEntity.addProperty(new Property(null, "user", ValueType.PRIMITIVE, userId));

			retEntitySet.getEntities().add(EventruleEntity);
		}

		return retEntitySet;
	}

}
