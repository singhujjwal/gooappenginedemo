/**
 * @author Nirmallya Mukherjee
 * 
 * Servlet to be used for any startup activities
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
 * 
 */

package com.skl.bo.web;

import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

import com.skl.bo.dao.OfyDaoService;
import com.skl.bo.entity.ResponseDO;
import com.skl.bo.entity.QuestionDO;
import com.skl.bo.entity.UserDO;

@SuppressWarnings("serial")
public class BootstrapServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(BootstrapServlet.class.getName());
	
	static {
		OfyDaoService.register(UserDO.class);
		OfyDaoService.register(QuestionDO.class);
		OfyDaoService.register(ResponseDO.class);
		logger.info("DO classes are now registered ...");
	}
	
}
