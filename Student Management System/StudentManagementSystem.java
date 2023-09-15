import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

class StudentGUI extends JFrame { // Main GUI window menu
    float balance = 0;
    JLabel Welcome, output, total;
    JButton newStudent, removeStudent, searchStudent, displayAll;
    StudentGUI z;
    database db;

    StudentGUI() {
        db = new database();
        this.setBounds(500, 250, 360, 260);
        this.setLayout(null);
        this.setTitle("Student Management System");
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        JLabel Welcome = new JLabel("Welcome!");
        JLabel output = new JLabel("Choose Function:-");
        JLabel total = new JLabel("Total No of students: " + (db.mainElement.getChildNodes().getLength() - 1));
        z = this;

        Welcome.setFont(new Font("Serif", Font.BOLD, 24));
        Welcome.setBounds(20, 20, 100, 30);
        output.setBounds(20, 60, 180, 20);
        total.setBounds(20, 80, 180, 20);

        JButton newStudent = new JButton("Add student");
        JButton removeStudent = new JButton("Remove student");
        JButton searchStudent = new JButton("Search student");
        JButton displayAll = new JButton("Display all");
        JButton exit = new JButton("Exit");

        newStudent.setBounds(200, 20, 140, 30);
        removeStudent.setBounds(200, 60, 140, 30);
        searchStudent.setBounds(200, 100, 140, 30);
        displayAll.setBounds(200, 140, 140, 30);
        exit.setBounds(200, 180, 140, 30);

        newStudent.addActionListener(new ActionListener() { // Action for adding New Student
            @Override
            public void actionPerformed(ActionEvent e) {
                addDialog add = new addDialog(z, "New Student", true);
                try {
                    db.addStudent(add.student.name, add.student.roll, add.student.grade, add.student.contact);
                    output.setText("New student added");
                    total.setText("Total No of students: " + (db.mainElement.getChildNodes().getLength() - 1));
                } catch (Exception x) {
                    output.setText("Enter valid details for new student");
                }
            }
        });

        removeStudent.addActionListener(new ActionListener() { // Action for removing a student
            @Override
            public void actionPerformed(ActionEvent e) {
                removeDialog remove = new removeDialog(z, "Remove Student", true);
                try {
                    if (db.removeStudent(remove.ADMNO)) {
                        output.setText("Student Removed.");
                        total.setText("Total No of students: " + (db.mainElement.getChildNodes().getLength() - 1));
                    } else {
                        output.setText("No student found with this Admission Number.");
                    }
                } catch (Exception x) {
                    output.setText("No student found with this Admission Number.");
                }
            }
        });

        searchStudent.addActionListener(new ActionListener() { // Action for searching for student/students
            @Override
            public void actionPerformed(ActionEvent e) {
                searchDialog search = new searchDialog(z, "Search Student", true);
                try {
                    Element[] elements = db.searchStudent(search.student.name,
                            search.student.roll,
                            search.student.grade,
                            search.student.contact, search.ADMNO);
                    displayDialog display = new displayDialog(z, "Search Results", true, elements);

                } catch (Exception x) {
                    output.setText("Search failed, Try again");
                }
            }
        });

        displayAll.addActionListener(new ActionListener() { // Action for displaying all Students
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    displayDialog display = new displayDialog(z, "All Students", true, db.displayAll());
                } catch (Exception x) {
                    output.setText("Try Again");
                }
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.add(Welcome);
        this.add(output);
        this.add(total);
        this.add(newStudent);
        this.add(removeStudent);
        this.add(searchStudent);
        this.add(displayAll);
        this.add(exit);
        this.setVisible(true);
    }
}

class addDialog extends JDialog { // Dialog box for adding a new student
    JLabel Name, Roll, Grade, Contact;
    JTextField name, roll, grade, contact;
    Student student;

    addDialog(Frame owner, String title, boolean modality) {
        super(owner, title, modality);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 20), false);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setBounds(500, 250, 380, 300);

