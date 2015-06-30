<%@include file = "header.jspf" %>

<%
	QuestionDO question = (QuestionDO)request.getAttribute(QuestionDO.FormAttributes.question.toString());
%>

<b><%= question.getQuestion() %></b><br><br>

Post a response here
<form name="skl_form" class="form-inline" role="form" method="post" action="/post">
	<div class="form-group">
		<input type="text" size="100" name="<%=ResponseDO.FormAttributes.response.toString()%>" class="form-control">
		<input type="hidden" name="<%= ResponseDO.FetchAttributes.questionId.toString() %>" value="<%= question.getId() %>">
		<a href="#" id="run_button" class="btn btn-success" onclick="skl_form.submit();">Respond</a>
	</div>
</form>
<br>
<%
	List<ResponseDO> responseList = (List<ResponseDO>) request.getAttribute(ResponseDO.FormAttributes.responseList.toString());
	if(responseList!=null) {
		System.out.println("The responseList contains " + responseList.size() + " items");
		for(ResponseDO post : responseList) {
			System.out.println("The question is " + post.toString());
%>
			<%= post.getResponse() %> <b>- <%= post.getUserName() %></b><br><br>
<%
		}
	}
%>

<%@include file = "footer.jspf" %>
