import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class GroupTest {
    String message;
    URL file;
    String path;
   // File fileXLSX ;
    File fileJSON ;
    Group Group;
    Dekanat Dekanat;
    @Before
    public void start() {

        this.fileJSON = new File("dekanat.json");
        Group = new Group("Test1");
        try {
            Dekanat=new Dekanat(".xlsx",fileJSON);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void addStudentInGroup()  {
        message="";
    String name="";
    try {
    for(int i=0;i<5;i++) {
            name = "men" + i;
            Group.addStudentInGroup(name, i, Group);
        }

    } catch (Group.AccessDeniedException e) {
          this.message=e.getMessage();
        }

        Assert.assertEquals("Access denied",message);
        Student actual=null;
        try {

            actual= Dekanat.getStudents().get(1);
            Assert.assertNotNull(actual);
        }catch (IndexOutOfBoundsException q){

            this.message=q.toString().substring(0,q.toString().indexOf(":"));
            Assert.assertEquals(false,message.equals("java.lang.IndexOutOfBoundsException"));
        }

    }

    @Test
    public void setHeadOfGroup() throws Exception {
        message="";
        String name="";
        try {
            for(int i=0;i<5;i++) {
                name = "men" + i;
                Group.addStudentInGroup(name, i, Group);
            }
Group.setHeadOfGroup(Group.getStudents().get(0));
        } catch (Group.AccessDeniedException e) {
            this.message=e.getMessage();
        }

        Assert.assertEquals("Access denied",message);
        Assert.assertEquals(null,Group.getHead());
        try {
            Dekanat.addHeadInGroupRandom(Dekanat.getGroups().get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }


        int actual=0;
        if(Dekanat.getGroups().get(0).getHead()!=null)
             actual=1;
        Assert.assertEquals(1,actual);
    }

    @Test
    public void removeHeadOfGroup() throws Exception {
        try {
            Dekanat.addHeadInGroupRandom(Dekanat.getGroups().get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }


        int actual=0;
        if(Dekanat.getGroups().get(0).getHead()!=null)
            actual=1;
        Assert.assertEquals(1,actual);
        Dekanat.removeHeadInGroup(Dekanat.getGroups().get(0));

        Assert.assertEquals(null,Dekanat.getGroups().get(0).getHead());
    }
    @Test
    public void searchStudent() throws AccessDeniedException {
      String name= Dekanat.getStudents().get(2).getNameOfStudent();
      String actual=Dekanat.getGroups().get(0).searchStudent(name).getNameOfStudent();
        assertTrue(name.equals(actual));
    }
    @Test
    public void searchStudentID() throws AccessDeniedException {
        int name= Dekanat.getStudents().get(2).getIDOfStudent();
        int actual=Dekanat.getGroups().get(0).searchStudent(name).getIDOfStudent();
        assertTrue(name==actual);
    }

    @Test
    public void addMark() throws Student.AccessDeniedException {
    this.message=" ";
    Group currentGroup=Dekanat.getGroups().get(0);
        Student student=currentGroup.getStudents().get(0);

        try {
            currentGroup.addMarks(student,5);
        } catch (Group.AccessDeniedException e) {
            String line=e.toString();
            this.message=line.substring(line.indexOf(" "),line.length());
        }

        Assert.assertEquals(" Access denied",message);
        Dekanat.addMarksRandom(1);
        int[] marks=Dekanat.getStudents().get(0).getMarksOfStudent();

        Assert.assertTrue("True if the length marks != 0",marks.length>0);
    }

    @Test
    public void averageMarkInGroup() throws AccessDeniedException, Student.AccessDeniedException {
        Dekanat.addMarksRandom(5);
        Group currentGroup=Dekanat.getGroups().get(0);
        ArrayList<Student> students=currentGroup.getStudents();
        double sum=0.0;
        for(Student student:students)
            sum+=student.averageMarks();
        Assert.assertEquals(sum/students.size(),currentGroup.averageMarkInGroup(),0.1);


    }

    @Test
    public void removeStudentFromGroup() throws AccessDeniedException {
       Group currentGroup=Dekanat.getGroups().get(0);

       Student student=currentGroup.getStudents().get(1);
        String name=student.getNameOfStudent();
       currentGroup.removeStudentFromGroup(student);

        Assert.assertTrue("Success is if the searchStudent for the group return null ",currentGroup.searchStudent(name)==null);
    }
}