package edu.depauw.csc480.model;

import edu.depauw.csc480.dao.VisitDAO;
import org.omg.CORBA.portable.ValueInputStream;

import java.sql.Time;
import java.util.Date;

/**
 * Model object for a row in the Course table.
 * Accesses the underlying database through a CourseDAO.
 * Based on Sciore, Section 9.1.
 *
 * @author bhoward
 */
public class Visit{
    private VisitDAO dao;
    private int visitID;
    private Date visit_date;
    private Time visit_time;
    private Patient patient;
    private String comments;

    public Visit(VisitDAO dao, int visitID, Date visit_date, Time visit_time, String comments, Patient patient) {
        this.dao = dao;
        this.visitID = visitID;
        this.visit_date = visit_date;
        this.visit_time = visit_time;
        this.comments = comments;
        this.patient = patient;

    }

    // TODO: edit this method.
    public String toString() {
        return dao + " " + ", " + " \n Comments:" + comments;
    }

    public int getVisitID(){
        return visitID;
    }

    public Patient getPatient() {
        return patient;
    }

    public Date getVisit_date() {
        return visit_date;
    }

    public Time getVisit_time() {
        return visit_time;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments(){
        return comments;
    }

}
