package com.skrf.backend.odatamodel;

import java.util.List;
import java.util.Locale;

import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmPrimitiveType;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeException;
import org.apache.olingo.commons.api.edm.EdmProperty;
import org.apache.olingo.commons.api.edm.EdmType;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriParameter;

public final class DataManipulatorHelper {

	public static String getURIKeyValueByName(String name, List<UriParameter> keyParams, EdmEntityType edmEntityType)
			throws ODataApplicationException {

		String valueAsString = null;
		// loop over all keys
		for (final UriParameter key : keyParams) {
			// key
			if (name.equals(key.getName())) {
				String keyText = key.getText();
				keyText = keyText.replaceAll("'", "");
				// Edm: we need this info for the comparison below
				EdmProperty edmKeyProperty = (EdmProperty) edmEntityType.getProperty(name);
				Boolean isNullable = edmKeyProperty.isNullable();
				Integer maxLength = edmKeyProperty.getMaxLength();
				Integer precision = edmKeyProperty.getPrecision();
				Boolean isUnicode = edmKeyProperty.isUnicode();
				Integer scale = edmKeyProperty.getScale();
				// get the EdmType in order to compare
				EdmType edmType = edmKeyProperty.getType();
				// Key properties must be instance of primitive type
				EdmPrimitiveType edmPrimitiveType = (EdmPrimitiveType) edmType;
				if (!edmPrimitiveType.validate(keyText, isNullable, maxLength, precision, scale, isUnicode)) {
					throw new ODataApplicationException("Value is incompitable",
							HttpStatusCode.INTERNAL_SERVER_ERROR.getStatusCode(), Locale.ENGLISH);
				} else {
					valueAsString = keyText;
				}
			}
		}
		return valueAsString;

	}
}
