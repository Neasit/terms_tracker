package com.skrf.backend.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the "USERS" database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	@Column(name="name")
	private String name;

	@Column(name="position")
	private String position;

	@Column(name="rank")
	private String rank;

	//bi-directional many-to-one association to Eventrule
	@OneToMany(mappedBy="ref_user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Eventrule> eventrules;

	//bi-directional many-to-one association to Object
	@OneToMany(mappedBy="ref_user", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Objects> objects;

	public User() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getRank() {
		return this.rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public List<Eventrule> getEventrules() {
		return this.eventrules;
	}

	public void setEventrules(List<Eventrule> eventrules) {
		this.eventrules = eventrules;
	}

	public Eventrule addEventrule(Eventrule eventrule) {
		getEventrules().add(eventrule);
		eventrule.setRefUser(this);

		return eventrule;
	}

	public Eventrule removeEventrule(Eventrule eventrule) {
		getEventrules().remove(eventrule);
		eventrule.setRefUser(null);

		return eventrule;
	}

	public List<Objects> getObjects() {
		return this.objects;
	}

	public void setObjects(List<Objects> objects) {
		this.objects = objects;
	}

	public Objects addObject(Objects object) {
		getObjects().add(object);
		object.setRef_user(this);

		return object;
	}

	public Objects removeObject(Objects object) {
		getObjects().remove(object);
		object.setRef_user(null);

		return object;
	}

}