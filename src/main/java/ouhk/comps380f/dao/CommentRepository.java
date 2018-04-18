package ouhk.comps380f.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ouhk.comps380f.model.Comments;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    
    public List<Comments> findByItemId(long item_id);
}
