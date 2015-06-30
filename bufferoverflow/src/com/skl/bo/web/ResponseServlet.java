/**
 * @author Nirmallya Mukherjee
 * 
 * Post service servlet
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
 * 
 */

package com.skl.bo.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.skl.bo.dao.BigTableDAO;
import com.skl.bo.entity.QuestionDO;
import com.skl.bo.entity.ResponseDO;
import com.skl.bo.entity.UserDO;

@SuppressWarnings("serial")
public class ResponseServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(ResponseServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doPost(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String questionIdStr = req.getParameter(ResponseDO.FetchAttributes.questionId.toString());
		String responseStr = req.getParameter(ResponseDO.FormAttributes.response.toString());
		long questionId = Long.parseLong(questionIdStr);
		ResponseDO newResponse = null;

		//Populate the question to show on the screen
		BigTableDAO<QuestionDO> dao1 = new BigTableDAO<QuestionDO>(QuestionDO.class);
		QuestionDO question = (QuestionDO) dao1.getById(questionId);
		req.setAttribute(QuestionDO.FormAttributes.question.toString(), question);
		
		//Save the response now
		BigTableDAO<ResponseDO> dao2 = new BigTableDAO<ResponseDO>(ResponseDO.class);
		if(StringUtils.isNotEmpty(responseStr)) {
			long uid = (long) req.getSession(false).getAttribute(UserDO.DisplayAttributes.userId.toString());
			String userName = (String) req.getSession(false).getAttribute(UserDO.DisplayAttributes.firstName.toString());
			newResponse = new ResponseDO(questionId, uid, userName, responseStr);
			dao2.save(newResponse);
		}
		
		//Now get the responses to the question to show on the screen
		List<ResponseDO> responseList = dao2.getList(ResponseDO.FetchAttributes.questionId.toString(), questionId,
													 "-" + ResponseDO.FetchAttributes.createdOn.toString(), 0, 50);
		//Handle eventual consistency
		boolean found = false;
		if(responseList!=null && newResponse!=null && responseList.size()>0) {
			for(ResponseDO response : responseList) {
				if(response.getId() == newResponse.getId()) {
					found = true;
				}
			}
		}
		if(!found && newResponse!=null) {
			if(responseList==null) {
				responseList = new ArrayList<ResponseDO>();
			}
			responseList.add(0, newResponse);
			logger.info("Eventual consistency handled ...");
		}
		req.setAttribute(ResponseDO.FormAttributes.responseList.toString(), responseList);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(LoginServlet.RESPONSE_PAGE);
		try {
			dispatcher.forward(req,resp);
		} catch (ServletException e) {
			logger.severe("Issue in forwarding to servlet " + e.toString());
			e.printStackTrace();
		}

	}
}
