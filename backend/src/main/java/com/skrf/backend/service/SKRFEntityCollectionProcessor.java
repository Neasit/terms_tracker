package com.skrf.backend.service;

import java.io.InputStream;
import java.util.List;

import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.data.EntityCollection;
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
import org.apache.olingo.server.api.processor.EntityCollectionProcessor;
import org.apache.olingo.server.api.serializer.EntityCollectionSerializerOptions;
import org.apache.olingo.server.api.serializer.ODataSerializer;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.serializer.SerializerResult;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;

import com.skrf.backend.odatamodel.entity_manipulator;
import com.skrf.backend.odatamodel.events_settings;

public class SKRFEntityCollectionProcessor implements EntityCollectionProcessor {
	private OData odata;
	private ServiceMetadata serviceMetadata;
	private entity_manipulator storage;
	
	public SKRFEntityCollectionProcessor(entity_manipulator storage) {
		this.storage = storage;
	}
	
	@Override
	public void init(OData arg0, ServiceMetadata arg1) {
		  this.odata = arg0;
		  this.serviceMetadata = arg1;
	}

	@Override
	public void readEntityCollection(ODataRequest arg0, ODataResponse arg1, UriInfo arg2, ContentType arg3)
			throws ODataApplicationException, ODataLibraryException {
		  // 1st we have retrieve the requested EntitySet from the uriInfo object (representation of the parsed service URI)
		  List<UriResource> resourcePaths = arg2.getUriResourceParts();
		  UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) resourcePaths.get(0);
		  EdmEntitySet edmEntitySet = uriResourceEntitySet.getEntitySet();

		  // 2nd: fetch the data from backend for this requested EntitySetName
		  // it has to be delivered as EntitySet object
		  events_settings EventsSettings = new events_settings();
		  EntityCollection entitySet = EventsSettings.readEntitySetData(edmEntitySet); // getData(edmEntitySet);

		  // 3rd: create a serializer based on the requested format (json)
		  ODataSerializer serializer = odata.createSerializer(arg3);

		  // 4th: Now serialize the content: transform from the EntitySet object to InputStream
		  EdmEntityType edmEntityType = edmEntitySet.getEntityType();
		  ContextURL contextUrl = ContextURL.with().entitySet(edmEntitySet).build();

		  final String id = arg0.getRawBaseUri() + "/" + edmEntitySet.getName();
		  EntityCollectionSerializerOptions opts = EntityCollectionSerializerOptions.with().id(id).contextURL(contextUrl).build();
		  try {
			  SerializerResult serializerResult = serializer.entityCollection(serviceMetadata, edmEntityType, entitySet, opts);
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
