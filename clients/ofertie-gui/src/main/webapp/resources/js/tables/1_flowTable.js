function ConvertJsonToFlowTable(parsedJson, tableId, tableClassName) {
    
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

    if (parsedJson) {
        var isStringArray = typeof (parsedJson[0]) === 'string';
        var headers;

        headers = array_keys(parsedJson[0]);
//        headers[0] = "Name";
        headers[0] = "IP Src";
        headers[1] = "IP Dst";
        headers[2] = "Ether-type";
//        headers[3] = "In";
        headers[3] = "Out";

        for (i = 0; i < headers.length; i++)
            thCon += thRow.format(headers[i]);

        var arr_size;
        try{    
            headers = array_keys(parsedJson.floodlightOFFlows.floodlightOFFlows.forwardingRules[0]);
            arr_size = parsedJson.floodlightOFFlows.floodlightOFFlows.forwardingRules.length;
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
		var regex = new RegExp("192.168.[0-9].9[0-9]");
                for (i = 0; i < arr_size; i++) {
                    if(parsedJson.floodlightOFFlows.floodlightOFFlows.forwardingRules[i].actions.value != -2 &&
			!parsedJson.floodlightOFFlows.floodlightOFFlows.forwardingRules[i].match.srcIp.match(regex)){
    //                    tbCon += tdRow.format(parsedJson.floodlightOFFlows.floodlightOFFlows.forwardingRules[i].name);
                        tbCon += tdRow.format(parsedJson.floodlightOFFlows.floodlightOFFlows.forwardingRules[i].match.srcIp);
                        tbCon += tdRow.format(parsedJson.floodlightOFFlows.floodlightOFFlows.forwardingRules[i].match.dstIp);
                        tbCon += tdRow.format(parsedJson.floodlightOFFlows.floodlightOFFlows.forwardingRules[i].match.etherType);
                        //tbCon += tdRow.format(parsedJson.floodlightOFFlows.floodlightOFFlows.forwardingRules[i].match.ingressPort);
                        tbCon += tdRow.format(parsedJson.floodlightOFFlows.floodlightOFFlows.forwardingRules[i].actions.value);
                        trCon += tr.format(tbCon);
                        tbCon = '';
                    }
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

function removeFlowAll(){
    var table = document.getElementById("jsonFlowTable");
    try{
        for(var i = table.rows.length - 1; i > 0; i--){
           table.deleteRow(i);
      }
    }catch(e){}
}