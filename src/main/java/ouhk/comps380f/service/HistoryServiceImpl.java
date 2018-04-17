package ouhk.comps380f.service;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ouhk.comps380f.dao.AttachmentRepository;
import ouhk.comps380f.dao.HistoryRepository;
import ouhk.comps380f.exception.AttachmentNotFound;
import ouhk.comps380f.exception.TicketNotFound;
import ouhk.comps380f.model.Attachment;
import ouhk.comps380f.model.History;

@Service
public class HistoryServiceImpl implements HistoryService{
    
    @Resource
    private HistoryRepository historyRepo;
    
    @Override
    @Transactional
    public List<History> getHistorys() {
        return historyRepo.findAll();
    }

     
    @Override
    @Transactional
    public void createHistory(String name, float bidPrice,String productName,long id) throws IOException {
        History his = new History();
        his.setName(name);
        his.setBidPrice(bidPrice);
        his.setProductName(productName);
        his.setItem_id(id);
        History savedTicket = historyRepo.save(his);
        
    }
}
