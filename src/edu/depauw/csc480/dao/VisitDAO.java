package edu.depauw.csc480.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        String s = "create table Visit("
                + "visitId int not null, "
                + "patientID not null, "
                + "visit_date timestamp not null";
        + "visit_time timestamp not null";
        + "comments varchar(1000) not null";
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
        String s = "alter table Visit add constraint fk_patientID"
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
            String qry = "select visit_date from Visit where visitID = ?";
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, visitID);
            ResultSet rs = pstmt.executeQuery();

            // return null if department doesn't exist
            if (!rs.next())
                return null;

            String dname = rs.getString("visit_date");
            rs.close();

            Visit visit = new Visit(this, visitID, visit_date);

            return visit;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error finding  Visit ID", e);
        }
    }

    /**
     * Retrieve a Visit object by name. Similar to find(visitID), except it
     * searches by name.
     *
     * @param dname
     * @return the Department object, or null if not found
     */
    public Department findByName(int patientID) {
        try {
            String qry = "select patientID from Visit where visitID = ?";
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setString(1, patientID);
            ResultSet rs = pstmt.executeQuery();

            // return null if department doesn't exist
            if (!rs.next())
                return null;

            int deptid = rs.getInt("patientID");
            rs.close();

            Visit visit = new Visit(this, visitID, patientID);

            return visit;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error finding Visit by Patient ID", e);
        }
    }

    /**
     * Add a new Visit with the given attributes.
     *
     * @param patientID
     * @param visitID
     * @param visit_dame
     * @return the new Department object, or null if key already exists
     */
    public Visit insert(int visitID, String comments, int patientID) {
        try {
            // make sure that the deptid is currently unused
            if (find(deptid) != null)
                return null;

            String cmd = "insert into VISIT(patientID, visitID, visit_date) "
                    + "values(?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(cmd);
            pstmt.setInt(1, patientID);
            pstmt.setString(2, visitID);
            if (head != null) {
                // special handling because the head might be null
                pstmt.setInt(3, visit_date.getVisitID());
            } else {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            }
            pstmt.executeUpdate();

            Patient patient = new Patient(this, patientID, visitID, visit_date);

            return department;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error inserting new Visit", e);
        }
    }

    /**
     * Visit date was changed in the model object, so propagate the change to the
     * database.
     *
     * @param visitID
     * @param visit_date
     *
     */
    public void changeVisit_date(int visitID, Visit visit_date) {
        try {
            String cmd = "update Visit set Visit_date = ? where VisitID = ?";
            PreparedStatement pstmt = conn.prepareStatement(cmd);
            if (head != null) {
                // special handling because the head might be null
                pstmt.setInt(1, visit_date.getVisitID());
            } else {
                pstmt.setNull(1, java.sql.Types.INTEGER);
            }
            pstmt.setInt(2, visitID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error changing Visit_date", e);
        }
    }

    /**
     * Retrieve a Collection of all Visits for a speicifc patient. Backwards
     * direction of patientID foreign key from Faculty.
     *
     * @param patientID
     * @return the Collection
     */
    public Collection<Visit> getVisit(int visitID) {
        try {
            Collection<Visit> visit = new ArrayList<Visit>();
            String qry = "select visitID from PATIENT where patientID = ?";
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, patientID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int visitID = rs.getInt("visitID");
                patient.add(dbm.findPatient(patientID));
            }
            rs.close();
            return visitID;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error getting Patient visits", e);
        }
    }

    /**
     * Retrieve a Collection of all Visits for a specific patient.
     * Backwards direction of Dept foreign key from Course.
     *
     * @param deptid
     * @return the Collection
     */
    public Collection<Visit> getVisit(int visitID) {
        try {
            Collection<Visit> courses = new ArrayList<Visit>();
            String qry = "select  from Visit where VisitID = ?";
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, visitID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int num = rs.getInt("patienID");
                courses.add(dbm.findCourse(visitID, patientID));
            }
            rs.close();
            return visits;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error getting patient visits", e);
        }
    }



    /**
     * Clear all data from the Department table.
     *
     * @throws SQLException
     */
    void clear() throws SQLException {
        Statement stmt = conn.createStatement();
        String s = "delete from Visit";
        stmt.executeUpdate(s);
    }
}
