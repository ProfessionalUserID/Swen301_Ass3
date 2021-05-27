package nz.ac.wgtn.swen301.a3.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class LogsServlet extends HttpServlet {

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
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        super.doPost(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        super.doDelete(req, resp);
    }

}
