function ConvertJsonToStatisticTable(parsedJson, tableId, tableClassName) {
console.log(parsedJson);
console.log(tableId);
console.log(tableClassName);
var switchId = parsedJson.timedPortStatistics.statistics.statistic[0].switchId;
    //waiting(true);
//showHidePreloader(true);
    //Pattern for table
    var idMarkup = tableId ? ' id="' + tableId + '"' : '';
    var classMarkup = tableClassName ? ' class="' + tableClassName + '"' : '';
    var tbl = '<table border="1" cellpadding="1" cellspacing="1"' + idMarkup + classMarkup + '>{0}{1}</table>';

    //Patterns for table content
    var th = '<thead>{0}</thead>';
    var tb = '<tbody>{0}</tbody>';
    var tr = '<tr>{0}</tr>';
    var thRow = '<th style="text-align:center">{0}</th>';
    var tdRow = '<td class="{1}" style="text-align:center">{0}</td>';
    var thCon = '';
    var tbCon = '';
    var trCon = '';

    if (parsedJson) {
        var isStringArray = typeof (parsedJson[0]) === 'string';
        var headers;

        headers = array_keys(parsedJson[0]);
//        headers[0] = "Name";
        headers[0] = "Timestamp";
        headers[1] = "Switch Id";
        headers[2] = "Port Id";
        headers[3] = "Throughput (Mbit/s)";
        headers[4] = "Packet Loss (%)";
        headers[5] = "Graph";

        for (i = 0; i < headers.length; i++)
            thCon += thRow.format(headers[i]);

        var arr_size;

        try {
//            headers = array_keys(parsedJson.timedPortStatistics.statistics[0]);
            arr_size = parsedJson.timedPortStatistics.statistics.statistic.length;
        } catch (e) {
            headers = [];
            arr_size = 0;
        }
        th = th.format(tr.format(thCon));

        // Create table rows from Json data
        if (isStringArray) {
            /*            for (i = 0; i < parsedJson.length; i++) {
             tbCon += tdRow.format(parsedJson[i]);
             trCon += tr.format(tbCon);
             tbCon = '';
             }
             */
        } else {
            if (headers) {
                console.log("Head");
                console.log(arr_size);
                for (i = 0; i < arr_size; i++) {
                    console.log(parsedJson.timedPortStatistics.statistics.statistic[i]);
                    var date = new Date(parseInt(parsedJson.timedPortStatistics.statistics.statistic[i].timestamp));
                    var options = {
                        year: "numeric", month: "numeric",
                        day: "numeric", hour: "2-digit", minute: "2-digit", second: "2-digit"
                    };
                    var swId = parsedJson.timedPortStatistics.statistics.statistic[i].switchId;
                    var portId = parsedJson.timedPortStatistics.statistics.statistic[i].portId;
                    tbCon += tdRow.format(date.toLocaleTimeString("en-us", options), "timeStamp");
                    tbCon += tdRow.format(parsedJson.timedPortStatistics.statistics.statistic[i].switchId, "swId");
                    tbCon += tdRow.format(parsedJson.timedPortStatistics.statistics.statistic[i].portId, "pId");
                    tbCon += tdRow.format((parseFloat(parsedJson.timedPortStatistics.statistics.statistic[i].throughput) * 1000).toFixed(3), "thpt");
                    tbCon += tdRow.format(parsedJson.timedPortStatistics.statistics.statistic[i].packetLoss, "pktl");
                    tbCon += tdRow.format('<a href="#" onclick="javascript:showGraph(\''+swId+'\', \''+portId+'\');return false;">View</a>');
                    trCon += tr.format(tbCon);
                    tbCon = '';
                }
            }
        }
        tb = tb.format(trCon);
        tbl = tbl.format(th, tb);
//setTimeout( 'waiting(false)', 1000);
//showHidePreloader(false);
        return tbl;
    }
    return null;
}

