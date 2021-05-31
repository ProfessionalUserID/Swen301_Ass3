package nz.ac.wgtn.swen301.a3.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class StatsServlet extends HttpServlet {

    public StatsServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<String> levels = new ArrayList<>(Arrays.asList("ALL", "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL", "OFF"));
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        Map<String, Map<String, Integer>> tableMap = new LinkedHashMap<>();

        for(ObjectNode o : Persistency.DB){
            Map<String, Integer> m = new HashMap<>();
            String name = o.get("logger").textValue();

            if (!tableMap.containsKey(name)) {
                tableMap.put(name, new HashMap<>());
            }
            else m = tableMap.get(name);

            String level = o.get("level").textValue();

            if(!m.containsKey((level))){
                m.put(level, 1);
            }
            else m.put(level, m.get(level)+1);
            tableMap.put(name, m);
        }

        out.println("<html>");
        out.println("<body>");
        out.println("<table border>");
        out.println("<th>logger</th>");

        for(String l : levels){
            out.println("<th>"+l+"</th>");
        }

        for(Map.Entry<String, Map<String, Integer>> m : tableMap.entrySet()){
            out.println("<tr><td>"+m.getKey()+"</td>");
            for(String l : levels){
                if(!m.getValue().containsKey(l)){
                    out.println("<td>"+"0"+"</td>");
                }
                else out.println("<td>"+m.getValue().get(l)+"</td>");
            }
            out.println("</tr>");

        }

        out.println("</body>");
        out.println("</html>");

        out.close();
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
