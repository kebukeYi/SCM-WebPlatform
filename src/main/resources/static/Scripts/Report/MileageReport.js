$(function () {
    flds['search'] && new dtc({
        columns: ['OrgName', 'DeviceName', "ModelTypeText", 'DeviceNumber', 'StrokeDate', 'Mileage'],
        name: 'Report',
        url: '/api/report/MileageReportList',
        showAddress: true,
        defaultparam: {
            StartTime: $.dateToJson(moment().subtract('days', 1).startOf('day').toDate()),
            EndTime: $.dateToJson(moment().subtract('days', 1).toDate())
        },
        validate: true,
        parameters: {
            'DeviceName': {
                'title': '设备名/车牌号',
                'downloadWidth': 80
            },
            'DeviceNumber': {
                'title': '设备号'
            },
            'ModelTypeText': {
                'title': '设备类型',

            },
            'OrgName': {
                'title': '所属组'
            },
            'Mileage': {
                'title': '里程(km)',
            },
            'StrokeDate': {
                'title': '日期',
                'tableRender': function (data, type, full, meta, obj) {
                    return $.toDate(data);
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
                'datafld': 'ModelType',
                'data': [
                    {'text': '设备类型', 'id': ''},
                    {'text': '有线', 'id': 0},
                    {'text': '无线', 'id': 1},
                    {'text': '无线不充电', 'id': 10},
                    {'text': '无线可充电', 'id': 11}
                ]
            }, {
                'category': 'rangedate',
                'placeholder': '时间',
                'readonly': true,
                'width': '200px',
                'control_option': {
                    "startDate": $.shortDateToJson(moment().subtract('days', 1).startOf('day').toDate()),
                    "endDate": $.shortDateToJson(moment().subtract('days', 1).toDate())
                }
            },
            {
                'category': 'text',
                'width': '180px',
                'placeholder': '设备号'
            }
        ]
    }, {});
});