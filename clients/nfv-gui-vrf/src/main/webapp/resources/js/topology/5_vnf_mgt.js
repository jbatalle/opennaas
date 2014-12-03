/**
 * Home page. Show the topology and allows to click on the nodes in order to see the information about it.
 *
 */
var file = "vnf_mgt";
document.getElementById("ui-id-3").className += " ui-state-highlight";

function runtime(node, links, controller, cloudON) {
    cloudON
        .on('mousedown', function (d) {
            // select node
            mousedown_node = d;
            if (mousedown_node === selected_node) selected_node = null;
            else selected_node = mousedown_node;
            selected_link = null;
            $(document).on("dragstart", function () {
                return false;
            }); //disable drag in Firefox 3.0 and later
            // reposition drag line
            console.log("Mouse down");
            console.log(mousedown_node);
            drag_line
                .style('marker-end', 'url(#end-arrow)')
                .classed('hidden', false)
                .attr('d', 'M' + mousedown_node.x + ',' + mousedown_node.y + 'L' + mousedown_node.x + ',' + mousedown_node.y);
console.log("DRag");
            //restart();
        })
        .on("contextmenu", function(d, index) {
            console.log(this);
             if(contextMenuShowing) {
                d3.event.preventDefault();
                d3.select(".popup_context_menu").remove();
                contextMenuShowing = false;
            } else {
                console.log(d);
                d3_target = d3.select(d3.event.target);
                d3.event.preventDefault();
                contextMenuShowing = true;

                canvas = d3.select("#chart");
                mousePosition = d3.mouse(canvas.node());
                popup = canvas.append("div")
                    .attr("class", "popup_context_menu")
                    .style("left", d3.event.clientX + "px")
                    .style("top", d3.event.clientY + "px")
                    .style("text-align", "left");
                popup.append("h3").text("OpenNaaS NFV "+d.id);
                popup.append("font").attr("style", "font-size: 1.2em; margin-bottom: 0; font-weight: bold;").text("IP: ").append("font").attr("style", "font-size: 13px; color: black; margin-bottom: 0; font-weight: normal;").text(d.ip);
                popup.append("h4").attr("style", "margin-bottom: 0; font-weight: bold;").text("Managed controllers: ");
                cloudLinks.forEach(function(entry){
                    if(entry.source.id === d.id) {
                       console.log(controllers[entry.target.id]);
                       popup.append("li").text(controllers[entry.target.id].name);
                   }
                });
                popup.append("p");
                popup.append("h4").attr("style", "margin-bottom: 0; font-weight: bold;").text("Pull of available servers: ");
                pullServers.forEach(function(entry) {
                    console.log(entry);
                    if(!entry.used){
                    popup.append("li").text(entry.name +". IP: "+entry.ip).
                    append("a").style("cursor", "pointer")
                            .attr({"xlink:href": "#"})
                            .on("mousedown", function(){
                                pullServers[entry.id].used = true;
                                newCloud = {id: d.id+1, name: "VNF"+(d.id+1), ip: entry.ip, type: "cloud", fixed: true, x: 400, y: 17, pullServers: pullServers};
                                addCloud(newCloud);
                                updateCloud(d);
                                updatePullServers(pullServers);
//                                vnfMgtAction("vnf"+(d.id+1), entry.ip);
                                vnfMgt();
                        })
                    .text("Clone");
                    }
                });
                popup.append("p").attr("style", "margin-top: 1.25em").append("a").style("cursor", "pointer").attr({"xlink:href": "#"})
                    .on("mousedown", function(){ clearLocalStorage(); restoreVNFs(); vnfMgt();}).text("Remove VNF");
        }
//              stop showing browser menu
//              d3.event.preventDefault();
        });
        
    controller
        .on('mouseup', function (d) {
            console.log("MouseUP");
    console.log(d);
            if (!mousedown_node) return;
            // needed by FF
            drag_line
                .classed('hidden', true)
                .style('marker-end', '');
            // check for drag-to-self
            mouseup_node = d;
            if (mouseup_node === mousedown_node) {
                resetMouseVars();
                return;
            }
            // add link to graph (update if exists)
            // NB: links are strictly source < target; arrows separately specified by booleans
            var source, target, newSource;
            source = mousedown_node;
            target = mouseup_node;
//            clLinks = getStorage("cloudLinks");
            clLinks = sessvars.cloudLinks;
            clLinks.forEach(function(entry){
                console.log(target);
                console.log(entry.target);
                console.log(entry.target.name == target.name);
                if(entry.target.name === target.name){
                    console.log("Set");
                    entry.source = source;
                }
            });
            console.log(clLinks);
            setStorage("cloudLinks", clLinks);
            result = vnfMgtAction(source.name, source.ip, target.controller);
            console.log(result);
//            cloudLinks
//            updateLinks();
        });
}

/**
 * Call OpenNaaS get Flow Table
 * @param {type} vrfName
 * @param {type} ctrlIP
 * @returns {undefined}
 */
function vnfMgtAction(vrfName, ctrlIP) {
    $.ajax({
        type: "GET",
        url: "vnfmgt/" + vrfName+"/"+ctrlIP,
        success: function (data) {
            $('#ajaxUpdate').html(data);
            window.location.reload();
        },
        error: function (data) {
            return false;
        }
    });
}

function vnfMgt() {
    $.ajax({
        type: "GET",
        url: "vnfmgt",
        success: function (data) {
            $('#ajaxUpdate').html(data);
            window.location.reload();
        },
    });
}

function mousemove() {
    if (!mousedown_node) return;
    // update drag line
    drag_line.attr('d', 'M' + mousedown_node.x + ',' + mousedown_node.y + 'L' + d3.mouse(this)[0] + ',' + d3.mouse(this)[1]);

}
// app starts here
svg.on('mousemove', mousemove)
    .on('mouseup', mouseup);

function restoreVNFs(){
    $.ajax({
        type: "GET",
        url: "http://admin:123456@84.88.40.189:8888/opennaas/vrf/routemgt/changeVRFControllers/84.88.40.189/VNF1",
        success: function (data) {
//            window.location.reload();
        },
    });
    $.ajax({
        type: "GET",
        url: "http://admin:123456@84.88.40.90:8888/opennaas/vrf/routemgt/changeVRFControllers/84.88.40.189/VNF1",
        success: function (data) {
            window.location.reload();
        },
    });
}