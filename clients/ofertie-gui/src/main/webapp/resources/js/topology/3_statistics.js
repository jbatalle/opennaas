/*
 * Animation page. Show the topology and the Routing Table.
 *
 */
var file = 'animation';//this javascript file corresponds to a animation page
document.getElementById("ui-id-2").className += " ui-state-highlight";

function showGraph(swId, portId) {
    //clear div
    $("#graphContent").html('<div id="axis0"></div><div id="chartStats" style="width: 75%;"></div><div id="axis1"></div><div id="legend"></div>');
    console.log(swId);
    console.log(portId);
    console.log($("#chartStats").width());
//    data = "demoElement";
    var dataJson = getPortStatistic(swId, portId);
console.log(dataJson);
    var tv = 1000;

    var graph, data, i, max, min, point, random, scales, series, _i, _j, _k, _l, _len, _len1, _len2, _ref;

    scales = [];
    min = 0;
    max = 1000;
    scales.push(d3.scale.linear().domain([min, max]).nice());
    scales.push(d3.scale.pow().domain([min, 0.01]).nice());
    var throughput = new Rickshaw.Graph({
        element: document.querySelector("#chartStats"),
        width: $("#chartStats").width(),//500
        height: "400",
        renderer: "line",
        series: new Rickshaw.Series.FixedDuration([{
                name: 'throughput', color: 'orange'
            }, {name: 'packetLoss', color: 'red'}], undefined, {
            timeInterval: tv,
            maxDataPoints: 100,
            timeBase: new Date().getTime() / 1000
        })//, scales: scales[0]
    });
    new Rickshaw.Graph.Axis.Y.Scaled({
        element: document.getElementById('axis0'),
        graph: throughput,
        orientation: 'left',
        scale: scales[0],
        tickFormat: Rickshaw.Fixtures.Number.formatKMBT
    });
    new Rickshaw.Graph.Axis.Y.Scaled({
        element: document.getElementById('axis1'),
        graph: throughput,
        orientation: 'right',
        scale: scales[1],
        tickFormat: Rickshaw.Fixtures.Number.formatKMBT
    });
    new Rickshaw.Graph.Axis.Time({
        graph: throughput
    });
    var legend = new Rickshaw.Graph.Legend({
        element: document.querySelector('#legend'),
        graph: throughput
    });

    throughput.render();
    promise = setInterval(function () {
//        var dataJson = getSwitchStatistic(swId);
getPortStatistic(swId, portId);
console.log(back_data);
dataJson = back_data;
        console.log(dataJson);
        var data = {throughput: dataJson.timedPortStatistics.statistics.statistic[0].throughput * 1000, 
            packetLoss: dataJson.timedPortStatistics.statistics.statistic[0].packetLoss * 1000};
        console.log(data.throughput);
        throughput.series.addData(data);
        throughput.render();

    }, tv);
}