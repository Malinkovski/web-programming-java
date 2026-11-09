
package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;

@WebServlet(urlPatterns = "/registrations/delete/*")
public class RegistrationDeleteServlet extends HttpServlet {

    @Autowired
    private RegistrationService registrationService;

    @Override
    public void init() throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        String id = pathInfo.substring(1);

        try {
            Long registrationId = Long.parseLong(id);
            registrationService.deleteById(registrationId);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/registrations");
    }
}
