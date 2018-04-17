package ouhk.comps380f.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ouhk.comps380f.model.Comments;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    
}
