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
import org.apache.olingo.commons.api.edm.provider.CsdlNavigationProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlNavigationPropertyBinding;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlPropertyRef;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;
import org.apache.olingo.commons.api.ex.ODataException;

import com.skrf.backend.odatamodel.ModelMetadata;

public class SKRFEdmProvider extends CsdlAbstractEdmProvider {
	// Service Namespace
	public static final String NAMESPACE = "OData.SKRF";

	// EDM Container
	public static final String CONTAINER_NAME = "Container";
	public static final FullQualifiedName CONTAINER = new FullQualifiedName(NAMESPACE, CONTAINER_NAME);

	// Entity Types Names
	public static final String ET_EVENTTYPE_NAME = "Eventtype";
	public static final FullQualifiedName ET_EVENTTYPE_FQN = new FullQualifiedName(NAMESPACE, ET_EVENTTYPE_NAME);
	public static final String ET_EVENTRULE_NAME = "Eventrule";
	public static final FullQualifiedName ET_EVENTRULE_FQN = new FullQualifiedName(NAMESPACE, ET_EVENTRULE_NAME);
	
	// Entity Set Names
	public static final String ES_EVENTTYPES_NAME = "Eventtypes";
	public static final String ES_EVENTRULES_NAME = "Eventrules";

	@Override
	public CsdlEntityType getEntityType(FullQualifiedName entityTypeName) throws ODataException {
		// this method is called for one of the EntityTypes that are configured in the
		// Schema
		if (entityTypeName.equals(ET_EVENTTYPE_FQN)) {
			return ModelMetadata.get_type_eventtype();
		} else if (entityTypeName.equals(ET_EVENTRULE_FQN)) {
			return ModelMetadata.get_type_eventrule();
		}

		return null;
	}

	@Override
	public CsdlEntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName) throws ODataException {
		if (entityContainer.equals(CONTAINER)) {
			if (entitySetName.equals(ES_EVENTTYPES_NAME)) {
				return ModelMetadata.get_set_eventtypes();
			} else if (entitySetName.equals(ES_EVENTRULES_NAME)) {
				return ModelMetadata.get_set_eventrules();
			}
		}
		return null;
	}

	@Override
	public CsdlEntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName) throws ODataException {
		// This method is invoked when displaying the Service Document at e.g.
		// http://localhost:8080/DemoService/DemoService.svc
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
		entityTypes.add(getEntityType(ET_EVENTRULE_FQN));
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
		entitySets.add(getEntitySet(CONTAINER, ES_EVENTTYPES_NAME));
		entitySets.add(getEntitySet(CONTAINER, ES_EVENTRULES_NAME));

		// create EntityContainer
		CsdlEntityContainer entityContainer = new CsdlEntityContainer();
		entityContainer.setName(CONTAINER_NAME);
		entityContainer.setEntitySets(entitySets);

		return entityContainer;
	}
}
