package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "updateServlet", urlPatterns = { "/updateservlet" })
public class UpdateServlet extends HttpServlet {
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
            con.setAutoCommit(false);
            con.setTransactionIsolation(java.sql.Connection.TRANSACTION_READ_COMMITTED);

            PreparedStatement prepStatement =
                con.prepareStatement("UPDATE dbo.OSOBY SET " + "imie=?, nazwisko=?, samochod_id=?, data_prod=? " +
                                     "WHERE osoba_id=?");


            RunUpdates(request, con, Integer.parseInt(request.getParameter("addID")));

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

    private void UpdateParameter(HttpServletRequest request, Connection con, String paramName, String value,
                                 int ID) throws SQLException {
        PreparedStatement prepStatement =
            con.prepareStatement("UPDATE OSOBY SET " + paramName + "=?" + " WHERE osoba_id=" + ID);

        prepStatement.setString(1, value);
        prepStatement.executeUpdate();
    }

    private void RunUpdates(HttpServletRequest request, Connection con, int ID) throws SQLException {

        String name = request.getParameter("name");
        if (name != null && !name.isEmpty())
            UpdateParameter(request, con, "imie", name, ID);

        String surname = request.getParameter("surname");
        if (surname != null && !surname.isEmpty())
            UpdateParameter(request, con, "nazwisko", surname, ID);

        String carID = request.getParameter("carID");
        if (carID != null && !carID.isEmpty())
            UpdateParameter(request, con, "samochod_id", carID, ID);

        String date = request.getParameter("date");
        if (date != null && !date.isEmpty())
            UpdateParameter(request, con, "data_prod", date, ID);

    }

}
