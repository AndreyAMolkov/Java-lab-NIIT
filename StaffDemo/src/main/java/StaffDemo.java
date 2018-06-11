import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StaffDemo {
    public static void main(String[]args){
        String path="/Staff of company.xlsx";
        Payroll payroll=null;
        try {
            payroll=new Payroll(path);
            } catch (IOException e) {
            e.printStackTrace();
        }


        ArrayList<Employee >staff=payroll.getStaff();
        ArrayList<WorkProject>workProjects=payroll.getWorkProjects();

        for(WorkProject workProject:workProjects)
            workProject.calculateBonusOfProject(payroll.getCostPersonnel());
        for(WorkProject workProject:workProjects)
            workProject.calculateBonusOfProjectForSeniorManager();

        PrintConsole print=new PrintConsole(staff);
    }
}
class Payroll extends Data{
   protected ArrayList<String>arrayDataOfFile;
   protected String sheetName;
   protected ArrayList<ArrayList<String>>array;
   protected Map map;
   private ArrayList<WorkProject>workProjects;
    private ArrayList<Employee>staff;
   private String []listPosition;//must be in order
    private int costPersonnel;

    Payroll(String pathFile) throws IOException {
        this.arrayDataOfFile=new ArrayList <String>(0);
        this.sheetName="Лист1";
        this.map=new HashMap<String,Integer>();
        this.array=new ArrayList<ArrayList<String>>();
        this.workProjects=new ArrayList <>(0);
        this.listPosition=new String[]{"Cleaner","Driver","Tester",
                "Programmer","TeamLeader","Manager","ProjectManager","SeniorManager"};//must be in  order up
        createFirstDataBase(pathFile);
        createMapArrayData();
        this.staff=createStaff();
        this.costPersonnel=calculateCostPersonnel();
    }
    public int getCostPersonnel(){
        return costPersonnel;
    }
    private int calculateCostPersonnel(){

        for(Employee employee:staff){
            int costOfPersonnel=0;
            String position=employee.getClass().getCanonicalName();
            if (position.equals("Cleaner")||position.equals("Driver"))
                costOfPersonnel+=employee.getPayment();
        }
        return  costPersonnel;
    }
public ArrayList <ArrayList<String>> getArray(){
        return array;
}
public ArrayList<Employee>getStaff(){
        return staff;
}

    public Map getMap() {
        return map;
    }

    private ArrayList <ArrayList <String>>  createFirstDataBase (String pathFile) throws IOException {
       String[][] buf = new String[0][0];
       ArrayList <String> arrayWidth = new ArrayList <String>(0);
       ArrayList <ArrayList <String>> arrayLength = new ArrayList <ArrayList <String>>(0);
       String line = "";
       //read excel file
       InputStream inputStream = File.class.getResourceAsStream(pathFile);

       XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
       XSSFSheet sheet = workbook.getSheet(sheetName);/////////////////////////////////// can be NullPointerException

       int maxLength = sheet.getLastRowNum();
       for (int length = 0; length < maxLength; length++) {
           arrayWidth = new ArrayList <String>(0);
           try {
               int maxWidth = sheet.getRow(length).getLastCellNum();
               for (int width = 0; width < maxWidth; width++) {
                   try {

                       line = sheet.getRow(length).getCell(width).toString();
                   } catch (NullPointerException exe) {
                       line = "";// need for right width
                   }
                   arrayWidth.add(line);
               }

               arrayLength.add(arrayWidth);
           } catch (NullPointerException exe) {
               // got to next iteration
           }

       }
this.array=arrayLength;

return  array;
   }
    public ArrayList<WorkProject> getWorkProjects(){
        return workProjects;
}
    private Map createMapArrayData (){
            Map keyMarker=new HashMap<String,Integer>();
       // ArrayList<ArrayList<String>> buf =(ArrayList<ArrayList<String>>)array.clone();
            for (int i=0;i<array.size();i++) {
               if( (positionId=array.get(i).indexOf(markerId))<0){
                   array.remove(i);
                   i=-1;// need for compensation iteration(i++)
                   continue;
               }
                if( (positionFio=array.get(i).indexOf(markerFIO))<0 || (positionPosition=array.get(i).indexOf(markerPosition))<0
                        ||(positionWage=array.get(i).indexOf(markerWage))<0||(positionProject=array.get(i).indexOf(markerProject))<0
                        ||(positionWorkTime=array.get(i).indexOf(markerWorkTime))<0){
                }  else {
                    keyMarker.put(markerId, positionId);
                    keyMarker.put(markerFIO, positionFio);
                    keyMarker.put(markerPosition, positionPosition);
                    keyMarker.put(markerWage, positionWage);
                    keyMarker.put(markerProject, positionProject);
                    keyMarker.put(markerWorkTime,positionWorkTime);
                   this.map=keyMarker;
                    return keyMarker;

                }
            }
            //this.array=buf;
            return null;
        }
    private ArrayList<Employee>createStaff(){
        String line="";

    int sizeStaff=array.size();
        ArrayList<Employee>staff=new ArrayList <>(0);
        Employee employee;
        // i=1 compensation first line , because the first line is title
         for (String patternPosition:listPosition) {
             for (int i = 1; i < sizeStaff; i++) {
                 String position = array.get(i).get((Integer) map.get(markerPosition)).toLowerCase();
                 if (position.equals(patternPosition.toLowerCase())) {
                     employee = createPositionOfStaff(array.get(i));
                     if (employee != null)
                         staff.add(employee);
                 }
             }
         }


     return staff;
    }

