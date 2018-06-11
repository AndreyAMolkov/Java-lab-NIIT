import java.util.ArrayList;

public class CollectConsolidatedList {


    public ArrayList<ConsolidatedList> collect(ArrayList <Employee> staff){
ConsolidatedList consolidatedList;
ArrayList<ConsolidatedList>list=new ArrayList <>(0);

        for(Employee employee:staff){
            consolidatedList=new ConsolidatedList();

           if(!isProjectManager(employee,consolidatedList))
               if(!isTeamLeader(employee,consolidatedList))
                   if(!isEngineer(employee,consolidatedList))
                       if(!isPersonal(employee,consolidatedList))
                           getDataFromEmployee(employee,consolidatedList);

            list.add(consolidatedList);
        }

        return list;
    }


private Boolean isPersonal(Employee employee,ConsolidatedList c){
    try {
        Personal personal = (Personal) employee;
         getDataFromPersonal(personal,c);// No exception: obj is of type Personal or IT MIGHT BE NULL!
        return true;
    } catch (ClassCastException e) {
        return false;
    }
}
    private Boolean isEngineer(Employee employee,ConsolidatedList c){
        try {
            Engineer engineer = (Engineer) employee;
            getDataFromEngineer(engineer,c);// No exception: obj is of type Engineer or IT MIGHT BE NULL!
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }
    private Boolean isTeamLeader(Employee employee,ConsolidatedList c){
        try {
            TeamLeader teamLeader= (TeamLeader) employee;
            getDataFromTeamLeader(teamLeader,c);// No exception: obj is of type TeamLeader or IT MIGHT BE NULL!
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }
    private Boolean isProjectManager (Employee employee,ConsolidatedList c){
        try {
            ProjectManager projectManager= (ProjectManager) employee;
            getDataFromProjectManager(projectManager,c);// No exception: obj is of type ProjectManager or IT MIGHT BE NULL!
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }
    private void getDataFromEmployee(Employee employee,ConsolidatedList consolidated){

        consolidated.setPosition(employee.getPosition());
        consolidated.setPayment(employee.getPayment());
        consolidated.setBonus(employee.getBonus());
        consolidated.setId(employee.getId());
        consolidated.setName(employee.getName());
        consolidated.setWorkTime(employee.getWorkTime());
        consolidated.setProject(employee.getProject());
    }
    private void getDataFromPersonal(Personal personal,ConsolidatedList consolidated){
        getDataFromEmployee((Employee)personal,consolidated);
         consolidated.setBase(personal.getBase());
    }
    private void getDataFromEngineer(Engineer engineer,ConsolidatedList consolidated){
        getDataFromEmployee((Employee)engineer,consolidated);
        consolidated.setBase(engineer.getBase());
    }
    private void getDataFromTeamLeader(TeamLeader teamLeader,ConsolidatedList consolidated){
       getDataFromEngineer((Engineer)teamLeader,consolidated);
        consolidated.setSubordinates(teamLeader.getSubordinates());
        consolidated.setRateFromManagingOfTeam(teamLeader.getRateFromManagingOfTeam());
        consolidated.setHeadingBonus(teamLeader.getHeadingBonus());
        consolidated.setNumberSubordinates(teamLeader.getNumberSubordinates());
    }

    private void getDataFromProjectManager(ProjectManager projectManager,ConsolidatedList consolidated){
        getDataFromEmployee((Employee)projectManager,consolidated);
        consolidated.setSubordinates(projectManager.getSubordinates());
        consolidated.setRateFromManagingOfTeam(projectManager.getRateFromManagingOfTeam());
        consolidated.setHeadingBonus(projectManager.getHeadingBonus());
        consolidated.setNumberSubordinates(projectManager.getNumberSubordinates());
    }
}
