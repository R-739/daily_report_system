package boxes;

import java.sql.Date;

import models.Employee;
import models.Follow;



public class  Box {

    private Integer id;

    private Date report_date;

    private String title;

    private Follow follow;

    private Employee employee;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getReport_date() {
        return report_date;
    }

    public void setReport_date(Date report_date) {
        this.report_date = report_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Follow getFollow() {
        return follow;
    }
    public void setFollow(Follow follow) {
        this.follow = follow;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }






}