    private int stringToInt(ArrayList<String>arrayLine,String marker){
        Character point='.';//separator for double
        String buf=arrayLine.get((Integer) map.get(marker));
        if (buf=="")return 0;// conditions , if you not found hour then hour=0
        if(buf.contains(".")) {
          return Integer.parseInt( buf.substring(0, buf.indexOf(point)));

        }else
        return Integer.parseInt(buf);
    }
    private WorkProject checkProject(String projectOfStaff){
        if(projectOfStaff=="")
            return null;
try {
    for (WorkProject workProject : workProjects) {
        if (workProject.getName().equals(projectOfStaff))
            return workProject;
    }
}catch (NullPointerException e){
    ;//pass first case
}
        WorkProject workProject=new WorkProject(projectOfStaff,1_000_000,workProjects);
        workProjects.add(workProject);
        return workProject;
    }
    private Employee createPositionOfStaff(ArrayList<String>arrayLine){


       String fioOfStaff=arrayLine.get((Integer) map.get(markerFIO));
       int wageOfStaff=stringToInt(arrayLine,markerWage);
       String idOfStaff=arrayLine.get((Integer) map.get(markerId));
       WorkProject project=checkProject(arrayLine.get((Integer) map.get(markerProject)));
       String positionOfStaff=arrayLine.get((Integer) map.get(markerPosition));
        if(positionOfStaff.equals("SeniorManager")&& project==null)
            project=workProjects.get(0);
       double workTimeOfStaff=Double.parseDouble(arrayLine.get((Integer)map.get(markerWorkTime)));
       Employee Person;
       switch(positionOfStaff){
           case "Tester":
                Person=new Tester(fioOfStaff,idOfStaff,wageOfStaff,workTimeOfStaff);
                project.setTesters((Tester) Person);
                return Person;
           case "Programmer":
               Person=new Programmer(fioOfStaff,idOfStaff,wageOfStaff,workTimeOfStaff);
               project.setProgrammers((Programmer) Person);
               return Person;
           case "TeamLeader":
              Person=new TeamLeader(fioOfStaff,idOfStaff,wageOfStaff,workTimeOfStaff);
               project.setTeamLeader((TeamLeader) Person);
               return Person;

           case "ProjectManager":
               Person=new ProjectManager(fioOfStaff,idOfStaff);
               project.setProjectManager((ProjectManager) Person);
               return Person;

           case "SeniorManager":
               Person=new SeniorManager(fioOfStaff,idOfStaff);
               for(WorkProject workProject:project.getWorkProjects())
               workProject.setSeniorManager((SeniorManager)Person);
              // for(WorkProject workProject:workProjects)
               //   workProject.setBalanceOfProject(Person.getPayment()/project.getWorkProjects().size());
               return (Person);
           case "Manager":
               Person=new Manager(fioOfStaff,idOfStaff);
               project.setManager((Manager)Person);
               return (Person);
           case "Cleaner":
               Person=new Cleaner(fioOfStaff,idOfStaff,wageOfStaff,workTimeOfStaff);
               return Person;

           case "Driver":
               Person=new Driver(fioOfStaff,idOfStaff,wageOfStaff,workTimeOfStaff);
               return Person;

               default:
                   System.out.println("ERROR record: new position of staff: "+positionOfStaff+" ,need check.");
               }
        return null;
   }

    }
