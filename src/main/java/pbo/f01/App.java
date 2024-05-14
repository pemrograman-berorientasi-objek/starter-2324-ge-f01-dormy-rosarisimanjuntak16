package pbo.f01;

import pbo.f01.model.Dorm;
import pbo.f01.model.Student;
import pbo.f01.model.Assignment;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

public class App {
    private static EntityManagerFactory emf;
    private static EntityManager entityManager;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("pbo-f01");
        entityManager = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("---")) {
                break;
            }

            String[] tokens = input.split("#");
            String command = tokens[0];

            switch (command) {
                case "display-all":
                    displayAll();
                    break;
                case "student-add":
                    String studentId = tokens[1];
                    String studentName = tokens[2];
                    int entranceYear = Integer.parseInt(tokens[3]);
                    String gender = tokens[4];
                    registerStudent(studentId, studentName, entranceYear, gender);
                    break;
                case "dorm-add":
                    String dormName = tokens[1];
                    int capacity = Integer.parseInt(tokens[2]);
                    String dormGender = tokens[3];
                    registerDorm(dormName, capacity, dormGender);
                    break;
                case "assign":
                    String studentIdToAssign = tokens[1];
                    String dormNameToAssign = tokens[2];
                    assignStudentToDorm(studentIdToAssign, dormNameToAssign);
                    break;
                default:
                    System.out.println("Invalid command.");
                    break;
            }
        }

        scanner.close();
        entityManager.close();
        emf.close();
    }

    private static void displayAll() {
        TypedQuery<Dorm> dormQuery = entityManager.createQuery("SELECT d FROM Dorm d ORDER BY d.name", Dorm.class);
        List<Dorm> dorms = dormQuery.getResultList();

        for (Dorm dorm : dorms) {
            System.out.println(dorm.getname() + "|" + dorm.getgender() + "|" + dorm.getCapasity() + "|" + getOccupancy(dorm));
            TypedQuery<Student> studentQuery = entityManager.createQuery("SELECT s FROM Student s WHERE s.dorm = :dorm ORDER BY s.name", Student.class);
            studentQuery.setParameter("dorm", dorm);
            List<Student> students = studentQuery.getResultList();
            for (Student student : students) {
                System.out.println(student.getId() + "|" + student.getname() + "|" + student.getAngkatan());
            }
        }
    }

    
    private static void registerStudent(String id, String name, int angkatan, String gender) {
        entityManager.getTransaction().begin();
        Student student = new Student(id, name, gender, angkatan);
        entityManager.persist(student);
        entityManager.getTransaction().commit();
    }

    private static void registerDorm(String name, int capacity, String gender) {
        entityManager.getTransaction().begin();
        Dorm dorm = new Dorm(name, capacity, gender);
        entityManager.persist(dorm);
        entityManager.getTransaction().commit();
    }

    private static void assignStudentToDorm(String studentId, String dormName) {
        entityManager.getTransaction().begin();
        Student student = entityManager.find(Student.class, studentId);
        Dorm dorm = entityManager.find(Dorm.class, dormName);
        student.setDorm(dorm);
        entityManager.persist(student);
        entityManager.getTransaction().commit();
    }

    private static int getOccupancy(Dorm dorm) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(s) FROM Student s WHERE s.dorm = :dorm", Long.class);
        query.setParameter("dorm", dorm);
        return query.getSingleResult().intValue();
    }
}
