package dao;

import db.MyConnection;
import model.Data;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataDao {
    public static List<Data> getAllFiles(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = null; // Changed to null for better resource management
        ResultSet rs = null; // Changed to null for better resource management
        List<Data> files = new ArrayList<>();

        try {
            ps = connection.prepareStatement("SELECT * FROM data WHERE email = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String path = rs.getString(3);
                files.add(new Data(id, name, path));
            }
        } finally {
            // Ensure resources are closed in the finally block
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            connection.close(); // Close the connection if not needed anymore
        }
        return files;
    }

    public static int hideFile(Data file) throws SQLException, IOException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = null; // Changed to null for better resource management
        FileReader fr = null; // Changed to null for better resource management
        int ans = 0;

        try {
            ps = connection.prepareStatement("INSERT INTO data(name, path, email, bin_data) VALUES(?,?,?,?)");
            ps.setString(1, file.getFileName());
            ps.setString(2, file.getPath());
            ps.setString(3, file.getEmail());
            File f = new File(file.getPath());

            fr = new FileReader(f);
            ps.setCharacterStream(4, fr, (int) f.length());
            ans = ps.executeUpdate();
            fr.close(); // Close FileReader

            // Delete the original file after hiding
            if (f.delete()) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("Failed to delete the file.");
            }
        } finally {
            // Ensure resources are closed in the finally block
            if (fr != null) fr.close();
            if (ps != null) ps.close();
            connection.close(); // Close the connection if not needed anymore
        }
        return ans;
    }

    public static void unhide(int id) throws SQLException, IOException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = null; // Changed to null for better resource management
        ResultSet rs = null; // Changed to null for better resource management
        Clob c = null; // Changed to null for better resource management

        try {
            ps = connection.prepareStatement("SELECT path, bin_data FROM data WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                String path = rs.getString("path");
                c = rs.getClob("bin_data");
                Reader r = c.getCharacterStream();
                FileWriter fw = new FileWriter(path);

                int i;
                while ((i = r.read()) != -1) {
                    fw.write((char) i);
                }
                fw.close();
                r.close(); // Close Reader

                // Delete the record from the database after un-hiding
                ps = connection.prepareStatement("DELETE FROM data WHERE id = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                System.out.println("Unhidden Successfully");
            }
        } finally {
            // Ensure resources are closed in the finally block
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (c != null) c.free(); // Free Clob if not null
            connection.close(); // Close the connection if not needed anymore
        }
    }
}
