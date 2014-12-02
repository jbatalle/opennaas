package org.opennaas.gui.nfvrouting.services.rest;

import java.util.concurrent.Future;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

/**
 *
 * @author Josep Batallé <josep.batalle@i2cat.net>
 */
public class ReportServiceImpl implements ReportService {  
  
    @Async  
    public Future<String> generateReport() {  
          
        try {  
            Thread.sleep(15000);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
          
        String report = "New Report Description";
          
        return new AsyncResult<String>(report);  
    }  
      
}  