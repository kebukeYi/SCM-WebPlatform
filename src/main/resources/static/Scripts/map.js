var MapControl = function(n, t, i) {
    if (t || (t = {}),
    i)
        this.onlyGetAddress = i,
        this.mapSetting = {
            interfaces: ["geocoder"]
        };
    else {
        var r = {
            center: {
                Lng: window.centerlng || 118.20,
                Lat: window.centerlat || 39.63
            },
            zoom: window.czoom || 10,
            maxlevel: 13,
            mapId: n,
            interfaces: ["geocoder"],
            plugins: {
                ToolBar: {},
                OverView: {},
                Scale: {},
                RightToolBar: {},
                MapType: {}
            }
        };
        $.extend(!0, r, t);
        this.mapSetting = r
    }
    this.Loading = !0;
    this.m = new Map_Plus(this,this.loadJsBack)
}, loadMapBack;

MapControl.prototype = {
    msg: function(n) {
        return map_message[n]
    },
    ecode: function(n) {
        return Common_Tools.DegreeToMillisecond(n)
    },
    decode: function(n) {
        return Common_Tools.MillisecondToDegree(n)
    },
    getPOIContent: function(n) {
        return Common_Tools.getPOIContent(n)
    },
    getId: function() {
        return (new Date).valueOf()
    },
    initRightToolBar: function() {
        var n = this;
        Common_Tools.getJsByUrl({
            url: "RightToolBar.js",
            funBack: function() {
                n.m.rightToolBar = new RightToolBar(n,n.mapSetting.plugins.RightToolBar)
            }
        })
    },
    rectZoomIn: function() {},
    rectZoomOut: function() {},
    closeMouseTool: function() {},
    searchAddress: function() {},
    ruler: function() {},
    selectAdminArea: function() {},
    showAdminArea: function() {},
    measureArea: function() {},
    lngLat: function() {},
    pixel: function() {},
    setCenter: function() {},
    getCenter: function() {},
    getZoom: function() {},
    circle: function() {},
    addCircle: function() {},
    rectangle: function() {},
    addRectangle: function() {},
    polygon: function() {},
    addPolygon: function() {},
    clearFigure: function() {},
    clearLocationPois: function() {},
    addPolyline: function() {},
    marker: function() {},
    markerSetIcon: function() {},
    addMarker: function() {},
    addLocationPoi: function() {},
    updateLocationPoi: function() {},
    LocationPoi: function() {},
    addSourceMarker: function() {},
    setScale: function() {},
    bounds: function() {},
    setMapBounds: function() {},
    setCity: function() {},
    addTrafficy: function() {},
    removeTrafficy: function() {},
    setFitView: function() {},
    keywordSearch: function() {},
    centerSearch: function() {},
    rectangleSearch: function() {},
    getAddress: function() {}
};
var JSVersion = "1.0.20"
  , Common_Tools = {
    Css: "/Content/map/",
    JS: "/Scripts/map/",
    DegreeToMillisecond: function(n) {
        var t, i, r;
        return n ? (t = 0,
        t = t + parseInt(n) * 36e5,
        i = n - parseInt(n),
        i > 0 && (i = i * 60,
        t = t + parseInt(i) * 6e4,
        r = i - parseInt(i),
        r > 0 && (r = r * 60,
        t = parseInt(t + r * 1e3))),
        t) : 0
    },
    MillisecondToDegree: function(n) {
        var t, i, r, u;
        return n ? (t = 0,
        i = parseInt(n / 1e3),
        t = t + (n - i * 1e3) / 36e5,
        i > 0 && (r = parseInt(i / 60),
        t = t + (i - r * 60) / 3600,
        r > 0 && (u = parseInt(r / 60),
        t = (t + u + (r - u * 60) / 60).toFixed(7),
        t = t / 1)),
        t) : 0
    },
    getPOIContent: function(n) {
        var t = [];
        return t.push('<h3><font color="#00a6ac">  ' + (n.i + 1) + "." + n.name + "<\/font><\/h3>"),
        (n.address || (n.address = "暂无")) && t.push("  地址：" + n.address + "<br />"),
        (n.tel || (n.tel = "暂无")) && t.push("  电话：" + n.tel + "<br />"),
        (n.type || (n.type = "暂无")) && t.push("  类型：" + n.type),
        t.join(" ")
    },
    getCursor: function(n) {
        return "url('" + Common_Tools.Css + "icons/" + n + ".cur'),pointer"
    },
    getJsByUrl: function(n) {
        var t = {
            url: null,
            dir: Common_Tools.JS,
            val: null,
            funBack: null,
            obj: null
        };
        $.extend(t, n);
        Common_Tools.getJsByUrlCom(t, t.funBack, function(n) {
            alert("get '" + t.dir + t.url + "' error:" + n)
        })
    },
    getJsByUrlCom: function(n, t, i) {
        if (n.val && (typeof n.val == "string" && window[n.val] || typeof n.val == "function" || typeof n.val == "object")) {
            n.funBack && n.funBack();
            return
        }
        if (n.url) {
            var r = (n.url.indexOf("?") > 0 ? "&" : "?") + "ver=" + JSVersion;
            $.ajax({
                cache: !0,
                url: n.dir + n.url + r,
                dataType: "script",
                success: function() {
                    t && t()
                },
                error: function(n, t, r) {
                    i && i(r)
                }
            })
        }
    }
}
  , map_message = {
    rectangle: "在地图中点击鼠标左键并按住拖动进行画矩形",
    circle: "在地图中点击鼠标左键选择中心点并按住拖动进行画圆形",
    polygon: "在地图中点击鼠标左键画出多边形跟点，双击左键完成",
    rule: "在地图中点击鼠标左键画出途经点，双击左键完成",
    resource: {
        SaveView: "保存视角",
        SearchAddress: "搜索地址",
        MoveMap: "移动地图",
        DistanceMeasurement: "距离测量",
        RectZoomIn: "缩小",
        RectZoomOut: "放大",
        InterestPoints: "兴趣点",
        DrawRoute: "画线",
        DragRoute: "拖拽画线",
        Rectangle: "画矩形",
        Polygon: "画多边形",
        Circle: "画圆",
        SelectAdminArea: "选择行政区域"
    }
};
Map_Plus = function(n) {
    this.mapUrl = {
        url: "webapi.amap.com/maps",
        version: "1.4.1",
        // key: "20e6143edbf17e1af84eb5e717c6efe8",
        key: "0c9a32244c434af519a1192ad934e9bd",
        getUrl: function() {
            return "http://" + this.url + "?v=" + this.version + "&key=" + this.key
        }
    };
    this.listeners = [];
    this.figures = {};
    this.sourceFigures = {};
    this.locationPois = {};
    this.loadJs(n)
}
;
loadMapBack = function() {}
;
Map_Plus.prototype = {
    loadJs: function(n) {
        var t = this;
        $.getScript(this.mapUrl.getUrl(), function() {
            if (n.onlyGetAddress)
                t.initInterface(n);
            else {
                var i = function() {
                    t.target = $("#" + n.mapSetting.mapId);
                    window.AMap ? (t.map = new AMap.Map(n.mapSetting.mapId,{
                        view: new AMap.View2D({
                            center: new AMap.LngLat(n.mapSetting.center.Lng,n.mapSetting.center.Lat),
                            zoom: n.mapSetting.zoom,
                            rotation: 0
                        }),
                        keyboardEnable: !1,
                        doubleClickZoom: !1,
                        lang: "zh_cn"
                    }),
                    t.defultCursor = t.map.getDefaultCursor(),
                    t.initInterface(n),
                    t.initPlugin(n),
                    n.Loading = !1) : setTimeout(i, 10)
                };
                setTimeout(i, 10)
            }
        })
    },
    initInterface: function(n) {
        var t = this;
        n.mapSetting.interfaces || (n.mapSetting.interfaces = []);
        n.mapSetting.interfaces.unshift("basic");
        $.each(n.mapSetting.interfaces, function(i, r) {
            var u = r;
            Common_Tools.getJsByUrl({
                url: "AMap_" + u + "_Interface.js",
                funBack: function() {
                    window["AMap_" + u + "_Interface"] && window["AMap_" + u + "_Interface"].call(t, n)
                }
            })
        })
    },
    initPlugin: function(n) {
        var t = this
          , i = {
            ToolBar: function(n) {
                AMap.plugin("AMap.ToolBar", function() {
                    var r = {}, i;
                    $.extend(r, n);
                    i = new AMap.ToolBar(r);
                    t.map.addControl(i);
                    i.hideRuler();
                    i.showRuler()
                })
            },
            OverView: function(n) {
                AMap.plugin("AMap.OverView", function() {
                    var i = {};
                    $.extend(i, n);
                    t.map.addControl(new AMap.OverView(i))
                })
            },
            Scale: function(n) {
                AMap.plugin("AMap.Scale", function() {
                    var i = {};
                    $.extend(i, n);
                    t.map.addControl(new AMap.Scale(i))
                })
            },
            MapType: function(n) {
                AMap.plugin("AMap.MapType", function() {
                    var i = new AMap.MapType({
                        defaultType: 0
                    });
                    $.extend(i, n);
                    t.map.addControl(i)
                })
            },
            RightToolBar: function() {
                n.initRightToolBar && n.initRightToolBar()
            }
        };
        $.each(n.mapSetting.plugins, function(n, t) {
            t && i[n] && i[n](t)
        })
    },
    initCircle: function(n) {
        this.initMouseTool(n)
    },
    initPolygon: function(n) {
        this.initMouseTool(n)
    },
    initRectangle: function(n) {
        this.initMouseTool(n)
    },
    initDragRoute: function(n) {
        this.initMouseTool(n)
    },
    initRoute: function(n) {
        this.initMouseTool(n)
    },
    initInterestPoints: function(n) {
        this.initMouseTool(n)
    },
    initDistanceMeasurement: function(n) {
        var t = this;
        t.initMouseTool(n);
        AMap.plugin(["AMap.RangingTool"], function() {
            n.m.ruler = new AMap.RangingTool(t.map);
            n.ruler = function() {
                t.setCursor(Common_Tools.getCursor("ruler"));
                n.m.ruler.turnOn()
            }
        })
    },
    initRectZoomOut: function(n) {
        this.initMouseTool(n)
    },
    initRectZoomIn: function(n) {
        this.initMouseTool(n)
    },
    initMouseTool: function(n) {
        var t = this;
        t.LoadMouseTool || (t.LoadMouseTool = !0,
        Common_Tools.getJsByUrl({
            url: "AMap_mouseTool_Interface.js",
            funBack: function() {
                AMap.plugin("AMap.MouseTool", function() {
                    $.extend(!0, Map_Plus.prototype, AMap_MouseTool);
                    t.mouseTool = new AMap.MouseTool(t.map);
                    AMap_mouseTool_Interface.call(t, n)
                })
            }
        }))
    },
    initSearchAddress: function(n) {
        Common_Tools.getJsByUrl({
            url: "AMap_searchAddress_Interface.js",
            funBack: function() {
                AMap_searchAddress_Interface(n)
            }
        })
    },
    initSelectAdminArea: function(n) {
        Common_Tools.getJsByUrl({
            url: "AMap_selectAdminArea_Interface.js",
            funBack: function() {
                AMap_selectAdminArea_Interface(n)
            }
        })
    },
    setCursor: function(n) {
        var t = this;
        n = n || t.defultCursor;
        t.map.setDefaultCursor(n)
    }
}
