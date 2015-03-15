<%@ page import="com.payment_cracker.api.dao.middle_level.middle_entities.TransactionEntity" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<%--
  Created by IntelliJ IDEA.
  User: Natalie
  Date: 19.02.2015
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Action Log</title>
    <style>
        <%@ include file="style_sheet.css"%>
    </style>
</head>
<body>
<%!
    List<ArrayList<TransactionEntity>> list;
%>
<%
    PrintWriter writer = response.getWriter();

    writer.println("<form id = \"user_actions\" method=\"post\" action = \"/controller\">\n" +
            "    <table \n" +
            "    <thead>\n" +
            "    <tr>\n" +
            "      <td colspan=\"2\" align=\"center\"></td>\n" +
            "    </tr>\n" +
            "    </thead>\n" +
            "    <tbody>\n" +
            "    <table>\n" +
            "    <tr>\n" +
            "        <td>\n" +
            "        <th align=\"center\">My Actions List</th>\n" +
            "        </td>\n" +
            "     </tr>\n" +
            "        <tr>\n" +
            "            <td align=\"center\">Action ID: </td>\n" +
            "            <td align=\"center\">Sender: </td>\n" +
            "            <td align=\"center\">Receiver: </td>\n" +
            "            <td align=\"center\">Amount: </td>\n" +
            "            <td align=\"center\">Sender Currency: </td>\n" +
            "            <td align=\"center\">Receiver Currency: </td>\n" +
            "            <td align=\"center\">Date: </td>\n" +
            "            <td align=\"center\">Current Currency Label: </td>\n" +
            "        </tr>");

    if ((ArrayList<TransactionEntity>) request.getAttribute("myTransactionsInfo") != null) {

        ArrayList<TransactionEntity> transactionEntity = (ArrayList<TransactionEntity>) request.getAttribute("myTransactionsInfo");

        for (TransactionEntity te : transactionEntity) {

            writer.println("<tr>");
            writer.println(" <td align=\"center\">");
            writer.println(te.getId());
            writer.println("</td>");

            writer.println(" <td align=\"center\">");
            writer.println(te.getSender().getId());
            writer.println("</td>");

            writer.println(" <td align=\"center\">");
            writer.println(te.getReceiver().getId());
            writer.println("</td>");

            writer.println(" <td align=\"center\">");
            writer.println(te.getMoney());
            writer.println("</td>");

            writer.println(" <td align=\"center\">");
            writer.println(te.getSenderCurrencyValue());
            writer.println("</td>");

            writer.println(" <td align=\"center\">");
            writer.println(te.getReceiverCurrencyValue());
            writer.println("</td>");

            writer.println(" <td align=\"center\">");
            writer.println(te.getDate());
            writer.println("</td>");

            writer.println(" <td align=\"center\">");
            writer.println(te.getSenderCurrencyLabel());
            writer.println("</td>");
            writer.println("</tr>");
        }
    }
    writer.println("" +
            "    </table>\n" +
            "        </td>\n" +
            "    </tr>\n" +
            "   <tr>\n" +
            "      <td colspan=\"2\" align=\"center\">\n" +
            "          <input type=\"submit\" value=\"Back to operations page\" name=\"transfertooperations\" />\n" +
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
