package pbo.f01.model;

import javax.persistence.*;

@Entity
@Table(name = "dorms")

public class Dorm {
 
    private String name;
    private int capasity;
    private String gender;

    public Dorm() {}

    public Dorm(String name, int capasity, String gender) {
        this.name = name;
        this.capasity = capasity;
        this.gender = gender;

    }

    public String getname(){
        return name;
    }
    public  void setName(String name){
        this.name = name;
    }

    public int getCapasity(){
        return capasity;
    }
    public  void setCapasity(int capasity){
        this.capasity = capasity;
    }

    public String getgender(){
        return gender;
    }
    public  void setGender(String gender){
        this.gender = gender;
    }
 
 
}

