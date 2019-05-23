/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *
 * @author Wala
 */
@WebServlet(urlPatterns = {"/NewServlet"})
public class NewServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter(); 
        String acc; 
        String Pass,newPass,Operation,Amount,temp;String query;
        try {
            Class.forName("com.mysql.jdbc.Driver"); 
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/firstdb", "root", "134687952a");
            if(con == null){
                out.println("shiiiiit");
            }
            else {
                Statement stmnt = con.createStatement();
                ResultSet rs =null; 
                Enumeration<String> paramNames = request.getParameterNames();
                acc = request.getParameter("accountNumber");
                temp = paramNames.nextElement();
               // out.println(temp);
                if(paramNames.hasMoreElements()){
                    temp = (String)paramNames.nextElement();
                    //out.println(temp);
                    if(temp.equals("NewPass")){//newPassowrd();
                        //out.println(temp);
                        newPass = request.getParameter("NewPass");
                        //out.println(newPass);
                        query = "Update users set Password= " + newPass + " where AccountNumber= " + acc;
                        int t = stmnt.executeUpdate(query);
                        if(t>0){
                            out.println("Password Changed");
                        }
                        else { 
                            out.println("Error Occured");
                        }
                    }
                    else if (temp.equals("Password")){//login();
                        Pass = request.getParameter("Password");
                        query ="Select * from users where AccountNumber=" + acc + " and Password=" + Pass;
                        rs = stmnt.executeQuery(query);
                        if(rs.next()){
                            out.println("Success");
                        }
                        else{
                            out.println("Failed");
                        }
                        
                    }
                    else if(temp.equals("Operation")){//WithdrawprDeposit();
                        Operation = request.getParameter("Operation");
                        temp = (String)paramNames.nextElement();
                        if(temp.equals("Ammount")){
                            Amount = request.getParameter("Ammount");
                            if (Operation.equals("Deposit")) {
                                query = "Select Balance from users where AccountNumber=" + acc;
                                rs = stmnt.executeQuery(query);
                                if (rs.next()) {
                                    int bal = Integer.parseInt(rs.getString("Balance"));
                                    bal += Integer.parseInt(Amount);
                                    query = "Update users set Balance=" + bal + " where AccountNumber=" + acc;
                                    int t = stmnt.executeUpdate(query);
                                    if(t>0){
                                        out.println(Amount + " Added ");
                                    }
                                    else { 
                                        out.println("Shiiiiiit");
                                    }
                                }
                            } 
                            else {//Withdraw
                                query = "Select Balance from users where AccountNumber=" + acc;
                                rs = stmnt.executeQuery(query);
                                if (rs.next()) {
                                    int bal = Integer.parseInt(rs.getString("Balance"));
                                    if (bal > Integer.parseInt(Amount)) {
                                        bal -= Integer.parseInt(Amount);
                                        query = "Update users set Balance=" + bal + " where AccountNumber=" + acc;
                                        int t = stmnt.executeUpdate(query);
                                        if (t > 0) {
                                            out.println(Amount + " Subbed ");
                                        } else {
                                            out.println("Shiiiiiit");
                                        }
                                    }
                                    else{//ifbal<amount
                                        out.println("Amount << Balance");
                                    }

                                }
                            }

                        }
                    }
                           
                }else { // getBalance;
                    //out.println(temp);
                    query = "Select Balance from users where AccountNumber=" + acc;
                    rs = stmnt.executeQuery(query);
                    if(rs.next()){
                        out.println(rs.getString("Balance"));
                    }
                    else{
                        out.println("Error Occured");
                    }
                }
            }        
          
        }
        catch (Exception e) { 
            out.print(e.toString());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
