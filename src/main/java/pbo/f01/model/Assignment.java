package pbo.f01.model;

import javax.persistence.*;


@Entity
@Table(name = "assignment")

public class Assignment {
 
    @Id
    private Long id;
    

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "dorm_name")
    private String dorm;  


    public Assignment(Student student2, Dorm dorm2) {}

    public Assignment(Student student, String dorm) {
        this.student = student;
        this.dorm = dorm;

    }

    public Long getId(){
        return id;
    }
    public  void setId(Long id){
        this.id = id;
    }

    public Student getStudent(){
        return student;
    }
    public  void setStudent(Student student){
        this.student = student;
    }

    public String getDorm(){
        return dorm;
    }
    public  void setDorm(String dorm){
        this.dorm = dorm;
    }

}
