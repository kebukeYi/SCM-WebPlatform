var AMap_basic_Interface = function (mapControl) {
    var obj = this;
    mapControl.mapSetting.maxlevel = 4;
    mapControl.lngLat = function (lng, lat) {
        return (new AMap.LngLat(lng, lat));
    };
    mapControl.pixel = function (x, y) {
        return AMap.Pixel(x, y)
    };
    mapControl.setCenter = function (lngLat, id) {
        if (!lngLat.getLng) {
            lngLat = new AMap.LngLat(lngLat.lng, lngLat.lat);
        }
        obj.map.setCenter(lngLat);
        if (id && obj.locationPois[id]) {
            obj.locationPois[id].openInfo();
        }
    };
    mapControl.getCenter = function () {
        var lngLat = obj.map.getCenter();
        return { lng: lngLat.lng, lat: lngLat.lat };
    };
    mapControl.getZoom = function () {
        return obj.map.getZoom();
    };
    mapControl.setScale = function (level) {
        obj.map.setZoom(level);
    };
    mapControl.bounds = function (left, bottom, right, top) {
        return (new AMap.Bounds(new AMap.LngLat(left, bottom),
					new AMap.LngLat(right, top)));
    };
    mapControl.setMapBounds = function (bounds) {
        obj.map.setBounds(bounds);
    };
    mapControl.setCity = function (city) {
        obj.map.setCity(city);
    };
    mapControl.addTrafficy = function () {
        obj.Trafficy = new AMap.TileLayer.Traffic({
            map: obj.map,
            zIndex: 10
        });
    };
    mapControl.removeTrafficy = function () {
        obj.Trafficy && obj.Trafficy.setMap(null) && (obj.Trafficy = null);
    };
    mapControl.setFitView = function () {
        obj.setFitViewAll();
    };

    //显示圆
    mapControl.addCircle = function (center, radius, id, name, circleOption) {
        var settings = {
            map: obj.map,
            strokeColor: "#F33", // 线颜色
            strokeOpacity: 1, // 线透明度
            strokeWeight: 3, // 线粗细度
            fillColor: "#ee2200", // 填充颜色
            fillOpacity: 0.35
            // 填充透明度
        };
        $.extend(settings, circleOption);
        settings.center = new AMap.LngLat(center.lng, center.lat);
        settings.radius = radius;
        var circle = new AMap.Circle(settings);
        obj.map.setFitView([circle]);
        id = id || mapControl.getId();
        var clearOptions = null;
        if (circleOption.hideClearImg) {
            clearOptions = {
                'hideImg': true
            };
        }
        var mark=null;
        if (name || (clearOptions && !clearOptions.hideImg)) {
            mark = obj.addClearMark(id, name, circle, settings.center, null, clearOptions);
        }
        obj.figures[id] = new figure(id, circle, mark);
    };
    //显示矩形
    mapControl.addRectangle = function (paths, id, name, rectangleOption) {
        var polygonArr = new Array();
        polygonArr.push(new AMap.LngLat(paths.northEast.lng,
					paths.northEast.lat));
        polygonArr.push(new AMap.LngLat(paths.southWest.lng,
					paths.northEast.lat));
        polygonArr.push(new AMap.LngLat(paths.southWest.lng,
					paths.southWest.lat));
        polygonArr.push(new AMap.LngLat(paths.northEast.lng,
					paths.southWest.lat));
        var center = new AMap.LngLat(
					(parseFloat(paths.northEast.lng) + parseFloat(paths.southWest.lng))
							/ 2,
					(parseFloat(paths.northEast.lat) + parseFloat(paths.southWest.lat))
							/ 2);
        addPolygonCommon(polygonArr, id, name, center, rectangleOption);
    };
    //显示多边形
    mapControl.addPolygon = function (paths, id, name, polygonOption) {
        var polygonArr = new Array();
        var center = null;
        if (paths.length > 0)
        {
            if (paths[0].getLat) {
                polygonArr = paths;
                !center && (center = polygonArr[0]);
            }
            else {
                $.each(paths, function (i, n) {
                    polygonArr.push(new AMap.LngLat(n.lng, n.lat));
                });
                center = polygonArr[0];
            }
        }
        addPolygonCommon(polygonArr, id, name, center, polygonOption);
    };
    function addPolygonCommon(polygonArr, id, name, center, option) {
        var settings = {
            map: obj.map,
            strokeColor: "#FF33FF", // 线颜色
            strokeOpacity: 0.2, // 线透明度
            strokeWeight: 3, // 线宽
            fillColor: "#1791fc", // 填充色
            fillOpacity: 0.35 // 填充透明度
        }
        $.extend(settings, option);
        settings.path = polygonArr;
        var polygon = new AMap.Polygon(settings);
        obj.map.setFitView([polygon]);
         var clearOptions = null;
         if (option.hideClearImg) {
             clearOptions = {
                 'hideImg': true
             };
         }
         var mark = null;
         if (name || (clearOptions && !clearOptions.hideImg)) {
             mark = obj.addClearMark(id || mapControl.getId(), name,
					polygon, center, null, clearOptions);
         }
        obj.figures[id] = new figure(id || mapControl.getId(), polygon,
					mark);
    };
    //显示线
    mapControl.addPolyline = function (paths, id, name, polylineOption) {
        var type = (polylineOption && polylineOption.type) || "figures";
        if (!obj[type][id]) {
            var fristDot = paths[0];
            var center = null;
            var polygonArr = new Array();
            if (fristDot.getLat) {
                center = fristDot;
                polygonArr = paths;
            }
            else {
                center = new AMap.LngLat(fristDot.lng, fristDot.lat);
                $.each(paths, function (i, n) {
                    polygonArr.push(new AMap.LngLat(n.lng, n.lat));
                });
            }
            var settings = {
                map: obj.map,
                strokeColor: "#3366FF", // 线颜色
                strokeOpacity: 1, // 线透明度
                strokeWeight: 5, // 线宽
                strokeStyle: "solid", // 线样式
                lineJoin: "bevel",
                strokeDasharray: [10, 5]
                // 补充线样式
            }
            $.extend(settings, polylineOption);
            settings.path = polygonArr;
            var polyline = new AMap.Polyline(settings);
            var mark = null;
            if (!polylineOption.hideClear) {
                mark = obj.addClearMark(id || mapControl.getId(), name,
							polyline, center);
            }
            obj[type][id] = new figure(id || mapControl.getId(), polyline,
						mark);
        };
        if (!polylineOption || !polylineOption.noFitView) {
            obj.map.setFitView([obj[type][id].obj]);
        }
        return obj[type][id];
    };
    //清除指定图形
    mapControl.clearFigure = function (id) {
        obj.clearFigure(id);
    };
    //清除定位点
    mapControl.clearLocationPois = function (id) {
        obj.clearLocationPois(id);
    };
    mapControl.markerSetIcon = function (id, url) {
        var marker = obj.locationPois[id] || obj.figures[id];
        if (marker) {
            marker.obj.setIcon(url);
        }
    };
    mapControl.addMarker = function (position, id, name, markerOption, infoWindowOption, clearMarkOption) {
        return obj.addMarker(position, id, name, markerOption, infoWindowOption, clearMarkOption, mapControl);
    };
    mapControl.addSourceMarker = function (position, id, name, deleteCheck) {
        return obj.addSourceMarker(position, id, name, deleteCheck, mapControl);
    };
    mapControl.addLocationPoi = function (position, id, name, markerOption, infoWindowOption) {
        return obj.addLocationPoi(position, id, name, markerOption, infoWindowOption, mapControl);
    };
    mapControl.updateLocationPoi = function (position, id, angle, infoWindowContent) {
        obj.updateLocationPoi(position, id, angle, infoWindowContent, mapControl);
    };
    mapControl.LocationPoi = function (key, id) {
        obj['locationPois'][id] && obj['locationPois'][id][key] && obj['locationPois'][id][key]();
    },
    mapControl.closeMouseTool = function (isClear) {
        obj.ruler && obj.ruler.turnOff();
        obj.closeMouseTool && obj.closeMouseTool(isClear);
    };
    mapControl.startMarkFollowing = function (id) {
        obj.startMarkFollowing(id);
    };
    mapControl.stopMarkFollowing = function (id) {
        obj.stopMarkFollowing(id);
     };
};

