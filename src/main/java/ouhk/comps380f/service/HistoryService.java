package ouhk.comps380f.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import ouhk.comps380f.model.History;


public interface HistoryService {
    public void createHistory(String name, float bidPrice,String productName,long id) throws IOException;
    
        public List<History> getHistorys();
        
}
