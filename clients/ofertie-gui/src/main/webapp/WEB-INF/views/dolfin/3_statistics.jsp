<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script>
    document.getElementById("ui-id-2").className += " ui-state-highlight";
    var colorPktL = "${settings.colorPktL}";
    var colorThpt = "${settings.colorThpt}";
    var packetLossAlert = "${settings.packetLossMax}";
    var throughputAlert = "${settings.throughput}";
    var promise;
</script>

<div id="resources_list">
    <div id="accordionCircuits">
        <h3 onclick="javascript:getCircuitStatisticButton(); return false;">Circuits</h3>
<!--        <div id="circuitsP" style="height: 90px;"></div>-->
    </div>
    <br/><h3>Network Elements:</h3>
    <div id="accordionStatistics">
        <c:forEach items="${listSwitches}" var="item">
            <h3 onclick="javascript:getSwitchStatistic('${item.dpid}');
                    return false;">${item.dpid}</h3>
            <div>
                <c:forEach items="${item.ports}" var="port">
                    <p><a href="#" onclick="javascript:getPortStatisticButton('${item.dpid}', '${port}');
                            return false;">${port}</a></p>
                    </c:forEach>
            </div>
        </c:forEach>
        <h3><a href="#" onclick="javascript:getControllerStatisticButton();
                return false;">Controller 1</a></h3>
    </div>
<!--    <br/><h3>Demo graph:</h3>
    <div id="accordionStatistics">
        <h3><a href="#" onclick="javascript:getSwitchStatistic('demoElement');
                return false;">Element1</a></h3>
        <div>
            <p><a href="#" onclick="javascript:getPortStatistic('demoPort', 'p1');
                    return false;">Port 1.1</a></p>
        </div>
    </div>-->
</div>
<div id="statistics">
    <table id="jsonStatisticTable" class="tablesorter" border="1" style="margin-bottom: 0; margin-left: 10px"></table>
    <table id="jsonCircuitStatisticTable" class="tablesorter" border="1"></table>
    <table id="memStatus" class="tablesorter" border="1" style="width:45%;display:none;margin-left:7%">
        <tr>
            <td style="border: 1px solid black;font-weight:bold">Memory (free/total)</td>
            <td style="border: 1px solid black;" id="memory"></td>
        </tr>
        <tr>
            <td style="border: 1px solid black;font-weight:bold">Status</td>
            <td style="border: 1px solid black;" id="status"></td>
        </tr>
    </table>
    <br/><span id="selectedId"></span><br/><br/>
    <div id="graphContent" class="content">
        <div id="axis0"></div>
        <div id="chartStats" style="width: 87%;"></div>
        <div id="axis1"></div>
        <div id="legend"></div>
    </div>
</div>

<script src="<c:url value="/resources/js/topology/3_statistics.js" />"></script>
<div class="modal"></div>
<script>
                statistic = "none";
                jsonObject = getAllCircuits();
                if (jsonObject === undefined) {
                    jsonObject = new Object;
                    jsonObject.circuits = new Object;
                    jsonObject.circuits.circuit = [];
                }
                console.log(jsonObject);
                for (var i = 0; i < jsonObject.circuits.circuit.length; i++) {
                    //document.getElementById("accordionCircuits").innerHTML += '<p><a style="text-decoration:none" href="javascript:void(0)" onclick="getCircuitStatistic(\''+jsonObject.circuits.circuit[i].circuitId+'\', 0)">Circuit: '+jsonObject.circuits.circuit[i].circuitId+'</a></p>';
                    document.getElementById("circuitsP").innerHTML += '<p>Circuit ' + i + '</p>';
                }

                setInterval(function () {
                    updateStatistics(statistic);
                }, ${settings.statisticsUpdateTime} * 1000);//5000

                function getAllCircuits() {
                    var xml = "";
                    $.ajax({
                        type: 'GET',
                        url: "ajax/getCircuits",
                        async: false,
                        success: function (data) {
                            xml = data;
                        }
                    });
                    if(xml === "") return;
                    var xmlText = new XMLSerializer().serializeToString(xml);
                    var json = convertXml2JSon(xmlText);
                    return eval("(" + json + ")");
                }
</script>

