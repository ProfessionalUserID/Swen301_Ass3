package nz.ac.wgtn.swen301.a3.server;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGetLogs {

    @Test
    public void GetLogsTest_1() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // query parameter missing
        LogsServlet service = new LogsServlet();
        service.doGet(request,response);

        assertEquals(400,response.getStatus());
    }



    @Test
    public void GetLogsTest_2() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("not a valid param name","42");
        MockHttpServletResponse response = new MockHttpServletResponse();
        // wrong query parameter

        LogsServlet service = new LogsServlet();
        service.doGet(request,response);

        assertEquals(400,response.getStatus());
    }

    @Test
    public void GetLogsTest_3() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("name","J");
        MockHttpServletResponse response = new MockHttpServletResponse();

        LogsServlet service = new LogsServlet();
        service.doGet(request,response);

        assertEquals(400,response.getStatus());
    }

    @Test
    public void GetLogsTest_4() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("level","ALL");
        request.setParameter("limit","1");
        MockHttpServletResponse response = new MockHttpServletResponse();

        LogsServlet service = new LogsServlet();
        service.doGet(request,response);

        assertTrue(Objects.requireNonNull(response.getContentType()).startsWith("application/json"));
    }



}
