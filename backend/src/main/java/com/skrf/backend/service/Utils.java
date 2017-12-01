package com.skrf.backend.service;

import java.util.List;
import java.util.Locale;

import org.apache.olingo.commons.api.edm.EdmBindingTarget;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriInfoResource;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.apache.olingo.server.api.uri.UriResourceNavigation;

public class Utils {

	public static EdmEntitySet getNavigationTargetEntitySet(final UriInfoResource uriInfo) throws ODataApplicationException {

	    EdmEntitySet entitySet;
	    final List<UriResource> resourcePaths = uriInfo.getUriResourceParts();

	    // First must be entity set (hence function imports are not supported here).
	    if (resourcePaths.get(0) instanceof UriResourceEntitySet) {
	        entitySet = ((UriResourceEntitySet) resourcePaths.get(0)).getEntitySet();
	    } else {
	        throw new ODataApplicationException("Invalid resource type.",
	                HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ROOT);
	    }

	    int navigationCount = 0;
	    while (entitySet != null
	        && ++navigationCount < resourcePaths.size()
	        && resourcePaths.get(navigationCount) instanceof UriResourceNavigation) {
	        final UriResourceNavigation uriResourceNavigation = (UriResourceNavigation) resourcePaths.get(navigationCount);
	        final EdmBindingTarget target = entitySet.getRelatedBindingTarget(uriResourceNavigation.getProperty().getName());
	        if (target instanceof EdmEntitySet) {
	            entitySet = (EdmEntitySet) target;
	        } else {
	            throw new ODataApplicationException("Singletons not supported", HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(),
	                                                 Locale.ROOT);
	        }
	    }

	    return entitySet;
	}
	
	public static UriResourceNavigation getLastNavigation(final UriInfoResource uriInfo) {

	    final List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
	    int navigationCount = 1;
	    while (navigationCount < resourcePaths.size()
	        && resourcePaths.get(navigationCount) instanceof UriResourceNavigation) {
	        navigationCount++;
	    }

	    return (UriResourceNavigation) resourcePaths.get(--navigationCount);
	}
}
