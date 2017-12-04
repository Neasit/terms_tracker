package com.skrf.backend.odatamodel;

import java.util.List;

import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriParameter;

public interface DataManipulator {

	public EntityCollection readEntitySetData() throws ODataApplicationException;
	public Entity readEntityData(List<UriParameter> keyPredicates) throws ODataApplicationException;
	public EntityCollection readRealatedFor(Entity filterEntity, List<UriParameter> keyPredicates) throws ODataApplicationException;
	
}
