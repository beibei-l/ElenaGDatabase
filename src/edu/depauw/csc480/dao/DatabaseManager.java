package edu.depauw.csc480.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.Properties;

import org.apache.derby.jdbc.EmbeddedDriver;

import edu.depauw.csc480.model.Patient;
import edu.depauw.csc480.model.Visit;
import edu.depauw.csc480.model.Medication;
import edu.depauw.csc480.model.Diagnosis;
/**
 * This class mediates access to the Hospital database,
 * hiding the lower-level DAO objects from the client.
 *
 * @author egonzalez
 */


public class DatabaseManager {
    private Driver driver;
    private Connection conn;
    private PatientDAO patientDAO;
    private VisitDAO visitDAO;
    private MedicationDAO medicationDAO;
    private DiagnosisDAO diagnosisDAO;

    private final String url = "jdbc:derby:HospitalDB";

    public DatabaseManager() {
        driver = new EmbeddedDriver();

        Properties prop = new Properties();
        prop.put("create", "false");

        // try to connect to an existing database
        try {
            conn = driver.connect(url, prop);
            conn.setAutoCommit(false);
        }
        catch(SQLException e) {
            // database doesn't exist, so try creating it
            try {
                prop.put("create", "true");
                conn = driver.connect(url, prop);
                conn.setAutoCommit(false);
                create(conn);
            }
            catch (SQLException e2) {
                throw new RuntimeException("cannot connect to database", e2);
            }
        }

        patientDAO = new PatientDAO(conn, this);
        visitDAO = new VisitDAO(conn, this);
        medicationDAO = new MedicationDAO(conn, this);
        diagnosisDAO = new DiagnosisDAO(conn,this);
    }

    /**
     * Initialize the tables and their constraints in a newly created database
     *
     * @param conn
     * @throws SQLException
     */
    private void create(Connection conn) throws SQLException {
        PatientDAO.create(conn);
        VisitDAO.create(conn);
        MedicationDAO.create(conn);
        DiagnosisDAO.create(conn);
//        PatientDAO.addConstraints(conn);
        VisitDAO.addConstraints(conn);
        MedicationDAO.addConstraints(conn);
        DiagnosisDAO.addConstraints(conn);
        conn.commit();
    }

    //***************************************************************
    // Data retrieval functions -- find a model object given its key

    //TODO: Find the object using primary key (id), no need to put in any other parameters.
    public Patient findPatient(int patientID) {
        return patientDAO.find(patientID);
    }
    public Patient findPatientByName(String patientName) {
        return patientDAO.findByName(patientName);
    }

    public Visit findVisit(int visitID) {
        return visitDAO.find(visitID);
    }

    public Medication findMedication(int medId) {
        return medicationDAO.find(medId);
    }
    public Medication findMedicationByName(String medication_name) {
        return medicationDAO.findByName(medication_name);
    }

    public Diagnosis findDiagnosis(int diagnosisID) {
        return diagnosisDAO.find(diagnosisID);
    }
    //TODO: need a find by name method in DiagnosisDao class.
//    public Diagnosis findDiagnosisByName(String dname) {
//        return diagnosisDAO.findByName(dname);
////    }


    //***************************************************************
    // Data insertion functions -- create new model object from attributes

    //TODO: match the parameters in insert, and model object constructors.
    //TODO: insertVisit() - this file, visitDao.insert() - VisitDAO, new Visit() - Visit
//    Visit(this, visitID, visit_date, visit_time, comments, patient);
    public Visit insertVisit(int visitID, Date visit_date, Time visit_time, String comments, Patient patient) {
        java.sql.Date  sql_visit_date = new java.sql.Date(visit_date.getDate()); //convert util.Date to sql.Date
        java.sql.Time sql_visit_time = new java.sql.Time(visit_time.getTime());

        return visitDAO.insert(visitID, sql_visit_date, sql_visit_time, comments, patient);
    }
    public Patient insertPatient(int patientID, String pname, String address, int demographic, String email) {
        return patientDAO.insert(patientID, pname, address, demographic, email);
    }

    public Medication insertMedication(int medID, String med_name, Patient patient) {
        return medicationDAO.insert(medID, med_name, patient);
    }

    public Diagnosis insertDiagnosis(int diagnosisID, String diagnosisContent, Patient patient) {
        return diagnosisDAO.insert(diagnosisID, diagnosisContent, patient);

    }

    //***************************************************************
    // Utility functions

    /**
     * Commit changes since last call to commit
     */
    public void commit() {
        try {
            conn.commit();
        }
        catch(SQLException e) {
            throw new RuntimeException("cannot commit database", e);
        }
    }

    /**
     * Abort changes since last call to commit, then close connection
     */
    public void cleanup() {
        try {
            conn.rollback();
            conn.close();
        }
        catch(SQLException e) {
            System.out.println("fatal error: cannot cleanup connection");
        }
    }

    /**
     * Close connection and shutdown database
     */
    public void close() {
        try {
            conn.close();
        }
        catch(SQLException e) {
            throw new RuntimeException("cannot close database connection", e);
        }

        // Now shutdown the embedded database system -- this is Derby-specific
        try {
            Properties prop = new Properties();
            prop.put("shutdown", "true");
            conn = driver.connect(url, prop);
        } catch (SQLException e) {
            // This is supposed to throw an exception...
            System.out.println("Derby has shut down successfully");
        }
    }

    /**
     * Clear out all data from database(but leave empty tables)
     */
    public void clearTables() {
        try {
            // This is not as straightforward as it may seem, because
            // of the cyclic foreign keys -- I had to play with
            // "on delete set null" and "on delete cascade" for a bit
            patientDAO.clear();
            visitDAO.clear();
            medicationDAO.clear();
            diagnosisDAO.clear();
        } catch (SQLException e) {
            throw new RuntimeException("cannot clear tables", e);
        }
    }
}
