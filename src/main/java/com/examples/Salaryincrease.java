import java.util.*;

class Employee {
  
  private long id;
  private String name;
  private double salary;
  
  public Employee(long id, String name, double salary) {
    this.id = id;
    this.name = name;
    this.salary = salary;
  }
  
  public long getId(){
      return this.id;
  }
  public void setId(long id){
      this.id = id;
  }
  
  public String getName(){
      return this.name;
  }
  public void setName(String name){
      this.name = name;
  }
  
  public double getSalary(){
      return this.salary;
  }
  public void setSalary(double salary){
      this.salary = salary;
  }
  
  public String toString()
  {
    return "Employee[id="+id+", name="+name+", salary="+salary+"]\n";
  }
}

public class Salaryincrease
{
	final static double INCREMENT_SALARY_1 = 10000;
	final static double INCREMENT_SALARY_2 = 20000;
  public static void main(String[] args) {
        
    List<Employee> employees = Arrays.asList(
        new Employee(1, "Gerhard", 90000),
        new Employee(2, "Peter", 100000),
        new Employee(3, "Andriya", 60000),
        new Employee(4, "Ashja", 80000)
        );
    
    List<Employee> newEployees1 = employees.stream().filter(e -> e.getSalary() > 50000).map(e -> 
    {
       e.setSalary(e.getSalary()+INCREMENT_SALARY_1);
         return e;
     }).collect(Collectors.toList());
     
    List<Employee> newEployees2 = employees.stream().filter(e -> e.getSalary() < 50000).map(e -> 
    {
       e.setSalary(e.getSalary()+INCREMENT_SALARY_2);
         return e;
     }).collect(Collectors.toList());
     
     Set<Employee> finalEmployees = new HashSet<Employee>();
     finalEmployees.addAll(newEployees1);
     finalEmployees.addAll(newEployees2);
     finalEmployees.addAll(employees);
    
    System.out.println(finalEmployees);
  }
}
