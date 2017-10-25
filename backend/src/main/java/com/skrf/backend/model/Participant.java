package com.skrf.backend.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


/**
 * The persistent class for the "PARTICIPANTS" database table.
 * 
 */
@Entity
@Table(name="participants")
@NamedQuery(name="Participant.findAll", query="SELECT p FROM Participant p")
public class Participant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name="date")
	private Date date;

	@Column(name="fullname")
	private String fullname;
	
	@Transient
	private Integer objid;	//Only for JSON Model
	
	@ManyToOne
	@JoinColumn(name="objid")
	@JsonIgnore
	private Objects ref_objid;

	@Column(name="position")
	private String position;

	public Participant() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Integer getObjid() {
		return this.objid;
	}

	public void setObjid(Integer objid) {
		this.objid = objid;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Object getRef_objid() {
		return this.ref_objid;
	}

	public void setRef_objid(Objects object) {
		this.ref_objid = object;
	}

}