class Data{
    //markers
    protected String markerId;
    protected String markerFIO;
    protected String markerPosition;
    protected String markerWage;
    protected String markerProject;
    protected String markerWorkTime;
    //field
    String id;
    String fio;
    String position;
    String wage;
    String project;
    //
    int positionId;
    int positionFio;
    int positionPosition;
    int positionWage;
    int positionProject;
    int positionWorkTime;

    Data(){
        this.markerId="id";
        this.markerFIO="FIO";
        this.markerPosition="Position";
        this.markerWage="Base Per Hour";
        this.markerProject="Project";
        this.markerWorkTime="WorkTime";

        this.id="";
        this.fio="";
        this.position="";
        this.wage="";
        this.project="";
        this.positionId=0;
        this.positionFio=0;
        this.positionPosition=0;
        this.positionWage=0;
        this.positionProject=0;

    }

}
class Employee {
    private int numberSubordinates;
    private String id; //-    идентификационный номер.
    private String name; //-ФИО.
    private double workTime; ;//    отработанное время.
    private int payment;// заработная плата.
    private ArrayList<WorkProject> project;
    private int bonus;
    private String position;
    Employee(String name,String id){
        this.name=name;
        this.id=id;
        this.workTime=0.0;
        this.payment=0;
        this.bonus=0;
        this.project=new ArrayList <>(0);
        this.numberSubordinates=0;
        this.position="";
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getNumberSubordinates() {
        return numberSubordinates;
    }
protected void setNumberSubordinates(int numberSubordinates){
        this.numberSubordinates=numberSubordinates;
}
    protected void setBonus(int bonus) {
        this.bonus = bonus;
    }
    public int getBonus(){
        return  bonus;
    }

    public void setProject(WorkProject project) {
        this.project.add(project);
    }

    public ArrayList<WorkProject> getProject(){
        return  project;
    }
    protected void setWorkTime(double workHour){
       this.workTime=workHour;
}

    public double getWorkTime(){
        return  workTime;
    }

     protected int setPayment(int payment) {
        this.payment=payment;
        return getPayment();
    }
    public int getPayment(){
        return  payment;
    }
   public String getId(){
        return id;
    }
    public String getName(){
        return  name;
    }

}

//Personal - работник по найму с оплатой за фактически отработанное время. Имеет ставку за час.
class Personal extends Employee implements WorkTime{
   private int base; //базовая ставка руб./час
  //  int bonus;
    Personal(String name, String id, int baseRatePerHour) {
        super(name, id);
        this.base=baseRatePerHour;
       // this.bonus=0;
    }


    int getBase(){
        return base;
}
    protected int setBonusAfterPayment(int bonus){
        setBonus(bonus);
        return bonus;
    }
   // public int getBonus(){
      //  return super.getBonus();
    //}


    public void setCalculatedWorkTime(double workHour) {
        setWorkTime(workHour);
        int payment=(int)(base*getWorkTime());
        setPayment(payment);

    }
}
//Cleaner - уборщица.
class Cleaner extends Personal{
    Cleaner(String name, String id, int baseRatePerHour, double workTime) {
        super(name, id, baseRatePerHour);
        setCalculatedWorkTime(workTime);
        setPosition( getClass().getCanonicalName());
    }

}
//Driver - водитель.
class Driver extends Personal{
   private int bonusPercentUp;
    Driver(String name, String id, int baseRatePerHour,double workTime) {
        super(name, id, baseRatePerHour);
        this.bonusPercentUp=20;
        setCalculatedWorkTime(workTime);
        setPosition( getClass().getCanonicalName());
    }

    public void setCalculatedWorkTime(double workHour) {
        super.setWorkTime(workHour);
        int payment=(int)(super.getBase()*super.getWorkTime());
        super.setBonusAfterPayment(payment/100*bonusPercentUp);
        super.setPayment(payment+getBonus());
    }

}
//Engineer - инженер. Имеет ставку и оплату за час + бонусы от выполняемого проекта.
abstract class Engineer extends Employee implements WorkTime,Project{
   // private int bonus;//need for calculation payment= workTime*rate per hour + bonus
   private int base; //базовая ставка руб./час

