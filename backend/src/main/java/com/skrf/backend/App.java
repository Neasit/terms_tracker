package com.skrf.backend;

import com.skrf.backend.generator.read_json;
import com.skrf.backend.model.*;

import java.util.Arrays;
import java.util.List;

import javax.persistence.*;
/**
 * Hello world!
 *
 */

public class App 
{
    public static void main( String[] args )
    {
    	
    	try {
    		
    		read_json CTest = new read_json();
    		
    		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("com.skrf.backend");
    		EntityManager em = entityManagerFactory.createEntityManager();
    		
    		//First step Users and Object types
    		List<Objtype> MyObjtypes = CTest.get_objtypes("C:\\Users\\adanilin\\Documents\\T-systems\\Teaching\\SKRF\\Examples\\objtype.json");
    		List<User> MyUsers = CTest.get_users("C:\\Users\\adanilin\\Documents\\T-systems\\Teaching\\SKRF\\Examples\\Users.json");
    		em.getTransaction().begin();
    		
    		for(User usr:MyUsers) {
    			em.persist(usr);
    		}
    		for(Objtype objt:MyObjtypes) {
    			em.persist(objt);
    		}
    		em.getTransaction().commit();
    		em.clear();
    		
    		//Second step Event types and rules
    		List<Eventtype> MyEventtypes = CTest.get_eventtypes("C:\\Users\\adanilin\\Documents\\T-systems\\Teaching\\SKRF\\Examples\\eventtypes.json");
    		em.getTransaction().begin();
    		
    		for(Eventtype evnt:MyEventtypes) {
    			em.persist(evnt);
    		}
    		em.getTransaction().commit();
    		em.clear();
    		
    		//Third step Objects with participants, terms and events
    		List<Objects> LObjects = CTest.get_objects("C:\\Users\\adanilin\\Documents\\T-systems\\Teaching\\SKRF\\Examples\\object.json");
    		
    		em.getTransaction().begin();
    		
    		for(Objects obj:LObjects) {
    			em.persist(obj);
    		}
            em.getTransaction().commit();
            
            em.close();
		} catch (Exception e) {
			// TODO: handle exception
			
		}
    	    	       
    }
}
