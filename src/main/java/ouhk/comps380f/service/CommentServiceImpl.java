package ouhk.comps380f.service;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import ouhk.comps380f.dao.CommentRepository;
import ouhk.comps380f.model.Comments;

@Service
public class CommentServiceImpl implements CommentService{
    
    @Resource
    private CommentRepository commentRepo;
    
    public long createComment(String name,String content, int item_id) throws IOException{
        Comments comment = new Comments();
        comment.setName(name);
        comment.setContent(content);
        comment.setTicketId(item_id);
        Comments savedTicket = commentRepo.save(comment);
        return savedTicket.getId();
    }

    public List<Comments> getComments(long id){
        return commentRepo.findAll();
    }
}
