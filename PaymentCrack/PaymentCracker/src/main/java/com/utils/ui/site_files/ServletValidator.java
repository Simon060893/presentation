package com.utils.ui.site_files;

import com.payment_cracker.api.dao.exceptions.DbException;
import com.payment_cracker.api.dao.high_level.access_actions.AccessPoint;
import com.payment_cracker.api.dao.utils.CurrencyTypes;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ServletValidator {

    public static final Logger logger = Logger.getLogger("payment");
    public void validate(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("user") != null)
            {
                if (request.getParameter("logouttransfer") != null) {
                    session.removeAttribute("user");
                    request.getRequestDispatcher("WEB-INF/main_site/sign_in.jsp").forward(request, response);
                } else if (request.getParameter("operations_addpurse") != null) {
                    request.getRequestDispatcher("/WEB-INF/main_site/add_purse.jsp").forward(request, response);
                } else if (request.getParameter("operations_performtransaction") != null) {
                    AccessPoint user = (AccessPoint) session.getAttribute("user");
                    request.setAttribute("purseslist_result", user.getPursesInfo());
                    request.getRequestDispatcher("WEB-INF/main_site/transaction.jsp").forward(request, response);
                } else if (request.getParameter("operations_addmoney") != null) {
                    AccessPoint user = (AccessPoint) session.getAttribute("user");
                    request.setAttribute("purseslist_result", user.getPursesInfo());
                    request.getRequestDispatcher("WEB-INF/main_site/add_money.jsp").forward(request, response);
                } else if (request.getParameter("operations_withdrawmoney") != null) {
                    AccessPoint user = (AccessPoint) session.getAttribute("user");
                    request.setAttribute("purseslist_result", user.getPursesInfo());
                    request.getRequestDispatcher("WEB-INF/main_site/withdraw_money.jsp").forward(request, response);
                } else if (request.getParameter("addmoney_sumbit") != null) {
                    addMoney(session, request, response);
                } else if (request.getParameter("transfertooperations") != null) {
                    transferOperations(session, request, response);
                } else if (request.getParameter("transfertoadminpage") != null) {
                    transferToAdminPage(session, request, response);
                } else if (request.getParameter("addpurse_add") != null) {
                    addPurse(session, request, response);
                } else if (request.getParameter("transaction_send") != null) {
                    makeTransaction(session, request, response);
                } else if (request.getParameter("withdrawmoney_withdraw") != null) {
                    withdrawMoney(session, request, response);
                } else if (request.getParameter("operations_showmyoperationslist") != null) {
                    transfer(session, request, response);
                } else if (request.getParameter("adminpage_setban") != null) {
                    adminSetBan(session, request, response);
                } else if (request.getParameter("adminpage_getaccountinfo") != null) {
                    adminGetAccountInfo(session, request, response);
                } else if (request.getParameter("adminpage_getaccountspurses") != null) {
                    adminGetAccountPurses(session, request, response);
                } else if (request.getParameter("adminpage_getaccountsinfo") != null) {
                    adminGetAccountsInfo(session, request, response);
                } else if (request.getParameter("deleteaccount") != null) {
                    deleteAccount(session, request, response);
                } else if (request.getParameter("adminpage_showsystembalance") != null) {
                    showSystemBalance(session, request, response);
                } else if (request.getParameter("adminpage_registeradmin") != null) {
                    registerAdminTransfer(session, request, response);
                } else if (request.getParameter("registeradmin_confirm") != null) {
                    registerAdmin(session, request, response);
                } else if (session.getAttribute("user") != null) {
                    redirectUser(session, request, response);
                }

            } else if (session.getAttribute("user") == null) {
                if (request.getParameter("signin_login") != null) {
                    signIn(session, request, response);
                } else if (request.getParameter("signup_confirm") != null) {
                    signUp(session, request, response);
                } else if (request.getParameter("signin_signup") != null) {
                    request.getRequestDispatcher("/WEB-INF/main_site/sign_up.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("WEB-INF/main_site/sign_in.jsp").forward(request, response);
                }
            }
        } catch (DbException|ServletException|IOException|ParseException|InterruptedException e) {
            logger.error(e.getMessage(),e);
        }
    }

    private void redirectUser(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DbException {
        AccessPoint user = (AccessPoint) session.getAttribute("user");
        if (!user.getAccountInfo().isAdministrator()) {
            request.setAttribute("pursesInfo", user.getPursesInfo());
            request.setAttribute("login", user.getAccountInfo().getLogin());
            request.getRequestDispatcher("/WEB-INF/main_site/operations.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("WEB-INF/main_site/admin_page.jsp").forward(request, response);
        }
    }

    public static boolean isValid(String inputStr, String expression) {
        boolean isValid = false;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if(matcher.matches()){
            isValid = true;
        }
        return isValid;
    }

    private void signIn(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DbException, InterruptedException {
        String login = request.getParameter("text_login");
        String password = request.getParameter("text_password");

        AccessPoint user = new AccessPoint();

        if (isValid(login, "^[a-zA-Z][a-zA-Z0-9]{6,12}") && isValid(password, "^[0-9a-zA-Z]{8,12}")) {
            switch (user.connect(login, password)) {
                case 1:
                    session.setAttribute("user", user);
                    request.setAttribute("pursesInfo", user.getPursesInfo());
                    request.getRequestDispatcher("/WEB-INF/main_site/operations.jsp").forward(request, response);
                    break;
                case -1:
                    request.getRequestDispatcher("/WEB-INF/main_site/error.jsp").forward(request, response);
                    break;
                case 2:
                    session.setAttribute("user", user);
                    request.getRequestDispatcher("/WEB-INF/main_site/admin_page.jsp").forward(request, response);
                    break;
                default:
                    request.getRequestDispatcher("/WEB-INF/main_site/sign_in.jsp").forward(request, response);
                    break;
            }

        } else {
            session.removeAttribute("user");
            request.setAttribute("result", "Invalid Data Format");
            request.getRequestDispatcher("WEB-INF/main_site/sign_in.jsp").forward(request, response);
        }
    }

    private void signUp(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DbException, InterruptedException {
        String login = request.getParameter("text_login");
        String password = request.getParameter("text_password");
        String fio = request.getParameter("text_name");
        String phoneNumber = request.getParameter("text_phonenumber");
        String email = request.getParameter("text_email");
        AccessPoint user = new AccessPoint();
        if(isValid(login, "^[a-zA-Z][a-zA-Z0-9]{6,12}")
                && isValid(password, "^[0-9a-zA-Z]{8,12}")
                && isValid(phoneNumber, "^[0-9]{8,16}")
                && isValid(email, "(\\s+[a-z][a-z0-9._%-]+@[a-z0-9._%-]+\\.[a-z]{2,4})|(^[a-z][a-z0-9._%-]+@[a-z0-9._%-]+\\.[a-z]{2,4})")) {
            request.setAttribute("result", user.createUser(login, password, fio, phoneNumber, email));
            request.getRequestDispatcher("WEB-INF/main_site/sign_in.jsp").forward(request, response);
        } else {
            request.setAttribute("result", "Invalid Data Format");
            request.getRequestDispatcher("WEB-INF/main_site/sign_up.jsp").forward(request, response);
        }
    }

    private void registerAdmin(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DbException, InterruptedException {
        String login = request.getParameter("text_login");
        String password = request.getParameter("text_password");
        String fio = request.getParameter("text_name");
        String phoneNumber = request.getParameter("text_phonenumber");
        String email = request.getParameter("text_email");
        AccessPoint user = (AccessPoint) session.getAttribute("user");
        if(isValid(login, "^[a-zA-Z][a-zA-Z0-9]{6,12}")
                && isValid(password, "^[0-9a-zA-Z]{8,12}")
                && isValid(phoneNumber, "^[0-9]{8,16}")
                && isValid(email, "(\\s+[a-z][a-z0-9._%-]+@[a-z0-9._%-]+\\.[a-z]{2,4})|(^[a-z][a-z0-9._%-]+@[a-z0-9._%-]+\\.[a-z]{2,4})")) {
            request.setAttribute("result", user.createAdministrator(login, password, fio, phoneNumber, email));
            request.getRequestDispatcher("WEB-INF/main_site/admin_page.jsp").forward(request, response);
        } else {
            request.setAttribute("result", "Invalid Data Format");
            request.getRequestDispatcher("WEB-INF/main_site/admin_page.jsp").forward(request, response);
        }
    }

    private void makeTransaction(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DbException {
        Long fromPurseId = Long.valueOf(request.getParameter("list_purses"));
        Long wherePurseId = Long.valueOf(request.getParameter("text_receiverspurseid"));
        Double moneyAmount = Double.valueOf(request.getParameter("text_amount"));
        AccessPoint user = (AccessPoint) session.getAttribute("user");
        if(isValid(String.valueOf(fromPurseId), "^[0-9]{12}")
                && isValid(String.valueOf(wherePurseId), "^[0-9]{12}")
                && isValid(String.valueOf(moneyAmount), "^[0-9]*\\.?[0-9]+$")) {
            request.setAttribute("result", user.makeTransaction(fromPurseId, wherePurseId, moneyAmount));
            request.getRequestDispatcher("WEB-INF/main_site/transaction.jsp").forward(request, response);
        } else {
            request.setAttribute("result", "Invalid Data Format");
            request.getRequestDispatcher("WEB-INF/main_site/transaction.jsp").forward(request, response);
        }
    }

    private void addMoney(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DbException {
        Long purseId = Long.valueOf(request.getParameter("list_purses"));
        Long creditCardId = Long.valueOf(request.getParameter("text_creditcardnumber"));
        Double moneyAmount = Double.valueOf(request.getParameter("text_amount"));
        AccessPoint user = (AccessPoint) session.getAttribute("user");
        if(isValid(String.valueOf(purseId), "^[0-9]{12}") && isValid(String.valueOf(creditCardId), "^[0-9]{12}") && isValid(String.valueOf(moneyAmount), "^[0-9]*\\.?[0-9]+$")) {
            request.setAttribute("result", user.makeTransaction(creditCardId, purseId, moneyAmount));
            request.getRequestDispatcher("WEB-INF/main_site/add_money.jsp").forward(request, response);
        } else {
            request.setAttribute("result", "Invalid Data Format");
            request.getRequestDispatcher("WEB-INF/main_site/add_money.jsp").forward(request, response);
        }
    }

    private void withdrawMoney(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws DbException, ServletException, IOException {
        Long creditCardId = Long.valueOf(request.getParameter("text_creditcardnumber"));
        Long purseId = Long.valueOf(request.getParameter("list_purses"));
        Double moneyAmount = Double.valueOf(request.getParameter("text_amount"));
        AccessPoint user = (AccessPoint) session.getAttribute("user");
        if(isValid(String.valueOf(creditCardId), "^[0-9]{12}")
                && isValid(String.valueOf(purseId), "^[0-9]{12}")
                && isValid(String.valueOf(moneyAmount), "^[0-9]*\\.?[0-9]+$")) {
            request.setAttribute("result", user.makeTransaction(purseId, creditCardId, moneyAmount));
            request.getRequestDispatcher("WEB-INF/main_site/withdraw_money.jsp").forward(request, response);
        } else {
            request.setAttribute("result", "Invalid Data Format");
            request.getRequestDispatcher("WEB-INF/main_site/withdraw_money.jsp").forward(request, response);
        }
    }

    private void addPurse(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws DbException, ServletException, IOException {
        String currencyLabel = request.getParameter("list_currencies");
        AccessPoint user = (AccessPoint) session.getAttribute("user");
        if (CurrencyTypes.UAH.getCurrencyName().equals(currencyLabel)) {
            request.setAttribute("result", user.createPurse(CurrencyTypes.UAH));
        } else if (CurrencyTypes.USD.getCurrencyName().equals(currencyLabel)) {
            request.setAttribute("result", user.createPurse(CurrencyTypes.USD));
        } else if (CurrencyTypes.EUR.getCurrencyName().equals(currencyLabel)) {
            request.setAttribute("result", user.createPurse(CurrencyTypes.EUR));
        }

        request.getRequestDispatcher("WEB-INF/main_site/add_purse.jsp").forward(request, response);
    }

    private void transferOperations(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DbException {
        AccessPoint user = (AccessPoint) session.getAttribute("user");
        request.setAttribute("pursesInfo", user.getPursesInfo());
        request.getRequestDispatcher("WEB-INF/main_site/operations.jsp").forward(request, response);
    }

    private void transferToAdminPage(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DbException {
        request.getRequestDispatcher("WEB-INF/main_site/admin_page.jsp").forward(request, response);
    }

    private void transfer(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws DbException, ParseException, ServletException, IOException {
        AccessPoint user = (AccessPoint) session.getAttribute("user");
        request.setAttribute("myTransactionsInfo", user.getLastTransactions());
        request.getRequestDispatcher("WEB-INF/main_site/user_actions.jsp").forward(request, response);
    }

    private void adminSetBan(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws DbException, ServletException, IOException {
        Long userId = Long.valueOf(request.getParameter("text_userid"));
        AccessPoint user = (AccessPoint) session.getAttribute("user");
        if(isValid(String.valueOf(userId), "^[0-9]{12}")) {
            user.setBan(userId, !user.getAccountInfo(userId).isBan());
            request.setAttribute("result", "Now user's account status is: " + user.getAccountInfo(userId).isBan());
            request.getRequestDispatcher("WEB-INF/main_site/admin_page.jsp").forward(request, response);
        } else {
            request.setAttribute("result", "Invalid Data Format");
            request.getRequestDispatcher("WEB-INF/main_site/admin_page.jsp").forward(request, response);
        }
    }

    private void adminGetAccountInfo(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DbException {
        AccessPoint user = (AccessPoint) session.getAttribute("user");
        Long userId = Long.valueOf(request.getParameter("text_userid"));
        if(isValid(String.valueOf(userId), "^[0-9]{12}")) {
            request.setAttribute("info", user.getAccountInfo(userId));
            request.getRequestDispatcher("WEB-INF/main_site/account_info.jsp").forward(request, response);
        } else {
            request.setAttribute("info", "Invalid Data Format");
            request.getRequestDispatcher("WEB-INF/main_site/admin_page.jsp").forward(request, response);
        }
    }

    private void adminGetAccountPurses(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DbException {
        AccessPoint user = (AccessPoint) session.getAttribute("user");
        Long userId = Long.valueOf(request.getParameter("text_userid"));
        if(isValid(String.valueOf(userId), "^[0-9]{12}")) {
            request.setAttribute("pursesInfo", user.getAllPursesById(userId));
            request.getRequestDispatcher("WEB-INF/main_site/account_purses.jsp").forward(request, response);
        } else {
            request.setAttribute("pursesInfo", "Invalid Data Format");
            request.getRequestDispatcher("WEB-INF/main_site/admin_page.jsp").forward(request, response);
        }
    }

    private void deleteAccount(HttpSession session, HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException, DbException {
        AccessPoint user = (AccessPoint) session.getAttribute("user");
        request.setAttribute("info", user.setAccountStage(user.getAccountInfo().getId(), false));
        session.removeAttribute("user");
        request.getRequestDispatcher("WEB-INF/main_site/sign_in.jsp").forward(request, response);
    }

    private void adminGetAccountsInfo(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DbException {
        AccessPoint user = (AccessPoint) session.getAttribute("user");
        request.setAttribute("info", user.getAllAccounts());
        request.getRequestDispatcher("WEB-INF/main_site/accounts_info.jsp").forward(request, response);
    }

    private void showSystemBalance(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DbException {
        AccessPoint user = (AccessPoint) session.getAttribute("user");
        request.setAttribute("system_balance", user.getMoneyAmountFromPaymentCrackerCreditCard());
        request.getRequestDispatcher("WEB-INF/main_site/admin_page.jsp").forward(request, response);
    }

    private void registerAdminTransfer(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DbException {
        request.getRequestDispatcher("WEB-INF/main_site/admin_registration.jsp").forward(request, response);
    }

    public void get(HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession();
        AccessPoint user;
        try {
            if (httpSession.getAttribute("user") != null) {
                user = (AccessPoint) httpSession.getAttribute("user");
                if (!user.getAccountInfo().isAdministrator()) {
                    request.setAttribute("pursesInfo", user.getPursesInfo());
                    request.getRequestDispatcher("WEB-INF/main_site/operations.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("WEB-INF/main_site/admin_page.jsp").forward(request, response);
                }
            }else {
                user = new AccessPoint();
                request.getRequestDispatcher("WEB-INF/main_site/sign_in.jsp").forward(request, response);
            }
        } catch (ServletException | IOException | DbException | InterruptedException e) {
           logger.error(e.getMessage(), e);
        }
    }
}
