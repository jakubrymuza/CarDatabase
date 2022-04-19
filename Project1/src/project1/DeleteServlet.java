package project1;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "deleteServlet", urlPatterns = { "/deleteservlet" })
public class DeleteServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1250";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Utils.PrintBeginningHTMLCode(out);
        Connection con = null;

        try {
            con = Utils.GetConnection();
            con.setTransactionIsolation(java.sql.Connection.TRANSACTION_READ_COMMITTED);

            PreparedStatement prepStatement =
                con.prepareStatement("DELETE FROM dbo.OSOBY WHERE osoba_id=?");
          
            prepStatement.setInt(1, Integer.parseInt(request.getParameter("delID")));
            
            prepStatement.executeUpdate();

            con.commit();
            
            out.println("Nie napotkano bledow.");
            prepStatement.close();
            con.close();
        } catch (Exception e) {
            Utils.TransactionError(e,con,out);         
        }
        
        
        Utils.PrintEndingHTMLCode(out);
        out.close();
    }
}
