import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class TableGenerator {
	private int no_of_days;
	private int no_of_periods;
	private int no_of_weeks;
	private int no_of_sem;
	private int no_of_sec;

	public TableGenerator(int no_of_days, int no_of_periods, int no_of_weeks, int no_of_sem, int no_of_sec) {
		super();
		this.no_of_days = no_of_days;
		this.no_of_periods = no_of_periods;
		this.no_of_weeks = no_of_weeks;
		this.no_of_sem = no_of_sem;
		this.no_of_sec = no_of_sec;
	}

	List<List<String>> assign = new ArrayList<>();

	public List<List<String>> assigner(List<String> days, List<String> sub, List<String> dept, List<String> per,
	        List<String> sem, List<String> sec, Map<String, String> subjectFacultyMap, List<String> cou) {
	    int totalSubjects = sub.size();
	    int subjectsPerWeek = 6; 

	    for (int depts = 0; depts < 1; depts++) { 
	        for (int seme = 0; seme < 1; seme++) { 
	            for (int sect = 0; sect < 1; sect++) { 
	                for (int week = 0; week < no_of_weeks; week++) {
	                    for (int day = 0; day < no_of_days; day++) {
	                        for (int period = 0; period < no_of_periods; period++) {
	                            String weekOfSemester = "Week " + (week + 1);
	                            String department = dept.get(depts);
	                            String dayOfWeek = days.get(day);
	                            String periodLabel = per.get(period);
	                            String course = cou.get(depts);
	                            
	                            
	                            if (dayOfWeek.equalsIgnoreCase("saturday") && periodLabel.equalsIgnoreCase("period5")) {
	                                break;
	                            } else {
	                                String semester = sem.get(seme);
	                                String section = sec.get(sect);
	                                String subject;
	                                if (periodLabel.equalsIgnoreCase("period5")) {
	                                    subject = "Break";
	                                } else {
	                                    
	                                    int subjectIndex = (week * no_of_days * no_of_periods + day * no_of_periods + period) % totalSubjects;
	                                    subject = sub.get(subjectIndex);
	                                }
	                                String faculty = subjectFacultyMap.get(subject);
	                                List<String> entry = Arrays.asList(department, course, semester, section, weekOfSemester,
	                                        dayOfWeek, periodLabel, subject, faculty);
	                                assign.add(entry);
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    }
	    return assign;
	}


	public void printTimetable(List<List<String>> assign) {
		/*
		 * for(int i = 0;i<assign.size();i++) { System.out.println(assign.get(i)); }
		 */
		for (List<String> combination : assign) {
			String department = combination.get(0);
			String course = combination.get(1);
			String semester = combination.get(2);
			String section = combination.get(3);
			String weekOfSemester = combination.get(4);
			String dayOfWeek = combination.get(5);
			String periodLabel = combination.get(6);
			String subject = combination.get(7);
			String faculty = combination.get(8);

			System.out.printf("%-5s\t%-5s\t%-5s\t%-10s\t%-12s\t%-10s\t%-10s\t%-20s\t%s\n", department, course, semester, section,
					weekOfSemester, dayOfWeek, periodLabel, subject, faculty);
		}
	}

}