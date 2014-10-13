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
    document.getElementById("ui-id-3").className += " ui-state-highlight";
</script>
<div id="settings" class="ui-widget-content ui-corner-all routTable padding">
    <h3>Settings</h3>
    <form:form action="" modelAttribute="settings" method="post" enctype="multipart/form-data">
        
        <div class="row">
            <form:label path="addShellMode">Open hosts shell in a: </form:label>
            <form:radiobutton cssStyle="label" path="addShellMode" value="tab"/>Tab
            <form:radiobutton cssStyle="label" path="addShellMode" value="window"/>Window
        </div>
        <div class="row">
            <form:label path="CircuitUpdateTime">Circuit update time: </form:label>
            <form:input path="CircuitUpdateTime" value="${settings.circuitUpdateTime}"/>
        </div>
        <div class="row">
            <form:label path="statisticsUpdateTime">Statistics update time: </form:label>
            <form:input path="statisticsUpdateTime" value="${settings.statisticsUpdateTime}"/>
        </div>
        <div class="row" style="margin-top:10px;">
            <form:label path="circuitColor">Select circuit route color: </form:label>
            <%--                <form:radiobutton cssStyle="label" path="colorDynamicRoutes" value="#81DAF5"/>Blue
                        <form:radiobutton cssStyle="label" path="colorDynamicRoutes" value="green"/>Green
            --%>            
            <form:input class="color1 color" name="color1" type="text" path="circuitColor" value="${settings.circuitColor}"/>
        </div>
        <div class="row" style="margin-top:10px;">
            <form:label path="colorPktL">Select Packet Loss statistics color: </form:label>
            <form:input class="color1 color" name="color2" type="text" path="colorPktL" value="${settings.colorPktL}"/>
        </div>
        <div class="row" style="margin-top:10px;">
            <form:label path="colorThpt">Select Throughput statistics color: </form:label>
            <form:input class="color1 color" name="color3" type="text" path="colorThpt" value="${settings.colorThpt}"/>
        </div>
        <style type="text/css">
            .color1{
                float: left; display: inline; 
            }
            .colorPicker-picker{
                float: left; position: absolute;
                display: inline; margin-left: 3px;
            }
        </style>
        <div class="row">
            <span class="formw">
                <input id="button2" type="submit" value="Save" style="float: right;"/>
            </span>
        </div>
    </form:form>
</div>

<div class="modal"></div>
