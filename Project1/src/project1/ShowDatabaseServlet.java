package project1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "showDatabaseServlet", urlPatterns = { "/showdatabaseservlet" })
public class ShowDatabaseServlet extends HttpServlet {
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
                con.prepareStatement("SELECT * FROM dbo.OSOBY " +
                                     "WHERE data_prod > ? AND data_prod < ? AND nazwisko LIKE ? ORDER BY " +
                                     GetSortFields(request));

            InitiateStatement(request, prepStatement);

            ResultSet rs = prepStatement.executeQuery();

            PrintResult(rs, out);

            prepStatement.close();
            rs.close();
            con.close();
        } catch (Exception e) {
            out.println("Napotkano blad");
            e.printStackTrace();
        }

        Utils.PrintEndingHTMLCode(out);
        out.close();

    }

    private void PrintResult(ResultSet rs, PrintWriter out) throws SQLException {

        out.println("<table border=\"1\">");

        out.println("<tr>");
        out.println("<th>ID</th>");
        out.println("<th>Imiê</th>");
        out.println("<th>Nazwisko</th>");
        out.println("<th>ID samochodu</th>");
        out.println("<th>Data produkcji</th>");
        out.println("</tr>");

        while (rs.next()) {
            out.println("<tr>");
            out.println("<th>" + rs.getString("osoba_id") + "</th>");
            out.println("<th>" + rs.getString("imie") + "</th>");
            out.println("<th>" + rs.getString("nazwisko") + "</th>");
            out.println("<th>" + rs.getString("samochod_id") + "</th>");
            out.println("<th>" + rs.getString("data_prod") + "</th>");
            out.println("</tr>");
        }

        out.println("</table> ");
    }

    private void InitiateStatement(HttpServletRequest request, PreparedStatement prepStatement) throws SQLException {
        String start = request.getParameter("startDate");
        if (start == null || start.isEmpty())
            start = "1000-01-01";

        String end = request.getParameter("endDate");
        if (end == null || end.isEmpty())
            end = "3000-01-01";

        String sur = request.getParameter("surSub");
        if (sur == null || sur.isEmpty())
            sur = "";


        prepStatement.setString(1, start);
        prepStatement.setString(2, end);
        prepStatement.setString(3, "%" + sur + "%");
    }

    private String GetSortFields(HttpServletRequest request) {
        String sort = request.getParameter("sort");
        if (sort.equals("byName"))
            return "nazwisko,imie";
        else if (sort.equals("byDate"))
            return "data_prod";
        return "osoba_id";
    }
}
