<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.payment_cracker.api.dao.middle_level.middle_entities.CreditCardEntity" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Natalie
  Date: 21.02.2015
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Page</title>
    <style>
        <%@ include file="style_sheet.css"%>
    </style>
</head>
<body>
<%
    PrintWriter writer = response.getWriter();
    writer.println("<form id = \"admin_page\" method=\"post\" action = \"/controller\">\n" +
            "  <table class=\"form_holder1\" cellspacing=\"1\">\n" +
            "    <thead>\n" +
            "    <tr>\n" +
            "      <td colspan=\"2\" align=\"center\">Users' Accounts Management</td>\n" +
            "    </tr>\n" +
            "    </thead>\n" +
            "    <tbody>\n" +
            "<tr>\n" +
            "<td>User Id</td>\n" +
            "<td><input size=\"26\" type=\"text\" name=\"text_userid\" /></td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td colspan=\"2\" align=\"center\"><hr /></td>\n" +
            "</tr>" +
            "    <tr>\n" +
            "      <td colspan=\"2\" align=\"center\">\n" +
            "          <input type=\"submit\" value=\"Set Ban\" name=\"adminpage_setban\" />\n" +
            "      </td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "      <td colspan=\"2\" align=\"center\">\n" +
            "          <input type=\"submit\" value=\"Get Account Info\" name=\"adminpage_getaccountinfo\" />\n" +
            "      </td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "      <td colspan=\"2\" align=\"center\">\n" +
            "          <input type=\"submit\" value=\"Get Account Purses\" name=\"adminpage_getaccountspurses\" />\n" +
            "      </td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "      <td colspan=\"2\" align=\"center\">\n" +
            "          <input type=\"submit\" value=\"Get Accounts Info\" name=\"adminpage_getaccountsinfo\" />\n" +
            "      </td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"2\" align=\"center\"><hr /></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "      <td colspan=\"2\" align=\"center\">\n" +
            "          <input type=\"submit\" value=\"Show System Balance\" name=\"adminpage_showsystembalance\" />\n" +
            "      </td>\n" +
            "    </tr>\n" +            
            "    <tr>\n" +
            "      <td colspan=\"2\" align=\"center\">\n" +
            "          <input type=\"submit\" value=\"Create Admin Account\" name=\"adminpage_registeradmin\" />\n" +
            "      </td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"2\" align=\"center\"><hr /></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"2\" align=\"center\">\n" +
            "            <input type=\"submit\" value=\"Log out\" name=\"logouttransfer\" />\n" +
            "        </td>\n" +
            "    </tr>\n" +
            "    </tbody>\n" +
            "  </table>" +
            "</form>");

    if (request.getAttribute("result") != null) {
        String result = (String) request.getAttribute("result");
        writer.println(result);
    }
    if (request.getAttribute("system_balance") != null) {
        List<String> result = (List<String>) request.getAttribute("system_balance");
        for (String s : result) {
            writer.println(s);
            writer.println("<br/>");
        }
    }
%>
</body>
</html>



