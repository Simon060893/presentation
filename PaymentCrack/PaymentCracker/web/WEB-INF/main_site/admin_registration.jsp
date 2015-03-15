<%--
  Created by IntelliJ IDEA.
  User: Natalie
  Date: 23.02.2015
  Time: 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register Admin</title>
    <style>
        <%@ include file="style_sheet.css"%>
    </style>
</head>
<body>
<form id = "registeradmin" method="post" action = "/controller">
    <table class="form_holder1" cellspacing="1">
        <thead>
        <tr>
            <td colspan="2" align="center">Register Admin</td>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>Login (6 to 12 symbols, starting with letter)</td>
            <td><input size="26" type="text" name="text_login" /></td>
        </tr>
        <tr>
            <td>Password (8 to 12 symbols)</td>
            <td><input size="26" type="password" name="text_password" /></td>
        </tr>
        <tr>
            <td>Full Name</td>
            <td><input size="26" type="text" name="text_name" /></td>
        </tr>
        <tr>
            <td>Phone Number</td>
            <td><input size="26" type="text" name="text_phonenumber" /></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><input size="26" type="text" name="text_email" /></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><hr /></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="Register" name="registeradmin_confirm"/>
                <input type="reset" value="Cancel" name="registeradmin_cancel"/>
            </td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>