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
    <table id="jsonStatisticTable" class="tablesorter" border="1"></table>
    <table id="jsonCircuitStatisticTable" class="tablesorter" border="1"></table>
</div>

<div class="modal"></div>
<script>
    
    setInterval(function(){
        updateStatistics();
    }, ${settings.statisticsUpdateTime}*1000);//5000
    
</script>
