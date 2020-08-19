$(function () {
    var self_obj = new dtc({
        columns: ['OrgName', 'PlateNo', 'VIN', 'CarColor', 'CarType', 'Brand', 'Model', 'Owner', 'Phone', 'ZipDeviceModels', 'Remark'],
        name: 'Vehicle',
        checkbox_data: "Id",
        validate: true,
        parameters: {
            'OrgId': {
                'title': '所属组',
                'type': 'treeSelect',
                // 'columnWidth': 50,
                'isNoEmpty': true,
                'validators': {}
            },
            'OrgName': {
                'title': '所属组'
            },
            'PlateNo': {
                'title': '车牌号',
                'validators': {
                    'notEmpty': {
                        'message': '车牌号不能为空'
                    },
                    'remote': {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                        url: '/api/Vehicle/CheckPlateNoOnly',//验证地址
                        message: '车牌号已存在',//提示消息
                        name: 'plateNo',
                        delay: 1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'post',//请求方式
                        data: function (validator) {
                            return {
                                "PlateNo": $('*[datafld=PlateNo]', validator.$form).val(),
                                "Id": $(".checkchild:checked", self_obj.tableObj).val()
                            };
                        }
                    },
                    'merge': true
                }
            },
            'VIN': {
                'title': '车架号',
                'validators': {
                    stringLength: {
                        min: 9,
                        max: 9,
                        message: '请输入9位车架号'
                    }
                }
            },
            'CarColor': {
                'title': '车辆颜色',
                'type': 'select',
                'data': [
                    {'text': '请选择', 'id': ''},
                    {'text': '黑色', 'id': '0'}
                    , {'text': '白色', 'id': '1'}
                    , {'text': '红色', 'id': '2'}
                    , {'text': '灰色', 'id': '3'}
                    , {'text': '银色', 'id': '4'}
                    , {'text': '黄色', 'id': '5'}
                ]
            },
            'CarType': {
                'title': '车辆类型',
                'type': 'select',
                'data': [
                    {'text': '请选择', 'id': ''},
                    {'text': '多用途车', 'id': '0'}
                    , {'text': '轿车', 'id': '1'}
                    , {'text': '商务车', 'id': '2'}
                    , {'text': '大巴', 'id': '3'}
                    , {'text': '小巴', 'id': '4'}
                    , {'text': '大客车', 'id': '5'}
                    , {'text': '大货车', 'id': '6'}
                    , {'text': '小货车', 'id': '7'}
                    , {'text': '农用车', 'id': '8'}
                    , {'text': '工程车', 'id': '9'}
                ]
            },
            'ZipDeviceModels': {
                'title': '关联的终端',
                'tableRender': function (data, type, full, meta, obj) {
                    //     var result = [];
                    if (data === undefined) return null;
                    var result = '<a href="javascript:void(0)" title="' + data + '" key="' + data + '" data-toggle="modal" data-target="#DeviceDetails">' + data + '</a>';
                    return result;
                    // if (data && data.length > 0) {
                    //     $.each(data, function (i, n) {
                    //         //result.push('<a href="javascript:void(0)" title="' + n.Name + '" key="' + n.DeviceNumber + '" data-toggle="modal" data-target="#DeviceDetails">' + n.Name + '[' + n.DeviceNumber + ']' + '</a>');
                    //         result.push('<a href="javascript:void(0)" title="' + n.DeviceNumber + '" key="' + n.DeviceNumber + '" data-toggle="modal" data-target="#DeviceDetails">' + n.DeviceNumber + '</a>');
                    //     });
                    // }
                    // return result.join('，');
                }
            },
            'Brand': {
                'title': '品牌',
                'type': 'select',
                'data': [
                    {'text': '请选择', 'id': ''},
                    {'text': '解放', 'id': '0'}
                    , {'text': '上汽', 'id': '1'}
                    , {'text': '三菱', 'id': '2'}
                ]
            },
            'Model': {
                'title': '型号',
                'type': 'select',
                'data': [
                    {'text': '请选择', 'id': ''},
                    {'text': '中型', 'id': '0'}
                    , {'text': '重型', 'id': '1'}
                    , {'text': '轻型', 'id': '2'}
                ]
            },
            'Owner': {
                'title': '车主姓名',
                'validators': {}
            },
            'Phone': {
                'title': '联系方式',
                'validators': {
                    stringLength: {
                        min: 11,
                        max: 11,
                        message: '请输入11位联系方式'
                    }
                }
            },
            'Remark': {
                'title': '备注',
                'type': 'textarea',
                'validators': {}
            }
        },
        buttons: [
            {
                'text': '增加',
                'id': 'add',
                'data': ['OrgId', 'PlateNo', 'VIN', 'CarColor', 'CarType', 'Brand', 'Model', 'Owner', 'Phone', 'Remark']
            },
            {
                'text': '删除',
                'id': 'del',
                'model_body': '确定要删除？'
            },
            {
                'text': '修改',
                'id': 'edit',
                'validateField': 'PlateNo',
                'data': ['OrgId', 'PlateNo', 'VIN', 'CarColor', 'CarType', 'Brand', 'Model', 'Owner', 'Phone', 'Remark']
            }
        ],
        searchs: [
            {
                'category': 'treeSelect',
                'isWithChildren': true,
                'isNoEmpty': true,
                'datafld': 'OrgId'
            }, {
                'category': 'text', 'width': '200px', 'placeholder': '请输入车牌号'
            }
        ]
    });

    var DeviceDetailsObj = $('body').addModel({
        id: 'DeviceDetails',
        width: '400px',
        height: '600px',
        text: '设备详情',
        nofooter: true,
        body: '<div id="Details_Div"></div>'
    }).on('hide.bs.modal', function (obj) {
        $('*[datafld]', DeviceDetailsObj).each(function (i, n) {
            $(n).html('');
        });
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
                {title: '所属组', datafld: 'OrgName',columnWidth: 70},
                {title: '设备号', datafld: 'DeviceNumber'},
                {title: '设备名称', datafld: 'Name'},
                {title: '设备型号', datafld: 'ModelName'},
                {title: '设备类型', datafld: 'ModelTypeText'},
                {title: '到期日期', datafld: 'ExpireDate'},
                {title: 'Sim卡号', datafld: 'Simcard'},
                {title: '出厂日期', datafld: 'MFDate'},
                {title: '备注', datafld: 'Remark'}];
            var intoDiv = [];
            $.each(data, function (i, n) {
                intoDiv.push('<div class="form-group">');
                intoDiv.push('<label class="col-sm-3 control-label">' + n.title + '</label>');
                intoDiv.push('<div class="col-sm-9">');
                intoDiv.push('<span datafld="' + n.datafld + '" class="form-control">');
                intoDiv.push('</span>');
                intoDiv.push('</div>');
                intoDiv.push('</div>');
            });
            _body.html(intoDiv.join(''));
            _body.addClass('isFormat');
        }
        var btnobj = $(obj.relatedTarget);
        DeviceDetailsObj.find('.modal-title').html(btnobj.attr('title') + '设备详情');
        $.updateSever({
            para: {deviceNumber: btnobj.attr('key')},
            url: '/api/Device/ShowDeviceDetails',
            success: function (result) {
                $.each(formatDatas, function (i, n) {
                    result[n.datafld] && (result[n.datafld] = n.format(result[n.datafld]));
                });
                DeviceDetailsObj.toBind(result);
            }
        });
    });
});