package nz.ac.wgtn.swen301.a3.server;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPostLogs {

    @Test
    public void PostLogsTest_1() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("level","ALL");
        request.setParameter("limit","1");
        MockHttpServletResponse response = new MockHttpServletResponse();

        LogsServlet service = new LogsServlet();
        service.doPost(request,response);

        assertTrue(Objects.requireNonNull(response.getContentType()).startsWith("application/json"));
    }

    @Test
    public void PostLogsTest_2() throws IOException, ServletException {
        String logEvent = "{\n" +
                "    \"id\": \"d290f1ee-6c54-4b01-90e6-d701748f0851\",\n" +
                "    \"message\": \"application started\",\n" +
                "    \"timestamp\": \"04-05-2021 10:12:00\",\n" +
                "    \"thread\": \"main\",\n" +
                "    \"logger\": \"com.example.Foo\",\n" +
                "    \"level\": \"DEBUG\",\n" +
                "    \"errorDetails\": \"string\"\n" +
                "  }";

        Persistency.DB.clear();
        LogsServlet servlet = new LogsServlet();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        byte[] byteArray = logEvent.getBytes();
        request.setContent(byteArray);
        servlet.doPost(request,response);
        assertEquals(1, Persistency.DB.size());

        assertEquals(201, response.getStatus());
    }

    @Test
    public void PostLogsTest_3() throws IOException, ServletException {
        String logEvent = "{\n" +
                "    \"id\": \"d290f1ee-6c54-4b01-90e6-d701748f0851\",\n" +
                "    \"message\": \"application started\",\n" +
                "    \"timestamp\": \"04-05-2021 10:12:00\",\n" +
                "    \"thread\": \"main\",\n" +
                "    \"logger\": \"com.example.Foo\",\n" +
                "    \"level\": \"fakelevel\",\n" +
                "    \"errorDetails\": \"string\"\n" +
                "  }";

        Persistency.DB.clear();
        LogsServlet servlet = new LogsServlet();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        byte[] byteArray = logEvent.getBytes();
        request.setContent(byteArray);
        servlet.doPost(request,response);
        assertEquals(0, Persistency.DB.size());

        assertEquals(400, response.getStatus());
    }

    @Test
    public void PostLogsTest_4() throws IOException, ServletException {
        String logEvent = "{\n" +
                "    \"id\": \"d290f1ee-6c54-4b01-90e6-d701748f0851\",\n" +
                "    \"message\": \"application started\",\n" +
                "    \"timestamp\": \"04-05-2021 10:12:00\",\n" +
                "    \"logger\": \"com.example.Foo\",\n" +
                "    \"level\": \"DEBUG\",\n" +
                "    \"errorDetails\": \"string\"\n" +
                "  }";

        Persistency.DB.clear();
        LogsServlet servlet = new LogsServlet();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        byte[] byteArray = logEvent.getBytes();
        request.setContent(byteArray);
        servlet.doPost(request,response);
        assertEquals(0, Persistency.DB.size());

        assertEquals(400, response.getStatus());
    }

    @Test
    public void PostLogsTest_5() throws IOException, ServletException {
        String logEvent = "{\n" +
                "    \"id\": \"d290f1ee-6c54-4b01-90e6-d701748f0851\",\n" +
                "    \"message\": \"application started\",\n" +
                "    \"timestamp\": \"faketime\",\n" +
                "    \"thread\": \"main\",\n" +
                "    \"logger\": \"com.example.Foo\",\n" +
                "    \"level\": \"DEBUG\",\n" +
                "    \"errorDetails\": \"string\"\n" +
                "  }";

        Persistency.DB.clear();
        LogsServlet servlet = new LogsServlet();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        byte[] byteArray = logEvent.getBytes();
        request.setContent(byteArray);
        servlet.doPost(request,response);
        assertEquals(0, Persistency.DB.size());

        assertEquals(400, response.getStatus());
    }

    @Test
    public void PostLogsTest_6() throws IOException, ServletException {
        String logEvent = "{\n" +
                "    \"id\": \"d290f1ee-6c54-4b01-90e6-d701748f0851\",\n" +
                "    \"message\": \"application started\",\n" +
                "    \"timestamp\": \"04-05-2021 10:12:00\",\n" +
                "    \"thread\": \"main\",\n" +
                "    \"level\": \"DEBUG\",\n" +
                "    \"errorDetails\": \"string\"\n" +
                "  }";

        Persistency.DB.clear();
        LogsServlet servlet = new LogsServlet();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        byte[] byteArray = logEvent.getBytes();
        request.setContent(byteArray);
        servlet.doPost(request,response);
        assertEquals(0, Persistency.DB.size());

        assertEquals(400, response.getStatus());
    }

    @Test
    public void PostLogsTest_7() throws IOException, ServletException {
        String logEvent = "{\n" +
                "    \"id\": \"d290f1ee-6c54-4b01-90e6-d701748f0851\",\n" +
                "    \"message\": \"application started\",\n" +
                "    \"timestamp\": \"04-05-2021 10:12:00\",\n" +
                "    \"thread\": \"main\",\n" +
                "    \"logger\": \"com.example.Foo\",\n" +
                "    \"level\": \"DEBUG\",\n" +
                "    \"errorDetails\": \"string\"\n" +
                "  }";

        Persistency.DB.clear();
        LogsServlet servlet = new LogsServlet();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        byte[] byteArray = logEvent.getBytes();
        request.setContent(byteArray);
        servlet.doPost(request,response);
        request.setContent(byteArray);
        servlet.doPost(request,response);
        assertEquals(1, Persistency.DB.size());

        assertEquals(409, response.getStatus());
    }

    @Test
    public void PostLogsTest_8() throws IOException, ServletException {
        String logEvent = "{\n" +
                "    \"id\": \"d290f1ee-6c54-4b01-90e6-d701748f0851\",\n" +
                "    \"message\": \"application started\",\n" +
                "    \"timestamp\": \"04-05-2021 10:12:00\",\n" +
                "    \"thread\": \"main\",\n" +
                "    \"logger\": \"com.example.Foo\",\n" +
                "    \"level\": \"DEBUG\",\n" +
                "    \"errorDetails\": \"string\"\n" +
                "    \"fakeField\": \"fakeInfo\"\n" +
                "  }";

        Persistency.DB.clear();
        LogsServlet servlet = new LogsServlet();

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        byte[] byteArray = logEvent.getBytes();
        request.setContent(byteArray);
        servlet.doPost(request,response);
        assertEquals(0, Persistency.DB.size());

        assertEquals(400, response.getStatus());
    }

}
