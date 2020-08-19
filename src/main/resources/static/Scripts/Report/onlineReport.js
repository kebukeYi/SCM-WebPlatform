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
        columns: ['OrgName', 'DeviceName', 'DeviceNumber', 'ModelName',
            'ModelTypeText', 'OffLineSecond', 'LastLocationTime', 'LngLat'],
        name: 'Report',
        url: '/api/report/ShowOnlineList',
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
                'downloadWidth': 20
            },
            'OrgName': {
                'title': '所属组',
                'columnWidth': 160,
                'downloadWidth':30
            },
            'ModelName': {
                'title': '设备型号',
                'columnWidth': 80,
            },
            'ModelTypeText': {
                'title': '设备类型',
                'columnWidth': 80
            },
            'ReceiveTime': {
                'title': '信号时间',
                'columnWidth': 100,
                'tableRender': function (data, type, full, meta, obj) {
                    return $.toDateTime(data);
                },
                'downloadWidth':30
            },
            'LastLocationTime': {
                'title': '最后定位时间',
                'columnWidth': 100,
                'tableRender': function (data, type, full, meta, obj) {
                    return $.toDateTime(data);
                },
                'downloadWidth': 30
            },
            'LngLat': {
                'title': '最后定位地址',
                'columnWidth': 300,
                'tableRender': function (data, type, full, meta, obj) {
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
                    { 'text': '设备类型', 'id': '' },
                    { 'text': '有线', 'id': 0 },
                    { 'text': '无线', 'id': 1 },
                    { 'text': '无线不充电', 'id': 10 },
                    { 'text': '无线可充电', 'id': 11 }
                ]
            }, {
                'category': 'text',
                'width': '180px',
                'placeholder': '设备号'
            }
        ]
    }, {});
});