package com.skrf.backend.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skrf.backend.model.Event;
import com.skrf.backend.model.Eventrule;
import com.skrf.backend.model.EventrulePK;
import com.skrf.backend.model.Eventtype;
import com.skrf.backend.model.Objects;
import com.skrf.backend.model.Objtype;
import com.skrf.backend.model.Participant;
import com.skrf.backend.model.Term;
import com.skrf.backend.model.User;

//We have independent tables: Users, Objtype - these tables can be filled automatically
//dependent tables: Object (Event, Participants,Terms) and Eventtype (Eventrules, Users) - additional logic is required 
public class read_json {

	private String file_name;
	private byte[] file_data;
	private JsonFactory JSONfactory;
	private ObjectMapper JSONMapper;
	
	private void read_file() throws IOException {
		file_data = Files.readAllBytes(Paths.get(file_name));
	}
	
	public read_json() { 	
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

		this.JSONfactory = new JsonFactory();
		this.JSONMapper = new ObjectMapper(this.JSONfactory); 
		this.JSONMapper.setDateFormat(df);
	}
	
	public List<User> get_users(String path_users) throws IOException {
		
		this.file_name = path_users;
		this.read_file();

		User[] jsonUsers = null;
		
		try {
			jsonUsers = this.JSONMapper.readValue(file_data, User[].class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Arrays.asList(jsonUsers);
	}
	
	public List<Objtype> get_objtypes(String path_objtype) throws IOException {
		
		this.file_name = path_objtype;
		this.read_file();
		
		Objtype[] Objtypes = null;
		
		try {
			Objtypes = this.JSONMapper.readValue(file_data, Objtype[].class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Arrays.asList(Objtypes);
	}
	
	public List<Eventtype> get_eventtypes(String path_eventtype) throws IOException {
		User usr_obj = null;
		
		this.file_name = path_eventtype;
		this.read_file();

		Eventtype[] Eventtypes = null;
		
		try {
			Eventtypes = this.JSONMapper.readValue(file_data, Eventtype[].class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Eventtype> LEventtypes = Arrays.asList(Eventtypes);
		EventrulePK er_id = null;
		for(Eventtype et:LEventtypes) {
			for(Eventrule er:et.getEventrules()) {
				er_id = er.getId();
				if (er_id == null) {
					er_id = new EventrulePK();
				}
				er_id.setEventtype(er.getEventtype());
				er_id.setRulesnmb(er.getRulesnmb());
				if(er.getAct_user() != null) {
					usr_obj = new User();
					usr_obj.setId(er.getAct_user());
					er.setRefUser(usr_obj);
				}
				er.setId(er_id);
			}
		}
		return Arrays.asList(Eventtypes);
	}
	
	public List<Objects> get_objects(String path_objects) throws IOException {
		Eventtype evntype = null;
		User usr_obj = null;
		Objtype objtype = null;
		
		this.file_name = path_objects;
		this.read_file();

		Objects[] lv_objects = null;
		
		try {
			lv_objects = this.JSONMapper.readValue(file_data, Objects[].class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Objects> ObjectsList = Arrays.asList(lv_objects);
		for(Objects obj:ObjectsList) {
			usr_obj = new User();
			usr_obj.setId(obj.getHandler());
			
			objtype = new Objtype();
			objtype.setObjtype(obj.getObjtype());
			
			obj.setRef_user(usr_obj);
			obj.setRef_Objtype(objtype);
			//Events
			for(Event evn:obj.getEvents()) {
				evn.setRef_object(obj);
				evntype = new Eventtype();
				evntype.setEventtype(evn.getEventtype());
				evn.setRef_eventtype(evntype);
			}
			//Terms
			for(Term trm:obj.getTerms()) {
				trm.setRef_objid(obj);
			}
			//participants
			for(Participant prt:obj.getParticipants()) {
				prt.setRef_objid(obj);
			}
		}
		return Arrays.asList(lv_objects);
	}
}
