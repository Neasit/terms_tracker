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
import org.apache.olingo.commons.api.edm.EdmPrimitiveType;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeException;
import org.apache.olingo.commons.api.edm.EdmProperty;
import org.apache.olingo.commons.api.edm.EdmType;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriParameter;

public abstract class entity_manipulator {

	protected EntityManagerFactory emf;

	public entity_manipulator() {
		this.emf = Persistence.createEntityManagerFactory("com.skrf.backend");
	}

	public EntityCollection readEntitySetData(EdmEntitySet edmEntitySet) throws ODataApplicationException {

		return null;
	}

	public Entity readEntityData(EdmEntitySet edmEntitySet, List<UriParameter> keyParams)
			throws ODataApplicationException {

		return null;
	}

	public static Entity findEntity(EdmEntityType edmEntityType, EntityCollection rt_entitySet,
			List<UriParameter> keyParams) throws ODataApplicationException {

		List<Entity> entityList = rt_entitySet.getEntities();

		// loop over all entities in order to find that one that matches all keys in
		// request
		// an example could be e.g. contacts(ContactID=1, CompanyID=1)
		for (Entity rt_entity : entityList) {
			boolean foundEntity = entityMatchesAllKeys(edmEntityType, rt_entity, keyParams);
			if (foundEntity) {
				return rt_entity;
			}
		}

		return null;
	}

	public static boolean entityMatchesAllKeys(EdmEntityType edmEntityType, Entity rt_entity,
			List<UriParameter> keyParams) throws ODataApplicationException {

		// loop over all keys
		for (final UriParameter key : keyParams) {
			// key
			String keyName = key.getName();
			String keyText = key.getText();
			keyText = keyText.replaceAll("'", "");

			// Edm: we need this info for the comparison below
			EdmProperty edmKeyProperty = (EdmProperty) edmEntityType.getProperty(keyName);
			Boolean isNullable = edmKeyProperty.isNullable();
			Integer maxLength = edmKeyProperty.getMaxLength();
			Integer precision = edmKeyProperty.getPrecision();
			Boolean isUnicode = edmKeyProperty.isUnicode();
			Integer scale = edmKeyProperty.getScale();
			// get the EdmType in order to compare
			EdmType edmType = edmKeyProperty.getType();
			// Key properties must be instance of primitive type
			EdmPrimitiveType edmPrimitiveType = (EdmPrimitiveType) edmType;

			// Runtime data: the value of the current entity
			Object valueObject = rt_entity.getProperty(keyName).getValue(); // null-check is done in FWK

			// now need to compare the valueObject with the keyText String
			// this is done using the type.valueToString //
			String valueAsString = null;
			try {
				valueAsString = edmPrimitiveType.valueToString(valueObject, isNullable, maxLength, precision, scale,
						isUnicode);
			} catch (EdmPrimitiveTypeException e) {
				throw new ODataApplicationException("Failed to retrieve String value",
						HttpStatusCode.INTERNAL_SERVER_ERROR.getStatusCode(), Locale.ENGLISH, e);
			}

			if (valueAsString == null) {
				return false;
			}

			boolean matches = valueAsString.equals(keyText);
			if (!matches) {
				// if any of the key properties is not found in the entity, we don't need to
				// search further
				return false;
			}
		}

		return true;
	}

	protected EntityManager createEntityManager() {
		return emf.createEntityManager();
	}
}
