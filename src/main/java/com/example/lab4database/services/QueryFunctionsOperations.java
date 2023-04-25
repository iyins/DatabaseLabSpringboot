package com.example.lab4database.services;

import com.example.lab4database.AppSettings;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Service
public class QueryFunctionsOperations {
    private final AppSettings appSettings;

    public QueryFunctionsOperations(AppSettings appSettings) throws ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        this.appSettings = appSettings;
    }

    public String getResultForActions(String actions) {
        switch (actions) {
            case "1": {
                return getAction1Result();
            }
            case "2": {
                return getAction2Result();
            }
            default: return null;
        }
    }

    public String getAction1Result() {

        StringBuilder stringBuilder = new StringBuilder();

        String server = appSettings.getDB_URL();
        String password = appSettings.getDB_PASSWORD();
        String username = appSettings.getDB_USERNAME();

        try(Connection con = DriverManager.getConnection(server, username, password)){

            String query = "SELECT d.DOC_NAME, h.hos_name " +
                    "FROM DOCTORS d " +
                    "INNER JOIN treatmentschedules t ON d.DOC_ID = t.doctor_id " +
                    "INNER JOIN Hospitals h on d.hos_id = h.hos_id " +
                    "GROUP BY d.DOC_NAME, h.hos_name " +
                    "HAVING COUNT(DISTINCT t.PAT_ID) > 1";

            PreparedStatement st=con.prepareStatement(query);

            ResultSet result=st.executeQuery();

            while(result.next())
            {
                String DoctorName = result.getString("DOC_NAME");
                String HospitalName = result.getString("hos_name");

                stringBuilder.append(String.format("DoctorName: %s\n", DoctorName));
                stringBuilder.append(String.format("HospitalName: %s\n", HospitalName));
                stringBuilder.append("\n");
            }


            result.close();
            st.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return stringBuilder.length() == 0 ? null : stringBuilder.toString();
    }

    public String getAction2Result() {

        StringBuilder stringBuilder = new StringBuilder();

        String server = appSettings.getDB_URL();
        String password = appSettings.getDB_PASSWORD();
        String username = appSettings.getDB_USERNAME();

        try(Connection con = DriverManager.getConnection(server, username, password)){

            String query = "SELECT d.DOC_NAME, COUNT(*) AS TREATMENT_COUNT " +
                    "FROM DOCTORS d " +
                    "INNER JOIN treatmentschedules t ON d.DOC_ID = t.doctor_id " +
                    "GROUP BY d.DOC_NAME ";

            PreparedStatement st=con.prepareStatement(query);

            ResultSet result=st.executeQuery();

            while(result.next())
            {
                String DoctorName = result.getString("DOC_NAME");
                int TREATMENT_COUNT = result.getInt("TREATMENT_COUNT");

                stringBuilder.append(String.format("DoctorName: %s\n", DoctorName));
                stringBuilder.append(String.format("TREATMENT_COUNT: %s\n", TREATMENT_COUNT));
                stringBuilder.append("\n");
            }


            result.close();
            st.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return stringBuilder.length() == 0 ? null : stringBuilder.toString();
    }
}
