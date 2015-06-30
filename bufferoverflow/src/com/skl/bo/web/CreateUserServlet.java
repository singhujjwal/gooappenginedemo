/**
 * @author Nirmallya Mukherjee
 * 
 * Provides a mechanism to create sample users
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
 * 
 */

package com.skl.bo.web;

import java.io.IOException;

import javax.servlet.http.*;

import java.util.logging.Logger;

import com.skl.bo.dao.BigTableDAO;
import com.skl.bo.entity.UserDO;

@SuppressWarnings("serial")
public class CreateUserServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(CreateUserServlet.class.getName());
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		BigTableDAO<UserDO> dao = new BigTableDAO<UserDO>(UserDO.class);
		
		for(int i=1; i<=30; i++) {
			UserDO user = new UserDO("u"+i, "gae", "User" + i, "Last");
			dao.save(user);
		}
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Users created ...");
		logger.info("Users created ...");
	}
}
