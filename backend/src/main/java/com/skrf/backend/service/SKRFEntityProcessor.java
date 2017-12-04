package com.skrf.backend.service;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.data.ContextURL.Suffix;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.ODataLibraryException;
import org.apache.olingo.server.api.ODataRequest;
import org.apache.olingo.server.api.ODataResponse;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.processor.EntityProcessor;
import org.apache.olingo.server.api.serializer.EntitySerializerOptions;
import org.apache.olingo.server.api.serializer.ODataSerializer;
import org.apache.olingo.server.api.serializer.SerializerResult;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriParameter;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.apache.olingo.server.api.uri.UriResourceNavigation;

import com.skrf.backend.odatamodel.DataManipulator;
import com.skrf.backend.odatamodel.DataManipulatorFactory;

public class SKRFEntityProcessor implements EntityProcessor {
	
    private OData odata;
    private ServiceMetadata serviceMetadata;
	private DataManipulatorFactory dataManipulatorF;
	
	public SKRFEntityProcessor(DataManipulatorFactory factory) {
		this.dataManipulatorF = factory;
	}
    
	@Override
	public void init(OData odata, ServiceMetadata serviceMetadata) {
		// TODO Auto-generated method stub
        this.odata = odata;
        this.serviceMetadata = serviceMetadata;
	}

	@Override
	public void readEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType responseFormat)
			throws ODataApplicationException, ODataLibraryException {
		EdmEntitySet responseEdmEntitySet = null; // for building ContextURL
		Entity responseEntity = null; // for the response body
		DataManipulator storage = null; // works with BD
		
	    // 1. retrieve the Entity Type
	    List<UriResource> resourcePaths = uriInfo.getUriResourceParts();
	    int segmentCount = resourcePaths.size();
	    // Note: only in our example we can assume that the first segment is the EntitySet
	    UriResource uriResource = resourcePaths.get(0); // the first segment is the EntitySet
		if (!(uriResource instanceof UriResourceEntitySet)) {
			throw new ODataApplicationException("Only EntitySet is supported",
					HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ROOT);
		}
		UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) uriResource;
		EdmEntitySet startEdmEntitySet = uriResourceEntitySet.getEntitySet();
		List<UriParameter> keyPredicates = uriResourceEntitySet.getKeyPredicates();
		
	    // 2. retrieve the data from backend
	    if (segmentCount == 1) {
	    	responseEdmEntitySet = startEdmEntitySet; // first (and only) entitySet
	    	storage = this.dataManipulatorF.getDataManipulator(responseEdmEntitySet);
	    	responseEntity = storage.readEntityData(keyPredicates);
	    } else if (segmentCount == 2) {
			UriResource lastSegment = Utils.getLastNavigation(uriInfo);
		    if(lastSegment instanceof UriResourceNavigation){
		        UriResourceNavigation uriResourceNavigation = (UriResourceNavigation)lastSegment;
		        List<UriParameter> navKeyPredicates = uriResourceNavigation.getKeyPredicates();

		        responseEdmEntitySet = Utils.getNavigationTargetEntitySet(uriInfo);
		        // 2nd: fetch the data from backend
		        // first fetch the entity where the first segment of the URI points to
		        // e.g. Categories(3)/Products first find the single entity: Category(3)
		        storage = this.dataManipulatorF.getDataManipulator(startEdmEntitySet);
		        Entity sourceEntity = storage.readEntityData(keyPredicates);
		        // error handling for e.g.  DemoService.svc/Categories(99)/Products
		        if(sourceEntity == null) {
		            throw new ODataApplicationException("Entity not found.", HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ROOT);
		        }
		        // then fetch the entity collection where the entity navigates to
		        storage = this.dataManipulatorF.getDataManipulator(responseEdmEntitySet);
		        
		        responseEntity = storage.readRealatedFor(sourceEntity, navKeyPredicates).getEntities().get(0);   
		    }
		} else {
			throw new ODataApplicationException("Not supported", HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(),Locale.ROOT);
		}
	    // 3. serialize
	    EdmEntityType entityType = responseEdmEntitySet.getEntityType();

	    ContextURL contextUrl = ContextURL.with().entitySet(responseEdmEntitySet).suffix(Suffix.ENTITY).build();
	    // expand and select currently not supported
	    EntitySerializerOptions options = EntitySerializerOptions.with().contextURL(contextUrl).build();

	    ODataSerializer serializer = odata.createSerializer(responseFormat);
	    SerializerResult serializerResult = serializer.entity(serviceMetadata, entityType, responseEntity, options);
	    InputStream entityStream = serializerResult.getContent();

	    //4. configure the response object
	    response.setContent(entityStream);
	    response.setStatusCode(HttpStatusCode.OK.getStatusCode());
	    response.setHeader(HttpHeader.CONTENT_TYPE, responseFormat.toContentTypeString());

	}

	@Override
	public void createEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType requestFormat,
			ContentType responseFormat) throws ODataApplicationException, ODataLibraryException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo, ContentType requestFormat,
			ContentType responseFormat) throws ODataApplicationException, ODataLibraryException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteEntity(ODataRequest request, ODataResponse response, UriInfo uriInfo)
			throws ODataApplicationException, ODataLibraryException {
		// TODO Auto-generated method stub

	}

}
