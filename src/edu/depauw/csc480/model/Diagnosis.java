package edu.depauw.csc480.model;

import java.util.Collection;

import edu.depauw.csc480.dao.DiagnosisDAO;

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
    private String diagnosis;
    private Patient patient;

    //TODO: does this mean a diagnosis has a lot of patients?
    private Collection<Patient> patients;
    private Collection<Visit> visit;

    public Diagnosis(DiagnosisDAO dao, int diagnosisID, String diagnosis, Patient patient) {
        this.dao = dao;
        this.diagnosisID = diagnosisID;
        this.diagnosis = diagnosis;
        this.patient = patient;
    }

    //TODO:
    public String toString() {
        return dao + diagnosis;
    }

    public int getDiagnosisID() {
        return diagnosisID;
    }

    public Patient getPatient(){
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis)
    {
        this.diagnosis = diagnosis;
    }

    //TODO: Ill check back on this later...
//    public Collection<Patient> getPatients() {
//        if (patient == null) patient = dao.getPatient(patientID);
//        return patients;
//    }
//
//    public Collection<Visit> getVisit() {
//        if (visit == null) courses = dao.getVisit(visitID);
//        return visit;
//    }
}
