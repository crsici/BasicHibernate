package model;

import javax.persistence.*;

/**
 * Created by pthanhtrung on 4/25/2016.
 */
@Entity
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    String subject;
    @OneToOne// (mappedBy = "email")
            Message message;

    public Email(String subject) {
        setSubject(subject);
    }

    public Email(){

    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
