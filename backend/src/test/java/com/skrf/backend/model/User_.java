package com.skrf.backend.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-11-27T12:30:56.646+0300")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, String> position;
	public static volatile SingularAttribute<User, String> rank;
	public static volatile ListAttribute<User, Eventrule> eventrules;
	public static volatile ListAttribute<User, Objects> objects;
}
