$(function () {
    var fencingStaus = {
        ids: [],
        selectedId: [],
        id: null,
        obj: null,
        table: null
    };
    //flds['picImg']=1
    fencingStaus.obj = $('body').addModel({
        id: 'FencingStaus',
        width: '1000px',
        height: '600px',
        text: '查看关联围栏',
        body: '<div id="fencingStaus_table" style="float:left;width:310px"></div><div id="fecingM" style="background:red;width:660px;height:400px;margin-left:310px;"></div>'
    }).on('shown.bs.modal', function (obj) {
        fencingStaus.ids = [];
        $(".checkchild:checked", dt.tableObj).each(function (i, n) {
            fencingStaus.ids.push($(n).val());
        });
        if (fencingStaus.table) {
            fencingStaus.table._load();
            return;
        }

        fencingStaus.table = new dtc({
            pid: 'fencingStaus_table',
            columns: ['FenceName', 'FenceTypeText', 'AlarmTypeText'],
            url: '/api/fence/FencesByGroupId',
            parameters: {
                'FenceName': {
                    'title': '名称',
                },
                'FenceTypeText': {
                    'title': '类型',
                },
                'AlarmTypeText': {
                    'title': '报警类型',
                }
            },
            loadAfter: function () {
                $.each(fencingStaus.selectedId, function (i, n) {
                    mapObj.clearFigure(n);
                });
                fencingStaus.selectedId = [];
            },
            ajax: function (data, callback, settings) {
                var _this = this;
                //ajax请求数据
                $.updateSever({
                    'para': {Id: fencingStaus.ids[0]},
                    'url': '/api/fence/FencesByGroupId',
                    'success': function (result) {
                        //封装返回数据
                        var returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.length;//返回数据全部记录
                        returnData.recordsFiltered = result.length;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result;//返回的数据列表
                        callback(returnData);
                    }
                });
            }
        }, {
            "rowId": "Id",
            "pagingType": "simple",
            "scrollY": '450px',
            "lengthChange": false,
            "scrollCollapse": true,
            "info": false,
            "bPaginate": false,
            "selecthandle": function (selected, rowData) {
                var drawfence = [function () {
                    var l = rowData.FenceData.split('|');
                    mapObj.showAdminArea(l[0], l[1], rowData.FenceName, 'fence', function (ids) {
                        fencingStaus.selectedId = ids;
                    });
                }, function () {
                    var l = rowData.FenceData.replace(/\||,/g, "-").split('-');
                    mapObj.addCircle({
                        'lng': l[0],
                        'lat': l[1],
                    }, l[2], 'fence', rowData.FenceName, {hideClearImg: true});
                    fencingStaus.selectedId = ['fence'];
                }];
                $.each(fencingStaus.selectedId, function (i, n) {
                    mapObj.clearFigure(n);
                });
                fencingStaus.selectedId = [];
                if (selected) {
                    drawfence[rowData.FenceType]();
                }
            }
        })
    });
    //var picImg = {
    //    ids: [],
    //    selectedId: [],
    //    id: null,
    //    obj: null,
    //    table: null
    //};
    //picImg.obj = $('body').addModel({
    //    id: 'picImg',
    //    width: '400px',
    //    text: '上传logo',
    //    nofooter: true

    //}).on('shown.bs.modal', function (obj) {
    //    var html='<div class="d_picImg">'+
    //     '<div class="form-group">'+
    //    ' <div class="upload-div login-upload-div">'+
    //    ' <input type="file" accept="image/jpeg" title="点击选择图片" value="点击选择图片" class="loginImg"  />'+
    //    ' <img src="/Content/img/addJPG.png " width="140" height="40" class="loginImg" />'+
    //    ' </div>'+
    //    ' <div class="d_logo_t">推荐尺寸 140x50</div>' +
    //    '</div>' +
    //     '</div>';
    //    $('#picImg .modal-body ').html('');
    //    $(html).appendTo($('#picImg .modal-body '))
    //    $('#picImg .modal-body .d_picImg').show();
    //    console.log(html)
    //})
    var mapObj = new MapControl('fecingM', {
        'interfaces': ['geocoder', 'selectAdminArea'],
        'plugins': {
            //"ToolBar": {},
            //"OverView": {},
            "Scale": {},
            //"MapType": {}
        }
    });
    var dt = null;
    flds['search'] && (dt = new dtc({
        columns: ['OrgName', 'ParentName', 'RoleId', 'Seq', 'RoleOrgModels', 'CreateTime', 'Remark'],
        name: 'Group',
        checkbox_data: "Id",
        validate: true,
        parameters: {
            'OrgName': {
                'title': '名称',
                'validators': {
                    'notEmpty': {
                        'message': '名称不能为空'
                    }
                }
            },
            'ParentId': {
                'title': '所属组',
                'type': 'treeSelect',
                'isNoEmpty': true,
                'validators': {}
            },
            'ParentName': {
                'title': '所属组',
                'columnWidth': 200
                //'orderKey': 'b.Name',
                //'orderable': true
            },
            'CreateTime': {
                'title': '创建时间',
                //'orderable': true,
                //'orderKey': 'a.LastUpdateTime',
                //'order': 'desc',
                'tableRender': function (data, type, full, meta, obj) {
                    return $.toDateTime(data);
                }
            },
            'Remark': {
                'title': '备注',
                'type': 'textarea',
                'validators': {}
            },
            'RoleOrgModels': {
                'title': '角色',
                'type': 'multiple_checkbox',
                'remote': {
                    'url': '/api/Permission/ShowRoleOrgList',
                    'text': 'RoleName',
                    'loadNow': true,
                    'id': 'RoleId'
                },
                'tableRender': function (data, type, full, meta, obj) {
                    var result = '';
                    if (data) {
                        var temp = [];
                        $.each(data, function (i, n) {
                            temp.push('【' + n.RoleName + '】');
                        });
                        result = temp.join(',');
                    }
                    return result;
                },
                'validators': {
                    'notEmpty': {
                        'message': '请选择角色'
                    }
                }
            },
            'Seq': {
                'title': '序号',
                'validators': {
                    'notEmpty': {
                        'message': '序号不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    }
                }
            }
        },
        buttons: [
            {
                'text': '增加',
                'id': 'add',
                'data': ['OrgName', 'ParentId', 'Seq', 'RoleOrgModels', 'Remark'],
                'formatData': function (data) {
                    var temp = data.RoleOrgModels;
                    data.RoleOrgModels = [];
                    $.each(temp, function (i, n) {
                        data.RoleOrgModels.push({
                            RoleId: n.RoleId,
                            SelectedRoleId: (n.IsChecked ? n.RoleId : null)
                        });
                    });
                }
            },
            {
                'text': '删除',
                'id': 'del',
                'model_body': '确定要删除？',
                'handle': function (n, data, closeModel) {
                    var _this = this;
                    var ids = [];
                    $(".checkchild:checked", _this.tableObj).each(function (i, n) {
                        ids.push($(n).val());
                    });
                    $.updateSever({
                        'para': ids,
                        'url': n.url || '/api/' + _this.name + '/Remove',
                        'success': function (data) {
                            if (data === true) {
                                closeModel();
                                _this._load();
                                loadTree();
                            } else {
                                $.alert({
                                    title: '提示',
                                    content: n['error_msg'] + n['text'] + '失败!'
                                });
                            }
                        },
                        'error': function () {
                            closeModel(true);
                        }
                    });
                }
            },
            {
                'text': '修改',
                'id': 'edit',
                'formatData': function (data) {
                    var temp = data.RoleOrgModels;
                    data.RoleOrgModels = [];
                    $.each(temp, function (i, n) {
                        data.RoleOrgModels.push({
                            RoleId: n.RoleId,
                            SelectedRoleId: (n.IsChecked ? n.RoleId : null)
                        });
                    });
                },
                'data': ['OrgName', 'ParentId', 'Seq', 'RoleOrgModels', 'Remark']
            },
            {
                'text': '查看关联围栏',
                'id': 'FencingStaus',
                'width': '800px',
                'height': '600px',
                'button_Obj': {
                    'isSingle': true,
                    'obj': fencingStaus.obj
                }
            }/*,
            {
                'text': '上传图片',
                'id': 'picImg',
                'width': '800px',
                'height': '600px',
                'button_Obj': {
                    'isSingle':true,
                }
             }*/
        ],
        searchs: [
            {
                'category': 'treeSelect',
                'showWidth': '250px',
                'isWithChildren': true,
                'isNoEmpty': true,
                'datafld': 'Id'
            },
            {'category': 'text', 'placeholder': '请输入组名'}
        ]
    }, {}));
});