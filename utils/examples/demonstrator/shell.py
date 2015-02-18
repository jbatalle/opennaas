#!/bin/bash

ctrl="localhost:8080"
sudo /etc/init.d/shellinabox stop
sudo killall shellinaboxd
sudo shellinaboxd --disable-ssl -s /h1/:SSH:10.0.1.1 -s /h2/:SSH:10.0.1.2  -s /opennaas:demo:grp:HOME:'ssh karaf@localhost -p 8101' &

#!/bin/bash
sudo ifconfig s1 10.0.1.98
sudo ifconfig s4 10.0.1.99

sudo route add 10.0.1.1 s1
sudo route add 10.0.1.2 s4

curl -d '{"switch": "00:00:00:00:00:00:00:01", "name":"flow-mod-11", "cookie":"0", "priority":"32768", "ether-type":"0x0800", "dst-ip":"10.0.1.1", "src-ip":"10.0.1.98","active":"true", "actions":"output=3"}' http://$ctrl/wm/staticflowentrypusher/json
curl -d '{"switch": "00:00:00:00:00:00:00:01", "name":"flow-mod-12", "cookie":"0", "priority":"32768", "ether-type":"0x0800", "src-ip":"10.0.1.1", "dst-ip":"10.0.1.98","active":"true", "actions":"output=65534"}' http://$ctrl/wm/staticflowentrypusher/json
curl -d '{"switch": "00:00:00:00:00:00:00:01", "name":"flow-mod-13", "cookie":"0", "priority":"32768", "ether-type":"0x0806", "dst-ip":"10.0.1.1", "src-ip":"10.0.1.98","active":"true", "actions":"output=3"}' http://$ctrl/wm/staticflowentrypusher/json
curl -d '{"switch": "00:00:00:00:00:00:00:01", "name":"flow-mod-14", "cookie":"0", "priority":"32768", "ether-type":"0x0806", "src-ip":"10.0.1.1", "dst-ip":"10.0.1.98","active":"true", "actions":"output=65534"}' http://$ctrl/wm/staticflowentrypusher/json

curl -d '{"switch": "00:00:00:00:00:00:00:04", "name":"flow-mod-41", "cookie":"0", "priority":"32768", "ether-type":"0x0800", "dst-ip":"10.0.1.2", "src-ip":"10.0.1.99","active":"true", "actions":"output=3"}' http://$ctrl/wm/staticflowentrypusher/json
curl -d '{"switch": "00:00:00:00:00:00:00:04", "name":"flow-mod-42", "cookie":"0", "priority":"32768", "ether-type":"0x0800", "src-ip":"10.0.1.2", "dst-ip":"10.0.1.99","active":"true", "actions":"output=65534"}' http://$ctrl/wm/staticflowentrypusher/json
curl -d '{"switch": "00:00:00:00:00:00:00:04", "name":"flow-mod-43", "cookie":"0", "priority":"32768", "ether-type":"0x0806", "dst-ip":"10.0.1.2", "src-ip":"10.0.1.99","active":"true", "actions":"output=3"}' http://$ctrl/wm/staticflowentrypusher/json
curl -d '{"switch": "00:00:00:00:00:00:00:04", "name":"flow-mod-44", "cookie":"0", "priority":"32768", "ether-type":"0x0806", "src-ip":"10.0.1.2", "dst-ip":"10.0.1.99","active":"true", "actions":"output=65534"}' http://$ctrl/wm/staticflowentrypusher/json

echo ""
