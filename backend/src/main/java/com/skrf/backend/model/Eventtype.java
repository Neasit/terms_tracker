package com.skrf.backend.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the "EVENTTYPES" database table.
 * 
 */
@Entity
@Table(name="eventtypes")
@NamedQuery(name="Eventtype.findAll", query="SELECT e FROM Eventtype e")
public class Eventtype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="eventtype")
	private String eventtype;

	@Column(name="descr")
	private String descr;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="ref_eventtype", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Event> events;

	//bi-directional many-to-one association to Eventrule
	@OneToMany(mappedBy="eventtypeBean", cascade=CascadeType.ALL)
	private List<Eventrule> eventrules;

	public Eventtype() {
	}

	public String getEventtype() {
		return this.eventtype;
	}

	public void setEventtype(String eventtype) {
		this.eventtype = eventtype;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Event addEvent(Event event) {
		getEvents().add(event);
		event.setRef_eventtype(this);

		return event;
	}

	public Event removeEvent(Event event) {
		getEvents().remove(event);
		event.setRef_eventtype(null);

		return event;
	}

	public List<Eventrule> getEventrules() {
		return this.eventrules;
	}

	public void setEventrules(List<Eventrule> eventrules) {
		this.eventrules = eventrules;
	}

	public Eventrule addEventrule(Eventrule eventrule) {
		getEventrules().add(eventrule);
		eventrule.setEventtypeBean(this);

		return eventrule;
	}

	public Eventrule removeEventrule(Eventrule eventrule) {
		getEventrules().remove(eventrule);
		eventrule.setEventtypeBean(null);

		return eventrule;
	}

}