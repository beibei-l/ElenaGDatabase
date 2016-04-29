package edu.depauw.csc480;

import java.util.Collection;
import java.sql.Timestamp;

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

        // department heads are set to null for now; see below
        Diagnosis cancer = dbm.insertDiagnosis(5, "Cancer", null);
        Diagnosis ebola = dbm.insertDiagnosist(8, "Ebola", null);
        Diagnosis asthma = dbm.insertDiagnosis(10, "Asthma", null);

        Patient Amy = dbm.insertPatient(1234, "Amy Brown", "414 S. Indiana St.");
        Patient John = dbm.insertPatient(2345, "John Green", "1010 Bloomington St.");
        Patient Cheslea = dbm.insertPatient(3456, "Chelsea Furtner", "42 Druley Lane");
        Patient Kate = dbm.insertPatient(4567, "Kate Richter", "813 Washington Ave");
        Patient Jake = dbm.insertPatient(5678, "Jake Johnson", "313 Jackson St.");
        dbm.insertPatient= dbm (6789, "Eric Brunssels", "542 Winter Circle");

        // Have to set department heads after creating faculty,
        // because faculty need to refer to departments (cycle in foreign keys)
        cancer.setDiagnosis(Amy);
        ebola.setDiagnosis(John);
        asthma.setDiagnosis(Chelsea);

        dbm.insertVisit(new Timestamp(System.currentTimeMillis()), 131);
        dbm.insertVisit(310, "12/17/15", 1:30pm);
        dbm.insertVisit(311, "12/17/15", 2:30pm);
        dbm.insertVisit(350, "12/31/15", 10:00am);
        dbm.insertVisit(360, "1/6/16", 3:00pm);
        dbm.insertVisit(380, "1/10/16", 12:45pm);
        dbm.insertVisit(400, "1/12/16", 1:00pm);
        dbm.insertVisit(410, "1/15/16", 1:45pm);

        dbm.commit();

        // Now retrieve a table of MathCS faculty and their courses;
        // each course also lists the head of the department offering the course
        Collection<Visit> visit = visit.getVisit();
        for (Visit visit : Visit) {
            System.out.println(visit);
            Collection<Course> visit = visit.getVisit();
            for (Visit c : visit) {
                System.out.println("Pname" + p + " [Pname: " + p.getPatient().getVisit() + "]");
            }
        }

        dbm.commit();

        dbm.close();

        System.out.println("Done");
    }

