package nz.ac.wgtn.swen301.a3.server;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDeleteLogs {

    @Test
    public void DeleteLogsTest_1() throws IOException, ServletException {
        Persistency.DB.clear();
        LogsServlet servlet = new LogsServlet();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        servlet.doDelete(request,response);
        assertEquals(0, Persistency.DB.size());

        assertEquals(200, response.getStatus());
    }

}
