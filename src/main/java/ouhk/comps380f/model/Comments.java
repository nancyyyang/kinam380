package ouhk.comps380f.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comments implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;
    
    @Column(name = "item_id", insertable = false, updatable = false)
    private long ticketId;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item ticket;

    public Comments(){}

    public Comments(String name, String content, long ticketId, Item ticket) {
        this.name = name;
        this.content = content;
        this.ticketId = ticketId;
        this.ticket = ticket;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public Item getTicket() {
        return ticket;
    }

    public void setTicket(Item ticket) {
        this.ticket = ticket;
    }
    
    
    
}
