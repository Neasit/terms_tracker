package com.skrf.backend.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-11-27T12:30:56.543+0300")
@StaticMetamodel(Objtype.class)
public class Objtype_ {
	public static volatile SingularAttribute<Objtype, String> objtype;
	public static volatile SingularAttribute<Objtype, String> descr;
	public static volatile SingularAttribute<Objtype, Integer> initterm;
	public static volatile SingularAttribute<Objtype, Integer> level;
	public static volatile ListAttribute<Objtype, Objects> objects;
}
