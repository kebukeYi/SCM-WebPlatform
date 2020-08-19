$(function () {

    /*
   全局 接口访问地址 IP ：port
    */
    var URL = GlobalConfig.IPSSAddress;

    var model_List = [{
        'text': '所有型号', 'id': null
    }];
    $.updateSever({
        'url': URL + '/api/Device/ShowModelList',
        'async': false,
        'success': function (result) {
            $.each(result, function (i, n) {
                model_List.push({
                    'text': n.ModelName, 'id': n.Id
                });
            });
        }
    });
    flds['search'] && new dtc({
        columns: ['DeviceNumber', 'OrgName', 'DeviceName', 'ModelName', 'VehicleInfo', 'ModelTypeText', 'Phone', 'LocationType', 'LocationTime', 'ExpireDate', 'LocationInfo', 'LngLat'],
        excludeColumns: ['VehicleInfo', 'LocationInfo'],//导出报表需要排除的列
        name: 'Report',
        url: URL + '/api/Report/ShowDeviceLocationInfo',
        showAddress: true,
        validate: true,
        parameters: {
            'DeviceNumber': {
                'title': '设备号',
                'columnWidth': 110
            },
            'OrgName': {
                'title': '所属组',
                'columnWidth': 70
            },
            'DeviceName': {
                'title': '设备名称',
                'columnWidth': 50
            },
            'ModelName': {
                'title': '设备型号',
                'columnWidth': 50
            },
            'ModelTypeText': {
                'title': '设备类型',
                'columnWidth': 60,
            },
            'VehicleInfo': {
                'title': '信息',
                'columnWidth': 60,
                'tableRender': function (data, type, full, meta, obj) {
                    var result = '<a href="javascript:void(0)" title="信息" key="' + full.DeviceNumber + '" name="' + full.DeviceName
                        + '" data-toggle="modal" data-target="#DeviceDetails"><img src="../Content/img/vehicleinfo.png" width="20" height="20" /></a>&nbsp;&nbsp;<a href="javascript:void(0)" title="报警列表" name="'
                        + full.DeviceName + '" key="' + full.DeviceNumber + '" class="checkAlarms_btn"><img src="../Content/img/alarmlist.png" width="20" height="20" /></a>';
                    return result;
                }
            },
            'Phone': {
                'title': '联系电话',
                'columnWidth': 110
            },
            'OnlineStatus': {
                'title': '设备状态',
                'columnWidth': 60
                //'tableRender': function (data, type, full, meta, obj) {
                //    if (data === true) {
                //        return "在线";
                //    } else if (data === false) {
                //        return "离线";
                //    } else {
                //        return "未激活";
                //    }
                //}
            },
            'LocationType': {
                'title': '定位方式',
                'columnWidth': 70
            },
            'LocationTime': {
                'title': '定位时间',
                'columnWidth': 120,
                'tableRender': function (data, type, full, meta, obj) {
                    return $.toDateTime(data);
                }
            },

            'LocationInfo': {
                'title': '用户管理',
                'columnWidth': 130,
                'tableRender': function (data, type, full, meta, obj) {
                    var result =
                        '<a href="javascript:void(0)" title="当前位置" key="' + full.DeviceNumber + '" name="' + full.DeviceName + '" isOnline="' + full.Online + '" modeltype="' + full.ModelType + '" modelname="' + full.ModelName + '" class="follow_btn">' + '<img src="../Content/img/currentlocation.png" width="20" height="20" /></a>&nbsp;&nbsp;' +
                        '<a href="javascript:void(0)" title="上线详情" key="' + full.DeviceNumber + '" name="' + full.DeviceName + '" class="highLines_btn">' + '<img src="../Content/img/onlinedetail.png" width="20" height="20" /></a>&nbsp;&nbsp;' +
                        '<a href="javascript:void(0)" title="历史轨迹" key="' + full.DeviceNumber + '" name="' + full.DeviceName + '" modeltype="' + full.ModelType + '" class="playback_btn">' + '<img src="../Content/img/track.png" width="20" height="20" /></a>&nbsp;&nbsp;' +
                        '<a href="javascript:void(0)" title="指令下发" key="' + full.DeviceNumber + '" name="' + full.DeviceName + '" data-toggle="modal" data-target="#CmdModal">' + '<img src="../Content/img/orderissued.png" width="20" height="20" /></a>&nbsp;&nbsp;' +
                        '<a href="javascript:void(0)" title="指令记录" key="' + full.DeviceNumber + '" name="' + full.DeviceName + '" data-toggle="modal" data-target="#CmdRecordModal">' + '<img src="../Content/img/instructionrecord.png" width="20" height="20" /></a>';
                    return result;
                }
            },
            //TODO
            'ExpireDate': {
                'title': '到期日期',
                'columnWidth': 90,
                // 'tableRender': function (data, type, full, meta, obj) {
                //     return $.toDateTime(data);
                // }
            },
            'LngLat': {
                'title': '设备当前位置',
                'columnWidth': 200,
                'tableRender': function (data, type, full, meta, obj) {
                    return obj.getAddressByLngLat(data);
                }
            },
            // 'MonthMileage': {
            //     'title': '当月里程(km)',
            //     'columnWidth': 90,
            //     'tableRender': function (data, type, full, meta, obj) {
            //         return data.toFixed(2);
            //     }
            // },
            // 'YearMileage': {
            //     'title': '今年里程(km)',
            //     'columnWidth': 90,
            //     'tableRender': function (data, type, full, meta, obj) {
            //         return data.toFixed(2);
            //     }
            // }
        },
        //buttons: [
        //    {
        //        'text': '刷新',
        //        'id': 'Refresh',
        //        'data': ['OrgId', 'ModeId', 'ModelType']
        //    }
        //],
        searchs: [
            {
                'category': 'treeSelect',
                'isWithChildren': true,
                'isNoEmpty': true,
                'datafld': 'OrgId'
            },
            //{
            //    'category': 'rangedate',
            //    'placeholder': '请选择设备到期日期区间',
            //    'width': '200px',
            //    'control_option': {
            //        autoUpdateInput: false
            //        //"startDate": $.shortDateToJson(moment().startOf('day').toDate()),
            //        //"endDate": $.shortDateToJson(moment().toDate())
            //    }
            //},
            {
                'category': 'select',
                'datafld': 'ModeId',
                'data': model_List
            },
            {
                'category': 'select',
                'datafld': 'ModelType',
                'data': [
                    {'text': '设备类型', 'id': null},
                    {'text': '有线', 'id': 0},
                    {'text': '无线', 'id': 1},
                    {'text': '无线不充电', 'id': 10},
                    {'text': '无线可充电', 'id': 11}
                ]
            },
            {'category': 'text', 'placeholder': '请输入设备号', 'width': '270px'}
        ]
    }, {scrollX: true});

    $('#table_Div').delegate('.playback_btn', 'click', function () {
        var selfObj = $(this);
        window.open("/playback?key=" + selfObj.attr('key') + '&name=' + escape(selfObj.attr('name')) + '&mt=' + selfObj.attr('modeltype'));
    });

    $('#table_Div').delegate('.follow_btn', 'click', function () {
        var selfObj = $(this);
        //TOdo follow?key
        var url = "/follow?key=" + selfObj.attr('key') + '&name=' + escape(selfObj.attr('name')) + '&mt=' + selfObj.attr('modeltype') + '&mn=' + selfObj.attr('modelname') + '&dn=' + escape(selfObj.attr('name'));
        if (selfObj.attr('isOnline')) {
            url = url + '&io=' + 1;
        }
        window.open(url);
    });

    $('#table_Div').delegate('.checkAlarms_btn', 'click', function () {
        var selfObj = $(this);
        if (alarmInfo) {
            alarmInfo.deviceNumber = selfObj.attr('key');
            alarmInfo.title = selfObj.attr('name');
            $('#alarmModal').modal('show');
        }
    });

    $('.d_line_marker span').click(function () {
        $('.d_line_marker').hide();
    });

    $('#table_Div').delegate('.highLines_btn', 'click', function () {
        $('.d_line_marker').show();
        $('.d_line_marker ul').html('');
        //$.getdate()
        var deviceNumber = $(this).attr("key");
        var name = $(this).attr("name");
        $('.d_line_marker h2').html(name + ' —上线详情');
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
        //hide();
    });


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
            "BP02": [{
                'datafld': 'Defence', 'type': 'radio', 'data': [{
                    'id': 0, 'text': '设防'
                }, {
                    'id': 1, 'text': '撤防'
                }]
            }, {
                'type': 'html',
                'body': '<div style="font-size:12px;">1、下发设防成功后,设备达到报警条件,会自动产生报警上报到监控平台;<br>2、撤防成功后,相关触发报警自动关闭;</div>'
            }
            ],
            "BP03": [{
                'datafld': 'Flag', 'type': 'radio', 'data': [{
                    'id': 0, 'text': '断油/电路'
                }
                    //, {
                    //    'id': 1, 'text': '断电'
                    //}
                ]
            }, {'type': 'html', 'body': '<div style="font-size:12px;">下发断油/电成功后，设备执行条件：卫星定位有效且行驶速度小于20Km/h；</div>'}],
            "BP04": [{
                'datafld': 'Flag', 'type': 'radio', 'data': [{
                    'id': 0, 'text': '恢复油/电路'
                }
                    //, {
                    //    'id': 1, 'text': '恢复电'
                    //}
                ]
            }],
            "BP06": [{
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
            "BP07": [{'type': 'label', 'text': '定时监控间隔'}
                , {
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
                        , {'id': '1440', 'text': '1天'}
                        , {'id': '2880', 'text': '2天'}
                        , {'id': '4320', 'text': '3天'}
                        , {'id': '5760', 'text': '4天'}
                        , {'id': '7200', 'text': '5天'}
                        , {'id': '8640', 'text': '6天'}
                        , {'id': '10080', 'text': '7天'}
                        , {'id': '11520', 'text': '8天'}
                        , {'id': '12960', 'text': '9天'}
                        , {'id': '14400', 'text': '10天'}
                    ]
                }, {
                    'type': 'html',
                    'body': '<div style="font-size:12px;">1、按照设置的间隔，终端唤醒上报；<br>2、缩短上报时间间隔虽然会提高定位频率，但会影响电池寿命；</div>'
                }],
            "BP08": [{
                'datafld': 'Language', 'type': 'select',
                'data': [{'id': 'zh-cn', 'text': '中文'}, {'id': 'en-us', 'text': '英文'}]
            }, {'type': 'html', 'body': '<div style="font-size:12px;">设备设置语言成功后，回复的短信将变为相应的语言；</div>'}],
            "BP11": [{'type': 'label', 'text': '报警号码'}
                , {
                    'datafld': 'PhoneNumber1',
                    'type': 'text',
                    'validators': {
                        'callback': {
                            'message': '三个号码必需输入一个',
                            'callback': function (value, validator, obj) {
                                var flag = Cmdinfo._BP11_check(validator, obj);
                                return flag;
                            }
                        }
                    },
                    'placeholder': 'sos号码1,三个号码必需输入一个'
                }, {
                    'datafld': 'PhoneNumber2',
                    'type': 'text',
                    'validators': {
                        'callback': {
                            'message': '三个号码必需输入一个',
                            'callback': function (value, validator, obj) {
                                var flag = Cmdinfo._BP11_check(validator, obj);
                                return flag;
                            }
                        }
                    },
                    'placeholder': 'sos号码2,三个号码必需输入一个'
                }, {
                    'datafld': 'PhoneNumber3',
                    'type': 'text',
                    'validators': {
                        'callback': {
                            'message': '三个号码必需输入一个',
                            'callback': function (value, validator, obj) {
                                var flag = Cmdinfo._BP11_check(validator, obj);
                                return flag;
                            }
                        }
                    },
                    'placeholder': 'sos号码3,三个号码必需输入一个'
                }, {
                    'type': 'html',
                    'body': '<div style="font-size:12px;">1、号码为空的时候，手机不接收报警短信提醒<br>2、产生的短信费用会从终端SIM卡上扣取（每条0.1元）。注意终端会因SIM卡欠费而离线。<br/>3、设置完毕后，请开启主机（拨开主机电源开关），确认主机收到该条指令；</div>'
                }],
            "BP13": [{
                'datafld': 'Flag', 'type': 'select',
                'data': [
                    {'id': 0, 'text': 'SOS号码'},
                    {'id': 1, 'text': '亲情号码'},
                    {'id': 2, 'text': '发送给SOS和亲情号码'}
                ]
            }, {
                'datafld': 'Content',
                'type': 'text',
                'placeholder': '发送内容',
                'validators': {
                    'notEmpty': {
                        'message': '发送内容不能为空'
                    }
                }
            }],
            "BP59": [{
                'datafld': 'IsEnabled', 'type': 'radio', 'data': [{
                    'id': 1, 'text': '开启', 'handler': function () {
                        this.parents('.form-group').nextAll().show();
                    }
                }, {
                    'id': 0, 'text': '关闭', 'handler': function () {
                        this.parents('.form-group').nextAll().each(function (i, n) {
                            $(n).hide();
                            if (i == 1) {
                                $(n).find('[datafld="Sensor"]').val(20);
                                var validator = CmdinfoObj.find('.JsonData').data('bootstrapValidator');
                                validator.updateStatus(("Sensor_lab"), "NOT_VALIDATED", null);
                                validator.validateField(('Sensor_lab'));
                            }
                        });
                    }
                }]
            }, {
                'datafld': 'Sensor',
                'type': 'text',
                'placeholder': '震动灵敏度,取值范围1-255',
                'validators': {
                    'notEmpty': {
                        'message': '震动灵敏度不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    },
                    'between': {
                        'message': '取值范围1-255',
                        'min': 1,
                        'max': 255
                    }
                }
            }],
            "BP61": [{
                'type': 'label', 'text': '设备收到下发指令后将重启'
            }, {'type': 'html', 'body': '<div style="font-size:12px;">重启后，主机会重新与平台重新连接，发送数据；</div>'}],
            "BP62": [{
                'type': 'label', 'text': '设备收到下发指令后将恢复出厂设置'
            }, {
                'type': 'html',
                'body': '<div style="font-size:12px;">1、恢复出厂设置后，主机自动清除远程控制指令，恢复到出厂时的默认设置。<br>2、如果设置了报警短信接收号码，则同时清除</div>'
            }],
            "BP64": [{
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
            "BP72": [{
                'type': 'label', 'text': '设置的时间间隔(0-60分钟),表示静止达到该时间后.进入设防状态(单位:分钟),为0时.则取消定时设防'
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
            "BP73": [{
                'datafld': 'Flag',
                'type': 'radio', 'data': [{
                    'id': 0, 'text': '关闭短信报警'
                }, {
                    'id': 1, 'text': '开启短信报警'
                }]
            }],
            "BP74": [{
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
            "BP77": [{
                'datafld': 'Delete1', 'type': 'radio', 'data': [{
                    'id': 0, 'text': '不删除第一个号码'
                }, {
                    'id': 1, 'text': '删除第一个号码'
                }]
            }, {
                'datafld': 'Delete2', 'type': 'radio', 'data': [{
                    'id': 0, 'text': '不删除第二个号码'
                }, {
                    'id': 1, 'text': '删除第二个号码'
                }]
            }, {
                'datafld': 'Delete3', 'type': 'radio', 'data': [{
                    'id': 0, 'text': '不删除第三个号码'
                }, {
                    'id': 1, 'text': '删除第三个号码'
                }]
            }],
            "BP78": [{
                'datafld': 'PhoneNumber', 'type': 'text',
                'placeholder': '主控号码',
                'validators': {
                    'notEmpty': {
                        'message': '主控号码不能为空'
                    }
                }
            }],
            "BP84": [{'type': 'label', 'text': '设置工作模式'}
                , {
                    'datafld': 'Flag', 'type': 'select',
                    'data': [
                        // { 'id': '01', 'text': '设置正常模式' },
                        {'id': '02', 'text': '连续定位'},
                        {'id': '03', 'text': '智能监控'},
                        // { 'id': '00', 'text': '不设置工作模式' }
                    ]
                }, {
                    'type': 'html',
                    'body': '<div style="font-size:12px;">1、非特殊情况，请不要设置工作模式，设置后，耗电量会增大；<br>2、连续定位模式，设备连续上报30分钟后自动关闭，关闭后返回上次工作模式；<br>3、智能监控模式，设备运动按照15秒间隔上报，静止自动休眠；</div>'
                }
            ],
            "BP85": [{
                'datafld': 'Flag',
                'type': 'radio', 'data': [{
                    'id': 0, 'text': '关闭'
                }, {
                    'id': 1, 'text': '打开'
                }]
            }, {'type': 'html', 'body': '<div style="font-size:12px;">防拆开关设置关闭后，但设备被拆掉后，将无法产生报警信息；</div>'}],
            //"BP88": [{
            //    'type': 'label', 'text': '格式:HHmmss.如:05时20分1秒,052001'
            //}, {
            //    'datafld': 'TimeStamp', 'type': 'text',
            //    'placeholder': '每天按时间点唤醒',
            //    'validators': {
            //        'notEmpty': {
            //            'message': '数据上传时间点不能为空'
            //        },
            //        'regexp': {
            //            'message': '格式不正确',
            //            'regexp': '^(([0-1][0-9])|(2[0-3]))([0-5][0-9]){2}$'
            //            //'regexp': '^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$'
            //        }
            //    },
            //    'isf':true
            //}],
            "BP88": [{'type': 'label', 'text': '每天按照时间点上报'}
                , {'datafld': 'TimeStamp', 'type': 'timeSelect'}
                , {
                    'type': 'html',
                    'body': '<div style="font-size:12px;">1、每天24小时定位一次，设置每天XX时:XX分：XX秒定时开机，设置成功后模式随之变成当前模式。<br>2、设备出厂默认为此监控模式</div>'
                }],
            "DP03": [{
                'datafld': 'Interval', 'type': 'text',
                'placeholder': '上传时间间隔,单位:秒,取值范围120-300',
                'validators': {
                    'notEmpty': {
                        'message': '时间间隔不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    },
                    'between': {
                        'message': '取值范围120-300',
                        'min': 120,
                        'max': 300
                    }
                }
            }],
            "DP04": [{
                'datafld': 'Flag',
                'type': 'radio', 'data': [{
                    'id': 0, 'text': '关闭震动传感器判断静止状态'
                }, {
                    'id': 1, 'text': '开启震动传感器判断静止状态'
                }]
            }],
            "DP05": [{
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
            "SD01": [{
                'datafld': 'Speed', 'type': 'text',
                'placeholder': '静止速度,一位小数,2位整数',
                'validators': {
                    'notEmpty': {
                        'message': '静止速度不能为空'
                    },
                    'regexp': {
                        'message': '格式不正确',
                        'regexp': '^\\d{1,2}(\\.\\d){0,1}$'
                    }
                }
            }],
            "WP02": [{
                'datafld': 'Seconds', 'type': 'text',
                'placeholder': '上传时间间隔,单位:秒,取值范围0-9999',
                'validators': {
                    'notEmpty': {
                        'message': '时间间隔不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    },
                    'between': {
                        'message': '取值范围0-9999',
                        'min': 0,
                        'max': 9999
                    }
                }
            }],
            "WP03": [{
                'datafld': 'Seconds', 'type': 'text',
                'placeholder': '上传时间间隔,单位:秒,取值范围0-99999',
                'validators': {
                    'notEmpty': {
                        'message': '时间间隔不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    },
                    'between': {
                        'message': '取值范围0-99999',
                        'min': 0,
                        'max': 99999
                    }
                }
            }],
            "WP10": [{
                'datafld': 'Flag',
                'type': 'radio', 'data': [{
                    'id': 0, 'text': '关闭短信回复'
                }, {
                    'id': 1, 'text': '开启短信回复'
                }]
            }],
            /// 014400:为正常工作模式时,是设置上传间隔为14400分钟,单位:分钟,限制30-28800；   
            ///为定点工作模式时,是设置的上传点为01:44:00；
            ///为连续工作模式、智能监控模式时,此项默认为000000
            "WP12": [{
                'datafld': 'WorkMode', 'type': 'select',
                'data': [{
                    'id': '01', 'text': '正常工作'
                }, {
                    'id': '02', 'text': '连续定位'
                }, {
                    'id': '03', 'text': '智能监控'
                }, {
                    'id': '00', 'text': '定点定位'
                }]
            }, {
                'datafld': 'Value', 'type': 'text',
                'placeholder': '最大限制输入9999'
            }],
            "WP13": [{
                'datafld': 'Flag',
                'type': 'radio', 'data': [{
                    'id': 0, 'text': '关闭'
                }, {
                    'id': 1, 'text': '开启'
                }]
            }],
            "SMS01": [{
                'type': 'label', 'text': '追踪'
            }],
            "SMS02": [{
                'type': 'label', 'text': '休眠'
            }],
            "SMS03": [{
                'type': 'label', 'text': '唤醒'
            }],
            "0003": [{
                'type': 'label', 'text': '终端注销'
            }],
            "8103_0001": [{
                'type': 'label', 'text': '终端心跳发送间隔，单位为秒（s）'
            }, {
                'datafld': 'HeartInterval',
                'type': 'text',
                'placeholder': '发送间隔',
                'validators': {
                    'notEmpty': {
                        'message': '终端心跳发送间隔不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    }
                }
            }],
            "8103_0010": [{
                'type': 'label', 'text': '无线通信拨号访问点。若网络制式为 CDMA，则该处为PPP 拨号号码'
            }, {
                'datafld': 'APN',
                'type': 'text',
                'placeholder': '主服务器APN',
                'validators': {
                    'notEmpty': {
                        'message': '主服务器APN不能为空'
                    }
                }
            }],
            "8103_0013": [{
                'datafld': 'Domain', 'type': 'text',
                'placeholder': 'IP\域名',
                'validators': {
                    'notEmpty': {
                        'message': 'IP\域名不能为空'
                    }
                }
            },
                {
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
            "8103_0027": [{
                'type': 'label', 'text': '休眠时汇报时间间隔，单位为秒（s）'
            }, {
                'datafld': 'Interval',
                'type': 'text',
                'placeholder': '汇报间隔',
                'validators': {
                    'notEmpty': {
                        'message': '休眠时汇报时间间隔不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    }
                }
            }],
            "8103_0029": [{
                'datafld': 'Interval',
                'type': 'text',
                'placeholder': '汇报间隔,单位秒',
                'validators': {
                    'notEmpty': {
                        'message': '汇报间隔不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    }
                }
            }],
            "8103_0030": [{
                'type': 'label', 'text': '拐点补传角度，<180 '
            }, {
                'datafld': 'Degree',
                'type': 'text',
                'placeholder': '角度',
                'validators': {
                    'notEmpty': {
                        'message': '角度不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    },
                    'between': {
                        'message': '取值范围0-180',
                        'min': 0,
                        'max': 180
                    }
                }
            }],
            "8103_0031": [{
                'type': 'label', 'text': '电子围栏半径（非法位移阈值），单位为米，0m 表示关闭'
            }, {
                'datafld': 'Radius',
                'type': 'text',
                'placeholder': '电子围栏半径',
                'validators': {
                    'notEmpty': {
                        'message': '电子围栏半径不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    }
                }
            }],
            "8103_0048": [{
                'datafld': 'MonitorPhone',
                'type': 'text',
                'placeholder': '设置监听号码',
                'validators': {
                    'notEmpty': {
                        'message': '监听号码不能为空'
                    }
                }
            }],
            "8103_0049": [{
                'datafld': 'SOSNumber',
                'type': 'text',
                'placeholder': '设置SOS号码',
                'validators': {
                    'notEmpty': {
                        'message': 'SOS号码不能为空'
                    }
                }
            }],
            "8103_0055": [{
                'datafld': 'MaxSpeed', 'type': 'text',
                'placeholder': '最高速度,单位km/h',
                'validators': {
                    'notEmpty': {
                        'message': '最高速度不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    }
                }
            }, {
                'datafld': 'Duration', 'type': 'text',
                'placeholder': '持续时间,单位秒',
                'validators': {
                    'notEmpty': {
                        'message': '端口不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    }
                }
            }],
            "8103_0080": [{
                'type': 'label', 'text': '里程表读数，1/10km'
            }, {
                'datafld': 'Mileage',
                'type': 'text',
                'placeholder': '里程',
                'validators': {
                    'notEmpty': {
                        'message': '里程不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    }
                }
            }],
            "8103_0083": [{
                'datafld': 'PlateNo',
                'type': 'text',
                'placeholder': '车牌号',
                'validators': {
                    'notEmpty': {
                        'message': '车牌号不能为空'
                    }
                }
            }],
            "8103_0084": [{
                'datafld': 'PlateColor', 'type': 'select',
                'data': [{
                    'id': '1', 'text': '蓝色'
                }, {
                    'id': '2', 'text': '黄色'
                }, {
                    'id': '3', 'text': '黑色'
                }, {
                    'id': '4', 'text': '白色'
                }, {
                    'id': '9', 'text': '其他'
                }]
            }],
            "8103_F000_3": [{'type': 'label', 'text': '闹钟监控'}
                , {'datafld': 'ModeValue', 'type': 'timeSelect', 'addible': true}
                , {
                    'type': 'html',
                    'body': '<div style="font-size:12px;">1、每天最多只能设置四组闹钟。<br>2、设置闹钟模式成功后，设备会按照闹钟时间上报；</div>'
                }
            ],
            "8103_F000_4": [{'type': 'label', 'text': '追车模式'}
                , {'type': 'html', 'body': '<div style="font-size:12px;">1、追车模式总时长设置。<br></div>'}
                , {
                    'datafld': 'TotalTime', 'type': 'select',// 'placeholder': '总时长,单位秒',
                    'data': [{
                        'id': '1800', 'text': '30分钟'
                    }, {
                        'id': '3600', 'text': '1小时'
                    }, {
                        'id': '7200', 'text': '2小时'
                    }, {
                        'id': '10800', 'text': '3小时'
                    }, {
                        'id': '14400', 'text': '4小时'
                    }, {
                        'id': '18000', 'text': '5小时'
                    }, {
                        'id': '21600', 'text': '6小时'
                    }, {
                        'id': '25200', 'text': '7小时'
                    }, {
                        'id': '28800', 'text': '8小时'
                    }, {
                        'id': '32400', 'text': '9小时'
                    }, {
                        'id': '36000', 'text': '10小时'
                    }, {
                        'id': '39600', 'text': '11小时'
                    }, {
                        'id': '43200', 'text': '12小时'
                    }, {
                        'id': '46800', 'text': '13小时'
                    }, {
                        'id': '50400', 'text': '14小时'
                    }, {
                        'id': '54000', 'text': '15小时'
                    }, {
                        'id': '57600', 'text': '16小时'
                    }, {
                        'id': '61200', 'text': '17小时'
                    }, {
                        'id': '64800', 'text': '18小时'
                    }, {
                        'id': '68400', 'text': '19小时'
                    }, {
                        'id': '72000', 'text': '20小时'
                    }, {
                        'id': '75600', 'text': '21小时'
                    }, {
                        'id': '79200', 'text': '22小时'
                    }, {
                        'id': '82800', 'text': '23小时'
                    }, {
                        'id': '86400', 'text': '1天'
                    }, {
                        'id': '172800', 'text': '2天'
                    }, {
                        'id': '259200', 'text': '3天'
                    }, {
                        'id': '345600', 'text': '4天'
                    }, {
                        'id': '432000', 'text': '5天'
                    }, {
                        'id': '518400', 'text': '6天'
                    }, {
                        'id': '604800', 'text': '7天'
                    }]
                    //'validators': {
                    //    'notEmpty': {
                    //        'message': '总时长不能为空'
                    //    },
                    //    'digits': {
                    //        'message': '只能输入数字'
                    //    },
                    //    'between': {
                    //        'message': '取值范围255-16777215',
                    //        'enabled': true,
                    //        'min': 255,
                    //        'max': 16777215
                    //    }
                    //}
                }
                , {'type': 'html', 'body': '<div style="font-size:12px;">2、追车模式上报时间间隔设置。</div>'}
                , {
                    'datafld': 'UploadInterval', 'type': 'select', //'placeholder': '上报间隔,单位秒',
                    'data': [{
                        'id': '10', 'text': '10秒'
                    }, {
                        'id': '15', 'text': '15秒'
                    }, {
                        'id': '20', 'text': '20秒'
                    }, {
                        'id': '30', 'text': '30秒'
                    }, {
                        'id': '40', 'text': '40秒'
                    }, {
                        'id': '50', 'text': '50秒'
                    }, {
                        'id': '60', 'text': '1分钟'
                    }, {
                        'id': '120', 'text': '2分钟'
                    }, {
                        'id': '180', 'text': '3分钟'
                    }, {
                        'id': '240', 'text': '4分钟'
                    }]
                    //'validators': {
                    //    'notEmpty': {
                    //        'message': '上报间隔不能为空'
                    //    },
                    //    'digits': {
                    //        'message': '只能输入数字'
                    //    },
                    //    'between': {
                    //        'message': '取值范围10-255',
                    //        'enabled': true,
                    //        'min': 10,
                    //        'max': 255
                    //    }
                    //}
                }
            ],
            "8103_F000_2": [{'type': 'label', 'text': '智能上报间隔'}
                , {
                    'datafld': 'ModeValue', 'type': 'select',
                    'data': [{'id': '10', 'text': '10秒'}
                        , {'id': '15', 'text': '15秒'}
                        , {'id': '30', 'text': '30秒'}
                        , {'id': '60', 'text': '1分钟'}
                        , {'id': '120', 'text': '2分钟'}
                        , {'id': '300', 'text': '5分钟'}
                    ]
                }
                , {
                    'type': 'html',
                    'body': '<div style="font-size:12px;">1、智能监控状态下，上报时间间隔越长，终端越省电，但轨迹跳跃越大。上报间隔越小，轨迹越连贯，但功耗将增加；<br>2、非特殊情况，请不要缩小定位上传间隔；<br>3、设备运动按照此间隔上报，静止自动休眠；</div>'
                }
            ],
            "8103_F000_0": [{'type': 'label', 'text': '每天按照时间点上报'}
                , {'datafld': 'ModeValue', 'type': 'timeSelect'}
                , {
                    'type': 'html',
                    'body': '<div style="font-size:12px;">1、每天24小时定位一次，设置每天XX时:XX分：XX秒定时开机，设置成功后模式随之变成当前模式。<br>2、设备出厂默认为此监控模式</div>'
                }
            ],
            "8103_F000_1": [{'type': 'label', 'text': '定时监控间隔'}
                , {
                    'datafld': 'ModeValue', 'type': 'select',
                    'data': [
                        {'id': '1800', 'text': '30分钟'}
                        , {'id': '3600', 'text': '1小时'}
                        , {'id': '7200', 'text': '2小时'}
                        , {'id': '10800', 'text': '3小时'}
                        , {'id': '14400', 'text': '4小时'}
                        , {'id': '18000', 'text': '5小时'}
                        , {'id': '21600', 'text': '6小时'}
                        , {'id': '25200', 'text': '7小时'}
                        , {'id': '28800', 'text': '8小时'}
                        , {'id': '32400', 'text': '9小时'}
                        , {'id': '36000', 'text': '10小时'}
                        , {'id': '39600', 'text': '11小时'}
                        , {'id': '43200', 'text': '12小时'}
                        , {'id': '46800', 'text': '13小时'}
                        , {'id': '50400', 'text': '14小时'}
                        , {'id': '54000', 'text': '15小时'}
                        , {'id': '57600', 'text': '16小时'}
                        , {'id': '61200', 'text': '17小时'}
                        , {'id': '64800', 'text': '18小时'}
                        , {'id': '68400', 'text': '19小时'}
                        , {'id': '72000', 'text': '20小时'}
                        , {'id': '75600', 'text': '21小时'}
                        , {'id': '79200', 'text': '22小时'}
                        , {'id': '82800', 'text': '23小时'}
                        , {'id': '86400', 'text': '1天'}
                        , {'id': '172800', 'text': '2天'}
                        , {'id': '259200', 'text': '3天'}
                        , {'id': '345600', 'text': '4天'}
                        , {'id': '432000', 'text': '5天'}
                        , {'id': '518400', 'text': '6天'}
                        , {'id': '604800', 'text': '7天'}
                        , {'id': '691200', 'text': '8天'}
                        , {'id': '777600', 'text': '9天'}
                        , {'id': '864000', 'text': '10天'}
                    ]
                }
                , {
                    'type': 'html',
                    'body': '<div style="font-size:12px;">1、按照设置的间隔，终端唤醒上报；<br>2、缩短上报时间间隔虽然会提高定位频率，但会影响电池寿命；</div>'
                }
            ],
            "8103_F000": [{
                'datafld': 'WorkMode',
                'type': 'radio', 'data': [{
                    'id': 0, 'text': '定时监控模式（每天按时间点唤醒）',
                    'handler': function (obj) {
                        Cmdinfo._8103_F000({
                            'placeholder': '格式为：HH:mm:ss',
                            'notEmptyMsg': '定时模式的值不能为空',
                            'regexpOption': '^(([0-1][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9])$',
                            'digits': 0,
                            'between': 0,
                            'lessThan': 0,
                            'regexp': 1
                        });
                    }
                }, {
                    'id': 1, 'text': '正常监控模式（按正常模式汇报间隔唤醒）',
                    'handler': function (obj) {
                        Cmdinfo._8103_F000({
                            'placeholder': '最小间隔300秒，单位秒',
                            'notEmptyMsg': '正常模式的值不能为空',
                            'digits': 1,
                            'between': 0,
                            'lessThan': 0,
                            'regexp': 0
                        });
                    }
                }
                    , {
                        'id': 2, 'text': '智能监控模式（运动按连续模式汇报间隔上报，静止休眠）',
                        'handler': function (obj) {
                            Cmdinfo._8103_F000({
                                'placeholder': '取值范围10-300秒',
                                'notEmptyMsg': '智能监控的值不能为空',
                                'digits': 1,
                                'between': 1,
                                'lessThan': 0,
                                'regexp': 0
                            });
                        }
                    }
                    /*, {
                        'id': 3, 'text': '闹铃模式（每天根据闹铃时间唤醒，可多组）',
                        'handler': function (obj) {
                            Cmdinfo._8103_F000({
                                'placeholder': '格式为HH:mm:ss,HH:mm:ss,HH:mm:ss',
                                'notEmptyMsg': '闹铃模式的值不能为空',
                                'regexpOption': '^((([0-1][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9]),)*((([0-1][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9]))+$',
                                'digits': 0,
                                'between': 0,
                                'lessThan':0,
                                'regexp': 1
                            });
                        }
                    }*/
                ]
            },
                {
                    'datafld': 'ModeValue', 'type': 'text',
                    'placeholder': '格式为：HH:mm:ss',
                    'validators': {
                        'notEmpty': {
                            'message': '定时模式的值不能为空'
                        },
                        'regexp': {
                            'message': '格式不正确',
                            'regexp': '^(([0-1][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9])$'
                        },
                        'digits': {
                            'message': '只能输入数字',
                            'enabled': false
                        },
                        'lessThan': {
                            'message': '最大值为86400',
                            'value': 86400,
                            'enabled': false
                        },
                        'between': {
                            'message': '取值范围10-300',
                            'enabled': false,
                            'min': 10,
                            'max': 300
                        }
                    }
                }],
            //"8103_F001": [{
            //    'datafld': 'Voltage', 'type': 'text',
            //    'placeholder': '电池电压低电阀值,精确到小数点后一位',
            //    'validators': {
            //        'notEmpty': {
            //            'message': '电池电压低电阀值不能为空'
            //        },
            //        'regexp': {
            //            'message': '格式不正确',
            //            'regexp': '^\\d+(\\.\\d){0,1}$'
            //        }
            //    }
            //}],
            "8103_F001": [
                {'type': 'label', 'text': '电池低电量阀值'}
                , {
                    'datafld': 'Voltage', 'type': 'select',
                    'data': [{'id': '3.2', 'text': '10%'}
                        , {'id': '3.3', 'text': '25%'}
                        , {'id': '3.4', 'text': '40%'}
                        , {'id': '3.5', 'text': '55%'}
                        , {'id': '3.6', 'text': '75%'}
                        , {'id': '3.7', 'text': '95%'}
                    ]
                }
                , {
                    'type': 'html',
                    'body': '<div style="font-size:12px;">1、低电量报警阀值设置最低为10%；<br/>2、当电池电量低于设置电量后，设备上报低电量报警；<br/>3、低电量（10%）报警后，按照一天上报一次，约能上报30次；</div>'
                }
            ],
            "8103_F002": [{
                'datafld': 'Flag',
                'type': 'radio', 'data': [{
                    'id': 0, 'text': '关闭'
                }, {
                    'id': 1, 'text': '打开'
                }]
            }],
            "8103_F003": [{
                'datafld': 'Flag',
                'type': 'select', 'data': [{
                    'id': 0, 'text': '撤防'
                }, {
                    'id': 1, 'text': '设防'
                }, {
                    'id': 2, 'text': '自动设防'
                }]
            }],
            "8103_F004": [{
                'datafld': 'Flag',
                'type': 'radio', 'data': [{
                    'id': 0, 'text': '关闭'
                }, {
                    'id': 1, 'text': '打开'
                }]
            }],
            "8103_F005": [{
                'datafld': 'Flag',
                'type': 'radio', 'data': [{
                    'id': 0, 'text': '关闭'
                }, {
                    'id': 1, 'text': '打开'
                }]
            }],
            "8103_F006": [{
                'datafld': 'Flag',
                'type': 'radio', 'data': [{
                    'id': 0, 'text': '关闭'
                }, {
                    'id': 1, 'text': '打开'
                }]
            }],
            "8103_F007": [{
                'datafld': 'Flag',
                'type': 'text', 'placeholder': '位移报警距离,单位米', 'validators': {
                    'notEmpty': {
                        'message': '位移报警距离不能为空'
                    },
                    'digits': {
                        'message': '只能输入数字'
                    }
                }
            }],
            "8103_F008": [{
                'type': 'label', 'text': '聚集报警开关'
            }, {
                'datafld': 'Flag',
                'type': 'radio', 'data': [{
                    'id': 0, 'text': '关闭'
                }, {
                    'id': 1, 'text': '打开'
                }]
            }],
            "8105_1": [{
                'type': 'label', 'text': '远程升级'
            }],
            "8105_4": [{
                'type': 'label', 'text': '终端复位'
            }],
            "8105_5": [{
                'type': 'label', 'text': '终端恢复出厂设置'
            }],
            "8501": [{
                'datafld': 'Flag',
                'type': 'radio', 'data': [{
                    'id': 0, 'text': '断油断电'
                }, {
                    'id': 1, 'text': '恢复油电'
                }]
            }],
            "8104": [{
                'type': 'label', 'text': '查询终端参数'
            }],
            "8107": [{
                'type': 'label', 'text': '查询终端属性'
            }],
            "8201": [{
                'type': 'label', 'text': '查询位置信息'
            }],
            "8804": [
                {'type': 'label', 'text': '录音开始上报'}
                , {
                    'datafld': 'RecordingCommand', 'type': 'select',
                    'data': [{'id': '0', 'text': '停止录音'}
                        , {'id': '1', 'text': '开始录音'}
                    ]
                },
                {
                    'datafld': 'RecordingTime', 'type': 'text',
                    'placeholder': '录音时间,单位秒',
                    'validators': {
                        'notEmpty': {
                            'message': '录音时间不能为空'
                        },
                        'digits': {
                            'message': '只能输入数字'
                        },
                        'between': {
                            'message': '取值范围0-65535',
                            'enabled': true,
                            'min': 0,
                            'max': 65535
                        }
                    }
                },
                {
                    'datafld': 'SaveLogo', 'type': 'select',
                    'data': [{'id': '0', 'text': '实时上传'}
                        , {'id': '1', 'text': '保存'}
                    ]
                },
                {
                    'datafld': 'SamplingRate', 'type': 'select',
                    'data': [{'id': '0', 'text': '8K'}
                        , {'id': '1', 'text': '11K'}
                        , {'id': '2', 'text': '23K'}
                        , {'id': '3', 'text': '32K'}
                    ]
                }

            ],
            "S4": [
                {'type': 'label', 'text': '平台主动定位终端'},
                {
                    'datafld': 'LocationTimes', 'type': 'text',
                    'placeholder': '定位次数',
                    'validators': {
                        'digits': {
                            'message': '只能输入数字'
                        },
                        'between': {
                            'message': '取值范围0-65535',
                            'enabled': true,
                            'min': 0,
                            'max': 65535
                        }
                    }
                },
                {
                    'datafld': 'LocationTimeInterval', 'type': 'text',
                    'placeholder': '定位时间间隔',
                    'validators': {
                        'digits': {
                            'message': '只能输入数字'
                        },
                        'between': {
                            'message': '取值范围0-65535',
                            'enabled': true,
                            'min': 0,
                            'max': 65535
                        }
                    }
                },
                {
                    'type': 'html',
                    'body': '<div style="font-size:12px;">1、无需定次呼叫时，该值可设为0，或省略定位次数和定位时间间隔；<br>2、无需定次呼叫时，该值可设为0，或省略定位次数和定位时间间隔；<br>3、定位次数和定位时间间隔要么全部存在，要么全部省略；</div>'
                }
            ],
            "S5": [
                {'type': 'label', 'text': '查询终端参数'},
                {
                    'datafld': 'DeviceParam', 'type': 'select',
                    'data': [{'id': 'SOFTVERSION', 'text': '软件版本号'}
                        , {'id': 'GSM', 'text': 'GSM'}
                        , {'id': 'GPS', 'text': 'GPS'}
                        , {'id': 'VBAT', 'text': '电压'}
                        , {'id': 'LOGIN', 'text': '连接状态'}
                        , {'id': 'IMSI', 'text': 'IMSI'}
                        , {'id': 'IMEI', 'text': 'IMEI'}
                        , {'id': 'ACC', 'text': 'ACC'}
                        , {'id': 'DOMAIN', 'text': '平台地址和端口'}
                        , {'id': 'FREQ', 'text': '上报频率'}
                        , {'id': 'TRACE', 'text': '追踪'}
                        , {'id': 'PULSE', 'text': '心跳时间'}
                        , {'id': 'PHONE', 'text': 'SIM卡号码'}
                        , {'id': 'USER', 'text': '主号'}
                        , {'id': 'RADIUS', 'text': '越界报警距离'}
                        , {'id': 'RADIUSS', 'text': '越界报警短信'}
                        , {'id': 'RADIUSL', 'text': '越界报警是否传送到后台'}
                        , {'id': 'RADIUSLARM', 'text': '区域报警检测'}
                        , {'id': 'FENCE', 'text': '区域告警短信'}
                        , {'id': 'FENCES', 'text': '区域告警是否传送到后台'}
                        , {'id': 'VIBALARM', 'text': '震动告警检测'}
                        , {'id': 'VIB', 'text': '震动告警短信'}
                        , {'id': 'VIBS', 'text': '震动告警是否传送到后台'}
                        , {'id': 'VIBL', 'text': '震动告警感应灵敏度'}
                        , {'id': 'VIBCHK', 'text': '震动告警条件'}
                        , {'id': 'POF', 'text': '断电和低电告警'}
                        , {'id': 'POFL', 'text': '断电和低电告警短信'}
                        , {'id': 'POFS', 'text': '断电和低电发送到后台'}
                        , {'id': 'POFT', 'text': '开启断电告警时间'}
                        , {'id': 'LBV', 'text': '低电量告警阈值'}
                        , {'id': 'SLEEP', 'text': '休眠节电模式'}
                        , {'id': 'SLEEPT', 'text': '自动进入休眠模式时间'}
                        , {'id': 'WAKEUP', 'text': '休眠后自动唤醒'}
                        , {'id': 'VIBGPS', 'text': 'GPS过滤功能'}
                        , {'id': 'SMS', 'text': '短信激活'}
                        , {'id': 'ACCLT', 'text': '进入设防时间'}
                        , {'id': 'ACCLOCK', 'text': '根据ACC状态自动设防'}
                        , {'id': 'WAKEUPT', 'text': '休眠自动唤醒间隔时长'}
                        , {'id': 'TM1', 'text': '震动报警时间间隔'}
                        , {'id': 'TM7', 'text': '断电报警时间间隔'}
                        , {'id': 'POWER', 'text': '电动汽车ACC远程控制'}
                        , {'id': 'APNUSER', 'text': 'APN用户名'}
                        , {'id': 'APNPWD', 'text': 'APN密码'}
                        , {'id': 'SOFTSWITCH', 'text': 'SOFTSWITCH'}
                    ]
                }
            ],
            "S6": [
                {'type': 'label', 'text': '设置终端参数'},
                {
                    'datafld': 'DeviceParam', 'type': 'select',
                    'data': [
                        {'id': 'DOMAIN', 'text': '平台地址和端口'}
                        , {'id': 'FREQ', 'text': '上报频率'}
                        , {'id': 'TRACE', 'text': '追踪'}
                        , {'id': 'PULSE', 'text': '心跳时间'}
                        , {'id': 'PHONE', 'text': 'SIM卡号码'}
                        , {'id': 'USER', 'text': '主号'}
                        , {'id': 'RADIUS', 'text': '越界报警距离'}
                        , {'id': 'RADIUSS', 'text': '越界报警短信'}
                        , {'id': 'RADIUSL', 'text': '越界报警是否传送到后台'}
                        , {'id': 'RADIUSLARM', 'text': '区域报警检测'}
                        , {'id': 'FENCE', 'text': '区域告警短信'}
                        , {'id': 'FENCES', 'text': '区域告警是否传送到后台'}
                        , {'id': 'VIBALARM', 'text': '震动告警检测'}
                        , {'id': 'VIB', 'text': '震动告警短信'}
                        , {'id': 'VIBS', 'text': '震动告警是否传送到后台'}
                        , {'id': 'VIBL', 'text': '震动告警感应灵敏度'}
                        , {'id': 'VIBCHK', 'text': '震动告警条件'}
                        , {'id': 'POF', 'text': '断电和低电告警'}
                        , {'id': 'POFL', 'text': '断电和低电告警短信'}
                        , {'id': 'POFS', 'text': '断电和低电发送到后台'}
                        , {'id': 'POFT', 'text': '开启断电告警时间'}
                        , {'id': 'LBV', 'text': '低电量告警阈值'}
                        , {'id': 'SLEEP', 'text': '休眠节电模式'}
                        , {'id': 'SLEEPT', 'text': '自动进入休眠模式时间'}
                        , {'id': 'WAKEUP', 'text': '休眠后自动唤醒'}
                        , {'id': 'VIBGPS', 'text': 'GPS过滤功能'}
                        , {'id': 'SMS', 'text': '短信激活'}
                        , {'id': 'ACCLT', 'text': '进入设防时间'}
                        , {'id': 'ACCLOCK', 'text': '根据ACC状态自动设防'}
                        , {'id': 'WAKEUPT', 'text': '休眠自动唤醒间隔时长'}
                        , {'id': 'TM1', 'text': '震动报警时间间隔'}
                        , {'id': 'TM7', 'text': '断电报警时间间隔'}
                        , {'id': 'POWER', 'text': '电动汽车ACC远程控制'}
                        , {'id': 'APNUSER', 'text': 'APN用户名'}
                        , {'id': 'APNPWD', 'text': 'APN密码'}
                        , {'id': 'SOFTSWITCH', 'text': 'SOFTSWITCH'}
                    ]
                },
                {
                    'datafld': 'DeviceValue', 'type': 'text',
                    'placeholder': '参数值',
                    'validators': {
                        'notEmpty': {
                            'message': '参数值不能为空'
                        }
                    }
                }
            ],
            "S7": [
                {'type': 'label', 'text': '远程重启设备'}
            ],
            "S8": [
                {'type': 'label', 'text': '远程设防'},
                {'type': 'html', 'body': '<div style="font-size:12px;">平台向终端下发要求进行远程设防的指令</div>'}

            ],
            "S9": [
                {'type': 'label', 'text': '远程撤防'},
                {'type': 'html', 'body': '<div style="font-size:12px;">平台向终端下发要求进行远程撤防的指令</div>'}

            ],
            "S20": [
                {'type': 'label', 'text': '取消区域告警'},
                {'type': 'html', 'body': '<div style="font-size:12px;">平台向终端下发取消区域报警设置的指令</div>'}

            ]
        },
        formatSendPara: function (key, deviceNumber) {
            var _this = this;
            if (!Cmdinfo.dictionary[key]) {
                return false;
            }
            var outDiv = $('<div></div>').css({
                'padding': '10px 20px 10px 20px'
            }).addClass('JsonData').appendTo(_this);
            var setting = Cmdinfo.dictionary[key];
            $('<input />').attr('datafld', 'CmdKey').val(key).hide().appendTo(outDiv);
            $('<input />').attr('datafld', 'DeviceNumber').val(deviceNumber).hide().appendTo(outDiv);
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
    var CmdinfoObj = $('body').addModel({
        id: 'CmdModal',
        width: '700px',
        height: '400px',
        text: '下发'
    }).on('hide.bs.modal', function (obj) {
        CmdinfoObj.find('.sendBtn').hide();
        CmdinfoObj.find('.sendbody').empty();
    }).on('show.bs.modal', function (obj) {
        //var btnobj = $('.cmd_btn');
        var btnobj = $(obj.relatedTarget);
        //var _terminal_ControlObj = terminals_ControlObj.terminalObjs[btnobj.attr('key')];
        CmdinfoObj.find('.modal-title').html(btnobj.attr('name') + '指令下发');
        //CmdinfoObj.find('.modal-title').html(_terminal_ControlObj['Aliase'] + '下发');
        Cmdinfo._key = btnobj.attr('key');
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
                    otherParam: ['DeviceNumber', Cmdinfo._key],
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
                    'para': CmdinfoObj.find('.sendbody').toJson(),
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
        //var btnobj = $('.cmdRecord_btn');
        var btnobj = $(obj.relatedTarget);
        //var _terminal_ControlObj = terminals_ControlObj.terminalObjs[btnobj.attr('key')];
        CmdRecordInfoObj.find('.modal-title').html(btnobj.attr('name') + '指令记录');
        //CmdRecordInfoObj.find('.modal-title').html(_terminal_ControlObj['Aliase'] + '指令记录');
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
                    'title': '指令名'
                },
                'Operator': {
                    'title': '操作人'
                },
                'Message': {
                    'title': '内容'
                }
                ,
                'LastSendTime': {
                    'title': '发送时间',
                    'tableRender': function (data, type, full, meta, obj) {
                        return $.toDateTime(data);
                    }
                },
                'ResponseTime': {
                    'title': '响应时间',
                    'tableRender': function (data, type, full, meta, obj) {
                        return $.toDateTime(data);
                    }
                },
                'CmdStatusText': {
                    'title': '指令状态'
                },
                'ResultText': {
                    'title': '结果'
                }
            }
        }, {
            "rowId": 'CmdName',
            "lengthChange": false,
            'nofooter': true
        });
    });

    var DeviceDetailsObj = $('body').addModel({
        id: 'DeviceDetails',
        width: '400px',
        height: '600px',
        text: '信息',
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
                {title: '所属组', datafld: 'OrgName'},
                {title: '设备类型', datafld: 'ModelTypeText'},
                {title: '设备型号', datafld: 'ModelName'},
                {title: '设备号', datafld: 'DeviceNumber'},
                {title: 'Sim卡号', datafld: 'Simcard'},
                {title: '到期日期', datafld: 'ExpireDate'},
            ];
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
        DeviceDetailsObj.find('.modal-title').html(btnobj.attr('name') + '信息');
        $.updateSever({
            'para': {deviceNumber: btnobj.attr('key')},
            'url': URL + '/api/Device/ShowDeviceDetails',
            'success': function (result) {
                $.each(formatDatas, function (i, n) {
                    result[n.datafld] && (result[n.datafld] = n.format(result[n.datafld]));
                });
                DeviceDetailsObj.toBind(result);
            }
        });
    });

});