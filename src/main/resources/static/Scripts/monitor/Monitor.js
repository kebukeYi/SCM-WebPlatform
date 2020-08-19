var cars = {}, terminals_ControlObj;

/*
全局 接口访问地址 IP ：port
*/
var URL = GlobalConfig.IPSSAddress;
var cmdInfos = [];//全局设备下发指令号码
$(function () {
    var leftarea_div = $('#leftarea'), shrinkButton_div = $('#shrink'), map_div = $('#mmap'), mapFrame,
        currentgroupname, btns_Div, page_Div, termailsearchObj, terminal_table_body, zTree_Div_Obj, zTreeObj,
        group_Selected, dangerPoint_Selected;
    var dn = $('#deviceNumber').attr('dn');
    var loadLeftarea = function () {
        var groupModel = function () {
            //组模块====Start
            var outDiv_lad = $('<div></div>').css({
                'height': '30%',
                'width': '100%',
                'margin-bottom': '5px',
                'border-radius': '3px',
                'background-color': 'white',
                'box-shadow': '0 1px 1px rgba(0,0,0,0.1)',
                'border-top': '1px solid #d2d6de'
            }).appendTo(leftarea_div);
            //组搜索框====Start
            var key_groupsearch = {
                total: 0,
                cur: 0,
                v: ''
            };
            var groupsearchObj = $('<div></div>').addClass('input-group margin').append($('<input type="text" class="form-control" placeholder="组名">'
                + '<span class="input-group-btn">'
                + '<button type="button" class="btn btn-info btn-flat">搜索</button>'
                + '</span>')).appendTo($('<div></div>')
                .css({'height': '40px'})
                .appendTo(outDiv_lad));

            function searchDo() {
                var key_temp = groupsearchObj.find('.form-control').val();
                $('.selected_terminal_group').removeClass('selected_terminal_group');
                if (key_temp) {
                    if (key_groupsearch.v == key_temp) {
                        if (key_groupsearch.cur < (key_groupsearch.total - 1)) {
                            key_groupsearch.cur = key_groupsearch.cur + 1;
                        } else {
                            key_groupsearch.cur = 0;
                        }
                        var curNode = key_groupsearch.nodes[key_groupsearch.cur];
                        var tid = curNode.tId;
                        while (curNode.parentTId) {
                            var pId = curNode.parentTId;
                            curNode = zTreeObj.getNodeByTId(pId);
                            zTreeObj.expandNode(curNode, true, false, true);
                        }
                        $("#" + tid, zTree_Div_Obj).focus().blur().find('.node_name:first').addClass('selected_terminal_group');
                        return;
                    } else {
                        var nodes = zTreeObj.getNodesByParamFuzzy("OrgName", key_temp, null);
                        if (nodes.length > 0) {
                            key_groupsearch = {
                                total: nodes.length,
                                cur: 0,
                                v: key_temp,
                                nodes: nodes
                            };
                            var curNode = nodes[key_groupsearch.cur];
                            var tid = curNode.tId;
                            while (curNode.parentTId) {
                                var pId = curNode.parentTId;
                                curNode = zTreeObj.getNodeByTId(pId);
                                zTreeObj.expandNode(curNode, true, false, true);
                            }
                            $("#" + tid, zTree_Div_Obj).focus().blur().find('.node_name:first').addClass('selected_terminal_group');
                            return;
                        } else {
                            $.alert({
                                title: '提示',
                                content: '没有符合的',
                            });
                        }
                    }
                }
                key_groupsearch = {
                    total: 0,
                    cur: 0,
                    nodes: [],
                    v: ''
                };
                var ikey = zTreeObj.setting.data.simpleData.idKey;
                var node = zTreeObj.getNodeByParam(ikey, group_Selected, null);
                zTreeObj.selectNode(node);
            };
            groupsearchObj.find('.btn-flat').bind('click', function () {
                searchDo();
            });
            $('[type="text"]', groupsearchObj).keydown(function (event) {
                if (event.keyCode == 13) { //绑定回车 
                    searchDo();
                    $(this).focus();
                }
            });
            //组搜索框====End
            //组搜索结果框====Start
            zTree_Div_Obj = $('<ul></ul>')
                .addClass('ztree')
                .attr('id', 'tarminal_grouptree')
                .css({
                    'overflow-y': 'auto',
                    'overflow-x': 'auto',
                    'height': '100%'
                })
                .appendTo($('<div></div>')
                    .css({'height': 'calc( 100% - 50px )'})
                    .appendTo(outDiv_lad));
            //组搜索结果框====End
            //组模块====End
            //组树初始化===Start
            var loadTree = function () {
                var setting = {
                    async: {
                        enable: true,
                        contentType: "application/json",
                        dataType: 'json',
                        type: 'post',
                        url: URL + '/api/Group/ShowChildren',
                        dataFilter: function (treeId, parentNode, responseResult) {
                            if (responseResult.Status == 0) {
                                $.alert({
                                    title: '提示',
                                    content: responseResult.ErrorMessage,
                                });
                                return [];
                            }
                            var responseData = responseResult.Result;
                            var key = zTreeObj.setting.data.simpleData.idKey;
                            group_Selected = group_Selected || (responseData.length > 0 && responseData[0][key]);
                            if (!group_Selected) {
                                $.alert({
                                    title: '提示',
                                    content: '获取组失败',
                                });
                            }
                            $.each(responseData, function (i, node) {
                                node.iconSkin = 'default';
                            });
                            currentgroupname.html(responseData[0].OrgName);
                            currentgroupname.attr('title', responseData[0].OrgName);
                            return responseData;
                        }
                    },
                    view: {
                        dblClickExpand: false,
                        showLine: true,
                        selectedMulti: false
                    },
                    data: {
                        key: {
                            name: 'OrgName',
                            title: ''
                        },
                        simpleData: {
                            enable: true,
                            idKey: 'Id',
                            pIdKey: 'ParentId',
                        }
                    },
                    callback: {
                        onClick: function (event, treeId, treeNode) {
                            group_Selected = treeNode.Id;
                            terminals_ControlObj.reload();
                            currentgroupname.html(treeNode.OrgName);
                            currentgroupname.attr('title', treeNode.OrgName);
                            $('.selected_terminal_group', zTree_Div_Obj).removeClass('selected_terminal_group');
                        }, onAsyncSuccess: function (event, treeId, treeNode, msg) {
                            //zTreeObj.expandAll(true);
                            var nodes = zTreeObj.getNodes();
                            for (var i = 0; i < nodes.length; i++) { //设置节点展开
                                zTreeObj.expandNode(nodes[i], true, false, true);
                            }
                            ;
                            var key = zTreeObj.setting.data.simpleData.idKey;
                            var node = zTreeObj.getNodeByParam(key, group_Selected, null);
                            zTreeObj.selectNode(node);
                        }
                    }
                };
                zTreeObj = $.fn.zTree.init(zTree_Div_Obj, setting, []);
            };
            loadTree();
            //组树初始化===End
        }
        var terminalModel = function () {
            //终端模块====Start
            var terminal_Divs = $('<div></div>').css({
                'height': '70%',
                'background-color': 'white',
                'width': '100%',
                'border-radius': '3px',
                'box-shadow': '0 1px 1px rgba(0,0,0,0.1)',
                'border-top': '1px solid #d2d6de'
            }).appendTo(leftarea_div);
            //组名显示====Start
            var currentgroup_Div = $('<div></div>')
                .css({
                    'height': '30px',
                    'margin-top': '5px',
                    'padding-left': '15px',
                    'font-size': '20px',
                    'font-weight': '900'
                })
                .appendTo(terminal_Divs);
            currentgroupname = $('<div></div>').css({
                'float': 'left'
            }).appendTo(currentgroup_Div);
            var showonly_Div = $('<div>&nbsp;&nbsp;<input type="checkbox" id="withChildren" style="vertical-align:middle;margin:0px 2px 0px 0px" /><span style="vertical-align:middle" >包含子组</span></div>').css({
                'margin-left': '10px',
                'font-size': '12px',
                'font-weight': '300'
            }).appendTo(terminal_Divs);
            $('[type="checkbox"]', showonly_Div).bind('click', function () {
                if (terminals_ControlObj) {
                    terminals_ControlObj.reload();
                }
            });
            //组名显示====End
            //终端显示过滤====Start
            btns_Div = $('<div></div>').css({
                'height': '30px',
                'margin-top': '5px'
            }).appendTo(terminal_Divs);
            //终端显示过滤====End
            //终端搜索====Start
            termailsearchObj = $('<div></div>')
                .addClass('input-group margin')
                .appendTo($('<div></div>')
                    .css({'margin-top': '5px', 'height': '40px'})
                    //.appendTo(terminal_Divs)).append($('<select style="width:30%;height:33px;border-color:#d2d6de"><option value=1>设备号</option><option value=2>设备名</option><option value=4>SIM卡号</option></select><input type="text" style="width:70%;font-size:12px;float:right" class="form-control" placeholder="设备名或设备号或SIM卡号">'
                    .appendTo(terminal_Divs)).append($('<input type="text" style="width:100%;font-size:12px;float:right" class="form-control" placeholder="设备号">'
                    + '<span class="input-group-btn">'
                    + '<button type="button" class="btn btn-info btn-flat">搜索</button>'
                    + '</span>'));
            termailsearchObj.find('.btn-flat').bind('click', function () {
                if (terminals_ControlObj) {
                    terminals_ControlObj.SearchKey = $.trim(termailsearchObj.find('.form-control').val()) || '';
                    terminals_ControlObj.SearchType = termailsearchObj.find('select').val() || '';
                    terminals_ControlObj.reload();
                }
            });
            //终端搜索====End

            //终端搜索结果====Start
            var selectAll_temp = $('<div></div>').css({'margin-top': '5px', 'height': '25px'}).appendTo(terminal_Divs);

            $('<a>监控当页</a>').addClass('btn btn-app').css({
                'padding': '5px',
                'height': '30px',
                'background-color': '#00acd6',
                'color': '#fff',
                'min-width': '30px'
            }).appendTo(selectAll_temp).bind('click', function () {
                var curul = $('ul:not(:hidden)', terminal_table_body);
                var terminalInfos = [];
                $('li.terminal_table_children', curul).each(function (i, n) {
                    var data = $(n).data('info');
                    if (data.Disabled) {
                        return;
                    }
                    var terminalObj = terminals_ControlObj.terminalObjs[data.DeviceNumber];
                    if (!terminalObj) {
                        terminalInfos.push(data);
                    }
                });
                terminalInfos.length > 0 && terminals_ControlObj._monitorPositions(terminalInfos);
                console.log(terminalInfos);
            });

            $('<a>取消当页监控</a>').addClass('btn btn-app').css({
                'padding': '5px',
                'margin-left': '15px',
                'height': '30px',
                'background-color': '#00acd6',
                'color': '#fff',
                'min-width': '30px'
            }).appendTo(selectAll_temp).bind('click', function () {
                var curul = $('ul:not(:hidden)', terminal_table_body);
                $('li.terminal_table_children', curul).each(function (i, n) {
                    var data = $(n).data('info');
                    if (data.Disabled) {
                        return;
                    }
                    var terminalObj = terminals_ControlObj.terminalObjs[data.DeviceNumber];
                    if (terminalObj) {
                        terminals_ControlObj._cancelPosition(data.DeviceNumber);
                    }
                });
            });

            $('<a>当页指令下发</a>').addClass('btn btn-app').css({
                'padding': '5px',
                'height': '30px',
                'margin-left': '15px',
                'background-color': '#00acd6',
                'color': '#fff',
                'min-width': '30px'
            }).appendTo(selectAll_temp).bind('click', function () {
                var curul = $('ul:not(:hidden)', terminal_table_body);
                var terminalInfos = [];
                $('li.terminal_table_children', curul).each(function (i, n) {
                    var data = $(n).data('info');
                    if (data.Disabled) {
                        return;
                    }
                    var terminalObj = terminals_ControlObj.terminalObjs[data.DeviceNumber];
                    if (!terminalObj) {
                        terminalInfos.push(data);
                    }
                    if (terminalObj) {
                        terminals_ControlObj._cancelPosition(data.DeviceNumber);
                    }

                });
                terminalInfos.length > 0 && terminals_ControlObj._monitorCmds(terminalInfos);
            });

            terminal_table_body = $('<div></div>').css({
                'margin-top': '5px', 'height': 'calc( 100% - 210px )'
            }).appendTo(terminal_Divs);
            //终端搜索结果====End


            //翻页按钮===Start
            page_Div = $('<div></div>').css({
                'height': '35px'
            }).appendTo(terminal_Divs);
            $('<button type="button" class="btn btn-default">上一页</button>')
                .css({'margin-left': '160px'})
                .addClass('up_page').appendTo(page_Div).bind('click', function () {
                if (terminals_ControlObj) {
                    terminals_ControlObj.start = terminals_ControlObj.start - terminals_ControlObj.limit;
                    terminals_ControlObj.page = terminals_ControlObj.page - 1;
                    terminals_ControlObj.load(true);
                }
            });
            $('<button type="button" class="btn btn-default">下一页</button>')
                .css({'margin-left': '10px'})
                .addClass('down_page').appendTo(page_Div).bind('click', function () {
                if (terminals_ControlObj) {
                    terminals_ControlObj.start = terminals_ControlObj.start + terminals_ControlObj.limit;
                    terminals_ControlObj.page = terminals_ControlObj.page + 1;
                    terminals_ControlObj.load(true);
                }
                ;
            });
            //翻页按钮===End
            //终端模块====End
            //终端显示过滤初始化===Start
            var btnObjs = [{
                'key': 'Total',
                'name': '所有',
                'class': 'bg-purple'
            }, {
                'key': 'Online',
                'name': '在线',
                'class': 'bg-green'
            }, {
                'key': 'Offline',
                'name': '离线',
                'class': 'bg-red'
            },
                //     {
                //     //todo 修改激活
                //     'key': 'Unknown',
                //     'name': '未激活',
                //     'class': 'bg-warning'
                // },
                {
                    'key': 'Attention',
                    'name': '关注',
                    'class': 'bg-yellow'
                }];

            function select_btn(btn_name) {
                var ulshowObj = $('.' + btn_name + '_ul');
                if (ulshowObj.is(':hidden')) {
                    $('.btn', btns_Div).css({'background-color': '#f4f4f4', 'color': '#000'});
                    $('[key="' + btn_name + '"]', btns_Div).css({'background-color': '#00acd6', 'color': '#fff'});
                    $('ul', terminal_table_body).hide().empty();
                    ulshowObj.show();
                    if (terminals_ControlObj) {
                        terminals_ControlObj.Flag = ulshowObj.attr('index')
                        terminals_ControlObj.reload();
                    }
                }
            }

            $.each(btnObjs, function (i, n) {
                $('<a key="' + n.key + '"><span class="badge ' + n.class + ' ' + n.key + '_span' + '" >0</span>' + n.name + '</a>')
                    .addClass('btn btn-app')
                    .css({
                        'padding': '5px 5px',
                        'height': '30px',
                        'min-width': '55px'
                    }).appendTo(btns_Div).bind('click', function () {
                    select_btn($(this).attr('key'));
                });
                $('<ul class="monitor_ul"></ul>').css({
                    'height': '100%',
                    'margin': 0,
                    'padding': 0,
                    'list-style': 'none',
                    'overflow-y': 'auto',
                    'overflow-x': 'auto',
                    'display': 'none'
                }).addClass(n.key + '_ul').attr('key', n.key).attr('index', i).appendTo(terminal_table_body);
            });
            select_btn('Total');
            //终端显示过滤初始化===End
        };
        var shrinkModel = function () {
            //收缩按钮===Start
            shrinkButton_div.bind('click', function () {
                var _thisObj = $(this);
                if (leftarea_div.is(":hidden")) {
                    leftarea_div.show(500);
                    $('i', _thisObj).removeClass('fa-chevron-right').addClass('fa-chevron-left');
                } else {
                    leftarea_div.hide(500);
                    $('i', _thisObj).removeClass('fa-chevron-left').addClass('fa-chevron-right');
                }
            }).append($('<div></div>').css({
                'display': 'table-cell',
                'vertical-align': 'middle'
            }).append($('<i></i>').addClass('fa fa-chevron-left')));
            //收缩按钮====End
        }
        groupModel();
        terminalModel();
        shrinkModel();
    };
    var noloadLeftarea = function () {
        leftarea_div.remove();
        shrinkButton_div.remove();
    };
    dn ? noloadLeftarea() : loadLeftarea();
    var mapModel = function () {
        mapFrame = $("#mapFrame")[0].contentWindow;
        $('#mapSelect').on('change', function () {

            if (typeof mapFrame.getView === "function") { //是函数    其中 FunName 为函数名称
                var v = mapFrame.getView();
                centerlng = v.lng;
                centerlat = v.lat;
                czoom = v.z;
            }

            //mapFrame.location.href = '/Monitor/map?mapType=' + $(this).val();
            $.cookie('mapType', $(this).val());
            showDangerPointObjs = [];
            $('#btnDangerPoint').hasClass('icon-dangerPointHover') && $('#btnDangerPoint').removeClass('icon-dangerPointHover').addClass('icon-dangerPoint');
        });
        $('#btnView').on('click', function () {
            var v = mapFrame.getView();
            $.updateSever({
                'para': {
                    'MapView': v.lng + ',' + v.lat + ',' + v.z
                },
                'url': URL + '/api/User/SaveMapView',
                'success': function (result) {
                    $.alert({
                        title: '提示',
                        content: '保存视角成功！'
                    });
                }
            });
        });
        $('#btnDistance').on('click', function () {
            mapFrame.enableDistance();
        });

        var showDangerPointObjs = [];
        $('#btnDangerPoint').on('click', function () {
            var that = this;

            if (!$.isEmptyObject(showDangerPointObjs)) {
                $.each(showDangerPointObjs, function (i, n) {
                    mapFrame.clearFigure(n);
                });
                showDangerPointObjs = [];
                $(that).hasClass('icon-dangerPointHover') && $(that).removeClass('icon-dangerPointHover').addClass('icon-dangerPoint');
                return false;
            }

            var para = {
                'WithChildren': ($('#withChildren').length > 0 && $('#withChildren')[0].checked),
                'limit': 100,
                'start': 0,
                'page': 1
            };
            group_Selected && (para.OrgId = group_Selected);

            $.updateSever({
                'para': para,
                'url': URL + '/api/DangerPoint/FenceListByOrg',
                'success': function (result) {

                    var showDangerPoint = function (rowData) {
                        var drawfence = [function () {
                            var l = rowData.FenceData.split('|');
                            var district = mapFrame.showAdminArea(l[0], l[1]);
                            showDangerPointObjs.push({type: 0, data: district});
                        }, function () {
                            var l = rowData.FenceData.replace(/\||,/g, "-").split('-');
                            var circle = mapFrame.addCircle(l[0], l[1], l[2], rowData.FenceName + "|" + rowData.DangerPointTypeText);
                            showDangerPointObjs.push({type: 1, data: circle});
                        }];
                        drawfence[rowData.FenceType]();
                    }

                    result && $.each(result.data, function (i, n) {
                        showDangerPoint(n);
                    });

                    $(that).hasClass('icon-dangerPoint') && $(that).removeClass('icon-dangerPoint').addClass('icon-dangerPointHover');
                }
            });
        });

        $('#btnRefresh').on('click', function () {
            if (terminals_ControlObj) {
                terminals_ControlObj.SearchKey = $.trim(termailsearchObj.find('.form-control').val()) || '';
                terminals_ControlObj.SearchType = termailsearchObj.find('select').val() || '';
                terminals_ControlObj.reload();
            }
        });
        //地图初始化===End
    }
    mapModel();
    //监控===Start
    var terminals_Control = function (options) {
        var _this = this;
        _this.Flag = 0;
        _this.SearchKey = '';
        _this.LastTime = null;
        _this.Map = 'AMap';
        _this.RoundTime_Monitor = 10000;
        _this.total = 0;
        _this.limit = 15;
        _this.start = 0;
        _this.page = 1;
        _this.tips_btn = false;
        _this.dn = options;
        _this.terminalObjs = {};
        _this.load = _this._getTableData;
        _this.init();
    };
    terminals_Control.prototype = {
        init: function () {
            var _this = this;
            if (_this.dn) {
                _this.load = function () {
                    $.updateSever({
                        'para': {
                            'DeviceNumber': _this.dn
                            , 'MapType': mapType
                        },
                        'url': URL + '/api/monitor/ShowMonitorDevice',
                        'success': function (result) {
                            if (result.Disabled) {
                                $('#Drive_Status').html('已过期');
                                if (_this.terminalObjs[_this.dn]) {
                                    _this.terminalObjs[_this.dn]._cancelPosition();
                                }
                            } else {
                                $('#Device_Status').html(_this.getStatusValue(result));
                                if (!_this.terminalObjs[_this.dn]) {
                                    $.showLoading();
                                    var addTerminal = function () {
                                        $.hideLoading();
                                        result.noDeleteCheck = true;
                                        result.autoSetCenter = true;
                                        _this.terminalObjs[_this.dn] = new terminal_Control(result);
                                    };
                                    addTerminal();
                                }
                            }
                        }
                    });
                };
            }
            _this._monitor();
        },
        _monitor: function () {
            var _this = this;
            if (!$.isEmptyObject(_this.terminalObjs)) {
                var deviceNumbers = [];
                $.each(_this.terminalObjs, function (i, n) {
                    deviceNumbers.push(i);
                });
                $.updateSever({
                    'para': {
                        // 'LastTime': $.dateToJson(_this.LastTime),
                        'DeviceNumbers': deviceNumbers,
                        // 'MapType': mapType
                    },
                    'url': URL + '/api/monitor/Refresh',
                    'success': function (result) {
                        _this.LastTime = $.formatJsDateTime(result.LastTime);
                        $.each(result.Data, function (i, n) {
                            var tObj = _this.terminalObjs[n.DeviceNumber];
                            if (tObj) {
                                setTimeout(function () {
                                    tObj.round_update(n);
                                }, 500);
                            }
                        });
                        setTimeout(function () {
                            _this._monitor();
                        }, _this.RoundTime_Monitor);
                    },
                    'error': function () {
                        setTimeout(function () {
                            _this._monitor();
                        }, _this.RoundTime_Monitor);
                    }
                });
            } else {
                setTimeout(function () {
                    _this._monitor();
                }, _this.RoundTime_Monitor);
            }
            _this.load();
        },
        reload: function () {
            var _this = this;
            _this.total = 0;
            _this.limit = 15;
            _this.start = 0;
            _this.page = 1;
            _this.load(true);
        },
        _getTableData: function (showLoading) {
            var _this = this;
            showLoading && $.showLoading();
            var setting = {
                'url': URL + '/api/monitor/ShowMonitorDeviceList',
                'success': function (data) {
                    if (data === null) {
                        $.alert({
                            title: '提示',
                            content: '查询无果!',
                        });
                        return;
                    }

                    var ulOut = $('ul', terminal_table_body);
                    $('[column_name="Aliase"]', ulOut).tooltip('destroy');
                    ulOut.empty();
                    var result = _this._loadFilter(data);
                    _this._loadTable(result);
                    _this._setpage();
                    $.hideLoading();
                },
                'error': function () {
                    $.hideLoading();
                }
            };
            setting.para = {
                'WithChildren': ($('#withChildren').length > 0 && $('#withChildren')[0].checked),
                'Key': $.trim(termailsearchObj.find('.form-control').val()) || '',//_this.SearchKey,
                'Type': _this.SearchType,
                'Flag': _this.Flag,
                'limit': _this.limit,
                'start': _this.start,
                'page': _this.page
            };
            group_Selected && (setting.para.OrgId = group_Selected);
            $.updateSever(setting);
        },
        _setpage: function () {
            var _this = this;
            if (_this.total > 0) {
                if (_this.page != 1) {
                    $('.up_page', page_Div).removeAttr('disabled');
                }
                if (_this.total > _this.limit * _this.page) {
                    $('.down_page', page_Div).removeAttr('disabled');
                }
            }
        },
        _loadFilter: function (data) {
            return data;
        },
        _cancelPosition: function (DeviceNumber) {
            var _this = this;
            if (!_this.terminalObjs[DeviceNumber] || _this.terminalObjs[DeviceNumber].isLoading) {
                return;
            }
            var tObj = _this.terminalObjs[DeviceNumber];
            var chk_obj = $('li[key="' + DeviceNumber + '"]', terminal_table_body).find('.postion_checkbox');
            (chk_obj.length > 0) && chk_obj.removeClass('checkbox_true_full').addClass('checkbox_false_full');
            tObj._cancelPosition();
            delete _this.terminalObjs[DeviceNumber];
        },
        _monitorPosition: function (n) {
            var _this = this;
            var chk_obj = $('li[key="' + n['DeviceNumber'] + '"]', terminal_table_body).find('.postion_checkbox');
            (chk_obj.length > 0) && chk_obj.addClass('checkbox_true_full').removeClass('checkbox_false_full');
            _this.terminalObjs[n['DeviceNumber']] = new terminal_Control(n);
        },

        _monitorCmds: function (terminalInfos) {
            var _this = this;
            $.showLoading();
            var deviceNumbers = [];
            var j = terminalInfos.length - 1;
            cmdInfos = [];//用之前 初始化
            $.each(terminalInfos, function (i, n) {
                deviceNumbers.push(n['DeviceNumber']);
                cmdInfos.push(n['DeviceNumber']);//设备号进数组
                var chk_obj = $('li[key="' + n['DeviceNumber'] + '"]', terminal_table_body).find('.postion_checkbox');
                (chk_obj.length > 0) && chk_obj.addClass('checkbox_true_full').removeClass('checkbox_false_full');
                n.hideWin = !(j == i);
                n.noSetCenter = !(j == i);
                _this.terminalObjs[n['DeviceNumber']] = new terminal_Control(n, true);
            });
            console.log(cmdInfos);
            console.log(deviceNumbers);

            $.hideLoading();
            top.$('#CmdModals').modal('show');


        },


        _monitorPositions: function (terminalInfos) {
            var _this = this;
            $.showLoading();
            var deviceNumbers = [];
            var j = terminalInfos.length - 1;
            $.each(terminalInfos, function (i, n) {
                deviceNumbers.push(n['DeviceNumber']);
                var chk_obj = $('li[key="' + n['DeviceNumber'] + '"]', terminal_table_body).find('.postion_checkbox');
                (chk_obj.length > 0) && chk_obj.addClass('checkbox_true_full').removeClass('checkbox_false_full');
                n.hideWin = !(j == i);
                n.noSetCenter = !(j == i);
                _this.terminalObjs[n['DeviceNumber']] = new terminal_Control(n, true);
            });

            $.updateSever({
                'para': {
                    'LastTime': null,
                    'DeviceNumbers': deviceNumbers
                    , 'MapType': mapType
                },
                'url': URL + '/api/monitor/Refresh',
                'success': function (result) {
                    $.each(result.Data, function (i, n) {
                        var tObj = _this.terminalObjs[n.DeviceNumber];
                        if (tObj) {
                            tObj.batchSeq = i;
                            tObj.round_update(n);
                        }
                    });
                    $.hideLoading();
                }
            });
        },


        _loadTable: function (data) {
            var _this = this;
            var curul = $('ul[index="' + _this.Flag + '"]', terminal_table_body);
            var key = curul.attr('key');
            _this.total = data.StatInfo[key];
            $('.btn', page_Div).attr('disabled', 'disabled');
            $.each(data.StatInfo, function (i, n) {
                $('.' + i + '_span', btns_Div).html(data.StatInfo[i]);
            });
            $.each(data.DeviceList, function (i, n) {
                var li_temp = $('<li></li>').css({
                    'list-style-type': 'none',
                    'height': '28px',
                    'width': '110%',
                    'float': 'left',
                    'cursor': 'pointer',
                    'margin': 0,
                    'padding': 0
                }).addClass('terminal_table_children').attr('key', n.DeviceNumber).appendTo(curul).on('click', function (e) {
                    var liObj = $(this);
                    var data = liObj.data('info');
                    if (data.OnlineStatus === "Unknown") {
                        $.alert({
                            title: '提示',
                            content: '设备' + data.Aliase + '未激活!',
                        });
                        return;
                    }

                    if (data.Disabled) {
                        $.alert({
                            title: '提示',
                            content: '设备' + data.Aliase + '已过期!',
                        });
                        return;
                    }

                    var terminalObj = terminals_ControlObj.terminalObjs[data.DeviceNumber];
                    if (terminalObj) {
                        if ($(e.target).hasClass('postion_checkbox')) {
                            terminals_ControlObj._cancelPosition(data.DeviceNumber);
                        } else {
                            terminalObj.setFitview();
                        }
                    } else {
                        terminals_ControlObj._monitorPosition(data);
                    }
                }).data('info', n);
                var div_temp = $('<div></div>').css({
                    'padding': '5px 0px 5px 0px',
                    'float': 'left',
                    'padding-left': '5px',
                    'padding-right': '5px',
                    'white-space': 'nowrap',
                    'overflow': 'hidden',
                    'text-overflow': 'ellipsis',
                    'width': '160px'
                }).attr('column_name', 'Aliase').attr('data-toggle', "tooltip").attr('data-html', true).attr('data-placement', "right")
                    .attr('title', '<div style="text-align: left;word-wrap:break-word;">设备号：' + n.DeviceNumber
                        + '</div><div style="text-align: left;word-wrap:break-word;">类&nbsp;&nbsp;&nbsp;&nbsp;型：' + (n.ModelType == 0 ? '有线' : '无线')
                        + '</div><div style="text-align: left;word-wrap:break-word;">组&nbsp;&nbsp;&nbsp;&nbsp;名：' + n.OrgName
                        + '</div>').appendTo(li_temp);
                var checkboxClass = 'checkbox_false_full';
                var terminalObj = terminals_ControlObj.terminalObjs[n['DeviceNumber']];
                if (terminalObj) {
                    checkboxClass = 'checkbox_true_full';
                    if (n['Disabled']) {
                        terminalObj._cancelPosition();
                        checkboxClass = 'checkbox_false_disable';
                    } else {
                        terminalObj.changeIcon(n.Online);
                    }
                } else if (n['Disabled']) {
                    checkboxClass = 'checkbox_false_disable';
                }
                var checkboxObj = $('<span class="postion_checkbox ' + checkboxClass + '" ></span>').appendTo(div_temp);
                $('<span></span>').addClass((n.Online ? 'icon-online' : 'icon-offline')).css({
                    'width': '16px',
                    'margin-bottom': '-3px',
                    'display': 'inline-block',
                    'height': '16px'
                }).addClass('status_icon').appendTo(div_temp);
                $('<span>' + n.Aliase + '</span>').appendTo(div_temp);
                //$('<div>' +n.DeviceNumber + '</div>').css({
                //    'padding': '5px 0px 5px 0px',
                //    'float': 'left',
                //    'padding-left': '5px',
                //    'padding-right': '5px',
                //    'width': '100px'
                //}).attr('column_name', 'DeviceNumber').appendTo(li_temp);
                var status = _this.getStatusValue(n);
                $('<div>' + status + '</div>').css({
                    'padding': '5px 0px 5px 0px',
                    'float': 'left',
                    'padding-left': '5px',
                    'padding-right': '5px',
                    'width': 'auto'
                }).attr('column_name', 'LastUpdateTime').appendTo(li_temp);

                if (terminalObj)
                    if (n.Attention !== terminalObj.isAttention) {
                        terminalObj.isAttention = n.Attention;
                        cars[n['DeviceNumber']].tipsContent = terminalObj._getContent();

                        mapFrame.updateMarker(n['DeviceNumber'], cars[n['DeviceNumber']]);
                    }
            });
        },
        getStatusValue: function (n) {
            var status = n.OnlineStatus || 'Unknown';
            if (status.indexOf(':') > -1) {
                var l = status.split(':');
                var temp_off = "在线";
                if (l[0] == "Stop") {
                    temp_off = "停留";
                    l[1] != "0" && (temp_off = temp_off + l[1] + '天');
                    l[2] != "0" && (temp_off = temp_off + l[2] + '时');
                    l[3] != "0" && (temp_off = temp_off + l[3] + '分');
                    //l[4] && (temp_off = temp_off + l[4]+ '秒');
                } else if (l[0] == "OnLine") {
                    temp_off = "在线";
                    l[1] != "0" && (temp_off = temp_off + l[1] + '天');
                    l[2] != "0" && (temp_off = temp_off + l[2] + '时');
                    l[3] != "0" && (temp_off = temp_off + l[3] + '分');
                    //l[4] && (temp_off = temp_off + l[4]+ '秒');
                } else if (l[0] == "OffLine") {
                    temp_off = "离线";
                    l[1] != "0" && (temp_off = temp_off + l[1] + '天');
                    l[2] != "0" && (temp_off = temp_off + l[2] + '时');
                    l[3] != "0" && (temp_off = temp_off + l[3] + '分');
                }
                status = temp_off;
            } else {
                var dic = {
                    'Unknown': '未激活'
                };
                status = dic[status] || status;
            }
            return status;
        }
    };
    var terminal_Control = function (option, noGetPosition) {
        var _this = this;
        _this.DeviceNumber = option.DeviceNumber;
        _this.ModelName = option.ModelName;
        _this.ModelType = option.ModelType;
        _this.DeviceName = option.DeviceName;
        _this.Aliase = option.Aliase;
        _this.IMEI = option.IMEI;
        _this.Simcard = option.Simcard;
        _this.ICCID = option.ICCID;
        _this.inMap = false;
        _this.isOnline = option.Online;
        _this.isAttention = option.Attention;
        _this.LastPosition = null;
        _this.LastTime = null;
        _this.isLoading = !noGetPosition;
        _this.autoSetCenter = option.autoSetCenter;
        _this.noDeleteCheck = option.noDeleteCheck;
        _this.hideWin = option.hideWin;
        _this.noSetCenter = option.noSetCenter;
        _this.Address = option.Address;
        _this._updates = [];
        _this.init();
    }
    terminal_Control.prototype = {
        init: function () {
            var _this = this;
            _this._updates.push(_this._updatePosition);
            _this._updates.push(_this._updateTerminalTable);
            _this.isLoading && _this._update(function (data) {
                _this.round_update(data);
            });
        },
        changeIcon: function (isOnline) {
            var _this = this;
            if (_this.isOnline !== isOnline) {
                _this.isOnline = isOnline;
                var url = '/Content/map/icons/Map_' + (_this.isOnline ? 'Online' : 'Offline') + '.png';
                var temp_status = $('[key="' + _this.DeviceNumber + '"]', terminal_table_body).find('.status_icon');
                if (temp_status) {
                    temp_status.removeClass('icon-online').removeClass('icon-offline');
                    _this.isOnline ? temp_status.addClass('icon-online') : temp_status.addClass('icon-offline');
                }
            }
        },
        changeAttention: function (isAttention) {
            var _this = this;
            if (_this.isAttention !== isAttention) {
                _this.isAttention = isAttention;
                cars[_this.DeviceNumber].tipsContent = _this._getContent();
                mapFrame.updateMarker(_this.DeviceNumber, cars[_this.DeviceNumber]);
            }
        },

        _getContent: function () {
            var _this = this;
            var info = [];
            info.push("<div><h3 style='padding-bottom:10px;font-size:18px;width:232px;text-overflow:ellipsis;white-space:nowrap;overflow:hidden' title='" + _this.Aliase + "'><font color=\"#00a6ac\">  " + _this.Aliase + "</font></h3></div> ");
            info.push('<div style="width:295px;padding-top:10px;border-top:2px solid #3c8dbc;" class="table_map" id="' + _this.DeviceNumber + '_InfoWindows">');
            info.push("<div style='margin-right:10px;width:182px'><span style='text-align:right;width:70px;'>设备编号&nbsp;:&nbsp;</span><span>" + _this.DeviceNumber + "</span></div>");
            info.push("<div><span style='text-align:right;width:70px;'>SIM卡号&nbsp;:&nbsp;</span><span>" + _this.ICCID + " </span></div>");
            info.push("<div style='margin-right:10px;width:182px'><span style='text-align:right;width:70px;'>定位方式&nbsp;:&nbsp;</span><span>" + ((_this.LastPosition.LocationType == "1") ? '北斗' : '基站') + "</span></div>");
            // info.push("<div><span style='text-align:right;width:70px;'>安装位置&nbsp;:&nbsp;</span><span>前部</span></div>");
            info.push("<div style='margin-right:10px;width:182px'><span style='text-align:right;width:70px;'>定位时间&nbsp;:&nbsp;</span><span>" + $.toDateTime(_this.LastPosition.LocationTime) + "</span></div>");
            info.push("<div><span style='text-align:right;width:70px;'>信号时间&nbsp;:&nbsp;</span><span>" + $.toDateTime(_this.LastPosition.ReceiveTime) + "</span></div>");

            info.push("<div class='clearfix'><span style='width:70px;float:left'>设备状态&nbsp;:&nbsp;</span><span style='float:left;width:225px'>" + (_this.LastPosition.Online ? '在线' : '离线') + statusModel.getStatusValue(_this.LastPosition.Status, _this.LastPosition.Alarm) + "</span></div>");
            _this.LastPosition.OuterVoltage && info.push("<div class='clearfix'><span style='float:left;width:70px;'>设备参数&nbsp;:&nbsp;</span><span style='float:left;width:225px'>" + (_this.LastPosition.OuterVoltage != null ? '输入电压' + _this.LastPosition.OuterVoltage + 'V;' : '') + '里程' + _this.LastPosition.Mileage + 'km;' + "</span></div>");
            //info.push("<tr><td style='text-align:right;width:70px;'>速&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度&nbsp:&nbsp</td><td>" + (_this.LastPosition.Speed ? (_this.LastPosition.Speed + 'km/h') : '0km/h(停留' + _this.LastPosition.StayTime + ')') + "</td></tr>");
            _this.ModelType && info.push(_this._getmode(['power', 'getWifi']));

            info.push("<div style='padding-bottom:10px;display:block;'class='clearfix'><span style='width:70px;float:left;'>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址&nbsp;:&nbsp;</span><span class='info_Address' style='float:left;width:225px'>" + _this.Address + "</span></div>");

            info.push("<div id='d_inforwidown'><div style='text-align:left;' onmouseleave='$(\".d_qt\").hide();'>");
            //info.push('<a href="javascript:void(0)" class="show_detailsbtns">详情</a>')
            flds['CheckAlarms'] = 1;
            flds['DeviceDetails'] = 1;
            flds['highLines'] = 1;
            flds['qt'] = 1;
            flds['StopStatus'] = 1;
            // flds['InstallSite'] = 1;
            flds['AttentionSet'] = 1;
            info.push('<div class="btn-group">');
            var d = [
                {
                    'id': 'Trace',
                    'value': '<span style="padding-right:10px" key="' + _this.DeviceNumber + '" onclick="top.follow(\'' + _this.DeviceNumber + '\')" class="btn btn-default btn-sm follow_btn">实时跟踪</span>'
                },
                {
                    'id': 'Playback',
                    'value': '<span style="padding-right:10px"  key="' + _this.DeviceNumber + '" onclick="top.playback(\'' + _this.DeviceNumber + '\')" class="btn btn-default btn-sm playback_btn">轨迹回放</span>'
                },
                {
                    'id': 'CmdSend',
                    'value': '<span style="padding-right:10px" key="' + _this.DeviceNumber + '" onclick="top.$(\'#CmdModal\').modal(\'show\');" class="btn btn-default btn-sm cmd_btn">指令下发</span>'
                },
                {
                    'id': 'highLines',
                    'value': '<span style="padding-right:10px" name="' + _this.Aliase + '" key="' + _this.DeviceNumber + '" onclick="top.showLocationDetail(\'' + _this.DeviceNumber + '\',\'' + _this.Aliase + '\')" class="btn btn-default btn-sm highLines_btn">定位详情</span>'
                },
                {
                    'id': 'qt',
                    'value': '<span name="' + _this.Aliase + '" key="' + _this.DeviceNumber + '" onmouseenter="$(\'.d_qt\').show();" class="btn btn-default btn-sm qt_btn"  >更多</span>'
                }
            ];
            flds && $.each(d, function (i, n) {
                flds[n['id']] && info.push(n['value']);
            });
            info.push('</div>');
            info.push('<div class="d_qt">');
            var d = [
                {
                    'id': 'CheckAlarms',
                    'value': '<span style="width:48px" key="' + _this.DeviceNumber + '" onclick="top.showAlarm(\'' + _this.DeviceNumber + '\')" class="btn btn-default btn-sm checkAlarms_btn ">查看报警</span>'
                },
                {
                    'id': 'CmdRecord',
                    'value': '<span style="width:48px" key="' + _this.DeviceNumber + '" onclick="top.$(\'#CmdRecordModal\').modal(\'show\');" class="btn btn-default btn-sm cmdRecord_btn">指令记录</span>'
                },
                {
                    'id': 'DeviceDetails',
                    'value': '<span type="button" key="' + _this.DeviceNumber + '" onclick="top.$(\'#DeviceDetails\').modal(\'show\');" class="btn btn-default btn-sm deviceDetails_btn">设备详情</span>'
                },
                {
                    'id': 'StopStatus',
                    'value': '<span type="button" key="' + _this.DeviceNumber + '" onclick="top.$(\'#StopStatus\').modal(\'show\');" class="btn btn-default btn-sm stopStatus_btn">停留详情</span>'
                },
                // {
                //     'id': 'InstallSite',
                //     'value': '<span style="button" key="' + _this.DeviceNumber + '" onclick="top.$(\'#InstallSite\').modal(\'show\');" class="btn btn-default btn-sm installSite_btn">安装位置</span>'
                // },
                {
                    'id': 'AttentionSet',
                    'value': '<span type="button" key="' + _this.DeviceNumber + '" onclick="top.AttentionSet(\'' + _this.DeviceNumber + '\',this)" class="btn btn-default btn-sm attentionSet_btn">' + (_this.isAttention ? '取消关注' : '关注') + '</span>'
                },
            ]
            flds && $.each(d, function (i, n) {
                flds[n['id']] && info.push(n['value']);
            })
            info.push('</div>');
            info.push("</div></div>");
            info.push("</div>");

            return info.join('');
        },
        _getWifi: function () {
            var _this = this;
            var wifiPoto = "";
            var w = [10, 20, 40, 60, 80, 100];
            for (var i = 0; i < w.length - 1; i++) {
                if (_this.LastPosition.Signal > w[i] && _this.LastPosition.Signal <= w[i + 1]) {
                    wifiPoto = "/Content/img/power/wifi" + (i + 1) + ".jpg";
                    break;
                }
                ;
            }
            ;
            if (wifiPoto == "") {
                return "";
            }
            return '<div class="singl" style="position:absolute;top:12px;right:25px" title="信号为' + _this.LastPosition.Signal + '%"><img style="width:25px;height:15px" src=' + wifiPoto + ' alt=""></div>';

        },
        _getPower: function () {
            var _this = this;
            var pow = '';
            var f = [0, 10, 25, 45, 65, 90, 101];
            for (var i = 0; i < f.length - 1; i++) {
                if (_this.LastPosition.Electricity >= f[i] && _this.LastPosition.Electricity < f[i + 1]) {
                    pow = "/Content/img/power/power" + (6 - i) + ".png";
                    break;
                }
                ;
            }
            ;
            if (pow == "") {
                return "";
            }
            return '<div class="electric" style="position:absolute;top:12px;right:48px" title="电量还剩余' + _this.LastPosition.Electricity + '%"><img style="height:12px;margin-right:10px" src=' + pow + ' ></div>';
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
            })
            return l;
        },
        _updatePosition: function () {
            var _this = this;
            var position = {lat: _this.LastPosition.Lat, lng: _this.LastPosition.Lng};
            var id = _this.DeviceNumber;
            var icon = '/Content/map/icons/Map_' + (_this.LastPosition.Online ? 'Online' : 'Offline') + '.png';
            var isAdd = typeof cars[_this.DeviceNumber] == 'undefined';

            cars[_this.DeviceNumber] = {
                icon: icon,
                text: _this.Aliase,
                position: position,
                tipsContent: _this._getContent()
            };

            mapFrame.updateMarker(_this.DeviceNumber, cars[_this.DeviceNumber]);

            if (isAdd == true && (typeof _this.batchSeq == 'undefined' || _this.batchSeq == 0)) {
                mapFrame.setCenter(_this.DeviceNumber, cars[_this.DeviceNumber]);
            }
            _this.inMap = true;
        },
        _updateTerminalTable: function () {
            var _this = this;
        },
        _cancelPosition: function () {
            var _this = this;
            if (cars[_this.DeviceNumber])
                delete cars[_this.DeviceNumber];
            mapFrame.removeMarker(_this.DeviceNumber);
        },
        setFitview: function () {
            var _this = this;
            if (cars[_this.DeviceNumber]) {
                mapFrame.setCenter(_this.DeviceNumber, cars[_this.DeviceNumber]);
            }
        },
        _update: function (callback) {
            var _this = this;
            $.updateSever({
                'para': {
                    'DeviceNumber': _this.DeviceNumber
                    , 'MapType': mapType
                },
                'url': URL + '/api/monitor/ShowMonitorTips',
                'success': function (result) {
                    _this.isLoading = false;
                    if (result) {
                        callback && callback(result);
                    }
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
                $.each(_this._updates, function (i, n) {
                    n.call(_this);
                });
            }
        }
    }
    terminals_ControlObj = new terminals_Control(dn);
    var hide = function () {
        $('.d_qt').hide();
    }

    var flg = true;
    //map_div.delegate('.qt_btn', 'mouseenter', function () {
    //    $('.d_qt').show();
    //});

    //map_div.delegate('#d_inforwidown>div', 'mouseleave', function () {
    //    $('.d_qt').hide();
    //});

    //监控===End
    var Cmdinfo = {
        ztree: null,
        _BP11: null,
        _key: null,
        _BP11_check: function (validator, obj) {
            var flag = false;
            if (Cmdinfo._BP11) {
                return Cmdinfo._BP11.isOk;
            }
            var f = [];
            for (var i = 1; i < 4; i++) {
                f.push($('[datafld="PhoneNumber' + i + '"]').val());
            }
            if (f[0] || f[1] || f[2]) {
                flag = true;
            }
            Cmdinfo._BP11 = {
                isOk: flag
            };
            var name = obj.attr('name');
            for (var i = 1; i < 4; i++) {
                if (name != ('PhoneNumber' + i + '_lab')) {
                    validator.updateStatus(("PhoneNumber" + i + "_lab"), "NOT_VALIDATED", 'callback');
                    validator.validateField(('PhoneNumber' + i + '_lab'));
                }
            }
            Cmdinfo._BP11 = null;
            return flag;
        },
        _8103_F000: function (da) {
            var textObj = CmdinfoObj.find('[datafld="ModeValue"]');
            textObj.val('').attr('placeholder', da.placeholder);
            var validator = CmdinfoObj.find('.JsonData').data('bootstrapValidator');
            validator.updateMessage('ModeValue_lab', 'notEmpty', da.notEmptyMsg);
            validator.enableFieldValidators('ModeValue_lab', (!!da.digits), 'digits');
            validator.enableFieldValidators('ModeValue_lab', (!!da.between), 'between');
            validator.enableFieldValidators('ModeValue_lab', (!!da.lessThan), 'lessThan');
            validator.enableFieldValidators('ModeValue_lab', (!!da.regexp), 'regexp');

            da.regexp && validator.updateOption('ModeValue_lab', 'regexp', 'regexp', da.regexpOption);
            validator.updateStatus(("ModeValue_lab"), "NOT_VALIDATED", null);
        },


        dictionary: {
            "BP02": [{//1
                'datafld': 'Defence', 'type': 'radio', 'data': [{
                    'id': 0, 'text': '设防'
                }, {
                    'id': 1, 'text': '撤防'
                }]
            }, {
                'type': 'html',
                'body': '<div style="font-size:12px;">1、下发设防成功后,设备达到报警条件,会自动产生报警上报到监控平台;<br>2、撤防成功后,相关触发报警自动关闭;</div>'
            }],

            "BP06": [{//2
                'datafld': 'Speed',
                'type': 'text',
                'placeholder': '限制速度,取值范围0-999',
                'validators': {
                    'notEmpty': {
                        'message': '限制速度不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    },
                    'between': {
                        'message': '取值范围0-999',
                        'min': 0,
                        'max': 999
                    }
                }
            }, {'type': 'html', 'body': '<div style="font-size:12px;">行驶最高速度超过设置的速度时，设备自动上报超速报警到平台；</div>'}],

            "BP07": [{//3
                'type': 'label', 'text': '定时监控间隔'
            }, {
                'datafld': 'Minutes', 'type': 'select',
                'data': [
                    {'id': '30', 'text': '30分钟'}
                    , {'id': '60', 'text': '1小时'}
                    , {'id': '120', 'text': '2小时'}
                    , {'id': '180', 'text': '3小时'}
                    , {'id': '240', 'text': '4小时'}
                    , {'id': '300', 'text': '5小时'}
                    , {'id': '360', 'text': '6小时'}
                    , {'id': '420', 'text': '7小时'}
                    , {'id': '480', 'text': '8小时'}
                    , {'id': '540', 'text': '9小时'}
                    , {'id': '600', 'text': '10小时'}
                    , {'id': '660', 'text': '11小时'}
                    , {'id': '720', 'text': '12小时'}
                    , {'id': '780', 'text': '13小时'}
                    , {'id': '840', 'text': '14小时'}
                    , {'id': '900', 'text': '15小时'}
                    , {'id': '960', 'text': '16小时'}
                    , {'id': '1020', 'text': '17小时'}
                    , {'id': '1080', 'text': '18小时'}
                    , {'id': '1140', 'text': '19小时'}
                    , {'id': '1200', 'text': '20小时'}
                    , {'id': '1260', 'text': '21小时'}
                    , {'id': '1320', 'text': '22小时'}
                    , {'id': '1380', 'text': '23小时'}
                    , {'id': '1440', 'text': '24小時'}
                ]
            }, {
                'type': 'html',
                'body': '<div style="font-size:12px;">1、按照设置的间隔，终端唤醒上报；<br>2、缩短上报时间间隔虽然会提高定位频率，但会影响电池寿命；</div>'
            }],


            "BP61": [{//4
                'type': 'label', 'text': '设备收到下发指令后将重启'
            }, {'type': 'html', 'body': '<div style="font-size:12px;">重启后，主机会重新与平台重新连接，发送数据；</div>'}],

            "BP62": [{//5
                'type': 'label', 'text': '设备收到下发指令后将恢复出厂设置'
            }, {
                'type': 'html',
                'body': '<div style="font-size:12px;">1、恢复出厂设置后，主机自动清除远程控制指令，恢复到出厂时的默认设置。<br>2、如果设置了报警短信接收号码，则同时清除</div>'
            }],

            "BP64": [{//6
                'type': 'label', 'text': '域名格式:www.*****.com/cn'
            }, {
                'datafld': 'Domain', 'type': 'text',
                'placeholder': '域名',
                'validators': {
                    'notEmpty': {
                        'message': '域名不能为空'
                    }
                }
            }, {
                'datafld': 'Port', 'type': 'text',
                'placeholder': '端口',
                'validators': {
                    'notEmpty': {
                        'message': '端口不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    }
                }
            }],

            "BP72": [{//7
                'type': 'label', 'text': '设置的时间间隔(0-60分钟),表示静止达到该时间后.车辆进入设防状态(单位:分钟),为0时.则取消定时设防'
            }, {
                'datafld': 'Interval',
                'type': 'text',
                'placeholder': '自动设防间隔',
                'validators': {
                    'notEmpty': {
                        'message': '时间间隔不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    },
                    'between': {
                        'message': '取值范围0-60',
                        'min': 0,
                        'max': 60
                    }
                }
            }],

            "BP74": [{//8
                'datafld': 'Duration',
                'type': 'text',
                'placeholder': '持续时间,单位:秒,取值范围0-999',
                'validators': {
                    'notEmpty': {
                        'message': '持续时间不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    },
                    'between': {
                        'message': '取值范围0-999',
                        'min': 0,
                        'max': 999
                    }
                }
            }, {
                'datafld': 'Speed',
                'type': 'text',
                'placeholder': '超速速度,单位:公里/小时,取值范围0-999',
                'validators': {
                    'notEmpty': {
                        'message': '超速速度不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    },
                    'between': {
                        'message': '取值范围0-999',
                        'min': 0,
                        'max': 999
                    }
                }
            }],


            "BP84": [{//9
                'type': 'label', 'text': '设置工作模式'
            }
                , {
                    'datafld': 'Flag', 'type': 'select',
                    'data': [
                        {'id': '01', 'text': '设置正常模式'},
                        {'id': '02', 'text': '连续定位'},
                        {'id': '03', 'text': '智能监控'},
                        {'id': '00', 'text': '不设置工作模式'}
                    ]
                }, {
                    'type': 'html',
                    'body': '<div style="font-size:12px;">1、非特殊情况，请不要设置工作模式，设置后，耗电量会增大；<br>2、连续定位模式，设备连续上报30分钟后自动关闭，关闭后返回上次工作模式；<br>3、智能监控模式，设备运动按照15秒间隔上报，静止自动休眠；</div>'
                }
            ],

            "BP88": [{//10
                'type': 'label', 'text': '格式:HHmmss.如:05时20分1秒,052001'
            }, {
                'datafld': 'TimeStamp', 'type': 'text',
                'placeholder': '设置数据上传时间点',
                'validators': {
                    'notEmpty': {
                        'message': '数据上传时间点不能为空'
                    },
                    'regexp': {
                        'message': '格式不正确',
                        'regexp': '^(([0-1][0-9])|(2[0-3]))([0-5][0-9]){2}$'
                        // 'regexp': '^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$'
                    }
                },
                'isf': true
            }],

            "BP78": [{
                'type': 'label', 'text': '格式:1372356****'
            },
                {//11
                    'datafld': 'PhoneNumber', 'type': 'text',
                    'placeholder': '主控号码',
                    'validators': {
                        'notEmpty': {
                            'message': '主控号码不能为空'
                        },
                        'regexp': {
                            'message': '格式不正确',
                            'regexp': '^1[3456789]\\d{9}$'
                            // 'regexp': '^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$'
                        }
                    }
                }],

            "BP85": [{//12
                'datafld': 'Flag',
                'type': 'radio', 'data': [{
                    'id': 0, 'text': '关闭'
                }, {
                    'id': 1, 'text': '打开'
                }]
            }, {'type': 'html', 'body': '<div style="font-size:12px;">防拆开关设置关闭后，但设备被拆掉后，将无法产生报警信息；</div>'}],


        },


        formatSendPara: function (key, deviceNumbers) {
            console.log(key, deviceNumbers);
            var _this = this;
            if (!Cmdinfo.dictionary[key]) {
                return false;
            }
            var outDiv = $('<div></div>').css({
                'padding': '10px 20px 10px 20px'
            }).addClass('JsonData').appendTo(_this);
            var setting = Cmdinfo.dictionary[key];
            $('<input />').attr('datafld', 'CmdKey').val(key).hide().appendTo(outDiv);
            $('<input />').attr('datafld', 'DeviceNumber').val(deviceNumbers).hide().appendTo(outDiv);
            $('<input />').attr('datafld', 'cmdInfos').val(deviceNumbers).hide().appendTo(outDiv);
            var fields = {};
            $.each(setting, function (i, n) {
                if (n.validators) {
                    fields[n.datafld + '_lab'] = {
                        validators: {}
                    };
                    $.extend(true, fields[n.datafld + '_lab']['validators'], n.validators);
                    n.name = n.datafld + '_lab';
                }
                var group_Div = $('<div></div>').addClass('form-group').appendTo(outDiv);
                getObjFromCategory[n.type].call(group_Div, n);
            });

            !$.isEmptyObject(fields) && outDiv.bootstrapValidator({
                message: 'This value is not valid',
                feedbackIcons: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: fields,
                excluded: [":disabled"]
            });
            return true;
        }
    };

    //群发 下发指令页面
    var CmdinfoObjs = $('body').addModel({
        id: 'CmdModals',
        width: '700px',
        height: '400px',
        text: '下发'
    }).on('hide.bs.modal', function (obj) {
        CmdinfoObjs.find('.sendBtn').hide();
        CmdinfoObjs.find('.sendbody').empty();
    }).on('show.bs.modal', function (obj) {
        // var btnobj = mapFrame.$('.cmd_btn');
        // var _terminal_ControlObj = terminals_ControlObj.terminalObjs[btnobj.attr('key')];
        // CmdinfoObj.find('.modal-title').html(_terminal_ControlObj['Aliase'] + '下发');//
        // Cmdinfo._key = btnobj.attr('key');
    }).on('shown.bs.modal', function (obj) {
        if (CmdinfoObjs.find('.ztree').length > 0) {
            Cmdinfo.ztree.setting.async.otherParam = ['DeviceNumber', Cmdinfo._key];
            Cmdinfo.ztree.reAsyncChildNodes(null, "refresh");
            return;
        }
        CmdinfoObjs.find('.modal-body').css({
            'height': '320px'
        });

        var ztreeDiv = $('<div></div>').css({
            'height': '300px',
            'float': 'left',
            'box-shadow': '0 1px 1px rgba(0,0,0,0.1)',
            'border': '1px solid #d2d6de',
            'padding': '5px',
            'width': '300px'
        }).appendTo(CmdinfoObjs.find('.modal-body'));

        var cmd_ztree = $('<ul></ul>')
            .addClass('ztree')
            .attr('id', 'cmd_ztree')
            .css({
                'overflow-y': 'auto',
                'overflow-x': 'auto',
                'border-radius': '3px',
                'height': '100%',
                'background-color': 'white'
            })
            .appendTo(ztreeDiv);

        setTimeout(function () {
            var setting = {
                async: {
                    enable: true,
                    contentType: "application/json",
                    dataType: 'json',
                    type: 'post',
                    // otherParam: ['DeviceNumber', Cmdinfo._key],//修改IMIE
                    url: URL + '/api/monitor/ShowCmdListByDevice',
                    dataFilter: function (treeId, parentNode, response) {
                        if (response.Status == 0) {
                            $.alert({
                                title: '提示',
                                content: response.ErrorMessage,
                            });
                            return [];
                        }

                        var responseData = response.Result;
                        var result = [];
                        if (responseData.length > 0) {
                            $.each(responseData, function (i, n) {
                                var da = {
                                    pid: '',
                                    iconSkin: 'default'
                                };
                                $.extend(da, n);
                                result.push(da);
                            });
                        } else {
                            CmdinfoObjs.modal('hide');
                            $.alert({
                                title: '提示',
                                content: '该设备没有可下发命令',
                            });
                        }
                        return result;
                    }
                },
                view: {
                    dblClickExpand: false,
                    showLine: true,
                    selectedMulti: false
                },
                data: {
                    key: {
                        name: 'CmdName',
                        title: ''
                    },
                    simpleData: {
                        enable: true,
                        idKey: 'CmdKey',
                        pIdKey: 'pid',
                    }
                },
                callback: {
                    beforeClick: function (treeId, treeNode, clickFlag) {
                        return !treeNode.isParent;
                    },
                    onClick: function (event, treeId, treeNode) {
                        CmdinfoObjs.find('.sendbody').empty();
                        if (Cmdinfo.formatSendPara.call(CmdinfoObjs.find('.sendbody'), treeNode.CmdKey, cmdInfos)) {
                            CmdinfoObjs.find('.sendBtn').show();
                        } else {
                            CmdinfoObjs.find('.sendbody').html("该命令没有配置");
                            CmdinfoObjs.find('.sendBtn').hide();
                        }
                    },
                    onAsyncSuccess: function (event, treeId, treeNode, msg) {
                        Cmdinfo.ztree.expandAll(true);
                    }
                }
            };
            Cmdinfo.ztree = $.fn.zTree.init(cmd_ztree, setting, []);
        }, 100);

        var sends_div = $('<div></div>').css({
            'height': '300px',
            'padding': '5px',
            'margin-left': '10px',
            'float': 'left',
            'width': 'calc( 100% - 310px )',
            'border-radius': '3px',
            'background-color': 'white',
            'box-shadow': '0 1px 1px rgba(0,0,0,0.1)',
            'border': '1px solid #d2d6de'
        }).appendTo(CmdinfoObjs.find('.modal-body'));
        $('<div></div>').addClass('sendbody').appendTo(sends_div);
        $('<button type="button" class="btn sbtn-default">下发</button>')
            .css({'display': 'none'})
            .addClass('sendBtn')
            .bind('click', function () {
                var validObj = CmdinfoObjs.find('.JsonData').data('bootstrapValidator');
                if (validObj) {
                    validObj.validate();
                    if (!validObj.isValid()) {
                        return;
                    }
                }

                $.updateSever({
                    'para': {
                        'sendbody': CmdinfoObjs.find('.sendbody').toJson(),
                        'cmdInfos': cmdInfos
                    },
                    'url': URL + '/api/monitor/Send',
                    'beforeSend': function () {
                        // $.alert({
                        //     title: '提示',
                        //     content: '下发中...',
                        // });
                    },
                    'success': function (result) {
                        $.alert({
                            title: '提示',
                            content: '下发成功',
                        });
                    }
                });
            })
            .appendTo($('<div></div>').css({
                'position': 'absolute',
                'right': '20px',
                'bottom': '10px'
            })
                .appendTo(sends_div));
    });


    //下发指令页面
    var CmdinfoObj = $('body').addModel({
        id: 'CmdModal',
        width: '700px',
        height: '400px',
        text: '下发'
    }).on('hide.bs.modal', function (obj) {
        CmdinfoObj.find('.sendBtn').hide();
        CmdinfoObj.find('.sendbody').empty();
    }).on('show.bs.modal', function (obj) {
        var btnobj = mapFrame.$('.cmd_btn');
        var _terminal_ControlObj = terminals_ControlObj.terminalObjs[btnobj.attr('key')];
        CmdinfoObj.find('.modal-title').html(_terminal_ControlObj['Aliase'] + '下发');//
        Cmdinfo._key = btnobj.attr('key');
        cmdInfos = [];
        cmdInfos.push(Cmdinfo._key);//新加代码
    }).on('shown.bs.modal', function (obj) {
        if (CmdinfoObj.find('.ztree').length > 0) {
            Cmdinfo.ztree.setting.async.otherParam = ['DeviceNumber', Cmdinfo._key];
            Cmdinfo.ztree.reAsyncChildNodes(null, "refresh");
            return;
        }
        CmdinfoObj.find('.modal-body').css({
            'height': '320px'
        });

        var ztreeDiv = $('<div></div>').css({
            'height': '300px',
            'float': 'left',
            'box-shadow': '0 1px 1px rgba(0,0,0,0.1)',
            'border': '1px solid #d2d6de',
            'padding': '5px',
            'width': '300px'
        }).appendTo(CmdinfoObj.find('.modal-body'));

        var cmd_ztree = $('<ul></ul>')
            .addClass('ztree')
            .attr('id', 'cmd_ztree')
            .css({
                'overflow-y': 'auto',
                'overflow-x': 'auto',
                'border-radius': '3px',
                'height': '100%',
                'background-color': 'white'
            })
            .appendTo(ztreeDiv);

        setTimeout(function () {
            var setting = {
                async: {
                    enable: true,
                    contentType: "application/json",
                    dataType: 'json',
                    type: 'post',
                    otherParam: ['DeviceNumber', Cmdinfo._key],//修改IMIE
                    url: URL + '/api/monitor/ShowCmdListByDevice',
                    dataFilter: function (treeId, parentNode, response) {

                        if (response.Status == 0) {
                            $.alert({
                                title: '提示',
                                content: response.ErrorMessage,
                            });
                            return [];
                        }

                        var responseData = response.Result;

                        var result = [];
                        if (responseData.length > 0) {
                            $.each(responseData, function (i, n) {
                                var da = {
                                    pid: '',
                                    iconSkin: 'default'
                                };
                                $.extend(da, n);
                                result.push(da);
                            });
                        } else {
                            CmdinfoObj.modal('hide');
                            $.alert({
                                title: '提示',
                                content: '该设备没有可下发命令',
                            });
                        }
                        return result;
                    }
                },
                view: {
                    dblClickExpand: false,
                    showLine: true,
                    selectedMulti: false
                },
                data: {
                    key: {
                        name: 'CmdName',
                        title: ''
                    },
                    simpleData: {
                        enable: true,
                        idKey: 'CmdKey',
                        pIdKey: 'pid',
                    }
                },
                callback: {
                    beforeClick: function (treeId, treeNode, clickFlag) {
                        return !treeNode.isParent;
                    },
                    onClick: function (event, treeId, treeNode) {
                        CmdinfoObj.find('.sendbody').empty();
                        if (Cmdinfo.formatSendPara.call(CmdinfoObj.find('.sendbody'), treeNode.CmdKey, Cmdinfo._key)) {
                            CmdinfoObj.find('.sendBtn').show();
                        } else {
                            CmdinfoObj.find('.sendbody').html("该命令没有配置");
                            CmdinfoObj.find('.sendBtn').hide();
                        }
                    },
                    onAsyncSuccess: function (event, treeId, treeNode, msg) {
                        Cmdinfo.ztree.expandAll(true);
                    }
                }
            };
            Cmdinfo.ztree = $.fn.zTree.init(cmd_ztree, setting, []);
        }, 100);

        var send_div = $('<div></div>').css({
            'height': '300px',
            'padding': '5px',
            'margin-left': '10px',
            'float': 'left',
            'width': 'calc( 100% - 310px )',
            'border-radius': '3px',
            'background-color': 'white',
            'box-shadow': '0 1px 1px rgba(0,0,0,0.1)',
            'border': '1px solid #d2d6de'
        }).appendTo(CmdinfoObj.find('.modal-body'));
        $('<div></div>').addClass('sendbody').appendTo(send_div);
        $('<button type="button" class="btn btn-default">下发</button>')
            .css({'display': 'none'})
            .addClass('sendBtn')
            .bind('click', function () {
                var validObj = CmdinfoObj.find('.JsonData').data('bootstrapValidator');
                if (validObj) {
                    validObj.validate();
                    if (!validObj.isValid()) {
                        return;
                    }
                }
                $.updateSever({
                    'para': {
                        "sendbody": CmdinfoObj.find('.sendbody').toJson(),
                        "cmdInfos": cmdInfos
                    },
                    'url': URL + '/api/monitor/Send',
                    'success': function (result) {
                        $.alert({
                            title: '提示',
                            content: '下发成功',
                        });
                    }
                });
            })
            .appendTo($('<div></div>').css({
                'position': 'absolute',
                'right': '20px',
                'bottom': '10px'
            })
                .appendTo(send_div));
    });


    var CmdRecordInfo = {
        table: null,
        _key: null
    };

    var CmdRecordInfoObj = $('body').addModel({
        id: 'CmdRecordModal',
        width: '960px',
        height: '600px',
        text: '指令记录',
        nofooter: true,
        body: '<div id="CmdRecord_table"></div>'
    }).on('show.bs.modal', function (obj) {
        var btnobj = mapFrame.$('.cmdRecord_btn');
        var _terminal_ControlObj = terminals_ControlObj.terminalObjs[btnobj.attr('key')];
        CmdRecordInfoObj.find('.modal-title').html(_terminal_ControlObj['Aliase'] + '指令记录');
        CmdRecordInfo._key = btnobj.attr('key');
    }).on('shown.bs.modal', function (obj) {
        if (CmdRecordInfo.table) {
            CmdRecordInfo.table._dataTable.ajax.reload();
            return;
        }
        CmdRecordInfo.table = new dtc({
            pid: 'CmdRecord_table',
            columns: ['CmdName', 'Operator', 'Message', 'LastSendTime', 'ResponseTime', 'CmdStatusText', 'ResultText'],
            name: 'monitor',
            handleBefore: function (data) {
                data.DeviceNumber = CmdRecordInfo._key;
            },
            url: URL + '/api/monitor/ShowCmdRecord',
            parameters: {
                'CmdName': {
                    'title': '指令名',
                    'columnWidth': 100
                },
                'Operator': {
                    'title': '操作人',
                    'columnWidth': 40
                },
                'Message': {
                    'title': '内容',
                    'columnWidth': 120
                }
                ,
                'LastSendTime': {
                    'title': '发送时间',
                    'columnWidth': 100,
                    'tableRender': function (data, type, full, meta, obj) {
                        return $.toDateTime(data);
                    }
                },
                'ResponseTime': {
                    'title': '响应时间',
                    'columnWidth': 100,
                    'tableRender': function (data, type, full, meta, obj) {
                        return $.toDateTime(data);
                    }
                },
                'CmdStatusText': {
                    'title': '指令状态',
                    'columnWidth': 50
                },
                'ResultText': {
                    'title': '结果',
                    'columnWidth': 170,
                }
            }
        }, {
            "rowId": 'CmdName',
            "lengthChange": false,
            'nofooter': true
        });
    });

    var treeObj;
    var DeviceDetailsObj = $('body').addModel({
        id: 'DeviceDetails',
        width: '400px',
        height: '600px',
        text: '设备详情',
        nofooter: false,
        footer: flds['edit'] ? '<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button><button type="submit" class="btn btn-primary">确定</button>' : '',
        body: '<div id="Details_Div"></div>'
    }).on('hide.bs.modal', function (obj) {
        $('*[datafld]', DeviceDetailsObj).each(function (i, n) {
            $(n).html('');
        });

        $('input[name="Simcard"]').val("");
        $('input[name="Name"]').val("");

        DeviceDetailsObj.data('bootstrapValidator').destroy();
        DeviceDetailsObj.data('bootstrapValidator', null);

        $('#Details_Div a:first').tab('show')

    }).on('shown.bs.modal', function (obj) {
        var _body = $('#Details_Div', DeviceDetailsObj);
        var formatDatas = [
            {
                datafld: 'ExpireDate', format: function (d) {
                    return $.toDate(d);
                }
            },
            {
                datafld: 'MFDate', format: function (d) {
                    return $.toDate(d);
                }
            }];
        if (!_body.hasClass('isFormat')) {
            var data = [
                {title: 'DeviceId', datafld: 'DeviceId'},
                {title: '所属组', datafld: 'OrgName'},
                {title: '设备号', datafld: 'DeviceNumber'},
                {title: '设备名称', datafld: 'Name'},
                {title: '设备型号', datafld: 'ModelName'},
                {title: '设备类型', datafld: 'ModelTypeText'},
                {title: 'Sim卡号', datafld: 'Simcard'}

                //{ title: '车牌号', datafld: 'PlateNo' },
                //{ title: '车主姓名', datafld: 'Owner' },
                //{ title: '联系方式', datafld: 'Phone' },
                //{ title: '车架号', datafld: 'VIN' },
                //{ title: '车辆颜色', datafld: 'CarColor' },
                //{ title: '车辆类型', datafld: 'CarType' },
                //{ title: '安装位置', datafld: 'location' },
                //{ title: '激活时间', datafld: 'time' },
                //{ title: '到期日期', datafld: 'ExpireDate' },
                //{ title: '出厂日期', datafld: 'MFDate' },
                //{ title: '备注', datafld: 'Remark' }
            ];

            var data1 = [
                {title: '车牌号', datafld: 'PlateNo'},
                {title: '车主姓名', datafld: 'Owner'},
                {title: '联系方式', datafld: 'Phone'},
                {title: '车架号', datafld: 'VIN'},
                {title: '车辆颜色', datafld: 'CarColor'},
                {title: '车辆类型', datafld: 'CarType'}
            ];

            var intoDiv = [];

            // <li><a href="#carInfo" data-toggle="tab">车辆信息</a></li >
            intoDiv.push('<ul class="nav nav-tabs"><li class= "active"><a href="#deviceInfo" data-toggle="tab">设备信息</a></li></ul>');
            intoDiv.push('<div class="tab-content"><div class="tab-pane fade in active" id="deviceInfo">');

            $.each(data, function (i, n) {

                if (n.datafld == 'OrgName' && flds['edit']) {
                    intoDiv.push('<div class="form-group margin">');
                    intoDiv.push('<label class="col-sm-3 control-label">' + n.title + '</label>');
                    intoDiv.push('<div class="col-sm-9" id="modelTree">');
                    intoDiv.push('</div>');
                    intoDiv.push('</div>');
                } else if ((n.datafld == 'Name' || n.datafld == 'Simcard') && flds['edit']) {
                    intoDiv.push('<div class="form-group margin">');
                    intoDiv.push('<label class="col-sm-3 control-label">' + n.title + '</label>');
                    intoDiv.push('<div class="col-sm-9">');
                    intoDiv.push('<input type="text" name="' + n.datafld + '" datafld="' + n.datafld + '" class="form-control" />');
                    intoDiv.push('</div>');
                    intoDiv.push('</div>');
                } else if (n.datafld == 'DeviceId') {
                    intoDiv.push('<input type="hidden" id="DeviceId" />');
                } else {
                    intoDiv.push('<div class="form-group margin">');
                    intoDiv.push('<label class="col-sm-3 control-label">' + n.title + '</label>');
                    intoDiv.push('<div class="col-sm-9">');
                    intoDiv.push('<span datafld="' + n.datafld + '" class="form-control">');
                    intoDiv.push('</span>');
                    intoDiv.push('</div>');
                    intoDiv.push('</div>');
                }
            });

            intoDiv.push('</div><div class="tab-pane fade" id="carInfo">');

            $.each(data1, function (i, n) {

                if (n.datafld == 'CarType' && flds['edit']) {
                    intoDiv.push('<div class="form-group margin">');
                    intoDiv.push('<label class="col-sm-3 control-label">' + n.title + '</label>');
                    intoDiv.push('<div class="col-sm-9">');
                    intoDiv.push('<select class="form-control">');
                    intoDiv.push('<option value="">请选择</option>');
                    intoDiv.push('<option value="轿车">轿车</option>');
                    intoDiv.push('<option value="商务车">商务车</option>');
                    intoDiv.push('<option value="大巴">大巴</option>');
                    intoDiv.push('<option value="小巴">小巴</option>');
                    intoDiv.push('<option value="大客车">大客车</option>');
                    intoDiv.push('<option value="大货车">大货车</option>');
                    intoDiv.push('<option value="小货车">小货车</option>');
                    intoDiv.push('<option value="农用车">农用车</option>');
                    intoDiv.push('<option value="工程车">工程车</option>');
                    intoDiv.push('</select>');
                    intoDiv.push('</div>');
                    intoDiv.push('</div>');
                } else if (flds['edit']) {
                    intoDiv.push('<div class="form-group margin">');
                    intoDiv.push('<label class="col-sm-3 control-label">' + n.title + '</label>');
                    intoDiv.push('<div class="col-sm-9">');
                    intoDiv.push('<input type="text" name="' + n.datafld + '" datafld="' + n.datafld + '" class="form-control">');
                    intoDiv.push('</span>');
                    intoDiv.push('</div>');
                    intoDiv.push('</div>');
                } else {
                    intoDiv.push('<div class="form-group margin">');
                    intoDiv.push('<label class="col-sm-3 control-label">' + n.title + '</label>');
                    intoDiv.push('<div class="col-sm-9">');
                    intoDiv.push('<span datafld="' + n.datafld + '" class="form-control">');
                    intoDiv.push('</span>');
                    intoDiv.push('</div>');
                    intoDiv.push('</div>');
                }
            });

            intoDiv.push('</div></div>');


            //$.each(data, function (i, n) {
            //    intoDiv.push('<div class="form-group">');
            //    intoDiv.push('<label class="col-sm-3 control-label">' + n.title + '</label>');
            //    intoDiv.push('<div class="col-sm-9">');
            //    intoDiv.push('<span datafld="' + n.datafld + '" class="form-control">');
            //    intoDiv.push('</span>');
            //    intoDiv.push('</div>');
            //    intoDiv.push('</div>');
            //});
            _body.html(intoDiv.join(''));
            _body.addClass('isFormat');

            DeviceDetailsObj.find('.btn-primary').unbind().click(function () {
                //var valid = DeviceDetailsObj.data("bootstrapValidator");
                //valid.validate();

                //if (valid.isValid()) {

                var model = {};
                model.Id = $('#DeviceId').val();
                model.OrgnizationId = treeObj.getValue();
                model.Name = $('input[name="Name"]').val();
                model.Simcard = $('input[name="Simcard"]').val();
                model.PlateNo = $('input[name="PlateNo"]').val();
                model.Owner = $('input[name="Owner"]').val();
                model.Phone = $('input[name="Phone"]').val();
                model.VIN = $('input[name="VIN"]').val();
                model.CarColor = $('input[name="CarColor"]').val();
                model.CarType = DeviceDetailsObj.find('select').val();

                $.updateSever({
                    'para': model,
                    'url': 'http://127.0.01:51662/api/Device/UpdateDeviceVehicle',
                    'success': function (result) {
                        if (result)
                            $.alert({
                                title: '提示',
                                content: '修改成功!',
                                onClose: function () {
                                    DeviceDetailsObj.find('button.btn-primary').removeAttr("disabled");
                                    DeviceDetailsObj.modal('hide');
                                }
                            });
                    }
                });
                //}
            });
        }

        DeviceDetailsObj.bootstrapValidator({
            message: 'This value is not valid',
            submitButtons: 'button[type="submit"]',
            feedbackIcons: {/*输入框不同状态，显示图片的样式*/
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {/*验证*/
                PlateNo: {/*键名username和input name值对应*/
                    message: '车牌号不可用',
                    validators: {
                        //threshold: 2, //有6字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
                        //remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                        //    url: 'http://60.2.213.22:51662/api/Vehicle/CheckPlateNoOnly',//验证地址
                        //    data: { userId: $('input[name="PlateNo"]').val() },
                        //    message: '车牌号已存在',//提示消息
                        //    delay: 1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        //    type: 'POST'//请求方式
                        //},
                        //可以在callback里面写上自定义的一些校验规则
                        callback: {
                            message: '车牌号已存在于其他组',
                            callback: function (value, validator) {
                                if (value == "") {
                                    $('input[name="Owner"]').val("").attr("disabled", "disabled");
                                    $('input[name="Phone"]').val("").attr("disabled", "disabled");

                                    return true;
                                }

                                $('input[name="Owner"]').removeAttr("disabled");
                                $('input[name="Phone"]').removeAttr("disabled");

                                var flag = false;
                                $.updateSever({
                                    'async': false,
                                    'para': {OrgId: treeObj.getValue(), PlateNo: $('input[name="PlateNo"]').val()},
                                    'url': URL + '/api/Vehicle/NewCheckPlateNoOnly',
                                    'success': function (result) {
                                        //console.log(JSON.stringify(result));
                                        if (result.valid) {
                                            if (result.model != null) {
                                                $('input[name="Owner"]').val(result.model.Owner);
                                                $('input[name="Phone"]').val(result.model.Phone);
                                                $('input[name="VIN"]').val(result.model.VIN);
                                                $('input[name="CarColor"]').val(result.model.CarColor);
                                                DeviceDetailsObj.find('select').val(result.model.CarType);
                                            }
                                        }
                                        flag = result.valid;
                                    }
                                });

                                return flag;
                            }
                        }
                    }
                },
                VIN: {
                    validators: {
                        message: '车架号不可用',
                        stringLength: {
                            min: 17,
                            max: 17,
                            message: '请输入17位车架号'
                        }
                    }
                }
            }
        });

        var btnobj = mapFrame.$('.deviceDetails_btn');
        var _terminal_ControlObj = terminals_ControlObj.terminalObjs[btnobj.attr('key')];
        DeviceDetailsObj.find('.modal-title').html(_terminal_ControlObj['Aliase'] + '设备详情');
        $.updateSever({
            'para': {deviceNumber: btnobj.attr('key')},
            'url': URL + '/api/Device/ShowDeviceDetails',
            'success': function (result) {
                //console.log(JSON.stringify(result));
                $.each(formatDatas, function (i, n) {
                    result[n.datafld] && (result[n.datafld] = n.format(result[n.datafld]));
                });
                DeviceDetailsObj.toBind(result);

                DeviceDetailsObj.find('select').val(result['CarType']);
                $('#DeviceId').val(result['Id']);

                if (!treeObj)
                    treeObj = new selectGroupZTree({
                        datafld: 'Id',
                        showWidth: '100%',
                        placeholder: 'placeholder',
                        isWithChildren: false,
                        defaultValue: result['OrgnizationId']
                    }, $('#modelTree'));
                else
                    treeObj.setValue(result['OrgnizationId']);
            }
        });
    });
    var StopStatusInfo = {
        table: null,
        _key: null
    };
    var StopStatusInfoObj = $('body').addModel({
        id: 'StopStatus',
        width: '960px',
        height: '600px',
        text: '停留详情',
        nofooter: true,
        body: '<div id="StopStatus_table"></div>'
    }).on('show.bs.modal', function (obj) {
        var btnobj = mapFrame.$('.stopStatus_btn');
        var _terminal_ControlObj = terminals_ControlObj.terminalObjs[btnobj.attr('key')];
        StopStatusInfoObj.find('.modal-title').html(_terminal_ControlObj['Aliase'] + '停留详情');
        StopStatusInfo._key = btnobj.attr('key');
    }).on('shown.bs.modal', function (obj) {
        if (StopStatusInfo.table) {
            StopStatusInfoObj.find('[datafld="Interval"]').val('');
            StopStatusInfoObj.find('#rangeDiv').hide();
            StopStatusInfo.table._dataTable.ajax.reload();
            return;
        }
        StopStatusInfo.table = new dtc({
            pid: 'StopStatus_table',
            columns: ['OrgName', 'DeviceName', 'DeviceNumber', 'StartTime', 'EndTime', 'IntervalText', 'LngLat'],
            name: 'report',
            noDownLoad: true,
            showAddress: true,
            handleBefore: function (data) {
                data.DeviceNumber = StopStatusInfo._key;
            },
            url: URL + '/api/report/ShowStopListByDeviceNumber',
            parameters: {
                'DeviceNumber': {
                    'title': '设备号',
                    'columnWidth': 110,
                    'downloadWidth': 40
                },
                'DeviceName': {
                    'title': '设备名',
                    'columnWidth': 110,
                    'downloadWidth': 40
                },
                'OrgName': {
                    'title': '所属组',
                    'columnWidth': 100,
                    'downloadWidth': 42
                },
                'StartTime': {
                    'title': '开始停留时间',
                    'columnWidth': 110,
                    'downloadWidth': 30,
                    'tableRender': function (data, type, full, meta, obj) {
                        return $.toDateTime(data);
                    }
                },
                'EndTime': {
                    'title': '结束停留时间',
                    'columnWidth': 110,
                    'downloadWidth': 30,
                    'tableRender': function (data, type, full, meta, obj) {
                        return $.toDateTime(data);
                    }
                },
                'IntervalText': {
                    'title': '停留时长',
                    'columnWidth': 80,
                    'downloadWidth': 30
                },
                'LngLat': {
                    'title': '地址',
                    'columnWidth': 500,
                    'tableRender': function (data, type, full, meta, obj) {
                        return obj.getAddressByLngLat(data);
                    }
                }
            },
            searchs: [
                {
                    'category': 'select',
                    'datafld': 'Interval',
                    'handler': function (id, text) {
                        var _this = this;
                        var textobj = $('#rangeDiv', _this);
                        textobj.val(id);
                        text == '自定义' ? textobj.show() : textobj.hide();
                    },
                    'data': [
                        {'id': '1440', 'text': '停留时长'},
                        {'id': '1440', 'text': '1天以上'},
                        {'id': '2880', 'text': '2天以上'},
                        {'id': '10080', 'text': '7天以上'},
                        {'id': '-1', 'text': '自定义'}
                    ]
                }, {
                    'category': 'rangeNumber',
                    'datafld': 'Interval',
                    'id': 'rangeDiv',
                    'unit': '分钟',
                    'hidden': true
                }
            ]
        }, {});
    });
    var InstallSiteObj = $('body').addModel({
        id: 'InstallSite',
        width: '600px',
        height: '300px',
        text: '安装位置',
        nofooter: false,
        footer: flds['edit'] ? '<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button><button type="submit" class="btn btn-primary">确定</button>' : '',
        body: '<div id="Details_Div"></div>'
    }).on('hide.bs.modal', function (obj) {
        InstallSiteObj.find('input[name="ins-radio"]').removeAttr('checked');
        InstallSiteObj.find("input[name='ins-radio']").removeAttr('disabled')
    }).on('shown.bs.modal', function (obj) {
        var _body = $('#Details_Div', InstallSiteObj);

        if (!_body.hasClass('isFormat')) {

            var intoDiv = [];

            intoDiv.push('<table style="width:50%;border:none;float:left;"><tbody><tr><td width="22%"><input style="margin:30px 0 0;" type="radio" name="ins-radio" value="1">电瓶</td><td width="22%"><input style="margin:30px 0 0;" type="radio" name="ins-radio" value="2">副驾驶杂物箱</td></tr>');
            intoDiv.push('<tr><td><input style="margin:30px 0 0;" type="radio" name="ins-radio" value="3">左侧踏板</td><td><input style="margin:30px 0 0;" type="radio" name="ins-radio" value="4">右侧踏板</td></tr>');
            intoDiv.push('<tr><td><input style="margin:30px 0 0;" type="radio" name="ins-radio" value="5">方向盘下面</td><td><input style="margin:30px 0 0;" type="radio" name="ins-radio" value="6">前顶灯</td></tr>');
            intoDiv.push('<tr><td><input style="margin:30px 0 0;" type="radio" name="ins-radio" value="7">后顶灯</td><td><input style="margin:30px 0 0;" type="radio" name="ins-radio" value="8">后备箱</td></tr>');
            intoDiv.push('<tr><td><input style="margin:30px 0 0;" type="radio" name="ins-radio" value="9">其他</td></tr>');
            intoDiv.push('<tr><td colspan="2"><textarea style="margin:5px 0 0; width:90%;" name="PositionReMark" autofocus="autofocus"></textarea></td></tr>');
            intoDiv.push('</tbody></table>');
            intoDiv.push('<div style="display: block;width:50%;overflow:hidden;position:relative;"><img style="height:300px;" src="../../Content/img/installed.png"></div>');

            _body.html(intoDiv.join(''));
            _body.addClass('isFormat');

            InstallSiteObj.find('.btn-primary').unbind().click(function () {
                var btnobj = mapFrame.$('.installSite_btn');
                var model = {};
                model.DeviceNumber = btnobj.attr('key');
                model.InstallPosition = InstallSiteObj.find('input[name="ins-radio"]:checked').val();
                model.InstallPositionRemark = InstallSiteObj.find('textarea[name="PositionReMark"]').val();

                $.updateSever({
                    'para': model,
                    'url': URL + '/api/Device/UpdateDevicePosition',
                    'success': function (result) {
                        if (result)
                            $.alert({
                                title: '提示',
                                content: '修改成功!',
                                onClose: function () {
                                    InstallSiteObj.find('button.btn-primary').removeAttr("disabled");
                                    InstallSiteObj.modal('hide');
                                }
                            });
                    }
                });
            });

            InstallSiteObj.find("input[name='ins-radio']").unbind().click(function () {
                if ($(this).val() == 9 && flds['edit'])
                    InstallSiteObj.find("textarea[name='PositionReMark']").removeAttr('disabled');
                else {
                    InstallSiteObj.find("textarea[name='PositionReMark']").attr('disabled', 'disabled');
                    InstallSiteObj.find("textarea[name='PositionReMark']").val('');
                }
            });
        }

        var btnobj = mapFrame.$('.installSite_btn');
        var _terminal_ControlObj = terminals_ControlObj.terminalObjs[btnobj.attr('key')];
        InstallSiteObj.find('.modal-title').html(_terminal_ControlObj['Aliase'] + '安装位置');
        $.updateSever({
            async: false,
            'para': {deviceNumber: btnobj.attr('key')},
            'url': URL + '/api/Device/ShowDevicePosition',
            'success': function (result) {
                if (result && result.InstallPosition != 0) {
                    $.each(InstallSiteObj.find("input[name='ins-radio']"), function () {
                        if ($(this).val() == result.InstallPosition.toString()) {
                            $(this).click();
                        }
                    });
                    InstallSiteObj.find("textarea[name='PositionReMark']").val(result.InstallPositionRemark == null ? "" : result.InstallPositionRemark);
                    //if (result.InstallPosition != 9)
                    //    InstallSiteObj.find("textarea[name='PositionReMark']").attr('disabled', 'disabled');
                }
            }
        });
        flds['edit'] ? InstallSiteObj.find("input[name='ins-radio']").removeAttr('disabled') : InstallSiteObj.find("input[name='ins-radio']").attr('disabled', 'disabled');
        //flds['edit'] && !InstallSiteObj.find("textarea[name='PositionReMark']").prop('disabled') ? InstallSiteObj.find("textarea[name='PositionReMark']").removeAttr('disabled') : InstallSiteObj.find("textarea[name='PositionReMark']").attr('disabled', 'disabled');
    });
    $('#marker').appendTo($('body'));

    $('.d_line_marker span').click(function () {
        $('.d_line_marker').hide();
    });

    $.cookie('mapType') && $('#mapSelect').val($.cookie('mapType')).change();
});

function playback(deviceNumber) {
    var _this = terminals_ControlObj.terminalObjs[deviceNumber];
    //todo 修改
    window.open("/playback?key=" + deviceNumber + '&name=' + escape(_this['Aliase']) + '&mt=' + _this['ModelType']);
}

function follow(deviceNumber) {
    var selfObj = $(this);
    var _this = terminals_ControlObj.terminalObjs[deviceNumber];
    //todo 修改
    var url = "/follow?key=" + _this['DeviceNumber'] + '&name=' + escape(_this['Aliase']) + '&mt=' + _this['ModelType'] + '&mn=' + _this['ModelName'] + '&dn=' + escape(_this['DeviceName']);
    if (_this['isOnline']) {
        url = url + '&io=' + 1;
    }
    window.open(url);
}

function AttentionSet(deviceNumber, obj) {
    var o = terminals_ControlObj.terminalObjs[deviceNumber];

    var willAttention = o.isAttention ? false : true;

    $.updateSever({
        'para': {DeviceNumber: deviceNumber, IsAttention: willAttention},
        'url': URL + '/api/Monitor/UpdateAttention',
        'success': function (result) {
            if (result)
                if (o.isAttention) {
                    $(obj).html("关注");
                    o.changeAttention(false);
                } else {
                    $(obj).html("取消关注");
                    o.changeAttention(true);
                }
        }
    });
}

function showAlarm(deviceNumber) {
    var o = terminals_ControlObj.terminalObjs[deviceNumber];
    if (alarmInfo) {
        alarmInfo.deviceNumber = deviceNumber;
        alarmInfo.title = o['Aliase'];
        $('#alarmModal').modal('show');
    }
}

function showLocationDetail(deviceNumber, name) {
    $('.d_line_marker').show();
    $('.d_line_marker ul').html('');
    $('.d_line_marker h2').html(name + ' —定位详情')
    $.updateSever({
        'para': {
            DeviceNumber: deviceNumber,
            Start: $.shortDateToJson($.getNextMonth(-1)),
            End: $.shortDateToJson($.getNextMonth(0))
        },
        'url': URL + '/api/Device/OnlineDetail',
        'success': function (result) {
            var liHtml = "";
            $.each(result, function (i, n) {
                if (n.IsOnline)
                    liHtml += '<li>' + $.toDate(n.OnlineDate) + '<br /><img src="/Content/img/v.jpg" /></li>';
                else
                    liHtml += '<li>' + $.toDate(n.OnlineDate) + '<br /><img src="/Content/img/x.jpg" /></li>';
            });
            $('.d_line_marker ul').html(liHtml);
        }
    });
    $('.d_qt').hide();
}

