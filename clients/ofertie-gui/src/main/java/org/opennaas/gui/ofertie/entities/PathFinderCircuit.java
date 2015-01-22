package org.opennaas.gui.ofertie.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josep Batallé <josep.batalle@i2cat.net>
 */
public class PathFinderCircuit {

    private String sw;
    private String portA;
    private String portB;

    private PathFinderCircuit(){
        
    }

    public String getSW() {
        return sw;
    }

    public void setSW(String sw) {
        this.sw = sw;
    }

    public String getPortA() {
        return portA;
    }

    public void setPortA(String portA) {
        this.portA = portA;
    }

    public String getPortB() {
        return portB;
    }

    public void setPortB(String portB) {
        this.portB = portB;
    }
    
    @Override
    public String toString() {
        return "PathFinderCircuit [sw=" + sw + ", portA= " + portA + ", portB=" + portB + "]";
    }

}
