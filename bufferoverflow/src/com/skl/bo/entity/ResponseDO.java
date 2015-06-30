/**
 * @author Nirmallya Mukherjee
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk.
 *  
 */

package com.skl.bo.entity;

import java.util.Date;

import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
public class ResponseDO {

	//This enum should be used by different classes to fetch this DO based on conditions
	public enum FetchAttributes {
		questionId, createdOn
	}

	public enum FormAttributes {
		response, responseList
	}

	@Id	private Long id;
	@Index private long questionId;
	private long userId;
	private String userName;
	private Text response;
	@Index private Date createdOn;

	
	private ResponseDO(){
		super();
		createdOn = new Date();
	}
	
	public ResponseDO(long questionId, long userId, String userName, String response){
		this();
		this.userId = userId;
		this.setQuestionId(questionId);
		this.setUserName(userName);
		this.setResponse(response);
	}
	
	public Long getId() {
		return id;
	}

	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getResponse() {
		return (response!=null) ? response.getValue() : "";
	}
	
	public void setResponse(String response) {
		this.response = new Text(response);
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

}
