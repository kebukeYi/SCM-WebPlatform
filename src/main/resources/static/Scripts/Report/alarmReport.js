$(function () {
    flds['search'] && new dtc({
        columns: ['OrgName', 'DeviceName', 'DeviceNumber', 'AlarmTime', 'ReceiveTime', 'AlarmText', 'Content', 'LngLat'],
        name: 'Report',
        url: '/api/report/ShowAlarmList',
        showAddress: true,
        defaultparam: {
            StartTime: $.dateToJson(moment().startOf('day').toDate()),
            EndTime: $.dateToJson(moment().toDate())
        },
        validate: true,
        parameters: {
            'DeviceName': {
                'title': '设备名',
                'columnWidth': 160,
                'downloadWidth': 100
            },
            'DeviceNumber': {
                'title': '设备号',
                'columnWidth': 100,
            },
            'OrgName': {
                'title': '所属组',
                'columnWidth': 160
            },
            'AlarmTime': {
                'title': '报警时间',
                'columnWidth': 100,
                'tableRender': function (data, type, full, meta, obj) {
                    return $.toDateTime(data);
                }
            },
            'Content': {
                'title': '报警内容',
                'columnWidth': 160
            },
            'AlarmText': {
                'title': '报警类型',
                'columnWidth': 80
            },
            'LngLat': {
                'title': '地址',
                'columnWidth': 200,
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
            },
            {
                'category': 'select',
                'datafld': 'AlarmType',
                'data': alarm.allAlarm
            },
            {
                'category': 'select',
                'datafld': 'ModelType',
                'data': [
                    {'text': '设备类型', 'id': ''},
                    {'text': '有线', 'id': 0},
                    {'text': '无线', 'id': 1},
                    {'text': '无线不充电', 'id': 10},
                    {'text': '无线可充电', 'id': 11}
                ]
            },
            {
                'category': 'rangedate',
                'placeholder': '报警时间',
                'width': '250px',
                'control_option': {
                    "startDate": $.shortDateToJson(moment().startOf('day').toDate()),
                    "endDate": $.dateToJson(moment().toDate()),
                    timePicker: true,
                    timePickerSeconds: false
                }
            },
            {'category': 'text', 'width': '180px', 'placeholder': '设备号'}
        ]
    }, {});
});