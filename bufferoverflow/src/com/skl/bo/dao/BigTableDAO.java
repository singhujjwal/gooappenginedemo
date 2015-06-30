/**
 * @author Nirmallya Mukherjee
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
 * 
 */

package com.skl.bo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.cmd.Query;


public class BigTableDAO<T> {

	private static final Logger logger = Logger.getLogger(BigTableDAO.class.getName());
	private Class<T> userClassName;
	
	
	public BigTableDAO(Class<T> userClassName){
		this.userClassName = userClassName;
	}
	
	
	//Generic save asynchronous
	public boolean saveAsynch(T entity){
		boolean success = true;
		try{
			OfyDaoService.getOfy().save().entity(entity);	
		} catch(Exception e) {
			success = false;
			logger.severe("Exception occured while saving entity asynchronously " + e.toString());
		}
		return success;
	}


	//Generic save synchronous
	public boolean save(T entity){
		boolean success = true;
		try{
			OfyDaoService.getOfy().save().entity(entity).now();
		} catch(Exception e) {
			success = false;
			logger.severe("Exception occured while saving entity synchronously " + e.toString());
		}
		return success;
	}



	public Object getById(Long id) {
		return OfyDaoService.getOfy().load().type(userClassName).id(id).now();
	}
	
	
	
	public Object get(String attribute, Object value) {
		Objectify ofy = OfyDaoService.getOfy();
		Object entity = null;
		try{
			Query<T> query = ofy.load().type(userClassName);
			query = query.filter(attribute + " = ", value);
			List<T> result = asList(query.iterable());
			if(result!=null && result.size()>0) {
				entity = result.get(0);
			}
		} catch(Exception e) {
			logger.severe("Exception occured while fetching the entity with attribute " + attribute + " value = " + value + "  Ex->" + e.toString());
		} finally {
			ofy.clear();
		}
		return entity;
	}
	

	

	//These are overloaded methods. Limit is a max of 100; default is 10
	public List<T> getList(String attribute, Object value) {
		return getList(attribute, value, null, 0, 10);
	}
	
	public List<T> getList(String attribute, Object value, int limit, int offset) {
		return getList(attribute, value, null, limit, offset);
	}
	
	public List<T> getList(String attribute, Object value, String orderBy) {
		return getList(attribute, value, orderBy, 0, 10);
	}
	
	public List<T> getList(String orderBy, int offset, int limit) {
		return getList(null, null, orderBy, offset, limit);
	}
	
	public List<T> getList(String attribute, Object value, String orderBy, int offset, int limit) {
		Objectify ofy = OfyDaoService.getOfy();
		List<T> result = null;
		try{
			Query<T> query = ofy.load().type(userClassName);
			if(attribute !=null) {
				query = query.filter(attribute + " = ", value);
			}
			if(orderBy!=null) {
				query = query.order(orderBy);
			}
			if(offset>=0) {
				query = query.offset(offset);
			}
			if(limit>0) {
				if(limit>100) {
					logger.info("Limit for the query is more than 100, will use 100 as upper limit");
					limit = 100;
				}
				query = query.limit(limit);
			}
			result = asList(query.iterable());
		} catch(Exception e) {
			logger.severe("Exception occured while fetching entity list with attribute " + attribute + " value = " + value + "  Ex->" + e.toString());
		} finally {
			ofy.clear();
		}
		return result;
	}

	
	//Internal method so that this class and all subclasses get access to it
	protected List<T> asList(Iterable<T> iterable){
		ArrayList<T> list = new ArrayList<T>();
		for(T t: iterable){
			list.add(t);
		}
		return list;
	}
}

