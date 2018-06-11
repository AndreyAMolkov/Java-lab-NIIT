import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.plaf.UIResource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DekanatTest {
    //URL file;
    Dekanat Dekanat;
    File fileXLSX ;
    File fileJSON ;
    @Before
    public void setup() {

        this.fileJSON = new File("dekanat.json");
        try {
            Dekanat = new Dekanat(".xlsx", fileJSON);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    @Test
    public void createFirstDataBase() throws IOException {
      //  Dekanat Dekanat = new Dekanat(".xlsx",fileJSON);
        ArrayList<String> actualList=Dekanat.createFirstDataBase();
   assertFalse("Fall because  the array of data from the file is empty",actualList.isEmpty());
    }

    @Test
    public void setStudentsAndGroup() throws Exception {
        Dekanat Dekanat = new Dekanat(".xlsx",fileJSON);

        Assert.assertFalse("fall if not found name of the student",Dekanat.getStudents().get(0).getNameOfStudent().isEmpty());
        Assert.assertFalse("fall if not found groups",Dekanat.getGroups().isEmpty());

    }

    @Test
    public void addMarksRandom() throws Exception {
        Dekanat Dekanat = new Dekanat(".xlsx",fileJSON);
        Dekanat.addMarksRandom(5);
        for (Student student:Dekanat.getStudents()) {
          int countMarks=student.getCountMarksOfStudent();
            Assert.assertTrue("fall if the count marks for the student = 0 ", countMarks>0);
        }
    }

    @Test
    public void downloadDataInFileJSON() throws Exception {
        Dekanat DekanatActual = new Dekanat(".xlsx",fileJSON);
        ArrayList <Student> actualStudents=DekanatActual.getStudents();
        DekanatActual.downloadDataInFileJSON();
        Dekanat DekanatExpect=new Dekanat(".xlsx",fileJSON);
         ArrayList<Student> expectStudents=DekanatExpect.getStudents();
       for(int i=0;i<actualStudents.size();i++)
           Assert.assertEquals("fall if the actual list of name of student after download from file" +
                   " not equal with list of student after download from file .json ",
                   actualStudents.get(i).getNameOfStudent(),expectStudents.get(i).getNameOfStudent());
Assert.assertEquals("fall if the size of the actual list of students after download from the file" +
        " not equal the size of list of student after download from file .json ",actualStudents.size(),expectStudents.size());
    }

    @Test
    public void removeStudent() throws Exception {
        Dekanat Dekanat = new Dekanat(".xlsx",fileJSON);
        Group currentGroup=Dekanat.getGroups().get(0);
        Dekanat.addHeadInGroupRandom(currentGroup);
        Student student=currentGroup.getHead();
        String nameHeadBefore=student.getNameOfStudent();
        String namePerson=student.getNameOfStudent();
        String expected=student.getNameOfStudent();
        int countStudentsBeforeRemove=Dekanat.getStudents().size();
        Dekanat.removeStudent(namePerson);


        Student actualStudent=Dekanat.searchStudent(namePerson);
        String actual;
        if (actualStudent==null)
            actual=" ";
        else
            actual=actualStudent.getNameOfStudent();
        Assert.assertNotEquals(expected,actual);
        Assert.assertNotEquals(nameHeadBefore,currentGroup.getHead().getNameOfStudent());
        int countStudentsAfterRemove=Dekanat.getStudents().size();
        Assert.assertNotEquals("Fall if the count of students  before remove equal " +
                "the count of students after remove",countStudentsBeforeRemove,countStudentsAfterRemove);
        //


    }

    @Test
    public void changeStudents() throws Exception {
        Dekanat Dekanat = new Dekanat(".xlsx",fileJSON);
        Student studentFirst=Dekanat.getStudents().get(0);
        Student studentLast=Dekanat.getStudents().get(Dekanat.getStudents().size()-1);
        //String groupNameFirstStudent=studentFirst.getGroupOfStudent().getTitle();
        Group groupFirst=studentFirst.getGroupOfStudent();
        Group groupLast=studentLast.getGroupOfStudent();
       // String groupNameLastStudent=studentLast.getGroupOfStudent().getTitle();
        Dekanat.changeStudents(studentFirst,studentLast);
        Assert.assertSame(groupLast.getTitle(),studentFirst.getGroupOfStudent().getTitle());
        Assert.assertSame(groupFirst.getTitle(),studentLast.getGroupOfStudent().getTitle());

    }

    @Test
    public void  addHeadInGroupRandom() throws Exception {
        Dekanat Dekanat = new Dekanat(".xlsx",fileJSON);
        for(Group group:Dekanat.getGroups())
        Dekanat.addHeadInGroupRandom(group);

        int position=0;
        String actual=Dekanat.addHeadInGroupRandom(Dekanat.getGroups().get(position)).getNameOfStudent();
        String expect=Dekanat.getGroups().get(position).getHead().getNameOfStudent();
        Assert.assertEquals(expect,actual);

    }

}