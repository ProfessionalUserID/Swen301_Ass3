package nz.ac.wgtn.swen301.a3.server;

import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class StatsCSVServlet extends HttpServlet {


    public StatsCSVServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<String> levels = new ArrayList<>(Arrays.asList("ALL", "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL", "OFF"));

        resp.setContentType("text/csv");
        ServletOutputStream out = resp.getOutputStream();
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

        out.print("logger");
        for(String l : levels){
            out.print("\t"+l);
        }
        out.print("\n");

        for(Map.Entry<String, Map<String, Integer>> m : tableMap.entrySet()){
            out.print(m.getKey());
            for(String l : levels){
                if(!m.getValue().containsKey(l)){
                    out.print("\t"+"0");
                }
                else out.print("\t"+m.getValue().get(l));
            }
            out.print("\n");

        }
        out.close();
        resp.setStatus(HttpServletResponse.SC_OK);
    }


}
