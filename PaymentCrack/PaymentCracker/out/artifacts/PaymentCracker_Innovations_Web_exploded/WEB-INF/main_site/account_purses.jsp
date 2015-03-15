<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Natalie
  Date: 22.02.2015
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User's Purses</title>
    <style>
        <%@ include file="style_sheet.css"%>
    </style>
</head>
<body>
<%
    PrintWriter writer = response.getWriter();
    writer.println("<form id = \"account_purses\" method=\"post\" action = \"/controller\">\n" +
            "  <table class=\"form_holder1\" cellspacing=\"1\">\n" +
            "    <thead>\n" +
            "    <tr>\n" +
            "      <td colspan=\"2\" align=\"center\">User's Purses</td>\n" +
            "    </tr>\n" +
            "    </thead>\n" +
            "    <tbody>\n" +
            "    <tr>\n" +
            "        <td>\n" +
            "    <table>\n" +
            "        <tr>\n" +
            "            <td align=\"center\">Purse ID</td>\n" +
            "            <td align=\"center\">Currency</td>\n" +
            "            <td align=\"center\">Balance</td>\n" +
            "            <td align=\"center\">Login</td>\n" +
            "            <td align=\"center\">FIO</td>\n" +
            "        </tr>");
    if ( (List<List<String>>) request.getAttribute("pursesInfo") != null) {

        List<List<String>> accountPursesList = (List<List<String>>) request.getAttribute("pursesInfo");

        for (List<String> apl : accountPursesList ) {
            writer.println("<tr>");
            for(String ap : apl) {
                writer.println(" <td align=\"center\">");
                writer.println(ap);
                writer.println("</td>");
            }
            writer.println("</tr>");
        }
    }
    writer.println("" +
            "    </table>\n" +
            "        </td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"2\" align=\"center\">\n" +
            "            <hr/>\n" +
            "        </td>\n" +
            "    </tr>\n" +
            "   <tr>\n" +
            "      <td colspan=\"2\" align=\"center\">\n" +
            "          <input type=\"submit\" value=\"Back to admin page\" name=\"transfertoadminpage\" />\n" +
            "      </td>\n" +
            "    </tr>" +
            "    <tr>\n" +
            "        <td colspan=\"2\" align=\"center\">\n" +
            "            <hr/>\n" +
            "        </td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"2\" align=\"center\">\n" +
            "            <input type=\"submit\" value=\"Log out\" name=\"logouttransfer\" />\n" +
            "        </td>\n" +
            "    </tr>\n" +
            "    </tbody>\n" +
            "  </table>" +
            "</form>");
%>
</body>
</html>