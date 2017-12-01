package com.skrf.backend.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-11-27T12:30:56.587+0300")
@StaticMetamodel(Term.class)
public class Term_ {
	public static volatile SingularAttribute<Term, Integer> id;
	public static volatile SingularAttribute<Term, String> acceptby;
	public static volatile SingularAttribute<Term, Integer> level;
	public static volatile SingularAttribute<Term, Objects> ref_objid;
	public static volatile SingularAttribute<Term, Date> term;
	public static volatile SingularAttribute<Term, String> ttype;
}
