package com.skrf.backend.odatamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriParameter;

public abstract class entity_manipulator {
	
	protected EntityManagerFactory emf;

	public entity_manipulator() { 
		this.emf = Persistence.createEntityManagerFactory("com.skrf.backend");	
	}
	
    public EntityCollection readEntitySetData(EdmEntitySet edmEntitySet) throws ODataApplicationException{
    	
    	return null;
    }
    

    public Entity readEntityData(EdmEntitySet edmEntitySet, List<UriParameter> keyParams) throws ODataApplicationException{

        return null;
    } 
    
	protected EntityManager createEntityManager() {
		return emf.createEntityManager();
	}
	
}
