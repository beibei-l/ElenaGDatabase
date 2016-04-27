package edu.depauw.csc480.model;

import edu.depauw.csc480.dao.VisitDAO;

/**
 * Model object for a row in the Medication table.
 * Accesses the underlying database through a MedicationDAO.
 * Based on Sciore, Section 9.1.
 *
 * @author egonzalez
 */
public class Medication {
    private MedicationDAO dao;
    private Patient patient;
    private int medID;
    private int patientID;
    private String med_name;


    public Medication(MedicationDAO dao, Patient patient, int medID, int patientId, String
            med_name)
    {
        this.dao = dao;
        this.patientID = patientID;
        this.comments = comments;
    }

    public String toString() {
        return dao + " " + patientID + ": " + medID + ", " + "Comments: \n" + + med_name;
    }

    public int MedicationID() {
        return medID;
    }

    public void setMedicationID(String med_name) {
        this.MedicationID = medID;
    }


    public int getPatientID(int patientID)
    {
        return patientID;
    }

    public void setPatientID()
    {
        this.PatientID = patientID;
    }

    public void setMed_name()
    { this.Med_name = med_name;
    }

    public String getMed_name(){
        return med_name;
    }
}
