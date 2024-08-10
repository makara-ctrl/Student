package org.example;

import java.sql.*;
import java.util.Scanner;

public class Professor {
    public static Connection connection(){
//        String url = "jdbc:mysql://localhost:port/dbName";
        String url = "jdbc:mysql://localhost:3307/JavaSP";
        String username = "root";
        String password = "";
        try{
//            System.out.println("Connected");
            return DriverManager.getConnection(url, username, password);
        }catch (Exception e){
//            System.out.println("Not Connected");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Professor.connection();
        Scanner sc = new Scanner(System.in);
        int op;
        String name;
        String gender,phone;
        do {
            System.out.println("------ MENU --------");
            System.out.println("1. INSERT ");
            System.out.println("2. SELECT ");
            System.out.println("3. SEARCH by ID ");
            System.out.println("4. SEARCH by name ");
            System.out.println("5. UPDATE by ID ");
            System.out.println("6. DELETE by ID ");
            System.out.println("7. SORT by ID DESC");
            System.out.println("8. SORT by name A-Z");
            System.out.print("Please Enter an option: ");
            op = sc.nextInt();
            switch (op) {
                case 1->{
                    int n;
                    System.out.println("------ INSERT -------");
                    System.out.print("Enter number of professor: ");
                    n = sc.nextInt();
                    for (int i = 0; i < n; i++) {
                        System.out.println("----- Professor #" + (i + 1) + "-----");
                        System.out.print("Enter Name: ");
                        sc.nextLine();
                        name = sc.nextLine();
                        System.out.print("Enter Gender: ");
                        //sc.nextLine();
                        gender = sc.nextLine();
                        System.out.print("Enter Phone Number : ");
                        phone = sc.nextLine();
                        String sql = "insert into professor (name,gender,phone) values(?,?,?)";
                        try{
                            Connection con =  connection();
                            PreparedStatement ps = con.prepareStatement(sql);
                            ps.setString(1, name);
                            ps.setString(2,gender);
                            ps.setString(3, phone);
                            ps.executeUpdate();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Insert Successfully");
                }
                case 2->{
                    System.out.println("------- SELECT -------");
                    try{
                        Connection con= connection();
                        String sql = "SELECT * FROM professor";//sql
                        Statement st= con.createStatement();
                        ResultSet rs= st.executeQuery(sql);
                        while (rs.next()){
                            System.out.println("ID ="+rs.getInt("id"));
                            System.out.println("Name ="+rs.getString("name"));
                            System.out.println("Gender ="+rs.getString("gender"));
                            System.out.println("Phone Number ="+rs.getString("phone"));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                case 3->{
                    System.out.println("-------Search By ID-------");
                    System.out.print("Enter ID for Search = ");
                    int id=sc.nextInt();
                    String checkSql = "SELECT COUNT(*) FROM student WHERE id = ?";
                    try {
                        Connection con = connection();
                        PreparedStatement checkPs = con.prepareStatement(checkSql);
                        checkPs.setInt(1, id);
                        ResultSet checkRs = checkPs.executeQuery();
                        checkRs.next();
                        int count = checkRs.getInt(1);

                        if (count == 0) {
                            // If the ID does not exist, print "Wrong ID"
                            System.out.println("Wrong ID");
                        } else {
                            // Proceed with search if the ID exists
                            String sql = "SELECT * FROM student WHERE id = " + id;
                            Statement st = con.createStatement();
                            ResultSet rs = st.executeQuery(sql);
                        while (rs.next()) {
                            System.out.println("ID =" + rs.getInt("id"));
                            System.out.println("Name =" + rs.getString("name"));
                            System.out.println("Gender =" + rs.getString("gender"));
                            System.out.println("Phone Number =" + rs.getString("phone"));
                        }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                case 4->{
                    System.out.println("-------Search By Name-------");
                    System.out.print("Enter Name for Search = ");
                    sc.nextLine();
                    String names=sc.nextLine();
                    String sql = "SELECT * FROM professor WHERE name LIKE ?";
                    try {
                        Connection con = connection();
                        PreparedStatement ps = con.prepareStatement(sql);
                        ps.setString(1, "%" + names + "%");
                        ResultSet rs = ps.executeQuery();
                        boolean found = false;
                        while (rs.next()) {
                            System.out.println("ID ="+rs.getInt("id"));
                            System.out.println("Name ="+rs.getString("name"));
                            System.out.println("Gender ="+rs.getString("gender"));
                            System.out.println("Phone Number ="+rs.getString("phone"));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                case 5->{
                    System.out.println("------ Update by ID -------");
                    System.out.print("Enter ID for Update = ");
                    int id = sc.nextInt();

                    // Check if the ID exists in the database
                    String checkSql = "SELECT COUNT(*) FROM professor WHERE id = ?";
                    try {
                        Connection con = connection();
                        PreparedStatement checkPs = con.prepareStatement(checkSql);
                        checkPs.setInt(1, id);
                        ResultSet checkRs = checkPs.executeQuery();
                        checkRs.next();
                        int count = checkRs.getInt(1);

                        if (count == 0) {
                            // If the ID does not exist, print "Wrong ID" and skip the update
                            System.out.println("Wrong ID");
                        } else {
                            // Proceed with the update if the ID exists
                            System.out.print("Enter Name: ");
                            sc.nextLine(); // Clear the buffer
                            name = sc.nextLine();
                            System.out.print("Enter Gender: ");
                            gender = sc.nextLine();
                            System.out.print("Enter phone number : ");
                            phone = sc.nextLine();
                            String sql = "UPDATE professor SET name=?, gender=?, phone=? WHERE id=?";
                            try {
                                PreparedStatement ps = con.prepareStatement(sql);
                                ps.setString(1, name);
                                ps.setString(2, gender);
                                ps.setString(3, phone);
                                ps.setInt(4, id);
                                ps.executeUpdate();
                                System.out.println("Update Successfully");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                case 6->{
                    System.out.println("------- Delete by ID ------");
                    System.out.print("Enter ID for Delete = ");
                    int del=sc.nextInt();
                    String checkSql = "SELECT COUNT(*) FROM professor WHERE id = ?";
                    try {
                        Connection con = connection();
                        PreparedStatement checkPs = con.prepareStatement(checkSql);
                        checkPs.setInt(1, del);
                        ResultSet checkRs = checkPs.executeQuery();
                        checkRs.next();
                        int count = checkRs.getInt(1);

                        if (count == 0) {
                            // If the ID does not exist, print "Wrong ID"
                            System.out.println("Wrong ID");
                        } else {
                            // Proceed with deletion if the ID exists
                            String sql = "DELETE FROM professor WHERE id = " + del;
                            Statement st = con.createStatement();
                            st.execute(sql);
                            System.out.println("Delete Successfully");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                case 7->{
                    System.out.println("------- Sort by ID -------");
                    String sql="SELECT * FROM professor ORDER BY id DESC";
                    try{
                        Connection con=connection();
                        Statement st=con.createStatement();
                        ResultSet rs=st.executeQuery(sql);
                        while (rs.next()){
                            System.out.println("ID ="+rs.getInt("id"));
                            System.out.println("Name ="+rs.getString("name"));
                            System.out.println("Gender ="+rs.getString("gender"));
                            System.out.println("Phone Number ="+rs.getString("phone"));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                case 8->{
                    System.out.println("------- Sort By Name A_Z  ------");
                    String sql="SELECT * FROM professor ORDER BY name ASC";
                    try{
                        Connection con=connection();
                        Statement st=con.createStatement();
                        ResultSet rs=st.executeQuery(sql);
                        while (rs.next()){
                            System.out.println("ID ="+rs.getInt("id"));
                            System.out.println("Name ="+rs.getString("name"));
                            System.out.println("Gender ="+rs.getString("gender"));
                            System.out.println("Phone Number ="+rs.getString("phone"));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }while(true);
    }
}