var AMap_Basic = {
    addMarker: function (position, id, name, markerOption, infoWindowOption, clearMarkOption) {
        return this.addMarkerCommon(this['figures'], position, id, name, markerOption, infoWindowOption, clearMarkOption);
    },
    addSourceMarker: function (position, id, name, deleteCheck) {
        return this.addMarkerCommon(this['sourceFigures'], position, id, name, {
            noSetCenter: true
        }, null, { deleteCheck: deleteCheck });
    },
    addMarkerCommon: function (figures, position, id, name, markerOption, infoWindowOption, clearMarkOption) {
        var obj = this;
        var point = new AMap.LngLat(position.lng, position.lat);
        if (!clearMarkOption) {
            clearMarkOption = {};
        }
        clearMarkOption.figures = figures;
        if (!figures[id]) {
            var settings = {
                map: obj.map,
                offset: new AMap.Pixel(-16, -34),
                icon: Common_Tools.Css + "icons/nail.png"
            }
            $.extend(settings, markerOption);
            settings.position = point;
            settings.autoRotation = false;
            settings.angle = 0;
            var marker = new AMap.Marker(settings);
            var mark = obj.addClearMark(id || mapControl.getId(), name, marker, point, null, clearMarkOption);
            var wind = null;
            if (infoWindowOption) {
                wind = obj.addInfoWindow(id || mapControl.getId(), marker, infoWindowOption);
            }
            figures[id] = new figure(id || mapControl.getId(), marker, mark, wind);
        }
        !markerOption.noSetCenter && obj.map.setCenter(point);
        return figures[id];
    },
    addLocationPoi: function (position, id, name, markerOption, infoWindowOption) {
        var obj = this;
        var clearMarkOption = {};
        clearMarkOption.hideImg = markerOption.hideClear;
        clearMarkOption.figures = obj.locationPois;
        markerOption.deleteCheck && (clearMarkOption.deleteCheck = markerOption.deleteCheck);
        var point = new AMap.LngLat(position.lng, position.lat);
        if (!obj['locationPois'][id]) {
            var settings = {
                map: obj.map,
                offset: new AMap.Pixel(-(markerOption.size.w / 2),
						-markerOption.size.h),
                icon: markerOption.icon,
                position: point,
                autoRotation: false,
                angle: 0
            };
            if (markerOption.zIndex) {
                settings.zIndex = markerOption.zIndex;
                clearMarkOption.zIndex = markerOption.zIndex;
            }
            var marker = new AMap.Marker(settings);
            var mark = null;
            if (name) {
                mark = obj.addClearMark(id || mapControl.getId(), name, marker, point, null, clearMarkOption);
            }
            var wind = null;
            if (infoWindowOption) {
                wind = obj.addInfoWindow(id || mapControl.getId(), marker,
						infoWindowOption);
                infoWindowOption.openAfter && wind.on('open', function (a) {
                    infoWindowOption.openAfter.call(a);
                });
            }
            obj['locationPois'][id] = new figure(id || mapControl.getId(), marker, mark, wind);
            wind && (!infoWindowOption.hideWin) && obj['locationPois'][id].openInfo();
            //if (markerOption.playBack) {
            //    $.extend(obj['locationPois'][id], markerPlay);
            //    obj['locationPois'][id].playBack = markerOption.playBack;
            //}
        }
        !markerOption.noSetCenter && obj.map.setCenter(point);
        return obj['locationPois'][id];
    },
    updateLocationPoi: function (position, id, angle, infoWindowContent) {
        var self = this;
        var marker_obj = self['locationPois'][id];
        if (marker_obj) {
            if (infoWindowContent && marker_obj.infoWindow.getIsOpen()) {
                marker_obj.infoWindow.setContent(infoWindowContent);
                if (marker_obj.infoWindow.G)
                {
                    marker_obj.infoWindow.G.openAfter && marker_obj.infoWindow.G.openAfter();
                }
            }
            if (position) {
                var new_Position = new AMap.LngLat(position.lng, position.lat);
                var old_Position = marker_obj.obj.getPosition();
                if (old_Position.lat != new_Position.lat || old_Position.lng != new_Position.lng) {
                    if (marker_obj.following) {
                        var line_id = id + '_line_' + marker_obj.followIndex;
                        marker_obj.setPosition(new_Position);
                        var settings = {
                            map: self.map,
                            strokeColor: "#3366FF", // 线颜色
                            strokeOpacity: 1, // 线透明度
                            strokeWeight: 5, // 线宽
                            strokeStyle: "solid", // 线样式
                            strokeDasharray: [10, 5],// 补充线样式
                            path: [old_Position, new_Position]
                        };
                        var polyline = new AMap.Polyline(settings);
                        marker_obj.followLines.push(polyline);
                        marker_obj.followIndex = marker_obj.followIndex + 1;
                    }
                    else {
                        marker_obj.setPosition(new_Position);
                    }
                }
            }
            angle && marker_obj.obj.setAngle(angle);
        }
    },
    startMarkFollowing: function (id) {
        var self = this;
        var marker_obj = self['locationPois'][id];
        marker_obj.following = true;
    },
    stopMarkFollowing: function (id) {
        var self = this;
        var marker_obj = self['locationPois'][id];
        marker_obj.following = false;
        marker_obj.clearFollowLine();
    },
    clearLocationPois: function (id) {
        this.clear(id, this['locationPois']);
    },
    clearFigure: function (id) {
        this.clear(id, this['figures']);
    },
    clear: function (id, figures) {
        var obj = this;
        if (!id) {
            $.each(figures, function (i, n) {
                n.clear();
            });
            figures = {};
        } else if (typeof id == "object" && id.length > 0) {
            for (var i = 0; i < id.length; i++) {
                var _id = id[i];
                var tempo = figures[_id];
                if (tempo) {
                    tempo.clear();
                    delete figures[_id];
                }
            }
        } else if ((typeof id == "string" || typeof id == "number")
				&& figures[id]) {
            figures[id].clear();
            delete figures[id];
        };
    },
    addClearMark: function (id, name, figureObj, lnglat, clearBack, clearMarkOption) {
        var markTemp = null;
        var obj = this;
        var outDiv = $('<div></div>').css({
            'position': 'relative',
            'background-color': (clearMarkOption && clearMarkOption.bgcolor)|| '#FFFFFF',
            'border': '1px solid #4B4B4B',
            'border-radius': '3px 3px 3px 3px',
            'box-shadow': '2px 2px 8px #999999',
            'padding': '2px 7px',
            'white-space': 'nowrap'
        });
        name = name || '';
        $('<span class="' + id + '">' + name + '</span>').appendTo(outDiv);
        if (!(clearMarkOption && clearMarkOption.hideImg)) {
            $('<span><img class="delimg" src="' + Common_Tools.Css + 'icons/trash.jpg"/></span>')
					.appendTo(outDiv).bind('click', function () {
					    var figures = obj.figures;
					    var deleteCheck = null;
					    if (clearMarkOption) {
					        clearMarkOption.figures && (figures = clearMarkOption.figures);
					        clearMarkOption.deleteCheck && (deleteCheck = clearMarkOption.deleteCheck);
					    }
					    if (deleteCheck) {
					        (function () {
					            deleteCheck(function () {
					                obj.clear(id, figures);
					            });
					        })();
					    }
					    else {
					        obj.clear(id, figures);
					    }
					});
        }
        markTemp = new AMap.Marker({
            map: obj.map,
            position: lnglat,
            content: outDiv[0],
            zIndex: (clearMarkOption && clearMarkOption.zIndex) || 100,
            offset: new AMap.Pixel(0, 0),
            bubble: false,
            extData: {
                clearBack: clearBack
            }
        });
        return markTemp;
    },
    addInfoWindow: function (id, marker, option) {
        var obj = this;
        var setting = {
            content: '',
            size: new AMap.Size(325, 0),
            autoMove: true,
            offset: new AMap.Pixel(0, -20)
        };
        $.extend(setting, option);
        return new AMap.InfoWindow(setting);
    },
    setFitViewAll: function () {
        this.map.setFitView();
    },
    setFitViewByIds: function (figures, ids) {
        if (!ids || ids.length == 0 || !figures || $.isEmptyObject(figures)) {
            return;
        }
        var _this = this;
        var allFigures = [];
        $.each(ids, function (i, n) {
            var t = figures[n];
            t && allFigures.push(t.obj);
        });
        this.setFitViewByObjs(allFigures);
    },
    setFitViewByObjs: function (objs) {
        if (!objs || objs.length == 0) {
            return;
        }
        this.map.setFitView(objs);
    }
};

