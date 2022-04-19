package project1;
import java.awt.Desktop;
import java.net.URI;

// funkcja testujaca, uruchamia end-start razy servlet AddServlet
public class TestClass {
    public static void main(String[] args) {
        int first = 6, end =7;
        long start = System.currentTimeMillis();
        
        for (int i = first; i < end; ++i) {
            StartAddServlet(i);

        }
        
        long finish = System.currentTimeMillis();
        System.out.println("Ilosc rekordow:"+((end-first)));
        System.out.println("Uplynelo:"+((finish - start))+"ms");
    }

    public static void StartAddServlet(int id) {
        Desktop desktop = java.awt.Desktop.getDesktop();
        try {
            URI myuri = new URI("http://127.0.0.1:7101/Project1/addservlet?personID="+id+"&name=imie&surname=nazw&carID=1&date=2000-02-02");
            desktop.browse(myuri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
