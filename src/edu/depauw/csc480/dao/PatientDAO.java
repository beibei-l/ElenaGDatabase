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
                + "demographic int not null, "
                + "email varchar(100) not null,"
                + "primary key(patientId))";
        stmt.executeUpdate(s);
    }

//    /**
//     * Modify the Patient table to add foreign key constraints (needs to happen
//     * after the other tables have been created)
//     *
//     * @param conn
//     * @throws SQLException
//     */
//    static void addConstraints(Connection conn) throws SQLException {
//        Statement stmt = conn.createStatement();
//        String s = "alter table PATIENT add constraint fk_patientID"
//                + "foreign key(patientID) references PATIENT";
//        stmt.executeUpdate(s);
//    }

    /**
     * Retrieve a Faculty object given its key.
     *
     * @return the Faculty object, or null if not found
     */
    public Patient find(int patientID) {
        try {
            String qry = "select patientName, address, demographic, email from PATIENT where patientID = ?";
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, patientID);
            ResultSet rs = pstmt.executeQuery();

            // return null if faculty doesn't exist
            if (!rs.next())
                return null;

            String patientName = rs.getString("patientName");
            String address = rs.getString("address");
            int demographic = rs.getInt("demographic");
            String email = rs.getString("email");

            rs.close();

            Patient patient = new Patient(this, patientID, patientName, address, demographic, email);

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
            String qry = "select patientID, address, demographic, email from PATIENT where patientName = ?"; //TODO: What if the patient name is repetitive?
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setString(1, patientName);
            ResultSet rs = pstmt.executeQuery();

            // return null if patient doesn't exist
            if (!rs.next())
                return null;

            //Here fill in all the information you have selected...
            int patientID = rs.getInt("patientID");
            String address = rs.getString("address");
            int demographic = rs.getInt("demographic");
            String email = rs.getString("email");

            rs.close();

            Patient patient = new Patient(this, patientID, patientName, address, demographic, email);

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
    public Patient insert(int patientID, String pname, String address, int demographic,  String email) {
        try {

            if (find(patientID) != null)
                return null;

            String cmd = "insert into PATIENT(patientID, patientName, address, demographic, email) "
                    + "values(?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(cmd);
            pstmt.setInt(1, patientID);
            pstmt.setString(2, pname);
            pstmt.setString(3, address);
            pstmt.setInt(4, demographic);
            pstmt.setString(5, email);
            pstmt.executeUpdate();

            Patient patient = new Patient(this, patientID, pname, address, demographic, email);

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

    public void changeAddress(int patientID , String address) {
        try {
            String cmd = "update PATIENT set address = ? where patientID = ?";
            PreparedStatement pstmt = conn.prepareStatement(cmd);
            pstmt.setString(1, address);
            pstmt.setInt(2, patientID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error changing address", e);
        }
    }

    public void changeEmail(int patientID , String email) {
        try {
            String cmd = "update PATIENT set email = ? where patientID = ?";
            PreparedStatement pstmt = conn.prepareStatement(cmd);
            pstmt.setString(1, email);
            pstmt.setInt(2, patientID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error changing email", e);
        }
    }

    public void changePatientName(int patientID , String patientName) {
        try {
            String cmd = "update PATIENT set patientName = ? where patientID = ?";
            PreparedStatement pstmt = conn.prepareStatement(cmd);
            pstmt.setString(1, patientName);
            pstmt.setInt(2, patientID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error changing patientName", e);
        }
    }

    public void changeDemographic(int patientID , int demographic) {
        try {
            String cmd = "update PATIENT set demographic = ? where patientID = ?";
            PreparedStatement pstmt = conn.prepareStatement(cmd);
            pstmt.setInt(1, demographic);
            pstmt.setInt(2, patientID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error changing demographic", e);
        }
    }


    //TODO: This should be in VisitDAO.
//    /**
//     * Visit was changed in the model object, so propagate the change to
//     * the database.
//     *
//     * @param patientID
//     * @param visitID
//     */
//    public void changeVisit(int patient, ) {
//        try {
//            String cmd = "update VISIT set vistId = ? where patientID = ?";
//            PreparedStatement pstmt = conn.prepareStatement(cmd);
//            pstmt.setInt(1, visit.getVisitId());
//            pstmt.setInt(2, patientID);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            dbm.cleanup();
//            throw new RuntimeException("error changing visit", e);
//        }
//    }




    //Find all the Visits of a patient using patientID...
    public Collection<Visit> getVisits(int patientID) {
        try {
            Collection<Visit> visits = new ArrayList<Visit>();
            String qry = "select visitID from VISIT where patientID = ?";
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, patientID);
            ResultSet rs = pstmt.executeQuery();
            // Find all the visits for this patient, and add them to the visits arraylist.
            while (rs.next()) {
                int visitID = rs.getInt("visitID");
                visits.add(dbm.findVisit(visitID)); //Add Visits, not visitID.
            }
            rs.close();
            return visits;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error getting visits", e);
        }
    }

    public Collection<Diagnosis> getDiagnoses(int patientID) {
        try {
            Collection<Diagnosis> diagnoses = new ArrayList<Diagnosis>();
            String qry = "select diagnosisID from DIAGNOSIS where patientID = ?";
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, patientID);
            ResultSet rs = pstmt.executeQuery();
            // Find all the visits for this patient, and add them to the visits arraylist.
            while (rs.next()) {
                int diagnosisID = rs.getInt("diagnosisID");
                diagnoses.add(dbm.findDiagnosis(diagnosisID));
            }
            rs.close();
            return diagnoses;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error getting diagnoses", e);
        }
    }

    public Collection<Medication> getMedications(int patientID) {
        try {
            Collection<Medication> medications = new ArrayList<Medication>();
            String qry = "select medicationID from MEDICATION where patientID = ?";
            PreparedStatement pstmt = conn.prepareStatement(qry);
            pstmt.setInt(1, patientID);
            ResultSet rs = pstmt.executeQuery();
            // Find all the visits for this patient, and add them to the visits arraylist.
            while (rs.next()) {
                int medicationID = rs.getInt("medicationID");
                medications.add(dbm.findMedication(medicationID));
            }
            rs.close();
            return medications;
        } catch (SQLException e) {
            dbm.cleanup();
            throw new RuntimeException("error getting medications", e);
        }
    }

    /**
     * Clear all data from the Patient table.
     *
     * @throws SQLException
     */
    void clear() throws SQLException {
        Statement stmt = conn.createStatement();
        String s = "delete from PATIENT";
        stmt.executeUpdate(s);
    }


}
