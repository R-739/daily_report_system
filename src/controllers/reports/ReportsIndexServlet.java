package controllers.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import boxes.Box;
import models.Employee;
import models.Follow;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsIndexServlet
 */
@WebServlet("/reports/index")
public class ReportsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

        List<Report> reports = em.createNamedQuery("getAllReports", Report.class)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        long reports_count = (long)em.createNamedQuery("getReportsCount", Long.class)
                .getSingleResult();




        List<Box> boxList = new ArrayList<Box>();

        for (int i = 0; i < reports.size(); i++) {
            Box box = new Box();


            box.setId(reports.get(i).getId());

            box.setReport_date(reports.get(i).getReport_date());



            box.setTitle(reports.get(i).getTitle());

            box.setEmployee(reports.get(i).getEmployee());

            Employee e = (Employee)request.getSession().getAttribute("login_employee");


            List<Follow> follow = em.createNamedQuery("getAllFollows", Follow.class)
                    .setParameter("employee", e)
                    .setParameter("coworker",reports.get(i).getEmployee())
                    .getResultList();



            if(follow != null && follow.size() > 0 ) {
                box.setFollow(follow.get(0));
            }else {
                box.setFollow(null);
            }
            boxList.add(box);
            }

        em.close();
        request.setAttribute("reports", boxList);
        request.setAttribute("reports_count", reports_count);
        request.setAttribute("page", page);
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");


        }
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/index.jsp");
        rd.forward(request, response);

    }

}
