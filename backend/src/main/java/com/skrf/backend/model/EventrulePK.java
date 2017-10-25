package com.skrf.backend.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the "EVENTRULES" database table.
 * 
 */
@Embeddable
public class EventrulePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="eventtype")
	private String eventtype;

	@Column(name="rulesnmb")
	private Integer rulesnmb;

	public EventrulePK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EventrulePK)) {
			return false;
		}
		EventrulePK castOther = (EventrulePK)other;
		return 
			this.eventtype.equals(castOther.eventtype)
			&& this.rulesnmb.equals(castOther.rulesnmb);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.eventtype.hashCode();
		hash = hash * prime + this.rulesnmb.hashCode();
		
		return hash;
	}
}