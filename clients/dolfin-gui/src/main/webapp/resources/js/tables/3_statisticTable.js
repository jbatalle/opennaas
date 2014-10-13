function ConvertJsonToStatisticTable(parsedJson, tableId, tableClassName) {
    
     waiting(true);
//showHidePreloader(true);    
     //Pattern for table                          
    var idMarkup = tableId ? ' id="' + tableId + '"' : '';
    var classMarkup = tableClassName ? ' class="' + tableClassName + '"' : '';
    var tbl = '<table border="1" cellpadding="1" cellspacing="1"' + idMarkup + classMarkup + '>{0}{1}</table>';

    //Patterns for table content
    var th = '<thead>{0}</thead>';
    var tb = '<tbody>{0}</tbody>';
    var tr = '<tr>{0}</tr>';
    var thRow = '<th>{0}</th>';
    var tdRow = '<td class="{1}">{0}</td>';
    var thCon = '';
    var tbCon = '';
    var trCon = '';
console.log(parsedJson);
    if (parsedJson) {
        var isStringArray = typeof (parsedJson[0]) === 'string';
        var headers;

        headers = array_keys(parsedJson[0]);
//        headers[0] = "Name";
        headers[0] = "Switch Id";
        headers[1] = "Port Id";
        headers[2] = "Throughput";
        headers[3] = "Packet Loss";

        for (i = 0; i < headers.length; i++)
            thCon += thRow.format(headers[i]);

        var arr_size;
        
        try{    
//            headers = array_keys(parsedJson.timedPortStatistics.statistics[0]);
            arr_size = parsedJson.timedPortStatistics.statistics.length;
        }catch (e){
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
                for (i = 0; i < arr_size; i++) {
                    tbCon += tdRow.format(parsedJson.timedPortStatistics.statistics[i].switchId, "swId");
                    tbCon += tdRow.format(parsedJson.timedPortStatistics.statistics[i].portId, "pId");
                    tbCon += tdRow.format(parsedJson.timedPortStatistics.statistics[i].throughput, "thpt green");
                    tbCon += tdRow.format(parsedJson.timedPortStatistics.statistics[i].packetLoss, "pktl");
                    trCon += tr.format(tbCon);
                    tbCon = '';
                }
            }
        }
        tb = tb.format(trCon);
        tbl = tbl.format(th, tb);
setTimeout( 'waiting(false)', 1000);
//showHidePreloader(false);
        return tbl;
    }
    return null;
}

function ConvertJsonToCircuitStatisticTable(parsedJson, tableId, tableClassName) {
    
     waiting(true);
//showHidePreloader(true);    
     //Pattern for table                          
    var idMarkup = tableId ? ' id="' + tableId + '"' : '';
    var classMarkup = tableClassName ? ' class="' + tableClassName + '"' : '';
    var tbl = '<table border="1" cellpadding="1" cellspacing="1"' + idMarkup + classMarkup + '>{0}{1}</table>';

    //Patterns for table content
    var th = '<thead>{0}</thead>';
    var tb = '<tbody>{0}</tbody>';
    var tr = '<tr>{0}</tr>';
    var thRow = '<th>{0}</th>';
    var tdRow = '<td>{0}</td>';
    var thCon = '';
    var tbCon = '';
    var trCon = '';
console.log(parsedJson);
    if (parsedJson) {
        var headers;
        //slaFlowId", "throughput", "packetLoss", "delay", "jitter", "flowData"];

//        headers = array_keys(parsedJson[0]);
        headers = [];
        headers[0] = "SLA Flow Id";
        headers[1] = "Throughput";
        headers[2] = "Packet Loss";
        headers[3] = "Delay";
        headers[4] = "Jitter";
        headers[5] = "Flow Data";

        for (i = 0; i < headers.length; i++)
            thCon += thRow.format(headers[i]);

        var arr_size;
        try{    
//            headers = array_keys(parsedJson.floodlightOFFlows.floodlightOFFlow[0]);
            arr_size = parsedJson.length;
        }catch (e){
            headers = [];
            arr_size = 0;
        }
        th = th.format(tr.format(thCon));

        // Create table rows from Json data
        if (headers) {
            for (i = 0; i < arr_size; i++) {
                tbCon += tdRow.format(parsedJson[i].slaFlowId);
                tbCon += tdRow.format(parsedJson[i].throughput);
                tbCon += tdRow.format(parsedJson[i].packetLoss);
                tbCon += tdRow.format(parsedJson[i].delay);
                tbCon += tdRow.format(parsedJson[i].jitter);
                tbCon += tdRow.format(parsedJson[i].flowData);
                trCon += tr.format(tbCon);
                tbCon = '';
            }
        }
        
        tb = tb.format(trCon);
        tbl = tbl.format(th, tb);
        setTimeout( 'waiting(false)', 1000);
//showHidePreloader(false);
        return tbl;
    }
    return null;
}

