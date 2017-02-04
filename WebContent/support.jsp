<%@ page contentType="text/html;charset=utf8"%>

<head>
<title>exception</title>
</head>

<body>
	<h1>Support Friendly Error Page</h1>

	<p>
		<b>Page:</b> <span>${url}</span>
	</p>

	<p id='created'>
		<b>Occurred:</b> <span>${timestamp}</span>
	</p>

	<p>
		<b>Response Status:</b> <span>${status}</span> <span>${error}</span>
	</p>

	<p>Application has encountered an error. Please contact support
		on...</p>

	<p>Support may ask you to right click to view page source.</p>

	<!--
      // Hidden Exception Details  - this is not a recommendation, but here is
      // how you hide an exception in the page using Thymeleaf
      -->
	<div>&lt;!--</div>
	<div>Failed URL: ${url}</div>
	<div>Exception: ${exception.message}</div>
	<ul>
		<%
			Exception excp = (Exception) request.getAttribute("exception");
			for (StackTraceElement ste : excp.getStackTrace()) {
		%>
		<li><%=ste%></li>
		<%
			}
		%>
	</ul>
	<div>--&gt;</div>



</body>
</html>
