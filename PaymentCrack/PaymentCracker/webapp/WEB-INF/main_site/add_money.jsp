<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.payment_cracker.api.dao.middle_level.middle_entities.CurrencyEntity" %>
<%@ page import="com.payment_cracker.api.dao.middle_level.middle_entities.PurseEntity" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%--
  Created by IntelliJ IDEA.
  User: Natalie
  Date: 19.01.2015
  Time: 2:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>AddMoney</title>
  <style>
    <%@ include file="style_sheet.css"%>
  </style>
</head>
<body>
<%
PrintWriter writer = response.getWriter();
writer.println("<form id = \"addmoney\" method=\"post\" action = \"/controller\">\n" +
        "  <table class=\"form_holder1\" cellspacing=\"1\">\n" +
        "    <thead>\n" +
        "    <tr>\n" +
        "      <td colspan=\"2\" align=\"center\">Add Money To Purse</td>\n" +
        "    </tr>\n" +
        "    </thead>\n" +
        "    <tbody>\n" +
        "    <tr>\n" +
        "      <td>Purse ID</td>\n" +
        "      <td>\n" +
        "          <select name=\"list_purses\">");

    if ((HashMap<CurrencyEntity, PurseEntity>) request.getAttribute("purseslist_result") != null) {

        HashMap<CurrencyEntity, PurseEntity> currencyPurseHashMap = (HashMap<CurrencyEntity, PurseEntity>) request.getAttribute("purseslist_result");

        for (Map.Entry<CurrencyEntity, PurseEntity> mapEntry : currencyPurseHashMap.entrySet()) {

            writer.println("<option>");
            writer.println(mapEntry.getValue().getId());
            writer.println("</option>");
        }
    }
    writer.println( " </select>\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "      <td>Credit Card Number</td>\n" +
                    "      <td><input size=\"26\" type=\"text\" name=\"text_creditcardnumber\" /></td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "      <td>Amount</td>\n" +
                    "      <td><input size=\"26\" type=\"text\" name=\"text_amount\" /></td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "      <td colspan=\"2\" align=\"center\"><hr /></td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "      <td colspan=\"2\" align=\"center\">\n" +
                    "        <input type=\"submit\" value=\"Add\" name=\"addmoney_sumbit\" />\n" +
                    "        <input type=\"reset\" value=\"Cancel\" name=\"addmoney_cancel\" />\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "      <td colspan=\"2\" align=\"center\">\n" +
                    "          <input type=\"submit\" value=\"Back to operations page\" name=\"transfertooperations\" />\n" +
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
                    "  </table>\n" +
                    "</form>"
    );
%>

<%
    if (request.getAttribute("result") != null) {
        String result = (String) request.getAttribute("result");
        response.getWriter().println(result);
    }
%>
</body>
</html>
