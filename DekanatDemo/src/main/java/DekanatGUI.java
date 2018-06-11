

import org.apache.xmlbeans.impl.jam.JConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

public class DekanatGUI extends JFrame implements Runnable {
    String separate;
    //xls or json
   private JRadioButton radioButtonXLSX;
    private JRadioButton radioButtonJSON;
    private ButtonGroup buttonGoup ;

    private JRadioButton[] radioButtons;

    private Dekanat Dekanat;//
    private JLabel lbl;
    private String[] buttonText;
    private JButton[] buttons;
    private JButton btnDownload;
    private String textForBtnDownload;
   //define array of groups
   private Vector<String>ArrayGroups;
    private JComboBox comboGroup;

    private  Vector<String>ArrayStudent;
    private JComboBox comboStudent;
    //List
    private JList listStudent;
    private JList listHead;
    private JList listGroup;
    private JList listAcademicPerformance;
    private JList listMarks;
    private JList listId;

    private JCheckBox[] checkBox;
    private String[][]checkBoxText;
    //markers for checkBox
    private Boolean markerHead;
    private Boolean markerGroup;
    private Boolean markerAcademicPerformance;
    private Boolean markerMarks;
    private Boolean markerId;
    Thread guiThrd;

    DekanatGUI(final Dekanat Dekanat) throws Exception {
this.guiThrd=new Thread(this::run);
      this.Dekanat=Dekanat;

        this.separate=",";
        this.ArrayGroups=new Vector <>();
        this.comboGroup=new JComboBox(ArrayGroups);
        this.ArrayStudent=new Vector <>();
        this.comboStudent=new JComboBox(ArrayStudent);
        this.listStudent=new JList(ArrayStudent);
        this.listHead=new JList(ArrayStudent);
        this.listGroup=new JList(ArrayStudent);
        this.listAcademicPerformance=new JList(ArrayStudent);
        this.listMarks=new JList(ArrayStudent);
        this.listId=new JList(ArrayStudent);

        this.btnDownload=new JButton(textForBtnDownload);
        this.radioButtonXLSX =new JRadioButton("cancel to .xlsx");
        radioButtonXLSX.setActionCommand("download"+separate+".xlsx");
        radioButtonXLSX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setVisible(false);
                   // File f=Dekanat.getFileXLSX();
                    File j=Dekanat.getFileJSON();
                    DekanatGUI d= new DekanatGUI(new Dekanat(".xlsx",j));
                    d.launchFrame();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        this.radioButtonJSON =new JRadioButton("record from .json",true);
        radioButtonJSON.setActionCommand("download"+separate+".json");
        this.buttonGoup=new ButtonGroup();
        String line="addMarksRandom,changeStudents,removeStudent,addHeadInGroupRandom";
        buttonText=line.split(separate);
        createButtons();
        createRadioButtonOfGroup();

        this.markerAcademicPerformance=false;
        this.markerGroup=false;
        this.markerHead=false;
        this.markerMarks=false;
        this.markerId=false;

        createCheckBoxLine();

    }
    void setMarkerId(int position,Boolean mark){
        this.markerId=mark;
        checkBoxText[position][1]=mark.toString();
    }
    void setMarkerMarks(int position,Boolean mark){
        this.markerMarks=mark;
        checkBoxText[position][1]=mark.toString();
}

    void setMarkerHead(int position,Boolean mark){
        this.markerHead=mark;
        checkBoxText[position][1]=mark.toString();
    }

    void setMarkerGroup(int position,Boolean mark){
        this.markerGroup=mark;
        checkBoxText[position][1]=mark.toString();
    }
    void setMarkerAcademicPerformance(int position,Boolean mark){
        this.markerAcademicPerformance=mark;
        checkBoxText[position][1]=mark.toString();
    }

