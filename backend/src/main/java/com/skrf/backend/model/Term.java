package com.skrf.backend.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


/**
 * The persistent class for the "TERMS" database table.
 * 
 */
@Entity
@Table(name="terms")
@NamedQuery(name="Term.findAll", query="SELECT t FROM Term t")
public class Term implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	@Column(name="acceptby")
	private String acceptby;

	@Column(name="level")
	private Integer level;

	@Transient
	private Integer objid;		//Only for JSON Model
	
	@ManyToOne
	@JoinColumn(name="objid" )
	@JsonIgnore
	private Objects ref_objid;

	@Temporal(TemporalType.DATE)
	@Column(name="term")
	private Date term;

	@Column(name="ttype")
	private String ttype;

	public Term() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAcceptby() {
		return this.acceptby;
	}

	public void setAcceptby(String acceptby) {
		this.acceptby = acceptby;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getObjid() {
		return this.objid;
	}

	public void setObjid(Integer objid) {
		this.objid = objid;
	}

	public Date getTerm() {
		return this.term;
	}

	public void setTerm(Date term) {
		this.term = term;
	}

	public String getType() {
		return this.ttype;
	}

	public void setType(String ttype) {
		this.ttype = ttype;
	}

	public Object getRef_objid() {
		return this.ref_objid;
	}

	public void setRef_objid(Objects object) {
		this.ref_objid = object;
	}

}