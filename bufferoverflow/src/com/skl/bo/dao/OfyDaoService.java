/**
 * @author Nirmallya Mukherjee
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk.
 *  
 */

package com.skl.bo.dao;

import java.util.logging.Logger;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;



public class OfyDaoService {

	private static final Logger logger = Logger.getLogger(OfyDaoService.class.getName());

    // This method should be used to explicitly register the class in the factory and should be used when objectifying new Entity
    public static <T> void register(Class<T> clazz){
    	try{
    		ObjectifyService.register(clazz);
    	}
    	catch(Exception e){
    		logger.warning("The class is already registered ->"+clazz);
    	}
    }

	public static Objectify getOfy() {
		return ObjectifyService.ofy();
    }

}
