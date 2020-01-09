package dk.miracle.test;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long key;
    String name;
    String lastName;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Long getKey() { return key;}

    public void setKey(Long key) {this.key = key;}
}
