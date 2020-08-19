$(function () {

    /*
 全局 接口访问地址 IP ：port
  */
    var uRL = GlobalConfig.IPSSAddress;


    //获取URL 参数
    function getQueryVariable(variable) {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split("=");
            if (pair[0] == variable) {
                return pair[1];
            }
        }
        return (false);
    }

    var mapType = 0;
    var update_key = getQueryVariable("key");
    var update_name = getQueryVariable("name");
    var update_mt = 0; //有线无线，
    var czoom = '14';

    var leftarea_div, shrinkButton_div, map_div, table_ul, table_OutDiv, postion_checkbox, stopMinutes_checkbox;

    var monitor_div = $('#monitor_Div').css({
        'width': '100%',
        'height': '100%',
        'position': 'absolute'
    });
    leftarea_div = $('#leftDiv');
    table_ul = $('#tableul');

    $('#tableul').delegate("li", "click", function () {
        var self = $(this);
        var _this = pbcObj._playbackObj;
        if (_this && !_this._running) {
            _this.currentDot.removeClass('selected');
            _this.currentDot = self.addClass('selected');
            _this.nextDot = null;
            var data = _this.currentDot.data('dot');
            mapObj.updateMarker(_this.terminal, {
                position: data.LngLat
                , tipsContent: data.Content
            });
        }
    });

    $('#shrinkdiv').on('click', function () {
        if (leftarea_div.is(":hidden")) {
            openLeftarea();
        } else {
            closeLeftarea();
        }
    });

    //地图
    var mapObj = new wrt.mapClass({
        'id': 'mmap'
    });

    $('#mapSelect').on('change', function () {
        location.href = "/playback?key=" + update_key + '&name=' + escape(update_name) + '&mt=' + update_mt + '&mapType=' + $(this).val();
    });

    mapObj.setCenter({
        lng: window['centerlng'] || 120.551232420000,
        lat: window['centerlat'] || 31.87759189
    }, window['czoom'] || 10);


    function closeLeftarea() {
        leftarea_div.hide(500);
        $('#shrinkdiv i').removeClass('fa-chevron-left').addClass('fa-chevron-right');
    }

    function openLeftarea() {
        leftarea_div.show(500);
        $('#shrinkdiv i').removeClass('fa-chevron-right').addClass('fa-chevron-left');
    }

    function layout() {
        //控制table 横幅  Div
        var playControl_Div = $('<div></div>').css({
            'position': 'absolute',
            'top': '20px',
            'left': '10px',
            'z-index': '3000',
            'cursor': 'pointer',
            'background-color': 'white',
            'padding': '5px',
            'border-radius': '3px',
            'box-shadow': 'rgba(0, 0, 0, 0.0980392) 0px 1px 1px',
            'border': '1px solid rgb(210, 214, 222)'
        }).appendTo($('#mmap'));

        //时间选择
        $('<span>' + update_name + '</span>').appendTo($('<div></div>').css({
            'float': 'left', 'margin': '6px 10px 6px 10px',
        }).appendTo(playControl_Div));
        getObjFromCategory['rangedatetime'].call($('<div></div>').css({
            'float': 'left'
        }).appendTo(playControl_Div), {
            'placeholder': '请选择回放时间段',
            'width': '300px',
            'control_option': {
                "autoUpdateInput": false,
                "dateLimit": {
                    "months": 2
                }
            }
        });

        //播放速度选择
        var speed_div = $('<div></div>').css({
            'margin-left': '10px',
            'margin-top': '6px',
            'float': 'left'
        }).appendTo(playControl_Div);
        $('<span>慢</span>').css({'margin-right': '8px'}).appendTo(speed_div);
        $('<input />').css({'background': '#BABABA'}).addClass('speed_slider').appendTo(speed_div).slider({
            min: 1,
            max: 10,
            formatter: function (a) {
                return 'x' + a;
            }
        });
        $('<span>快</span>').css({'margin-left': '8px'}).appendTo(speed_div);
        var postion_checkboxDiv = $('<div></div>').css({
            'float': 'left', 'margin': '6px 5px 6px 10px',
        }).appendTo(playControl_Div).on('click', function () {
            if (postion_checkbox.hasClass('checkbox_true_full')) {
                postion_checkbox.removeClass('checkbox_true_full').addClass('checkbox_false_full');
            } else if (postion_checkbox.hasClass('checkbox_false_full')) {
                postion_checkbox.addClass('checkbox_true_full').removeClass('checkbox_false_full');
            }
        });
        postion_checkbox = $('<span class="postion_checkbox checkbox_false_full"></span>').appendTo(postion_checkboxDiv);
        $('<span>包含基站点</span>').appendTo(postion_checkboxDiv);

        var stopMinutes_checkboxDiv = $('<div></div>').css({
            'float': 'left', 'margin': '6px 5px 6px 10px',
        }).appendTo(playControl_Div);
        stopMinutes_checkbox = $('<span class="postion_checkbox checkbox_false_full"></span>').appendTo(stopMinutes_checkboxDiv).on('click', function () {
            if (stopMinutes_checkbox.hasClass('checkbox_true_full')) {
                stopMinutes_checkbox.removeClass('checkbox_true_full').addClass('checkbox_false_full');
                stopMinutes_checkboxDiv.find('.stopMinutes_text').val("").attr("disabled", true);
            } else if (stopMinutes_checkbox.hasClass('checkbox_false_full')) {
                stopMinutes_checkbox.addClass('checkbox_true_full').removeClass('checkbox_false_full');
                stopMinutes_checkboxDiv.find('.stopMinutes_text').attr("disabled", false);
            }
        });
        $('<span>停留标示</span><input type="text" class="stopMinutes_text" style="width:40px;" disabled="true"><span>分钟以上</span>').appendTo(stopMinutes_checkboxDiv);


        //播放按钮
        $('<a title="播放"><i class="fa fa-play"></i></a>').addClass('control_btn').appendTo(playControl_Div);
        $('<a title="暂停"><i class="fa fa-stop"></i></a>').addClass('control_btn').appendTo(playControl_Div);
        $('<a title="导出" id="exportBtn"><i class="fa fa-file-excel-o"></i></a>').addClass('control_btn').appendTo(playControl_Div);
        return playControl_Div;
    };


    var playBackControl = function (option) {
        var _this = this;
        _this._controlDiv = layout();
        _this._playbackObj = null;
        _this.PageSize = 100;
        _this.name = update_name;
        _this.key = update_key;
        _this.speed = 10;
        _this.StartTime = null;
        _this.SourceStartTime = null;
        _this.SourceEndTime = null;
        _this.SourceWithBS = false;
        _this._stopGetData = false;
        _this.SourceStopMinutes = 0;
        _this.SourceWithStop = false;
        _this.init();
    };

    playBackControl.prototype = {
        disablePlayControl: function () {
            var _this = this;
            _this._controlDiv.find('.range_date_time_second').attr('disabled', 'disabled');
            _this._controlDiv.find('.fa-play').removeClass('fa-play').addClass('fa-pause');
            if (postion_checkbox.hasClass('checkbox_true_full')) {
                postion_checkbox.addClass('checkbox_true_disable').removeClass('checkbox_true_full');
            } else if (postion_checkbox.hasClass('checkbox_false_full')) {
                postion_checkbox.addClass('checkbox_false_disable').removeClass('checkbox_false_full');
            }
        },
        enablePlayControl: function () {
            var _this = this;
            _this._controlDiv.find('.range_date_time_second').removeAttr('disabled');
            _this._controlDiv.find('.fa-pause').removeClass('fa-pause').addClass('fa-play');
            if (postion_checkbox.hasClass('checkbox_true_disable')) {
                postion_checkbox.removeClass('checkbox_true_disable').addClass('checkbox_true_full');
            } else if (postion_checkbox.hasClass('checkbox_false_disable')) {
                postion_checkbox.removeClass('checkbox_false_disable').addClass('checkbox_false_full');
            }
        },
        init: function () {
            var _this = this;
            _this._controlDiv.find('.fa-play').bind('click', function () {
                var tempObj = $(this);
                if (tempObj.hasClass('fa-play')) {
                    _this['play']();
                } else {
                    _this['pause']();
                }
            });
            _this._controlDiv.find('.fa-stop').bind('click', function () {
                _this['stop']();
            });
            _this._controlDiv.find('.fa-file-excel-o').bind('click', function () {
                _this['toExcel']();
            });
            _this._controlDiv.find('.speed_slider').val(5).on('change', function (e) {
                if (e.value.oldValue != e.value.newValue) {
                    var data_s = [1, 2, 4, 8, 10, 30, 100, 300, 900, 2000];
                    _this.speed = data_s[e.value.newValue - 1];
                    _this._playbackObj && (_this._playbackObj.speed = _this.speed);
                }
            });
            _this.insertTableDo = _this.insertTable_Wireless;
            if (!update_mt) {
                _this.insertTableDo = _this.insertTable_Wired;
                table_ul.delegate('.show_address', 'click', function () {
                    var _this = $(this);
                    var para = '';
                    if (_this.attr('lng') && _this.attr('lat')) {
                        mapObj.getAddress({
                            lng: _this.attr('lng'),
                            lat: _this.attr('lat')
                        }, function (data) {
                            _this.hide();
                            _this.after(data);
                        });
                    } else {
                        _this.hide();
                    }
                });
            }
        },
        getData: function (option, isFrist, callback) {
            var _this = this;
            $.updateSever({
                'para': {
                    'DeviceNumber': _this.key,
                    'IsFirst': isFrist,
                    'PageSize': _this.PageSize,
                    'StartTime': (option && option.startTime) || _this.StartTime,
                    'WithBS': (option && option.withBS) || _this.SourceWithBS,
                    'EndTime': (option && option.endTime) || _this.SourceEndTime,
                    'WithStop': (option && option.withStop) || _this.SourceWithStop,
                    'StopMinutes': (option && option.stopMinutes) || _this.SourceStopMinutes,
                    'MapType': mapType
                },
                'url': uRL + '/api/monitor/ShowHistory',
                'success': function (result) {
                    if (isFrist && result.Data.length == 0) {
                        $('.loading_mask').hide();
                        $.alert({
                            title: '提示',
                            content: '未找到相关数据'
                        });
                        return;
                    }
                    callback && callback();
                    _this.StartTime = result.MaxDateTime;
                    if (_this._playbackObj) {
                        _this._playbackObj.addData(result);
                    } else {
                        table_ul.empty();
                        _this._playbackObj = new playBack({
                            speed: _this.speed,
                            name: _this.name,
                            key: _this.key,
                            insertTableDo: _this.insertTableDo,
                            data: result,
                            endDo: function () {
                                _this.stop();
                            }
                        });
                    }
                    //todo
                    !_this._stopGetData && !!_this.StartTime && setTimeout(function () {
                        // _this.getData();
                        // alert("278行1秒");
                    }, 500);
                }
            });
        },
        toExcel: function () {
            var _this = this;
            var dataInput = _this._controlDiv.find('.range_date_time_second');
            var go = false;
            var startTime, endTime, withBS, withStop, stopMinutes;
            if (dataInput[0].value.indexOf('-->') >= 0) {
                var dal = dataInput[0].value.split('-->');
                if (dal.length == 2) {
                    startTime = dal[0] ? $.trim(dal[0]) : '';
                    endTime = dal[1] ? $.trim(dal[1]) : '';
                    var regexp = /^((\d{2}(([02468][048])|([13579][26]))[\-\/\s]?((((0?[13578])|(1[02]))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\-\/\s]?((0?[1-9])|([1-2][0-9])))))|(\d{2}(([02468][1235679])|([13579][01345789]))[\-\/\s]?((((0?[13578])|(1[02]))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\-\/\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\s((([0-1][0-9])|(2?[0-3]))\:([0-5]?[0-9])((\s)|(\:([0-5]?[0-9])))))?$/;
                    go = (!!startTime && !!endTime && regexp.test(startTime) && regexp.test(endTime));
                }
            }
            withBS = postion_checkbox.hasClass('checkbox_true_full') || postion_checkbox.hasClass('checkbox_true_disable');
            withStop = stopMinutes_checkbox.hasClass('checkbox_true_full') || stopMinutes_checkbox.hasClass('checkbox_true_disable');
            stopMinutes = _this._controlDiv.find('.stopMinutes_text').val().trim().length == 0 ? 0 : _this._controlDiv.find('.stopMinutes_text').val().trim();

            if (go) {
                $.showLoading('生成数据。。');
                //请求参数
                var param = {
                    PageSize: 500,
                    IsExcel: true,
                    StartTime: startTime,
                    EndTime: endTime,
                    WithBS: withBS,
                    WithStop: withStop,
                    StopMinutes: stopMinutes,
                    DeviceNumber: _this.key
                };
                param.Excel = {
                    'Title': '轨迹数据(' + param.StartTime + '~' + param.EndTime + ')',
                    'FileName': _this.name + '(' + param.StartTime + '~' + param.EndTime + ')',
                    'Params': [
                        {Name: "DeviceNumber", Display: "设备号", Width: 18}
                        , {Name: "LocationTime", Display: "定位时间", Width: 21}
                        , {Name: "LocationType", Display: "定位方式", Width: 10}
                        , {Name: "Speed", Display: "速度(km/h)", Width: 11}
                        , {Name: "Lng", Display: "经度", Width: 12}
                        , {Name: "Lat", Display: "纬度", Width: 12}
                        , {Name: "Address", Display: "地址", Width: 46}
                    ]
                };

                //ajax请求数据
                $.ajax({
                    type: 'POST',
                    responseType: 'blob',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(param),
                    url: uRL + '/api/monitor/ExportHistory',
                    success: function (data) {
                        console.log(data.Result);
                        var key = data.Result;

                        // const link = document.createElement('a');
                        // let blob = new Blob([data], {type: 'application/vnd.ms-excel'});
                        // link.style.display = 'none';
                        // link.href = URL.createObjectURL(blob);
                        // let num = '';
                        // link.setAttribute('download',  _this.name + '(' + param.StartTime + '~' + param.EndTime + ')'+ '轨迹');
                        // document.body.appendChild(link);
                        // link.click();
                        // document.body.removeChild(link);

                        // var ifObj = $('#download_if[iframe]', $('body'));
                        // if (ifObj.length == 0) {
                        //     ifObj = $('<iframe></iframe>').attr('name', 'download_if').attr('id', 'download_if').css({'display': 'none'}).appendTo($('body'));
                        // }
                        // ifObj.attr('src', URL+'/get' + downloadId);

                        $.hideLoading();
                        window.location.href = uRL + "/api/downloadExcelT?key=" + key;

                    }
                });


            } else {
                $.alert({
                    title: '提示',
                    content: '时间区间选择有误'
                });
            }
        },
        play: function () {
            var _this = this;
            var dataInput = _this._controlDiv.find('.range_date_time_second');
            var go = false;
            var startTime, endTime, withBS, withStop, stopMinutes;
            if (dataInput[0].value.indexOf('-->') >= 0) {
                var dal = dataInput[0].value.split('-->');
                if (dal.length == 2) {
                    startTime = dal[0] ? $.trim(dal[0]) : '';
                    endTime = dal[1] ? $.trim(dal[1]) : '';
                    var regexp = /^((\d{2}(([02468][048])|([13579][26]))[\-\/\s]?((((0?[13578])|(1[02]))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\-\/\s]?((0?[1-9])|([1-2][0-9])))))|(\d{2}(([02468][1235679])|([13579][01345789]))[\-\/\s]?((((0?[13578])|(1[02]))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\-\/\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\s((([0-1][0-9])|(2?[0-3]))\:([0-5]?[0-9])((\s)|(\:([0-5]?[0-9])))))?$/;
                    go = (!!startTime && !!endTime && regexp.test(startTime) && regexp.test(endTime));
                }
            }
            withBS = postion_checkbox.hasClass('checkbox_true_full') || postion_checkbox.hasClass('checkbox_true_disable');
            withStop = stopMinutes_checkbox.hasClass('checkbox_true_full') || stopMinutes_checkbox.hasClass('checkbox_true_disable');
            stopMinutes = _this._controlDiv.find('.stopMinutes_text').val().trim().length == 0 ? 0 : _this._controlDiv.find('.stopMinutes_text').val().trim();

            if (go && _this.SourceStartTime == startTime && _this.SourceEndTime == endTime && _this._playbackObj && _this.SourceWithBS === withBS && _this.SourceWithStop === withStop && _this.SourceStopMinutes == stopMinutes) {
                _this.disablePlayControl();
                _this._playbackObj.replay();
                if (!!_this.StartTime) {
                    _this._stopGetData = false;
                    _this.getData();
                }
            } else {
                if (_this._playbackObj) {
                    _this._playbackObj.clear();
                    _this._playbackObj = null;
                }
                if (go) {
                    $('.loading_mask').show();
                    _this.getData({
                        startTime: startTime,
                        endTime: endTime,
                        withBS: withBS,
                        withStop: withStop,
                        stopMinutes: stopMinutes
                    }, true, function () {
                        _this.SourceStartTime = startTime;
                        _this.SourceEndTime = endTime;
                        _this.SourceWithBS = withBS;
                        _this.SourceWithStop = withStop;
                        _this.SourceStopMinutes = stopMinutes;
                        _this._stopGetData = false;
                        _this.disablePlayControl();
                    });
                } else {
                    $.alert({
                        title: '提示',
                        content: '时间区间选择有误'
                    });
                }
            }
        },
        pause: function () {
            var _this = this;
            _this._controlDiv.find('.fa-pause').removeClass('fa-pause').addClass('fa-play');
            _this._playbackObj.pause();
            _this._stopGetData = true;
        },
        stop: function () {
            var _this = this;
            _this.enablePlayControl();
            _this._playbackObj.reset();
            _this._stopGetData = true;
        },
        insertTable_Wireless: function (dot) {
            var _this = this;
            dot.LocationType = (dot.LocationType == "1" ? '北斗' : '基站');
            var saveDot = {
                LngLat: {lng: dot.Lng, lat: dot.Lat},
                Content: _this.getContent(dot),
                LocationTime: $.formatJsDateTime(dot.LocationTime),
                LocationType: dot.LocationType,
                Speed: dot.Speed,
                StopContent: (dot.StopMinutes ? _this.getStopContent(dot) : null)
            };
            var row = '<li' + _this.background + '><span class="dot_cell1">' + $.toDateTime(dot.LocationTime) + '</span><span class="dot_cell2">' + dot.Speed + 'km/h</span><span class="dot_cell3">' + dot.LocationType + '</span><span class="dot_cell4" lng="' + dot.Lng + '" lat="' + dot.Lat + '"></span></li>';
            if (_this.background) {
                _this.background = '';
            } else {
                _this.background = ' class="table-alt"';
            }
            return {
                html: row, dot: saveDot, autoAddress: function (_thisObj) {
                    _this.mapObj.getAddress({
                        lng: _thisObj.attr('lng'),
                        lat: _thisObj.attr('lat')
                    }, function (data) {
                        _thisObj.html(data);
                    });
                }
            };
        },
        insertTable_Wired: function (dot) {
            var _this = this;
            dot.LocationType = (dot.LocationType == "1" ? '北斗' : '基站');
            var saveDot = {
                LngLat: {lng: dot.Lng, lat: dot.Lat},
                Content: _this.getContent(dot),
                LocationTime: $.formatJsDateTime(dot.LocationTime),
                LocationType: dot.LocationType,
                Speed: dot.Speed,
                StopContent: (dot.StopMinutes ? _this.getStopContent(dot) : null)
            };
            var row = '<li' + _this.background + '><span class="dot_cell1">' + $.toDateTime(dot.LocationTime) + '</span><span class="dot_cell2">' + dot.Speed + 'km/h</span><span class="dot_cell3">' + dot.LocationType + '</span><span class="dot_cell4"><a class="show_address" style="cursor:pointer;" lng="' + dot.Lng + '" lat="' + dot.Lat + '">查看</a></span></li>';
            if (_this.background) {
                _this.background = '';
            } else {
                _this.background = ' class="table-alt"';
            }
            return {
                html: row, dot: saveDot, autoAddress: function (_thisObj) {

                }
            };
        }
    };

    var playBack = function (options) {
        var _this = this;
        _this.isEnd = false;
        _this._startDot = options.data.Data[0];
        _this._lastDot = null;
        _this._currentIndex = 0;
        _this._allIds = [];
        _this.speed = options.speed;
        _this.mapObj = mapObj;
        _this.name = options.name;
        _this.key = options.key;
        _this.terminal = null;
        _this.endDo = options.endDo;
        _this.insertTable = options.insertTableDo;
        _this._running = false;
        _this._stoptime = 0;
        _this.background = '';
        _this.currentDot = null;
        _this.nextDot = null;
        _this.init(options.data);
    };

    playBack.prototype = {
        init: function (result) {
            var _this = this;
            _this.drawStart();
            _this.addData(result);
            _this.drawTerminal();
            setTimeout(function () {
                openLeftarea();
                _this._running = true;
                _this.getData();
                //todo
                // alert("514行1秒");
            }, 500);
        },

        addData: function (result) {
            var _this = this;
            //循环数据
            if (result.Data.length > 0) {
                var line_dots = [];
                var li_html = [];
                var dots = [];
                _this._lastDot && line_dots.push(_this._lastDot.LngLat);
                $.each(result.Data, function (i, n) {
                    var dot = _this.insertTable(n);
                    if (!_this._lastDot || dot.dot.LocationTime > _this._lastDot.LocationTime) {
                        li_html.push(dot.html);
                        dots.push(dot);
                        _this._lastDot = dot.dot;
                        //加入线点
                        line_dots.push(_this._lastDot.LngLat);
                    }
                    //by ljw 20180907
                    dot.dot.StopContent && _this.drawStop(dot.dot);
                });
                if (dots.length > 0) {
                    //插入表格
                    var lis = $(li_html.join('')).appendTo(table_ul);
                    lis.each(function (i, n) {
                        $(n).data('dot', dots[i].dot);
                        dots[i].autoAddress($(n).find('span.dot_cell4'));
                    });
                    _this.drawLine(line_dots);
                }
            }
            //加入结束点
            if (!result.MaxDateTime) {
                _this.drawEnd(_this._lastDot.LngLat);
                table_ul.find('li:last').attr('isEnd', 1);
            }
        },

        getData: function () {
            var _this = this;
            if (_this.nextDot) {
                _this.currentDot = _this.nextDot;
                _this.nextDot = null;
            }
            if (_this.currentDot.attr('isEnd')) {
                _this.endDo && _this.endDo();
                $('.loading_mask').hide();
                return;
            }
            var temp = _this.currentDot.next('li');
            if (temp.length == 0) {
                $('.loading_mask').show();
                setTimeout(function () {
                    _this.getData();
                    //todo
                    alert("572行1秒");
                }, 1000);
                return;
            }
            _this.nextDot = temp;
            $('.loading_mask').hide();
            _this.moveTerminal();
        },
        drawStart: function () {
            var _this = this;
            var id = _this.key + '_start';
            _this.enterMap_Marker({
                dot: _this._startDot,
                zIndex: 120,
                icon: '/Content/map/icons/start_CN.png',
                id: id,
                size: {
                    w: 32, h: 32
                }
            });
            _this._allIds.push(id);
        },
        drawEnd: function (lnglat) {
            var _this = this;
            var id = _this.key + '_end';
            _this.enterMap_Marker({
                dot: {
                    Lat: lnglat.lat, Lng: lnglat.lng
                },
                zIndex: 121,
                icon: '/Content/map/icons/end_CN.png',
                id: id,
                size: {
                    w: 32, h: 32
                }
            });
            _this._allIds.push(id);
        },
        drawStop: function (dot) {
            var _this = this;
            var id = _this.key + '_stop' + dot.LngLat.lat + dot.LngLat.lng;
            if (_this._allIds.indexOf(id) < 0) {
                var stopMarker = _this.enterMap_Marker({
                    dot: {
                        Lat: dot.LngLat.lat, Lng: dot.LngLat.lng
                    },
                    zIndex: 122,
                    icon: '/Content/map/icons/blue_flag.png',
                    id: id,
                    content: dot.StopContent,
                    hideWin: true,
                    size: {
                        w: 32, h: 32
                    }
                });

                var tipsContent = dot.StopContent;
                _this.mapObj.addMarkerClickEvent(stopMarker, function (m) {
                    m.tipsContent = tipsContent;
                });
                _this._allIds.push(id);
            }
        },
        drawAlarm: function (dot, i) {
            //var _this = this;
            //var id = _this.key + '_alarm';
            //_this.enterMap_Marker({
            //    dot: dot,
            //    zIndex: 122,
            //    icon: '/Content/map/icons/blue_flag.png',
            //    id: id
            //});
            //_this._allIds.push(id);
        },
        drawTerminal: function () {
            var _this = this;
            var id = _this.key + '_terminal';
            _this.terminal = _this.enterMap_Marker({
                dot: _this._startDot,
                zIndex: 200,
                //name: _this.name,
                icon: '/Content/map/icons/Map_Online2.png',
                content: _this.getContent(_this._startDot),
                size: {
                    w: 19, h: 33
                },
                id: id
            });

            _this.mapObj.addMarkerClickEvent(_this.terminal, function (m) {
                var currentDot = _this.currentDot.data('dot');
                m.tipsContent = currentDot.Content;
            });
            _this.mapObj.addMarkerMoveendEvent(_this.terminal, function () {
                _this.moveNext();
            });

            mapObj.showTips(_this.terminal);

            //_this.terminal.obj.on('moveend', function () {
            //    _this.terminal.infoWindow.setPosition(_this.nextDot.data('dot').LngLat);
            //    _this.moveNext();
            //});

            _this.selectRow(true);

            //_this.terminal.obj.on('moving', function () {
            //    _this.terminal.infoWindow.setPosition(this.getPosition());
            //});

            _this._allIds.push(id);
        },
        drawLine: function (dots) {
            var _this = this;
            var id = _this.key + '_line' + _this._currentIndex;
            //_this.mapObj.addPolyline(
            //    dots,
            //    id,
            //    "",
            //    {
            //        hideClear: true,
            //        noFitView: true,
            //        type: 'locationPois'
            //    });

            _this.mapObj.drawLine(dots);
            _this._allIds.push(id);
            _this._currentIndex = _this._currentIndex + 1;
        },
        enterMap_Marker: function (info) {
            var _this = this;
            var position = {
                lat: info.dot.Lat, lng: info.dot.Lng
            };
            var id = info.id;
            var name = info.name;
            var markerOption = {
                size: info.size,
                icon: info.icon,
                zIndex: info.zIndex,
                hideClear: true,
                noSetCenter: true,
                angle: 0
            };
            var infoWindowOption = null;
            if (info.content) {
                infoWindowOption = {
                    content: info.content,
                    hideWin: info.hideWin
                };
            }
            mapObj.setCenter(position);
            return mapObj.addMarker({
                icon: info.icon,
                text: name,
                position: position,
                tipsContent: info.content,
                size: info.size
            });
            //return _this.mapObj.addLocationPoi(position, id, name, markerOption, infoWindowOption);
        },
        moveTerminal: function () {
            var _this = this;
            if (!_this._running) {
                return;
            }
            var currentDot = _this.currentDot.data('dot');
            var nextDot = _this.nextDot.data('dot');
            if (currentDot.LngLat.lng != nextDot.LngLat.lng || currentDot.LngLat.lat != nextDot.LngLat.lat) {
                _this._stoptime = 0;
                var oldLngLat = currentDot.LngLat;
                var nextLngLat = nextDot.LngLat;
                var distance = mapObj.getDistance(oldLngLat, nextLngLat);//单位是米
                var speed_base = (distance * 3600) / (1000 * 10);//平均分为10个段，转换为公里/小时
                var speed_para = _this.speed;
                mapObj.moveMarker(_this.terminal, oldLngLat, nextLngLat, speed_para, speed_base);
                //_this.terminal.obj.moveTo([oldLngLat, nextLngLat], speed_para * speed_base);
                //_this.terminal.overViewDo(nextLngLat);
            } else {
                _this.moveNext();
            }
            //by ljw 20180907
            //currentDot.StopContent && _this.drawStop(currentDot);
        },
        moveNext: function () {
            var _this = this;
            var sr = _this.selectRow();
            $('#tipsLocationTime').html($.dateToJson(sr.LocationTime));
            $('#tipsLat').html(sr.LngLat.lat);
            $('#tipsLng').html(sr.LngLat.lng);
            $('#tipsLocationType').html(sr.LocationType);
            $('#tipsSpeed').html(sr.Speed);
            //mapObj.updateTipsContent(sr.Content);
            //_this.terminal.infoWindow.setContent(_this.selectRow().Content);
            setTimeout(function () {
                _this.getData();
                //todo
                // alert("770行0.5秒");
            }, 50);
        },
        replay: function () {
            openLeftarea();
            this._running = true;
            this.getData();
        },
        pause: function () {
            this._running = false;
            mapObj.stopMovingMarker(this.terminal);
            //this.terminal.obj.stopMove();
            //var currentDot = this.currentDot.data('dot');
            //this.terminal.infoWindow.setContent(currentDot.Content);
            //this.terminal.setPosition(currentDot.LngLat);
            this.nextDot = null;
        },
        reset: function () {
            var _this = this;
            var currentDot = _this.selectRow(true);
            _this._running = false;
            mapObj.stopMovingMarker(_this.terminal);
            mapObj.updateMarker(_this.terminal, {position: currentDot.LngLat, tipsContent: currentDot.Content});
            //_this.terminal.obj.stopMove();
            //_this.terminal.infoWindow.setContent(currentDot.Content);
            //_this.terminal.setPosition(currentDot.LngLat);
            table_ul.scrollTop(0);
        },
        getContent: function (dot) {
            var _this = this;
            var info = [];
            info.push("<table>");
            info.push("<tr><td style='text-align:right;'>时间：</td><td id='tipsLocationTime'>" + $.toDateTime(dot.LocationTime) + "</td></tr>");
            //info.push("<tr><td style='text-align:right;'>设备号：</td><td>" + self.terminalNo + "</td></tr>");
            info.push("<tr><td style='text-align:right;'>经度：</td><td id='tipsLat'>" + dot.Lat + "</td></tr>");
            info.push("<tr><td style='text-align:right;'>纬度：</td><td id='tipsLng'>" + dot.Lng + "</td></tr>");
            info.push("<tr><td style='text-align:right;'>定位：</td><td id='tipsLocationType'>" + dot.LocationType + "</td></tr>");
            info.push("<tr><td style='text-align:right;'>速度：</td><td id='tipsSpeed'>" + dot.Speed + "</td></tr>");
            info.push("</table>");
            return info.join('');
        },

        getStopContent: function (dot) {
            var _this = this;
            var info = [];
            info.push("<table>");
            info.push("<tr><td style='text-align:right;'>开始时间：</td><td>" + $.toDateTime(dot.LocationTime) + "</td></tr>");
            info.push("<tr><td style='text-align:right;'>结束时间：</td><td>" + (dot.StopEndTime ? $.toDateTime(dot.StopEndTime) : '--') + "</td></tr>");
            info.push("<tr><td style='text-align:right;'>停留间隔：</td><td>" + (dot.StopEndTime ? (dot.StopMinutes == 0 ? "小于一分钟" : (dot.StopMinutes + '分钟')) : '--') + "</td></tr>");
            info.push("</table>");
            return info.join('');
        },
        insertTable: function (dot) {
            return this.insertTableDo.call(this, dot);
        },
        selectRow: function (isFirst) {
            var _this = this;
            var temp = null;
            _this.currentDot && _this.currentDot.removeClass('selected');
            if (isFirst) {
                _this.currentDot = $('li:first', table_ul).addClass('selected');
                _this.nextDot = null;
                temp = _this.currentDot;
            } else {
                _this.nextDot.addClass('selected');
                temp = _this.nextDot;
            }
            var p_top = _this.currentDot.position().top;
            if (p_top - table_ul.height() > 50) {
                var st = table_ul.scrollTop() + p_top - 25;
                table_ul.scrollTop(st);
            }
            return temp.data('dot');
        },
        clear: function () {
            var _this = this;
            _this.mapObj.clearOverlays();
        }
    };

    var pbcObj = new playBackControl();

    $('#btnDistance').on('click', function () {
        mapObj.enableDistance();
    });


});