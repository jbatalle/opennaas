#!/bin/bash

curl http://localhost:8080/wm/staticflowentrypusher/clear/00:00:00:00:00:00:00:01/json
curl http://localhost:8080/wm/staticflowentrypusher/clear/00:00:00:00:00:00:00:02/json
curl http://localhost:8080/wm/staticflowentrypusher/clear/00:00:00:00:00:00:00:04/json

curl http://localhost:8080/wm/staticflowentrypusher/list/00:00:00:00:00:00:00:01/json
curl http://localhost:8080/wm/staticflowentrypusher/list/00:00:00:00:00:00:00:04/json

./shell.py
