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
    private String address;
    private String email;
    private int demographic;

    private Collection<Visit> visits;   // Need one of this...
    private Collection<Diagnosis> diagnoses;
    private Collection<Medication> medications;

    // patientId patientName address demographic email
    public Patient(PatientDAO dao, int patientID, String pname, String address, int demographic, String email) {
        this.dao = dao;
        this.patientID = patientID;
        this.pname = pname;
        this.email = email;
        this.address = address;
        this.demographic = demographic;
    }

    public String toString() {
        return pname + ", " + email + ", " + demographic;
    }

    public int getPatientID(){
        return patientID;
    }

    public void setPname(String pname)
    {
        this.pname = pname;
    }

    public String getPname() {
        return pname;
    }

    public void getEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setDemographic(int demographic) {   this.demographic = demographic;
    }

    public int getDemographic()
    {
        return demographic;
    }

    public Collection<Visit> getVisits() {
        if (visits == null)
            visits = dao.getVisits(patientID);
        //TODO: Add this in UserDao, use patientID to find visits(Visit table should have a patientID as foreign key.)
        return visits;
    }

    public Collection<Diagnosis> getDiagnoses(){
        if (diagnoses == null)
            diagnoses = dao.getDiagnoses(patientID);
        return diagnoses;
    }

    public Collection<Medication> getMedications(){
        if (medications == null)
            medications = dao.getMedications(patientID);
        return medications;
    }
}
