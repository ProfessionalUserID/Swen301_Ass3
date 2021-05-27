package nz.ac.wgtn.swen301.a3.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LogsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int n;

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



        super.doGet(req, resp);
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
