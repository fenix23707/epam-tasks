<%@tag language="java" pageEncoding="UTF-8" %>

<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String" %>


<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <title>${title}</title>
</head>
<body style="background: blueviolet">
    <jsp:doBody/>
</body>
</html>