import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
//import jdk.nashorn.internal.parser.JSONParser;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.json.simple.parser.JSONParser;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import sun.plugin.javascript.navig.Array;

import java.io.*;
import java.lang.annotation.Retention;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class DekanatDemo {
    Dekanat Dekanat;
  //  File fileXLSX = (DekanatDemo.class.getResourceAsStream("/dekanat.xlsx"));
    File fileJSON;

    DekanatDemo() throws Exception {
            this.fileJSON = new File("dekanat.json");
            if (fileJSON.exists() && fileJSON.length() > 0){
                this.Dekanat = new Dekanat(".json", fileJSON);
        }else{
            // create the new empty json
              this.fileJSON = new File("dekanat.json");
              fileJSON.createNewFile();
            this.Dekanat = new Dekanat(".xlsx",fileJSON);
        }
    }
    public static void main(String[] args) {

        try {
            DekanatDemo Dekanat=new DekanatDemo();
            DekanatGUI DekanatGUI=new DekanatGUI(Dekanat.Dekanat);
            DekanatGUI.run();
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }

    }

}

    class Student{
        private int id;    //- идентификационный номер
        private String fio;//  - фамилия и инициалы
        private Group group;//  Group - ссылка на группу (объект Group)
        private int[] marks;//  Marks - массив оценок
        private int countMarks;//  Num - количество оценок
        //  создание студента с указанием ИД и ФИО
        // зачисление в группу
        Student(String fio,int id,Group group){
            this.id=id;
            this.fio=fio;
            this.group=group;
            this.marks=new int[0];
            this.countMarks=marks.length;
        }

        public class AccessDeniedException extends Exception {
            AccessDeniedException (String msgText){
                super(msgText);
            }
        }

        // check access (true is only for the Dekanat)
        void checkAccess()throws AccessDeniedException {
            String className= Thread.currentThread().getStackTrace()[3].getClassName();
            StackTraceElement[] className1= Thread.currentThread().getStackTrace();
            if(className!="Dekanat")
                throw  new Student.AccessDeniedException("Access denied");
        }

       // добавление оценки
        int[] addMark(int mark) throws AccessDeniedException {
            checkAccess();

            int[] newMarks=new int[countMarks+1];
          for(int i=0;i<countMarks;i++)
              newMarks[i]=marks[i];

          newMarks[countMarks]=mark;
            marks=newMarks;
            this.countMarks=countMarks+1;
            return newMarks;
        }
      //  вычисление средней оценки
        double averageMarks(){

          double summ=0;
          for(int i:marks)
              summ+=i;
try {
    return summ / marks.length;
}catch (ArithmeticException e) {
    return summ / 1;
}
        }

        String getNameOfStudent(){
            return fio;
        }
        int getIDOfStudent(){
            return id;
        }
        Group getGroupOfStudent(){
            return group;
        }
        int getCountMarksOfStudent(){
            return countMarks;
        }
        int[] getMarksOfStudent(){
            return marks;
        }
        Student changeGroupOfStudent(Student person,Group newGroup){
            this.group=newGroup;
            return person;
        }
    }

    class Group{

private String title;//- название группы
private ArrayList<Student>students;// - массив из ссылок на студентов
//private int num;// - количество студентов в группе
private Student head;// - ссылка на старосту (из членов группы)
        //создание группы с указанием названия
        Group(String nameOfGroup){
            this.title=nameOfGroup;
            //this.num=num;
            this.head=head;
            this.students=new ArrayList<Student>(0);
        }

// check access (true is only for the Dekanat)
        void checkAccess()throws AccessDeniedException {
            String className= Thread.currentThread().getStackTrace()[3].getClassName();
            StackTraceElement[] className1= Thread.currentThread().getStackTrace();
                    if(className!="Dekanat")
                       throw  new AccessDeniedException("Access denied");
        }

        public class AccessDeniedException extends Exception {
            AccessDeniedException (String msgText){
                super(msgText);
            }
        }
//добавление студента
        ArrayList<Student> addStudentInGroup(String fioStudent,int idStudent,Group group) throws AccessDeniedException {
            checkAccess();

            Student newStudent=new Student(fioStudent,idStudent,group);
            students.add(newStudent);

            return students;
        }
//избрание старосты
        Student setHeadOfGroup(Student person) throws AccessDeniedException {
            checkAccess();
          this.head=person;
        return person;
        }
        void removeHeadOfGroup() throws AccessDeniedException {
            checkAccess();
            this.head=null;
        }
//поиск студента по ФИО или ИД
        Student searchStudent(String nameStudent){
            String person="";
           for(int i=0;i<students.size();i++) {
               person=students.get(i).getNameOfStudent();
               if(person.equals(nameStudent)==true)
                    return students.get(i);
           }
            return  null;
        }
        Student searchStudent(int IDStudent){
            int person=0;
            for(int i=0;i<students.size();i++) {
                person=students.get(i).getIDOfStudent();
                if(person==IDStudent)
                    return students.get(i);
            }
            return  null;
        }

        int[]addMarks(Student person,int mark) throws AccessDeniedException, Student.AccessDeniedException {
            checkAccess();
            return person.addMark(mark) ;
        }
//вычисление среднего балла в группе
        double averageMarkInGroup(){
            double sum=0;
            for(Student person:students)
                sum+=person.averageMarks();

            double averageMark=sum/students.size();
           return averageMark;
        }
//исключение студента из группы*/
        ArrayList<Student> removeStudentFromGroup(Student person){

             students.remove(person);
           return students;
        }
        ArrayList<Student> removeStudentFromGroup(String nameStudent){
            Student person=searchStudent(nameStudent);
            students.remove(person);

            return students;
        }
        ArrayList<Student> removeStudentFromGroup(int IDStudent){
            Student person=searchStudent(IDStudent);
            students.remove(person);

            return students;
        }


        ArrayList<Student> getStudents(){
            return students;
        }
        String getTitle(){
            return title;
        }
        Student getHead(){
            return head;
        }
    }

    class Dekanat{
    // markers
    private String markerGroup;
    //markers for the students
    private int numberOfCapitalLetter;
    private int numberOfGapOfLine;
    private int numberOfPointOfLine;
    private char pointMarker;
   private enum marker{STUDENT,GROUP,OTHER};

    marker state;
    // default mode
    private  String sheetName;// set for first download of excel file
    private int numberRowList;// set for first download of excel file
    private ArrayList<String>arrayDataOfFile=new ArrayList<String>();
    private String extensionFileSource;
    private File fileJSON;
    private File fXLSX;
    private ArrayList<Student> students;// - массив студентов
    private ArrayList<Group> groups;// - массив групп

        Dekanat (String extensionFileSource,File fileJSON) throws IOException, ParseException, Exception {
            this.extensionFileSource=extensionFileSource;
            String file=new String(".xlsx");
            String json=new String(".json");
            //this.fileXLSX= new File(System.class.getResource("/dekanat.xlsx").getFile());
            //this.fileXLSX= new File(System.class.get("dekanat.xlsx").getFile());

            this.fileJSON =fileJSON;
                    // this.fileJSON = new File(System.class.getResource("dekanat.json").getFile());
            this.students=new ArrayList<Student>(0);
            this.groups=new ArrayList<Group>(0);
            this.sheetName="Лист1";// set for first download
            this.numberRowList=0;
            this.markerGroup="MBA";
            this.pointMarker='.';
            this.state=marker.OTHER;
           this.numberOfCapitalLetter=3;
            this.numberOfGapOfLine=2;
            this.numberOfPointOfLine=0;

             if(extensionFileSource.equals(file)){
            setStudentsAndGroup();
           downloadDataInFileJSON();
           }
           else if(extensionFileSource.equals(json))
               downloadDataFromJSON();
        }


       /* File getFileXLSX(){
            return fileXLSX;
        }*/
        File getFileJSON(){
            return  fileJSON;
        }
        ArrayList<String> createFirstDataBase () throws IOException {

            XSSFWorkbook workbook=null;
            try {
                     workbook = new XSSFWorkbook(File.class.getResourceAsStream("/dekanat.xlsx") );

            }catch(NotOfficeXmlFileException e){
               e.printStackTrace();// System.out.println("Not found file: "  +fileSource.getName());

            }
            XSSFSheet sheet = workbook.getSheet(sheetName);/////////////////////////////////// can NullPointerException

            for (int i = 0; i < sheet.getLastRowNum() + 1; i++){
               try {
                   arrayDataOfFile.add(sheet.getRow(i).getCell(numberRowList).toString());
               }catch (NullPointerException exe) {
                   //goto next iteration
               }
            }
            workbook.close();
            return arrayDataOfFile;
        }
       private marker searchMarker(String line){
            // search student
            int countCapitalLetter=calculationCapitalLetter(line);
            int countGap=calculationGap(line);
            int countPoint=calculationPoint(line);
            boolean isGroup=line.startsWith(markerGroup,0);
            if(isGroup==true)
                state=marker.GROUP;

            if(countCapitalLetter==numberOfCapitalLetter && countGap==numberOfGapOfLine
                                             && countPoint==numberOfPointOfLine && isGroup==false)
                state=marker.STUDENT;


            return state;
        }
        //need for FIO
       private int calculationPoint(String line){
            int count=0;
            char[]buf=line.toCharArray();
            int max= buf.length;
            for(int i=0;i<max;i++)
                if( buf[i]==pointMarker)
                    ++count;

            return count;
        }
        //need for FIO
        private int calculationGap(String line){
            int count=0;
            char[]buf=line.toCharArray();
            int max= buf.length;
            for(int i=0;i<max;i++)
                if( Character.isWhitespace(buf[i]))
                    ++count;

            return count;
        }
        // need for FIO
       private int calculationCapitalLetter(String line){
            int count=0;
            char[]buf=line.toCharArray();
           int max= buf.length;
           for(int i=0;i<max;i++)
               if( Character.isUpperCase(buf[i]))
                   ++count;

            return count;
        }
        //создание студентов на основе данных из файла
        ArrayList<Student> setStudentsAndGroup() throws IOException, Group.AccessDeniedException {
            arrayDataOfFile=createFirstDataBase();
         int max=arrayDataOfFile.size();
         String line="";
         Group currentGroup=null;
            int idStudent=0;
         for (int i=0;i<max;i++) {
            line= arrayDataOfFile.get(i).toString();
               state=searchMarker(line);
                if(state==marker.OTHER)
                    continue;
                if(state==marker.GROUP) {
                    currentGroup = new Group(line);
                    groups.add(currentGroup);
                    continue;
                }
             if(state==marker.STUDENT&& currentGroup!=null) {
                 currentGroup.addStudentInGroup(line, idStudent++, currentGroup);
             }

         }
         updateStudents();
         return students;
        }
//создание групп на основе данных из файла
      private void updateStudents(){
            int maxGroup=groups.size();
            int maxStudentInCurrentGroup=0;
            for(int i=0;i<maxGroup;i++) {
                maxStudentInCurrentGroup=groups.get(i).getStudents().size();
                for (int j = 0; j < maxStudentInCurrentGroup; j++)
                    students.add(groups.get(i).getStudents().get(j));
            }
        }
        ArrayList<Student>getStudents(){
            return students;
        }

        ArrayList<Group> getGroups(){
        return  groups;
        }
//добавление случайных оценок студентам
        void addMarksRandom(int numberOfMarks) throws Student.AccessDeniedException {
            Random random=new Random();
            int maxStudent=students.size();
            for(int i=0;i<maxStudent;i++)
                for(int j=0;j<numberOfMarks;j++)
                    students.get(i).addMark(random.nextInt(6));


            try {
                downloadDataInFileJSON();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//add mark to student
        void setMarkToStudent(Student student, int[]marks) throws Student.AccessDeniedException {
            for(int mark:marks)
                student.addMark(mark);
        }
//накопление статистики по успеваемости студентов и групп
String[][] academicPerformance(Group group){
    String[][] students=new String[group.getStudents().size()][3];
    Student currentStudent=null;
    double averageMark=0.0;
    for(int i=0;i<group.getStudents().size();i++){
        currentStudent=group.getStudents().get(i);
        students[i][0]=Integer.toString(currentStudent.getIDOfStudent());//record of the id Student
        students[i][1]=currentStudent.getNameOfStudent();//record of the Name of Student
        students[i][2]=Double.toString(currentStudent.averageMarks());// record of the average Mark of the student
        }
    return students;
        }
        String[][] academicPerformance(){
            String[][] studentAll=new String[students.size()][3];
            Group curentGroup=null;

            for(int i=0;i<groups.size();i++){
                curentGroup=groups.get(i);
                String[][] studentOfGroup=academicPerformance(curentGroup);
                for(int j=0;j<curentGroup.getStudents().size();j++){
                    studentAll[i][0]=studentOfGroup[j][0];
                    studentAll[i][1]=studentOfGroup[j][1];
                    studentAll[i][2]=studentOfGroup[j][2];
                }
            }

            return studentAll;
        }
//перевод студентов из группы в группу
        Boolean changeStudents(Student firstStudent,Student secondStudent) throws Exception {
            int countChange=0;
            Group firstGroup,secondGroup;
            Boolean firstHead=true;
            Boolean secondHead=true;
            firstGroup= firstStudent.getGroupOfStudent();
            secondGroup=secondStudent.getGroupOfStudent();
            if(firstGroup.getHead()==firstStudent)
                firstHead=false;
            if(firstGroup.getHead()==secondStudent)
                secondHead=false;
            firstStudent.getGroupOfStudent().removeStudentFromGroup(firstStudent);
            secondStudent.getGroupOfStudent().removeStudentFromGroup(secondStudent);

            firstGroup.getStudents().add(secondStudent);
            secondGroup.getStudents().add(firstStudent);

            secondStudent.changeGroupOfStudent(secondStudent,firstGroup);
            firstStudent.changeGroupOfStudent(firstStudent,secondGroup);

            if(firstGroup==secondStudent.getGroupOfStudent()&&secondGroup==firstStudent.getGroupOfStudent()){
                if(secondHead==false)
                    addHeadInGroupRandom(secondGroup);
                if(firstHead==false)
                    addHeadInGroupRandom(firstGroup);
                downloadDataInFileJSON();
            return true;
            }

            return false;
        }
//отчисление студентов за неуспеваемость
        Boolean removeStudent(String fio) throws Exception {
Boolean studentHead=false;

            for(Group currentGroup:groups)
                for(Student person:currentGroup.getStudents())
                    if(person.getNameOfStudent().equals(fio)){
                        if(currentGroup.getHead()==person){
                            studentHead=true;
                            removeHeadInGroup(currentGroup);
                        }
                        currentGroup.removeStudentFromGroup(person);
                        students.remove(person);
                        if(studentHead==true)
                            addHeadInGroupRandom(currentGroup);
                        downloadDataInFileJSON();// safe of change
                        return true;
                    }
            return false;
        }

//сохранение обновленных данных в файлах
        String downloadDataInFileJSON () throws IOException {
            FileWriter writer=new FileWriter(fileJSON);
            JSONArray objectArray= new JSONArray();
            JSONObject object;
           int maxStudent=students.size();
           int mark=0;
            for(int i=0;i<maxStudent;i++) {
                object = new JSONObject();
                object.put("title group", students.get(i).getGroupOfStudent().getTitle());
                object.put("fio", students.get(i).getNameOfStudent());

                object.put("id",Integer.toString(students.get(i).getIDOfStudent()));

                int sizeMarks=students.get(i).getMarksOfStudent().length;
                JSONArray marks = new JSONArray();
                   for(int j=0;j<sizeMarks;j++) {
                        mark=students.get(i).getMarksOfStudent()[j];
                        marks.add(mark);
                   }
                    object.put("marks",marks);
                //writer.write(object.toJSONString());
                objectArray.add(object);
                writer.flush();
                }
            int maxGroup=groups.size();

            for(int i=0;i<maxGroup;i++) {
                object = new JSONObject();
                try{
                    object.put("head", groups.get(i).getHead().getNameOfStudent());
                    object.put("group", groups.get(i).getTitle());

            }catch (NullPointerException exc){
                   // System.out.println("the group:"+groups.get(i).getTitle()+" haven't head");
                    continue;
                }
               // writer.write(object.toJSONString());
               objectArray.add(object);
                writer.flush();
            }
            writer.write(objectArray.toJSONString());
            writer.flush();
            writer.close();
            return fileJSON.getName();
        }
 // download data from JSON file
        ArrayList<Student> downloadDataFromJSON() throws IOException, ParseException, Group.AccessDeniedException, Student.AccessDeniedException {

            String titleGroup="";
          //  String currentNameGroup="";
            String fio="";
            Group currentGroup;
            int id=0;
            JSONParser parser = new JSONParser();
            JSONArray objectArray=new JSONArray();

           try {

               objectArray = (JSONArray) parser.parse(new FileReader(fileJSON));
           }catch (ParseException e){
               System.err.println(e);
               }
JSONObject object;
          // String nameHead="";
         //  String group;
           String value="";
           Student currentStudent;
            int maxRecords=objectArray.size();
            for (int i=0;i<maxRecords;i++){

            object=(JSONObject) objectArray.get(i);
                fio=(String) object.get("fio");
                if(object.containsKey("head")){// this mean that you record all students
                    // add head
                    for(Group g:groups) {
                        String name=object.get("group").toString();
                        if (g.getTitle().equals(name))
                            g.setHeadOfGroup(g.searchStudent(object.get("head").toString()));
                    }
                    continue;
                }

                titleGroup=(String)object.get("title group");
               currentGroup= setGroup(titleGroup);



             try {
                 boolean check=object.containsKey("id");
                 value=(String) object.get("id");
                 id = Integer.parseInt(value);
             }catch (Exception exc){
                 System.out.println("Problem download in json :id-"+i+", fio: "+fio);
             }
             //currentStudent=currentGroup.addStudentInGroup(fio,id,currentGroup);

               ArrayList<Student>a=currentGroup.addStudentInGroup(fio,id,currentGroup);
                currentStudent=a.get(a.size()-1);
                this.students.add(currentStudent);

                JSONArray bufMarks= (JSONArray) object.get("marks");
                int []marks=new int[bufMarks.size()];
                for (int j=0; j<bufMarks.size();j++) {
                    Object m =bufMarks.get(j);
                    marks[j] = m.hashCode();
                }
                    this.setMarkToStudent(currentStudent,marks);
            }


            return students;
        }
private Group setGroup(String nameGroup){
            Group currentGroup;
            if(groups.size()!=0) {
                int max = groups.size();
                for (Group group : groups) {
                    if (group.getTitle().equals(nameGroup))
                        return group;
                }
            }
                currentGroup = new Group(nameGroup);
                groups.add(currentGroup);

                return currentGroup;
        }
//инициация выборов старост в группах
        Student addHeadInGroupRandom(Group group) throws Exception {
            int numberOfStudents=group.getStudents().size();
            int random=(int)(Math.random()*numberOfStudents);
            Student studentHead=group.getStudents().get(random);
            group.setHeadOfGroup(studentHead);
            downloadDataInFileJSON();
            return group.getHead();
        }
        Student removeHeadInGroup(Group group) throws Exception {
            if(group.getStudents().size()>0){
            group.removeHeadOfGroup();
            return group.getHead();
            } else
                return null;
        }
        Group getGroupOfStudent(Student student){
           if (groups.size()>0) {
               for (Group group : groups)
                   if (group.getHead() == student)
                       return group;
           }
            return null;
        }
//вывод данных на консоль
        void printAcademicPerformance(String students[][]){
            System.out.println("ID"+"   "+"Name of Student"+"     " +"Average mark");
            System.out.println();
            for(int i=0;i<students.length;i++){
                System.out.printf("%3i/t%s/t%.2f" ,Integer.parseInt(students[i][0]),students[i][1],Double.parseDouble(students[i][2]));
            }

        }

        Student searchStudent(String name){
            Student student;
            for(Group group:groups)
               if( (student=group.searchStudent(name))!=null) return student;
        return null;
        }
        Student searchStudent(int id){
            Student student;
            for(Group group:groups)
                if( (student=group.searchStudent(id))!=null) return student;
            return null;
        }
        Group getGroup(String nameOfGroup){
            for(Group group:getGroups())
                if(group.getTitle().equals(nameOfGroup))
                    return group;
            return null;
        }
    }

