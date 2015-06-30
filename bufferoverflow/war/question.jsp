<%@include file = "header.jspf" %>

Ask a question here
<form name="skl_form" class="form-inline" role="form" method="post" action="/question">
	<div class="form-group">
		<input type="text" size="100" name="<%=QuestionDO.FormAttributes.question.toString()%>" class="form-control">
		<a href="#" id="run_button" class="btn btn-success" onclick="skl_form.submit();"> Ask </a>
	</div>
</form>
<br>
<%
	List<QuestionDO> questionList = (List<QuestionDO>) request.getAttribute(QuestionDO.FormAttributes.questionList.toString());
	if(questionList!=null) {
		System.out.println("The questionList contains " + questionList.size() + " items");
		int i = 1;
		for(QuestionDO question : questionList) {
			System.out.println("The question is " + question.toString());
%>
			<%= i %>.&nbsp;<%= question.getQuestion() %> <b>- <%= question.getUserName() %></b> [<a href="/post?<%= ResponseDO.FetchAttributes.questionId.toString() %>=<%= question.getId() %>">Post/view response</a>]<br><br>
<%
			i++;
		}
	}
%>

<%@include file = "footer.jspf" %>
