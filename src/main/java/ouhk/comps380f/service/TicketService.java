package ouhk.comps380f.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import ouhk.comps380f.exception.AttachmentNotFound;
import ouhk.comps380f.exception.TicketNotFound;
import ouhk.comps380f.model.Item;

public interface TicketService {

    public long createTicket(String customerName, String name,
            String description, float price, List<MultipartFile> attachments) throws IOException;

    public List<Item> getTickets();

    public Item getTicket(long id);

    public void updateTicket(long id, String subject,
            String body, List<MultipartFile> attachments)
            throws IOException, TicketNotFound;
    
    public void updateBiddingPrice(long id,float bidPrice, String winner)
            throws IOException, TicketNotFound;
    
    public void addHistoryBiddingPrice(long id,float bidPrice, String user)
            throws IOException, TicketNotFound;

    public void delete(long id) throws TicketNotFound;

    public void deleteAttachment(long ticketId, String name)
            throws AttachmentNotFound;
    public void updateStatus(long id, String winner, String status)
            throws IOException, TicketNotFound;
}