function ConvertJsonToCircuitStatisticTable(parsedJson, tableId, tableClassName) {

//     waiting(true);
//showHidePreloader(true);
    //Pattern for table
    var idMarkup = tableId ? ' id="' + tableId + '"' : '';
    var classMarkup = tableClassName ? ' class="' + tableClassName + '"' : '';
    var tbl = '<table border="1" cellpadding="1" cellspacing="1"' + idMarkup + classMarkup + '>{0}{1}</table>';

    //Patterns for table content
    var th = '<thead>{0}</thead>';
    var tb = '<tbody>{0}</tbody>';
    var tr = '<tr>{0}</tr>';
    var thRow = '<th  style="text-align:center">{0}</th>';
    var tdRow = '<td  style="text-align:center" class="{1}">{0}</td>';
    var thCon = '';
    var tbCon = '';
    var trCon = '';

    if (parsedJson) {
        var headers;
        //slaFlowId", "throughput", "packetLoss", "delay", "jitter", "flowData"];

//        headers = array_keys(parsedJson[0]);
        headers = [];
        headers[0] = "SLA Flow Id";
        headers[1] = "Throughput (Gb/s)";
        headers[2] = "Packet Loss (%)";
        headers[3] = "Delay (ms)";
        headers[4] = "Jitter (ms)";
        headers[5] = "Flow Data";

        for (i = 0; i < headers.length; i++)
            thCon += thRow.format(headers[i]);

        var arr_size;
        try {
//            headers = array_keys(parsedJson.floodlightOFFlows.floodlightOFFlow[0]);
            arr_size = parsedJson.length;
        } catch (e) {
            headers = [];
            arr_size = 0;
        }
        th = th.format(tr.format(thCon));

        // Create table rows from Json data
        if (headers) {
            for (i = 0; i < arr_size; i++) {
                if (parsedJson[i].slaFlowId == undefined)
                    continue;
                tbCon += tdRow.format(parsedJson[i].slaFlowId);
                tbCon += tdRow.format(parsedJson[i].throughput, "thpt");
                tbCon += tdRow.format(parsedJson[i].packetLoss, "pktl");
                tbCon += tdRow.format(parseFloat(parsedJson[i].delay / 1000).toFixed(3));
                tbCon += tdRow.format(parseFloat(parsedJson[i].jitter / 1000).toFixed(3));
                tbCon += tdRow.format(parsedJson[i].flowData);
                trCon += tr.format(tbCon);
                tbCon = '';
            }
        }

        tb = tb.format(trCon);
        tbl = tbl.format(th, tb);
        setTimeout('waiting(false)', 1000);
//showHidePreloader(false);
        return tbl;
    }
    return null;
}

var statisticSession = {};
statisticSession.switchId = "";
statisticSession.portId = "";

function showTable(id) {
    var tb = document.getElementById(id);
    tb.style.display = "";

}

function getSwitchStatistic(switchId) {
    statistic = "switch";
    hideControllerStatistic();
    hideCircuitStatistic();
    showTable("jsonStatisticTable");
    console.log("Get Statistic");
    console.log("Switch id:" + switchId);
    if (switchId === "demoElement") {
        statisticSession.switchId = switchId;
        statisticSession.portId = "";
        json = {};

        var xml = '<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><timedPortStatistics><statistics><statistic><timestamp>1</timestamp><switchId>switch1</switchId><portId>p1</portId><throughput>10</throughput><packetLoss>0</packetLoss></statistic><statistic><timestamp>2</timestamp><switchId>switch1</switchId><portId>p2</portId><throughput>10</throughput><packetLoss>1</packetLoss></statistic><statistic><timestamp>3</timestamp><switchId>switch2</switchId><portId>p3</portId><throughput>5</throughput><packetLoss>14</packetLoss></statistic></statistics></timedPortStatistics>';
        var json = convertXml2JSon(xml);
        data = "";
        json = eval("(" + json + ")");
        console.log(json);
        var jsonHtmlTable = ConvertJsonToStatisticTable(json, 'jsonStatisticTable', null);
        document.getElementById("jsonStatisticTable").innerHTML = jsonHtmlTable;
        changeTdColorValues("jsonStatisticTable");
        return json;
    }
    $.ajax({
        type: "GET",
        url: "ajax/portStatistics/" + switchId,
        success: function (data) {
            $('#ajaxUpdate').html(data);
            var json = convertXml2JSon(data);
            data = "";
            json = eval("(" + json + ")");
            console.log(json);
            var jsonHtmlTable = ConvertJsonToStatisticTable(json, 'jsonStatisticTable', null);
            document.getElementById("jsonStatisticTable").innerHTML = jsonHtmlTable;
            changeTdColorValues("jsonStatisticTable");
        }
    });

    statisticSession.switchId = switchId;
    statisticSession.portId = "";
}

