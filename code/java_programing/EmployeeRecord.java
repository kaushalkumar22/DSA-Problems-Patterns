package code.java_programing;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRecord {
    private final Department department;
    private final List<Integer> list;

    public EmployeeRecord(Department department, List<Integer> li){
        this.department = new Department(department);
        this.list = new ArrayList<>(li);
    }

    public Department getDepartment() {
        return department;
    }

}
class Department{

    private String name;
    public Department(String name) {
        this.name = name;
    }
    // copy constructor for deep copy
    public Department(Department other) {
        this.name = other.name;
    }
}