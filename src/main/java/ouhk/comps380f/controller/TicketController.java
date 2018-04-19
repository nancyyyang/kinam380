package ouhk.comps380f.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.exception.AttachmentNotFound;
import ouhk.comps380f.exception.TicketNotFound;
import ouhk.comps380f.model.Attachment;
import ouhk.comps380f.model.Comments;
import ouhk.comps380f.model.Item;
import ouhk.comps380f.service.AttachmentService;
import ouhk.comps380f.service.CommentService;
import ouhk.comps380f.service.TicketService;
import ouhk.comps380f.service.HistoryService;
import ouhk.comps380f.view.DownloadingView;

@Controller
@RequestMapping("ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private HistoryService historyService;
    
    @Autowired
    private CommentService commentService;

    @Autowired
    private AttachmentService attachmentService;

    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String list(ModelMap model, Principal principal) {
        String name = "";
        model.addAttribute("ticketDatabase", ticketService.getTickets());
        if(principal != null){
        model.addAttribute("name", principal.getName());}
        else{
            name = "Guest";
            model.addAttribute("name", name);
        }
            
        return "list";
    }
    
     @RequestMapping(value = "history", method = RequestMethod.GET)
    public String historyList(ModelMap model, Principal principal) {
        model.addAttribute("historyDatabase", historyService.getHistorysByName(principal.getName()));
        model.addAttribute("name", principal.getName());
        return "history";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView create() {
        return new ModelAndView("add", "ticketForm", new Form());
    }
    
    @RequestMapping(value = "view/comment/{ticketId}", method = RequestMethod.GET)
    public ModelAndView comment() {
        return new ModelAndView("comment", "ticketForm", new Form());
    }
    

    public static class Form {

        private String Name;
        private String description;
        private String winner;
        private String status;
        private int price;
        private int bidPrice;
        private List<MultipartFile> attachments;

        public String getName() {
            return Name;
        }

        public int getBidPrice() {
            return bidPrice;
        }

        public void setBidPrice(int bidPrice) {
            this.bidPrice = bidPrice;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }

        public String getWinner() {
            return winner;
        }

        public void setWinner(String winner) {
            this.winner = winner;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(Form form, Principal principal) throws IOException {
        long ticketId = ticketService.createTicket(principal.getName(),
                form.getName(), form.getDescription(), form.getPrice(), form.getAttachments());
        return "redirect:/ticket/view/" + ticketId;
    }
    
    @RequestMapping(value = "view/comment/{ticketId}", method = RequestMethod.POST)
    public String createComment(@PathVariable("ticketId") int ticketId, Form form, Principal principal) throws IOException {
        commentService.createComment(principal.getName(),form.getDescription(),ticketId);
        return "redirect:/ticket/view/" + ticketId;
    }
    
    @RequestMapping(value = "view/{ticketId}/deleteComment/{commentId}", method = RequestMethod.GET)
    public String deleteComment(@PathVariable("ticketId") int ticketId,@PathVariable("commentId") int commentId) throws IOException {
        commentService.deleteComments(commentId);
        return "redirect:/ticket/view/" + ticketId;
    }

    @RequestMapping(value = "view/{ticketId}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("ticketId") long ticketId,
            ModelMap model, Principal principal) throws IOException, TicketNotFound{
        String newName = "";
        Item ticket = ticketService.getTicket(ticketId);
        List<Comments> comments=commentService.getComments(ticketId);
        ModelAndView modelAndView = new ModelAndView("view");
        modelAndView.addObject("ticket", ticket);
        modelAndView.addObject("comments", comments);
        Form ticketForm = new Form();
        ticketForm.setBidPrice((int)ticket.getCurrent_price()+1);
        modelAndView.addObject("ticketForm", ticketForm);
        if (principal == null)
        {newName = "guest";}
        model.addAttribute("name", newName);
        return modelAndView;
    }

    @RequestMapping(value = "view/{ticketId}", method = RequestMethod.POST)
    public String viewUpdate(@PathVariable("ticketId") long ticketId, Form form, Principal principal) throws IOException, TicketNotFound {
        Item item=ticketService.getTicket(ticketId);
        if(!principal.getName().equals(item.getOwner())){
        ticketService.updateBiddingPrice(ticketId, (int)form.getBidPrice(), principal.getName());
        
        historyService.createHistory(principal.getName(),form.getBidPrice(),item.getName(),ticketId);
        return "redirect:/ticket/list";
        }
        else{
            ticketService.updateStatus(ticketId, form.getWinner(), form.getStatus());
            return "redirect:/ticket/list";
        }
        
    }

    @RequestMapping(
            value = "/{ticketId}/attachment/{attachment:.+}",
            method = RequestMethod.GET
    )
    public View download(@PathVariable("ticketId") long ticketId,
            @PathVariable("attachment") String name) {

        Attachment attachment = attachmentService.getAttachment(ticketId, name);
        if (attachment != null) {
            return new DownloadingView(attachment.getName(),
                    attachment.getMimeContentType(), attachment.getContents());
        }
        return new RedirectView("/ticket/list", true);
    }

    @RequestMapping(value = "delete/{ticketId}", method = RequestMethod.GET)
    public String deleteTicket(@PathVariable("ticketId") long ticketId)
            throws TicketNotFound {
        ticketService.delete(ticketId);
        return "redirect:/ticket/list";
    }

    @RequestMapping(value = "edit/{ticketId}", method = RequestMethod.GET)
    public ModelAndView showEdit(@PathVariable("ticketId") long ticketId,
            Principal principal, HttpServletRequest request) {
        Item ticket = ticketService.getTicket(ticketId);
        if (ticket == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(ticket.getOwner()))) {
            return new ModelAndView(new RedirectView("/ticket/list", true));
        }

        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("ticket", ticket);

        Form ticketForm = new Form();
        ticketForm.setName(ticket.getName());
        ticketForm.setPrice((int)ticket.getPrice());
        ticketForm.setDescription(ticket.getDescription());
        modelAndView.addObject("ticketForm", ticketForm);

        return modelAndView;
    }

    @RequestMapping(value = "edit/{ticketId}", method = RequestMethod.POST)
    public View edit(@PathVariable("ticketId") long ticketId, Form form,
            Principal principal, HttpServletRequest request)
            throws IOException, TicketNotFound {
        Item ticket = ticketService.getTicket(ticketId);
        if (ticket == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(ticket.getOwner()))) {
            return new RedirectView("/ticket/list", true);
        }

        ticketService.updateTicket(ticketId, form.getName(),form.getPrice(),
                form.getDescription(), form.getAttachments());
        return new RedirectView("/ticket/view/" + ticketId, true);
    }

    @RequestMapping(
            value = "/{ticketId}/delete/{attachment:.+}",
            method = RequestMethod.GET
    )
    public String deleteAttachment(@PathVariable("ticketId") long ticketId,
            @PathVariable("attachment") String name) throws AttachmentNotFound {
        ticketService.deleteAttachment(ticketId, name);
        return "redirect:/ticket/edit/" + ticketId;
    }

}