function getPortStatistic(switchId, portName) {
    clearInterval(refreshIntervalId);
    statistic = "port";
    hideControllerStatistic();
    hideCircuitStatistic();
    showTable("jsonStatisticTable");
    console.log("Get Statistic");
    console.log(switchId);
    console.log(portName);
if (switchId === "demoPort" && portName === "p1") {
        statisticSession.switchId = switchId;
        statisticSession.portId = "";
        json = {};

        var xml = '<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><timedPortStatistics><statistics><statistic><timestamp>1</timestamp><switchId>switch1</switchId><portId>p1</portId><throughput>10</throughput><packetLoss>0</packetLoss></statistic><statistic><timestamp>2</timestamp><switchId>switch1</switchId><portId>p2</portId><throughput>10</throughput><packetLoss>1</packetLoss></statistic><statistic><timestamp>3</timestamp><switchId>switch2</switchId><portId>p3</portId><throughput>5</throughput><packetLoss>14</packetLoss></statistic></statistics></timedPortStatistics>';
        var json = convertXml2JSon(xml);
        data = "";
        json = eval("(" + json + ")");
        console.log(json);
var json = jsonStatisticsGivenPort(json.timedPortStatistics, portName);
        var jsonHtmlTable = ConvertJsonToStatisticTable(json, 'jsonStatisticTable', null);
        document.getElementById("jsonStatisticTable").innerHTML = jsonHtmlTable;
        changeTdColorValues("jsonStatisticTable");
        return;
    }
    $.ajax({
        type: "GET",
        url: "ajax/portStatistics/" + switchId,
        success: function (data) {
            $('#ajaxUpdate').html(data);
            var json = convertXml2JSon(data);
            data = "";
            json = eval("(" + json + ")");
            console.log(json);
            var json = jsonStatisticsGivenPort(json.timedPortStatistics, portName);
            back_data = json;
            var jsonHtmlTable = ConvertJsonToStatisticTable(json, 'jsonStatisticTable', null);
            document.getElementById("jsonStatisticTable").innerHTML = jsonHtmlTable;
            changeTdColorValues("jsonStatisticTable");
        }
    });

    statisticSession.switchId = switchId;
    statisticSession.portId = portName;
}

/**
 * Update statistics function. Is called each $statisticsUpdateTime seconds
 * @returns {undefined}
 */
function updateStatistics(statistic) {
    if (statistic == "circuit") {
        getCircuitStatistic();
        statistic = "circuit";
    } else if (statistic == "controller") {
        getControllerStatistic();
        statistic = "controller";
    } else if (statisticSession.switchId !== "" && statisticSession.portId !== "") {
        getPortStatistic(statisticSession.switchId, statisticSession.portId);
        statistic = "port";
    } else if (statisticSession.switchId !== "") {
        getSwitchStatistic(statisticSession.switchId);
        statistic = "switch";
    } else {
        statisticSession.switchId = "";
    }
}

/**
 * Change the color of the packet loss and throughput values
 * @param {type} tableId
 * @returns {undefined}
 */
function changeTdColorValues(tableId) {
    var t = document.getElementById(tableId);
    var elements = t.getElementsByClassName("pktl");
    for (var i = 0; i < elements.length; i++) {
        if (parseInt(elements[i].innerHTML) >= packetLossAlert) {
            elements[i].style.backgroundColor = colorPktL;
            elements[i].style.fontWeight = "bold";
        } else
            elements[i].style.color = "black";
    }
    elements = t.getElementsByClassName("thpt");
    for (var i = 0; i < elements.length; i++) {
        if (parseInt(elements[i].innerHTML) >= throughputAlert) {
            elements[i].style.backgroundColor = colorThpt;
            elements[i].style.fontWeight = "bold";
        } else
            elements[i].style.color = "black";
    }
}

