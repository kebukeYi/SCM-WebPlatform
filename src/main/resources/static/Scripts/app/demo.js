function dtc(n, t) {
    var i = this;
    i.name = n.name;
    i.validate = !!n.validate;
    i.table_options = {
        processing: !0,
        serverSide: !0,
        orderMulti: !1,
        ordering: !1,
        order: [],
        searching: !1,
        dom: '<"Add_Div">rt<"pageOutDiv"<"col-sm-5"<"leftDiv"i><"pageLength"l>><"col-sm-7"p>>',
        lengthChange: !0,
        pageLength: 15,
        lengthMenu: [15, 30, 60, 100, 300],
        columns: [],
        rowId: "Id",
        language: {
            sProcessing: "处理中...",
            sLengthMenu: "每页 _MENU_ 项",
            sZeroRecords: "没有匹配结果",
            sInfo: "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
            sInfoEmpty: "当前显示第 0 至 0 项，共 0 项",
            sInfoFiltered: "(由 _MAX_ 项结果过滤)",
            sInfoPostFix: "",
            sSearch: "搜索:",
            sUrl: "",
            sEmptyTable: "表中数据为空",
            sLoadingRecords: "载入中...",
            sInfoThousands: ",",
            oPaginate: {sFirst: "首页", sPrevious: "上页", sNext: "下页", sLast: "末页", sJump: "跳转"},
            oAria: {sSortAscending: ": 以升序排列此列", sSortDescending: ": 以降序排列此列"}
        }
    };
    $.extend(i.table_options, t);
    i.options = {
        pid: "table_Div",
        tableClass: ["table", "table-striped", "table-bordered", "dataTable", "no-footer", "table-hover"],
        checkbox_data: null,
        parameters: {},
        excludeColumns: [],
        columns: []
    };
    $.extend(i.options, n);
    i._init()
}

function button_modal(n) {
    var t = this;
    if (t.options = {}, $.extend(t.options, t.default_options, n), n.obj) {
        t.obj = n.obj;
        t.obj.on("hidden.bs.modal", function () {
            t._clear()
        });
        return
    }
    t._init()
}

