package com.skrf.backend.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the "EVENT" database table.
 * 
 */
@Entity
@Table(name="event")
@NamedQuery(name="Event.findAll", query="SELECT e FROM Event e")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name="crtdate")
	private Date crtdate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="eventtype" )
	@JsonIgnore
	private Eventtype ref_eventtype;
	
	@Transient
	private Integer objid;		//only JSON Model
	@Transient	
	private String eventtype;	//only JSON Model
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="objid" )
	@JsonIgnore
	private Objects ref_obj;
	
	@Column(name="value")
	private String value;

	public Event() {
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCrtdate() {
		return this.crtdate;
	}

	public void setCrtdate(Date crtdate) {
		this.crtdate = crtdate;
	}

	public Eventtype getRef_eventtype() {
		return this.ref_eventtype;
	}

	public void setRef_eventtype(Eventtype eventtypeBean) {
		this.ref_eventtype = eventtypeBean;
	}

	public Object getRef_object() {
		return this.ref_obj;
	}

	public void setRef_object(Objects object) {
		this.ref_obj = object;
	}

	public String getEventtype() {
		return this.eventtype;
	}
	
	public Integer getObjid() {
		return this.objid;
	}
}