import java.util.ArrayList;

public class ConsolidatedList {

    private String id; //-    идентификационный номер.
    private String name; //-ФИО.
    private String base; //базовая ставка руб./час
    private String workTime; ;//    отработанное время.
    private String payment;// заработная плата.
    private String project;
    private String bonus;
    private String engineers;
    private String subordinates;
    private String numberSubordinates;
    private String rateFromManagingOfTeam;
    private String headingBonus;
    private String position;
    ConsolidatedList(){
        this.id="";
        this.name="";
        this.base="";
        this.workTime="";
        this.payment="";
        this.project="";
        this.bonus="";
        this.engineers="";
        this.subordinates="";
        this.numberSubordinates="";
        this.rateFromManagingOfTeam="";
        this.headingBonus="";
    }



    public String getId() {
        return id;
    }

    public String getBase() {
        return base;
    }

    public String getName() {
        return name;
    }

    public String getBonus() {
        return bonus;
    }

    public String getWorkTime() {
        return workTime;
    }

    public String getEngineers() {
        return engineers;
    }

    public String getPayment() {
        return payment;
    }

    public String getProject() {
        return project;
    }

    public String getHeadingBonus() {
        return headingBonus;
    }

    public String getNumberSubordinates() {
        return numberSubordinates;
    }

    public String getRateFromManagingOfTeam() {
        return rateFromManagingOfTeam;
    }

    public String getSubordinates() {
        return subordinates;
    }

    public String getPosition() {
        return position;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus.toString();
    }

    public void setBase(int base) {
        this.base = Integer.toString(base);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProject(ArrayList<WorkProject>projects) {
        StringBuilder line=new StringBuilder();
        for(WorkProject workProject:projects)
            line.append(workProject.getName()+",");

    if(line.toString().contains(","))
        this.project =  line.toString().substring(0,line.toString().lastIndexOf(","));
    else
        this.project="";
    }

    public void setEngineers(String engineers) {
        this.engineers = engineers;
    }

    public void setPayment(Integer payment) {
        this.payment = payment.toString();
    }

    public void setWorkTime(Double workTime) {
       if(workTime.equals(0.0))
           this.workTime ="";
       else
           this.workTime = workTime.toString();
    }

    public void setHeadingBonus(Integer headingBonus) {
        this.headingBonus = headingBonus.toString();
    }

    public void setNumberSubordinates(Integer numberSubordinates) {
        this.numberSubordinates = numberSubordinates.toString();
    }

    public void setRateFromManagingOfTeam(Integer rateFromManagingOfTeam) {
        this.rateFromManagingOfTeam = rateFromManagingOfTeam.toString();
    }

    public void setSubordinates(ArrayList<Employee> subordinates) {
        StringBuilder line=new StringBuilder();
        for(Employee employee:subordinates)
            line.append(employee.getName()+",");

        this.subordinates =  line.toString().substring(0,line.toString().lastIndexOf(","));
    }
    public void setPosition(String position) {
        this.position = position;
    }
}
