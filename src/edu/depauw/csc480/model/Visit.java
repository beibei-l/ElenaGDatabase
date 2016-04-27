package edu.depauw.csc480.model;

import edu.depauw.csc480.dao.VisitDAO;
import org.omg.CORBA.portable.ValueInputStream;

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

    private Patient patient;
    private String comments;


    public Visit(VisitDAO dao, int visitID, Patient patient, String comments) {
        this.dao = dao;
        this.visitID = visitID;
        this.patient = patient;
        this.comments = comments;
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

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments(){
        return comments;
    }

}
