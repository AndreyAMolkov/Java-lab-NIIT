import java.util.ArrayList;


public class PrintConsole {
    private ArrayList <Employee> staff;
    private String[] form;


    PrintConsole(ArrayList <Employee> staff) {
        this.staff = staff;
        this.form = new String[]{"Id", "Name", "Position", "Base per hour", "Project",
                "Work time", "Payment", "BonusOfProject", "Heading bonus", "Rate of heading", "Subordinates"};
        populateStaffForPrint();
    }

    private void populateStaffForPrint() {

        System.out.printf("%-5s%-35s%-20s%-9s%-15s%-15s%-9s%n", "Id", "Name", "Position", "Payment",
                "BonusOfProject", "Heading Bonus", "Subordinates");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        for (Employee person : staff) {
            String position=person.getClass().getCanonicalName();
            if (position.equals( "SeniorManager") || position.equals("TeamLeader")||position.equals("ProjectManager")) {
                if(position.equals("TeamLeader"))
                    System.out.printf("%-5s%-35s%-20s%-9d%-15d%-15d%-9d%n",
                        person.getId(),
                        (person).getName(),
                        person.getClass().getCanonicalName(),
                        person.getPayment(),
                        person.getBonus(),
                        ((TeamLeader)person).getHeadingBonus(),
                        ((TeamLeader) person).getEngineers().size());
                else
                    System.out.printf("%-5s%-35s%-20s%-9d%-15d%-15d%-9d%n",
                            person.getId(),
                            (person).getName(),
                            person.getClass().getCanonicalName(),
                            person.getPayment(),
                            person.getBonus(),
                            ((ProjectManager)person).getHeadingBonus(),
                            person.getNumberSubordinates());

            } else {
                System.out.printf("%-5s%-35s%-20s%-9d%-9d%n",
                        person.getId(),
                        person.getName(),
                        person.getClass().getCanonicalName(),
                        person.getPayment(),
                        person.getBonus());
            }
        }
    }

}