package org.opennaas.gui.nfvrouting.services.rest;

import java.util.concurrent.Future;

/**
 *
 * @author Josep Batallé <josep.batalle@i2cat.net>
 */
public interface ReportService {
    public Future<String> generateReport();  
}
