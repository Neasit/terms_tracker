package com.skrf.backend.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-11-27T12:30:56.568+0300")
@StaticMetamodel(Participant.class)
public class Participant_ {
	public static volatile SingularAttribute<Participant, Integer> id;
	public static volatile SingularAttribute<Participant, Date> date;
	public static volatile SingularAttribute<Participant, String> fullname;
	public static volatile SingularAttribute<Participant, Objects> ref_objid;
	public static volatile SingularAttribute<Participant, String> position;
}
