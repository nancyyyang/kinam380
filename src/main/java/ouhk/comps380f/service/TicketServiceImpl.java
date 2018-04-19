package ouhk.comps380f.service;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ouhk.comps380f.dao.AttachmentRepository;
import ouhk.comps380f.dao.TicketRepository;
import ouhk.comps380f.exception.AttachmentNotFound;
import ouhk.comps380f.exception.TicketNotFound;
import ouhk.comps380f.model.Attachment;
import ouhk.comps380f.model.Item;

@Service
public class TicketServiceImpl implements TicketService {

    @Resource
    private TicketRepository ticketRepo;

    @Resource
    private AttachmentRepository attachmentRepo;

    @Override
    @Transactional
    public List<Item> getTickets() {
        return ticketRepo.findAll();
    }

    @Override
    @Transactional
    public Item getTicket(long id) {
        return ticketRepo.findOne(id);
    }

    @Override
    @Transactional(rollbackFor = TicketNotFound.class)
    public void delete(long id) throws TicketNotFound {
        Item deletedTicket = ticketRepo.findOne(id);
        if (deletedTicket == null) {
            throw new TicketNotFound();
        }
        ticketRepo.delete(deletedTicket);
    }

    @Override
    @Transactional(rollbackFor = AttachmentNotFound.class)
    public void deleteAttachment(long ticketId, String name) throws AttachmentNotFound {
        Item ticket = ticketRepo.findOne(ticketId);
        for (Attachment attachment : ticket.getAttachments()) {
            if (attachment.getName().equals(name)) {
                ticket.deleteAttachment(attachment);
                ticketRepo.save(ticket);
                return;
            }
        }
        throw new AttachmentNotFound();
    }

    @Override
    @Transactional
    public long createTicket(String customerName, String name,
            String description, float price, List<MultipartFile> attachments) throws IOException {
        Item ticket = new Item();
        ticket.setOwner(customerName);
        ticket.setName(name);
        ticket.setDescription(description);
        ticket.setPrice((float) price);
        ticket.setCurrent_price((float) price);
        ticket.setNum_of_bid(0);
        ticket.setStatus("Available");
        ticket.setWinner("");

        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setTicket(ticket);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                ticket.getAttachments().add(attachment);
            }
        }
        Item savedTicket = ticketRepo.save(ticket);
        return savedTicket.getId();
    }

    @Override
    @Transactional(rollbackFor = TicketNotFound.class)
    public void updateTicket(long id, String subject, int price,
            String body, List<MultipartFile> attachments)
            throws IOException, TicketNotFound {
        Item updatedTicket = ticketRepo.findOne(id);
        if (updatedTicket == null) {
            throw new TicketNotFound();
        }

        updatedTicket.setName(subject);
        updatedTicket.setDescription(body);
        updatedTicket.setPrice(price);
        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setTicket(updatedTicket);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0
                    && !updatedTicket.getAttachments().contains(attachment)) {
                updatedTicket.getAttachments().add(attachment);
            }
        }
        ticketRepo.save(updatedTicket);
    }

    @Override
    @Transactional(rollbackFor = TicketNotFound.class)
    public void updateBiddingPrice(long id, float bidPrice, String winner)
            throws IOException, TicketNotFound {
        Item updatedTicket = ticketRepo.findOne(id);
        if (updatedTicket == null) {
            throw new TicketNotFound();
            
        }
        updatedTicket.setCurrent_price((float) bidPrice);
        updatedTicket.setNum_of_bid(updatedTicket.getNum_of_bid()+1);
        updatedTicket.setWinner(winner);
        ticketRepo.save(updatedTicket);
    }
    @Override
    @Transactional(rollbackFor = TicketNotFound.class)
    public void addHistoryBiddingPrice(long id,float bidPrice, String user)
            throws IOException, TicketNotFound {
        Item updatedTicket = ticketRepo.findOne(id);
        if (updatedTicket == null) {
            throw new TicketNotFound();
            
        }
        updatedTicket.setCurrent_price((float) bidPrice);
        updatedTicket.setNum_of_bid(updatedTicket.getNum_of_bid()+1);
        updatedTicket.setWinner(user);
        ticketRepo.save(updatedTicket);
    }
    @Override
    @Transactional(rollbackFor = TicketNotFound.class)
    public void updateStatus(long id, String winner, String status)
            throws IOException, TicketNotFound {
        Item updatedTicket = ticketRepo.findOne(id);
        if (updatedTicket == null) {
            throw new TicketNotFound();
            
        }
        updatedTicket.setWinner(winner);
        updatedTicket.setStatus(status);
        ticketRepo.save(updatedTicket);
    }
    
}
