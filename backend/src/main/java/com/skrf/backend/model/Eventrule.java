package com.skrf.backend.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the "EVENTRULES" database table.
 * 
 */
@Entity
@Table(name="eventrules")
@NamedQueries({
@NamedQuery(name="Eventrule.findAll", query="SELECT e FROM Eventrule e"),
@NamedQuery(name="Eventrule.findByType", query="SELECT e FROM Eventrule e WHERE e.id.eventtype = :eventtype"),
@NamedQuery(name="Eventrule.findByKey", 
			query="SELECT e FROM Eventrule e WHERE e.id.eventtype = :eventtype AND e.id.rulesnmb = :rulenmb"),
})
public class Eventrule implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Transient
	private String eventtype;		//Only for JSON Model
	@Transient	
	private Integer rulesnmb;		//Only for JSON Model
	
	@EmbeddedId
	@JsonIgnore
	private EventrulePK id;

	@Column(name="action")
	private String action;

	@Column(name="intvalue")
	private Integer intvalue;

	@Transient
	private Integer act_user;		//Only for JSON Model
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="act_user")
	@JsonIgnore
	private User ref_user;

	//bi-directional many-to-one association to Eventtype
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="eventtype", insertable=false, updatable=false)
	@JsonIgnore
	private Eventtype eventtypeBean;

	public Eventrule() {
	}
	
	public String getEventtype() {
		return this.eventtype;
	}
	
	public void setEventtype(String eventtype) {
		this.eventtype = eventtype;
	}
	
	public Integer getRulesnmb() {
		return this.rulesnmb;
	}
	
	public void setRulesnmb(Integer rulesnmb) {
		this.rulesnmb = rulesnmb;
	}
	
	public EventrulePK getId() {
		return this.id;
	}

	public void setId(EventrulePK id) {
		this.id = id;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getIntvalue() {
		return this.intvalue;
	}

	public void setIntvalue(Integer intvalue) {
		this.intvalue = intvalue;
	}

	public Integer getAct_user() {
		return this.act_user;
	}

	public void setAct_user(Integer act_user) {
		this.act_user = act_user;
	}

	public Eventtype getEventtypeBean() {
		return this.eventtypeBean;
	}

	public void setEventtypeBean(Eventtype eventtypeBean) {
		this.eventtypeBean = eventtypeBean;
	}

	public User getRefUser() {
		return this.ref_user;
	}

	public void setRefUser(User userBean) {
		this.ref_user = userBean;
	}

}