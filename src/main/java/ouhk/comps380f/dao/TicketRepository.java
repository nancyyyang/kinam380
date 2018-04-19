package ouhk.comps380f.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ouhk.comps380f.model.Item;

public interface TicketRepository extends JpaRepository<Item, Long> {
}
