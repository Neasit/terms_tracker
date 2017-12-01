package com.skrf.backend.odatamodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.provider.CsdlEntitySet;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlNavigationProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlNavigationPropertyBinding;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlPropertyRef;
import org.apache.olingo.commons.api.edm.provider.CsdlReferentialConstraint;

import com.skrf.backend.service.SKRFEdmProvider;

public class ModelMetadata {

	public static CsdlEntityType get_type_eventtype() {

		// create EntityType properties
		CsdlProperty eventtype = new CsdlProperty().setName("eventtype")
				.setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
		CsdlProperty descr = new CsdlProperty().setName("descr")
				.setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

		// create CsdlPropertyRef for Key element
		CsdlPropertyRef propertyRef = new CsdlPropertyRef();
		propertyRef.setName("eventtype");

	    // navigation property: one-to-many
	    CsdlNavigationProperty navProp = new CsdlNavigationProperty()
	                            .setName("Rules")
	                            .setType(SKRFEdmProvider.ET_EVENTRULE_FQN)
	                            .setCollection(true)
	                            .setPartner("RefType")
	                            .setNullable(true);
	    
	    List<CsdlNavigationProperty> navPropList = new ArrayList<CsdlNavigationProperty>();
	    navPropList.add(navProp);
	    
		// configure EntityType
		CsdlEntityType entityType = new CsdlEntityType();
		entityType.setName(SKRFEdmProvider.ET_EVENTTYPE_NAME);
		entityType.setProperties(Arrays.asList(eventtype, descr));
		entityType.setKey(Collections.singletonList(propertyRef));
		entityType.setNavigationProperties(navPropList);

		return entityType;
	}

	public static CsdlEntityType get_type_eventrule() {
		// create EntityRule properties
		CsdlProperty eventtype = new CsdlProperty().setName("eventtype")
				.setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
		CsdlProperty rulesnmb = new CsdlProperty().setName("rulesnmb")
				.setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
		CsdlProperty action = new CsdlProperty().setName("action")
				.setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
		CsdlProperty intvalue = new CsdlProperty().setName("intvalue")
				.setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
		CsdlProperty user = new CsdlProperty().setName("user")
				.setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());

		// create CsdlPropertyRef for Key element
		List<CsdlPropertyRef> keys = new ArrayList<CsdlPropertyRef>();
		CsdlPropertyRef propertyRef = new CsdlPropertyRef();
		propertyRef.setName("eventtype");
		keys.add(propertyRef);
		propertyRef = new CsdlPropertyRef();
		propertyRef.setName("rulesnmb");
		keys.add(propertyRef);
		// create Navigation property
		CsdlNavigationProperty navProp = new CsdlNavigationProperty();
		navProp.setName("RefType").setType(SKRFEdmProvider.ET_EVENTTYPE_FQN).setNullable(false)
				.setPartner("Rules");
		//Reference!!!
		CsdlReferentialConstraint navRef = new CsdlReferentialConstraint();
		navRef.setProperty("eventtype");
		navRef.setReferencedProperty("eventtype");
		navProp.setReferentialConstraints(Collections.singletonList(navRef));
		// Done
		List<CsdlNavigationProperty> navPropList = new ArrayList<CsdlNavigationProperty>();
		navPropList.add(navProp);

		// configure EntityType
		CsdlEntityType entityType = new CsdlEntityType();
		entityType.setName(SKRFEdmProvider.ET_EVENTRULE_NAME);
		entityType.setProperties(Arrays.asList(eventtype, rulesnmb, action, intvalue, user));
		entityType.setKey(keys);
		entityType.setNavigationProperties(navPropList);

		return entityType;
	}

	public static CsdlEntitySet get_set_eventtypes() {
		CsdlEntitySet entitySet = new CsdlEntitySet();
		//Navigation
		CsdlNavigationPropertyBinding navPropBinding = new CsdlNavigationPropertyBinding();
		navPropBinding.setTarget(SKRFEdmProvider.ES_EVENTRULES_NAME);//target entitySet, where the nav prop points to
		navPropBinding.setPath("Rules"); // the path from entity type to navigation property
		List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<CsdlNavigationPropertyBinding>();
		navPropBindingList.add(navPropBinding);
		entitySet.setNavigationPropertyBindings(navPropBindingList);
		
		entitySet.setName(SKRFEdmProvider.ES_EVENTTYPES_NAME);
		entitySet.setType(SKRFEdmProvider.ET_EVENTTYPE_FQN);

		return entitySet;
	}

	public static CsdlEntitySet get_set_eventrules() {
		CsdlEntitySet entitySet = new CsdlEntitySet();
		//Navigation
		CsdlNavigationPropertyBinding navPropBinding = new CsdlNavigationPropertyBinding();
		navPropBinding.setPath("RefType"); // the path from entity type to navigation property
		navPropBinding.setTarget(SKRFEdmProvider.ES_EVENTTYPES_NAME); // target entitySet, where the nav prop points to
		List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<CsdlNavigationPropertyBinding>();
		navPropBindingList.add(navPropBinding);
		entitySet.setNavigationPropertyBindings(navPropBindingList);
		//Name
		entitySet.setName(SKRFEdmProvider.ES_EVENTRULES_NAME);
		entitySet.setType(SKRFEdmProvider.ET_EVENTRULE_FQN);

		return entitySet;
	}
}
