$(function () {
    var model_List = [{
        'text': '所有型号', 'id': ''
    }];
    $.updateSever({
        'url': '/api/Device/ShowModelList',
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
        columns: ['OrgName', 'DeviceName', 'DeviceNumber', 'ModelName', 'ModelTypeText', 'OffLineSecond', 'LastLocationTime', 'LngLat'],
        name: 'Report',
        url: '/api/report/ShowOfflineList',
        validate: true,
        showAddress: true,
        parameters: {
            'DeviceName': {
                'title': '设备名/车牌号',
                'columnWidth': 160,
                'downloadWidth': 15
            },
            'DeviceNumber': {
                'title': '设备号',
                'columnWidth': 100,
                'downloadWidth': 30
            },
            'OrgName': {
                'title': '所属组',
                'columnWidth': 160,
                'downloadWidth': 30
            },
            'ModelName': {
                'title': '设备型号',
                'columnWidth': 80
            },
            'ModelTypeText': {
                'title': '设备类型',
                'columnWidth': 80
            },
            'OffLineSecond': {
                'title': '离线时长',
                'columnWidth': 90,
                'downloadWidth': 15
            },
            'LastLocationTime': {
                'title': '最后定位时间',
                'columnWidth': 100,
                'tableRender': function (data, type, full, meta, obj) {
                    console.log(data);
                    console.log(full.OfflineTime);
                    if (data == null || data == '' || data == undefined) {
                        data = full.OfflineTime;
                    }

                    return $.toDateTime(data);
                },
                'downloadWidth': 15
            },
            'LngLat': {
                'title': '最后定位地址',
                'columnWidth': 300,
                'tableRender': function (data, type, full, meta, obj) {
                    console.log(data);
                    if (data == null || data == '' || data == undefined) {
                        data = '121.432977086379,31.1975033300686';
                    }
                    return obj.getAddressByLngLat(data);
                }

            }
        },
        searchs: [
            {
                'category': 'treeSelect',
                'isWithChildren': true,
                'isNoEmpty': true,
                'datafld': 'OrgId'
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
                'category': 'select',
                'datafld': 'Days',
                'handler': function (id, text) {
                    var _this = this;
                    var textobj = $('#rangeDiv', _this);
                    textobj.val(id);
                    text == '自定义' ? textobj.show() : textobj.hide();
                },
                'data': [
                    {'id': '', 'text': '所有离线时间'},
                    {'id': '1', 'text': '离线1天以内'},
                    {'id': '2', 'text': '离线2天以内'},
                    {'id': '3', 'text': '离线3天以内'},
                    {'id': '7', 'text': '离线7天以内'},
                    {'id': '15', 'text': '离线15天以内'},
                    // { 'id': '-1', 'text': '自定义' }
                ]
            }, {
                'category': 'rangeNumber',
                'datafld': 'Days',
                'id': 'rangeDiv',
                'min': 1,
                'max': 3,
                'hidden': true
            },
            {
                'category': 'text',
                'width': '180px',
                'placeholder': '设备号'
            }
        ]
    }, {});
});