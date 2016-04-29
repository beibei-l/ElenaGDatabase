package edu.depauw.csc480.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import edu.depauw.csc480.model.Patient;
import edu.depauw.csc480.model.Visit;
import edu.depauw.csc480.model.Diagnosis;
import edu.depauw.csc480.model.Medication;

/**
 * Data Access Object for the Department table.
 * Encapsulates all of the relevant SQL commands.
 * Based on Sciore, Section 9.1.
 *
 * @author egonzalez
 */
public class VisitDAO {
    private Connection conn;
    private DatabaseManager dbm;

    public VisitDAO(Connection conn, DatabaseManager dbm) {
        this.conn = conn;
        this.dbm = dbm;
    }

    /**
     * Create the Department table via SQL
     *
     * @param conn
     * @throws SQLException
     */
    static void create(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String s = "create table VISIT("
                + "visitID int not null, "
                + "visit_date DATE not null, "
                + "visit_time TIME not null"
                + "comments varchar(1000) not null"
                + "patientID not null, "
                + "primary key(visitID))";
        stmt.executeUpdate(s);
    }

    /**
     * Modify the Department table to add foreign key constraints (needs to
     * happen after the other tables have been created)
     *
     * @param conn
     * @throws SQLException
     */
    static void addConstraints(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String s = "alter table VISIT add constraint fk_patientID"
                + "foreign key(patientID) references PATIENT on delete cascade";
        stmt.executeUpdate(s);
    }

    /**
     * Retrieve a Department object given its key.
     *
     *
     * @param visitID
     * @return the Visit object, or null if not found
     */
    public Visit find(int visitID) {
        try {
            String qry = "select visit_date, visit_time, comments, patientID from Visit where visitID = ?";
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, visitID);
            ResultSet rs = pstmt.executeQuery();

            // return null if department doesn't exist
            if (!rs.next())
                return null;

            Date visit_date = rs.getDate("visit_date");
            Time visit_time = rs.getTime("visit_time");
            String comments = rs.getString("comments");
            int patientID = rs.getInt("patientID");
            rs.close();

            Patient patient = dbm.findPatient(patientID);
            Visit visit = new Visit(this, visitID, visit_date, visit_time, comments, patient);

            return visit;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error finding visit", e);
        }
    }

    //TODO: this should be in the PatientDAO?
//    /**
//     * Retrieve a Visit object by name. Similar to find(visitID), except it
//     * searches by name.
//     *
//     * @return the Department object, or null if not found
//     */
//    public Department findByName(int patientID) {
//        try {
//            String qry = "select patientID from Visit where visitID = ?";
//            PreparedStatement pstmt = conn.prepareStatement(qry);
//            pstmt.setString(1, patientID);
//            ResultSet rs = pstmt.executeQuery();
//
//            // return null if department doesn't exist
//            if (!rs.next())
//                return null;
//
//            int deptid = rs.getInt("patientID");
//            rs.close();
//
//            Visit visit = new Visit(this, visitID, patientID);
//
//            return visit;
//        } catch (SQLException e) {
//            dbm.cleanup();
//            throw new RuntimeException("error finding Visit by Patient ID", e);
//        }
//    }

    /**
     * Add a new Visit with the given attributes.
     *
     * @param visitID
     * @param visit_date
     * @param visit_time
     * @param comments
     * @param patient
     * @return the new Visit object, or null if key already exists
     */
    public Visit insert(int visitID, Date visit_date, Time visit_time, String comments, Patient patient) {
        try {
            // make sure that the visitID is currently unused
            if (find(visitID) != null)
                return null;

            String cmd = "insert into VISIT(visitID, visit_date, visit_time, comments, patientID) "
                    + "values(?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(cmd);

            java.sql.Date  sql_visit_date = new java.sql.Date(visit_date.getDate()); //convert util.Date to sql.Date
            java.sql.Time sql_visit_time = new java.sql.Time(visit_time.getTime());

            pstmt.setInt(1, visitID);
            pstmt.setDate(2, sql_visit_date);
            pstmt.setTime(3, sql_visit_time);
            pstmt.setString(4, comments);
            pstmt.setInt(5, patient.getPatientID());

            pstmt.executeUpdate();

            Visit visit = new Visit(this, visitID, visit_date, visit_time, comments, patient);

            return visit;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error inserting new Visit", e);
        }
    }

    //TODO:....Um.. Later.
//    /**
//     * Visit date was changed in the model object, so propagate the change to the
//     * database.
//     *
//     * @param visitID
//     * @param visit_date
//     *
//     */
//    public void changeVisit_date(int visitID, Visit visit_date) {
//        try {
//            String cmd = "update Visit set visit_date = ? where VisitID = ?";
//            PreparedStatement pstmt = conn.prepareStatement(cmd);
//            if (head != null) {
//                // special handling because the head might be null
//                pstmt.setInt(1, visit_date.getVisitID());
//            } else {
//                pstmt.setNull(1, java.sql.Types.INTEGER);
//            }
//            pstmt.setInt(2, visitID);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            dbm.cleanup();
//            throw new RuntimeException("error changing Visit_date", e);
//        }
//    }


    /**
     * Clear all data from the Department table.
     *
     * @throws SQLException
     */
    void clear() throws SQLException {
        Statement stmt = conn.createStatement();
        String s = "delete from VISIT";
        stmt.executeUpdate(s);
    }
}
