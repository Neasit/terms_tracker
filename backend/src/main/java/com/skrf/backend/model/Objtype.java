package com.skrf.backend.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the "OBJTYPE" database table.
 * 
 */
@Entity
@Table(name="objtype")
@NamedQuery(name="Objtype.findAll", query="SELECT o FROM Objtype o")
public class Objtype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="objtype")
	private String objtype;

	@Column(name="descr")
	private String descr;

	@Column(name="initterm")
	private Integer initterm;

	@Column(name="level")
	private Integer level;

	//bi-directional many-to-one association to Object
	@OneToMany(mappedBy="ref_objtype", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Objects> objects;

	public Objtype() {
	}

	public String getObjtype() {
		return this.objtype;
	}

	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Integer getInitterm() {
		return this.initterm;
	}

	public void setInitterm(Integer initterm) {
		this.initterm = initterm;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public List<Objects> getObjects() {
		return this.objects;
	}

	public void setObjects(List<Objects> objects) {
		this.objects = objects;
	}

	public Objects addObject(Objects object) {
		getObjects().add(object);
		object.setRef_Objtype(this);

		return object;
	}

	public Objects removeObject(Objects object) {
		getObjects().remove(object);
		object.setRef_Objtype(null);

		return object;
	}

}