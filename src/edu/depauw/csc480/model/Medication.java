package edu.depauw.csc480.model;

import edu.depauw.csc480.dao.MedicationDAO;
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
    int medID;
    private Patient patient;
    private String med_name;


    public Medication(MedicationDAO dao, int medID, String med_name, Patient patient)
    {
        this.dao = dao;
        this.medID = medID;
        this.patient = patient;
        this.med_name = med_name;
    }

    //TODO: Fix this.
    public String toString() {
        return "";
    }

    public int getMedID(){
        return medID;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    //TODO: All the setters need to update in dao. I only set this one as an example.. need to update the others.
    // look at medDao - changeMed_name function...
    public void setMed_name(String med_name) {
        this.med_name = med_name;
        dao.changeMed_name(medID, med_name);

    }

    public String getMed_name(){
        return med_name;
    }
}
