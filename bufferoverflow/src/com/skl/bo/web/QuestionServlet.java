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
import com.skl.bo.entity.UserDO;

@SuppressWarnings("serial")
public class QuestionServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(QuestionServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doPost(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		QuestionDO newQuestion = null;
		BigTableDAO<QuestionDO> dao = new BigTableDAO<QuestionDO>(QuestionDO.class);
		String questionStr = req.getParameter(QuestionDO.FormAttributes.question.toString());
		
		if(StringUtils.isNotEmpty(questionStr)) {
			long uid = (long) req.getSession(false).getAttribute(UserDO.DisplayAttributes.userId.toString());
			String userName = (String) req.getSession(false).getAttribute(UserDO.DisplayAttributes.firstName.toString());
			newQuestion = new QuestionDO(uid, userName, questionStr);
			dao.save(newQuestion);
		}
		
		//Fetch the questions now
		List<QuestionDO> questionList = dao.getList("-" + QuestionDO.FetchAttributes.createdOn.toString(), 0, 50);
		//Handle eventual consistency
		boolean found = false;
		if(questionList!=null && newQuestion!=null && questionList.size()>0) {
			for(QuestionDO question : questionList) {
				if(question.getId() == newQuestion.getId()) {
					found = true;
				}
			}
		}
		if(!found && newQuestion!=null) {
			if(questionList==null) {
				questionList = new ArrayList<QuestionDO>();
			}
			questionList.add(0, newQuestion);
			logger.info("Eventual consistency handled ...");
		}
		req.setAttribute(QuestionDO.FormAttributes.questionList.toString(), questionList);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(LoginServlet.QUESTION_PAGE);
		try {
			dispatcher.forward(req,resp);
		} catch (ServletException e) {
			logger.severe("Issue in forwarding to servlet " + e.toString());
			e.printStackTrace();
		}

	}
}
