package com.skrf.backend.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-11-27T12:30:56.286+0300")
@StaticMetamodel(Event.class)
public class Event_ {
	public static volatile SingularAttribute<Event, Integer> id;
	public static volatile SingularAttribute<Event, Date> crtdate;
	public static volatile SingularAttribute<Event, Eventtype> ref_eventtype;
	public static volatile SingularAttribute<Event, Objects> ref_obj;
	public static volatile SingularAttribute<Event, String> value;
}
