package edu.depauw.csc480;

import java.util.Collection;

import edu.depauw.csc480.dao.DatabaseManager;
import edu.depauw.csc480.model.Patient;
import edu.depauw.csc480.model.Visit;
import edu.depauw.csc480.model.Diagnosis;
import edu.depauw.csc480.model.Medication;


/**
 * Simple client that retrieves data from an already created database.
 * Running this after Test will check that the same data may be retrieved
 * from the database and not just from the in-memory cache.
 *
 * @author egonzalez
 */
public class Test2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        DatabaseManager dbm = new DatabaseManager();

        Patient patient = dbm.findPatientByName("Amy Brown");

        // Now retrieve a table of Patients and their Visits;

        Collection<Visit> visit = patient.Visit();
        for (Visit visit : visit) {
            System.out.println(visit);
            Collection<Visit> visit = visit.getVisit();
            for (Visit p : visit) {
                System.out.println("pname" + p +  " [Pname: " + p.getPatient().getVisit() + "]");
            }
        }

        dbm.commit();

        dbm.close();

        System.out.println("Done");
    }

}



