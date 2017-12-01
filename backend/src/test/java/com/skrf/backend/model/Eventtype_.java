package com.skrf.backend.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-11-27T12:30:56.456+0300")
@StaticMetamodel(Eventtype.class)
public class Eventtype_ {
	public static volatile SingularAttribute<Eventtype, String> eventtype;
	public static volatile SingularAttribute<Eventtype, String> descr;
	public static volatile ListAttribute<Eventtype, Event> events;
	public static volatile ListAttribute<Eventtype, Eventrule> eventrules;
}
