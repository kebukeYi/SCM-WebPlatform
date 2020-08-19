$(function () {
    var dt = null;
    flds['search'] && (dt = new dtc({
        columns: ['OrgName', 'UserName', 'RealName', 'RoleName', 'Tel', 'ExpireDate', 'Email', 'CreateTime', 'EnabledText', 'Remark'],
        name: 'User',
        validate: true,
        checkbox_data: "Id",
        parameters: {
            'CreateTime': {
                'title': '创建时间',
                'tableRender': function (data, type, full, meta, obj) {
                    return $.toDateTime(data);
                }
            },
            'Email': {
                'title': 'Email',
                'type': 'text',
                'validators': {
                    'emailAddress': {
                        'message': '格式不正确'
                    }
                }
            },
            'Enabled': {
                'title': '状态',
                'type': 'checkbox',
                'validators': {}
            },
            'EnabledText': {
                'title': '状态'
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
                'validators': {}
            },
            'OrgnizationId': {
                'title': '所属组',
                'type': 'treeSelect',
                'isNoEmpty': true
            },
            'OrgName': {
                'title': '所属组'
            },
            'RealName': {
                'title': '姓名',
                'type': 'text',
                'validators': {
                    'notEmpty': {
                        'message': '姓名不能为空'
                    }
                }
            },
            'Remark': {
                'title': '备注',
                'type': 'textarea',
                'validators': {}
            },
            'Tel': {
                'title': '电话',
                'type': 'text'
            },
            'UserName': {
                'title': '用户名',
                'type': 'text',
                'validators': {
                    'notEmpty': {
                        'message': '用户名不能为空'
                    },
                    'remote': {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                        url: '/api/User/CheckUserNameOnly',//验证地址
                        message: '用户已存在',//提示消息
                        name: 'username',
                        delay: 1000,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'post'//请求方式
                    }
                }
            },
            'Password': {
                'title': '密码',
                'type': 'text',
                'validators': {
                    'notEmpty': {
                        'message': '密码不能为空'
                    }
                }
            },
            'RoleName': {
                'title': '角色名'
            },
            'RoleId': {
                'title': '角色',
                'type': 'select',
                'remote': {
                    'url': '/api/Permission/ShowRoles',
                    'firstData': {'text': '不关联', 'id': ''},
                    'text': 'RoleName',
                    'id': 'Id'
                }
            }
        },
        buttons: [
            {
                'text': '增加',
                'id': 'add',
                'data': ['OrgnizationId', 'UserName', 'Password', 'RealName', 'RoleId', 'Tel', 'Email', 'ExpireDate', 'Enabled', 'Remark']
            },
            {
                'text': '删除',
                'id': 'del',
                'model_body': '确定要删除？'
            },
            {
                'text': '修改',
                'id': 'edit',
                'data': ['OrgnizationId', 'RoleId', 'RealName', 'Tel', 'Email', 'ExpireDate', 'Enabled', 'Remark']
            },
            {
                'text': '重置密码',
                'id': 'resetPassword',
                'data': ['Password'],
                'button_Obj': {
                    'isMultiple': true,
                    'handle': function (n, data, closeModel) {
                        var _this = this;
                        var id = $(".checkchild:checked", _this.tableObj).val();
                        var rowData = _this._dataTable.row('#' + id).data();
                        data['username'] = rowData.UserName;
                        $.updateSever({
                            'para': data,
                            'url': '/api/User/ResetPassword',
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
                'control_option': {
                    autoUpdateInput: false
                }
            },
            {'category': 'text', 'placeholder': '请输入用户名'},
            {
                'category': 'select',
                'datafld': 'Enabled',
                'data': [
                    {'text': '所有状态', 'id': ''},
                    {'text': '启用', 'id': true},
                    {'text': '禁用', 'id': false}
                ]
            }
        ]
    }, {}));
});