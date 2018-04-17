package ouhk.comps380f.service;

import java.io.IOException;
import java.util.List;
import ouhk.comps380f.model.Comments;


public interface CommentService {
    
    public long createComment(String name,String content, int item_id) throws IOException;

    public List<Comments> getComments(long id);
}
