<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.payment_cracker.api.dao.middle_level.middle_entities.CurrencyEntity" %>
<%@ page import="com.payment_cracker.api.dao.middle_level.middle_entities.PurseEntity" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.io.PrintWriter" %>
<%--
  Created by IntelliJ IDEA.
  User: Natalie
  Date: 19.01.2015
  Time: 2:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Operations</title>
    <style>
        <%@ include file="style_sheet.css"%>
    </style>
</head>
<body>
<%
    PrintWriter writer = response.getWriter();
    writer.println("<form id = \"operations\" method=\"post\" action = \"/controller\">\n" +
            "  <table class=\"form_holder1\" cellspacing=\"1\">\n" +
            "    <thead>\n" +
            "    <tr>\n" +
            "      <td colspan=\"2\" align=\"center\">Operations</td>\n" +
            "    </tr>\n" +
            "    </thead>\n" +
            "    <tbody>\n" +
            "    <tr>\n" +
            "      <td colspan=\"2\" align=\"center\">\n" +
            "          <input type=\"submit\" value=\"Add purse\" name=\"operations_addpurse\" />\n" +
            "      </td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "      <td colspan=\"2\" align=\"center\">\n" +
            "          <input type=\"submit\" value=\"Perform money transfer\" name=\"operations_performtransaction\" />\n" +
            "      </td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "      <td colspan=\"2\" align=\"center\">\n" +
            "          <input type=\"submit\" value=\"Add money to purse\" name=\"operations_addmoney\" />\n" +
            "      </td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "      <td colspan=\"2\" align=\"center\">\n" +
            "          <input type=\"submit\" value=\"Withdraw money from purse\" name=\"operations_withdrawmoney\" />\n" +
            "      </td>\n" +
            "    </tr>\n" +
            "      <td colspan=\"2\" align=\"center\">\n" +
            "          <input type=\"submit\" value=\"Show my actions list\" name=\"operations_showmyoperationslist\" />\n" +
            "      </td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"2\" align=\"center\"><hr /></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td>\n" +
            "    <table>\n" +
            "    <tr>\n" +
            "        <td>\n" +
            "        <th align=\"center\">My Purses List</th>\n" +
            "        </td>\n" +
            "     </tr>\n" +
            "        <tr>\n" +
            "            <td align=\"center\">Purse ID</td>\n" +
            "            <td align=\"center\">Currency</td>\n" +
            "            <td align=\"center\">Balance</td>\n" +
            "        </tr>");
    if ((HashMap<CurrencyEntity, PurseEntity>) request.getAttribute("pursesInfo") != null) {

        HashMap<CurrencyEntity, PurseEntity> currencyPurseHashMap = (HashMap<CurrencyEntity, PurseEntity>) request.getAttribute("pursesInfo");

        for (Map.Entry<CurrencyEntity, PurseEntity> mapEntry : currencyPurseHashMap.entrySet()) {

            writer.println("<tr>");
            writer.println(" <td align=\"center\">");
            writer.println(mapEntry.getValue().getId());
            writer.println("</td>");

            writer.println(" <td align=\"center\">");
            writer.println(mapEntry.getKey().getCurrencyLabel());
            writer.println("</td>");

            writer.println(" <td align=\"center\">");
            writer.println(mapEntry.getValue().getBalance());
            writer.println("</td>");
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
            "    <tr>\n" +
            "        <td colspan=\"2\" align=\"center\">\n" +
            "            <input type=\"submit\" value=\"Log out\" name=\"logouttransfer\" />\n" +
            "        </td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"2\" align=\"center\">\n" +
            "            <hr/>\n" +
            "        </td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td colspan=\"2\" align=\"center\">\n" +
            "            <input type=\"submit\" value=\"Delete Account\" name=\"deleteaccount\" />\n" +
            "        </td>\n" +
            "    </tr>\n" +
            "    </tbody>\n" +
            "  </table>" +
            "</form>");
%>
</body>
</html>
