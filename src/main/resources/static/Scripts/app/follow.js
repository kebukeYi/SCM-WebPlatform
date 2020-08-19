$(function () {

    /*
全局 接口访问地址 IP ：port
*/
    var URL = GlobalConfig.IPSSAddress;

    var update_key = '';
    var update_name = '';
    var update_mn = '';
    var update_dn = '';

    var update_io = '1';
    var update_mt = 0; //有线无线，
    var czoom = '14';
    var mapType = 0;

    //获取URL 参数
    function getQueryVariable(variable)
    {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i=0;i<vars.length;i++) {
            var pair = vars[i].split("=");
            if(pair[0] == variable){return pair[1];}
        }
        return(false);
    }

    var DeviceNum = getQueryVariable("key");

    var leftarea_div, shrinkButton_div, map_div, table_div, table_OutDiv;

    table_div = $('#tableDiv');

    leftarea_div = $('#leftareadiv');

    $('#shrinkDiv').on('click', function () {
        if (leftarea_div.is(":hidden")) {
            leftarea_div.show(500);
            $('i', $(this)).removeClass('fa-chevron-right').addClass('fa-chevron-left');
        } else {
            leftarea_div.hide(500);
            $('i', $(this)).removeClass('fa-chevron-left').addClass('fa-chevron-right');
        }
    });

    var monitor_div = $('#monitor_Div').css({
        'width': '100%',
        'height': '100%',
        'position': 'absolute'
    });

    //地图
    //地图初始化===Start
    mapObj = new wrt.mapClass({
        'id': 'mmap',
        'onTipsUpdated': function (mObj, p) {
            mObj.getAddress(p, function (a) {
                $('.table_map .info_Address').html(a);
            });
        }
    });


    $('#mapSelect').on('change', function () {
        location.href = "/follow.html?key=" + update_key + '&name=' + escape(update_name) + '&mn=' + update_mn + '&mt=' + update_mt + '&dn=' + escape(update_dn) + '&mapType=' + $(this).val();
    });

    //mapObj.setCenter({
    //    lng: window['centerlng'] || 114.087388,
    //    lat: window['centerlat'] || 22.546990
    //}, window['czoom'] || 10);

    var trace_Control = {
        terminal: null,
        roundTime: 10000,
        lastTime: null,
        map: null,
        _monitor: function () {
            var deviceNumbers = [update_key];
            $.updateSever({
                'para': {
                    'LastTime': $.dateToJson(trace_Control.lastTime),
                    'DeviceNumbers': deviceNumbers,
                    'MapType': mapType
                },
                'url': URL + '/api/monitor/Refresh',
                'success': function (result) {
                    trace_Control.lastTime = $.formatJsDateTime(result.lastTime);
                    if (trace_Control.terminal) {
                        setTimeout(function () {
                            trace_Control.terminal.round_update(result.Data[0]);
                        }, 500);
                    }
                    setTimeout(function () {
                        trace_Control._monitor();
                    }, trace_Control.roundTime);
                },
                'error': function () {
                    setTimeout(function () {
                        trace_Control._monitor();
                    }, trace_Control.RoundTime_Monitor);
                }
            });
        }
    };

    var terminal_Control = function () {
        var _this = this;
        _this.DeviceNumber = update_key;
        _this.ModelName = update_mn;
        _this.DeviceName = update_dn;
        _this.Aliase = update_name;
        _this.inMap = false;
        _this.isOnline = !!update_io;
        _this.LastPosition = null;
        _this.LastTime = null;
        _this._updates = [];
        _this.init();
    };

    terminal_Control.prototype = {
        init: function () {
            var _this = this;
            _this._updates.push(_this._updatePosition);
            _this._updates.push(_this._updateTerminalTable);
            _this._update(function (data) {
                _this.round_update(data);
            });
        },
        changeIcon: function (isOnline) {
            var _this = this;
            if (_this.isOnline !== isOnline) {
                _this.isOnline = isOnline;
                var url = '/Content/map/icons/Map_' + (_this.isOnline ? 'Online' : 'Offline') + '.png';
                mapObj.markerSetIcon(_this.DeviceNumber, url);
            }
        },
        _getContent: function () {
            var _this = this;
            var info = [];
            var typeList = {
                'R01': ['power', 'getWifi'],
                'c09': ['power', 'getWifi'],
                'R06': ['power', 'getWifi'],
                'S01': ['power', 'getWifi'],
                'W30': ['power', 'getWifi']
            };
            info.push("<div><h3 style='margin-top:0px;margin-bottom:5px;font-size:20px;width:232px;text-overflow:ellipsis;white-space:nowrap;overflow:hidden' title='" + _this.Aliase + "'><font color=\"#00a6ac\">  " + _this.Aliase + "</font></h3></div> ");
            info.push('<div id="' + _this.DeviceNumber + '_InfoWindows" class="follow_infowindow">');
            info.push("<div class='display width'><span style='text-align:right;width:70px;'>定位时间&nbsp;:&nbsp;</span><span>" + $.toDateTime(_this.LastPosition.LocationTime) + "</span></div>");
            info.push("<div class='display'><span style='text-align:right;width:70px;'>信号时间&nbsp;:&nbsp;</span><span>" + $.toDateTime(_this.LastPosition.ReceiveTime) + "</span></div>");
            info.push("<div class='display width'><span style='text-align:right;width:70px;'>设&nbsp;&nbsp;&nbsp;备&nbsp;&nbsp;号&nbsp;:&nbsp;</span><span>" + _this.LastPosition.DeviceNumber + "</span></div>");
            info.push("<div class='display'><span style='text-align:right;width:70px;'>定位方式&nbsp;:&nbsp;</span><span>" + ((_this.LastPosition.LocationType == "1") ? '北斗' : '基站') + "</span></div>");
            info.push("<div class='display clearfix'><span style='width:70px;float:left'>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态&nbsp;:&nbsp;</span><span style='float:left;width:220px'>" + (_this.LastPosition.Online ? '在线' : '离线') + statusModel.getStatusValue(_this.LastPosition.Status, _this.LastPosition.Alarm) + "</span></div>")
            update_mt && info.push(_this._getmode(['power', 'getWifi']));

            info.push("<div class='clearfix'><span style='width:70px;float:left;'>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址&nbsp;:&nbsp;</span><span style='float:left;width:220px'>" + _this.LastPosition.Address + "</span></div>");
            info.push("</div>");
            return info.join('');
        },
        _getWifi: function () {
            var _this = this;
            var wifiPoto = "";
            var w = [10, 20, 40, 60, 80, 101];
            for (var i = 0; i < w.length - 1; i++) {
                if (_this.LastPosition.Signal > w[i] && _this.LastPosition.Signal <= w[i + 1]) {
                    wifiPoto = "/Content/img/power/wifi" + (i + 1) + ".jpg";
                    break;
                };
            };
            if (wifiPoto == "") {
                return "";
            }
            return '<div style="position:absolute;top:12px;right:25px" class="singl" title="信号为' + _this.LastPosition.Signal + '%"><img style="width:25px;height:15px" src=' + wifiPoto + ' alt=""></div>';
        },
        _getPower: function () {
            var _this = this;
            var pow = '';
            var f = [0, 10, 25, 45, 65, 90, 101];
            for (var i = 0; i < f.length - 1; i++) {
                if (_this.LastPosition.Electricity >= f[i] && _this.LastPosition.Electricity < f[i + 1]) {
                    pow = "/Content/img/power/power" + (6 - i) + ".png";
                    break;
                };
            };
            if (pow == "") {
                return "";
            }
            return '<div style="position:absolute;top:12px;right:48px" class="electric" title="电量还剩余' + _this.LastPosition.Electricity + '%"><img style="height:12px;margin-right:10px" src=' + pow + ' ></div>';
        },
        _getmode: function (data) {
            var l = '';
            var _this = this;
            var ary = {
                'power': function () {
                    return _this._getPower();
                },
                'getWifi': function () {
                    return _this._getWifi();
                }
            };
            $.each(data, function (i, v) {
                ary[v] && (l += ary[v]());
            });
            return l;
        },
        _updatePosition: function () {
            var _this = this;
            var position = {lat: _this.LastPosition.Lat, lng: _this.LastPosition.Lng};
            var id = _this.DeviceNumber;
            var icon = '/Content/map/icons/Map_' + (_this.LastPosition.Online ? 'Online' : 'Offline') + '.png';

            if (_this.carMarker) {
                //_this.changeIcon(_this.LastPosition.Online);
                var oldPos = mapObj.getMarkerPosition(_this.carMarker);

                mapObj.drawLine([oldPos, position]);

                var updatedTips = mapObj.updateMarker(_this.carMarker, {
                    icon: icon,
                    position: position,
                    tipsContent: _this._getContent()
                });

                mapObj.adjustView(position);

            } else {
                _this.carMarker = mapObj.addMarker({
                    icon: icon,
                    text: _this.Aliase,
                    position: position,
                    tipsContent: _this._getContent()
                });
                mapObj.addMarkerClickEvent(_this.carMarker);
                mapObj.setCenter(position);
                mapObj.showTips(_this.carMarker);
            }
        },
        _updateTerminalTable: function () {
            var _this = this;
            var dot = _this.LastPosition;
            var _this = this;
            //dot.LngLat = mapObj.lngLat(dot.Lng, dot.Lat);
            dot.Content = _this._getContent(dot);
            var row = $('<div><div class="dot_cell1">' + $.toDateTime(dot.LocationTime) + '</div><div class"dot_cell2">' + dot.Speed + 'km/h</div><div class="dot_cell3">' + (dot.LocationType == "1" ? '北斗' : '基站') + '</div><div class="dot_cell4">' + dot.Address + '</div></div>').addClass('dot_row').appendTo(table_div).data('dot', dot);
            if (_this.background) {
                row.addClass(_this.background);
                _this.background = '';
            } else {
                _this.background = 'table-alt';
            }
        },
        _cancelPosition: function () {
            var _this = this;
            mapObj.removeMarker(_this.carMarker);
            //mapObj.clearLocationPois(_this.DeviceNumber);
        },
        _update: function (callback) {
            var _this = this;
            $.updateSever({
                'para': {
                    // 'DeviceNumber': _this.DeviceNumber,
                    'DeviceNumber': DeviceNum,
                    'MapType': mapType
                },
                'url': URL + '/api/monitor/ShowMonitorTipLocation',
                'success': function (result) {
                    if (result) {
                        callback && callback(result);
                    }
                    setTimeout(function () {
                        trace_Control._monitor();
                    }, trace_Control.roundTime);
                }
            });
        },
        round_update: function (position) {
            var _this = this;
            if (position) {
                var nowTime = $.formatJsDateTime(position.ReceiveTime);
                if (_this.LastTime && _this.LastTime >= nowTime) {
                    return;
                }
                _this.LastTime = nowTime;
                _this.LastPosition = position;
                mapObj.getAddress({
                    lng: _this.LastPosition.Lng,
                    lat: _this.LastPosition.Lat
                }, function (data) {
                    _this.LastPosition.Address = data;
                    $.each(_this._updates, function (i, n) {
                        n.call(_this);
                    });
                });
            }
        }
    }
    $.showLoading();

    setTimeout(function () {
        trace_Control.terminal = new terminal_Control();
        $.hideLoading();
    }, 1000);

    $('#btnDistance').on('click', function () {
        mapObj.enableDistance();
    });
});