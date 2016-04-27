package edu.depauw.csc480.model;

import java.util.Collection;

import edu.depauw.csc480.dao.PatientDAO;

/**
 * Model object for a row in the Faculty table.
 * Accesses the underlying database through a FacultyDAO.
 * Based on Sciore, Section 9.1.
 *
 * @author egonzalez
 */
public class Patient {
    private PatientDAO dao;
    private int patientID;
    private String pname;
    private int demograhic;
    private String email;

    public Patient(PatientDAO dao, int patientID, String pname, String email, int demographic) {
        this.dao = dao;
        this.patientID = patientID;
        this.pname = pname;
        this.email = email;
        this.demographic = demographic;
    }

    public String toString() {
        return pname + ", " + patientID + ", " + email + ", " + demographic;
    }

    public void setPname(int pname)
    {
        this.Pname = pname
    }

    public String getPname() {
        return pname;
    }

    public void setPatientID(int patientID) {
        this.PatientID = patientID;
    }

    public int getPatientID()
    { return patientID;
    }

    public void getEmail(String email)
    {   this.Email = email;

    }

    public String getEmail()
    {
        return email;
    }

    public void setVisit(Visit vis)
    {
        this.vis = vis;
        dao.changeVisit(patientID, visitID);
    }

    public void getVisit(Visit vis)
    {
        Collection<Visits> getVisit()
        {
            if(courses == null) courses = dao.getVisits(visitID);
            return visit;
        }
    }

    public int getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.Address = address;
    }

    public void setDemographic(int demographic)
    {   this.Demographic = demographic;
    }

    public int getDemographic()
    {  return demographic;
    }

    public Collection<Visit> getVisit() {
        if (visit == null) courses = dao.getVisit(VisitID);
        return visit;
    }
}
