package org.opennaas.gui.dolfin.entities.settings;

/**
 *
 * @author Josep Batallé <josep.batalle@i2cat.net>
 */
public class Settings {
    
    private String addShellMode = "window"; //window/tab
    private String circuitColor = "#ccffff";//"#81DAF5"
    private String colorPktL = "#ff0000";//"#81DAF5"
    private String colorThpt = "#00ff00";//"#81DAF5"
    private String circuitUpdateTime = "5";//seconds
    private String statisticsUpdateTime = "5";//seconds
    private String genNetResName = "ofnet1";//"ofnet1"
    private String packetLossMax = "5";//%
    private String thoughput = "10";//MB/s

    public Settings() {
        this.addShellMode = "window";
    }

    public String getAddShellMode() {
        return addShellMode;
    }

    public void setAddShellMode(String addShellMode) {
        this.addShellMode = addShellMode;
    }

    public String getCircuitColor() {
        return circuitColor;
    }

    public void setCircuitColor(String circuitColor) {
        this.circuitColor = circuitColor;
    }

    public String getColorPktL() {
        return colorPktL;
    }

    public void setColorPktL(String colorPktL) {
        this.colorPktL = colorPktL;
    }

    public String getColorThpt() {
        return colorThpt;
    }

    public void setColorThpt(String colorThpt) {
        this.colorThpt = colorThpt;
    }

    public String getCircuitUpdateTime() {
        return circuitUpdateTime;
    }

    public void setCircuitUpdateTime(String circuitUpdateTime) {
        this.circuitUpdateTime = circuitUpdateTime;
    }

    public String getStatisticsUpdateTime() {
        return statisticsUpdateTime;
    }

    public void setStatisticsUpdateTime(String statisticsUpdateTime) {
        this.statisticsUpdateTime = statisticsUpdateTime;
    }
    
    public String getGenNetResName() {
        return genNetResName;
    }

    public void setGenNetResName(String genNetResName) {
        this.genNetResName = genNetResName;
    }

    public String getPacketLossMax() {
        return packetLossMax;
    }

    public void setPacketLossMax(String packetLossMax) {
        this.packetLossMax = packetLossMax;
    }

    public String getThoughput() {
        return thoughput;
    }

    public void setThoughput(String thoughput) {
        this.thoughput = thoughput;
    }
    
}

