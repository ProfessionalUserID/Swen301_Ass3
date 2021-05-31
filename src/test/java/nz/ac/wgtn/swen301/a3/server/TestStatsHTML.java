package nz.ac.wgtn.swen301.a3.server;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestStatsHTML {

    @Test
    public void testStatsHTML_1() throws ServletException, IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        StatsServlet service = new StatsServlet();
        service.doGet(request, response);

        assertEquals(200, response.getStatus());
    }

    @Test
    public void testStatsHTML_2() throws ServletException, IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        StatsServlet service = new StatsServlet();
        service.doGet(request,response);

        assertTrue(response.getContentType().startsWith("text/html"));
    }

}
