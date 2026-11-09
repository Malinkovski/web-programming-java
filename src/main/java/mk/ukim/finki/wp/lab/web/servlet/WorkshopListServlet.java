
package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Workshop;
import mk.ukim.finki.wp.lab.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/servlet/workshops")
public class WorkshopListServlet extends HttpServlet {

    @Autowired
    private WorkshopService workshopService;

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

        String text = req.getParameter("text");
        String maxPriceParam = req.getParameter("maxPrice");

        if (text != null && text.isBlank()) {
            text = null;
        }

        Double maxPrice = null;

        if (maxPriceParam != null && !maxPriceParam.isBlank()) {
            try {
                maxPrice = Double.parseDouble(maxPriceParam);
            } catch (NumberFormatException e) {
                req.setAttribute("errorMessage", "Invalid maximum price");
            }
        }

        List<Workshop> workshops;

        if (text != null || maxPrice != null) {
            workshops = workshopService.search(text, maxPrice);
        } else {
            workshops = workshopService.listAll();
        }

        Context context = new Context();
        context.setVariable("workshops", workshops);
        context.setVariable("errorMessage",
                req.getAttribute("errorMessage") != null
                        ? req.getAttribute("errorMessage")
                        : req.getParameter("errorMessage"));
        context.setVariable("text", text);
        context.setVariable("maxPrice", maxPriceParam);

        resp.setContentType("text/html;charset=UTF-8");
        templateEngine.process("listWorkshops", context, resp.getWriter());
    }
}
