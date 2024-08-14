package org.example;

import java.sql.*;
import java.util.Scanner;

public class Student {

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
        Student.connection();

        Scanner sc = new Scanner(System.in);
        int op;
        String name;
        String gender;
        float score1,score2,score3,total,average;
        char grade;

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
                    System.out.print("Enter number of students: ");
                    n = sc.nextInt();
                    for (int i = 0; i < n; i++) {
                        System.out.println("----- Student #" + (i + 1) + "-----");
                        System.out.print("Enter Name: ");
                        sc.nextLine();
                        name = sc.nextLine();
                        System.out.print("Enter Gender: ");
                        gender = sc.nextLine();
                        System.out.print("Enter Score 1: ");
                        score1 = sc.nextFloat();
                        System.out.print("Enter Score 2: ");
                        score2 = sc.nextFloat();
                        System.out.print("Enter Score 3: ");
                        score3 = sc.nextFloat();
                        total = score1 + score2 + score3;
                        average = total / 3;
                        grade = (average>=90)?'A' :
                                (average>=80)?'B':
                                        (average>=70)?'C':
                                                (average>=60)?'D':
                                                        (average>=50)?'E':'F';
                        String sql = "insert into student (name,gender,score1,score2,score3,total,average,grade) values(?,?,?,?,?,?,?,?)";
                        try{
                            Connection con =  connection();
                            PreparedStatement ps = con.prepareStatement(sql);
                            ps.setString(1, name);
                            ps.setString(2,gender);
                            ps.setFloat(3, score1);
                            ps.setFloat(4, score2);
                            ps.setFloat(5, score3);
                            ps.setFloat(6, total);
                            ps.setFloat(7, average);
                            ps.setString(8,grade+"");
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
                        String sql = "SELECT * FROM student";//sql
                        Statement st= con.createStatement();
                        ResultSet rs= st.executeQuery(sql);
                        while (rs.next()){
                            System.out.println("ID ="+rs.getInt("id"));
                            System.out.println("Name ="+rs.getString("name"));
                            System.out.println("Gender ="+rs.getString("gender"));
                            System.out.println("Score1 ="+rs.getFloat("score1"));
                            System.out.println("Score2 ="+rs.getFloat("score2"));
                            System.out.println("Score3 ="+rs.getFloat("score3"));
                            System.out.println("Total ="+rs.getFloat("total"));
                            System.out.println("Average ="+rs.getFloat("average"));
                            System.out.println("Grade ="+rs.getString("grade"));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                case 3->{
                    System.out.println("-------Search By ID-------");
                    System.out.print("Enter ID for Search = ");
                    int id = sc.nextInt();
                    // Check if the ID exists in the database
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
                                System.out.println("Score1 =" + rs.getFloat("score1"));
                                System.out.println("Score2 =" + rs.getFloat("score2"));
                                System.out.println("Score3 =" + rs.getFloat("score3"));
                                System.out.println("Total =" + rs.getFloat("total"));
                                System.out.println("Average =" + rs.getFloat("average"));
                                System.out.println("Grade =" + rs.getString("grade"));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                case 4->{
                    System.out.println("-------Search By Name-------");
                    System.out.print("Enter Name for Search = ");
                    sc.nextLine();
                    String names = sc.nextLine();
                    String sql = "SELECT * FROM student WHERE name LIKE ?";
                    try {
                        Connection con = connection();
                        PreparedStatement ps = con.prepareStatement(sql);
                        ps.setString(1, "%" + names + "%");
                        ResultSet rs = ps.executeQuery();
                        boolean found = false;
                        while (rs.next()) {
                            found = true;
                            System.out.println("ID =" + rs.getInt("id"));
                            System.out.println("Name =" + rs.getString("name"));
                            System.out.println("Gender =" + rs.getString("gender"));
                            System.out.println("Score1 =" + rs.getFloat("score1"));
                            System.out.println("Score2 =" + rs.getFloat("score2"));
                            System.out.println("Score3 =" + rs.getFloat("score3"));
                            System.out.println("Total =" + rs.getFloat("total"));
                            System.out.println("Average =" + rs.getFloat("average"));
                            System.out.println("Grade =" + rs.getString("grade"));
                        }
                        if (!found) {
                            System.out.println("No records found");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                case 5->{
                    System.out.println("------ Update by ID -------");
                    System.out.print("Enter ID for Update = ");
                    int id = sc.nextInt();

                    // Check if the ID exists in the database
                    String checkSql = "SELECT COUNT(*) FROM student WHERE id = ?";
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
                            System.out.print("Enter Score 1: ");
                            score1 = sc.nextFloat();
                            System.out.print("Enter Score 2: ");
                            score2 = sc.nextFloat();
                            System.out.print("Enter Score 3: ");
                            score3 = sc.nextFloat();
                            total = score1 + score2 + score3;
                            average = total / 3;
                            grade = (average >= 90) ? 'A' :
                                    (average >= 80) ? 'B' :
                                            (average >= 70) ? 'C' :
                                                    (average >= 60) ? 'D' :
                                                            (average >= 50) ? 'E' : 'F';
                            String sql = "UPDATE student SET name=?, gender=?, score1=?, score2=?, score3=?, total=?, average=?, grade=? WHERE id=?";
                            try {
                                PreparedStatement ps = con.prepareStatement(sql);
                                ps.setString(1, name);
                                ps.setString(2, gender);
                                ps.setFloat(3, score1);
                                ps.setFloat(4, score2);
                                ps.setFloat(5, score3);
                                ps.setFloat(6, total);
                                ps.setFloat(7, average);
                                ps.setString(8, grade + "");
                                ps.setInt(9, id);
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
                    int del = sc.nextInt();

                    // Check if the ID exists in the database
                    String checkSql = "SELECT COUNT(*) FROM student WHERE id = ?";
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
                            String sql = "DELETE FROM student WHERE id = " + del;
                            Statement st = con.createStatement();
                            st.execute(sql);
                            System.out.println("Delete Successfully");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                case 7->{
                    System.out.println("------- Sort by ID -------");
                    String sql="SELECT * FROM student ORDER BY id DESC";
                    try{
                        Connection con=connection();
                        Statement st=con.createStatement();
                        ResultSet rs=st.executeQuery(sql);
                        while (rs.next()){
                            System.out.println("ID ="+rs.getInt("id"));
                            System.out.println("Name ="+rs.getString("name"));
                            System.out.println("Gender ="+rs.getString("gender"));
                            System.out.println("Score1 ="+rs.getFloat("score1"));
                            System.out.println("Score2 ="+rs.getFloat("score2"));
                            System.out.println("Score3 ="+rs.getFloat("score3"));
                            System.out.println("Total ="+rs.getFloat("total"));
                            System.out.println("Average ="+rs.getFloat("average"));
                            System.out.println("Grade ="+rs.getString("grade"));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                case 8->{
                    System.out.println("------- Sort By Name A_Z  ------");
                    String sql="SELECT * FROM student ORDER BY name ASC";
                    try{
                        Connection con=connection();
                        Statement st=con.createStatement();
                        ResultSet rs=st.executeQuery(sql);
                        while (rs.next()){
                            System.out.println("ID ="+rs.getInt("id"));
                            System.out.println("Name ="+rs.getString("name"));
                            System.out.println("Gender ="+rs.getString("gender"));
                            System.out.println("Score1 ="+rs.getFloat("score1"));
                            System.out.println("Score2 ="+rs.getFloat("score2"));
                            System.out.println("Score3 ="+rs.getFloat("score3"));
                            System.out.println("Total ="+rs.getFloat("total"));
                            System.out.println("Average ="+rs.getFloat("average"));
                            System.out.println("Grade ="+rs.getString("grade"));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }while(true);
    }
}