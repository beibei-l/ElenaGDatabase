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
                + "patientID int not null, "
                + "diagnosisID integer not null, "
                + "diagnosis varchar(500) not null, "
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
        String s = "alter table DIAGNOSIS add constraint fk_patientID "
                + "foreign key(patientID) references PATIENT on delete cascade";
        stmt.executeUpdate(s);
    }

    /**
     * Retrieve a Diagnosis object given its key.
     *
     * @param patientID
     * @param diagnosisID
     * @return the Diagnosis object, or null if not found
     */
    public Diagnosis find(int patientID, int diagnosisID) {
        try {
            String qry = "select diagnosis, diagnosisID from DIAGNOSIS where diagnosisID = ? and PatientID = ?";
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, patientID);
            pstmt.setInt(2, diagnosisID);
            ResultSet rs = pstmt.executeQuery();

            // return null if diagnosis doesn't exist
            if (!rs.next())
                return null;

            String diagnosis = rs.getString("diagnosis");
            int diagnosisID = rs.getInt("patientID");
            rs.close();

            Visit visit = dbm.find(visitID);
            Patient patient = dbm.findPatient(patientID);
            Diagnosis diagnosis = new Diagnosis(this, diagnosisID, patientID, diagnosis);

            return diagnosis;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error finding diagnosis", e);
        }
    }

    /**
     * Add a new Diagnosis with the given attributes.
     *
     * @param patientID
     * @param diagnosisID
     * @param diagnosis
     * @return the new Diagnosis object, or null if key already exists
     */


    public Diagnosis insert(int diagnosisID, int patientID, String diagnosis) {
        try {
            // make sure that the diagnosis, diagnosisID pair is currently unused
            if (find(diagnosis.getDiagnosisID(), num) != null)
                return null;

            String cmd = "insert into DIAGNOSIS(diagnosisID, patientID, diagnosis) "
                    + "values(?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(cmd);
            pstmt.setInt(1, .getDiagnosisID());
            pstmt.setInt(2, patientID;
            pstmt.setString(3, diagnosis);
            pstmt.executeUpdate();

            Diagnosis diagnosis = new Diagnosis(this, diagnosisID, patientID, diagnosis);

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
     * @param patientID
     * @param diagnosisID
     * @param diagnosis
     */
    public void changeDiagnosis(int diagnosisID, int patientID, String diagnosis) {
        try {
            String cmd = "update DIAGNOSIS set diagnosis = ? where patientID = ? and  diagnosisID = ?";
            PreparedStatement pstmt = conn.prepareStatement(cmd);
            pstmt.setString(1, diagnosis);
            pstmt.setInt(2, diagnosis.getPatientID());
            pstmt.setInt(3, diagnosisID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error changing diagnosis", e);
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
