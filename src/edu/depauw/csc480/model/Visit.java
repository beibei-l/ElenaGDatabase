package edu.depauw.csc480.model;

import edu.depauw.csc480.dao.VisitDAO;

/**
 * Model object for a row in the Course table.
 * Accesses the underlying database through a CourseDAO.
 * Based on Sciore, Section 9.1.
 *
 * @author bhoward
 */
public class Visit{
    private VisitDAO dao;
    private Patient patient;
    private int visitID;
    private int patientID;
    private String comments;


    public Visit(CourseDAO dao, Visit visit, int visitID, int patientID, String comments) {
        this.dao = dao;
        this.Patient = patient;
        this.visitID = visitID;
        this.patientID = patientID;
        this.comments = comments;
    }

    public String toString() {
        return dao + " " + visitID + ": " + patientID + ", " + " \n Comments:" + + comments;
    }

    public String getVisitID() {
        return visitID;
    }

    public void setVisitID(String visit) {
        this.visit = visit;
        dao.changeVisit(visitID, patientID, Java.SQL.Date);
    }

    public void setPatientID()
    {
        this.patientID = patientID;
    }

    public patientId getPatientID() {
        return patientID;
    }

    public void setComments()
    { this.comments = comments;
    }

    public String Comments(){
        return comments;
    }

}
