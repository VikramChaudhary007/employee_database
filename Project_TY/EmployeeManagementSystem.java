import java.sql.*;
import java.util.Scanner;

public class EmployeeManagementSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Employee Management System (JDBC) ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Update Employee Salary");
            System.out.println("5. Delete Employee");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addEmployee(sc);
                    break;

                case 2:
                    viewEmployees();
                    break;

                case 3:
                    searchEmployee(sc);
                    break;

                case 4:
                    updateSalary(sc);
                    break;

                case 5:
                    deleteEmployee(sc);
                    break;

                case 6:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 6);

        sc.close();
    }

    // 1. Add Employee
    static void addEmployee(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {

            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Salary: ");
            double salary = sc.nextDouble();
            sc.nextLine();

            System.out.print("Enter Department: ");
            String dept = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO employee VALUES (?, ?, ?, ?)"
            );
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDouble(3, salary);
            ps.setString(4, dept);

            ps.executeUpdate();
            System.out.println("Employee Added Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2. View Employees
    static void viewEmployees() {
        try (Connection con = DBConnection.getConnection()) {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM employee");

            System.out.println("ID\tName\tSalary\tDepartment");
            while (rs.next()) {
                System.out.println(
                    rs.getInt(1) + "\t" +
                    rs.getString(2) + "\t" +
                    rs.getDouble(3) + "\t" +
                    rs.getString(4)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3. Search Employee
    static void searchEmployee(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {

            System.out.print("Enter ID: ");
            int id = sc.nextInt();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM employee WHERE id=?"
            );
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Employee Found:");
                System.out.println(
                    rs.getInt(1) + " " +
                    rs.getString(2) + " " +
                    rs.getDouble(3) + " " +
                    rs.getString(4)
                );
            } else {
                System.out.println("Employee Not Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 4. Update Salary
    static void updateSalary(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {

            System.out.print("Enter ID: ");
            int id = sc.nextInt();

            System.out.print("Enter New Salary: ");
            double salary = sc.nextDouble();

            PreparedStatement ps = con.prepareStatement(
                "UPDATE employee SET salary=? WHERE id=?"
            );
            ps.setDouble(1, salary);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Salary Updated!");
            else
                System.out.println("Employee Not Found");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 5. Delete Employee
    static void deleteEmployee(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {

            System.out.print("Enter ID: ");
            int id = sc.nextInt();

            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM employee WHERE id=?"
            );
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Employee Deleted!");
            else
                System.out.println("Employee Not Found");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}