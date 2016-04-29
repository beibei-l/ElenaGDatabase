package edu.depauw.csc480;

import java.util.Collection;
import java.sql.Timestamp;
import java.util.Date;

import edu.depauw.csc480.dao.DatabaseManager;
import edu.depauw.csc480.model.Patient;
import edu.depauw.csc480.model.Visit;
import edu.depauw.csc480.model.Diagnosis;
import edu.depauw.csc480.model.Medication;

/**
 * Simple client that inserts sample data then runs a query.
 *
 * @author egonzalez
 */
public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        DatabaseManager dbm = new DatabaseManager();

        dbm.clearTables();

        Patient amy = dbm.insertPatient(1, "Amy Brown", "414 S. Indiana St.", 1, "amybrown@gmail.com");
//        Patient John = dbm.insertPatient(2345, "John Green", "1010 Bloomington St.");
//        Patient Cheslea = dbm.insertPatient(3456, "Chelsea Furtner", "42 Druley Lane");
//        Patient Kate = dbm.insertPatient(4567, "Kate Richter", "813 Washington Ave");
//        Patient Jake = dbm.insertPatient(5678, "Jake Johnson", "313 Jackson St.");

        //AMY got everything...
        Diagnosis cancer = dbm.insertDiagnosis(5, "Cancer", amy);
        Diagnosis ebola = dbm.insertDiagnosis(8, "Ebola", amy);
        Diagnosis asthma = dbm.insertDiagnosis(10, "Asthma", amy);

        //Set up a new visit for Amy..
        Visit visit1 = dbm.insertVisit(1, new Date(), new java.sql.Time(new java.util.Date().getTime()), "new patient visit", amy);

        dbm.commit();

        // Now retrieve a table of MathCS faculty and their courses;
        // each course also lists the head of the department offering the course
        Collection<Visit> visits = amy.getVisits();
        for (Visit visit : visits) {
            System.out.println(visit);
            System.out.println("Patient Name:" + visit.getPatient().getPname() +
                                "Visit:" + visit.toString());

        }

        dbm.commit();

        dbm.close();

        System.out.println("Done");
    }
}

