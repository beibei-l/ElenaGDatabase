package edu.depauw.csc480.model;

import java.util.Collection;

import edu.depauw.csc480.dao.DepartmentDAO;

/**
 * Model object for a row in the Department table.
 * Accesses the underlying database through a DepartmentDAO.
 * Based on Sciore, Section 9.1.
 *
 * @author egonzalez
 */
public class Diagnosis {
    private DiagnosisDAO dao;
    private int diagnosisID;
    private int patientID;
    private String Diagnosis;
    private Collection<Patient> patient;
    private Collection<Visit> visit;

    public Diagnosis(DiagnosisDAO dao, int diagnosisID, String Diagnosis, int patientID) {
        this.dao = dao;
        this.diagnosisID = diagnosisID;
        this.dname = dname;
    }

    public String toString() {
        return dao "," + diagnosis "," + diagnosisID "," + dname;
    }

    public int getDiagnosisID() {
        return diagnosisID;
    }

    pubic void setDiagnosisID(int diagnosisID)
    {
        this.DiagnosisID = diagnosisID;
    }

    public String getPatientID()
    {
        return patientID;
    }

    public void setPatientID()
    {
        this.PatientID = patientID;
    }


    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis)
    {
        this.diagnosis = diagnosis;
    }

    public Collection<Patient> getPatient() {
        if (patient == null) patient = dao.getPatient(patientID);
        return patient;
    }

    public Collection<Visit> getVisit() {
        if (visit == null) courses = dao.getVisit(visitID);
        return visit;
    }
}
