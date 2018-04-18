package ouhk.comps380f.service;

import java.io.IOException;
import java.util.List;
import ouhk.comps380f.model.Comments;


public interface CommentService {
    
    public long createComment(String name,String content, long item_id) throws IOException;

    public List<Comments> getComments(long item_id);
    
    public void deleteComments(long item_id);
}
