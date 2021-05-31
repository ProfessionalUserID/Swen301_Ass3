package nz.ac.wgtn.swen301.a3.server;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class StatsXLSServlet extends HttpServlet {

    public StatsXLSServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<String> levels = new ArrayList<>(Arrays.asList("ALL", "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL", "OFF"));

        resp.setContentType("application/vnd.ms-excel");
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

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet s = wb.createSheet("stats");

        int row = 0;
        int col = 0;

        Row r = s.createRow(row);
        Cell c = r.createCell(col);
        row++;
        col++;

        c.setCellValue("logger");
        for(String l : levels){
            c = r.createCell(col);
            col++;
            c.setCellValue(l);
        }

        for(Map.Entry<String, Map<String, Integer>> m : tableMap.entrySet()){
            col = 0;
            r = s.createRow(row);
            c = r.createCell(col);
            col++;
            c.setCellValue(m.getKey());

            for(String l : levels){
                c = r.createCell(col);
                c.setCellValue(m.getValue().getOrDefault(l, 0));
                col++;
            }
            row++;

        }
        wb.write(out);

        out.close();
        wb.close();
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
