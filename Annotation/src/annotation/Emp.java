package annotation;

public class Emp {
    public Emp() {
    }
    int id;
    String name;
    float salary;

    public Emp(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public void giveRaise(float moreMoney){
        this.setSalary(this.getSalary()+moreMoney);
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

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public float getSalary() {
        return salary;
    }
}
