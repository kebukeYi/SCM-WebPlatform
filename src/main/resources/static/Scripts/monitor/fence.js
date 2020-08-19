$(function () {

    /*
    全局 接口访问地址 IP ：port
    */
    var URL = GlobalConfig.IPSSAddress;

    //地图的右上角展示什么
    var rightToolBar = {
        'SaveView': {},
        'SearchAddress': {},
        'Move': {},
        'DistanceMeasurement': {},//InterestPoints,Route,DragRoute,Rectangle,Circle,Polygon
        'RectZoomIn': {},
        'RectZoomOut': {},
        'Rectangle': {},//围栏矩形
        // 'Polygon':{},//围栏多边形
        'Circle': {},//围栏圆形
        // 'SelectAdminArea':{}
    };

    var fenceBind = {
        table: {},
        alarmType_dictionary: [
            {'id': '1', 'text': '进围栏'},
            {'id': '2', 'text': '出围栏'},
            {'id': '3', 'text': '进出围栏'}
        ],
        FenceId: null,
        FenceName: null
    };

    if (flds['add']) {
        rightToolBar['Circle'] = {};
        rightToolBar['Rectangle'] = {};
        rightToolBar['Polygon'] = {};
        rightToolBar['SelectAdminArea'] = {
            'handler': function (text, value) {
                if (value) {
                    fenceModalObj.find('[datafld="FenceData"]').val(value);
                    fenceModalObj.find('[datafld="FenceType"]').val(0);
                    fenceModalObj.find('[datafld="FenceName"]').val(text);
                    fenceModalObj.off('hidden.bs.modal');
                    fenceModalObj.off('show.bs.modal');
                    fenceModalObj.on('hidden.bs.modal', function () {
                        fenceModalObj.find('[datafld="FenceName"]').val('');
                        fenceModalObj.bootstrapValidator('resetForm', true);
                    });
                    fenceModalObj.modal();
                } else {
                    $.alert({
                        title: '提示',
                        content: '请选择行政区域'
                    });
                }
            }
        }
    } else {
        rightToolBar['SelectAdminArea'] = {};
    }

    var mapObj = new MapControl('mmap', {
        plugins: {
            "ToolBar": {},
            "OverView": {},
            "Scale": {},
            "RightToolBar": rightToolBar,
            "MapType": {}
        }
    });

    //新增围栏
    var fenceModalObj = $('body').addModel({
        id: 'fenceModal',
        width: '300px',
        height: '300px',
        text: '新增围栏',
        body: '<input type="hidden" class="form-control" name="FenceData" datafld="FenceData" />' +
            '<input type="hidden" value="1" name="FenceType" datafld="FenceType" />' +
            '<div class="form-group">' +
            '<div class="col-sm-12">' +
            '<input type="text" class="form-control" placeholder="围栏名称" name="fenceName" datafld="FenceName" />' +
            '</div></div>',
        footer: '<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>' +
            '<button type="button" class="btn btn-default btnAddFence">确定</button>'
    }).bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            fenceName: {
                validators: {
                    notEmpty: {
                        message: '围栏名称不能为空'
                    }
                }
            }
        },
        excluded: [":disabled"]
    }).delegate('.btnAddFence', 'click', function (a, b, c) {
        fenceModalObj.data('bootstrapValidator').validate();
        if (!fenceModalObj.data('bootstrapValidator').isValid()) {
            return;
        }
        var d = fenceModalObj.toJson();

        $.updateSever({
            'para': d,
            'url': URL + '/api/fence/Add',
            'success': function (result) {
                fenceModalObj.modal('hide');
                if (result === true) {
                    dt._load();//重载
                    $.alert({
                        title: '提示',
                        content: '添加"' + d.FenceName + '"围栏成功!'
                    });
                } else {
                    $.alert({
                        title: '提示',
                        content: '添加围栏失败!',
                    });
                }
            }
        });
    });

    //关联设备
    var fenceBindObj = $('body').addModel({
        id: 'fenceBindModal',
        width: '1000px',
        height: '700px',
        text: '关联设备',
        nofooter: true,
        body: '<div id="fenceBind_table"></div>'
    }).on('shown.bs.modal', function (obj) {
        var btnobj = $(obj.relatedTarget);
        var id = $(".checkchild:checked", dt.tableObj).val();//围栏id
        console.log(id);
        var rowData = dt._dataTable.row('#' + id).data();
        fenceBind.FenceId = rowData.Id;
        fenceBind.FenceName = rowData.FenceName;
        fenceBindObj.find('.modal-title').html('关联设备 - ' + fenceBind.FenceName);
        if (fenceBind.table['Device']) {
            $('[datafld]', fenceBind.table['Device'].searchDiv).each(function (i, n) {
                $(n).data('reSet') && $(n).data('reSet')();
            });
            $('.checkall', fenceBindObj).length > 0 && ($('.checkall', fenceBindObj).prop("checked", false));
            fenceBind.table['Device']._dataTable.ajax.reload();
            return;
        }
        fenceBind.table['Device'] = new dtc({
            pid: 'fenceBind_table',
            columns: ['DeviceNumber', 'DeviceName', 'OrgName', 'FenceId', 'AlarmTypeText', 'FenceAlarmTypeText', 'BindTime'],
            name: 'fence',
            validate: true,
            checkbox_data: "DeviceNumber",//传送后台的设备号码
            handleBefore: function (data) {
                data.FenceId = fenceBind.FenceId;
            },
            url: URL + '/api/fence/DeviceFenceList',
            parameters: {
                'OrgName': {
                    'title': '所属组名',
                },
                'DeviceName': {
                    'title': '设备名',
                },
                'DeviceNumber': {
                    'title': '设备号',
                },
                'AlarmTypeText': {
                    'title': '设备报警类型',
                },
                'BindTime': {
                    'title': '绑定时间',
                },
                'FenceAlarmTypeText': {
                    'title': '围栏报警类型',
                    'type': 'select',
                    'data': fenceBind.alarmType_dictionary
                }
            },
            buttons: [{
                'text': '关联',
                'id': 'bindFence',
                'data': ['FenceAlarmTypeText'],
                'button_Obj': {
                    'isMultiple': true,
                    'handle': function (n, data, closeModel) {
                        var _this = this;
                        var ids = [];
                        $(".checkchild:checked", _this.tableObj).each(function (i, n) {
                            ids.push($(n).val());
                        });
                        data.Ids = ids;
                        data.FenceId = fenceBind.FenceId;
                        $.updateSever({
                            'para': data,
                            'url': URL + '/api/fence/BindFence',
                            'success': function (data) {
                                if (data === true) {
                                    closeModel();
                                    _this._load();
                                } else {
                                    $.alert({
                                        title: '提示',
                                        content: n['text'] + '失败!'
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                'text': '取消关联',
                'id': 'unbindFence',
                'model_body': '确定要取消选中设备与当前围栏的关联？',
                'button_Obj': {
                    'isMultiple': true,//允许多选
                    'handle': function (n, data, closeModel) {
                        var _this = this;
                        var ids = [];
                        $(".checkchild:checked", _this.tableObj).each(function (i, n) {
                            ids.push($(n).val());
                        });
                        data.Ids = ids;
                        data.FenceId = fenceBind.FenceId;
                        $.updateSever({
                            'para': data,
                            'url': URL + '/api/fence/UnBindFence',
                            'success': function (data) {
                                if (data === true) {
                                    closeModel();
                                    _this._load();
                                } else {
                                    $.alert({
                                        title: '提示',
                                        content: n['text'] + '失败!'
                                    });
                                }
                            }
                        });
                    }
                }
            }],
            searchs: [
                {
                    'category': 'treeSelect',
                    'isWithChildren': true,
                    'isNoEmpty': true,
                    'datafld': 'OrgId'
                },
                {'category': 'text', 'placeholder': '设备名称或号码'},
                {
                    'category': 'select',
                    'datafld': 'IsBind',
                    'data': [
                        {'id': '0', 'text': '所有'},
                        {'id': '1', 'text': '已关联'},
                        {'id': '2', 'text': '未关联'}
                    ]
                }
            ]
        }, {
            "rowId": 'DeviceId',
            "scrollY": "400px",
            "scrollCollapse": true,
            "lengthChange": false,
            'nofooter': true
        });
    });

    //关联组
    var fenceBindGroupObj = $('body').addModel({
        id: 'fenceBindGroupModal',
        width: '1000px',
        height: '700px',
        text: '关联组',
        nofooter: true,
        body: '<div id="fenceBindGroup_table"></div>'
    }).on('shown.bs.modal', function (obj) {
        var btnobj = $(obj.relatedTarget);
        var id = $(".checkchild:checked", dt.tableObj).val();
        var rowData = dt._dataTable.row('#' + id).data();
        fenceBind.FenceId = rowData.Id;
        fenceBind.FenceName = rowData.FenceName;
        fenceBindGroupObj.find('.modal-title').html('关联组--' + fenceBind.FenceName);

        if (fenceBind.table['Group']) {
            $('[datafld]', fenceBind.table['Group'].searchDiv).each(function (i, n) {
                $(n).data('reSet') && $(n).data('reSet')();
            });
            $('.checkall', fenceBindGroupObj).length > 0 && ($('.checkall', fenceBindGroupObj).prop("checked", false));
            fenceBind.table['Group']._dataTable.ajax.reload();
            return;
        }

        fenceBind.table['Group'] = new dtc({
            pid: 'fenceBindGroup_table',
            //TODO  设置围栏报警状态
            columns: ['ParentName', 'OrgName', 'OrgId', 'FenceId', 'FenceAlarmTypeText', 'BindTime'],
            name: 'fence',
            validate: true,
            checkbox_data: "OrgId",
            handleBefore: function (data) {
                data.FenceId = fenceBind.FenceId;
            },
            url: URL + '/api/fence/GroupFenceList',
            parameters: {
                'ParentName': {
                    'title': '所属组名',
                    'columnWidth': 130
                },
                'OrgName': {
                    'title': '组名',
                    'columnWidth': 160
                },
                'OrgId': {
                    'title': '组号',
                    'columnWidth': 180
                },
                'BindTime': {
                    'title': '绑定时间',
                    'columnWidth': 140
                },
                'FenceAlarmTypeText': {
                    'title': '围栏报警类型',
                    'type': 'select',
                    'data': fenceBind.alarmType_dictionary,
                    'columnWidth': 130
                }
            },
            buttons: [{
                'text': '关联',
                'id': 'BindGroupFence',
                'data': ['FenceAlarmTypeText'],
                'button_Obj': {
                    'isMultiple': true,
                    'handle': function (n, data, closeModel) {
                        var _this = this;
                        var ids = [];
                        $(".checkchild:checked", _this.tableObj).each(function (i, n) {
                            ids.push($(n).val());
                        });
                        data.Ids = ids;
                        data.FenceId = fenceBind.FenceId;
                        $.updateSever({
                            'para': data,
                            'url': URL + '/api/fence/BindFenceByGroup',
                            'success': function (data) {
                                if (data === true) {
                                    closeModel();
                                    _this._load();
                                } else {
                                    $.alert({
                                        title: '提示',
                                        content: n['text'] + '失败!'
                                    });
                                }
                            }
                        });
                    }
                }
            }, {
                'text': '取消关联',
                'id': 'UnBindGroupFence',
                'model_body': '确定要取消选中组与当前围栏的关联？',
                'button_Obj': {
                    'isMultiple': true,
                    'handle': function (n, data, closeModel) {
                        var _this = this;
                        var ids = [];
                        $(".checkchild:checked", _this.tableObj).each(function (i, n) {
                            ids.push($(n).val());
                        });
                        data.Ids = ids;
                        data.FenceId = fenceBind.FenceId;
                        $.updateSever({
                            'para': data,
                            'url': URL + '/api/fence/UnBindFenceByGroup',
                            'success': function (data) {
                                if (data === true) {
                                    closeModel();
                                    _this._load();
                                } else {
                                    $.alert({
                                        title: '提示',
                                        content: n['text'] + '失败!'
                                    });
                                }
                            }
                        });
                    }
                }
            }],
            searchs: [
                {
                    'category': 'treeSelect',
                    'isWithChildren': true,
                    'isNoEmpty': true,
                    'datafld': 'OrgId'
                },
                {'category': 'text', 'placeholder': '组号'},
                {
                    'category': 'select',
                    'datafld': 'IsBind',
                    'data': [
                        {'id': '0', 'text': '所有'},
                        {'id': '1', 'text': '已关联'},
                        {'id': '2', 'text': '未关联'}
                    ]
                }
            ]
        }, {
            "rowId": 'OrgId',
            "scrollY": "400px",
            "scrollCollapse": true,
            "lengthChange": false,
            'nofooter': true
        });
    });

    //各个按钮的显示
    flds['fenceBindModal'] = 1;
    flds['fenceBindGroupModal'] = 1;

    flds['bindFence'] = 1;
    flds['unbindFence'] = 1;
    flds['BindGroupFence'] = 1;
    flds['UnBindGroupFence'] = 1;
    flds['edit'] = 1;
    flds['del'] = 1;


    var dt = flds['search'] && new dtc({
        pid: 'table_Div',
        columns: ['FenceName', 'FenceTypeText', 'VehicleInfo'],
        name: 'fence',
        url: URL + '/api/fence/FenceListByOrg',
        checkbox_data: "Id",
        validate: true,
        parameters: {
            'FenceName': {
                'title': '名称'
            },
            'VehicleInfo': {
                'title': '信息',
                'columnWidth': 60,
                'tableRender': function (data, type, full, meta, obj) {
                    var result = '<a href="#" title="信息" key="' + full.Id + '" name="' + full.FenceName
                        + '" data-toggle="modal" data-target="#FenceDetails"><img src="../Content/img/vehicleinfo.png" width="20" height="20" /></a>';
                    return result;
                }
            },
            'FenceTypeText': {
                'title': '类型'
            }
        },
        loadAfter: function () {
            if (!$.isEmptyObject(dt.selectedId)) {
                $.each(dt.selectedId, function (i, n) {
                    mapObj.clearFigure(n);
                });
                dt.selectedId = {};
            }
        },
        buttons: [{
            'text': '修改',
            'id': 'edit',
            'data': ['FenceName']
        }, {
            'text': '删除',
            'id': 'del',
            'model_body': '确定要删除选中围栏？'
        }, {
            'text': '关联设备',
            'id': 'fenceBindModal',
            'button_Obj': {
                'isSingle': true,
                'obj': fenceBindObj
            }
        }, {
            'text': '关联组',
            'id': 'fenceBindGroupModal',
            'button_Obj': {
                'isSingle': true,
                'obj': fenceBindGroupObj
            }
        }],
        searchs: [{
            'category': 'treeSelect',
            'isWithChildren': true,
            'isNoEmpty': true,
            'showWidth': "100%",
            'datafld': 'OrgId'
        }, {'category': 'text', 'datafld': 'FenceName', 'placeholder': '围栏名称', 'width': '310px'}]
    }, {
        "rowId": "Id",
        "pagingType": "simple",
        "scrollY": '450px',
        "lengthChange": false,
        "scrollCollapse": true,
        "info": false,
        "selecthandle": function (selected, rowData) {
            var drawfence = [function () {
                var l = rowData.FenceData.split('|');
                mapObj.showAdminArea(l[0], l[1], rowData.FenceName, rowData.Id, function (ids) {
                    dt.selectedId[rowData.Id] = ids;
                });
            }, function () {
                var l = rowData.FenceData.replace(/\||,/g, "-").split('-');
                mapObj.addCircle({
                    'lng': l[0],
                    'lat': l[1],
                }, l[2], rowData.Id, rowData.FenceName, {hideClearImg: true});
                dt.selectedId[rowData.Id] = [rowData.Id];
            }, function () {
                var l = rowData.FenceData.split(';');
                var p = [];
                for (var i = 0; i < l.length; i++) {
                    var d = l[i].split(',');
                    p.push({'lng': d[0], 'lat': d[1]});
                }
                mapObj.addPolygon(p, rowData.Id, rowData.FenceName, {hideClearImg: true});
                dt.selectedId[rowData.Id] = [rowData.Id];
            }, function () {
                console.log(rowData.FenceData);
                var l = rowData.FenceData.split(';');
                var d0 = l[0].split(',');
                var lng0 = d0[0];
                var lat0 = d0[1];

                var d1 = l[1].split(',');
                var lng1 = d1[0];
                var lat1 = d1[1];

                //新增矩形 paths:{northEast:{lat : lat,lng : lng},southWest:{lat : lat,lng : lng}}
                var paths = {
                    northEast: {
                        lat: lat0,
                        lng: lng0
                    },
                    southWest:
                        {lat: lat1, lng: lng1}
                };

                mapObj.addRectangle(paths, rowData.Id, rowData.FenceName, {hideClearImg: true});
                dt.selectedId[rowData.Id] = [rowData.Id];
            }];
            if (!dt.selectedId) {
                dt.selectedId = {};
            }
            if (!$.isEmptyObject(dt.selectedId)) {
                $.each(dt.selectedId, function (i, n) {
                    mapObj.clearFigure(n);
                });
                dt.selectedId = {};
            }
            if (selected) {
                drawfence[rowData.FenceType]();
            }
        }
    });

    var FenceDetailsObj = $('body').addModel({
        id: 'FenceDetails',
        width: '800px',
        height: '600px',
        text: '信息',
        nofooter: true,
        body: '<div id="FenceDetails_Div"></div>'
    }).on('hide.bs.modal', function (obj) {
        $('*[datafld]', FenceDetailsObj).each(function (i, n) {
            $(n).html('');
        });
    }).on('shown.bs.modal', function (obj) {
        var _body = $('#FenceDetails_Div', FenceDetailsObj);
        if (!_body.hasClass('isFormat')) {
            var data = [
                {title: '已绑定用户', datafld: 'UserName'},
                {title: '已绑定组', datafld: 'OrgName'},
                {title: '已绑定目录', datafld: 'CataName'},
                {title: '建立时间', datafld: 'CreatTime'},
            ];
            var intoDiv = [];
            $.each(data, function (i, n) {
                intoDiv.push('<div class="form-group">');
                intoDiv.push('<label class="col-sm-2 control-label">' + n.title + '</label>');
                intoDiv.push('<div class="col-sm-10" >');
                intoDiv.push('<span  style="min-height:30px;display: inline-table; "  datafld="' + n.datafld + '" class="form-control">');
                intoDiv.push('</span >');
                intoDiv.push('</div>');
                intoDiv.push('</div>');
            });
            _body.html(intoDiv.join(''));
            _body.addClass('isFormat');
        }
        var btnobj = $(obj.relatedTarget);
        FenceDetailsObj.find('.modal-title').html(btnobj.attr('name') + ' - 信息');
        $.updateSever({
            'para': {deviceNumber: btnobj.attr('key')},
            'url': URL + '/api/Device/ShowFenceDetails',
            'success': function (result) {
                $.each(function (i, n) {
                    result[n.datafld] && (result[n.datafld] = n.format(result[n.datafld]));
                });
                FenceDetailsObj.toBind(result);
            }
        });
    });


});