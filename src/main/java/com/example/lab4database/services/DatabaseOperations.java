package com.example.lab4database.services;

import com.example.lab4database.AppSettings;
import com.example.lab4database.models.Patient;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseOperations {

    private final AppSettings appSettings;

    public DatabaseOperations(AppSettings appSettings) throws ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        this.appSettings = appSettings;
    }

    public List<Patient> getPatients() {

        List<Patient> patients = new ArrayList<>();

        String server = appSettings.getDB_URL();
        String password = appSettings.getDB_PASSWORD();
        String username = appSettings.getDB_USERNAME();

        try(Connection con = DriverManager.getConnection(server, username, password)){

            String query = "SELECT * FROM Patients";

            PreparedStatement st=con.prepareStatement(query);

            ResultSet result=st.executeQuery();

            while(result.next())
            {
                    int PAT_ID = result.getInt("PAT_ID");
                    String PAT_NAME = result.getString("PAT_NAME");
                    String PAT_MOBILE = result.getString("PAT_MOBILE");
                    String PAT_ADDRESS = result.getString("PAT_ADDRESS");
                    String PAT_CONTACT_NO = result.getString("PAT_CONTACT_NO");
                    String PAT_EMAIL = result.getString("PAT_EMAIL");

                patients.add(new Patient(PAT_ID, PAT_NAME, PAT_MOBILE, PAT_CONTACT_NO, PAT_ADDRESS, PAT_EMAIL));
            }


            result.close();
            st.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return patients;
    }

    public void createPatient(Patient model) {

        String server = appSettings.getDB_URL();
        String password = appSettings.getDB_PASSWORD();
        String username = appSettings.getDB_USERNAME();

        try(Connection con = DriverManager.getConnection(server, username, password)){
            String sql = "INSERT INTO PATIENTS (PAT_NAME, PAT_MOBILE, PAT_ADDRESS, PAT_CONTACT_NO, PAT_EMAIL) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, model.getName());
            statement.setString(2, model.getMobile());
            statement.setString(3, model.getAddress());
            statement.setString(4, model.getContact());
            statement.setString(5, model.getEmail());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new patient record has been inserted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePatient(Patient model) {

        String server = appSettings.getDB_URL();
        String password = appSettings.getDB_PASSWORD();
        String username = appSettings.getDB_USERNAME();

        try(Connection con = DriverManager.getConnection(server, username, password)){
            String sql = "UPDATE PATIENTS SET PAT_NAME = ?, PAT_MOBILE = ?, PAT_ADDRESS = ?, " +
                    "PAT_CONTACT_NO = ?, PAT_EMAIL = ? WHERE PAT_ID = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, model.getName());
            statement.setString(2, model.getMobile());
            statement.setString(3, model.getAddress());
            statement.setString(4, model.getContact());
            statement.setString(5, model.getEmail());
            statement.setInt(6, model.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Patient record with ID = " + model.getId() + " has been updated.");
            } else {
                System.out.println("No patient record found with ID = " + model.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePatient(int id) {
        String server = appSettings.getDB_URL();
        String password = appSettings.getDB_PASSWORD();
        String username = appSettings.getDB_USERNAME();

        try(Connection con = DriverManager.getConnection(server, username, password)){
            String sql = "DELETE FROM PATIENTS WHERE PAT_ID = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Patient record with ID = " + id + " has been deleted.");
            } else {
                System.out.println("No patient record found with ID = " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Patient getPatientById(int id) {
        Patient patient = null;

        String server = appSettings.getDB_URL();
        String password = appSettings.getDB_PASSWORD();
        String username = appSettings.getDB_USERNAME();

        try(Connection con = DriverManager.getConnection(server, username, password)){

            String sql = "SELECT * FROM PATIENTS WHERE PAT_ID = ?";

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                int PAT_ID = result.getInt("PAT_ID");
                String PAT_NAME = result.getString("PAT_NAME");
                String PAT_MOBILE = result.getString("PAT_MOBILE");
                String PAT_ADDRESS = result.getString("PAT_ADDRESS");
                String PAT_CONTACT_NO = result.getString("PAT_CONTACT_NO");
                String PAT_EMAIL = result.getString("PAT_EMAIL");

                patient = new Patient(PAT_ID, PAT_NAME, PAT_MOBILE, PAT_CONTACT_NO, PAT_ADDRESS, PAT_EMAIL);
            }


            result.close();
            statement.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return patient;
    }
}
