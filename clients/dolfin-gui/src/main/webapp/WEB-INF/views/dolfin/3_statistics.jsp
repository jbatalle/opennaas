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
</script>
<div id="resources_list">
    <div id="accordion">
        <c:forEach items="${listSwitches}" var="item">
            <h3><a href="#" onclick="javascript:getSwitchStatistic('${item.dpid}');return false;">${item.dpid}</a></h3>
            <div>
            <c:forEach items="${item.ports}" var="port">
                <p><a href="#" onclick="javascript:getPortStatistic('${item.dpid}', '${port}');return false;">${port}</a></p>
            </c:forEach>
            </div>
        </c:forEach>
    </div>
</div>
<div id="statistics">
    <br>
    <a id="myLink" title="Call circuit statistics" href="#" onclick="javascript:checkTableValues();return false;">Change color</a>
    <a id="myLink" title="Call circuit statistics" href="#" onclick="javascript:getCircuitStatistic();return false;">Circuit statistics</a>
    <table id="jsonStatisticTable" class="tablesorter" border="1"></table>
    <table id="jsonCircuitStatisticTable" class="tablesorter" border="1"></table>
</div>

<script language="JavaScript" type="text/JavaScript">
//    console.log(${xml});
/*    var test = getAllCircuits();
    var jsonObject = test;
    console.log(jsonObject);
    if(jsonObject.circuits.circuit.length == 0 || jsonObject.circuits == null){
            document.getElementById("innerTable").innerHTML = '<table id="jsonTable" class="tablesorter"></table>';
            document.getElementById("innerTable2").innerHTML = '<table id="jsonQoS" class="tablesorter"></table>';
        }
    for ( var i = 0; i < jsonObject.circuits.circuit.length; i++){
        document.getElementById("listRoutes").innerHTML += '<a style="text-decoration:none" href="javascript:void(0)" onclick="getSpecificCircuit(\''+jsonObject.circuits.circuit[i].circuitId+'\', 0)"><span id="innerTextRoute">Circuit: '+jsonObject.circuits.circuit[i].circuitId+'.</span></a><br/>';
    }
*/    
</script>

<div class="modal"></div>
<script>
    if ( $(window).height() > 450 ){
        $(".innera").height(($(window).height() - 400)/2);
    }
    
    setInterval(function(){
        
//        var jsonObject = getAllCircuits();
        console.log(jsonObject);
        document.getElementById("listRoutes").innerHTML = "";
        for ( var i = 0; i < jsonObject.circuits.circuit.length; i++){
            document.getElementById("listRoutes").innerHTML += '<a style="text-decoration:none" href="javascript:void(0)" onclick="getSpecificCircuit(\''+jsonObject.circuits.circuit[i].circuitId+'\', 0)"><span id="innerTextRoute">Circuit: '+jsonObject.circuits.circuit[i].circuitId+'.</span></a><br/>';
        }
        if(jsonObject.circuits.circuit.length == 0 || jsonObject.circuits == null){
            document.getElementById("innerTable").innerHTML = '<table id="jsonTable" class="tablesorter"></table>';
            document.getElementById("innerTable2").innerHTML = '<table id="jsonQoS" class="tablesorter"></table>';
        }
        
    }, ${settings.circuitUpdateTime}*10000);//5000
    
</script>
