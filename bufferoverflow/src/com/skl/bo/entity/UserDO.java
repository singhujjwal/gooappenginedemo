/**
 * @author Nirmallya Mukherjee
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
 * 
 */

package com.skl.bo.entity;

import java.util.Date;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


@Entity
@Cache(expirationSeconds=86400)
public class UserDO {

	//This enum should be used by different classes to fetch this DO based on conditions
	public enum FetchAttributes {
		loginId, password
	}

	public enum DisplayAttributes {
		userId, firstName, lastName
	}

	@Id private Long id;			//This will be the userId in other DO
	@Index 	private String loginId;	//Will be used for login purpose
	@Index private Date createdOn;	//In case we want to get a list of users created in the last 'n' minutes/hours/days/months/querters/yeras
	private String password;
	private String firstName;
	private String lastName;
	private Date lastLoginDT;
	private Date modifiedOn;	
	

	
	private UserDO() {
		super();
		this.createdOn = new Date();
	}

	public UserDO(String loginId, String password, String firstName, String lastName) {
		this();
		this.loginId = loginId;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getLoginId() {
		return loginId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getLastLoginDT() {
		return lastLoginDT;
	}

	public void setLastLoginDT(Date lastLoginDT) {
		this.lastLoginDT = lastLoginDT;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