var statisticSession = {};
statisticSession.switchId = "";
statisticSession.portId = "";

function getSwitchStatistic(switchId){
    console.log("Get Statistic");
    $.ajax({
        type: "GET",
        url: "ajax/portStatistics/"+switchId,
        success: function(data) {
            $('#ajaxUpdate').html(data);    
            var json = convertXml2JSon(data);
            data = "";
            json = eval("("+json+")");
            console.log(json);
            var jsonHtmlTable = ConvertJsonToStatisticTable(json, 'jsonStatisticTable', null);
            document.getElementById("jsonStatisticTable").innerHTML = jsonHtmlTable;
        }
    });
    
    statisticSession.switchId = switchId;
    statisticSession.portId = "";
}

function updateStatistics(){
    if(statisticSession.switchId !== "" && statisticSession.portId !== ""){
        getPortStatistic(statisticSession.switchId, statisticSession.portId);
    }
    else{
        getSwitchStatistic(statisticSession.switchId);
    }
}

function checkTableValues(){
    updateStatistics();
    var t = document.getElementById("jsonStatisticTable");
    var elements = t.getElementsByClassName("pktl");
    
    for (var i = 0; i < elements.length; i++) {
        if( elements[i].innerHTML > 5 )
            elements[i].className = elements[i].className + ' red';
        else
            elements[i].classList.remove('red');
    }
    var elements = t.getElementsByClassName("thpt");
    for (var i = 0; i < elements.length; i++) {
        if( elements[i].innerHTML > 10 )
            elements[i].className = elements[i].className + ' green';
        else
            elements[i].classList.remove('green');
    }
}

function getPortStatistic(switchId, portName){
    console.log("Get Statistic");

    $.ajax({
        type: "GET",
        url: "ajax/portStatistics/"+switchId,
        success: function(data) {
            $('#ajaxUpdate').html(data);    
            var json = convertXml2JSon(data);
            data = "";                        
            json = eval("("+json+")");
            console.log(json);
            var json = jsonStatisticsGivenPort(json.timedPortStatistics, portName);
            var jsonHtmlTable = ConvertJsonToStatisticTable(json, 'jsonStatisticTable', null);
            document.getElementById("jsonStatisticTable").innerHTML = jsonHtmlTable;
        }
    });
    
    statisticSession.switchId = switchId;
    statisticSession.portId = portName;
}

function jsonStatisticsGivenPort(json, portName){
    var timedPortStatistics = new Object;
    var statistics = [];
    console.log(json);
    for(i=0; i<json.statistics.length; i++){
        if(json.statistics[i].portId === portName){
            statistics.push(json.statistics[i]);
        }
    }
    var newJson = new Object;
    timedPortStatistics.statistics = statistics;
    newJson.timedPortStatistics = timedPortStatistics;
    return newJson;
}

function getCircuitStatistic(){
    $.ajax({
        type: "GET",
        url: "ajax/circuitStatistics",
        success: function(data) {
            json = csvJSON(data);
            data = "";
            var jsonHtmlTable = ConvertJsonToCircuitStatisticTable(json, 'jsonCircuitStatisticTable', null);
            console.log(jsonHtmlTable);
            document.getElementById("jsonCircuitStatisticTable").innerHTML = jsonHtmlTable;
        }
    });
}

function csvJSON(csv){
    var lines=csv.split("\n");
    var result = [];
//    var headers=lines[0].split(",");
    var headers = ["timestamp", "slaFlowId", "throughput", "packetLoss", "delay", "jitter", "flowData"];
    for(var i=0;i<lines.length;i++){
        var obj = {};
        var currentline=lines[i].split(",");
        for(var j=0;j<headers.length;j++){
            obj[headers[j]] = currentline[j];
        }
        result.push(obj);
    }
    return result; //JavaScript object
    //return JSON.stringify(result); //JSON
}