        panel.setBackground(Color.LIGHT_GRAY);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        JLabel instruct = new JLabel("Enter new Student details: -");
        JLabel output = new JLabel("");
        JLabel Name = new JLabel("Name: ");
        JLabel Roll = new JLabel("Roll Number: ");
        JLabel Grade = new JLabel("Grade: ");
        JLabel Contact = new JLabel("Contact No.: ");

        output.setBounds(10, 10, 200, 20);

        JTextField name = new JTextField();
        JTextField roll = new JTextField();
        JTextField grade = new JTextField();
        JTextField contact = new JTextField();

        JButton ok = new JButton("OK");

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (name.getText().isEmpty() || roll.getText().isEmpty() || grade.getText().isEmpty()
                        || contact.getText().isEmpty()) {
                    output.setText("Enter complete details");
                } else {
                    student = new Student(name.getText(), roll.getText(), grade.getText(), contact.getText());
                    dispose();
                }
            }
        });

        this.add(panel);
        panel.add(instruct);
        panel.add(output);
        panel.add(Name);
        panel.add(name);
        panel.add(Roll);
        panel.add(roll);
        panel.add(Grade);
        panel.add(grade);
        panel.add(Contact);
        panel.add(contact);
        panel.add(ok);
        this.setVisible(true);
    }
}

class removeDialog extends JDialog { // Dialog box for removing a student
    JLabel AdmNo;
    JTextField admNo;
    Student student;
    String ADMNO;

    removeDialog(Frame owner, String title, boolean modality) {
        super(owner, title, modality);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 20), false);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setBounds(500, 250, 380, 160);

        panel.setBackground(Color.LIGHT_GRAY);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        JLabel instruct = new JLabel("Enter new Student details: -");
        JLabel output = new JLabel("");
        JLabel AdmNo = new JLabel("Admission Number:");

        output.setBounds(10, 10, 200, 20);

        JTextField admNo = new JTextField();

        JButton ok = new JButton("OK");

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((ADMNO = admNo.getText()).isEmpty()) {
                    output.setText("Enter valid Admission No.");
                } else {
                    dispose();
                }
            }
        });

        this.add(panel);
        panel.add(instruct);
        panel.add(output);
        panel.add(AdmNo);
        panel.add(admNo);
        panel.add(ok);
        this.setVisible(true);
    }
}

class searchDialog extends JDialog { // Dialog box for searching student
    JLabel Name, Roll, Grade, Contact, output;
    JTextField name, roll, grade, contact;
    Student student;
    String ADMNO;

    searchDialog(Frame owner, String title, boolean modality) {
        super(owner, title, modality);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 20), false);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setBounds(500, 250, 380, 350);

        panel.setBackground(Color.LIGHT_GRAY);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        JLabel instruct = new JLabel("Enter Student details: -");
        JLabel output = new JLabel("");
        JLabel Name = new JLabel("Name: ");
        JLabel Roll = new JLabel("Roll Number: ");
        JLabel Grade = new JLabel("Grade: ");
        JLabel Contact = new JLabel("Contact No.: ");
        JLabel AdmNo = new JLabel("Admission No.:");

        output.setBounds(10, 10, 200, 20);

        JTextField name = new JTextField();
        JTextField roll = new JTextField();
        JTextField grade = new JTextField();
        JTextField contact = new JTextField();
        JTextField admNo = new JTextField();

        JButton search = new JButton("Search");

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (name.getText().isEmpty() && roll.getText().isEmpty() && grade.getText().isEmpty()
                        && contact.getText().isEmpty() && admNo.getText().isEmpty()) {
                    output.setText("Enter atleast one detail.");
                } else {
                    ADMNO = admNo.getText();
                    if (ADMNO.isEmpty()) {
                        ADMNO = "";
                    }
                    student = new Student(name.getText(), roll.getText(), grade.getText(), contact.getText());
                    dispose();
                }
            }
        });

        this.add(panel);
        panel.add(instruct);
        panel.add(output);
        panel.add(Name);
        panel.add(name);
        panel.add(Roll);
        panel.add(roll);
        panel.add(Grade);
        panel.add(grade);
        panel.add(Contact);
        panel.add(contact);
        panel.add(AdmNo);
        panel.add(admNo);
        panel.add(search);
        this.setVisible(true);
    }
}