    Engineer(String name, String id,int baseRatePerHour,double workTime) {
        super(name, id);
        this.base=baseRatePerHour;
        setCalculatedWorkTime(workTime);
         }

    public int getBase() {
        return base;
    }

    @Override
    public void setCalculatedWorkTime(double workHour) {
        setWorkTime(workHour);
        int payment=(int)(base*getWorkTime());
        setPayment(payment);

    }

}
//Tester - инженер по тестированию.
class Tester extends Engineer{


    Tester(String name, String id, int baseRatePerHour, double workTime) {
        super(name, id, baseRatePerHour, workTime);
        setPosition( getClass().getCanonicalName());
    }


    @Override
    public int setBonusProject(int bonus) {
        setBonus(bonus);
        setPayment(getPayment()+getBonus());
    return getBonus();
    }
}
//Programmer - инженер-программист.
class Programmer extends Engineer{


    Programmer(String name, String id, int baseRatePerHour, double workTime) {
        super(name, id, baseRatePerHour, workTime);
        setPosition( getClass().getCanonicalName());
    }

        @Override
        public int setBonusProject(int bonus) {
            setBonus(bonus);
            setPayment(getPayment()+getBonus());
        return getBonus();
        }

}
//TeamLeader - ведущий программист.
class TeamLeader extends Programmer implements Heading{
    private ArrayList<Engineer>engineers;
    private ArrayList<Employee>subordinates;
    private int rateFromManagingOfTeam;
    private int headingBonus;

    TeamLeader(String name, String id, int baseRatePerHour, double workTime) {
        super(name, id, baseRatePerHour, workTime);
        this.engineers=new ArrayList <>(0);
        this.headingBonus=0;
        this.rateFromManagingOfTeam=5000;
        this.subordinates=new ArrayList <>(0);
        setPosition( getClass().getCanonicalName());
    }

    @Override
    public int getNumberSubordinates() {
        return subordinates.size();
    }

    public void setProject(WorkProject workProject){
        super.setProject(workProject);
        setEngineers();
        setNumberSubordinates((engineers.size()));
    }
    private void setEngineers(){
        ArrayList<Programmer>programmers=getProject().get(0).getProgrammers();
       for(Programmer programmer:programmers){
           engineers.add(programmer);
            subordinates.add(programmer);
       }
       for(Tester tester:getProject().get(0).getTesters()){
           engineers.add(tester);
           subordinates.add(tester);
       }

}
public ArrayList<Engineer>getEngineers(){
        return engineers;
}
    public int getRateFromManagingOfTeam() {
        return rateFromManagingOfTeam;
    }

    public int getHeadingBonus(){
        return headingBonus;
    }
    @Override
    public int setHeadingBonus(int numberOfSubordinates) {
        int bonusH=rateFromManagingOfTeam*numberOfSubordinates;
        this.headingBonus=bonusH;
        setPayment(getPayment()+getHeadingBonus());
        return bonusH;

    }

    public ArrayList <Employee> getSubordinates() {
        return subordinates;
    }
}
//Manager - менеджер. Оплату получает из денег проекта, которым руководит.
class Manager extends Employee implements Project{

    Manager(String name, String id) {
        super(name, id);
        super.setPayment(30000);//wage for manager
        setPosition( getClass().getCanonicalName());
    }


    @Override
    public int setBonusProject(int bonus) {
        setBonus(bonus);
        setPayment(getPayment()+getBonus());
        return bonus;
    }
}
//ProjectManager - проектный менеджер.
class ProjectManager extends Manager implements Heading{
private ArrayList<Employee> subordinates;
private int headingBonus;
private int rateFromManagingOfTeam;
    ProjectManager(String name, String id) {
        super(name, id);
        this.headingBonus=0;
        this.rateFromManagingOfTeam=5000;
        this.subordinates=new ArrayList <>(0);
        super.setPayment(50000);//the wage of manager
        setPosition( getClass().getCanonicalName());
    }

