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
 * Data Access Object for the Patient table.
 * Encapsulates all of the relevant SQL commands.
 * Based on Sciore, Section 9.1.
 *
 * @author egonzalez
 */
public class PatientDAO {
    private Connection conn;
    private DatabaseManager dbm;

    public PatientDAO(Connection conn, DatabaseManager dbm) {
        this.conn = conn;
        this.dbm = dbm;
    }

    /**
     * Create the Patient table via SQL
     *
     * @param conn
     * @throws SQLException
     */
    static void create(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String s = "create table PATIENT("
                + "patientId int not null, "
                + "patientName varchar(100) not null, "
                + "address varchar(500) not null, "
                + "demographic varchar(100) not null, "
                + "email varchar(100) not null,";
        stmt.executeUpdate(s);
    }

    /**
     * Modify the Patient table to add foreign key constraints (needs to happen
     * after the other tables have been created)
     *
     * @param conn
     * @throws SQLException
     */
    static void addConstraints(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String s = "alter table PATIENT add constraint fk_patientID"
                + "foreign key(patientID) references PATIENT";
        stmt.executeUpdate(s);
    }

    /**
     * Retrieve a Faculty object given its key.
     *
     * @param ssn
     * @return the Faculty object, or null if not found
     */
    public Patient find(int patientID) {
        try {
            String qry = "select patientName, p from PATIENT where patientID = ?";
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, patientID);
            ResultSet rs = pstmt.executeQuery();

            // return null if faculty doesn't exist
            if (!rs.next())
                return null;

            String patientName = rs.getString("patientName");
            String address = rs.getString("address")
            String email = rs.getString("email");
            int demographic = rs.getInt("demographic");
            rs.close();

            Patient patient = new Patient(this, patientName, address, email, demographic);

            return patient;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error finding patient", e);
        }
    }

    /**
     * Retrieve a Patient object by name. Similar to find(pname), except it
     * searches by name.
     *
     * @param patientName
     * @return the Patient object, or null if not found
     */
    public Patient findByName(String patientName) {
        try {
            String qry = "select patientID, pname, pname from PATIENT where pname = ?";
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setString(1, pname);
            ResultSet rs = pstmt.executeQuery();

            // return null if patient doesn't exist
            if (!rs.next())
                return null;

            int patientID = rs.getInt("patientID");
            String pname = rs.getString("pname");
            rs.close();

            Patient patient = dbm.findPatient(pname);
            Patient patient = new Patient(this, patientID, pname, address, demographic, email);

            return patient;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error finding patient by name", e);
        }
    }

    /**
     * Add a new Patient with the given attributes.
     *
     * @param patientID
     * @param pname
     * @param demographic
     * @param address
     * @param email
     * @return the new Patient object, or null if key already exists
     */
    public Patient insert(int patientID, String pname, String demographic, String address, String
            email) {
        try {

            if (find(patientID) != null)
                return null;

            String cmd = "insert into PATIENT(patientID, pname, demogrpahic, address, email) "
                    + "values(?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(cmd);
            pstmt.setInt(1, patientID);
            pstmt.setString(2, pname);
            pstmt.setString(3, demographic);
            pstmt.setInt(4, address);
            pstmt.setInt(5, email)
            pstmt.executeUpdate();

            Patient patient = new Patient(this, patientID, pname, demogrpahic, address, email);

            return patient;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error inserting new Patient", e);
        }
    }

    /**
     * Address was changed in the model object, so propagate the change to the
     * database.
     *
     * @param patientID
     * @param address
     */

    public void changePatient(int patientID , String address) {
        try {
            String cmd = "update PATIENT set Address = ? where Patient = ?";
            PreparedStatement pstmt = conn.prepareStatement(cmd);
            pstmt.setString(1, address);
            pstmt.setInt(2, patientID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error changing address", e);
        }
    }

    /**
     * Visit was changed in the model object, so propagate the change to
     * the database.
     *
     * @param patientID
     * @param visitID
     */
    public void changeVisit(int patient, ) {
        try {
            String cmd = "update VISIT set vistId = ? where patientID = ?";
            PreparedStatement pstmt = conn.prepareStatement(cmd);
            pstmt.setInt(1, visit.getVisitId());
            pstmt.setInt(2, patientID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error changing visit", e);
        }
    }

    /**
     * Retrieve a Collection of all Visits for a given Patient.
     *
     * @param ssn
     * @return
     */
    public Collection<Visit> getVisit(int visitID) {
        try {
            Collection<Visit> visit = new ArrayList<Visit>();
            String qry = "select visitID, visit_time, visit_date from VISIT where patientID = ?";
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, patientID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int patient = rs.getInt("patientID");
                int visit = rs.getInt("visitID");
                courses.add(dbm.findVisit(patientID, visitID));
            }
            rs.close();
            return visit;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error getting patient visits", e);
        }
    }


    /**
     * Clear all data from the Patient table.
     *
     * @throws SQLException
     */
    void clear() throws SQLException {
        Statement stmt = conn.createStatement();
        String s = "delete from Patient";
        stmt.executeUpdate(s);
    }
}
