import javax.xml.bind.Unmarshaller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Listener implements ActionListener {
    private Dekanat Dekanat;
    private DekanatGUI GUI;
    Listener(Dekanat Dekanat,DekanatGUI gui){
       this.Dekanat=Dekanat;
       this.GUI=gui;

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String line=e.getActionCommand();
        String[]buf=e.getActionCommand().split(",");

            //e.getSource().getClass().getClasses();
        switch (buf[0]) {
            case "group":
                //GUI.setVisible(false);
                //GUI.populateComboStudent();
               // GUI.populateListStudent();

                //GUI.setVisible(true);
                break;
          /*  case "download":
                caseCash(buf[positionParameter]);
                break;
            case "service":
                if(buf[positionParameter].equals("Cancel"))
                    caseCancel();
                break;*/
            default:
                // System.out.println("Error switch caseWord");
        }
    }
}
