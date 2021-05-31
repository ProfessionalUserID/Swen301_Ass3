package nz.ac.wgtn.swen301.a3.server;

import com.fasterxml.jackson.databind.ObjectMapper;
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

        Persistency.DB.clear();
        ObjectMapper objectMapper1 = new ObjectMapper();
        ObjectNode objectNode1 = objectMapper1.createObjectNode();
        objectNode1.put("id", "d290f1ee-6c54-4b01-90e6-d701748f0851");
        objectNode1.put("message", "application started");
        objectNode1.put("timestamp", "04-05-2021 10:12:00");
        objectNode1.put("thread", "main");
        objectNode1.put("logger", "com.example.Foo");
        objectNode1.put("level", "OFF");
        objectNode1.put("errorDetails", "string");
        Persistency.DB.add(objectNode1);

        ObjectNode objectNode2 = objectMapper1.createObjectNode();
        objectNode2.put("id", "d290f1ee-6c54-4b01-90e6-d701748f0851");
        objectNode2.put("message", "application started");
        objectNode2.put("timestamp", "05-05-2021 10:12:00");
        objectNode2.put("thread", "main");
        objectNode2.put("logger", "com.example.Foo");
        objectNode2.put("level", "ALL");
        objectNode2.put("errorDetails", "string");
        Persistency.DB.add(objectNode2);

        ObjectNode objectNode3 = objectMapper1.createObjectNode();
        objectNode3.put("id", "d290f1ee-6c54-4b01-90e6-d701748f0851");
        objectNode3.put("message", "application started");
        objectNode3.put("timestamp", "06-05-2021 10:12:00");
        objectNode3.put("thread", "main");
        objectNode3.put("logger", "ok");
        objectNode3.put("level", "TRACE");
        objectNode3.put("errorDetails", "string");
        Persistency.DB.add(objectNode3);
        Persistency.DB.add(objectNode3);

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
