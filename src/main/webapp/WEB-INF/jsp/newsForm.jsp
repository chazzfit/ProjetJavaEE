<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<style>
input[type=text], select {
    width: 100%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

input[type=submit] {
    width: 100%;
    background-color: #4CAF50;
    color: white;
    padding: 14px 20px;
    margin: 8px 0;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

input[type=submit]:hover {
    background-color: #45a049;
}

.form {
    border-radius: 5px;
    background-color: #f2f2f2;
    padding: 20px;
}
.error {
	color: red;
}
</style>
<body>

<div class="form">
  <form:form  method="POST" modelAttribute="newsValidator">
    <label for="title">Title</label>
    <form:input path="title"/>
    <form:errors path="title" cssClass="error"/>

    <label for="description">Description</label>
    <form:input path="description"/>
    <form:errors path="description" cssClass="error"/>
    

	<label for="image">Image URL</label>
    <form:input path="image"/>
    
    <form:checkbox path="active"/> Active<br>
    <br>
    
    Expiration date:
    <form:input type="date" path="date"/>
    
    <input type="submit" value="Create news">
  </form:form>
</div>

</body>
</html>
