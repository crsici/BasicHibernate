package onetomany;

import javax.persistence.*;
import java.util.List;

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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @OneToMany(mappedBy = "email",fetch = FetchType.EAGER)
    List<Message> messages;

    public Email(String subject) {
        setSubject(subject);
    }

    public Email() {

    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
