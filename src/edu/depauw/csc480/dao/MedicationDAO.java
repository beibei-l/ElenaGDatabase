package edu.depauw.csc480.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.depauw.csc480.model.Patient;
import edu.depauw.csc480.model.Visit;
import edu.depauw.csc480.model.Diagnosis;
import edu.depauw.csc480.model.Medication;

/**
 * Data Access Object for the Medicine table.
 * Encapsulates all of the relevant SQL commands.
 * Based on Sciore, Section 9.1.
 *
 * @author egonzalez
 */
public class MedicationDAO {
    private Connection conn;
    private DatabaseManager dbm;

    public MedicationDAO(Connection conn, DatabaseManager dbm) {
        this.conn = conn;
        this.dbm = dbm;
    }

    /**
     * Create the Medication table via SQL
     *
     * @param conn
     * @throws SQLException
     */
    static void create(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String s = "create table MEDICATION("
                + "medID int not null, "
                + "med_name varchar(100) not null, "
                + "patientID int not null, "
                + "primary key(medID))";
        stmt.executeUpdate(s);
    }

    /**
     * Modify the Medication table to add foreign key constraints (needs to happen
     * after the other tables have been created)
     *
     * @param conn
     * @throws SQLException
     */
    static void addConstraints(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String s = "alter table MEDICATION add constraint fk_medpatient "
                + "foreign key(patientID) references PATIENT";
        stmt.executeUpdate(s);
    }

    /**
     * Retrieve a Medication object given its key.
     *
     * @param medID
     * @return the Medication object, or null if not found
     */
    public Medication find(int medID) {
        try {
            String qry = "select med_name, patientID from PATIENT where medID = ?";
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, medID);
            ResultSet rs = pstmt.executeQuery();

            // return null if medication doesn't exist
            if (!rs.next())
                return null;

            String med_name = rs.getString("med_name");
            int patientID = rs.getInt("patientID");
            rs.close();

            Patient patient = dbm.findPatient(patientID);
            Medication medication = new Medication(this, medID, med_name, patient);

            return medication;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error finding medication ID", e);
        }
    }

    /**
     * Retrieve a Medication object by name. Similar to find(medID), except it
     * searches by name.
     *
     * @param med_name
     * @return the Medication object, or null if not found
     */
    public Medication findByName(String med_name) {
        try {
            String qry = "select medID, patientID from MEDICATION where med_name = ?";
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setString(1, med_name);
            ResultSet rs = pstmt.executeQuery();

            // return null if faculty member doesn't exist
            if (!rs.next())
                return null;

            int medID = rs.getInt("medID");
            int patientID = rs.getInt("patientID");
            rs.close();

            Patient patient = dbm.findPatient(patientID);
            Medication medication = new Medication(this, medID, med_name, patient);

            return medication;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error finding medication by name", e);
        }
    }

    /**
     * Add a new Medication object with the given attributes.
     *
     * @param medID
     * @param med_name
     * @param patient

     * @return the new Medication object, or null if key already exists
     */
    public Medication insert(int medID, String med_name, Patient patient) {
        try {
            // make sure that the medID is currently unused
            if (find(medID) != null)
                return null;

            String cmd = "insert into MEDICATION(medID, med_name, patientID) "
                    + "values(?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(cmd);
            pstmt.setInt(1, medID);
            pstmt.setString(2, med_name);
            pstmt.setInt(3, patient.getPatientID());
            pstmt.executeUpdate();

            Medication medication = new Medication(this, medID, med_name, patient);

            return medication;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error inserting new medication", e);
        }
    }

//TODO: This should probably be in PatientDAO?, or ..change medication name instead?..
//    /**
//     * Medication was changed in the Medication object, so propagate the change to
//     * the database.
//     *
//     * @param medID
//     * @param patientID
//     */
//    public void changeMedication(int medID, int patientID) {
//        try {
//            String cmd = "update MEDICATION set patientID = ? where medID = ?";
//            PreparedStatement pstmt = conn.prepareStatement(cmd);
//            pstmt.setInt(1, medication.getMedID());
//            pstmt.setInt(2, patientID);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            dbm.cleanup();
//            throw new RuntimeException("error changing medication", e);
//        }
//    }


    //TODO: add change Med name and change patient later.
    /**
     * Clear all data from the Medication table.
     *
     * @throws SQLException
     */
    void clear() throws SQLException {
        Statement stmt = conn.createStatement();
        String s = "delete from MEDICATION";
        stmt.executeUpdate(s);
    }
}
