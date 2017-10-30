package com.skrf.backend.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.CsdlAbstractEdmProvider;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainer;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainerInfo;
import org.apache.olingo.commons.api.edm.provider.CsdlEntitySet;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlPropertyRef;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;
import org.apache.olingo.commons.api.ex.ODataException;


public class SKRFEdmProvider extends CsdlAbstractEdmProvider {
	// Service Namespace
	public static final String NAMESPACE = "OData.SKRF";

	// EDM Container
	public static final String CONTAINER_NAME = "Container";
	public static final FullQualifiedName CONTAINER = new FullQualifiedName(NAMESPACE, CONTAINER_NAME);

	// Entity Types Names
	public static final String ET_EVENTTYPE_NAME = "Eventtype";
	public static final FullQualifiedName ET_EVENTTYPE_FQN = new FullQualifiedName(NAMESPACE, ET_EVENTTYPE_NAME);

	// Entity Set Names
	public static final String ES_EVENTSETTINGS_NAME = "Events_settings";
	
	@Override
	public CsdlEntityType getEntityType(FullQualifiedName entityTypeName) throws ODataException {
		  // this method is called for one of the EntityTypes that are configured in the Schema
		  if(entityTypeName.equals(ET_EVENTTYPE_FQN)){

		    //create EntityType properties
		    CsdlProperty eventtype = new CsdlProperty().setName("eventtype").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
		    CsdlProperty descr = new CsdlProperty().setName("descr").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

		    // create CsdlPropertyRef for Key element
		    CsdlPropertyRef propertyRef = new CsdlPropertyRef();
		    propertyRef.setName("eventtype");

		    // configure EntityType
		    CsdlEntityType entityType = new CsdlEntityType();
		    entityType.setName(ET_EVENTTYPE_NAME);
		    entityType.setProperties(Arrays.asList(eventtype, descr));
		    entityType.setKey(Collections.singletonList(propertyRef));

		    return entityType;
		  }

		  return null;
	}

	@Override
	public CsdlEntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName) throws ODataException {
		  if(entityContainer.equals(CONTAINER)){
		    if(entitySetName.equals(ES_EVENTSETTINGS_NAME)){
		      CsdlEntitySet entitySet = new CsdlEntitySet();
		      entitySet.setName(ES_EVENTSETTINGS_NAME);
		      entitySet.setType(ET_EVENTTYPE_FQN);

		      return entitySet;
		    }
		  }
		  return null;
	}

	@Override
	public CsdlEntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName) throws ODataException {
	    // This method is invoked when displaying the Service Document at e.g. http://localhost:8080/DemoService/DemoService.svc
	    if (entityContainerName == null || entityContainerName.equals(CONTAINER)) {
	        CsdlEntityContainerInfo entityContainerInfo = new CsdlEntityContainerInfo();
	        entityContainerInfo.setContainerName(CONTAINER);
	        return entityContainerInfo;
	    }

	    return null;
	}

	@Override
	public List<CsdlSchema> getSchemas() throws ODataException {
		  // create Schema
		  CsdlSchema schema = new CsdlSchema();
		  schema.setNamespace(NAMESPACE);

		  // add EntityTypes
		  List<CsdlEntityType> entityTypes = new ArrayList<CsdlEntityType>();
		  entityTypes.add(getEntityType(ET_EVENTTYPE_FQN));
		  schema.setEntityTypes(entityTypes);

		  // add EntityContainer
		  schema.setEntityContainer(getEntityContainer());

		  // finally
		  List<CsdlSchema> schemas = new ArrayList<CsdlSchema>();
		  schemas.add(schema);

		  return schemas;
	}

	@Override
	public CsdlEntityContainer getEntityContainer() throws ODataException {
		  // create EntitySets
		  List<CsdlEntitySet> entitySets = new ArrayList<CsdlEntitySet>();
		  entitySets.add(getEntitySet(CONTAINER, ES_EVENTSETTINGS_NAME));

		  // create EntityContainer
		  CsdlEntityContainer entityContainer = new CsdlEntityContainer();
		  entityContainer.setName(CONTAINER_NAME);
		  entityContainer.setEntitySets(entitySets);

		  return entityContainer;
	}
}