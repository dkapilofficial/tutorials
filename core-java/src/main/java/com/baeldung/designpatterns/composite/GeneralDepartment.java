package com.baeldung.designpatterns.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gebruiker on 5/1/2018.
 */
public class GeneralDepartment implements Department {

    private Integer id;
    private String name;

    private List<Department> childDepartments;

    public GeneralDepartment(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.childDepartments = new ArrayList<Department>();
    }

    public void printDepartmentName() {
        childDepartments.stream().forEach(Department::printDepartmentName);
    }

    public void addDepartMent(Department department) {
        childDepartments.add(department);
    }

    public void removeDepartment(Department department) {
        childDepartments.remove(department);
    }
}
