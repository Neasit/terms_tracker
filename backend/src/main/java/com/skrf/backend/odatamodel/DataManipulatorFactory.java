package com.skrf.backend.odatamodel;

import java.util.Locale;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.ODataApplicationException;

import com.skrf.backend.service.SKRFEdmProvider;

public class DataManipulatorFactory {
	protected EntityManagerFactory emf;		//JPA Manager
	
	
	public DataManipulatorFactory() {
		this.emf = Persistence.createEntityManagerFactory("com.skrf.backend");
	}
	
	public DataManipulator getDataManipulator(EdmEntitySet edmEntitySet) throws ODataApplicationException {
		DataManipulator ref_dm = null;
		
		switch (edmEntitySet.getName()) {
			case SKRFEdmProvider.ES_EVENTTYPES_NAME:
				ref_dm = new EventtypeEntitySet(this.emf, edmEntitySet);
				break;
			case SKRFEdmProvider.ES_EVENTRULES_NAME:
				ref_dm = new EventruleEntitySet(this.emf, edmEntitySet);
				break;
			default:
				throw new ODataApplicationException("Collection name is unknown",
						HttpStatusCode.BAD_REQUEST.getStatusCode(), Locale.ENGLISH);
			};
		
		return ref_dm;
		
	}

}
