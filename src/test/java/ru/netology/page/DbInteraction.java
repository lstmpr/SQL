package ru.netology.page;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class DbInteraction {
    //@BeforeEach
    @SneakyThrows
    public static Connection getConnectionDB() {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");
        return connection;
    }

    @SneakyThrows
    public static String getVerificationCode(String login) {
        String userId = null;
        String getLogin = "SELECT id FROM users WHERE login=?;";

        try (
                Connection conn = getConnectionDB();
                PreparedStatement idPrepStat = conn.prepareStatement(getLogin);
        ) {
            idPrepStat.setString(1, login);
            try (ResultSet rs = idPrepStat.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getString("id");
                }
            }
        }
        String code = null;
        String getCode = "SELECT code FROM auth_codes WHERE user_id=?;";
        try (
                Connection conn = getConnectionDB();
                PreparedStatement codePrepState = conn.prepareStatement(getCode);
        ) {
            codePrepState.setString(1, userId);
            try (ResultSet rs = codePrepState.executeQuery()) {
                if (rs.next()) {
                    code = rs.getString("code");
                }
            }
        }
        return code;

    }

    @SneakyThrows
    public static String getStatus(String login) {
        String status = null;
        String statusQuery = "SELECT status FROM users WHERE login = ?;";

        try (
                Connection conn = getConnectionDB();
                PreparedStatement statusPrepStat = conn.prepareStatement(statusQuery);
        ) {
            statusPrepStat.setString(1, login);
            try (ResultSet rs = statusPrepStat.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("status");
                }
            }
        }
        return status;
    }

    @SneakyThrows
    public static void cleanDb() {
        String deleteCards = "DELETE FROM cards WHERE TRUE;";
        String deleteAuthCodes = "DELETE FROM auth_codes WHERE TRUE;";
        String deleteUsers = "DELETE FROM users WHERE TRUE;";
        try (Connection conn = getConnectionDB();
             Statement deleteCardsStmt = conn.createStatement();
             Statement deleteAuthCodesStmt = conn.createStatement();
             Statement deleteUsersStmt = conn.createStatement();
        ) {
            deleteCardsStmt.executeUpdate(deleteCards);
            deleteAuthCodesStmt.executeUpdate(deleteAuthCodes);
            deleteUsersStmt.executeUpdate(deleteUsers);
        }
    }

}