class displayDialog extends JDialog { // Dialog box for diplaying Students list

    displayDialog(Frame owner, String title, boolean modality, Element[] elements) {

        super(owner, title, modality);
        Container ControlHost = getContentPane();
        ControlHost.add(new JLabel("Maika mero"));
        setBounds(500, 250, 720, 200);

        JPanel panel = new JPanel(new GridLayout(0, 5, 10, 10));

        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel AdmNo = new JLabel("Admission No.");
        JLabel Name = new JLabel("Name");
        JLabel Roll = new JLabel("Roll No.");
        JLabel Grade = new JLabel("Grade");
        JLabel Contact = new JLabel("Contact");

        Font f = new Font("Serif", Font.BOLD, 20);

        AdmNo.setFont(f);
        Name.setFont(f);
        Roll.setFont(f);
        Grade.setFont(f);
        Contact.setFont(f);

        panel.add(AdmNo);
        panel.add(Name);
        panel.add(Roll);
        panel.add(Grade);
        panel.add(Contact);

        for (int i = 0; i < elements.length; i++) {
            if (elements[i] == null) {
                break;

            }
            JLabel admlabel = new JLabel(elements[i].getAttribute("AdmNo"));
            admlabel.setSize(400, 30);
            panel.add(admlabel);
            NodeList details = elements[i].getChildNodes();
            for (int j = 0; j < details.getLength(); j++) {
                JLabel label = new JLabel(details.item(j).getTextContent());
                label.setSize(400, 30);
                panel.add(label);
            }
        }

        JScrollPane jsp = new JScrollPane(

                panel,

                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,

                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        ControlHost.add(jsp);
        this.setVisible(true);
    }
}

class Student {
    String name, roll, grade, contact;

    Student(String NAME, String ROLL, String GRADE, String CONTACT) {
        if (NAME.isEmpty()) {
            NAME = "";
        }
        if (ROLL.isEmpty()) {
            ROLL = "";
        }
        if (GRADE.isEmpty()) {
            GRADE = "";
        }
        if (CONTACT.isEmpty()) {
            CONTACT = "";
        }
        this.name = NAME;
        this.roll = ROLL;
        this.grade = GRADE;
        this.contact = CONTACT;
    }
}

class database { // Class for managing XML database
    static String currentPath = System.getProperty("user.dir");
    Document document;
    Element mainElement;
    Transformer tFormer;
    Source source;
    Result result;
    int admNo;
    File xmlFile;

