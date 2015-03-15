<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.payment_cracker.api.dao.middle_level.middle_entities.UserEntity" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Natalie
  Date: 22.02.2015
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User's Data</title>
    <style>
        <%@ include file="style_sheet.css"%>
    </style>
</head>
<body>
<%
    PrintWriter writer = response.getWriter();
    writer.println("<form id = \"account_info\" method=\"post\" action = \"/controller\">\n" +
            "  <table class=\"form_holder1\" cellspacing=\"1\">\n" +
            "    <thead>\n" +
            "    <tr>\n" +
            "      <td colspan=\"2\" align=\"center\">User's Data</td>\n" +
            "    </tr>\n" +
            "    </thead>\n" +
            "    <tbody>\n" +
            "    <tr>\n" +
            "        <td>\n" +
            "    <table>\n" +
/*            "    <tr>\n" +
            "        <td>\n" +
            "        <th align=\"center\">Data List</th>\n" +
            "        </td>\n" +
            "     </tr>\n" +*/
            "        <tr>\n" +
            "            <td align=\"center\">User ID</td>\n" +
            "            <td align=\"center\">Login</td>\n" +
            "            <td align=\"center\">FIO</td>\n" +
            "            <td align=\"center\">Phone Number</td>\n" +
            "            <td align=\"center\">Email</td>\n" +
            "            <td align=\"center\">Reg. Date</td>\n" +
            "            <td align=\"center\">Ban</td>\n" +
            "            <td align=\"center\">Active</td>\n" +
            "            <td align=\"center\">Role</td>\n" +
            "        </tr>");
    if (request.getAttribute("info") != null) {

        UserEntity userEntity = (UserEntity) request.getAttribute("info");

        writer.println("<tr>");
        writer.println(" <td align=\"center\">");
        writer.println(userEntity.getId());
        writer.println("</td>");

        writer.println(" <td align=\"center\">");
        writer.println(userEntity.getLogin());
        writer.println("</td>");

        writer.println(" <td align=\"center\">");
        writer.println(userEntity.getFIO());
        writer.println("</td>");

        writer.println(" <td align=\"center\">");
        writer.println(userEntity.getPhoneNumber());
        writer.println("</td>");

        writer.println(" <td align=\"center\">");
        writer.println(userEntity.getEmail());
        writer.println("</td>");

        writer.println(" <td align=\"center\">");
        writer.println(userEntity.getRegDate());
        writer.println("</td>");

        writer.println(" <td align=\"center\">");
        writer.println(userEntity.isBan());
        writer.println("</td>");

        writer.println(" <td align=\"center\">");
        writer.println(userEntity.isActive());
        writer.println("</td>");

        writer.println(" <td align=\"center\">");
        writer.println(userEntity.isAdministrator());
        writer.println("</td>");
        writer.println("</tr>");
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