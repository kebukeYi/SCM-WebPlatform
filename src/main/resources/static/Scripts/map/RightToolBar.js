var RightToolBar = function (mapControl, options) {
    var _this = this;
    var defaultTools = [{
        key: 'SaveView',
        iconCls: 'icon-saveview',
        text: map_message.resource.SaveView,
        clickEvent: function () {
            var self = this;
            var center = mapControl.getCenter(); //返回地图中心点经纬度坐标
            var zoom = mapControl.getZoom(); //返回当前的地图缩放级别
            $.updateSever({
                'para': {
                    'MapView': center.lng + ',' + center.lat + ',' + zoom
                },
                'url': '/api/User/SaveMapView',
                'success': function (result) {
                    $.alert({
                        title: '提示',
                        content: '保存视角成功！'
                    });
                }
            });
        }
    }, {
        key: 'SearchAddress',
        iconCls: 'icon-SearchAddress',
        text: map_message.resource.SearchAddress,
        clickEvent: function (e, mapControl, obj) {
            _this.openDefaultTools(obj, mapControl, mapControl.searchAddress);
        }
    }];

    var drawTools = [{
        key: 'Move',
        iconCls: 'icon-move',
        isDefault: true,
        text: map_message.resource.MoveMap,
        clickEvent: function (e, mapControl, obj) {
            _this.openDrawTools(obj, mapControl);
        }
    }, {
        key: 'DistanceMeasurement',
        iconCls: 'icon-measure',
        text: map_message.resource.DistanceMeasurement,
        clickEvent: function (e, mapControl, obj) {
            _this.openDrawTools(obj, mapControl, mapControl.ruler);
        }
    }, {
        key: 'RectZoomIn',
        iconCls: 'icon-rectZoomIn',
        text: map_message.resource.RectZoomIn,
        clickEvent: function (e, mapControl, obj) {
            _this.openDrawTools(obj, mapControl, mapControl.rectZoomIn);
        }
    }, {
        key: 'RectZoomOut',
        iconCls: 'icon-rectZoomOut',
        text: map_message.resource.RectZoomOut,
        clickEvent: function (e, mapControl, obj) {
            _this.openDrawTools(obj, mapControl, mapControl.rectZoomOut);
        }
    },
    {
        key: 'Rectangle',
        iconCls: 'icon-rectangle',
        text: map_message.resource.Polygon,
        clickEvent: function (e, mapControl, obj) {
            _this.openDrawTools(obj, mapControl, function () {
                mapControl.rectangle({}, function (rectangle, figure) {
                    var northEast = rectangle.northEast;
                    var southWest = rectangle.southWest;
                    var d = northEast.lng + "," + northEast.lat + ";" + southWest.lng + "," + southWest.lat;
                    $('[datafld="FenceData"]', $('#fenceModal')).val(d);
                    $('[datafld="FenceType"]', $('#fenceModal')).val(3);
                    $('#fenceModal').off('hidden.bs.modal');
                    $('#fenceModal').off('show.bs.modal');
                    $('#fenceModal').on('hidden.bs.modal', function () {
                        _this.drawToolsDefault();
                        mapControl.clearFigure(figure.id);
                        $('#fenceModal').bootstrapValidator('resetForm', true);
                    }).on('show.bs.modal', function () {
                        $("#fenceName").val('');
                    });
                    $('#fenceModal').modal();
                });
            });
        }
    },
    {
        key: 'Polygon',
        iconCls: 'icon-fence',
        text: map_message.resource.Polygon,
        clickEvent: function (e, mapControl, obj) {
            _this.openDrawTools(obj, mapControl, function () {
                mapControl.polygon({}, function (paths, figure) {
                    // debugger
                    var p = paths.paths;
                    var d;
                    var l = p.length;
                    if (l > 0) {
                        d = p[0].lng + "," + p[0].lat;
                        for (var i = 1; i < l; i++) {
                            d += ";" + p[i].lng + "," + p[i].lat;
                        }
                    }
                    $('[datafld="FenceData"]', $('#fenceModal')).val(d);
                    $('[datafld="FenceType"]', $('#fenceModal')).val(2);
                    $('#fenceModal').off('hidden.bs.modal');
                    $('#fenceModal').off('show.bs.modal');
                    $('#fenceModal').on('hidden.bs.modal', function () {
                        _this.drawToolsDefault();
                        mapControl.clearFigure(figure.id);
                        $('#fenceModal').bootstrapValidator('resetForm', true);
                    }).on('show.bs.modal', function () {
                        $("#fenceName").val('');
                    });
                    $('#fenceModal').modal();
                });
            });
        }
    }, {
        key: 'Circle',
        iconCls: 'icon-circle',
        text: map_message.resource.Circle,
        clickEvent: function (e, mapControl, obj) {
            _this.openDrawTools(obj, mapControl, function () {
                mapControl.circle({}, function (center, figure) {
                    $('[datafld="FenceData"]', $('#fenceModal')).val(center.lng + '|' + center.lat + ',' + Math.floor(center.radius));
                    $('[datafld="FenceType"]', $('#fenceModal')).val(1);
                    $('#fenceModal').off('hidden.bs.modal');
                    $('#fenceModal').off('show.bs.modal');
                    $('#fenceModal').on('hidden.bs.modal', function () {
                        _this.drawToolsDefault();
                        mapControl.clearFigure(figure.id);
                        $('#fenceModal').bootstrapValidator('resetForm', true);
                    }).on('show.bs.modal', function () {
                        $("#fenceName").val('');
                    });
                    $('#fenceModal').modal();
                });
            });
        }
    },
    {
        key: 'SelectAdminArea',
        iconCls: 'icon-SelectAdminArea',
        text: map_message.resource.SelectAdminArea,
        clickEvent: function (e, mapControl, obj) {
            _this.openDrawTools(obj, mapControl, function () {
                mapControl.selectAdminArea(true);
            });
            _this.clearing.push(function () {
                mapControl.selectAdminArea(false);
            });
        }
    }];


    _this.defaultTools = [];
    $.each(defaultTools, function (i, n) {
        var settings = options[n.key];
        if (settings) {
            $.extend(n, settings);
            _this.defaultTools.push(n);
        }
    });
    _this.drawTools = [];
    $.each(drawTools, function (i, n) {
        var settings = options[n.key];
        if (settings) {
            $.extend(n, settings);
            _this.drawTools.push(n);
        }
    });
    _this.drawTools_status = '';
    _this.defaultTools_status = '';
    _this.clearing = [];
    _this.init(mapControl);
};

