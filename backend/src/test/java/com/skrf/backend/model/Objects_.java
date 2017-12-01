package com.skrf.backend.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-11-27T12:30:56.512+0300")
@StaticMetamodel(Objects.class)
public class Objects_ {
	public static volatile SingularAttribute<Objects, Integer> id;
	public static volatile SingularAttribute<Objects, Boolean> arch;
	public static volatile SingularAttribute<Objects, Date> crtdate;
	public static volatile SingularAttribute<Objects, String> descr;
	public static volatile SingularAttribute<Objects, String> guard;
	public static volatile SingularAttribute<Objects, User> ref_user;
	public static volatile SingularAttribute<Objects, Boolean> important;
	public static volatile SingularAttribute<Objects, String> objnumber;
	public static volatile SingularAttribute<Objects, Objtype> ref_objtype;
	public static volatile SingularAttribute<Objects, String> reason;
	public static volatile ListAttribute<Objects, Event> events;
	public static volatile ListAttribute<Objects, Participant> participants;
	public static volatile ListAttribute<Objects, Term> terms;
}