    database() {
        try {
            File dir = new File(currentPath, "Database");
            dir.mkdirs();
            this.xmlFile = new File(dir, "xmlTest.xml");
            boolean flag = this.xmlFile.createNewFile();

            if (flag) {
                admNo = 1;
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                this.document = documentBuilder.newDocument();

                Element school = document.createElement("school");
                document.appendChild(school);

                Element admNo = document.createElement("admNo");
                school.appendChild(admNo);

                admNo.appendChild(document.createTextNode("1"));

                this.mainElement = this.document.getDocumentElement();

                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer();
                Source sc = new DOMSource(this.document);
                Result dest = new StreamResult(this.xmlFile);
                transformer.transform(sc, dest);
            } else {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                this.document = documentBuilder.parse(this.xmlFile);

                this.mainElement = this.document.getDocumentElement();
                admNo = Integer.parseInt(this.mainElement.getFirstChild().getTextContent());
            }
            this.tFormer = TransformerFactory.newInstance().newTransformer();
            this.tFormer.setOutputProperty(OutputKeys.METHOD, "xml");
            this.source = new DOMSource(this.document);
            this.result = new StreamResult(this.xmlFile);

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public void addStudent(String NAME, String ROLL, String GRADE, String CONTACT) throws Exception {
        // Function for adding student elements
        Element student = document.createElement("student");
        Element name = document.createElement("name");
        Element roll = document.createElement("roll");
        Element grade = document.createElement("grade");
        Element contact = document.createElement("contact");

        name.setTextContent(NAME);
        roll.setTextContent(ROLL);
        grade.setTextContent(GRADE);
        contact.setTextContent(CONTACT);

        student.setAttribute("AdmNo", Integer.toString(this.admNo++));
        student.appendChild(name);
        student.appendChild(roll);
        student.appendChild(grade);
        student.appendChild(contact);
        this.mainElement.appendChild(student);
        this.mainElement.getFirstChild().setTextContent(Integer.toString(admNo));

        this.document.replaceChild(mainElement, mainElement);
        tFormer.transform(this.source, this.result);
    }

    public boolean removeStudent(String removeNo) throws Exception {
        // Function for removing student elements
        boolean flag = false;
        NodeList nodes = this.mainElement.getElementsByTagName("student");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element student = (Element) nodes.item(i);
            if (removeNo.equals(student.getAttribute("AdmNo"))) {
                this.mainElement.removeChild(nodes.item(i));
                flag = true;
                this.document.replaceChild(mainElement, mainElement);
                tFormer.transform(this.source, this.result);
                break;
            }
        }
        return flag;
    }

    public Element[] searchStudent(String NAME, String ROLL, String GRADE, String CONTACT, String ADMNO) {
        // Function for searching student elements
        NodeList nodes = this.mainElement.getElementsByTagName("student");
        Element[] elements = new Element[nodes.getLength()];

        for (int i = 0; i < nodes.getLength(); i++) {
            elements[i] = (Element) nodes.item(i);
        }

        elements = Arrays.copyOf((searchStudentTag("name", NAME, elements)), elements.length);
        elements = Arrays.copyOf((searchStudentTag("roll", ROLL, elements)), elements.length);
        elements = Arrays.copyOf((searchStudentTag("grade", GRADE, elements)), elements.length);
        elements = Arrays.copyOf((searchStudentTag("contact", CONTACT, elements)), elements.length);
        elements = Arrays.copyOf((searchStudentTag("AdmNo", ADMNO, elements)), elements.length);
        return elements;
    }

    public Element[] searchStudentTag(String tag, String tagValue, Element[] elements) {// Function for searchingdetail
        if (tagValue.isEmpty()) {
            return elements;
        }

        int count = 0;
        Element[] newElements = new Element[elements.length];
        if (tag.equals("AdmNo")) {
            for (int i = 0; i < elements.length; i++) {
                if (elements[i] == null) {
                    continue;
                }
                if (tagValue.equals(elements[i].getAttribute(tag))) {
                    newElements[count++] = elements[i];
                }
            }
            return newElements;
        }
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] == null) {
                continue;
            }
            NodeList nodes = elements[i].getElementsByTagName(tag);
            if (tagValue.equals(nodes.item(0).getTextContent())) {
                newElements[count++] = elements[i];
            }
        }
        return newElements;
    }

    public Element[] displayAll() { // Function for returning all student elements
        NodeList nodes = this.mainElement.getElementsByTagName("student");
        Element[] elements = new Element[nodes.getLength()];

        for (int i = 0; i < nodes.getLength(); i++) {
            elements[i] = (Element) nodes.item(i);
        }
        return elements;
    }
}

public class StudentManagementSystem {
    public static void main(String args[]) {
        StudentGUI gui = new StudentGUI();
        gui.db.displayAll();

    }
}