var selectGroupZTree, getObjFromCategory;
jQuery.fn.extend({
    daterangepickerplus: function (n) {
        var i = this, t = {
            singleDatePicker: !0,
            showDropdowns: !0,
            minDate: "1900-01-01",
            maxDate: "2199-12-31",
            ranges: {
                // "今日": [moment().startOf("day"), moment()],
                // "昨日": [moment().subtract("days", 1).startOf("day"), moment().subtract("days", 1).endOf("day")],
                // "最近24小时": [moment().subtract("hour", 24), moment()],
                "最近3日": [moment().subtract("days", 3), moment()],
                "最近7日": [moment().subtract("days", 6), moment()],
                "最近30日": [moment().subtract("days", 29), moment()],
                "本月": [moment().startOf("month"), moment().endOf("month")],
                "上个月": [moment().subtract(1, "month").startOf("month"), moment().subtract(1, "month").endOf("month")]
            },
            locale: {
                direction: "ltr",
                format: "YYYY-MM-DD",
                separator: " --> ",
                applyLabel: "确定",
                cancelLabel: "取消",
                fromLabel: "从",
                toLabel: "到",
                customRangeLabel: "自定义",
                daysOfWeek: ["日", "一", "二", "三", "四", "五", "六"],
                monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                firstDay: 1
            }
        }, r;
        if ($.extend(!0, t, n), n.timePicker || delete t.ranges["最近24小时"], r = $(i).daterangepicker(t), !t.autoUpdateInput) {
            $(i).on("apply.daterangepicker", function (n, i) {
                t.singleDatePicker ? $(this).val(i.startDate.format(t.locale.format)) : $(this).val(i.startDate.format(t.locale.format) + t.locale.separator + i.endDate.format(t.locale.format))
            });
            $(i).on("cancel.daterangepicker", function () {
                $(this).val("")
            })
        }
        return $(i).data("reSet", function () {
            var n = t.startDate || new Date, r;
            $(i).data("daterangepicker").setStartDate(n);
            ($(i).hasClass("range_date") || $(i).hasClass("range_date_time") || $(i).hasClass("range_date_time_second")) && (r = n.setDate(d.getDate() + 1), $(i).data("daterangepicker").setEndDate(r))
        }), r
    }, bindDaterangepicker: function (n, t) {
        var i = n || t;
        i && (i = i.replace(/T| | --> |:/g, "-").split("-"), $(this).hasClass("date") ? $(this).data("daterangepicker").setStartDate(i[0] + "-" + i[1] + "-" + i[2]) : $(this).hasClass("date_time") ? $(this).data("daterangepicker").setStartDate(i[0] + "-" + i[1] + "-" + i[2] + " " + i[3] + ":" + i[4]) : $(this).hasClass("date_time_second") ? $(this).data("daterangepicker").setStartDate(i[0] + "-" + i[1] + "-" + i[2] + " " + i[3] + ":" + i[4] + ":" + i[5]) : $(this).hasClass("range_date") ? ($(this).data("daterangepicker").setStartDate(i[0] + "-" + i[1] + "-" + i[2]), $(this).data("daterangepicker").setEndDate(i[4] + "-" + i[5] + "-" + i[6])) : $(this).hasClass("range_date_time") ? ($(this).data("daterangepicker").setStartDate(i[0] + "-" + i[1] + "-" + i[2] + " " + i[3] + ":" + i[4]), $(this).data("daterangepicker").setEndDate(i[5] + "-" + i[6] + "-" + i[7] + " " + i[8] + ":" + i[9])) : $(this).hasClass("range_date_time_second") && ($(this).data("daterangepicker").setStartDate(i[0] + "-" + i[1] + "-" + i[2] + " " + i[3] + ":" + i[4] + ":" + i[5]), $(this).data("daterangepicker").setEndDate(i[6] + "-" + i[7] + "-" + i[8] + " " + i[9] + ":" + i[10] + ":" + i[11])))
    }, toJson: function () {
        var n = {};
        return $("*[datafld]", this).each(function () {
            var t = $(this).attr("datafld"), u, f, r, i;
            switch (this.tagName) {
                case"SPAN":
                    n[t] = $.trim($(this).text());
                    break;
                case"SELECT":
                    n[t] = $(this)[0].value;
                    break;
                case"DIV":
                    $(this).hasClass("radio") ? n[t] = $('input[type="radio"][name="' + t + '_radio"]:checked', $(this)).val() : $(this).hasClass("multiple_checkbox") ? (u = $(this), f = u.attr("mc_id"), n[t] = [], $(".mult_chk", u).each(function (i, r) {
                        var u = {};
                        u[f] = $(r).val();
                        u.IsChecked = $(r).prop("checked");
                        u.obj = $(r).data("obj_value");
                        n[t].push(u)
                    })) : $(this).hasClass("timeSelect") && (n[t] ? n[t] += "," + $("select", this).eq(0).val() + ":" + $("select", this).eq(1).val() + ":" + $("select", this).eq(2).val() : n[t] = $("select", this).eq(0).val() + ":" + $("select", this).eq(1).val() + ":" + $("select", this).eq(2).val());
                    break;
                case"INPUT":
                    $(this).attr("type") == "checkbox" ? n[t] = this.checked : $(this).hasClass("date_control") ? this.value.indexOf("-->") >= 0 ? (r = this.value.split("-->"), i = t.split("-->"), n[i[0]] = r[0] ? $.trim(r[0]) : "", n[i[1]] = r[1] ? $.trim(r[1]) : "", $(this).hasClass("range_date") && n[i[1]] && (n[i[1]] = n[i[1]] + " 23:59:59")) : n[t] = $.trim(this.value) : this.value ? n[t] = $.trim(this.value) : $(this).attr("showNull") && (n[t] = "");
                    break;
                default:
                    this.value && (n[t] = $.trim(this.value))
            }
        }), n
    }, toBind: function (n) {
        $("*[datafld]", this).each(function () {
            var u = $(this).attr("datafld"), t = typeof n[u] == "undefined" ? "" : n[u], i, r, f;
            switch (this.tagName) {
                case"SPAN":
                    $(this).text(t || "");
                    break;
                case"SELECT":
                    t || (i = $(this)[0].options, i.length > 0 && (t = i[0].value));
                    $(this).val(t);
                    $(this).trigger("change");
                    break;
                case"INPUT":
                    if ($(this).attr("type") == "checkbox") this.checked = typeof t == "boolean" ? t : (t == "false" ? !1 : !0) || !1; else if ($(this).hasClass("selectzTree")) {
                        if ($(this).data("selectzTree").setValue(t), $(this).data("selectzTree").onChange) $(this).data("selectzTree").onChange(t, m)
                    } else $(this).hasClass("date_control") ? $(this).bindDaterangepicker(t) : $(this).val(t);
                    break;
                case"DIV":
                    $(this).hasClass("multiple_checkbox") && (r = $(this), f = r.attr("mc_id"), t && $.each(t, function (n, t) {
                        $('.mult_chk[value="' + t[f] + '"]', r).prop("checked", !0)
                    }));
                    break;
                default:
                    $(this).val(t)
            }
        })
    }, addModel: function (n) {
        var u = this,
            t = $("<div><\/div>").addClass("modal fade").attr("id", n.id).attr("tabindex", "-1").attr("data-backdrop", "static").attr("role", "dialog").attr("aria-labelledby", n.id + "Label").attr("hidden", "true").appendTo($(u)),
            i = $("<div><\/div>").addClass("modal-content").appendTo($("<div><\/div>").addClass("modal-dialog").appendTo(t)),
            r, f;
        return n.width && t.find(".modal-dialog").css({width: n.width}), n.height && t.find(".modal-dialog").css({height: n.height}), r = $("<div><\/div>").addClass("modal-header").appendTo(i), $('<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;<\/span><span class="sr-only">Close<\/span><\/button>').appendTo(r), $('<h4 class="modal-title">' + n.text + "<\/h4>").attr("id", n.id + "Label").appendTo(r), f = $("<div>" + (n.body || "") + "<\/div>").addClass("modal-body form-horizontal").appendTo(i), n.nofooter || $("<div>" + (n.footer || "") + "<\/div>").addClass("modal-footer").appendTo(i), t
    }
});
jQuery.extend({
    formatJsDateTime: function (n) {
        if (n) {
            var t = n.replace(/T| |\.|:/g, "-").split("-");
            return new Date(t[0], parseInt(t[1]) - 1, t[2], t[3], t[4], t[5])
        }
        return n
    }, dateToJson: function (n) {
        if (n) {
            var e = n.getFullYear(), t = n.getMonth() + 1, i = n.getDate(), r = n.getHours(), u = n.getMinutes(),
                f = n.getSeconds();
            return e + "-" + (t < 10 ? "0" + t : t) + "-" + (i < 10 ? "0" + i : i) + " " + (r < 10 ? "0" + r : r) + ":" + (u < 10 ? "0" + u : u) + ":" + (f < 10 ? "0" + f : f)
        }
        return n
    }, shortDateToJson: function (n) {
        if (n) {
            var r = n.getFullYear(), t = n.getMonth() + 1, i = n.getDate();
            return r + "-" + (t < 10 ? "0" + t : t) + "-" + (i < 10 ? "0" + i : i)
        }
        return n
    }, toDate: function (n) {
        if (n) {
            var t = n.replace(/T| |:/g, "-").split("-");
            return t[0] + "-" + t[1] + "-" + t[2]
        }
        return ""
    }, toDateTime: function (n) {
        if (n) {
            var t = n.replace(/T| |\.|:/g, "-").split("-");
            return t[0] + "-" + t[1] + "-" + t[2] + " " + t[3] + ":" + t[4] + ":" + t[5]
        }
        return ""
    }, getNextMonth: function (n) {
        typeof n == undefined && (n = 1);
        var t = new Date;
        return t.setMonth(t.getMonth() + n), t
    }, getNextYear: function () {
        var n = new Date;
        return n.setFullYear(n.getFullYear() + 1), n
    }, showLoading: function (n) {
        $(".loading_mask").length == 0 && ($('<div class="loading_mask mask_div"><\/div>').appendTo($("body")), $('<div class="loading_mask shining_loading">' + (n || "加载中。。。") + "<\/div>").appendTo($("body")));
        n && $(".shining_loading").html(n);
        $(".loading_mask").show()
    }, hideLoading: function () {
        $(".loading_mask").hide()
    }, updateSever: function (n) {
        var t = {
            url: "",
            async: !0,
            dataType: "json",
            type: "post",
            contentType: "application/json",
            success: function () {
            },
            error: function () {
            }
        };
        if ($.extend(t, n), !t.url) {
            $.alert({title: "提示", content: "参数有误！"});
            return
        }
        t.type == "post" && (t.data = t.para && JSON.stringify(t.para) || "");
        delete t.para;
        t.success = function (t, i) {
            t.Status == 1 ? n.success(t.Result, i) : ($.hideLoading(), $.alert({
                title: "提示",
                content: t.ErrorMessage
            }), n.error && n.error(t, i))
        };
        t.error = function (t, i, r) {
            $.hideLoading();
            n.error ? n.error(t, i, r) : $.alert({title: "提示", content: "网络错误!"})
        };
        $.ajax(t)
    }
});
var statusInfo = function () {
    this.allStatus = {
        Disassembly: "已拆卸",
        NotDisassembly: "未拆卸",
        Saving: "省电",
        Defence: "设防",
        ACCON: "ACC开",
        ACCOFF: "ACC关",
        "OilON ": "油路开",
        OilOFF: "油路关",
        EleON: "开电",
        EleOFF: "关电",
        EleConnected: "连接外部电源",
        EleDisconnected: "没有连接外部电源",
        Moving: "运动",
        Stopping: "静止",
        Standby: "待机",
        OEConnected: "油电接通",
        OEBreak: "油电断开",
        Sleeping: "休眠",
        Waking: "唤醒",
        PowerOff: "关机",
        Normal: "正常监控",
        ContinueLocation: "连续定位",
        AutoMonitor: "智能监控",
        FixedLocation: "定时定位",
        RecordingON: "自动录音开",
        VoiceControlON: "声控回拨开"
    };
    this.getStatusValue = function (n, t) {
        for (var f = this, e = n.split(","), r = "", i, u = 0; u < e.length; u++) i = $.trim(e[u]), f.allStatus[i] && (i == "Normal" || i == "ContinueLocation" || i == "AutoMonitor" || i == "FixedLocation" ? r = "/" + f.allStatus[i] + r : r += "/" + f.allStatus[i]);
        return t && t.indexOf("DropOff") > -1 && (r += "/脱落"), r
    }
}, statusModel = new statusInfo, alarmInfo = function () {
    this.allAlarm = [
        {id: '', text: "所有报警类型"},
        {id: "1", text: "紧急报警"},
        {id: "PowerCut", text: "断电报警"},
        {id: "2", text: "振动报警"},
        {id: "3", text: "进围栏报警"},
        {id: "Out", text: "出围栏报警"},
        {id: "InOut", text: "进出围栏报警"},
        {id: "OverSpeed", text: "超速报警"},
        {id: "Move", text: "位移报警"},
        {id: "Low", text: "低电报警"},
        {id: "Recovery", text: "供电恢复报警"},
        {id: "BreakDown", text: "强拆报警"},
        {id: "DropOff", text: "脱落报警"},
        {id: "Collect", text: "聚集报警"},
        {id: "DangerPoint", text: "风险点报警"}];
    var n = {};
    $.each(this.allAlarm, function (t, i) {
        i.id && (n[i.id] = i.text)
    });
    this.getName = function (t) {
        return n[t] || t
    }
}, alarm = new alarmInfo;
dtc.prototype = {
    _init: function () {
        var n = this, t;
        n.tableObj = $("<table><\/table>").appendTo($("#" + n.options.pid));
        $.each(n.options.tableClass, function (t, i) {
            n.tableObj.addClass(i)
        });
        n.table_options.columns.length == 0 && n._init_columns();
        n.table_options.ajax = n.options.ajax ? function (t, i, r) {
            n.options.ajax.call(n, t, i, r)
        } : function (t, i) {
            var u, r;
            if (n.isClear) {
                u = {};
                u.draw = t.draw;
                u.recordsTotal = 0;
                u.recordsFiltered = 0;
                u.data = [];
                i(u);
                return
            }
            r = {};
            t.order.length > 0 && (r.OrderBys = [], $.each(t.order, function (t, i) {
                var u = n.table_options.columns[i.column];
                r.OrderBys.push({Key: u.orderKey || u.data, Value: i.dir})
            }));
            n.options.isCreateByOneKey && (r.isCreateByOneKey = n.options.isCreateByOneKey);
            r.limit = t.length > 0 ? t.length : 100;
            r.start = t.start;
            r.page = t.start / t.length + 1;
            $.extend(r, n.searchDiv ? n.searchDiv.toJson() : n.options.defaultparam ? n.options.defaultparam : {});
            n.options.handleBefore && n.options.handleBefore(r);
            $.updateSever({
                para: r, url: n.options.url || "/api/" + n.name + "/Search", success: function (n) {
                    var r = {};
                    r.draw = t.draw;
                    r.recordsTotal = n.total;
                    r.recordsFiltered = n.total;
                    r.data = n.data;
                    i(r)
                }
            })
        };
        n._dataTable = n.tableObj.on("draw.dt", function () {
            n.options.loadAfter && n.options.loadAfter.call(n);
            n._init_checkDo()
        }).DataTable(n.table_options);
        n.tableObj.delegate("tr", "click", function () {
            var t = $(this), r, u, i, f;
            n.table_options.selecthandle && (r = t.attr("id"), r && (u = n._dataTable.row("#" + r).data(), t.hasClass("selected") ? (t.removeClass("selected"), n.table_options.selecthandle(!1, u)) : (n.tableObj.find("tr.selected").removeClass("selected"), t.addClass("selected"), n.table_options.selecthandle(!0, u))));
            i = t.find('[type="checkbox"].checkchild');
            i.length > 0 && (f = i.prop("checked"), i.prop("checked", !f), n._init_checkDo())
        });
        t = $(".Add_Div", $("#" + n.options.pid));
        n.options.searchs && n._init_search(t);
        n.options.buttons && n._init_button(t);
        t.css({height: "100%"});
        n.options.showAddress && n.tableObj.delegate(".show_address", "click", function () {
            var n = $(this), t = "";
            n.attr("lngLat") ? t = {lngLat: n.attr("lngLat")} : n.attr("lng") && n.attr("lat") && (t = {lngLat: n.attr("lng") + "," + n.attr("lat")});
            t ? $.updateSever({
                url: "/api/monitor/FetchAddress", type: "get", data: t, success: function (t) {
                    n.hide();
                    n.after(t)
                }
            }) : n.hide()
        })
    }, getAddressByLngAndLat: function (n, t) {
        return '<div style="width:200px"><a class="show_address" style="cursor:pointer;" lng="' + n + '" lat="' + t + '">查看<\/a><\/div>'
    }, getAddressByLngLat: function (n) {
        return '<div style="width:200px"><a class="show_address" style="cursor:pointer;" lngLat="' + n + '">查看<\/a><\/div>'
    }, _init_columns: function () {
        var n = this, t = !1, i = 0;
        n.options.checkbox_data && (i = i + 1, n.table_options.columns.push({
            sClass: "text-center",
            title: '<input type="checkbox" class="checkall" />',
            width: "15px",
            orderable: !1,
            data: n.options.checkbox_data,
            render: function (n) {
                return '<input type="checkbox"  class="checkchild"  value="' + n + '" />'
            }
        }), $("#" + n.options.pid).delegate(".checkall", "click", function () {
            var t = $(this).prop("checked");
            $(".checkchild", n.tableObj).prop("checked", t);
            n._init_checkDo()
        }), n.tableObj.delegate(".checkchild", "click", function (t) {
            n._init_checkDo();
            t.stopPropagation()
        }));
        n.options.downloadColumns = [];
        $.each(n.options.columns, function (r, u) {
            var f = n.options.parameters[u], e;
            f && (!f.orderable || (t = !!f.orderable, f.order && n.table_options.order.push([i + r, f.order])), e = {
                data: u,
                orderable: !!f.orderable,
                title: f.title,
                orderKey: f.orderKey,
                render: function (t, i, r, u) {
                    var e = this;
                    return f.tableRender ? f.tableRender.call(e, t, i, r, u, n) : t ? t : ""
                }
            }, f.columnWidth && (e.width = f.columnWidth), n.table_options.columns.push(e), n.options.excludeColumns.indexOf(u) < 0 && n.options.downloadColumns.push({
                Name: u,
                Display: f.title,
                Width: f.downloadWidth || null,
                Render: f.downloadRender
            }), f.btnhandlers && $.each(f.btnhandlers, function (t, i) {
                n.tableObj.delegate("." + i.className, "click", function () {
                    i.handler.call(n)
                })
            }))
        });
        n.table_options.ordering = n.table_options.order.length > 0 ? t : !1;
        n.table_options.orderMulti = n.table_options.order.length > 0 ? t : !1
    }, _init_search: function (n) {
        var t = this;
        t.searchDiv = $('<div class="add_search_param"><\/div>').css({"margin-left": "10px"}).appendTo(n);
        $.each(t.options.searchs, function (n, i) {
            getObjFromCategory[i.category].call(t.searchDiv, i, t.searchDiv)
        });
        getObjFromCategory.button.call(t.searchDiv, {
            id: t.validate ? "search" : "",
            validate: t.validate,
            handle: function () {
                t._load()
            }
        }, t.searchDiv);
        t.options.noDownLoad || getObjFromCategory.button.call(t.searchDiv, {
            id: t.validate ? "download" : "",
            text: "导出",
            validate: t.validate,
            handle: function () {
                $.showLoading("生成数据。。");
                var n = {page: 1, limit: 500, IsExcel: !0};
                t.options.isCreateByOneKey && (n.isCreateByOneKey = t.options.isCreateByOneKey);
                $.extend(n, t.searchDiv ? t.searchDiv.toJson() : {});
                t.options.handleBefore && t.options.handleBefore(n);
                n.Excel = {Title: t.options.downloadTitle || document.title, Params: t.options.downloadColumns};
                $.updateSever({
                    para: n, url: t.options.url || "/api/" + t.name + "/Search", success: function (n) {
                        $.hideLoading();
                        var t = $("#download_if[iframe]", $("body"));
                        t.length == 0 && (t = $("<iframe><\/iframe>").attr("name", "download_if").attr("id", "download_if").css({display: "none"}).appendTo($("body")));
                        t.attr("src", "/Report/Download/" + n)
                    }
                })
            }
        }, t.searchDiv)
    }, _init_checkDo: function () {
        var r = this, n = $("#" + r.options.pid), t = $(".checkchild:checked", n).length, i = !1;
        t > 0 ? ($(".multiple_button_TB:disabled", n).removeAttr("disabled"), t == 1 ? $(".single_button_TB:disabled", n).removeAttr("disabled") : $(".single_button_TB:not(:disabled)", n).attr("disabled", "disabled"), i = t == $(".checkchild", n).length) : $(".single_button_TB,.multiple_button_TB:not(:disabled)", n).attr("disabled", "disabled");
        n.find(".checkall").prop("checked", i)
    }, _init_button: function (n) {
        var t = this, i = $("<div><\/div>").appendTo(n);
        t.btnObjs = {};
        $.each(t.options.buttons, function (n, r) {
            var f = [], u = {};
            $.extend(u, t._button_Obj[r.id], r.button_Obj);
            r.data && $.each(r.data, function (n, i) {
                var u = t.options.parameters[i], r;
                u && (r = {}, r.key = i, r.datafld = i, $.extend(r, u), f.push(r))
            });
            !r.handleBefore && u.handleBefore && (r.handleBefore = u.handleBefore);
            r.disabled = r.disabled || u && u.disabled;
            t.btnObjs[r.id] = getObjFromCategory.button.call(i, {
                text: r.text,
                id: r.id,
                validate: t.validate,
                model_options: {
                    id: r.id,
                    width: r.width,
                    height: r.height,
                    body: r.model_body,
                    footer: r.model_footer,
                    parameters: f,
                    validateField: r.validateField,
                    bindData: r.bindData,
                    dtc: t,
                    show: function () {
                        r.show ? r.show.call(t, r, this) : u && u.show && u.show.call(t, r, this)
                    },
                    showBefore: function () {
                        var n = !0;
                        return r.showBefore ? n = r.showBefore.call(t, r) : u && u.showBefore && (n = u.showBefore.call(t, r)), n
                    },
                    obj: u.obj
                },
                disabled: r.disabled,
                handle: function (n, i) {
                    (r.handleBefore && r.handleBefore.call(t, n)) !== !1 && (r.handle ? r.handle.call(t, r, n, i) : u && u.handle && u.handle.call(t, r, n, i))
                }
            }, i);
            u.isSingle && t.btnObjs[r.id].addClass("single_button_TB").attr("disabled", "disabled");
            u.isMultiple && t.btnObjs[r.id].addClass("multiple_button_TB").attr("disabled", "disabled")
        })
    }, _load: function () {
        this._dataTable.ajax.reload();
        this.options.updateAfter && this.options.updateAfter.call(this)
    }, clear: function () {
        this.isClear = !0;
        this._dataTable.ajax.reload();
        this.isClear = !1
    }, _button_Obj: {
        add: {
            handle: function (n, t, i) {
                var r = this;
                n.formatData && n.formatData(t);
                $.updateSever({
                    para: t, url: n.url || "/api/" + r.name + "/Add", success: function (t) {
                        t === !0 ? (r._load(), i()) : $.alert({title: "提示", content: n.error_msg + n.text + "失败!"})
                    }
                })
            }
        }, edit: {
            isSingle: !0, handle: function (n, t, i) {
                var r = this, u = $(".checkchild:checked", r.tableObj).val();
                t[r.table_options.rowId] = u;
                n.formatData && n.formatData(t);
                $.updateSever({
                    para: t, url: n.url || "/api/" + r.name + "/Update", success: function (t) {
                        t === !0 ? (i(), r._load()) : $.alert({title: "提示", content: n.error_msg + n.text + "失败!"})
                    }
                })
            }, show: function (n, t) {
                var i = this, r = $(".checkchild:checked", i.tableObj).val(), u = i._dataTable.row("#" + r).data();
                t.toBind(u)
            }
        }, del: {
            isMultiple: !0, handle: function (n, t, i) {
                var r = this, u = [];
                $(".checkchild:checked", r.tableObj).each(function (n, t) {
                    u.push($(t).val())
                });
                $.updateSever({
                    para: u, url: n.url || "/api/" + r.name + "/Remove", success: function (t) {
                        t === !0 ? (i(), r._load()) : $.alert({title: "提示", content: n.error_msg + n.text + "失败!"})
                    }, error: function () {
                        i(!0)
                    }
                })
            }
        }
    }
};
selectGroupZTree = function (n, t) {
    var i = this, u, r;
    i.outDiv = t;
    i.source = n;
    i.isWithChildren = n.isWithChildren;
    i.withChildren = !0;
    i.isShow = !1;
    i.isFormat = !1;
    i.selected = null;
    i.defaultValue = n.defaultValue;
    u = new Date;
    r = u.getTime();
    i.showId = n.datafld + "_show_" + r;
    i.hidenId = n.datafld + "_hiden_" + r;
    i.viewId = n.datafld + "_view_" + r;
    i.treeId = n.datafld + "_tree_" + r;
    i.datafld = n.datafld;
    i.viewHeight = n.viewHeight || 300;
    i.showWidth = n.showWidth;
    i.loadZTree = i._formatzTree;
    i.key_groupsearch = {};
    i._formatKey_groupsearch();
    i._init()
};
selectGroupZTree.prototype = {
    _init: function () {
        function t(t) {
            n._onBodyDown.call(this, t, n)
        }

        var n = this;
        n._addObj();
        n.bindmouse = function () {
            $("body").bind("mousedown", t)
        };
        n.unbindmouse = function () {
            $("body").unbind("mousedown", t)
        };
        n.defaultValue && n.setValue(n.defaultValue);
        n.showObj.data("reSet", function () {
            n.defaultValue ? n.setValue(n.defaultValue) : n.clear()
        })
    }, _addObj: function () {
        var n = this, t;
        if (n.showObj = $("<input>").attr("type", "text").focus(function () {
            this.blur()
        }).attr("id", n.showId).addClass("form-control treeSelect down-input").attr("placeholder", "请选择所属组").css({cursor: "pointer"}).bind("click", function () {
            n.open()
        }), n.showWidth && n.showObj.css({width: n.showWidth}), n.isWithChildren) {
            t = $('<div class="input-group"><\/div>').appendTo(n.outDiv);
            n.showObj.appendTo(t);
            $('<span style="cursor:pointer;width:38px" class="input-group-addon" title="包含子组"><i class="fa fa-check-square-o"><\/i><\/span>').appendTo(t).on("click", function () {
                n.withChildren ? (n.withChildren = !1, n.withChildrenObj.val(!1), $(this).find("i").removeClass("fa-check-square-o").addClass("fa-square-o")) : (n.withChildren = !0, n.withChildrenObj.val(!0), $(this).find("i").removeClass("fa-square-o").addClass("fa-check-square-o"))
            })
        } else n.showObj.appendTo(n.outDiv);
        n.showObj.data("selectGroupZTree", n);
        n.hidenObj = $("<input />").attr("type", "hidden").attr("id", n.hidenId).addClass("selectzTree").attr("datafld", n.datafld).appendTo(n.outDiv).data("selectzTree", n);
        n.withChildrenObj = $("<input />").attr("type", "hidden").attr("datafld", "WithChildren").attr("value", !0).appendTo(n.outDiv);
        n.viewObj = $("<div><\/div>").css({height: n.viewHeight + "px"}).attr("id", n.viewId).addClass("treeSelectDiv").appendTo(n.outDiv);
        n.searchObj = $("<div><\/div>").addClass("input-group margin").append($('<input type="text" style="width:220px" class="form-control" placeholder="组名"><span class="input-group-btn"><button type="button" class="btn btn-info btn-flat">搜索<\/button><\/span>')).appendTo($("<div><\/div>").css({height: "50px"}).appendTo(n.viewObj));
        n.searchObj.find(".btn-flat").bind("click", function () {
            n._searchDo()
        });
        $('[type="text"]', n.viewObj).keydown(function (t) {
            t.keyCode == 13 && (n._searchDo(), $(this).focus())
        });
        n.treeObj = $("<ul><\/ul>").css({
            height: n.viewHeight - 60 + "px",
            "min-width": "295px",
            "max-width": "295px",
            "overflow-y": "auto",
            "overflow-x": "auto",
            "z-index": 9999
        }).addClass("ztree").attr("id", n.treeId).appendTo(n.viewObj)
    }, _formatzTree: function (n) {
        var t = this, i = {
            async: {
                enable: !0,
                contentType: "application/json",
                dataType: "json",
                type: "post",
                url: "/api/Group/ShowChildren",
                dataFilter: function (n, t, i) {
                    if (i.Status == 0) return $.alert({title: "提示", content: i.ErrorMessage}), [];
                    var r = i.Result;
                    return $.each(r, function (n, t) {
                        t.iconSkin = "default"
                    }), r
                }
            },
            view: {dblClickExpand: !1, showLine: !0, selectedMulti: !1},
            data: {key: {name: "OrgName", title: ""}, simpleData: {enable: !0, idKey: "Id", pIdKey: "ParentId"}},
            callback: {
                onClick: function (n, i, r) {
                    t._selectedDo(r, !0)
                }, onAsyncSuccess: function () {
                    for (var r = t.ztreeObj.getNodes(), u, f, i = 0; i < r.length; i++) t.ztreeObj.expandNode(r[i], !0, !1, !0);
                    t.selected && (u = t.ztreeObj.setting.data.simpleData.idKey, f = t.ztreeObj.getNodeByParam(u, t.selected, null), t.ztreeObj.selectNode(f));
                    n && n()
                }
            }
        };
        t.ztreeObj = $.fn.zTree.init(t.treeObj, i, []);
        t.isFormat = !0;
        t.loadZTree = t._loadZTree
    }, _searchDo: function () {
        function r() {
            for (var t = n.key_groupsearch.nodes[n.key_groupsearch.cur], i; t.parentTId;) i = t.parentTId, t = n.ztreeObj.getNodeByTId(i), n.ztreeObj.expandNode(t, !0, !1, !0);
            n.ztreeObj.selectNode(n.key_groupsearch.nodes[n.key_groupsearch.cur]);
            n._selectedDo(n.key_groupsearch.nodes[n.key_groupsearch.cur])
        }

        var n = this, t = n.searchObj.find(".form-control").val(), i;
        if (t) {
            if (n.key_groupsearch.v == t) {
                n.key_groupsearch.cur = n.key_groupsearch.cur < n.key_groupsearch.total - 1 ? n.key_groupsearch.cur + 1 : 0;
                r();
                return
            }
            if (i = n.ztreeObj.getNodesByParamFuzzy("OrgName", t, null), i.length > 0) {
                n.key_groupsearch.total = i.length;
                n.key_groupsearch.cur = 0;
                n.key_groupsearch.nodes = i;
                n.key_groupsearch.v = t;
                r();
                return
            }
            $.alert({title: "提示", content: "没有符合的"})
        }
        n._formatKey_groupsearch()
    }, open: function () {
        var n = this;
        n.isShow || (n.isShow = !0, n.loadZTree(function () {
            var t = n.outDiv.width();
            n.treeObj.width(t);
            n.viewObj.slideDown("fast");
            n.bindmouse()
        }))
    }, _loadZTree: function () {
        var n = this;
        n.ztreeObj.reAsyncChildNodes(null, "refresh")
    }, _selectedDo: function (n, t) {
        var i = this;
        return i.selected = n.Id, i.onChange && i.hidenObj.val() != n.Id && setTimeout(function () {
            i.onChange(i.hidenObj.val(), i.showObj.val())
        }, 500), i.hidenObj.val(n.Id), i.showObj.val(n.OrgName), t && i.close(), n
    }, _unSelectedDo: function (n, t) {
        var i = this;
        return i.selected = null, i.hidenObj.val(""), i.showObj.val(""), t && i.close(), n
    }, _formatKey_groupsearch: function () {
        var n = this;
        n.key_groupsearch.total = 0;
        n.key_groupsearch.cur = 0;
        n.key_groupsearch.nodes = [];
        n.key_groupsearch.v = ""
    }, close: function () {
        var n = this;
        n.isShow = !1;
        n._formatKey_groupsearch();
        $('[type="text"]', n.viewObj).val("");
        n.viewObj.fadeOut("fast");
        n.unbindmouse()
    }, _onBodyDown: function (n, t) {
        n.target.id == t.showId || n.target.id == t.viewId || $(n.target).parents("#" + t.viewId).length > 0 || t.close()
    }, _getName: function (n, t) {
        var i = this;
        $.updateSever({
            para: {Id: n}, url: "/api/Group/SearchOrgName", success: function (n) {
                t && t(n)
            }
        })
    }, clear: function () {
        var n = this;
        n.hidenObj.val("");
        n.showObj.val("");
        n.selected = null;
        n._formatKey_groupsearch();
        $('[type="text"]', n.viewObj).val("")
    }, setValue: function (n) {
        var t = this;
        t.clear();
        t._getName(n, function (i) {
            i != null && (t.selected = n, t.hidenObj.val(n), t.showObj.val(i))
        })
    }, getValue: function () {
        return this.selected
    }, getText: function () {
        var n = this;
        return n.showObj.val()
    }
};
getObjFromCategory = {
    treeSelect: function (n, t) {
        n.isNoEmpty && !n.defaultValue && ($.updateSever({
            async: !1,
            url: "/api/Group/SearchCurrentGroupId",
            success: function (t) {
                n.defaultValue = t
            }
        }), n.defaultValue);
        var i = new selectGroupZTree({
            datafld: n.datafld,
            showWidth: n.showWidth,
            placeholder: n.placeholder,
            isWithChildren: n.isWithChildren,
            defaultValue: n.defaultValue
        }, this);
        return n.handler && (i.onChange = function (i, r) {
            n.handler(i, r, t)
        }), i.showObj
    }, text: function (n, t) {
        var i = {"class": "form-control", datafld: "Key", name: null, prefix: t && t.prefix || ""}, r;
        return $.extend(i, n), r = $("<input>").attr("type", "text").attr("id", i.prefix + "_" + i.datafld + "_id").addClass(i.class).attr("datafld", i.datafld), i.name && r.attr("name", i.name), i.width && r.css({width: i.width}), i.placeholder && r.attr("placeholder", i.placeholder), i.hidden && r.hide(), i.value && r.val(i.value), r.appendTo(this), r.data("reSet", function () {
            r.val("")
        }), r
    }, number: function (n, t) {
        var i = {"class": "form-control", datafld: "Key", name: null, prefix: t && t.prefix || ""}, r;
        return $.extend(i, n), r = $("<input>").attr("type", "number").attr("id", i.prefix + "_" + i.datafld + "_id").addClass(i.class).attr("datafld", i.datafld), i.name && r.attr("name", i.name), i.width && r.css({width: i.width}), i.placeholder && r.attr("placeholder", i.placeholder), i.minValue && r.attr("min", i.minValue), i.hidden && r.hide(), i.value && r.val(i.value), r.appendTo(this), r.data("reSet", function () {
            r.val("")
        }), r
    }, text_typeahead: function (n, t) {
        var i = {"class": "form-control", datafld: "Key", name: null, prefix: t && t.prefix || ""}, u, r;
        return $.extend(i, n), u = $("<input>").attr("type", "hidden").attr("datafld", i.datafld), r = $("<input>").attr("type", "text").attr("id", i.prefix + "_" + i.datafld + "_id").addClass(i.class).typeahead({
            source: function (n, t) {
                u.val("");
                $.updateSever({
                    para: {key: n}, url: "/api/Device/SelectDeviceByOrgId", success: function (n) {
                        t(n)
                    }
                })
            }, items: 10, updater: function (n) {
                return n.Id
            }, displayText: function (n) {
                return n.Name
            }, afterSelect: function (n) {
                u.val(n)
            }, delay: 500
        }), i.width && r.css({width: i.width}), i.placeholder && r.attr("placeholder", i.placeholder), r.appendTo(this), u.appendTo(this), r.data("reSet", function () {
            r.val("");
            u.val("")
        }), r
    }, textarea: function (n, t) {
        var i = {"class": "form-control", datafld: "Remark", name: null, rows: 3, prefix: t && t.prefix || ""}, r;
        return $.extend(i, n), r = $("<textarea ><\/textarea>").attr("id", i.prefix + "_" + i.datafld + "_id").addClass(i.class).attr("rows", i.rows).attr("datafld", i.datafld), i.name && r.attr("name", i.name), i.width && r.css({width: i.width}), r.appendTo(this), r
    }, label: function (n) {
        var t = {text: "text", "for": "key"};
        return $.extend(t, n), $('<label for="' + t.for + '">' + (t.notEmpty ? '<span style="color:red">*<\/span>' : "") + t.text + "<\/label>").appendTo(this)
    }, button: function (n) {
        var t, i;
        if (n.id && n.validate && !flds[n.id]) return $("");
        if (t = {
            "class": "btn bg-default margin", text: "搜索", model_options: null, handle: function () {
            }
        }, $.extend(t, n), i = $("<button>" + t.text + "<\/button>").addClass(t.class), t.model_options) t.model_options.id = t.model_options.id || t.text, t.model_options.text = t.model_options.text || t.text, t.handle && (t.model_options.handle = t.handle), t.model_options.showBefore && i.bind("click", function () {
            var n = t.model_options.showBefore();
            return n || event.stopPropagation(), n
        }), i.attr("data-toggle", "modal").attr("data-target", "#" + t.model_options.id), i.data("button_modal", new button_modal(t.model_options)); else if (t.handle) i.on("click", t.handle);
        return i.appendTo(this), i
    }, checkbox: function (n) {
        var f = {datafld: "Enabled"}, i, u, t, e, r;
        return $.extend(f, n), i = {
            noChecked: !1,
            label: "启用"
        }, $.extend(i, n.control_option), u = !1, t = [], t.push('<div class="checkbox"><label>'), t.push('<input  type="checkbox"'), t.push(" datafld=" + f.datafld), i.noChecked || (t.push(' checked="checked"'), u = !0), t.push(" />"), t.push(i.label), t.push("<\/label><\/div>"), e = $(t.join("")).appendTo(this), r = $('[type="checkbox"]', e), r.data("reSet", function () {
            r[0].checked = u
        }), r
    }, multiple_checkbox: function (n) {
        function u(n) {
            var i = {
                async: !1, url: t.remote.url, success: function (n) {
                    t.data = n
                }
            };
            t.remote.paraformat && (i.para = t.remote.paraformat(n));
            $.updateSever(i)
        }

        function r() {
            $.each(t.data, function (n, r) {
                n % t.model_column == 0 && (temp_div = $("<div><\/div>").css({
                    width: "100%",
                    display: "table-row"
                }).appendTo(i));
                var u = $('<div><input class="mult_chk" type="checkbox" name="' + t.name + '" value="' + r[t.id] + '" style="vertical-align:middle;margin:0px 2px 0px 0px" /><span style="vertical-align:middle" >' + r[t.text] + "<\/span><\/div>").css({
                    display: "table-cell",
                    padding: "1px 5px 1px 5px",
                    width: t.model_column_width
                }).appendTo(temp_div);
                $('[type="checkbox"]', u).data("obj_value", r)
            })
        }

        var f = this, t = {
            "class": "form-control",
            datafld: "checkboxs",
            model_column: 4,
            model_column_width: "50px",
            text: "text",
            id: "id",
            data: []
        }, i;
        return $.extend(!0, t, n), n.plus && $.extend(!0, t, n.plus), i = $("<div><\/div>").attr("id", t.datafld + "_id").css({display: "table"}).addClass(t.class).addClass("multiple_checkbox").attr("datafld", t.datafld), t.remote ? (i.data("loadData", function (n) {
            u(n);
            i.empty();
            r()
        }), t.remote.text && (t.text = t.remote.text), t.remote.id && (t.id = t.remote.id), t.remote.loadNow && (u(), r())) : r(), i.attr("mc_id", t.id).attr("mc_text", t.text), i.data("reSet", function () {
            i.find(".mult_chk").prop("checked", !1)
        }), i.appendTo(f), i
    }, date: function (n, t) {
        var i = {"class": "form-control", datafld: "DateOnly"};
        return i.control_option || (i.control_option = {}), i.control_option.singleDatePicker = !0, $.extend(!0, i, n), getObjFromCategory.datetime_common.call(this, i, "", t)
    }, datetime: function (n, t) {
        var i = {"class": "form-control", datafld: "Date_Time"};
        return $.extend(!0, i, n), i.control_option || (i.control_option = {}), i.control_option.singleDatePicker = !0, i.control_option.timePicker = !0, i.control_option.timePickerSeconds = !0, getObjFromCategory.datetime_common.call(this, i, "", t)
    }, rangedate: function (n, t) {
        var i = {"class": "form-control", datafld: "StartTime-->EndTime"};
        return $.extend(!0, i, n), getObjFromCategory.datetime_common.call(this, i, "range", t)
    }, rangedatetime: function (n, t) {
        var i = {"class": "form-control", datafld: "StartTime-->EndTime"};
        return $.extend(!0, i, n), i.control_option.timePicker = !0, i.control_option.timePickerSeconds = !0, getObjFromCategory.datetime_common.call(this, i, "range", t)
    }, datetime_common: function (n, t) {
        var r = (t ? t + "_" : "") + "date", f = n.prefix || "",
            u = $("<input />").attr("type", "text").attr("id", f + "_" + n.datafld + "_id").addClass(n.class).addClass("date_control").attr("datafld", n.datafld).appendTo(this),
            i;
        return n.width && u.css({width: n.width}), n.placeholder && u.attr("placeholder", n.placeholder), i = {
            singleDatePicker: !1,
            timePicker: !1,
            timePicker24Hour: !0,
            timePickerSeconds: !1,
            locale: {format: "YYYY-MM-DD"}
        }, $.extend(!0, i, n.control_option), i.timePicker && (i.locale.format = "YYYY-MM-DD HH:mm", r = r + "_time", i.timePickerSeconds && (i.locale.format = "YYYY-MM-DD HH:mm:ss", r = r + "_second")), u.addClass(r).daterangepickerplus(i), n.readonly && u.attr("readonly", "readonly"), u
    }, select: function (n, t) {
        var e = this, i = {"class": "form-control", datafld: "Enabled", handler: null, defaultValue: null, data: []}, r,
            f, u;
        return $.extend(i, n), r = $("<select><\/select>").attr("id", i.datafld + "_id").addClass(i.class).attr("datafld", i.datafld), i.handler && r.bind("change", function () {
            var n = this.options[this.selectedIndex];
            i.handler.call(e, n.value, n.text, t)
        }), i.remote ? (f = function (n) {
            var t = {
                async: !1, url: i.remote.url, success: function (n) {
                    r.empty();
                    i.remote.firstData && $("<option>" + i.remote.firstData.text + "<\/option>").attr("value", i.remote.firstData.id).appendTo(r);
                    $.each(n, function (n, t) {
                        $("<option>" + t[u.text] + "<\/option>").attr("value", t[u.id]).appendTo(r)
                    });
                    r.trigger("change")
                }
            };
            t.para = n && i.remote.paraformat ? i.remote.paraformat(n) : {};
            $.updateSever(t)
        }, u = {
            text: "text",
            id: "id"
        }, $.extend(u, i.remote), r.data("openLoad", f), i.remote.loadNow && f(), r.data("reSet", function () {
            i.remote.firstData && r.val(i.remote.firstData.id);
            r.trigger("change")
        })) : ($.each(i.data, function (n, t) {
            var i = $("<option>" + t.text + "<\/option>").attr("value", t.id).appendTo(r)
        }), r.data("reSet", function () {
            i.data.length > 0 && r.val(i.data[0].id);
            r.trigger("change")
        }), i.defaultValue != null && r.val(i.defaultValue), r.trigger("change")), r.appendTo(e), r
    }, rangeNumber: function (n) {
        var u = this,
            t = {"class": "form-control", datafld: "NumberValue", id: "", min: 0, max: 4, unit: "天", hidden: !1}, r, i;
        $.extend(t, n);
        r = "<div style='display:inline-block;border:0px;' id='" + t.id + "'><input datafld='Min" + t.datafld + "'  class='form-control' style='margin-right:0px;width:30px;padding-left:3px;padding-right:3px;text-align:right;' maxlength='3' value='" + t.min + "'/>--<input datafld='Max" + t.datafld + "' class='form-control' style='width:30px;padding-left:3px;padding-right:3px;text-align:right;' maxlength='3' value='" + t.max + "' />" + t.unit + "<\/div>";
        i = $(r);
        i.on("keyup", "input", function () {
            this.value = this.value.replace(/[^\d]/g, "")
        });
        return t.hidden && i.hide(), i.appendTo(u)
    }, timeSelect: function (n) {
        var f = this, r = {"class": "form-control", datafld: "TimeValue", addible: !1, max: 4, rmv: !1}, i, t, u;
        for ($.extend(r, n), i = r.rmv ? "" : "<div class='container'>", i += "<div class='timeSelect' datafld='" + r.datafld + "'>", i += "<select>", t = 0; t < 24; t++) i += t < 10 ? '<option value="0' + t + '">0' + t + "时<\/option>" : '<option value="' + t + '">' + t + "时<\/option>";
        for (i += "<\/select><select>", t = 0; t < 60; t++) i += t < 10 ? '<option value="0' + t + '">0' + t + "分<\/option>" : '<option value="' + t + '">' + t + "分<\/option>";
        for (i += "<\/select><select>", t = 0; t < 60; t++) i += t < 10 ? '<option value="0' + t + '">0' + t + "秒<\/option>" : '<option value="' + t + '">' + t + "秒<\/option>";
        if (i += "<\/select>", r.addible && (i += r.rmv ? '<input type="button" value="-" />' : '<input type="button" value="+" />'), i += "<\/div>", i += r.rmv ? "" : "<\/div>", u = $(i).appendTo(f), r.addible) if (r.rmv) u.find("input").on("click", function () {
            $(this).parent().remove()
        }); else u.find("input").on("click", function () {
            $(this).parent().parent().find(".timeSelect").length < r.max && getObjFromCategory.timeSelect.call($(this).parent().parent(), {
                datafld: r.datafld,
                addible: !0,
                rmv: !0
            })
        })
    }, multiSelect: function (n) {
        var i = this, t = {"class": "form-control", selecTag: []};
        $.extend(t, n);
        $.each(t.selecTag, function (n, r) {
            var u = $("<select><\/select>").attr("id", r.datafld + "_id").addClass(t.class).attr("datafld", r.datafld);
            r.width && u.css({width: r.width});
            u.css({padding: "0", display: "inline-block", float: "left"});
            u[0].onmousedown = function () {
                this.options.length > 6 && (this.size = 7)
            };
            u[0].onblur = function () {
                this.size = 0
            };
            u[0].onchange = function () {
                this.size = 0
            };
            $.each(r.data, function (n, t) {
                var i = $("<option>" + t.text + "<\/option>").attr("value", t.id).appendTo(u)
            });
            u.data("reSet", function () {
                r.data.length > 0 && u.val(r.data[0].id);
                u.trigger("change")
            });
            u.trigger("change");
            u.appendTo(i)
        })
    }, radio: function (n) {
        var t = {"class": "form-control", datafld: "Enabled", data: []}, i;
        return $.extend(t, n), i = $('<div class="radio"><\/div>').attr("datafld", t.datafld).appendTo(this), $.each(t.data, function (n, r) {
            var u = $("<label><\/label>").appendTo(i),
                f = $("<input />").attr("type", "radio").attr("value", r.id).attr("name", t.datafld + "_radio").appendTo(u);
            r.handler && f.bind("click", function () {
                r.handler.call(i)
            });
            n || f.attr("checked", "checked");
            $('<span style="padding-right:10px">' + r.text + "<\/span>").appendTo(u)
        }), i
    }, "import": function () {
        var n = $('<input type="file" />');
        return n.appendTo(this), n
    }, html: function (n) {
        var t = $(n.body);
        return t.appendTo(this), t
    }
};
button_modal.prototype = {
    default_options: {
        parameters: [], handle: function () {
        }, width: "580px", height: "auto"
    }, _init: function () {
        var n = this;
        n._addModel()
    }, _openDo: function (n) {
        var t = this, i, r;
        n && (i = {}, r = $(".checkchild:checked", t.options.dtc.tableObj).val(), i[t.options.dtc.table_options.rowId] = r, this.obj.data("bootstrapValidator").updateOption(n + "_lab", "remote", "data", i), this.obj.data("bootstrapValidator").enableFieldValidators(n + "_lab", !0));
        $("select", t.obj).each(function (n, t) {
            $(t).data("openLoad") && $(t).data("openLoad")()
        });
        $(".multiple_checkbox", t.obj).each(function (n, t) {
            $(t).data("openLoad") && $(t).data("openLoad")()
        })
    }, _clear: function () {
        var n = this;
        n.obj.bootstrapValidator("resetForm", !0);
        $(".treeSelect", n.obj).each(function (n, t) {
            $(t).data("reSet") && $(t).data("reSet")()
        });
        $("select", n.obj).each(function (n, t) {
            $(t).data("reSet") && $(t).data("reSet")()
        });
        $(".date_control", n.obj).each(function (n, t) {
            $(t).data("reSet") && $(t).data("reSet")()
        });
        $('[type="checkbox"]', n.obj).each(function (n, t) {
            $(t).data("reSet") && $(t).data("reSet")()
        });
        $('[type="file"]', n.obj).each(function (n, t) {
            $(t).val("")
        });
        $(".multiple_checkbox", n.obj).each(function (n, t) {
            $(t).data("reSet") && $(t).data("reSet")()
        })
    }, _addModel: function () {
        var n = this, u = {}, t, r, i, f;
        n.obj = $("body").addModel(n.options);
        t = n.obj.find(".modal-body");
        i = !1;
        $.each(n.options.parameters, function (f, e) {
            i || (r = $("<div><\/div>").addClass("form-group").appendTo(t));
            i = e.merge && !i ? !0 : !1;
            getObjFromCategory.label.call(r, {
                text: e.title,
                "for": e.datafld + "_lab",
                notEmpty: !!(e.validators && e.validators.notEmpty)
            }, t).addClass("col-sm-2 control-label");
            var s = e.merge ? $("<div><\/div>").addClass("col-sm-4").appendTo(r) : $("<div><\/div>").addClass("col-sm-10").appendTo(r),
                o = {
                    type: e.type,
                    remote: e.remote,
                    datafld: e.datafld,
                    isNoEmpty: e.isNoEmpty,
                    isWithChildren: e.isWithChildren,
                    placeholder: e.placeholder,
                    minValue: e.minValue,
                    name: e.datafld + "_lab",
                    data: e.data,
                    handler: e.handler,
                    plus: e.plus,
                    body: e.body,
                    prefix: n.options.id
                };
            e.obj_option && $.extend(!0, o, e.obj_option);
            n.obj[e.datafld] = getObjFromCategory[e.type || "text"].call(s, o, t);
            u[e.datafld + "_lab"] = {};
            e.validators && (u[e.datafld + "_lab"].validators = e.validators)
        });
        f = n.obj.find(".modal-footer");
        $('<button type="button" class="btn btn-default" data-dismiss="modal">关闭<\/button>').appendTo(f);
        $('<button type="button" class="btn btn-default" >确定<\/button>').appendTo(f).on("click", function () {
            if (n.options.validateField && n.obj.data("bootstrapValidator").getFieldElements(n.options.validateField + "_lab").data("bv.result.remote") == "NOT_VALIDATED" && n.obj.data("bootstrapValidator").enableFieldValidators(n.options.validateField + "_lab", !1), n.obj.data("bootstrapValidator").validate(), n.obj.data("bootstrapValidator").isValid()) {
                var i = t.toJson();
                n.options.handle && n.options.handle(i, function (t) {
                    n.obj.modal("hide");
                    t || $.alert({title: "提示", content: n.options.text + "成功!"})
                })
            }
        });
        n.obj.on("hidden.bs.modal", function () {
            n._clear()
        }).on("show.bs.modal", function () {
            n._openDo(n.options.validateField);
            n.options.show && n.options.show.call(n.obj)
        }).bootstrapValidator({
            message: "This value is not valid",
            feedbackIcons: {
                valid: "glyphicon glyphicon-ok",
                invalid: "glyphicon glyphicon-remove",
                validating: "glyphicon glyphicon-refresh"
            },
            fields: u,
            excluded: [":disabled"]
        })
    }
}