var figure = function (id, figureObj, clearMark, infoWindow) {
    var _this = this;
    _this.obj = figureObj;
    _this.mark = clearMark;
    _this.map = (figureObj.getMap && figureObj.getMap()) || (clearMark && clearMark.getMap && clearMark.getMap());
    _this.infoWindow = infoWindow;
    _this.following = false;
    _this.followIndex = 0;
    _this.followLines = [];
    _this.id = id;
    AMap.event.addListener(_this.obj, "mouseover", function () {
        if (_this.infoWindow && !_this.infoWindow.getIsOpen()) {
            _this.openInfo();
        }
    });
};
figure.prototype = {
    clear: function () {
        if (this.obj) {
            this.obj.stopMove && this.obj.stopMove();
            this.obj.setMap(null);
        }
        if (this.mark) {
            this.obj.stopMove && this.mark.stopMove();
            this.mark.setMap(null);
            var extData = this.mark.getExtData();
            extData && extData.clearBack && extData.clearBack();
        }
        this.infoWindow && this.infoWindow.close();
        this.following && this.clearFollowLine();
    },
    clearFollowLine: function () {
        var self = this;
        $.each(self.followLines, function (i, n) {
            n.setMap(null);
        });
        self.followLines = [];
        self.followIndex = 0;
    },
    setPosition: function (position) {
        var self = this;
        self.obj.setPosition(position);
        self.mark && self.mark.setPosition(position);
        self.infoWindow && self.infoWindow.setPosition(position);
    },
    overViewDo: function (lngLat) {
        var bounds = this.map.getBounds();
        if (!bounds.contains(lngLat)) {
            this.map.setCenter(lngLat);
        }
    },
    setTop: function () {
        this.obj && this.obj.setTop(true);
        this.mark && this.mark.setTop(true);
        this.infoWindow && this.openInfo();
    },
    openInfo: function () {
        this.map && this.infoWindow && this.infoWindow.open(this.map, this.obj.getPosition());
    }
};

