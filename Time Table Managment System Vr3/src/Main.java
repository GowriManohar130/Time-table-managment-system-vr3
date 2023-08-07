import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver found successful");
			String url = "jdbc:mysql://localhost:3306/timetable";
			String username = "root";
			String password = "root";
			con = DriverManager.getConnection(url, username, password);
			System.out.println("connected with database successfully");
		} catch (ClassNotFoundException e) {
			System.out.println("unable to find the driver");
		} catch (SQLException e) {
			System.out.println("unable to connect with database");
		}
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the department");
		String dep = scan.nextLine().toUpperCase();
		System.out.println("Enter the course");
		String cn = scan.nextLine();
		System.out.println("Enter the SEMESTER NO");
		int se = scan.nextInt();
		System.out.println("Enter the Section no");
		int s = scan.nextInt();
		System.out.println("Enter no of days");
		int nod = scan.nextInt();
		int nop;
		if(cn.equalsIgnoreCase("mtech")) 
			nop = 4;
		else {
			System.out.println("Enter no of periods");
			nop = scan.nextInt();}
		System.out.println("Enter no of weeks");
		int now = scan.nextInt();
		
		List<String> days = new ArrayList();
		List<String> sub = new ArrayList();
		Map<String, String> subjectFacultyMap = new HashMap<>();
		List<String> dept = new ArrayList<>();
		List<String> per  = new ArrayList<>();
		List<String> sem  = new ArrayList<>();
		List<String> sec  = new ArrayList<>();
		List<String> cou  = new ArrayList<>();
		try {
			String query = "select * from timetable.days";
			PreparedStatement st = con.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				days.add(rs.getString("daysname"));
			}
			String query1 = "select * from timetable.subfacalot where deptName = ? and semId = ? and courseName = ? and sectionId = ?";
			PreparedStatement st1 = con.prepareStatement(query1);
			st1.setString(1, dep);
			st1.setInt(2, se);
			st1.setString(3, cn);
			st1.setInt(4, s);
			ResultSet rs1 = st1.executeQuery();
			while (rs1.next()) {
				subjectFacultyMap.put(rs1.getString("subjectName"), rs1.getString("facultyname"));
				sub.add(rs1.getString("subjectName"));
			}
			String query2 = "SELECT * FROM timetable.department WHERE deptname = ?";
			PreparedStatement st2 = con.prepareStatement(query2);
			st2.setString(1, dep);
			ResultSet rs2 = st2.executeQuery();
			while (rs2.next()) {
				dept.add(rs2.getString("deptname"));
			}
			String query3 = "select * from timetable.Periods";
			PreparedStatement st3 = con.prepareStatement(query3);
			ResultSet rs3 = st3.executeQuery();
			while (rs3.next()) {
				per.add(rs3.getString("periodName"));
			}
			String query4 = "SELECT * FROM timetable.semester WHERE semId = ?";
			PreparedStatement st4 = con.prepareStatement(query4);
			st1.setInt(1, s);
			st4.setInt(1, se);
			ResultSet rs4 = st4.executeQuery();
			while (rs4.next()) {
				sem.add(rs4.getString("semName"));
			}
			String query5 = "SELECT * FROM timetable.sections WHERE sectionId = ?";
			PreparedStatement st5 = con.prepareStatement(query5);
			st5.setInt(1, s);
			ResultSet rs5 = st5.executeQuery();
			while (rs5.next()) {
				sec.add(rs5.getString("sectionName"));
			}
			String query6 = "SELECT * FROM timetable.course WHERE courseName = ?";
			PreparedStatement st6 = con.prepareStatement(query6);
			st6.setString(1, cn);
			ResultSet rs6 = st6.executeQuery();
			while (rs6.next()) {
				cou.add(rs6.getString("courseName"));
			}
			System.out.println("Data fetched Successfully...");
			con.close();

		} catch (SQLException e) {
			System.out.println("unable to connect with database");
		}
		 
		System.out.println(dept);
		System.out.println(sem);
		System.out.println(sec);
		System.out.println(sub);
		System.out.println(days);
		System.out.println(cou);
		
		TableGenerator tg = new TableGenerator(nod,nop,now,se,s);
		tg.printTimetable(tg.assigner(days,sub,dept,per,sem,sec,subjectFacultyMap,cou));

	}
}