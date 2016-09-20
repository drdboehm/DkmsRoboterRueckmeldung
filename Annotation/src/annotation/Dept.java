package annotation;

import java.util.Collection;

public class Dept {
    public Dept() {
    }
    
    int id;
    String name;
    String location;
    Collection<Emp> employees;

    public Dept(int id, String name, String location,
                Collection<Emp> employees) {
        super();
        this.id = id;
        this.name = name;
        this.location = location;
        this.employees = employees;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setEmployees(Collection<Emp> employees) {
        this.employees = employees;
    }

    public Collection<Emp> getEmployees() {
        return employees;
    }
}