    public ArrayList<Employee>getSubordinates(){
        return subordinates;
    }
@Override
    public void setProject(WorkProject workProject){
           super.setProject(workProject);
           setSubordinates(workProject);

    }
    protected void setSubordinates(WorkProject workProject){
        subordinates.add(workProject.getTeamLeader());
        for (Employee employee:workProject.getTeamLeader().getEngineers())
            subordinates.add(employee);

        setNumberSubordinates(getSubordinates().size());
    }
public int getHeadingBonus(){
        return headingBonus;
}
    @Override
    public int setHeadingBonus(int numberOfSubordinates) {
        int bonusHead=rateFromManagingOfTeam*numberOfSubordinates;
        this.headingBonus=headingBonus+bonusHead;
        setPayment(getPayment()+bonusHead);
        return bonusHead;

    }

    public int getRateFromManagingOfTeam() {
        return rateFromManagingOfTeam;
    }

    @Override
    public int getNumberSubordinates() {
        return super.getNumberSubordinates();
    }
}
//SeniorManager - руководитель направления
class SeniorManager extends ProjectManager{
    SeniorManager(String name, String id) {
        super(name, id);
        setPosition( getClass().getCanonicalName());

    }
@Override
    protected void setSubordinates(WorkProject workProject){
            getSubordinates().add(workProject.getManager());
            getSubordinates().add(workProject.getProjectManager());
            for (Employee employee:workProject.getProjectManager().getSubordinates())
                getSubordinates().add(employee);

            setNumberSubordinates(getSubordinates().size());

    }

    public int setBonusOfProjects(int oneOfBonus){
        setBonus(getBonus()+oneOfBonus);
        setPayment(getPayment()+oneOfBonus);
        return oneOfBonus;
    }
}

class WorkProject{
    private int costPersonnel;
    private String name;
    private ArrayList<Programmer>programmers;
    private ArrayList<Tester>testers;
    private TeamLeader teamLeader;
    private Manager manager;
    private ProjectManager projectManager;
    private SeniorManager seniorManager;
    private int costProject;
    private int balanceOfProject;
    private double workTime;
    private ArrayList<WorkProject>workProjects;
    private ArrayList<Employee> staff;


    WorkProject(String name,int costProject,ArrayList<WorkProject>workProjectsAll){
        this.costPersonnel=0;
        this.workProjects=workProjectsAll;
        this.name=name;
        this.costProject=costProject;
        this.balanceOfProject=costProject;
        this.programmers=new ArrayList <>(0);
        this.testers=new ArrayList <>(0);

    }
    private void setBalanceOfProject(int cost){
        this.balanceOfProject-=cost;
    }
    private int calculateBalanceOfStaffWithoutPersonnel(){
        int cost=0;
        for(Employee employee:this.getProjectManager().getSubordinates())
            cost+=employee.getPayment();
        cost+=this.getProjectManager().getPayment();
        cost+=this.getManager().getPayment();
        cost+=this.seniorManager.getPayment()/this.getWorkProjects().size();
        return cost;
    }
public void calculateBonusOfProject(int costOfPersonnel){

    int bufBonus=getBalanceOfProject();
    this.balanceOfProject=costProject-calculateBalanceOfStaffWithoutPersonnel();//new
this.costPersonnel=costOfPersonnel;
this.balanceOfProject-=costPersonnel;

if (balanceOfProject<=0)
    return;
//Engineers-70% from balance Project
    int bonusEngineers=balanceOfProject/100*70;
setBonusEngineers(balanceOfProject/100*70);
//management-30% from balance Project,when manager-5%, teamLead-20%,projectManager=50%,seniorManager-25%
int bonusManager=balanceOfProject/100*5;
manager.setBonusProject(bonusManager);
this.balanceOfProject-=manager.getBonus();
int bonusTeamLeader=balanceOfProject/100*20;
    this.balanceOfProject-=teamLeader.setBonusProject(bonusTeamLeader);
    int bonusProjectManager=balanceOfProject/100*50;
    this.balanceOfProject-=projectManager.setBonusProject(bonusProjectManager);

}
//we consider separately because bonuses from different projects
public  void calculateBonusOfProjectForSeniorManager(){
   // for(WorkProject workProject:this.getWorkProjects())
        this.balanceOfProject-=seniorManager.setBonusOfProjects(this.getBalanceOfProject());

}
// programmers -70% and testers - 20%
        private void setBonusEngineers(int bonus){
        int bonusProgrammers=bonus*70/100;
        int bonusProgrammer=0;
        int workTimeProgrammer=0;
            for(Engineer employee:programmers)
                workTimeProgrammer+=employee.getWorkTime();
        for(Programmer programmer:programmers) {
            bonusProgrammer=(int)(bonusProgrammers/workTimeProgrammer*programmer.getWorkTime());
            programmer.setBonusProject(bonusProgrammer );
            this.balanceOfProject-=programmer.getBonus();
        }
        int bonusTester=0;
        int bonusTesters=bonus*30/100;
            int workTimeTester=0;
            for(Engineer employee:testers)
                workTimeTester+=employee.getWorkTime();
        for (Tester tester:testers){
            bonusTester=(int)(bonusTesters/workTimeTester*tester.getWorkTime());
            tester.setBonusProject(bonusTester);
            this.balanceOfProject-=tester.getBonus();

        }

}

public String getName(){
        return name;
}
    public void setManager(Manager manager) {
        this.manager = manager;
        manager.setProject(this);
        this.balanceOfProject-=manager.getPayment();

    }
public Manager getManager(){
        return manager;
}
    public int getCostProject() {
        return costProject;
    }

