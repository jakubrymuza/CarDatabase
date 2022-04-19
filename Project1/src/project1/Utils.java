package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.servlet.annotation.WebServlet;

import javax.sql.DataSource;

import javax.xml.transform.Result;

public class Utils {
    
    public static Connection GetConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=PD1", "sa", "sastudent");
    }


    public static String GetBackButton() {
        return "<button onclick=\"location.href='Main.html'\" type=\"button\">Wstecz</button>";
    }

    
    public static void PrintBeginningHTMLCode(PrintWriter out) {
        out.println("<html>");
        out.println("<head><title>Okno akcji</title></head>");
        out.println("<body>");
    }
    
    public static void PrintEndingHTMLCode(PrintWriter out) {
        out.println(Utils.GetBackButton());
        out.println("</body>");
        out.println("</html>");
    }
    
    public static void TransactionError(Exception e, Connection con, PrintWriter out) {
        out.println("Napotkano blad. ");
        try {
            if (con != null) {
                con.rollback();
                out.println("Wykonano rollback.");
            }
        } catch (SQLException e2) {
            out.println("Nie udalo sie wykonac rollbacku.");
        }


        e.printStackTrace();
    }

}