//TODO 解开注释
var markerPlay = {
   last: 0,
   count: 0,
   speed: 1,
   playBack: null,
   play: function () {
       var self = this;
       self.last = 0;
       self.count = self.playBack.dots.length;
       self.obj.on('moveend', function () {
           if (self.last == self.count - 1) {
               self.stop(true);
           }
           else {
               self.move();
           }
       });
       self.obj.on('moving', function () {
           self.infoWindow.getIsOpen();
           self.infoWindow.setPosition(this.getPosition());
       })
       self.play = self.move;
       self.play();
   },
   move: function () {
       var self = this;
       self.playBack.setBtn(1);
       if (self.last < self.count) {
           var next = self.last + 1;
           var lastDot = self.playBack.dots[self.last];
           var goMoveAlong = false;
           while (next < self.count) {
               var nextDot = self.playBack.dots[next];
               if (nextDot.Speed) {
                   var oldLngLat = new AMap.LngLat(lastDot.lng, lastDot.lat);
                   var nextLngLat = new AMap.LngLat(nextDot.lng, nextDot.lat);
                   var distance = nextLngLat.distance(oldLngLat);
                   var speed = distance / (1000 * 10);
                   var speed_base = self.playBack.getSpeed();
                   self.infoWindow.setContent(self.playBack.getContent(lastDot));
                   self.obj.moveAlong([oldLngLat, nextLngLat], speed * speed_base * 3600);
                   self.last = next;
                   goMoveAlong = true;
                   break;
               }
               else {
                   next = next + 1;
               }
           }
           !goMoveAlong && self.stop(true);
       }
   },
   stop: function (noStopMove) {
       var self = this;
       !noStopMove && self.obj.stopMove();
       self.obj.setPosition(new AMap.LngLat(self.playBack.dots[0].lng, self.playBack.dots[0].lat));
       self.last = 0;
       self.playBack.setBtn(3);
   },
   pause: function () {
       var self = this;
       self.obj.stopMove();
       if (self.last != 0) {
           self.last = self.last - 1;
           self.obj.setPosition(new AMap.LngLat(self.playBack.dots[self.last].lng, self.playBack.dots[self.last].lat));
       }
       self.playBack.setBtn(2);
   }
};

$(function () {
    $.extend(true, Map_Plus.prototype, AMap_Basic);
});