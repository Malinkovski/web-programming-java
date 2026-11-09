
package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;

@WebServlet(urlPatterns = "/registrations")
public class RegistrationListServlet extends HttpServlet {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        Context context = new Context();
        context.setVariable("registrations", registrationService.listAll());

        resp.setContentType("text/html;charset=UTF-8");

        templateEngine.process(
                "listRegistrations",
                context,
                resp.getWriter()
        );
    }
}
