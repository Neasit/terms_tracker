package com.skrf.backend.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.*;


/**
 * The persistent class for the "OBJECT" database table.
 * 
 */
@Entity
@Table(name="objects")
@NamedQuery(name="Objects.findAll", query="SELECT o FROM Objects o")
public class Objects implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	@Column(name="arch")
	private Boolean arch;

	@Temporal(TemporalType.DATE)
	@Column(name="crtdate")
	private Date crtdate;

	@Column(name="descr")
	private String descr;

	@Column(name="guard")
	private String guard;
	
	@Transient
	private Integer handler;  //Only JSON Model
	
	@ManyToOne
	@JoinColumn(name="handler")
	@JsonIgnore
	private User ref_user;

	@Column(name="important")
	private Boolean important;

	@Column(name="objnumber")
	private String objnumber;
	
	@Transient
	private String objtype; 	//Only JSON Model
	
	@ManyToOne
	@JoinColumn(name="objtype" )
	@JsonIgnore
	private Objtype ref_objtype;

	@Column(name="reason")
	private String reason;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="ref_obj", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Event> events;

	//bi-directional many-to-one association to Participant
	@OneToMany(mappedBy="ref_objid", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Participant> participants;

	//bi-directional many-to-one association to Term
	@OneToMany(mappedBy="ref_objid", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Term> terms;

	public Objects() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getArch() {
		return this.arch;
	}

	public void setArch(Boolean arch) {
		this.arch = arch;
	}

	public Date getCrtdate() {
		return this.crtdate;
	}

	public void setCrtdate(Date crtdate) {
		this.crtdate = crtdate;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}


	public String getGuard() {
		return this.guard;
	}

	public void setGuard(String guard) {
		this.guard = guard;
	}

	public Integer getHandler() {
		return this.handler;
	}

	public void setHandler(Integer handler) {
		this.handler = handler;
	}

	public Boolean getImportant() {
		return this.important;
	}

	public void setImportant(Boolean important) {
		this.important = important;
	}

	public String getObjnumber() {
		return this.objnumber;
	}

	public void setObjnumber(String objnumber) {
		this.objnumber = objnumber;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Event addEvent(Event event) {
		getEvents().add(event);
		event.setRef_object(this);

		return event;
	}

	public Event removeEvent(Event event) {
		getEvents().remove(event);
		event.setRef_object(null);

		return event;
	}

	public Objtype getRef_Objtype() {
		return this.ref_objtype;
	}

	public void setRef_Objtype(Objtype objtypeBean) {
		this.ref_objtype = objtypeBean;
	}

	public User getRef_user() {
		return this.ref_user;
	}

	public void setRef_user(User user) {
		this.ref_user = user;
	}

	public List<Participant> getParticipants() {
		return this.participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

	public Participant addParticipant(Participant participant) {
		getParticipants().add(participant);
		participant.setRef_objid(this);

		return participant;
	}

	public Participant removeParticipant(Participant participant) {
		getParticipants().remove(participant);
		participant.setRef_objid(null);

		return participant;
	}

	public List<Term> getTerms() {
		return this.terms;
	}

	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}

	public Term addTerm(Term term) {
		getTerms().add(term);
		term.setRef_objid(this);

		return term;
	}

	public Term removeTerm(Term term) {
		getTerms().remove(term);
		term.setRef_objid(null);

		return term;
	}
	public String getObjtype() {
		return this.objtype;
	}
}