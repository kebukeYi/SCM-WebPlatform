$(function () {
    flds['search'] && new dtc({
        columns: ['OrgName', 'DeviceName', 'DeviceNumber', 'StartTime', 'EndTime', 'IntervalText', 'LngLat'],
        name: 'Report',
        url: '/api/report/ShowStopList',
        showAddress: true,
        defaultparam: {
            StartTime: $.dateToJson(moment().startOf('day').toDate()),
            EndTime: $.dateToJson(moment().toDate())
        },
        handleBefore: function (param) {

        },
        validate: true,
        parameters: {
            'DeviceNumber': {
                'title': '设备号',
                'columnWidth': 100,
                'downloadWidth': 40
            },
            'DeviceName': {
                'title': '设备名/车牌号',
                'columnWidth': 160,
                'downloadWidth': 40
            },
            'OrgName': {
                'title': '所属组',
                'columnWidth': 160,
                'downloadWidth': 42
            },

            'EndTime': {
                'title': '开始停留时间',
                'columnWidth': 100,
                'downloadWidth': 30,
                'tableRender': function (data, type, full, meta, obj) {
                    return $.toDateTime(data);
                }
            },
            'StartTime': {
                'title': '结束停留时间',
                'columnWidth': 100,
                'downloadWidth': 30,
                'tableRender': function (data, type, full, meta, obj) {
                    return $.toDateTime(data);
                }
            },
            'IntervalText': {
                'title': '停留时长/分种',
                'columnWidth': 80,
                'downloadWidth': 30
            },
            'LngLat': {
                'title': '地址',
                'columnWidth': 400,
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
                'datafld': 'ModelType',
                'data': [
                    {'text': '设备类型', 'id': ''},
                    {'text': '有线', 'id': 0},
                    {'text': '无线', 'id': 1},
                    {'text': '无线不充电', 'id': 10},
                    {'text': '无线可充电', 'id': 11}
                ]
            },
            // {
            //     'category': 'rangedate',
            //     'placeholder': '开始停留时间',
            //     'readonly': true,
            //     'width': '200px',
            //     'control_option': {
            //         "startDate": $.shortDateToJson(moment().startOf('day').toDate()),
            //         "endDate": $.shortDateToJson(moment().toDate())
            //     }
            // },
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
                    {'id': '', 'text': '停留时长'},
                    {'id': '10', 'text': '10分钟以上'},
                    {'id': '30', 'text': '30分钟以上'},
                    {'id': '60', 'text': '1小时以上'},
                    {'id': '120', 'text': '2小时以上'},
                    {'id': '300', 'text': '5小时以上'},
                    {'id': '1440', 'text': '1天以上'},
                    {'id': '2880', 'text': '2天以上'},
                    {'id': '10080', 'text': '7天以上'},
                    // { 'id': '-1', 'text': '自定义' }
                ]
            }, {
                'category': 'rangeNumber',
                'datafld': 'Interval',
                'id': 'rangeDiv',
                'unit': '分钟',
                'hidden': true
            },
            {
                'category': 'text',
                'width': '200px',
                'placeholder': '设备号'
            }
        ]
    }, {});
});