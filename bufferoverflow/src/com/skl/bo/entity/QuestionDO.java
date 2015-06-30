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
public class QuestionDO {

	//This enum should be used by different classes to fetch this DO based on conditions
	public enum FetchAttributes {
		createdOn
	}

	public enum FormAttributes {
		question, questionList
	}

	@Id	private Long id;
	private long userId;
	private String userName;
	private Text question;
	@Index private Date createdOn;

	
	private QuestionDO(){
		super();
		createdOn = new Date();
	}
	
	public QuestionDO(long userId, String userName, String question){
		this();
		this.userId = userId;
		this.setUserName(userName);
		this.setQuestion(question);
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
	
	public String getQuestion() {
		return (question!=null) ? question.getValue() : "";
	}
	
	public void setQuestion(String question) {
		this.question = new Text(question);
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

}
