package ouhk.comps380f.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ouhk.comps380f.model.History;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
