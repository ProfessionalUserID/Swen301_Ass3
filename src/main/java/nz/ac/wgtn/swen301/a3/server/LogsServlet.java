package nz.ac.wgtn.swen301.a3.server;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jdk.jfr.Percentage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class LogsServlet extends HttpServlet {

    public LogsServlet(){}

    Map<String, Integer> priorities = Map.ofEntries(Map.entry("OFF", 0), Map.entry("FATAL", 1), Map.entry("ERROR", 2), Map.entry("WARN", 3), Map.entry("INFO", 4), Map.entry("DEBUG", 5), Map.entry("TRACE", 6), Map.entry("ALL", 7));


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int n;
        PrintWriter pw = resp.getWriter();

        try {
           n = Integer.parseInt(req.getParameter("limit"));
        }catch (NumberFormatException e){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String m = req.getParameter("level");
        if (m==null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        resp.setContentType("application/json");

        ArrayList<ObjectNode> a = new ArrayList<>();

        for (ObjectNode o : Persistency.DB) {
            if (priorities.get(o.get("level").toString().toUpperCase()) <= priorities.get(m)){
                a.add(o);
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        a.sort((l1, l2) -> {
            try {
                return sdf.parse(l2.get("timestamp").asText()).compareTo(sdf.parse(l1.get("timestamp").asText()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        });

        ObjectMapper om = new ObjectMapper();
        ArrayNode an = om.createArrayNode();

        for (int i = 0; i < n && i < a.size(); i++){
            an.add(a.get(i));
        }

        pw.print(om.writerWithDefaultPrettyPrinter().writeValueAsString(an));
        pw.close();
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        StringBuffer string = new StringBuffer();
        String line;

        try {
            BufferedReader br = req.getReader();
            System.out.println(br.lines());
            while ((line = br.readLine()) != null) {
                string.append(line);
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid input, object invalid");
            return;
        }

        try {
            ObjectMapper map = new ObjectMapper();
            ObjectNode node = map.readValue(string.toString(), ObjectNode.class);
            HashSet<JsonNode> jnodes = new HashSet<>();
            jnodes.add(node.get("id"));
            jnodes.add(node.get("message"));
            jnodes.add(node.get("timestamp"));
            jnodes.add(node.get("thread"));
            jnodes.add(node.get("logger"));
            jnodes.add(node.get("level"));

            if (jnodes.contains(null)) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid input, object invalid");
                return;
            }

            for (ObjectNode on : Persistency.DB){
                if (node.get("id").textValue().equals(on.get("id").textValue())){
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid input, object invalid");
                    return;
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                try {
                    sdf.parse(node.get("timestamp").textValue());
                } catch (ParseException e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid input, object invalid");
                    return;
                }

            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                jnodes.add(field.getValue());
            }

            if((jnodes.size() > 7 && jnodes.contains(node.get("errorDetails"))) || (jnodes.size() > 6 && !jnodes.contains(node.get("errorDetails")))){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid input, object invalid");
                return;
            }

            if(!priorities.containsKey(node.get("level").textValue())){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid input, object invalid");
                return;
            }

            Persistency.DB.add(node);

        }catch (JsonProcessingException e){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid input, object invalid");
            return;
        }
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Persistency.DB.clear();
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

    }

}