RightToolBar.prototype = {
    init: function (mapControl) {
        var map_Plus = mapControl.m;
        var _this = this;
        _this.obj = $('<div></div>').css({
            'position': 'absolute',
            'top': '13px',
            'right': '80px',
            'border-radius': '3px',
            'overflow': 'hidden'
        }).addClass('RightToolBar').appendTo($(map_Plus.target));
        addToolsInDiv(_this.defaultTools, 'defaultTools');
        addToolsInDiv(_this.drawTools, 'drawTools');
        function addToolsInDiv(l, type) {
            var tools_Div = $("<div></div>").addClass(type + '_rightToolBar').css({ 'background': '#fff', 'float': 'left', 'margin-right': '6px', 'border': '1px solid #c4c7cc', 'cursor': 'pointer' });
            $.each(l, function (i, n) {
                var sp_temp = $("<span></span>").attr('type_rt', type).attr('title', n.text).appendTo(tools_Div).addClass(n.iconCls).addClass('icon-rightToolBar').css({ 'float': 'left', 'height': '26px', 'width': '26px', 'border-right': '1px solid #ecedef' });
                n.clickEvent && sp_temp.bind('click', function (e) {
                    n.clickEvent.call(_this, e, mapControl, sp_temp);
                });
                if (n.isDefault) {
                    sp_temp.addClass('rightToolBar_selected');
                    _this.drawToolsDefault = function () {
                        n.clickEvent.call(_this, sp_temp.target, mapControl, sp_temp);
                    }
                }
                map_Plus['init' + n.key] && map_Plus['init' + n.key](mapControl);
            });
            tools_Div.appendTo(_this.obj);
        }
    },
    drawToolsDefault: function () {

    },
    openDefaultTools: function (obj, mapControl, callBack) {
        if (obj.hasClass('rightToolBar_selected')) {
            obj.removeClass('rightToolBar_selected');
            callBack(false);
        }
        else {
            obj.addClass('rightToolBar_selected');
            callBack(true);
        }
    },
    openDrawTools: function (obj, mapControl, callBack) {
        var _this = this;
        if (!obj.hasClass('rightToolBar_selected')) {
            $('[type_rt="drawTools"]').removeClass('rightToolBar_selected');
            obj.addClass('rightToolBar_selected');
            mapControl.closeMouseTool();
            _this.clearing.length > 0 && $.each(_this.clearing, function (i, n) {
                n && n.call(_this);
            });
            _this.clearing = [];
            callBack && callBack();
        }
    }
};