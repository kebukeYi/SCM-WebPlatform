$(function () {

    /**
     全局 接口访问地址 IP ：port
     */
    var URL = GlobalConfig.IPSSAddress;

    var model_List = [{
        'text': '所有型号', 'id': ''
    }];

    var model_List2 = [];

    $.updateSever({
        'url': URL + '/api/Device/ShowModelList',
        'async': false,
        'success': function (result) {
            $.each(result, function (i, n) {
                model_List.push({
                    'text': n.ModelName, 'id': n.Id
                });
                model_List2.push({
                    'text': n.ModelName, 'id': n.Id
                });
            });
        }
    });


    if (flds['search']) {

        var associateVehicle = {
            ids: [],
            id: null,
            obj: null,
            table: null
        };

        associateVehicle.obj = $('body').addModel({
            id: 'AssociateVehicle',
            width: '900px',
            height: '700px',
            text: '绑定车辆',
            body: '<div>请在下表中选择绑定的车辆</div><div id="AssociateVehicle_table"></div>'
        }).on('shown.bs.modal', function (obj) {
            associateVehicle.ids = [];
            $(".checkchild:checked", dt.tableObj).each(function (i, n) {
                associateVehicle.ids.push($(n).val());
            });
            if (associateVehicle.table) {
                $('[datafld]', associateVehicle.table.searchDiv).each(function (i, n) {
                    $(n).data('reSet') && $(n).data('reSet')();
                });
                associateVehicle.table._load();
                return;
            }
            associateVehicle.table = new dtc({
                pid: 'AssociateVehicle_table',
                columns: ['OrgName', 'PlateNo', 'Owner', 'Phone'],
                noDownLoad: true,
                name: 'Vehicle',
                updateAfter: function () {
                    associateVehicle.id = null;
                },
                parameters: {
                    'PlateNo': {
                        'title': '车牌号'
                    },
                    'OrgName': {
                        'title': '所属组'
                    },
                    'Owner': {
                        'title': '车主姓名'
                    },
                    'Phone': {
                        'title': '联系方式'
                    }
                },
                searchs: [
                    {
                        'category': 'treeSelect',
                        'isWithChildren': true,
                        'isNoEmpty': true,
                        'datafld': 'OrgId'
                    },
                    {'category': 'text', 'width': '300px', 'placeholder': '车牌号称'}
                ]
            }, {
                "selecthandle": function (selected, rowData) {
                    associateVehicle.id = rowData.Id;
                    // associateVehicle.id = rowData.DeviceNumber;
                },
                "lengthChange": false,
                "pageLength": 10
            });
            var _footer = associateVehicle.obj.find('.modal-footer');
            $('<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>').appendTo(_footer);
            $('<button type="button" class="btn btn-default" >确定</button>').appendTo(_footer).on("click", function () {
                if (!associateVehicle.id) {
                    $.alert({
                        title: '提示',
                        content: '请选择绑定的车辆!'
                    });
                    return;
                }
                var para = {};
                para['TerIds'] = associateVehicle.ids[0];
                para['CarId'] = associateVehicle.id;
                $.updateSever({
                    'para': para,
                    'url': URL + '/api/Device/AssociateVehicle',
                    'success': function (data) {
                        if (data === true) {
                            associateVehicle.obj.modal('hide');
                            $.alert({
                                title: '提示',
                                content: '绑定车辆成功!'
                            });
                            dt._load();
                        } else {
                            $.alert({
                                title: '提示',
                                content: '绑定车辆失败!'
                            });
                        }
                    }
                });
            });
        });

        var fencingStaus = {
            ids: [],
            selectedId: [],
            id: null,
            obj: null,
            table: null
        };

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
                url: URL + '/api/fence/FencesByTerminalId',
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
                        'url': URL + '/api/fence/FencesByTerminalId',
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

        var mapObj = new MapControl('fecingM', {
            'interfaces': ['geocoder', 'selectAdminArea'],
            'plugins': {
                //"ToolBar": {},
                //"OverView": {},
                "Scale": {},
                //"MapType": {}
            }
        });
        var dt = new dtc({
            columns: ['OrgName', 'DeviceNumber', 'Name', 'PlateNo', 'ModelName', 'ModelTypeText', 'ICCID', 'LastUpdateTime', 'CreateTime', 'ExpireDate', 'Simcard', 'MFDate', 'Remark'],
            name: 'Device',
            // checkbox_data: "Id",
            checkbox_data: "DeviceNumber",
            validate: true,
            parameters: {
                'CreateTime': {
                    'title': '创建时间',
                    'tableRender': function (data, type, full, meta, obj) {
                        return $.toDateTime(data);
                    }
                },
                'DeviceNumber': {
                    'title': '设备号',
                    'type': 'text',
                    'validators': {
                        'notEmpty': {
                            'message': '设备号不能为空'
                        },
                        'digits': {
                            'message': '只能输入数字'
                        },
                        'remote': {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                            url: URL + '/api/Device/CheckDeviceNumberOnly',//验证地址
                            message: '设备号已存在',//提示消息
                            name: 'deviceNumber',
                            delay: 1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                            type: 'post'//请求方式
                        }
                    }
                },
                'ModeId': {
                    'title': '设备型号',
                    'type': 'select',
                    'data': model_List2
                },
                'ModelName': {
                    'title': '设备型号'
                },
                'ModelTypeText': {
                    'title': '设备类型'
                },
                'ICCID': {
                    'title': 'ICCID号',
                    'type': 'text',
                    'validators': {
                        'notEmpty': {
                            'message': '设备号不能为空'
                        },
                        'digits': {
                            'message': '只能输入数字'
                        }
                    }
                },
                'DevicePassword': {
                    'title': '终端密码',
                    'type': 'text',
                    'validators': {
                        'notEmpty': {
                            'message': '终端密码不能为空'
                        }
                    }
                },
                'LastUpdateTime': {
                    'title': '最后更新时间',
                    // 'orderable': true,
                    // 'orderKey': 'a.LastUpdateTime',
                    // 'order': 'desc',
                    'tableRender': function (data, type, full, meta, obj) {
                        return $.toDateTime(data);
                    }
                },
                'MFDate': {
                    'title': '出厂日期',
                    'type': 'date',
                    'tableRender': function (data, type, full, meta, obj) {
                        return $.toDate(data);
                    }
                },
                'Name': {
                    'title': '名称',
                    'type': 'text',
                    'validators': {
                        'notEmpty': {
                            'message': '名称不能为空'
                        }
                    }
                },
                'OrgnizationId': {
                    'title': '所属组',
                    'type': 'treeSelect',
                    'isNoEmpty': true,
                    'validators': {}
                },
                'OrgId': {
                    'title': '目标组',
                    'type': 'treeSelect',
                    'isNoEmpty': true,
                    'validators': {}
                },
                'ImportXls': {
                    'title': '文件',
                    'type': 'import'
                },
                'DownLaod_Templet': {
                    'type': 'html',
                    'title': '',
                    'body': '<a style="font-weight:900;text-decoration:underline" href="/Document/ExcelTemplate.xlsx">导入模板下载</a>'
                },
                'DownLaod_TransferTemplet': {
                    'type': 'html',
                    'title': '',
                    'body': '<a style="font-weight:900;text-decoration:underline" href="/Document/DeviceToGroup.xlsx">导入模板下载</a>'
                },
                'TransferDeviceNumber': {
                    'title': '设备号',
                    'type': 'textarea',
                    'obj_option': {
                        'rows': 10
                    },
                    'validators': {
                        'notEmpty': {
                            'message': '设备号不能为空'
                        }
                    }
                },
                'Remark': {
                    'title': '备注',
                    'type': 'textarea',
                    'validators': {}
                },
                'Simcard': {
                    'title': 'Sim卡号',
                    'type': 'text',
                    'validators': {
                        'notEmpty': {
                            'message': '设备号不能为空'
                        },
                        'digits': {
                            'message': '只能输入数字'
                        }
                    }
                },
                'OrgName': {
                    'title': '所属组',
                    'orderKey': 'b.Name',
                    'orderable': true
                },
                'PlateNo': {
                    'title': '车牌号'
                },
                'ExpireDate': {
                    'title': '到期日期',
                    'type': 'date',
                    'obj_option': {
                        'control_option': {
                            'minDate': (new Date()),
                            'startDate': $.getNextYear()
                        }
                    },
                    'tableRender': function (data, type, full, meta, obj) {
                        return $.toDate(data);
                    },
                    'validators': {
                        'notEmpty': {
                            'message': '到期日期不能为空'
                        }
                    }
                }
            },
            buttons: [
                {
                    'text': '增加',
                    'width': '700px',
                    'id': 'add',
                    'data': ['OrgId', 'DeviceNumber', 'Name', 'ModeId', 'ICCID', 'DevicePassword', 'Simcard', 'ExpireDate', 'MFDate', 'Remark']
                },
                {
                    'text': '删除',
                    'id': 'del',
                    'model_body': '确定要删除？'
                },
                {
                    'text': '修改',
                    'id': 'edit',
                    'data': ['OrgId', 'Name', 'ModeId', 'ICCID', 'DevicePassword', 'Simcard', 'MFDate', 'Remark']
                },

                // {
                //     'text': '选择改组',
                //     'id': 'updateOrgnization',
                //     'data': ['OrgId'],
                //     'button_Obj': {
                //         'isMultiple': true
                //     },
                //     'handle': function (n, data, closeModel) {
                //         var _this = this;
                //         var ids = [];
                //         $(".checkchild:checked", _this.tableObj).each(function (i, n) {
                //             ids.push($(n).val());
                //         });
                //         var para = {};
                //         para['Ids'] = ids;
                //         para['Id'] = data.OrgId;
                //         $.updateSever({
                //             'para': para,
                //             'url': URL + '/api/Device/UpdateOrgnization',
                //             'success': function (data) {
                //                 if (data === true) {
                //                     closeModel();
                //                     _this._load();
                //                 } else {
                //                     $.alert({
                //                         title: '提示',
                //                         content: '选择改组失败!'
                //                     });
                //                 }
                //             }
                //         });
                //     }
                // },

                // {
                //     'text': '导入改组',
                //     'id': 'ImportTransfer',
                //     'data': ['OrgId', 'ImportXls', 'DownLaod_TransferTemplet'],
                //     'handle': function (n, data, closeModel) {
                //         var _this = this;
                //         var para = new FormData();
                //         var files = $('[type="file"]', _this.btnObjs['ImportTransfer'].data('button_modal').obj).get(0).files;
                //         if (files.length > 0) {
                //             para.append("ExcelFile", files[0]);
                //             para.append("OrgId", data.OrgId);
                //         } else {
                //             $.alert({
                //                 title: '提示',
                //                 content: '请选择要导入的文件!'
                //             });
                //             return;
                //         }
                //         $.showLoading('导入中。。。');
                //         setTimeout(function () {
                //             $.ajax({
                //                 type: "POST",
                //                 url: URL + "/api/Device/ImportTransfer",
                //                 contentType: false,
                //                 processData: false,
                //                 data: para,
                //                 success: function (result) {
                //                     $.hideLoading();
                //                     if (!result.Result) {
                //                         closeModel();
                //                         _this._load();
                //                     } else {
                //                         $.alert({
                //                             title: '提示',
                //                             content: (result.Result || '导入改组失败!')
                //                         });
                //                     }
                //                 }
                //             });
                //         }, 50);
                //     }
                // },
                // {
                //     'text': '输入改组',
                //     'id': 'EnterTransfer',
                //     'data': ['OrgId', 'TransferDeviceNumber'],
                //     'handle': function (n, data, closeModel) {
                //         var _this = this;
                //         data.Ids = [];
                //         if (data.TransferDeviceNumber) {
                //             $.each(data.TransferDeviceNumber.split("\n"), function (i, n) {
                //                 data.Ids.push($.trim(n));
                //             });
                //         }
                //         data.Id = data.OrgId;
                //         delete data.TransferDeviceNumber;
                //         delete data.OrgId;
                //         $.updateSever({
                //             'para': data,
                //             'url': URL + '/api/Device/EnterTransfer',
                //             'success': function (result) {
                //                 if (result === true) {
                //                     closeModel();
                //                     _this._load();
                //                 } else {
                //                     $.alert({
                //                         title: '提示',
                //                         content: '输入改组失败!'
                //                     });
                //                 }
                //             }
                //         });
                //     }
                // },

                {
                    'text': '绑定车辆',
                    'id': 'AssociateVehicle',
                    'button_Obj': {
                        // 'isMultiple': true,
                        //只能选中一列
                        'isSingle': true,
                        'obj': associateVehicle.obj
                    }
                },
                {
                    'text': '取消车辆绑定',
                    'id': 'DisassociateVehicle',
                    'model_body': '确定要取消绑定？',
                    'button_Obj': {
                        // 'isMultiple': true,
                        'isSingle': true,
                        'handle': function (n, data, closeModel) {
                            var _this = this;
                            var Ids = [];
                            $(".checkchild:checked", _this.tableObj).each(function (i, n) {
                                Ids.push($(n).val());
                            });
                            $.updateSever({
                                'para': Ids,
                                'url': URL + '/api/Device/DisassociateVehicle',
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
                },
                {
                    'text': '导入终端',
                    'id': 'ImportTerminals',
                    'data': ['OrgId', 'ImportXls', 'DownLaod_Templet'],
                    'handle': function (n, data, closeModel) {
                        var _this = this;
                        var para = new FormData();
                        var files = $('[type="file"]', _this.btnObjs['ImportTerminals'].data('button_modal').obj).get(0).files;
                        if (files.length > 0) {
                            para.append("ExcelFile", files[0]);
                            para.append("OrgId", data.OrgId);
                        } else {
                            $.alert({
                                title: '提示',
                                content: '请选择要导入的文件!'
                            });
                            return;
                        }
                        $.showLoading('导入中。。。');
                        setTimeout(function () {
                            $.ajax({
                                type: "POST",
                                url: URL + "/api/Device/ImportDevice",
                                contentType: false,
                                processData: false,
                                data: para,
                                success: function (result) {
                                    $.hideLoading();
                                    if (!result.Result) {
                                        closeModel();
                                        _this._load();
                                    } else {
                                        $.alert({
                                            title: '提示',
                                            content: (result.Result || '导入失败!')
                                        });
                                    }
                                }
                            });
                        }, 50);
                    }
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
                }
            ],
            searchs: [
                {
                    'category': 'treeSelect',
                    'isWithChildren': true,
                    'isNoEmpty': true,
                    'datafld': 'Id'
                },
                {
                    'category': 'rangedate',
                    'placeholder': '请选择到期日期区间',
                    'width': '200px',
                    'control_option': {
                        autoUpdateInput: false
                    }
                }, {
                    'category': 'select',
                    'datafld': 'ModeId',
                    'data': model_List
                }, {
                    'category': 'select',
                    'datafld': 'ModelType',
                    'data': [
                        {'text': '设备类型', 'id': ''},
                        {'text': '有线', 'id': 0},
                        {'text': '无线', 'id': 1},
                        {'text': '无线不充电', 'id': 10},
                        {'text': '无线可充电', 'id': 11}
                    ]
                }, {
                    'category': 'text',
                    'width': '300px',
                    'placeholder': '请输入设备号或名称或车牌号或Sim卡号'
                }]
        }, {});
    }
});