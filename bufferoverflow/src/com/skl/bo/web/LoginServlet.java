/**
 * @author Nirmallya Mukherjee
 * 
 * Servlet that provides login service
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
 * 
 */


package com.skl.bo.web;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.skl.bo.dao.BigTableDAO;
import com.skl.bo.entity.QuestionDO;
import com.skl.bo.entity.UserDO;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	
	private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());
	
	public static final String LOGIN_PAGE = "/index.jsp";
	public static final String QUESTION_PAGE = "/question.jsp";
	public static final String RESPONSE_PAGE = "/response.jsp";
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doService(req, resp);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doService(req, resp);
	}
	
	
	private void doService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String forwardJsp = "";
		String loginId = req.getParameter(UserDO.FetchAttributes.loginId.toString());
		String password = req.getParameter(UserDO.FetchAttributes.password.toString());
		
		BigTableDAO<UserDO> dao = new BigTableDAO<UserDO>(UserDO.class);
		UserDO user = (UserDO) dao.get(UserDO.FetchAttributes.loginId.toString(), loginId);
		if(user!=null && StringUtils.equals(password, user.getPassword())) {
			HttpSession session = req.getSession(true);
			session.setAttribute(UserDO.DisplayAttributes.userId.toString(), user.getId());
			session.setAttribute(UserDO.DisplayAttributes.firstName.toString(), user.getFirstName());
			session.setAttribute(UserDO.DisplayAttributes.lastName.toString(), user.getLastName());
			
			//Get the questions
			BigTableDAO<QuestionDO> daoPost = new BigTableDAO<QuestionDO>(QuestionDO.class);
			List<QuestionDO> questionList = daoPost.getList("-" + QuestionDO.FetchAttributes.createdOn.toString(), 0, 50);
			req.setAttribute(QuestionDO.FormAttributes.questionList.toString(), questionList);
			
			forwardJsp = QUESTION_PAGE;
			logger.info("Login was good.");
		} else {
			forwardJsp = LOGIN_PAGE;
			logger.info("Login was NOT good.");
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(forwardJsp);
		try {
			dispatcher.forward(req,resp);
		} catch (ServletException e) {
			logger.severe("Issue in forwarding to servlet " + e.toString());
			e.printStackTrace();
		}
	}
	
}
