package com.skrf.backend.service;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.data.ContextURL.Suffix;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmNavigationProperty;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.ODataLibraryException;
import org.apache.olingo.server.api.ODataRequest;
import org.apache.olingo.server.api.ODataResponse;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.processor.EntityCollectionProcessor;
import org.apache.olingo.server.api.serializer.EntityCollectionSerializerOptions;
import org.apache.olingo.server.api.serializer.ODataSerializer;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.serializer.SerializerResult;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriParameter;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.apache.olingo.server.api.uri.UriResourceNavigation;

import com.skrf.backend.odatamodel.DataManipulator;
import com.skrf.backend.odatamodel.DataManipulatorFactory;
import com.skrf.backend.odatamodel.entity_manipulator;
import com.skrf.backend.odatamodel.events_settings;

public class SKRFEntityCollectionProcessor implements EntityCollectionProcessor {
	private OData odata;
	private ServiceMetadata serviceMetadata;
	private DataManipulatorFactory dataManipulatorF;

	public SKRFEntityCollectionProcessor(DataManipulatorFactory factory) {
		this.dataManipulatorF = factory;
	}

	@Override
	public void init(OData arg0, ServiceMetadata arg1) {
		this.odata = arg0;
		this.serviceMetadata = arg1;
	}

	@Override
	public void readEntityCollection(ODataRequest arg0, ODataResponse arg1, UriInfo arg2, ContentType arg3)
			throws ODataApplicationException, ODataLibraryException {
		EdmEntitySet responseEdmEntitySet = null; // for building ContextURL
		EntityCollection responseEntityCollection = null; // for the response body
		DataManipulator storage = null; // works with BD

		// 1st we have retrieve the requested EntitySet from the uriInfo object
		// (representation of the parsed service URI)
		List<UriResource> resourcePaths = arg2.getUriResourceParts();
		int segmentCount = resourcePaths.size();

		UriResource uriResource = resourcePaths.get(0); // the first segment is the EntitySet
		if (!(uriResource instanceof UriResourceEntitySet)) {
			throw new ODataApplicationException("Only EntitySet is supported",
					HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ROOT);
		}
		UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) uriResource;
		EdmEntitySet startEdmEntitySet = uriResourceEntitySet.getEntitySet();

		// 2nd: fetch the data from backend for this requested EntitySetName
		// it has to be delivered as EntitySet object
		if (segmentCount == 1) {
			responseEdmEntitySet = startEdmEntitySet; // first (and only) entitySet
			storage = this.dataManipulatorF.getDataManipulator(startEdmEntitySet);
			responseEntityCollection = storage.readEntitySetData();
		} else if (segmentCount == 2) { // Navigation
			UriResource lastSegment = Utils.getLastNavigation(arg2);
		    if(lastSegment instanceof UriResourceNavigation){
		        UriResourceNavigation uriResourceNavigation = (UriResourceNavigation)lastSegment;
		        List<UriParameter> navKeyPredicates = uriResourceNavigation.getKeyPredicates();
		        responseEdmEntitySet = Utils.getNavigationTargetEntitySet(arg2);
		        
		        // 2nd: fetch the data from backend
		        // first fetch the entity where the first segment of the URI points to
		        // e.g. Categories(3)/Products first find the single entity: Category(3)
		        List<UriParameter> keyPredicates = uriResourceEntitySet.getKeyPredicates();
		        storage = this.dataManipulatorF.getDataManipulator(startEdmEntitySet);
		        Entity sourceEntity = storage.readEntityData(keyPredicates);
		        // error handling for e.g.  DemoService.svc/Categories(99)/Products
		        if(sourceEntity == null) {
		            throw new ODataApplicationException("Entity not found.", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ROOT);
		        }
		        // then fetch the entity collection where the entity navigates to
		        storage = this.dataManipulatorF.getDataManipulator(responseEdmEntitySet);
		        
		        responseEntityCollection = storage.readRealatedFor(sourceEntity, navKeyPredicates); 
		    }
		} else {
			throw new ODataApplicationException("Not supported", HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(),Locale.ROOT);
		}

		// 3rd: create a serializer based on the requested format (json)
	    ContextURL contextUrl = ContextURL.with().entitySet(responseEdmEntitySet).build();;
	    final String id = arg0.getRawBaseUri() + "/" + responseEdmEntitySet.getName();
	    EntityCollectionSerializerOptions opts = EntityCollectionSerializerOptions.with().contextURL(contextUrl).id(id).build();
	    EdmEntityType edmEntityType = responseEdmEntitySet.getEntityType();

	    ODataSerializer serializer = odata.createSerializer(arg3);
		try {
			SerializerResult serializerResult = serializer.entityCollection(serviceMetadata, edmEntityType, responseEntityCollection,
					opts);
			InputStream serializedContent = serializerResult.getContent();
			// Finally: configure the response object: set the body, headers and status code
			arg1.setContent(serializedContent);
			arg1.setStatusCode(HttpStatusCode.OK.getStatusCode());
			arg1.setHeader(HttpHeader.CONTENT_TYPE, arg3.toContentTypeString());
		} catch (SerializerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