    public int getBalanceOfProject() {
        return balanceOfProject;
    }

    public void setProgrammers(Programmer programmer) {
        this.programmers.add(programmer);
        programmer.setProject(this);
        this.balanceOfProject-=programmer.getPayment();
        this.workTime+=programmer.getWorkTime();
    }

    public ArrayList <Programmer> getProgrammers() {
        return programmers;
    }

    public void setTesters(Tester tester) {
        this.testers.add(tester);
        tester.setProject(this);
        this.balanceOfProject-=tester.getPayment();
        this.workTime+=tester.getWorkTime();
    }

    public ArrayList <Tester> getTesters() {
        return testers;
    }

    public void setTeamLeader(TeamLeader teamLeader) {
        this.teamLeader = teamLeader;
        teamLeader.setProject(this);
        this.balanceOfProject-=teamLeader.getPayment();
        this.workTime+=teamLeader.getWorkTime();
       // int countSubordinates=calculateSubordinates(this);
        int countSubordinates=teamLeader.getNumberSubordinates();

        this.balanceOfProject-=teamLeader.setHeadingBonus(countSubordinates);
    }
    public TeamLeader getTeamLeader(){
        return teamLeader;
    }

    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
        projectManager.setProject(this);

        int countSubordinates=projectManager.getNumberSubordinates();
        projectManager.setHeadingBonus(countSubordinates);
    }

    public ProjectManager getProjectManager() {
        return projectManager;
    }

    public ArrayList <WorkProject> getWorkProjects() {
        return workProjects;
    }

    public void setSeniorManager(SeniorManager seniorManager) {
        this.seniorManager = seniorManager;

            seniorManager.setProject(this);
           // this.balanceOfProject -= seniorManager.getPayment() / this.getWorkProjects().size();
            int countSubordinates = this.getProjectManager().getNumberSubordinates()+1;//+ProjectManager,only for this project
           if(this.getManager()!=null)
               countSubordinates+=1;
            seniorManager.setHeadingBonus(countSubordinates);

    }

    public SeniorManager getSeniorManager() {
        return seniorManager;
    }
}

//Перечень интерфейсов:

//WorkTime - расчет оплаты исходя из отработанного времени (часы умножаются на ставку).


// Project - расчет оплаты исходя из участия в проекте (бюджет проекта делится пропорционально персональному вкладу).
//Heading - расчет оплаты исходя из руководства  (количество подчиненных).

// Заработная плата работникам начисляется, исходя из должности, почасовой ставки и участию в проектах.
//Employee - работник. Основной родительский класс для всех разновидностей работников.

/*
Файл данных
Подготовить текстовый файл со списком сотрудников. Информация о каждом сотруднике должна включать:

id - идентификационный номер.
name - ФИО сотрудника.
position - должность.
base - базовая ставка руб./час (если нужна).
project - название проекта (если нужен).
Приложение
Написать демонстрационное приложение, которое создает штат сотрудников на основе данных из текстового файла
и рассчитывает им зарплату, исходя из отработанного времени и участия в проектах. Информация о сотрудниках,
об отработанном времени и заработной плате выводится на экран в виде таблицы.
*/