function jsonStatisticsGivenPort(json, portName) {
    console.log(json);
    var timedPortStatistics = new Object;
    var statistic = [];
    var statistics = new Object();
    for (i = 0; i < json.statistics.statistic.length; i++) {
        if (json.statistics.statistic[i].portId === portName) {
            statistic.push(json.statistics.statistic[i]);
        }
    }
    statistics.statistic = statistic;
    timedPortStatistics.statistics = statistics;
    var newJson = new Object;
    newJson.timedPortStatistics = timedPortStatistics;
    return newJson;
}

function getCircuitStatistic() {
    statistic = "circuit";
    hideControllerStatistic();
    hideSwitchStatistic();
    showTable("jsonCircuitStatisticTable");
    $.ajax({
        type: "GET",
        url: "ajax/circuitStatistics",
        success: function (data) {
            json = csvJSON(data);
            data = "";
//            console.log(json);
//             console.log(json[0]);
//            console.log(json[0].timestamp == "");
            if (json[0].timestamp == "")
                return;
            var jsonHtmlTable = ConvertJsonToCircuitStatisticTable(json, 'jsonCircuitStatisticTable', null);
            document.getElementById("jsonCircuitStatisticTable").innerHTML = jsonHtmlTable;
//            document.getElementById("circuitStatisticTitle").style.display = "block";
            changeTdColorValues("jsonCircuitStatisticTable");
        }
    });
}

function getControllerStatistic() {
    statistic = "controller";

    //switchId = document.getElementById();
    var tb = document.getElementById("jsonStatisticTable");
    tb.style.display = "none";
    var tb = document.getElementById("jsonCircuitStatisticTable");
    tb.style.display = "none";
    switchId = "openflowswitch:s1";
    var mE = getControllerMemoryUsage(switchId);
    var tb = document.getElementById("memStatus");
    tb.style.display = "block";
    var td = document.getElementById("memory");
    td.innerHTML = mE.memoryUsage.free + "/" + mE.memoryUsage.total + " (" + (parseFloat(mE.memoryUsage.free * 100 / mE.memoryUsage.total)).toFixed(2) + "% )";

    var hS = getHealthState(switchId);
    var td = document.getElementById("status");
    console.log(hS);
    td.innerHTML = hS.healthState.__text;
}

function hideControllerStatistic() {
    var tb = document.getElementById("memStatus");
    tb.style.display = "none";
}

function hideCircuitStatistic() {
//    var tb = document.getElementById("circuitStatisticTitle");
//    tb.style.display="none";
    var tb = document.getElementById("jsonCircuitStatisticTable");
    tb.style.display = "none";
}

function hideSwitchStatistic() {
    var tb = document.getElementById("jsonStatisticTable");
    tb.style.display = "none";
}

function getControllerMemoryUsage(switchId) {
    var xml = "";
    $.ajax({
        type: "GET",
        url: "ajax/memoryUsage/" + switchId,
        async: false,
        success: function (data) {
            xml = data;
        }
    });
    console.log(xml);
    var xmlText = new XMLSerializer().serializeToString(xml);
    var json = convertXml2JSon(xmlText);
    return eval("(" + json + ")");
}

function getHealthState(switchId) {
    $.ajax({
        type: "GET",
        url: "ajax/healthState/" + switchId,
        async: false,
        success: function (data) {
            xml = data;
        }
    });
    console.log(xml);
    var xmlText = new XMLSerializer().serializeToString(xml);
    var json = convertXml2JSon(xmlText);
    return eval("(" + json + ")");
}

function csvJSON(csv) {
    var lines = csv.split("\n");
    var result = [];
//    var headers=lines[0].split(",");
    var headers = ["timestamp", "slaFlowId", "throughput", "packetLoss", "delay", "jitter", "flowData"];
    for (var i = 0; i < lines.length; i++) {
        var obj = {};
        var currentline = lines[i].split(",");
        for (var j = 0; j < headers.length; j++) {
            obj[headers[j]] = currentline[j];
        }
        result.push(obj);
    }
    return result; //JavaScript object
    //return JSON.stringify(result); //JSON
}


