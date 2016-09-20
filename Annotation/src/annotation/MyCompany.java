package annotation;

import java.util.ArrayList;
import java.util.Collection;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(name = "MyCompany", serviceName = "MyCompanyWS")
public class MyCompany {
    public MyCompany() {
        Collection<Dept> depts = new ArrayList<Dept>();
        Collection<Emp> myEmp10 = new ArrayList<Emp>();

        Emp a = new Emp(1, "Larry");
        myEmp10.add(a);
        a = new Emp(2, "Ken");
        myEmp10.add(a);


        depts.add(new Dept(10, "Administration", "Redwood City", myEmp10));
        Collection<Emp> myEmp20 = new ArrayList<Emp>();

        a = new Emp(3, "Gary");
        myEmp20.add(a);
        a = new Emp(4, "Shay");
        myEmp20.add(a);
        a = new Emp(5, "Duncan");
        myEmp20.add(a);
        depts.add(new Dept(20, "JDeveloper", "Redwood Shores", myEmp20));

        Collection<Emp> myEmp30 = new ArrayList<Emp>();

        a = new Emp(8, "Mike");
        myEmp30.add(a);
        a = new Emp(7, "George");
        myEmp30.add(a);
        a = new Emp(6, "David");
        myEmp30.add(a);
        depts.add(new Dept(30, "WebLogic", "New York", myEmp30));
        this.setMyDepts(depts);

    }

    Collection<Dept> myDepts = new ArrayList<Dept>();


    public void setMyDepts(Collection<Dept> myDepts) {
        this.myDepts = myDepts;
    }

    public Collection<Dept> getMyDepts() {
        return myDepts;
    }

    public boolean addEmployeeToDept(Emp emp, int deptid) {
        //TODO write some logic here
        System.out.println("Here we'll be adding an employee to " + deptid);
        return true;
    }
    
    @WebMethod
    public Dept getDeptInfo (int id) {
        for (Dept a : this.getMyDepts()) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }
}
