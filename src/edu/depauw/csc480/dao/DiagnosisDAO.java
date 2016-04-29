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
 * Data Access Object for the Diagnosis table.
 * Encapsulates all of the relevant SQL commands.
 * Based on Sciore, Section 9.1.
 *
 * @author egonzalez
 */
public class DiagnosisDAO {
    private Connection conn;
    private DatabaseManager dbm;

    public DiagnosisDAO(Connection conn, DatabaseManager dbm) {
        this.conn = conn;
        this.dbm = dbm;
    }

    /**
     * Create the Diagnosis table via SQL
     *
     * @param conn
     * @throws SQLException
     */
    static void create(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String s = "create table DIAGNOSIS("
                + "diagnosisID int not null, "
                + "diagnosis varchar(500) not null, "
                + "patientID int not null, "
                + "primary key(diagnosisID))";  //Set the primary key here.
        stmt.executeUpdate(s);
    }

    /**
     * Modify the Diagnosis table to add foreign key constraints (needs to happen
     * after the other tables have been created)
     *
     * @param conn
     * @throws SQLException
     */
    static void addConstraints(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String s = "alter table DIAGNOSIS add constraint fk_diagnosispatient "
                + "foreign key(patientID) references PATIENT on delete cascade";
        stmt.executeUpdate(s);
    }

    /**
     * Retrieve a Diagnosis object given its key.
     *
     * @param diagnosisID
     * @return the Diagnosis object, or null if not found
     */
    public Diagnosis find(int diagnosisID) {
        try {
            // Primary key is diagnosisID, only need this to find the entry.
            String qry = "select diagnosis, patientID from DIAGNOSIS where diagnosisID = ? ";
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, diagnosisID);
            ResultSet rs = pstmt.executeQuery();

            // return null if diagnosis doesn't exist
            if (!rs.next())
                return null;

            String diagnosisContent = rs.getString("diagnosis");
            int patientID = rs.getInt("patientID");
            rs.close();

            Patient patient = dbm.findPatient(patientID);
            Diagnosis diagnosis = new Diagnosis(this, diagnosisID, diagnosisContent, patient);

            return diagnosis;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error finding diagnosis", e);
        }
    }

    /**
     * Add a new Diagnosis with the given attributes.
     *
     * @param diagnosisID
     * @param diagnosisContent
     * @param patient
     * @return the new Diagnosis object, or null if key already exists
     */


    public Diagnosis insert(int diagnosisID, String diagnosisContent, Patient patient) {
        try {
            // make sure that the diagnosis, diagnosisID pair is currently unused
            if (find(diagnosisID) != null)
                return null;

            String cmd = "insert into DIAGNOSIS(diagnosisID, diagnosis, patientID) "
                    + "values(?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(cmd);
            pstmt.setInt(1, diagnosisID);
            pstmt.setString(2, diagnosisContent);
            pstmt.setInt(3, patient.getPatientID());
            pstmt.executeUpdate();

            Diagnosis diagnosis = new Diagnosis(this, diagnosisID, diagnosisContent, patient);

            return diagnosis;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error inserting new Diagnosis", e);
        }
    }

    /**
     * Diagnosis was changed in the model object, so propagate the change to the
     * database.
     *
     * @param diagnosisID
     * @param diagnosisContent
     */
    public void changeDiagnosis(int diagnosisID, String diagnosisContent) {
        try {
            String cmd = "update DIAGNOSIS set diagnosis = ? where diagnosisID = ?";
            PreparedStatement pstmt = conn.prepareStatement(cmd);
            pstmt.setString(1, diagnosisContent);
            pstmt.setInt(2, diagnosisID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error changing diagnosis", e);
        }
    }

        /**
         * Clear all data from the Diagnosis table.
         *
         * @throws SQLException
         */
    void clear() throws SQLException {
        Statement stmt = conn.createStatement();
        String s = "delete from DIAGNOSIS";
        stmt.executeUpdate(s);
    }
}
