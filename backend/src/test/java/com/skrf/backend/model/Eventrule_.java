package com.skrf.backend.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-11-27T12:30:56.364+0300")
@StaticMetamodel(Eventrule.class)
public class Eventrule_ {
	public static volatile SingularAttribute<Eventrule, EventrulePK> id;
	public static volatile SingularAttribute<Eventrule, String> action;
	public static volatile SingularAttribute<Eventrule, Integer> intvalue;
	public static volatile SingularAttribute<Eventrule, User> ref_user;
	public static volatile SingularAttribute<Eventrule, Eventtype> eventtypeBean;
}
