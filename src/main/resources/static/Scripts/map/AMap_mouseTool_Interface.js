var AMap_mouseTool_Interface = function (mapControl) {
    var obj = this;
    mapControl.rectZoomIn = function () {
        var rectOptions = {
            strokeStyle: "dashed",
            strokeColor: "#FF33FF",
            fillColor: "#FF99FF",
            fillOpacity: 0.5,
            strokeOpacity: 1,
            strokeWeight: 2
        };
        obj.setCursor(Common_Tools.getCursor("pencle"));
        obj.mouseTool.rectZoomIn(rectOptions);
    };
    mapControl.rectZoomOut = function () {
        var rectOptions = {
            strokeStyle: "dashed",
            strokeColor: "#FF33FF",
            fillColor: "#FF99FF",
            fillOpacity: 0.5,
            strokeOpacity: 1,
            strokeWeight: 2
        };
        obj.setCursor(Common_Tools.getCursor("pencle"));
        obj.mouseTool.rectZoomOut(rectOptions);
    };
    mapControl.measureArea = function () {
        measure('measureArea');
    };
    function measure(type, callback) {
        obj.setCursor(Common_Tools.getCursor("ruler"));
        var drawOver = {
            measureArea: function (area_obj) {
                var paths = area_obj.getPath();
                var op = area_obj.getOptions();
                var g = area_obj.getArea();
                obj.closeMouseTool(true);
                var id = mapControl.getId();
                var polygonTemp = new AMap.Polygon(op);
                g = 1E5 < g ? (1E-6 * g).toFixed(2)
							+ '\u5e73\u65b9\u516c\u91cc' : g.toFixed(1)
							+ '\u5e73\u65b9\u7c73';
                var mark = obj.addClearMark(id, g, polygonTemp, paths[0]);
                obj.figures[id] = new figure(id, polygonTemp, mark);
            }
        };
        obj.listeners.push(AMap.event.addListenerOnce(obj.mouseTool,
					"draw", function (e) {
					    drawOver[type](e.obj);
					}));
        var lineOptions = {
            strokeStyle: "solid",
            strokeColor: "#FF33FF",
            strokeOpacity: 1,
            strokeWeight: 2
        };
        // 通过lineOptions更改量测线的样式
        obj.mouseTool[type](lineOptions);
    };
    mapControl.circle = function (circleOption, callBack) {
        var settings = {
            strokeColor: "#FF33FF",
            fillColor: "#FF99FF",
            fillOpacity: 0.5,
            strokeOpacity: 1,
            strokeWeight: 2
        };
        $.extend(settings, circleOption); 
        obj.setCursor(Common_Tools.getCursor("pencle"));
        obj.mouseTool.circle(settings);
        obj.listeners.push(AMap.event.addListenerOnce(obj.mouseTool,
					"draw", function (e) {
					    var lnglat = e.obj.getCenter();
					    var radius = e.obj.getRadius();
					    var id = mapControl.getId();
					    var name = circleOption.name || "";
					    var mark = obj.addClearMark(id, name, e.obj, lnglat);
					    obj.figures[id] = new figure(id, e.obj, mark);
					    callBack && callBack({
					        lat: lnglat.lat,
					        lng: lnglat.lng,
					        radius: radius
					    }, {
					        map: mapControl,
					        id: id
					    });
					    obj.closeMouseTool();
					}));
    };
    mapControl.rectangle = function (rectangleOption, callBack,clearBack) {
        var settings = {
            strokeColor: "#FF33FF",
            strokeOpacity: 1,
            strokeWeight: 2
        };
        $.extend(settings, rectangleOption);
        obj.setCursor(Common_Tools.getCursor("pencle"));
        obj.mouseTool.rectangle(settings);
        obj.listeners.push(AMap.event.addListenerOnce(obj.mouseTool,
					"draw", function (e) {
					    var bounds = e.obj.getBounds();
					    var northEast = bounds.getNorthEast();
					    var southWest = bounds.getSouthWest();
					    var id = mapControl.getId();
					    var center = new AMap.LngLat(
								(northEast.lng + southWest.lng) / 2,
								(northEast.lat + southWest.lat) / 2);
					    var name = rectangleOption.name || "";
					    var mark = obj.addClearMark(id, name, e.obj, center,
								function () {
								    clearBack && clearBack();
								});
					    obj.figures[id] = new figure(id, e.obj, mark);
					    callBack && callBack({
					        northEast: {
					            lat: northEast.lat,
					            lng: northEast.lng
					        },
					        southWest: {
					            lat: southWest.lat,
					            lng: southWest.lng
					        }
					    }, {
					        map: mapControl,
					        id: id
					    });
					    obj.closeMouseTool();
					}));
    };
    mapControl.polygon = function (polygonOption, callBack) {
        var settings = {
            strokeColor: "#FF33FF",
            strokeOpacity: 1,
            strokeWeight: 2
        };
        $.extend(settings, polygonOption);
        obj.setCursor(Common_Tools.getCursor("pencle"));
        obj.mouseTool.polygon(settings);
        obj.listeners.push(AMap.event.addListenerOnce(obj.mouseTool,
					"draw", function (e) {
					    var id = mapControl.getId();
					    var name = polygonOption.name || "";
					    var mark = obj.addClearMark(id, name, e.obj, e.obj
										.getPath()[0]);
					    obj.figures[id] = new figure(id, e.obj, mark);
					    callBack && callBack({
					        paths: e.obj.getPath()
					    }, {
					        map: mapControl,
					        id: id
					    });
					    obj.closeMouseTool();
					}));
    };
    mapControl.marker = function (markerOption, callBack, clearBack) {
        var settings = {
            offset: new AMap.Pixel(-16, -34),
            icon: Common_Tools.Css + "icons/nail.png"
        };
        $.extend(settings, markerOption);
        obj.setCursor(Common_Tools.getCursor("nail"));
        obj.mouseTool.marker(settings);
        obj.listeners.push(AMap.event.addListenerOnce(obj.mouseTool,
					"draw", function (e) {
					    var lnglat = e.obj.getPosition();
					    var id = mapControl.getId();
					    var name = markerOption.name || "";
					    var mark = obj.addClearMark(id, name, e.obj, lnglat,
								function () {
								    clearBack && clearBack();
								});
					    obj.figures[id] = new figure(id, e.obj, mark);
					    callBack && callBack({
					        lat: lnglat.lat,
					        lng: lnglat.lng
					    }, id);
					    obj.closeMouseTool();
					}));
    };
};
var AMap_MouseTool = {
    closeMouseTool: function (isClear) {
        this.mouseTool.close(isClear);
        this.setCursor(this.defultCursor);
        $.each(this.listeners, function (i, n) {
            AMap.event.removeListener(n);
        });
        AMap.event.removeListener(this.mouseTool);
    }
};