    void createRadioButtonOfGroup(){
        this.buttonGoup=new ButtonGroup();

        radioButtons=new JRadioButton[Dekanat.getGroups().size()+1];
        radioButtons[0]=new JRadioButton("All group");
        radioButtons[0].setActionCommand("group"+separate+"All group");
        radioButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    populateListStudent();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        });
        buttonGoup=new ButtonGroup();
        buttonGoup.add(radioButtons[0]);
        for(int i=0;i<Dekanat.getGroups().size();i++) {
            radioButtons[i+1] = new JRadioButton(Dekanat.getGroups().get(i).getTitle());
            radioButtons[i+1].setActionCommand("group"+separate+Dekanat.getGroups().get(i).getTitle());
            radioButtons[i+1].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        populateListStudent();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });

            buttonGoup.add(radioButtons[i+1]);
        }
        createTextButtonGroup();
    }
    void createTextButtonGroup(){
        String name;
        String group;
        for(JRadioButton rbtn:radioButtons){
            name=rbtn.getActionCommand();
            group = name.substring(name.lastIndexOf(separate)+1);
            if(group.equals("All group")){
                rbtn.setText("All group" + " - " + Dekanat.getStudents().size() + " students");

            }else {
               // group = name.substring(name.lastIndexOf(separate)+1);
                rbtn.setText(group + " - " + Dekanat.getGroup(group).getStudents().size() + " students");

            }
        }
    }
    void populateCheckBoxText(){
        String line="id,head,group,academicPerformance,marks";
        String[]buf=line.split(separate);
        this.checkBoxText=new String[buf.length][2]; //the first value = marker, the second value= the boolean of marker
        checkBoxText[0][0]="id";
        checkBoxText[0][1]=markerId.toString();
        checkBoxText[1][0]="head";
        checkBoxText[1][1]=markerHead.toString();
        checkBoxText[2][0]="group";
        checkBoxText[2][1]=markerGroup.toString();
        checkBoxText[3][0]="academicPerformance";
        checkBoxText[3][1]=markerAcademicPerformance.toString();
        checkBoxText[4][0]="marks";
        checkBoxText[4][1]=markerMarks.toString();
       }

    void createCheckBoxLine(){
        populateCheckBoxText();
        this.checkBox= new JCheckBox[checkBoxText.length];
       for(int i=0;i<checkBox.length;i++) {
           checkBox[i] = new JCheckBox(checkBoxText[i][0]);
           checkBox[i].setActionCommand(checkBoxText[i][0]+separate+Integer.toString(i));
           checkBox[i].addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {

                   String[]buf=e.getActionCommand().split(",");
                   int position=Integer.parseInt(buf[1]);
                  Boolean marker=checkBox[position].isSelected();
                     switch (buf[0]) {
                         case "id":
                             setMarkerId(position,marker);
                             break;
                         case "group":
                             setMarkerGroup(position,marker);
                             break;
                         case "head":
                             setMarkerHead(position,marker);
                             break;
                         case "academicPerformance":
                             setMarkerAcademicPerformance(position,marker);
                             break;
                         case "marks":
                                 setMarkerMarks(position,marker);
                             break;
                         default:
                     }

               }
           });
       }
    }

    void createButtons(){
    this.buttons= new JButton[buttonText.length];
    for(int i=0;i<buttons.length;i++){
          buttons[i]=new JButton(buttonText[i]);
         // buttons[i].addActionListener(new Listener(Dekanat,this));
        buttons[i].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[]buf=e.getActionCommand().split(separate);
                switch (buf[0]) {

                    case "changeStudents":

                        try {
                            createFormChangeStudent();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        // populateListStudent();
                        break;

                    case "removeStudent":

                        try {
                            createFormRemoveStudent();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        // populateListStudent();
                        break;
                    case "addMarksRandom":
                        try {
                            Dekanat.addMarksRandom(5);
                        } catch (Student.AccessDeniedException e1) {
                            e1.printStackTrace();
                        }
                        // populateListStudent();
                        break;
                   case "addHeadInGroupRandom":
                        int positionOfGroup=0;
                        for(int i=0;i<radioButtons.length;i++)
                            if( radioButtons[i].isSelected()){
                                positionOfGroup=i;
                            }
                        try {
                            if(positionOfGroup==0)
                           for(Group group:Dekanat.getGroups())
                               Dekanat.addHeadInGroupRandom(group);
                        else
                        {
                            Group group=Dekanat.getGroups().get(positionOfGroup-1);

                                Dekanat.addHeadInGroupRandom(group);

                        }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                        break;
                    default:
                }
                try {
                    populateListStudent();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

      }

}
    JPanel createBoxLayout(){
        JPanel panel=new JPanel();
        BoxLayout bl=new BoxLayout(panel,BoxLayout.Y_AXIS);
        panel.setLayout(bl);
        buttonGoup=new ButtonGroup();
        buttonGoup.add(radioButtonJSON);
        buttonGoup.add(radioButtonXLSX);
        radioButtonJSON.addActionListener(new Listener(Dekanat,this));
        radioButtonXLSX.addActionListener(new Listener(Dekanat,this));
        panel.add(radioButtonXLSX);
        panel.add(radioButtonJSON);

       // panel.add(btnDownload);

        for(JRadioButton jrbtn:radioButtons)
            panel.add(jrbtn);
        for(JButton btn:buttons)
            panel.add(btn);

        return panel;

    }
    JPanel createCheckBoxLayout(){
        JPanel panel=new JPanel(new FlowLayout());
        for(JCheckBox box:checkBox) {
            panel.add(box);
        }
        JButton btnUpdate=new JButton("Update");
        btnUpdate.setActionCommand("checkBox"+separate+"update");
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    launchFrame();
                    populateListStudent();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        });
        panel.add(btnUpdate);
        return panel;
    }
    JPanel createListPanel() throws Exception {

       JPanel generalListPanel=new JPanel();
       generalListPanel.setLayout(new FlowLayout());

       if(checkBoxText[0][1].equals("true")){
           this.listId=populateListMarker(listId,"id");
           listId.setVisibleRowCount(0);
           JScrollPane scrollPaneList=new JScrollPane(listId);
           scrollPaneList.setPreferredSize(new Dimension(50,650));
           generalListPanel.add(scrollPaneList);

       }
       listStudent.setVisibleRowCount(0);
       JScrollPane scrollPaneListStudent=new JScrollPane(listStudent);
       scrollPaneListStudent.setPreferredSize(new Dimension(200,650));
       generalListPanel.add(scrollPaneListStudent);

       //for(int i=1;i<checkBoxText.length;i++) {
           if (checkBoxText[1][1].equals("true")) {
               this.listHead = populateListMarker(listHead, checkBoxText[1][1]);
               listHead.setVisibleRowCount(0);
               JScrollPane scrollPaneList = new JScrollPane(listHead);
               scrollPaneList.setPreferredSize(new Dimension(50, 650));
               generalListPanel.add(scrollPaneList);
           }
     //  }
        if (checkBoxText[2][1].equals("true")) {
            this.listGroup = populateListMarker(listGroup, checkBoxText[2][1]);
            listHead.setVisibleRowCount(0);
            JScrollPane scrollPaneList = new JScrollPane(listGroup);
            scrollPaneList.setPreferredSize(new Dimension(50, 650));
            generalListPanel.add(scrollPaneList);
        }
        if (checkBoxText[3][1].equals("true")) {
            this.listAcademicPerformance = populateListMarker(listAcademicPerformance, checkBoxText[3][1]);
            listHead.setVisibleRowCount(0);
            JScrollPane scrollPaneList = new JScrollPane(listAcademicPerformance);
            scrollPaneList.setPreferredSize(new Dimension(50, 650));
            generalListPanel.add(scrollPaneList);
        }
        if (checkBoxText[4][1].equals("true")) {
            this.listMarks = populateListMarker(listMarks, checkBoxText[4][1]);
            listHead.setVisibleRowCount(0);
            JScrollPane scrollPaneList = new JScrollPane(listMarks);
            scrollPaneList.setPreferredSize(new Dimension(400, 650));
            generalListPanel.add(scrollPaneList);
        }


//generalListPanel.add(listStudent);

        return generalListPanel;
    }
    JPanel createRemoveStudentPanel()throws Exception{
        JPanel panel=new JPanel();
        panel.add(comboStudent);
        JButton btnRemove=new JButton("Remove student");
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name=(String) comboStudent.getSelectedItem();
                    Dekanat.removeStudent(name);
                    createTextButtonGroup();
                    populateComboStudent();
                    populateListStudent();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        panel.add(btnRemove);
        JButton btnCancel=new JButton("Hide");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    populateListStudent();
                    createForm();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        panel.add(btnCancel);
        return panel;
    }
    JPanel createChangeStudentPanel()throws Exception{
        JPanel panel=new JPanel();
        //panel.add(comboStudent); old
      JTextField fieldIdStudent1=  new JTextField("Input id first of student");
      panel.add(fieldIdStudent1);

        JTextField fieldIdStudent2=  new JTextField("Input id second of student");

        panel.add(fieldIdStudent2);
        JButton btnCheck=new JButton("Change student");
        btnCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                Student student1=null;
                Student student2=null;
try {
    id = Integer.parseInt(fieldIdStudent1.getText());
    student1 = Dekanat.searchStudent(id);
}catch (NumberFormatException e1){
    fieldIdStudent1.setText("repeat input ID of student");
}

try {
    id = Integer.parseInt(fieldIdStudent2.getText());
    student2 = Dekanat.searchStudent(id);
}catch (NumberFormatException e2){
    fieldIdStudent2.setText("repeat input ID of student");
}

    if (student1 != null && student2 != null) {
        try {
            Dekanat.changeStudents(student1, student2);
            populateComboStudent();
            populateListStudent();
            fieldIdStudent1.setText("Done");
            fieldIdStudent2.setText("successfully");
        } catch (Exception e1) {
            e1.printStackTrace();

        }

    } else {

        if (student1 == null)
            fieldIdStudent1.setText("not found, repeat input ID of student");
        if (student2 == null)
            fieldIdStudent2.setText("not found, repeat input ID of student");
    }

            }
        });
        panel.add(btnCheck);

        JButton btnCancel=new JButton("Hide");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    populateListStudent();
                    createForm();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        panel.add(btnCancel);
        return panel;
    }
    void createFormRemoveStudent()throws Exception{
        setVisible(false);

        JPanel mainPanel=new JPanel(new BorderLayout());
        add(mainPanel);

        populateComboStudent();

        try {
            mainPanel.add(createBoxLayout(),BorderLayout.WEST);
            mainPanel.add(createRemoveStudentPanel(),BorderLayout.NORTH);
            mainPanel.add(createListPanel(),BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    createVisible(mainPanel);

    }
    void createFormChangeStudent()throws Exception{
        setVisible(false);

        JPanel mainPanel=new JPanel(new BorderLayout());
        add(mainPanel);

        populateComboStudent();

        try {
            mainPanel.add(createBoxLayout(),BorderLayout.WEST);
            mainPanel.add(createChangeStudentPanel(),BorderLayout.NORTH);
            mainPanel.add(createListPanel(),BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        createVisible(mainPanel);

    }
    void createVisible(JPanel panel){
    setContentPane(panel);
    setSize(1300,1800);
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
}
    void createForm() throws Exception {
        setVisible(false);
        JPanel mainPanel=new JPanel(new BorderLayout());
        add(mainPanel);
        mainPanel.add(createBoxLayout(),BorderLayout.WEST);
        mainPanel.add(createCheckBoxLayout(),BorderLayout.NORTH);
        mainPanel.add(createListPanel(),BorderLayout.CENTER);
        createVisible(mainPanel);
}

    void populateComboStudent() throws Exception {
        comboStudent.removeAllItems();
        populateListMarker(listId,"id");
        populateListMarker(listHead,"head");
        populateListMarker(listGroup,"group");
        populateListMarker(listAcademicPerformance,"academicPerformance");
        populateListMarker(listMarks,"marks");
        int nameOfGroup=0;
        for(int i=0;i<radioButtons.length;i++)
       if( radioButtons[i].isSelected()){
            nameOfGroup=i;

        }
        if(nameOfGroup==0) {
        for (Student student : Dekanat.getStudents())
            ArrayStudent.add(student.getNameOfStudent());
    }else

    {
         for(Student student:Dekanat.getGroups().get(nameOfGroup-1).getStudents())
            ArrayStudent.add(student.getNameOfStudent());

    }
    try {
        comboStudent.setSelectedIndex(0);
    }catch (IllegalArgumentException e){
;// empty
    }
        comboStudent.setMinimumSize(new Dimension(300,200));
        comboStudent.setMaximumRowCount(30);
}

    JList populateListMarker(JList listStudent,String marker) throws Exception {
        listStudent.removeAll();
        String[] data;
        int nameOfGroup=0;
        for(int i=0;i<radioButtons.length;i++)
            if( radioButtons[i].isSelected()){
                nameOfGroup=i;

            }
        if(nameOfGroup==0)
            data= gatheringOfData(Dekanat.getStudents(),marker);
        else
        {
            Group group=Dekanat.getGroups().get(nameOfGroup-1);
            data=gatheringOfData(group.getStudents(),marker);
        }
        listStudent.setListData(data);

return listStudent;
    }

    String[] gatheringOfData(ArrayList<Student>students,String marker) throws Exception {
        String []data=new String[students.size()];
        Student student;
        Group group;
        for (int i=0;i<students.size();i++) {
            student=students.get(i);
            group=student.getGroupOfStudent();
            switch (marker) {
                case "id":
                    data[i] = Integer.toString(student.getIDOfStudent());
                    break;
                case "group":
                    data[i] = group.getTitle();
                    break;
                case "head":
                    if(group.getHead()!=null) {
                        if(student.getNameOfStudent().equals(group.getHead().getNameOfStudent()))
                        data[i] =group.getTitle();
                        else
                            data[i]=" ";
                        break;
                    }
                        data[i]=" ";
                   break;
                case "academicPerformance":
                    data[i] = Double.toString(student.averageMarks());
                    break;
                case "marks":
                    String buf="";
                    int[]marks=student.getMarksOfStudent();
                   for(int j:marks)
                    buf+=j+separate;
                       data[i] =buf;

                    break;
                default:
            }

        }
        return data;
    }
    String[] gatheringOfData(ArrayList<Student>students){// default
        String []data=new String[students.size()];
        for (int i=0;i<students.size();i++)
                    data[i] = students.get(i).getNameOfStudent();

        return data;
    }

    void populateListStudent() throws Exception {    //default

populateListMarker(listId,"id");
populateListMarker(listHead,"head");
populateListMarker(listGroup,"group");
populateListMarker(listAcademicPerformance,"academicPerformance");
populateListMarker(listMarks,"marks");

        listStudent.removeAll();

String[] data;
        int nameOfGroup=0;
        for(int i=0;i<radioButtons.length;i++)
            if( radioButtons[i].isSelected()){
                nameOfGroup=i;

            }
        if(nameOfGroup==0)
           data= gatheringOfData(Dekanat.getStudents());
        else
        {
        Group group=Dekanat.getGroups().get(nameOfGroup-1);
            data=gatheringOfData(group.getStudents());
        }
        listStudent.setListData(data);

    }// default

    private void launchFrame () throws Exception {

     createForm();

}

    @Override
    public void run() {
        try {
            launchFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
