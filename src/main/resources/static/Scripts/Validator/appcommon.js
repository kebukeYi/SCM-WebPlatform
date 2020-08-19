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

function _init() {
    "use strict";
    $.AdminLTE.layout = {
        activate: function () {
            var n = this;
            n.fix();
            n.fixSidebar();
            $("body, html, .wrapper").css("height", "auto");
            $(window, ".wrapper").resize(function () {
                n.fix();
                n.fixSidebar()
            })
        }, fix: function () {
            var r, t;
            $(".layout-boxed > .wrapper").css("overflow", "hidden");
            var u = $(".main-footer").outerHeight() || 0, f = $(".main-header").outerHeight() + u,
                n = $(window).height(), i = $(".sidebar").height() || 0;
            $("body").hasClass("fixed") ? $(".content-wrapper, .right-side").css("min-height", n - u) : (n >= i ? ($(".content-wrapper, .right-side").css("min-height", n - f), r = n - f) : ($(".content-wrapper, .right-side").css("min-height", i), r = i), t = $($.AdminLTE.options.controlSidebarOptions.selector), "undefined" != typeof t && t.height() > r && $(".content-wrapper, .right-side").css("min-height", t.height()))
        }, fixSidebar: function () {
            return $("body").hasClass("fixed") ? ("undefined" == typeof $.fn.slimScroll && window.console && window.console.error("Error: the fixed layout requires the slimscroll plugin!"), void ($.AdminLTE.options.sidebarSlimScroll && "undefined" != typeof $.fn.slimScroll && ($(".sidebar").slimScroll({destroy: !0}).height("auto"), $(".sidebar").slimScroll({
                height: $(window).height() - $(".main-header").height() + "px",
                color: "rgba(0,0,0,0.2)",
                size: "3px"
            })))) : void ("undefined" != typeof $.fn.slimScroll && $(".sidebar").slimScroll({destroy: !0}).height("auto"))
        }
    };
    $.AdminLTE.pushMenu = {
        activate: function (n) {
            var t = $.AdminLTE.options.screenSizes;
            $(document).on("click", n, function (n) {
                n.preventDefault();
                $(window).width() > t.sm - 1 ? $("body").hasClass("sidebar-collapse") ? $("body").removeClass("sidebar-collapse").trigger("expanded.pushMenu") : $("body").addClass("sidebar-collapse").trigger("collapsed.pushMenu") : $("body").hasClass("sidebar-open") ? $("body").removeClass("sidebar-open").removeClass("sidebar-collapse").trigger("collapsed.pushMenu") : $("body").addClass("sidebar-open").trigger("expanded.pushMenu")
            });
            $(".content-wrapper").click(function () {
                $(window).width() <= t.sm - 1 && $("body").hasClass("sidebar-open") && $("body").removeClass("sidebar-open")
            });
            ($.AdminLTE.options.sidebarExpandOnHover || $("body").hasClass("fixed") && $("body").hasClass("sidebar-mini")) && this.expandOnHover()
        }, expandOnHover: function () {
            var n = this, t = $.AdminLTE.options.screenSizes.sm - 1;
            $(".main-sidebar").hover(function () {
                $("body").hasClass("sidebar-mini") && $("body").hasClass("sidebar-collapse") && $(window).width() > t && n.expand()
            }, function () {
                $("body").hasClass("sidebar-mini") && $("body").hasClass("sidebar-expanded-on-hover") && $(window).width() > t && n.collapse()
            })
        }, expand: function () {
            $("body").removeClass("sidebar-collapse").addClass("sidebar-expanded-on-hover")
        }, collapse: function () {
            $("body").hasClass("sidebar-expanded-on-hover") && $("body").removeClass("sidebar-expanded-on-hover").addClass("sidebar-collapse")
        }
    };
    $.AdminLTE.tree = function (n) {
        var i = this, t = $.AdminLTE.options.animationSpeed;
        $(document).off("click", n + " li a").on("click", n + " li a", function (n) {
            var u = $(this), r = u.next(), f, e, o;
            r.is(".treeview-menu") && r.is(":visible") && !$("body").hasClass("sidebar-collapse") ? (r.slideUp(t, function () {
                r.removeClass("menu-open")
            }), r.parent("li").removeClass("active")) : r.is(".treeview-menu") && !r.is(":visible") && (f = u.parents("ul").first(), e = f.find("ul:visible").slideUp(t), e.removeClass("menu-open"), o = u.parent("li"), r.slideDown(t, function () {
                r.addClass("menu-open");
                f.find("li.active").removeClass("active");
                o.addClass("active");
                i.layout.fix()
            }));
            r.is(".treeview-menu") && n.preventDefault()
        })
    };
    $.AdminLTE.controlSidebar = {
        activate: function () {
            var t = this, i = $.AdminLTE.options.controlSidebarOptions, n = $(i.selector), u = $(i.toggleBtnSelector),
                r;
            u.on("click", function (r) {
                r.preventDefault();
                n.hasClass("control-sidebar-open") || $("body").hasClass("control-sidebar-open") ? t.close(n, i.slide) : t.open(n, i.slide)
            });
            r = $(".control-sidebar-bg");
            t._fix(r);
            $("body").hasClass("fixed") ? t._fixForFixed(n) : $(".content-wrapper, .right-side").height() < n.height() && t._fixForContent(n)
        }, open: function (n, t) {
            t ? n.addClass("control-sidebar-open") : $("body").addClass("control-sidebar-open")
        }, close: function (n, t) {
            t ? n.removeClass("control-sidebar-open") : $("body").removeClass("control-sidebar-open")
        }, _fix: function (n) {
            var t = this;
            if ($("body").hasClass("layout-boxed")) {
                if (n.css("position", "absolute"), n.height($(".wrapper").height()), t.hasBindedResize) return;
                $(window).resize(function () {
                    t._fix(n)
                });
                t.hasBindedResize = !0
            } else n.css({position: "fixed", height: "auto"})
        }, _fixForFixed: function (n) {
            n.css({position: "fixed", "max-height": "100%", overflow: "auto", "padding-bottom": "50px"})
        }, _fixForContent: function (n) {
            $(".content-wrapper, .right-side").css("min-height", n.height())
        }
    };
    $.AdminLTE.boxWidget = {
        selectors: $.AdminLTE.options.boxWidgetOptions.boxWidgetSelectors,
        icons: $.AdminLTE.options.boxWidgetOptions.boxWidgetIcons,
        animationSpeed: $.AdminLTE.options.animationSpeed,
        activate: function (n) {
            var t = this;
            n || (n = document);
            $(n).on("click", t.selectors.collapse, function (n) {
                n.preventDefault();
                t.collapse($(this))
            });
            $(n).on("click", t.selectors.remove, function (n) {
                n.preventDefault();
                t.remove($(this))
            })
        },
        collapse: function (n) {
            var t = this, i = n.parents(".box").first(),
                r = i.find("> .box-body, > .box-footer, > form  >.box-body, > form > .box-footer");
            i.hasClass("collapsed-box") ? (n.children(":first").removeClass(t.icons.open).addClass(t.icons.collapse), r.slideDown(t.animationSpeed, function () {
                i.removeClass("collapsed-box")
            })) : (n.children(":first").removeClass(t.icons.collapse).addClass(t.icons.open), r.slideUp(t.animationSpeed, function () {
                i.addClass("collapsed-box")
            }))
        },
        remove: function (n) {
            var t = n.parents(".box").first();
            t.slideUp(this.animationSpeed)
        }
    }
}

var selectGroupZTree, getObjFromCategory, alarmInfo;
(function (n) {
    var h = {}, c = {}, l = {}, a = {
        treeId: "",
        treeObj: null,
        view: {
            addDiyDom: null,
            autoCancelSelected: !0,
            dblClickExpand: !0,
            expandSpeed: "fast",
            fontCss: {},
            nameIsHTML: !1,
            selectedMulti: !0,
            showIcon: !0,
            showLine: !0,
            showTitle: !0,
            txtSelectedEnable: !1
        },
        data: {
            key: {children: "children", name: "name", title: "", url: "url", icon: "icon"},
            simpleData: {enable: !1, idKey: "id", pIdKey: "pId", rootPId: null},
            keep: {parent: !1, leaf: !1}
        },
        async: {
            enable: !1,
            contentType: "application/x-www-form-urlencoded",
            type: "post",
            dataType: "text",
            url: "",
            autoParam: [],
            otherParam: [],
            dataFilter: null
        },
        callback: {
            beforeAsync: null,
            beforeClick: null,
            beforeDblClick: null,
            beforeRightClick: null,
            beforeMouseDown: null,
            beforeMouseUp: null,
            beforeExpand: null,
            beforeCollapse: null,
            beforeRemove: null,
            onAsyncError: null,
            onAsyncSuccess: null,
            onNodeCreated: null,
            onClick: null,
            onDblClick: null,
            onRightClick: null,
            onMouseDown: null,
            onMouseUp: null,
            onExpand: null,
            onCollapse: null,
            onRemove: null
        }
    }, y = function (n) {
        var i = t.getRoot(n);
        i || (i = {}, t.setRoot(n, i));
        i[n.data.key.children] = [];
        i.expandTriggerFlag = !1;
        i.curSelectedList = [];
        i.noSelection = !0;
        i.createdNodes = [];
        i.zId = 0;
        i._ver = (new Date).getTime()
    }, p = function (n) {
        var i = t.getCache(n);
        i || (i = {}, t.setCache(n, i));
        i.nodes = [];
        i.doms = []
    }, w = function (n) {
        var t = n.treeObj, r = i.event;
        t.bind(r.NODECREATED, function (t, i, r) {
            u.apply(n.callback.onNodeCreated, [t, i, r])
        });
        t.bind(r.CLICK, function (t, i, r, f, e) {
            u.apply(n.callback.onClick, [i, r, f, e])
        });
        t.bind(r.EXPAND, function (t, i, r) {
            u.apply(n.callback.onExpand, [t, i, r])
        });
        t.bind(r.COLLAPSE, function (t, i, r) {
            u.apply(n.callback.onCollapse, [t, i, r])
        });
        t.bind(r.ASYNC_SUCCESS, function (t, i, r, f) {
            u.apply(n.callback.onAsyncSuccess, [t, i, r, f])
        });
        t.bind(r.ASYNC_ERROR, function (t, i, r, f, e, o) {
            u.apply(n.callback.onAsyncError, [t, i, r, f, e, o])
        });
        t.bind(r.REMOVE, function (t, i, r) {
            u.apply(n.callback.onRemove, [t, i, r])
        });
        t.bind(r.SELECTED, function (t, i, r) {
            u.apply(n.callback.onSelected, [i, r])
        });
        t.bind(r.UNSELECTED, function (t, i, r) {
            u.apply(n.callback.onUnSelected, [i, r])
        })
    }, b = function (n) {
        var r = n.treeObj, t = i.event;
        r.unbind(t.NODECREATED).unbind(t.CLICK).unbind(t.EXPAND).unbind(t.COLLAPSE).unbind(t.ASYNC_SUCCESS).unbind(t.ASYNC_ERROR).unbind(t.REMOVE).unbind(t.SELECTED).unbind(t.UNSELECTED)
    }, k = function (n) {
        var c = n.target, f = t.getSetting(n.data.treeId), e = "", a = null, o = "", h = "", v = null, l = null,
            r = null;
        if (u.eqs(n.type, "mousedown") ? h = "mousedown" : u.eqs(n.type, "mouseup") ? h = "mouseup" : u.eqs(n.type, "contextmenu") ? h = "contextmenu" : u.eqs(n.type, "click") ? u.eqs(c.tagName, "span") && c.getAttribute("treeNode" + i.id.SWITCH) !== null ? (e = u.getNodeMainDom(c).id, o = "switchNode") : (r = u.getMDom(f, c, [{
            tagName: "a",
            attrName: "treeNode" + i.id.A
        }]), r && (e = u.getNodeMainDom(r).id, o = "clickNode")) : u.eqs(n.type, "dblclick") && (h = "dblclick", r = u.getMDom(f, c, [{
            tagName: "a",
            attrName: "treeNode" + i.id.A
        }]), r && (e = u.getNodeMainDom(r).id, o = "switchNode")), h.length > 0 && e.length == 0 && (r = u.getMDom(f, c, [{
            tagName: "a",
            attrName: "treeNode" + i.id.A
        }]), r && (e = u.getNodeMainDom(r).id)), e.length > 0) {
            a = t.getNodeCache(f, e);
            switch (o) {
                case"switchNode":
                    a.isParent ? u.eqs(n.type, "click") || u.eqs(n.type, "dblclick") && u.apply(f.view.dblClickExpand, [f.treeId, a], f.view.dblClickExpand) ? v = s.onSwitchNode : o = "" : o = "";
                    break;
                case"clickNode":
                    v = s.onClickNode
            }
        }
        switch (h) {
            case"mousedown":
                l = s.onZTreeMousedown;
                break;
            case"mouseup":
                l = s.onZTreeMouseup;
                break;
            case"dblclick":
                l = s.onZTreeDblclick;
                break;
            case"contextmenu":
                l = s.onZTreeContextmenu
        }
        return {stop: !1, node: a, nodeEventType: o, nodeEventCallback: v, treeEventType: h, treeEventCallback: l}
    }, d = function (n, i, r, f, e, o) {
        if (r) {
            var h = t.getRoot(n), s = n.data.key.children;
            r.level = i;
            r.tId = n.treeId + "_" + ++h.zId;
            r.parentTId = f ? f.tId : null;
            r.open = typeof r.open == "string" ? u.eqs(r.open, "true") : !!r.open;
            r[s] && r[s].length > 0 ? (r.isParent = !0, r.zAsync = !0) : (r.isParent = typeof r.isParent == "string" ? u.eqs(r.isParent, "true") : !!r.isParent, r.open = r.isParent && !n.async.enable ? r.open : !1, r.zAsync = !r.isParent);
            r.isFirstNode = e;
            r.isLastNode = o;
            r.getParentNode = function () {
                return t.getNodeCache(n, r.parentTId)
            };
            r.getPreNode = function () {
                return t.getPreNode(n, r)
            };
            r.getNextNode = function () {
                return t.getNextNode(n, r)
            };
            r.getIndex = function () {
                return t.getNodeIndex(n, r)
            };
            r.getPath = function () {
                return t.getNodePath(n, r)
            };
            r.isAjaxing = !1;
            t.fixPIdKeyValue(n, r)
        }
    }, e = {
        bind: [w],
        unbind: [b],
        caches: [p],
        nodes: [d],
        proxys: [k],
        roots: [y],
        beforeA: [],
        afterA: [],
        innerBeforeA: [],
        innerAfterA: [],
        zTreeTools: []
    }, t = {
        addNodeCache: function (n, i) {
            t.getCache(n).nodes[t.getNodeCacheId(i.tId)] = i
        }, getNodeCacheId: function (n) {
            return n.substring(n.lastIndexOf("_") + 1)
        }, addAfterA: function (n) {
            e.afterA.push(n)
        }, addBeforeA: function (n) {
            e.beforeA.push(n)
        }, addInnerAfterA: function (n) {
            e.innerAfterA.push(n)
        }, addInnerBeforeA: function (n) {
            e.innerBeforeA.push(n)
        }, addInitBind: function (n) {
            e.bind.push(n)
        }, addInitUnBind: function (n) {
            e.unbind.push(n)
        }, addInitCache: function (n) {
            e.caches.push(n)
        }, addInitNode: function (n) {
            e.nodes.push(n)
        }, addInitProxy: function (n, t) {
            t ? e.proxys.splice(0, 0, n) : e.proxys.push(n)
        }, addInitRoot: function (n) {
            e.roots.push(n)
        }, addNodesData: function (n, t, i, u) {
            var f = n.data.key.children, e;
            t[f] ? i >= t[f].length && (i = -1) : (t[f] = [], i = -1);
            t[f].length > 0 && i === 0 ? (t[f][0].isFirstNode = !1, r.setNodeLineIcos(n, t[f][0])) : t[f].length > 0 && i < 0 && (t[f][t[f].length - 1].isLastNode = !1, r.setNodeLineIcos(n, t[f][t[f].length - 1]));
            t.isParent = !0;
            i < 0 ? t[f] = t[f].concat(u) : (e = [i, 0].concat(u), t[f].splice.apply(t[f], e))
        }, addSelectedNode: function (n, i) {
            var r = t.getRoot(n);
            t.isSelectedNode(n, i) || r.curSelectedList.push(i)
        }, addCreatedNode: function (n, i) {
            if (!!n.callback.onNodeCreated || !!n.view.addDiyDom) {
                var r = t.getRoot(n);
                r.createdNodes.push(i)
            }
        }, addZTreeTools: function (n) {
            e.zTreeTools.push(n)
        }, exSetting: function (t) {
            n.extend(!0, a, t)
        }, fixPIdKeyValue: function (n, t) {
            n.data.simpleData.enable && (t[n.data.simpleData.pIdKey] = t.parentTId ? t.getParentNode()[n.data.simpleData.idKey] : n.data.simpleData.rootPId)
        }, getAfterA: function () {
            for (var n = 0, t = e.afterA.length; n < t; n++) e.afterA[n].apply(this, arguments)
        }, getBeforeA: function () {
            for (var n = 0, t = e.beforeA.length; n < t; n++) e.beforeA[n].apply(this, arguments)
        }, getInnerAfterA: function () {
            for (var n = 0, t = e.innerAfterA.length; n < t; n++) e.innerAfterA[n].apply(this, arguments)
        }, getInnerBeforeA: function () {
            for (var n = 0, t = e.innerBeforeA.length; n < t; n++) e.innerBeforeA[n].apply(this, arguments)
        }, getCache: function (n) {
            return l[n.treeId]
        }, getNodeIndex: function (n, i) {
            var u, f, r, e;
            if (!i) return null;
            for (u = n.data.key.children, f = i.parentTId ? i.getParentNode() : t.getRoot(n), r = 0, e = f[u].length - 1; r <= e; r++) if (f[u][r] === i) return r;
            return -1
        }, getNextNode: function (n, i) {
            var u, f, r, e;
            if (!i) return null;
            for (u = n.data.key.children, f = i.parentTId ? i.getParentNode() : t.getRoot(n), r = 0, e = f[u].length - 1; r <= e; r++) if (f[u][r] === i) return r == e ? null : f[u][r + 1];
            return null
        }, getNodeByParam: function (n, i, r, u) {
            var o, f, s, e;
            if (!i || !r) return null;
            for (o = n.data.key.children, f = 0, s = i.length; f < s; f++) {
                if (i[f][r] == u) return i[f];
                if (e = t.getNodeByParam(n, i[f][o], r, u), e) return e
            }
            return null
        }, getNodeCache: function (n, i) {
            if (!i) return null;
            var r = l[n.treeId].nodes[t.getNodeCacheId(i)];
            return r ? r : null
        }, getNodeName: function (n, t) {
            var i = n.data.key.name;
            return "" + t[i]
        }, getNodePath: function (n, t) {
            if (!t) return null;
            var i;
            return i = t.parentTId ? t.getParentNode().getPath() : [], i && i.push(t), i
        }, getNodeTitle: function (n, t) {
            var i = n.data.key.title === "" ? n.data.key.name : n.data.key.title;
            return "" + t[i]
        }, getNodes: function (n) {
            return t.getRoot(n)[n.data.key.children]
        }, getNodesByParam: function (n, i, r, u) {
            var o, e, f, s;
            if (!i || !r) return [];
            for (o = n.data.key.children, e = [], f = 0, s = i.length; f < s; f++) i[f][r] == u && e.push(i[f]), e = e.concat(t.getNodesByParam(n, i[f][o], r, u));
            return e
        }, getNodesByParamFuzzy: function (n, i, r, u) {
            var o, e, f, s;
            if (!i || !r) return [];
            for (o = n.data.key.children, e = [], u = u.toLowerCase(), f = 0, s = i.length; f < s; f++) typeof i[f][r] == "string" && i[f][r].toLowerCase().indexOf(u) > -1 && e.push(i[f]), e = e.concat(t.getNodesByParamFuzzy(n, i[f][o], r, u));
            return e
        }, getNodesByFilter: function (n, i, r, f, e) {
            var c, s, o, l, h;
            if (!i) return f ? null : [];
            for (c = n.data.key.children, s = f ? null : [], o = 0, l = i.length; o < l; o++) {
                if (u.apply(r, [i[o], e], !1)) {
                    if (f) return i[o];
                    s.push(i[o])
                }
                if (h = t.getNodesByFilter(n, i[o][c], r, f, e), f && !!h) return h;
                s = f ? h : s.concat(h)
            }
            return s
        }, getPreNode: function (n, i) {
            var u, f, r, e;
            if (!i) return null;
            for (u = n.data.key.children, f = i.parentTId ? i.getParentNode() : t.getRoot(n), r = 0, e = f[u].length; r < e; r++) if (f[u][r] === i) return r == 0 ? null : f[u][r - 1];
            return null
        }, getRoot: function (n) {
            return n ? c[n.treeId] : null
        }, getRoots: function () {
            return c
        }, getSetting: function (n) {
            return h[n]
        }, getSettings: function () {
            return h
        }, getZTreeTools: function (n) {
            var t = this.getRoot(this.getSetting(n));
            return t ? t.treeTools : null
        }, initCache: function () {
            for (var n = 0, t = e.caches.length; n < t; n++) e.caches[n].apply(this, arguments)
        }, initNode: function () {
            for (var n = 0, t = e.nodes.length; n < t; n++) e.nodes[n].apply(this, arguments)
        }, initRoot: function () {
            for (var n = 0, t = e.roots.length; n < t; n++) e.roots[n].apply(this, arguments)
        }, isSelectedNode: function (n, i) {
            for (var u = t.getRoot(n), r = 0, f = u.curSelectedList.length; r < f; r++) if (i === u.curSelectedList[r]) return !0;
            return !1
        }, removeNodeCache: function (n, i) {
            var u = n.data.key.children, r, f;
            if (i[u]) for (r = 0, f = i[u].length; r < f; r++) t.removeNodeCache(n, i[u][r]);
            t.getCache(n).nodes[t.getNodeCacheId(i.tId)] = null
        }, removeSelectedNode: function (n, r) {
            for (var f = t.getRoot(n), u = 0, e = f.curSelectedList.length; u < e; u++) r !== f.curSelectedList[u] && t.getNodeCache(n, f.curSelectedList[u].tId) || (f.curSelectedList.splice(u, 1), n.treeObj.trigger(i.event.UNSELECTED, [n.treeId, r]), u--, e--)
        }, setCache: function (n, t) {
            l[n.treeId] = t
        }, setRoot: function (n, t) {
            c[n.treeId] = t
        }, setZTreeTools: function () {
            for (var n = 0, t = e.zTreeTools.length; n < t; n++) e.zTreeTools[n].apply(this, arguments)
        }, transformToArrayFormat: function (n, i) {
            var e, r, f, o;
            if (!i) return [];
            if (e = n.data.key.children, r = [], u.isArray(i)) for (f = 0, o = i.length; f < o; f++) r.push(i[f]), i[f][e] && (r = r.concat(t.transformToArrayFormat(n, i[f][e]))); else r.push(i), i[e] && (r = r.concat(t.transformToArrayFormat(n, i[e])));
            return r
        }, transformTozTreeFormat: function (n, t) {
            var i, e, o = n.data.simpleData.idKey, f = n.data.simpleData.pIdKey, s = n.data.key.children, h, r;
            if (!o || o == "" || !t) return [];
            if (u.isArray(t)) {
                for (h = [], r = {}, i = 0, e = t.length; i < e; i++) r[t[i][o]] = t[i];
                for (i = 0, e = t.length; i < e; i++) r[t[i][f]] && t[i][o] != t[i][f] ? (r[t[i][f]][s] || (r[t[i][f]][s] = []), r[t[i][f]][s].push(t[i])) : h.push(t[i]);
                return h
            }
            return [t]
        }
    }, o = {
        bindEvent: function () {
            for (var n = 0, t = e.bind.length; n < t; n++) e.bind[n].apply(this, arguments)
        }, unbindEvent: function () {
            for (var n = 0, t = e.unbind.length; n < t; n++) e.unbind[n].apply(this, arguments)
        }, bindTree: function (n) {
            var i = {treeId: n.treeId}, t = n.treeObj;
            n.view.txtSelectedEnable || t.bind("selectstart", s.onSelectStart).css({"-moz-user-select": "-moz-none"});
            t.bind("click", i, o.proxy);
            t.bind("dblclick", i, o.proxy);
            t.bind("mouseover", i, o.proxy);
            t.bind("mouseout", i, o.proxy);
            t.bind("mousedown", i, o.proxy);
            t.bind("mouseup", i, o.proxy);
            t.bind("contextmenu", i, o.proxy)
        }, unbindTree: function (n) {
            var t = n.treeObj;
            t.unbind("selectstart", s.onSelectStart).unbind("click", o.proxy).unbind("dblclick", o.proxy).unbind("mouseover", o.proxy).unbind("mouseout", o.proxy).unbind("mousedown", o.proxy).unbind("mouseup", o.proxy).unbind("contextmenu", o.proxy)
        }, doProxy: function () {
            for (var i = [], t, n = 0, r = e.proxys.length; n < r; n++) if (t = e.proxys[n].apply(this, arguments), i.push(t), t.stop) break;
            return i
        }, proxy: function (n) {
            var c = t.getSetting(n.data.treeId), f, h, i;
            if (!u.uCanDo(c, n)) return !0;
            var e = o.doProxy(n), r = !0, s = !1;
            for (f = 0, h = e.length; f < h; f++) i = e[f], i.nodeEventCallback && (s = !0, r = i.nodeEventCallback.apply(i, [n, i.node]) && r), i.treeEventCallback && (s = !0, r = i.treeEventCallback.apply(i, [n, i.node]) && r);
            return r
        }
    }, s = {
        onSwitchNode: function (n, i) {
            var f = t.getSetting(n.data.treeId);
            if (i.open) {
                if (u.apply(f.callback.beforeCollapse, [f.treeId, i], !0) == !1) return !0;
                t.getRoot(f).expandTriggerFlag = !0;
                r.switchNode(f, i)
            } else {
                if (u.apply(f.callback.beforeExpand, [f.treeId, i], !0) == !1) return !0;
                t.getRoot(f).expandTriggerFlag = !0;
                r.switchNode(f, i)
            }
            return !0
        }, onClickNode: function (n, f) {
            var e = t.getSetting(n.data.treeId),
                o = e.view.autoCancelSelected && (n.ctrlKey || n.metaKey) && t.isSelectedNode(e, f) ? 0 : e.view.autoCancelSelected && (n.ctrlKey || n.metaKey) && e.view.selectedMulti ? 2 : 1;
            return u.apply(e.callback.beforeClick, [e.treeId, f, o], !0) == !1 ? !0 : (o === 0 ? r.cancelPreSelectedNode(e, f) : r.selectNode(e, f, o === 2), e.treeObj.trigger(i.event.CLICK, [n, e.treeId, f, o]), !0)
        }, onZTreeMousedown: function (n, i) {
            var r = t.getSetting(n.data.treeId);
            return u.apply(r.callback.beforeMouseDown, [r.treeId, i], !0) && u.apply(r.callback.onMouseDown, [n, r.treeId, i]), !0
        }, onZTreeMouseup: function (n, i) {
            var r = t.getSetting(n.data.treeId);
            return u.apply(r.callback.beforeMouseUp, [r.treeId, i], !0) && u.apply(r.callback.onMouseUp, [n, r.treeId, i]), !0
        }, onZTreeDblclick: function (n, i) {
            var r = t.getSetting(n.data.treeId);
            return u.apply(r.callback.beforeDblClick, [r.treeId, i], !0) && u.apply(r.callback.onDblClick, [n, r.treeId, i]), !0
        }, onZTreeContextmenu: function (n, i) {
            var r = t.getSetting(n.data.treeId);
            return u.apply(r.callback.beforeRightClick, [r.treeId, i], !0) && u.apply(r.callback.onRightClick, [n, r.treeId, i]), typeof r.callback.onRightClick != "function"
        }, onSelectStart: function (n) {
            var t = n.originalEvent.srcElement.nodeName.toLowerCase();
            return t === "input" || t === "textarea"
        }
    }, u = {
        apply: function (n, t, i) {
            return typeof n == "function" ? n.apply(v, t ? t : []) : i
        }, canAsync: function (n, t) {
            var i = n.data.key.children;
            return n.async.enable && t && t.isParent && !(t.zAsync || t[i] && t[i].length > 0)
        }, clone: function (n) {
            var i, t;
            if (n === null) return null;
            i = u.isArray(n) ? [] : {};
            for (t in n) i[t] = n[t] instanceof Date ? new Date(n[t].getTime()) : typeof n[t] == "object" ? u.clone(n[t]) : n[t];
            return i
        }, eqs: function (n, t) {
            return n.toLowerCase() === t.toLowerCase()
        }, isArray: function (n) {
            return Object.prototype.toString.apply(n) === "[object Array]"
        }, isElement: function (n) {
            return typeof HTMLElement == "object" ? n instanceof HTMLElement : n && typeof n == "object" && n !== null && n.nodeType === 1 && typeof n.nodeName == "string"
        }, $: function (t, i, r) {
            return !i || typeof i == "string" || (r = i, i = ""), typeof t == "string" ? n(t, r ? r.treeObj.get(0).ownerDocument : null) : n("#" + t.tId + i, r ? r.treeObj : null)
        }, getMDom: function (n, t, i) {
            if (!t) return null;
            while (t && t.id !== n.treeId) {
                for (var r = 0, f = i.length; t.tagName && r < f; r++) if (u.eqs(t.tagName, i[r].tagName) && t.getAttribute(i[r].attrName) !== null) return t;
                t = t.parentNode
            }
            return null
        }, getNodeMainDom: function (t) {
            return n(t).parent("li").get(0) || n(t).parentsUntil("li").parent().get(0)
        }, isChildOrSelf: function (t, i) {
            return n(t).closest("#" + i).length > 0
        }, uCanDo: function () {
            return !0
        }
    }, r = {
        addNodes: function (n, e, o, s, h) {
            if (!n.data.keep.leaf || !e || e.isParent) if (u.isArray(s) || (s = [s]), n.data.simpleData.enable && (s = t.transformTozTreeFormat(n, s)), e) {
                var c = f(e, i.id.SWITCH, n), l = f(e, i.id.ICON, n), a = f(e, i.id.UL, n);
                e.open || (r.replaceSwitchClass(e, c, i.folder.CLOSE), r.replaceIcoClass(e, l, i.folder.CLOSE), e.open = !1, a.css({display: "none"}));
                t.addNodesData(n, e, o, s);
                r.createNodes(n, e.level + 1, s, e, o);
                h || r.expandCollapseParentNode(n, e, !0)
            } else t.addNodesData(n, t.getRoot(n), o, s), r.createNodes(n, 0, s, null, o)
        }, appendNodes: function (n, i, u, f, e, o, s) {
            var l, b, h, y;
            if (!u) return [];
            var c = [], a = n.data.key.children, k = f ? f : t.getRoot(n), v = k[a], p, w;
            for ((!v || e >= v.length - u.length) && (e = -1), l = 0, b = u.length; l < b; l++) h = u[l], o && (p = (e === 0 || v.length == u.length) && l == 0, w = e < 0 && l == u.length - 1, t.initNode(n, i, h, f, p, w, s), t.addNodeCache(n, h)), y = [], h[a] && h[a].length > 0 && (y = r.appendNodes(n, i + 1, h[a], h, -1, o, s && h.open)), s && (r.makeDOMNodeMainBefore(c, n, h), r.makeDOMNodeLine(c, n, h), t.getBeforeA(n, h, c), r.makeDOMNodeNameBefore(c, n, h), t.getInnerBeforeA(n, h, c), r.makeDOMNodeIcon(c, n, h), t.getInnerAfterA(n, h, c), r.makeDOMNodeNameAfter(c, n, h), t.getAfterA(n, h, c), h.isParent && h.open && r.makeUlHtml(n, h, c, y.join("")), r.makeDOMNodeMainAfter(c, n, h), t.addCreatedNode(n, h));
            return c
        }, appendParentULDom: function (n, t) {
            var o = [], u = f(t, n), e, s, h;
            u.get(0) || !t.parentTId || (r.appendParentULDom(n, t.getParentNode()), u = f(t, n));
            e = f(t, i.id.UL, n);
            e.get(0) && e.remove();
            s = n.data.key.children;
            h = r.appendNodes(n, t.level + 1, t[s], t, -1, !1, !0);
            r.makeUlHtml(n, t, o, h.join(""));
            u.append(o.join(""))
        }, asyncNode: function (e, o, s, h) {
            var c, v, b, a, l, y, p, w;
            if (o && !o.isParent) return u.apply(h), !1;
            if (o && o.isAjaxing) return !1;
            if (u.apply(e.callback.beforeAsync, [e.treeId, o], !0) == !1) return u.apply(h), !1;
            for (o && (o.isAjaxing = !0, b = f(o, i.id.ICON, e), b.attr({
                style: "",
                "class": i.className.BUTTON + " " + i.className.ICO_LOADING
            })), a = {}, c = 0, v = e.async.autoParam.length; o && c < v; c++) l = e.async.autoParam[c].split("="), y = l, l.length > 1 && (y = l[1], l = l[0]), a[y] = o[l];
            if (u.isArray(e.async.otherParam)) for (c = 0, v = e.async.otherParam.length; c < v; c += 2) a[e.async.otherParam[c]] = e.async.otherParam[c + 1]; else for (p in e.async.otherParam) a[p] = e.async.otherParam[p];
            return w = t.getRoot(e)._ver, n.ajax({
                contentType: e.async.contentType,
                cache: !1,
                type: e.async.type,
                url: u.apply(e.async.url, [e.treeId, o], e.async.url),
                data: e.async.contentType.indexOf("application/json") > -1 ? JSON.stringify(a) : a,
                dataType: e.async.dataType,
                success: function (msg) {
                    if (w == t.getRoot(e)._ver) {
                        var newNodes = [];
                        try {
                            newNodes = msg && msg.length != 0 ? typeof msg == "string" ? eval("(" + msg + ")") : msg : []
                        } catch (err) {
                            newNodes = msg
                        }
                        o && (o.isAjaxing = null, o.zAsync = !0);
                        r.setNodeLineIcos(e, o);
                        newNodes && newNodes !== "" ? (newNodes = u.apply(e.async.dataFilter, [e.treeId, o, newNodes], newNodes), r.addNodes(e, o, -1, !newNodes ? [] : u.clone(newNodes), !!s)) : r.addNodes(e, o, -1, [], !!s);
                        e.treeObj.trigger(i.event.ASYNC_SUCCESS, [e.treeId, o, msg]);
                        u.apply(h)
                    }
                },
                error: function (n, u, f) {
                    w == t.getRoot(e)._ver && (o && (o.isAjaxing = null), r.setNodeLineIcos(e, o), e.treeObj.trigger(i.event.ASYNC_ERROR, [e.treeId, o, n, u, f]))
                }
            }), !0
        }, cancelPreSelectedNode: function (n, r, u) {
            for (var s = t.getRoot(n).curSelectedList, o, e = s.length - 1; e >= 0; e--) if (o = s[e], r === o || !r && (!u || u !== o)) if (f(o, i.id.A, n).removeClass(i.node.CURSELECTED), r) {
                t.removeSelectedNode(n, r);
                break
            } else s.splice(e, 1), n.treeObj.trigger(i.event.UNSELECTED, [n.treeId, o])
        }, createNodeCallback: function (n) {
            var r, f;
            if (!!n.callback.onNodeCreated || !!n.view.addDiyDom) for (r = t.getRoot(n); r.createdNodes.length > 0;) f = r.createdNodes.shift(), u.apply(n.view.addDiyDom, [n.treeId, f]), !n.callback.onNodeCreated || n.treeObj.trigger(i.event.NODECREATED, [n.treeId, f])
        }, createNodes: function (u, e, o, s, h) {
            var l, c, a, v;
            if (o && o.length != 0) {
                var y = t.getRoot(u), p = u.data.key.children, w = !s || s.open || !!f(s[p][0], u).get(0);
                y.createdNodes = [];
                l = r.appendNodes(u, e, o, s, h, !0, w);
                s ? (v = f(s, i.id.UL, u), v.get(0) && (c = v)) : c = u.treeObj;
                c && (h >= 0 && (a = c.children()[h]), h >= 0 && a ? n(a).before(l.join("")) : c.append(l.join("")));
                r.createNodeCallback(u)
            }
        }, destroy: function (n) {
            n && (t.initCache(n), t.initRoot(n), o.unbindTree(n), o.unbindEvent(n), n.treeObj.empty(), delete h[n.treeId])
        }, expandCollapseNode: function (n, e, o, s, h) {
            var y = t.getRoot(n), c = n.data.key.children, p, a;
            if (!e) {
                u.apply(h, []);
                return
            }
            if (y.expandTriggerFlag && (a = h, p = function () {
                a && a();
                e.open ? n.treeObj.trigger(i.event.EXPAND, [n.treeId, e]) : n.treeObj.trigger(i.event.COLLAPSE, [n.treeId, e])
            }, h = p, y.expandTriggerFlag = !1), !e.open && e.isParent && (!f(e, i.id.UL, n).get(0) || e[c] && e[c].length > 0 && !f(e[c][0], n).get(0)) && (r.appendParentULDom(n, e), r.createNodeCallback(n)), e.open == o) {
                u.apply(h, []);
                return
            }
            var l = f(e, i.id.UL, n), w = f(e, i.id.SWITCH, n), v = f(e, i.id.ICON, n);
            e.isParent ? (e.open = !e.open, e.iconOpen && e.iconClose && v.attr("style", r.makeNodeIcoStyle(n, e)), e.open ? (r.replaceSwitchClass(e, w, i.folder.OPEN), r.replaceIcoClass(e, v, i.folder.OPEN), s == !1 || n.view.expandSpeed == "" ? (l.show(), u.apply(h, [])) : e[c] && e[c].length > 0 ? l.slideDown(n.view.expandSpeed, h) : (l.show(), u.apply(h, []))) : (r.replaceSwitchClass(e, w, i.folder.CLOSE), r.replaceIcoClass(e, v, i.folder.CLOSE), s != !1 && n.view.expandSpeed != "" && e[c] && e[c].length > 0 ? l.slideUp(n.view.expandSpeed, h) : (l.hide(), u.apply(h, [])))) : u.apply(h, [])
        }, expandCollapseParentNode: function (n, t, i, u, f) {
            if (t) {
                if (t.parentTId) r.expandCollapseNode(n, t, i, u); else {
                    r.expandCollapseNode(n, t, i, u, f);
                    return
                }
                t.parentTId && r.expandCollapseParentNode(n, t.getParentNode(), i, u, f)
            }
        }, expandCollapseSonNode: function (n, i, u, f, e) {
            var l = t.getRoot(n), h = n.data.key.children, s = i ? i[h] : l[h], a = i ? !1 : f,
                v = t.getRoot(n).expandTriggerFlag, o, c;
            if (t.getRoot(n).expandTriggerFlag = !1, s) for (o = 0, c = s.length; o < c; o++) s[o] && r.expandCollapseSonNode(n, s[o], u, a);
            t.getRoot(n).expandTriggerFlag = v;
            r.expandCollapseNode(n, i, u, f, e)
        }, isSelectedNode: function (n, i) {
            if (!i) return !1;
            for (var u = t.getRoot(n).curSelectedList, r = u.length - 1; r >= 0; r--) if (i === u[r]) return !0;
            return !1
        }, makeDOMNodeIcon: function (n, u, f) {
            var e = t.getNodeName(u, f),
                o = u.view.nameIsHTML ? e : e.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
            n.push("<span id='", f.tId, i.id.ICON, "' title='' treeNode", i.id.ICON, " class='", r.makeNodeIcoClass(u, f), "' style='", r.makeNodeIcoStyle(u, f), "'><\/span><span id='", f.tId, i.id.SPAN, "' class='", i.className.NAME, "'>", o, "<\/span>")
        }, makeDOMNodeLine: function (n, t, u) {
            n.push("<span id='", u.tId, i.id.SWITCH, "' title='' class='", r.makeNodeLineClass(t, u), "' treeNode", i.id.SWITCH, "><\/span>")
        }, makeDOMNodeMainAfter: function (n) {
            n.push("<\/li>")
        }, makeDOMNodeMainBefore: function (n, t, r) {
            n.push("<li id='", r.tId, "' class='", i.className.LEVEL, r.level, "' tabindex='0' hidefocus='true' treenode>")
        }, makeDOMNodeNameAfter: function (n) {
            n.push("<\/a>")
        }, makeDOMNodeNameBefore: function (n, f, e) {
            var h = t.getNodeTitle(f, e), o = r.makeNodeUrl(f, e), c = r.makeNodeFontCss(f, e), l = [], s;
            for (s in c) l.push(s, ":", c[s], ";");
            n.push("<a id='", e.tId, i.id.A, "' class='", i.className.LEVEL, e.level, "' treeNode", i.id.A, ' onclick="', e.click || "", '" ', o != null && o.length > 0 ? "href='" + o + "'" : "", " target='", r.makeNodeTarget(e), "' style='", l.join(""), "'");
            u.apply(f.view.showTitle, [f.treeId, e], f.view.showTitle) && h && n.push("title='", h.replace(/'/g, "&#39;").replace(/</g, "&lt;").replace(/>/g, "&gt;"), "'");
            n.push(">")
        }, makeNodeFontCss: function (n, t) {
            var i = u.apply(n.view.fontCss, [n.treeId, t], n.view.fontCss);
            return i && typeof i != "function" ? i : {}
        }, makeNodeIcoClass: function (n, t) {
            var r = ["ico"];
            return t.isAjaxing || (r[0] = (t.iconSkin ? t.iconSkin + "_" : "") + r[0], t.isParent ? r.push(t.open ? i.folder.OPEN : i.folder.CLOSE) : r.push(i.folder.DOCU)), i.className.BUTTON + " " + r.join("_")
        }, makeNodeIcoStyle: function (n, t) {
            var i = [], r;
            return t.isAjaxing || (r = t.isParent && t.iconOpen && t.iconClose ? t.open ? t.iconOpen : t.iconClose : t[n.data.key.icon], r && i.push("background:url(", r, ") 0 0 no-repeat;"), n.view.showIcon != !1 && u.apply(n.view.showIcon, [n.treeId, t], !0) || i.push("width:0px;height:0px;")), i.join("")
        }, makeNodeLineClass: function (n, t) {
            var u = [];
            return n.view.showLine ? t.level == 0 && t.isFirstNode && t.isLastNode ? u.push(i.line.ROOT) : t.level == 0 && t.isFirstNode ? u.push(i.line.ROOTS) : t.isLastNode ? u.push(i.line.BOTTOM) : u.push(i.line.CENTER) : u.push(i.line.NOLINE), t.isParent ? u.push(t.open ? i.folder.OPEN : i.folder.CLOSE) : u.push(i.folder.DOCU), r.makeNodeLineClassEx(t) + u.join("_")
        }, makeNodeLineClassEx: function (n) {
            return i.className.BUTTON + " " + i.className.LEVEL + n.level + " " + i.className.SWITCH + " "
        }, makeNodeTarget: function (n) {
            return n.target || "_blank"
        }, makeNodeUrl: function (n, t) {
            var i = n.data.key.url;
            return t[i] ? t[i] : null
        }, makeUlHtml: function (n, t, u, f) {
            u.push("<ul id='", t.tId, i.id.UL, "' class='", i.className.LEVEL, t.level, " ", r.makeUlLineClass(n, t), "' style='display:", t.open ? "block" : "none", "'>");
            u.push(f);
            u.push("<\/ul>")
        }, makeUlLineClass: function (n, t) {
            return n.view.showLine && !t.isLastNode ? i.line.LINE : ""
        }, removeChildNodes: function (n, u) {
            var s, e, o, h, c, l;
            if (u && (s = n.data.key.children, e = u[s], e)) {
                for (o = 0, h = e.length; o < h; o++) t.removeNodeCache(n, e[o]);
                t.removeSelectedNode(n);
                delete u[s];
                n.data.keep.parent ? f(u, i.id.UL, n).empty() : (u.isParent = !1, u.open = !1, c = f(u, i.id.SWITCH, n), l = f(u, i.id.ICON, n), r.replaceSwitchClass(u, c, i.folder.DOCU), r.replaceIcoClass(u, l, i.folder.DOCU), f(u, i.id.UL, n).remove())
            }
        }, scrollIntoView: function (n) {
            n && (Element.prototype.scrollIntoViewIfNeeded || (Element.prototype.scrollIntoViewIfNeeded = function (n) {
                function s(t, i, r, u) {
                    return !1 === n || r <= t + u && t <= i + u ? Math.min(r, Math.max(i, t)) : (i + r) / 2
                }

                function r(n, t, u, f) {
                    return {
                        left: n,
                        top: t,
                        width: u,
                        height: f,
                        right: n + u,
                        bottom: t + f,
                        translate: function (i, e) {
                            return r(i + n, e + t, u, f)
                        },
                        relativeFromTo: function (e, o) {
                            var s = n, h = t;
                            if (e = e.offsetParent, o = o.offsetParent, e === o) return i;
                            for (; e; e = e.offsetParent) s += e.offsetLeft + e.clientLeft, h += e.offsetTop + e.clientTop;
                            for (; o; o = o.offsetParent) s -= o.offsetLeft + o.clientLeft, h -= o.offsetTop + o.clientTop;
                            return r(s, h, u, f)
                        }
                    }
                }

                for (var t, f = this, i = r(this.offsetLeft, this.offsetTop, this.offsetWidth, this.offsetHeight), e, o; u.isElement(t = f.parentNode);) e = t.offsetLeft + t.clientLeft, o = t.offsetTop + t.clientTop, i = i.relativeFromTo(f, t).translate(-e, -o), t.scrollLeft = s(t.scrollLeft, i.right - t.clientWidth, i.left, t.clientWidth), t.scrollTop = s(t.scrollTop, i.bottom - t.clientHeight, i.top, t.clientHeight), i = i.translate(e - t.scrollLeft, o - t.scrollTop), f = t
            }), n.scrollIntoViewIfNeeded())
        }, setFirstNode: function (n, t) {
            var i = n.data.key.children, r = t[i].length;
            r > 0 && (t[i][0].isFirstNode = !0)
        }, setLastNode: function (n, t) {
            var i = n.data.key.children, r = t[i].length;
            r > 0 && (t[i][r - 1].isLastNode = !0)
        }, removeNode: function (n, u) {
            var y = t.getRoot(n), o = n.data.key.children, e = u.parentTId ? u.getParentNode() : y, c, p, l, h, v, a, s,
                w;
            if (u.isFirstNode = !1, u.isLastNode = !1, u.getPreNode = function () {
                return null
            }, u.getNextNode = function () {
                return null
            }, t.getNodeCache(n, u.tId)) {
                for (f(u, n).remove(), t.removeNodeCache(n, u), t.removeSelectedNode(n, u), c = 0, p = e[o].length; c < p; c++) if (e[o][c].tId == u.tId) {
                    e[o].splice(c, 1);
                    break
                }
                r.setFirstNode(n, e);
                r.setLastNode(n, e);
                a = e[o].length;
                n.data.keep.parent || a != 0 ? n.view.showLine && a > 0 && (s = e[o][a - 1], l = f(s, i.id.UL, n), h = f(s, i.id.SWITCH, n), v = f(s, i.id.ICON, n), e == y ? e[o].length == 1 ? r.replaceSwitchClass(s, h, i.line.ROOT) : (w = f(e[o][0], i.id.SWITCH, n), r.replaceSwitchClass(e[o][0], w, i.line.ROOTS), r.replaceSwitchClass(s, h, i.line.BOTTOM)) : r.replaceSwitchClass(s, h, i.line.BOTTOM), l.removeClass(i.line.LINE)) : (e.isParent = !1, e.open = !1, l = f(e, i.id.UL, n), h = f(e, i.id.SWITCH, n), v = f(e, i.id.ICON, n), r.replaceSwitchClass(e, h, i.folder.DOCU), r.replaceIcoClass(e, v, i.folder.DOCU), l.css("display", "none"))
            }
        }, replaceIcoClass: function (n, t, r) {
            var f, u;
            if (t && !n.isAjaxing && (f = t.attr("class"), f != undefined)) {
                u = f.split("_");
                switch (r) {
                    case i.folder.OPEN:
                    case i.folder.CLOSE:
                    case i.folder.DOCU:
                        u[u.length - 1] = r
                }
                t.attr("class", u.join("_"))
            }
        }, replaceSwitchClass: function (n, t, u) {
            var e, f;
            if (t && (e = t.attr("class"), e != undefined)) {
                f = e.split("_");
                switch (u) {
                    case i.line.ROOT:
                    case i.line.ROOTS:
                    case i.line.CENTER:
                    case i.line.BOTTOM:
                    case i.line.NOLINE:
                        f[0] = r.makeNodeLineClassEx(n) + u;
                        break;
                    case i.folder.OPEN:
                    case i.folder.CLOSE:
                    case i.folder.DOCU:
                        f[1] = u
                }
                t.attr("class", f.join("_"));
                u !== i.folder.DOCU ? t.removeAttr("disabled") : t.attr("disabled", "disabled")
            }
        }, selectNode: function (n, u, e) {
            e || r.cancelPreSelectedNode(n, null, u);
            f(u, i.id.A, n).addClass(i.node.CURSELECTED);
            t.addSelectedNode(n, u);
            n.treeObj.trigger(i.event.SELECTED, [n.treeId, u])
        }, setNodeFontCss: function (n, t) {
            var e = f(t, i.id.A, n), u = r.makeNodeFontCss(n, t);
            u && e.css(u)
        }, setNodeLineIcos: function (n, t) {
            if (t) {
                var u = f(t, i.id.SWITCH, n), o = f(t, i.id.UL, n), e = f(t, i.id.ICON, n), s = r.makeUlLineClass(n, t);
                s.length == 0 ? o.removeClass(i.line.LINE) : o.addClass(s);
                u.attr("class", r.makeNodeLineClass(n, t));
                t.isParent ? u.removeAttr("disabled") : u.attr("disabled", "disabled");
                e.removeAttr("style");
                e.attr("style", r.makeNodeIcoStyle(n, t));
                e.attr("class", r.makeNodeIcoClass(n, t))
            }
        }, setNodeName: function (n, r) {
            var o = t.getNodeTitle(n, r), e = f(r, i.id.SPAN, n), s;
            e.empty();
            n.view.nameIsHTML ? e.html(t.getNodeName(n, r)) : e.text(t.getNodeName(n, r));
            u.apply(n.view.showTitle, [n.treeId, r], n.view.showTitle) && (s = f(r, i.id.A, n), s.attr("title", o ? o : ""))
        }, setNodeTarget: function (n, t) {
            var u = f(t, i.id.A, n);
            u.attr("target", r.makeNodeTarget(t))
        }, setNodeUrl: function (n, t) {
            var e = f(t, i.id.A, n), u = r.makeNodeUrl(n, t);
            u == null || u.length == 0 ? e.removeAttr("href") : e.attr("href", u)
        }, switchNode: function (n, t) {
            if (t.open || !u.canAsync(n, t)) r.expandCollapseNode(n, t, !t.open); else if (n.async.enable) {
                if (!r.asyncNode(n, t)) {
                    r.expandCollapseNode(n, t, !t.open);
                    return
                }
            } else t && r.expandCollapseNode(n, t, !t.open)
        }
    };
    n.fn.zTree = {
        consts: {
            className: {
                BUTTON: "button",
                LEVEL: "level",
                ICO_LOADING: "ico_loading",
                SWITCH: "switch",
                NAME: "node_name"
            },
            event: {
                NODECREATED: "ztree_nodeCreated",
                CLICK: "ztree_click",
                EXPAND: "ztree_expand",
                COLLAPSE: "ztree_collapse",
                ASYNC_SUCCESS: "ztree_async_success",
                ASYNC_ERROR: "ztree_async_error",
                REMOVE: "ztree_remove",
                SELECTED: "ztree_selected",
                UNSELECTED: "ztree_unselected"
            },
            id: {A: "_a", ICON: "_ico", SPAN: "_span", SWITCH: "_switch", UL: "_ul"},
            line: {ROOT: "root", ROOTS: "roots", CENTER: "center", BOTTOM: "bottom", NOLINE: "noline", LINE: "line"},
            folder: {OPEN: "open", CLOSE: "close", DOCU: "docu"},
            node: {CURSELECTED: "curSelectedNode"}
        }, _z: {tools: u, view: r, event: o, data: t}, getZTreeObj: function (n) {
            var i = t.getZTreeTools(n);
            return i ? i : null
        }, destroy: function (n) {
            if (!!n && n.length > 0) r.destroy(t.getSetting(n)); else for (var i in h) r.destroy(h[i])
        }, init: function (e, s, c) {
            var l = u.clone(a), v, y, p;
            return n.extend(!0, l, s), l.treeId = e.attr("id"), l.treeObj = e, l.treeObj.empty(), h[l.treeId] = l, typeof document.body.style.maxHeight == "undefined" && (l.view.expandSpeed = ""), t.initRoot(l), v = t.getRoot(l), y = l.data.key.children, c = c ? u.clone(u.isArray(c) ? c : [c]) : [], v[y] = l.data.simpleData.enable ? t.transformTozTreeFormat(l, c) : c, t.initCache(l), o.unbindTree(l), o.bindTree(l), o.unbindEvent(l), o.bindEvent(l), p = {
                setting: l, addNodes: function (n, t, i, f) {
                    function s() {
                        r.addNodes(l, n, t, o, f == !0)
                    }

                    var e, o;
                    return (n || (n = null), n && !n.isParent && l.data.keep.leaf) ? null : (e = parseInt(t, 10), isNaN(e) ? (f = !!i, i = t, t = -1) : t = e, !i) ? null : (o = u.clone(u.isArray(i) ? i : [i]), u.canAsync(l, n) ? r.asyncNode(l, n, f, s) : s(), o)
                }, cancelSelectedNode: function (n) {
                    r.cancelPreSelectedNode(l, n)
                }, destroy: function () {
                    r.destroy(l)
                }, expandAll: function (n) {
                    return n = !!n, r.expandCollapseSonNode(l, null, n, !0), n
                }, expandNode: function (n, i, e, o, s) {
                    function h() {
                        var t = f(n, l).get(0);
                        t && o !== !1 && r.scrollIntoView(t)
                    }

                    return !n || !n.isParent ? null : (i !== !0 && i !== !1 && (i = !n.open), s = !!s, s && i && u.apply(l.callback.beforeExpand, [l.treeId, n], !0) == !1) ? null : s && !i && u.apply(l.callback.beforeCollapse, [l.treeId, n], !0) == !1 ? null : (i && n.parentTId && r.expandCollapseParentNode(l, n.getParentNode(), i, !1), i === n.open && !e) ? null : (t.getRoot(l).expandTriggerFlag = s, !u.canAsync(l, n) && e ? r.expandCollapseSonNode(l, n, i, !0, h) : (n.open = !i, r.switchNode(this.setting, n), h()), i)
                }, getNodes: function () {
                    return t.getNodes(l)
                }, getNodeByParam: function (n, i, r) {
                    return n ? t.getNodeByParam(l, r ? r[l.data.key.children] : t.getNodes(l), n, i) : null
                }, getNodeByTId: function (n) {
                    return t.getNodeCache(l, n)
                }, getNodesByParam: function (n, i, r) {
                    return n ? t.getNodesByParam(l, r ? r[l.data.key.children] : t.getNodes(l), n, i) : null
                }, getNodesByParamFuzzy: function (n, i, r) {
                    return n ? t.getNodesByParamFuzzy(l, r ? r[l.data.key.children] : t.getNodes(l), n, i) : null
                }, getNodesByFilter: function (n, i, r, u) {
                    return (i = !!i, !n || typeof n != "function") ? i ? null : [] : t.getNodesByFilter(l, r ? r[l.data.key.children] : t.getNodes(l), n, i, u)
                }, getNodeIndex: function (n) {
                    var r, u, i, f;
                    if (!n) return null;
                    for (r = l.data.key.children, u = n.parentTId ? n.getParentNode() : t.getRoot(l), i = 0, f = u[r].length; i < f; i++) if (u[r][i] == n) return i;
                    return -1
                }, getSelectedNodes: function () {
                    for (var i = [], r = t.getRoot(l).curSelectedList, n = 0, u = r.length; n < u; n++) i.push(r[n]);
                    return i
                }, isSelectedNode: function (n) {
                    return t.isSelectedNode(l, n)
                }, reAsyncChildNodes: function (n, u, e) {
                    var s, o, h, c, a;
                    if (this.setting.async.enable) {
                        if (s = !n, s && (n = t.getRoot(l)), u == "refresh") {
                            for (o = this.setting.data.key.children, h = 0, c = n[o] ? n[o].length : 0; h < c; h++) t.removeNodeCache(l, n[o][h]);
                            t.removeSelectedNode(l);
                            n[o] = [];
                            s ? this.setting.treeObj.empty() : (a = f(n, i.id.UL, l), a.empty())
                        }
                        r.asyncNode(this.setting, s ? null : n, !!e)
                    }
                }, refresh: function () {
                    this.setting.treeObj.empty();
                    var n = t.getRoot(l), i = n[l.data.key.children];
                    t.initRoot(l);
                    n[l.data.key.children] = i;
                    t.initCache(l);
                    r.createNodes(l, 0, n[l.data.key.children], null, -1)
                }, removeChildNodes: function (n) {
                    if (!n) return null;
                    var i = l.data.key.children, t = n[i];
                    return r.removeChildNodes(l, n), t ? t : null
                }, removeNode: function (n, t) {
                    n && ((t = !!t, t && u.apply(l.callback.beforeRemove, [l.treeId, n], !0) == !1) || (r.removeNode(l, n), t && this.setting.treeObj.trigger(i.event.REMOVE, [l.treeId, n])))
                }, selectNode: function (n, t, i) {
                    function e() {
                        if (!i) {
                            var t = f(n, l).get(0);
                            r.scrollIntoView(t)
                        }
                    }

                    if (n && u.uCanDo(l)) {
                        if (t = l.view.selectedMulti && t, n.parentTId) r.expandCollapseParentNode(l, n.getParentNode(), !0, !1, e); else if (!i) try {
                            f(n, l).focus().blur()
                        } catch (o) {
                        }
                        r.selectNode(l, n, t)
                    }
                }, transformTozTreeNodes: function (n) {
                    return t.transformTozTreeFormat(l, n)
                }, transformToArray: function (n) {
                    return t.transformToArrayFormat(l, n)
                }, updateNode: function (n) {
                    if (n) {
                        var t = f(n, l);
                        t.get(0) && u.uCanDo(l) && (r.setNodeName(l, n), r.setNodeTarget(l, n), r.setNodeUrl(l, n), r.setNodeLineIcos(l, n), r.setNodeFontCss(l, n))
                    }
                }
            }, v.treeTools = p, t.setZTreeTools(l, p), v[y] && v[y].length > 0 ? r.createNodes(l, 0, v[y], null, -1) : l.async.enable && l.async.url && l.async.url !== "" && r.asyncNode(l), p
        }
    };
    var v = n.fn.zTree, f = u.$, i = v.consts
})(jQuery);
jQuery.fn.extend({
    daterangepickerplus: function (n) {
        var i = this, t = {
            singleDatePicker: !0,
            showDropdowns: !0,
            minDate: "1900-01-01",
            maxDate: "2199-12-31",
            ranges: {
                "今日": [moment().startOf("day"), moment()],
                "昨日": [moment().subtract("days", 1).startOf("day"), moment().subtract("days", 1).endOf("day")],
                "最近24小时": [moment().subtract("hour", 24), moment()],
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
    this.allAlarm = [{id: "", text: "所有报警类型"}, {id: "SOS", text: "紧急报警"}, {
        id: "PowerCut",
        text: "断电报警"
    }, {id: "Vibration", text: "振动报警"}, {id: "In", text: "进围栏报警"}, {id: "Out", text: "出围栏报警"}, {
        id: "InOut",
        text: "进出围栏报警"
    }, {id: "OverSpeed", text: "超速报警"}, {id: "Move", text: "位移报警"}, {id: "Low", text: "低电报警"}, {
        id: "Recovery",
        text: "供电恢复报警"
    }, {id: "BreakDown", text: "强拆报警"}, {id: "DropOff", text: "脱落报警"}, {
        id: "Collect",
        text: "聚集报警"
    }, {id: "DangerPoint", text: "风险点报警"}];
    var n = {};
    $.each(this.allAlarm, function (t, i) {
        i.id && (n[i.id] = i.text)
    });
    this.getName = function (t) {
        return n[t] || t
    }
}, alarm = new alarmInfo;
if (dtc.prototype = {
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
}, selectGroupZTree = function (n, t) {
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
}, selectGroupZTree.prototype = {
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
}, getObjFromCategory = {
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
}, button_modal.prototype = {
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
}, $(function () {
    function n() {
        $("#btnModypwd").on("click", function () {
            if ($("#pwdModal").data("bootstrapValidator").validate(), $("#pwdModal").data("bootstrapValidator").isValid()) {
                var n = $("#pwdModal").toJson();
                $.ajax({
                    url: "/api/user/UpdatePassword",
                    async: !0,
                    dataType: "json",
                    type: "post",
                    contentType: "application/json",
                    data: JSON.stringify(n),
                    success: function (n) {
                        n.Status == 0 ? $.alert({
                            title: "提示",
                            content: n.ErrorMessage
                        }) : n.Result == !0 ? $.alert({
                            title: "提示", content: "密码修改成功!", onClose: function () {
                                $("#pwdModal").modal("hide")
                            }
                        }) : $.alert({title: "提示", content: "密码修改失败!"})
                    },
                    error: function () {
                        $.alert({title: "提示", content: "密码修改失败!"})
                    }
                })
            }
        });
        $("#pwdModal").on("hidden.bs.modal", function () {
            $("#pwdModal").bootstrapValidator("resetForm", !0)
        });
        $("#pwdModal").bootstrapValidator({
            message: "This value is not valid",
            feedbackIcons: {
                valid: "glyphicon glyphicon-ok",
                invalid: "glyphicon glyphicon-remove",
                validating: "glyphicon glyphicon-refresh"
            },
            fields: {
                oldpwd: {validators: {notEmpty: {message: "旧密码不能为空"}}},
                password: {validators: {notEmpty: {message: "新密码不能为空"}}},
                confirmPassword: {
                    validators: {
                        notEmpty: {message: "确认密码不能为空"},
                        identical: {field: "password", message: "两次输入的密码不一致"}
                    }
                }
            },
            excluded: [":disabled"]
        });
        $("li.alarm_audio_li").find("a").on("click", function () {
            var n = $(this), t = n.find("i");
            t.hasClass("glyphicon-volume-off") ? $.updateSever({
                para: {EnabledAlarmAudio: !0},
                url: "/api/User/SaveAlarmAudio",
                success: function () {
                    $.alert({title: "提示", content: "已打开报警声音！"});
                    t.addClass("glyphicon-volume-up").removeClass("glyphicon-volume-off");
                    n.attr("title", "关闭报警声音");
                    $("#alarmAudio").addClass("up")
                }
            }) : $.updateSever({
                para: {EnabledAlarmAudio: !1}, url: "/api/User/SaveAlarmAudio", success: function () {
                    $.alert({title: "提示", content: "已关闭报警声音！"});
                    t.addClass("glyphicon-volume-off").removeClass("glyphicon-volume-up");
                    n.attr("title", "打开报警声音");
                    $("#alarmAudio").removeClass("up")
                }
            })
        })
    }

    $("#pwdModal").length > 0 && n();
    alarmInfo = {
        lasttime: null,
        roundTime: 3e4,
        deviceNumber: null,
        curdn: $("#deviceNumber").attr("dn"),
        title: "",
        table: null,
        LastTime: null,
        load: function () {
            $.updateSever({
                para: {LastTime: alarmInfo.LastTime, DeviceNumber: alarmInfo.curdn},
                alertError: !1,
                url: "/api/monitor/RoundAlarm",
                success: function (n) {
                    if (alarmInfo.data = n || [], $(".alarm_li").find("span").html(n.Total), n.Alarms.length > 0) {
                        $("#ShowAlarms").length > 0 && $("#ShowAlarms").alert("close");
                        var t = $("<div><\/div>").attr("id", "ShowAlarms").addClass("alert alert-danger").css({
                            position: "fixed",
                            bottom: "0px",
                            right: "5px",
                            "z-index": "1049"
                        });
                        $('<a href="#" class="close" data-dismiss="alert">&times;<\/a>').css({
                            position: "absolute",
                            top: "5px",
                            right: "5px"
                        }).appendTo(t);
                        $.each(n.Alarms, function (n, i) {
                            var r = i.AlarmType == 0 ? i.Content : i.AlarmText;
                            $('<div><span style="padding-right:20px;">' + i.OrgName + '<\/span><span style="padding-right:20px;">' + i.Aliase + "<\/span><span>" + r + "<\/span><\/div>").css({"padding-right": "10px"}).appendTo(t)
                        });
                        $(t).appendTo($("body"));
                        $("#alarmAudio").hasClass("up") && $("#alarmAudio")[0].play();
                        setTimeout(function () {
                            $("#ShowAlarms").length > 0 && $("#ShowAlarms").alert("close")
                        }, 1e4)
                    }
                    alarmInfo.LastTime = n.LastTime;
                    setTimeout(alarmInfo.load, alarmInfo.roundTime)
                },
                error: function () {
                    setTimeout(alarmInfo.load, alarmInfo.roundTime)
                }
            })
        }
    };
    alarmInfo.load();
    var t = $("body").addModel({
        id: "alarmModal",
        width: "900px",
        height: "600px",
        text: "报警详情",
        body: '<div id="alarm_table"><\/div>'
    }).on("hidden.bs.modal", function () {
        alarmInfo.deviceNumber = null;
        alarmInfo.title = "";
        alarmInfo.table && alarmInfo.table.clear()
    }).on("show.bs.modal", function () {
        var n = alarmInfo.title == "" ? "最新报警" : alarmInfo.title + "报警详情";
        t.find(".modal-title").html(n)
    }).on("shown.bs.modal", function () {
        if (alarmInfo.table) {
            alarmInfo.table._dataTable.ajax.reload();
            return
        }
        alarmInfo.table = new dtc({
            pid: "alarm_table",
            columns: ["OrgName", "DeviceName", "DeviceNumber", "AlarmTime", "ReceiveTime", "AlarmText"],
            name: "monitor",
            handleBefore: function (n) {
                n.DeviceNumber = alarmInfo.deviceNumber || alarmInfo.curdn
            },
            url: "/api/monitor/ShowAlarm",
            parameters: {
                DeviceName: {title: "设备名/车牌号"},
                DeviceNumber: {title: "设备号"},
                AlarmTime: {
                    title: "报警时间", tableRender: function (n) {
                        return $.toDateTime(n)
                    }
                },
                OrgName: {title: "所属组"},
                ReceiveTime: {
                    title: "信号时间", tableRender: function (n) {
                        return $.toDateTime(n)
                    }
                },
                AlarmText: {title: "报警类型"}
            }
        }, {rowId: "DeviceNumber", lengthChange: !1, nofooter: !0})
    })
}), "undefined" == typeof jQuery) throw new Error("AdminLTE requires jQuery");
$.AdminLTE = {};
$.AdminLTE.options = {
    navbarMenuSlimscroll: !0,
    navbarMenuSlimscrollWidth: "3px",
    navbarMenuHeight: "200px",
    animationSpeed: 500,
    sidebarToggleSelector: "[data-toggle='offcanvas']",
    sidebarPushMenu: !0,
    sidebarSlimScroll: !0,
    sidebarExpandOnHover: !1,
    enableBoxRefresh: !0,
    enableBSToppltip: !0,
    BSTooltipSelector: "[data-toggle='tooltip']",
    enableFastclick: !1,
    enableControlTreeView: !0,
    enableControlSidebar: !0,
    controlSidebarOptions: {
        toggleBtnSelector: "[data-toggle='control-sidebar']",
        selector: ".control-sidebar",
        slide: !0
    },
    enableBoxWidget: !0,
    boxWidgetOptions: {
        boxWidgetIcons: {collapse: "fa-minus", open: "fa-plus", remove: "fa-times"},
        boxWidgetSelectors: {remove: '[data-widget="remove"]', collapse: '[data-widget="collapse"]'}
    },
    directChat: {enable: !0, contactToggleSelector: '[data-widget="chat-pane-toggle"]'},
    colors: {
        lightBlue: "#3c8dbc",
        red: "#f56954",
        green: "#00a65a",
        aqua: "#00c0ef",
        yellow: "#f39c12",
        blue: "#0073b7",
        navy: "#001F3F",
        teal: "#39CCCC",
        olive: "#3D9970",
        lime: "#01FF70",
        orange: "#FF851B",
        fuchsia: "#F012BE",
        purple: "#8E24AA",
        maroon: "#D81B60",
        black: "#222222",
        gray: "#d2d6de"
    },
    screenSizes: {xs: 480, sm: 768, md: 992, lg: 1200}
};
$(function () {
    "use strict";
    $("body").removeClass("hold-transition");
    "undefined" != typeof AdminLTEOptions && $.extend(!0, $.AdminLTE.options, AdminLTEOptions);
    var n = $.AdminLTE.options;
    _init();
    $.AdminLTE.layout.activate();
    n.enableControlTreeView && $.AdminLTE.tree(".sidebar");
    n.enableControlSidebar && $.AdminLTE.controlSidebar.activate();
    n.navbarMenuSlimscroll && "undefined" != typeof $.fn.slimscroll && $(".navbar .menu").slimscroll({
        height: n.navbarMenuHeight,
        alwaysVisible: !1,
        size: n.navbarMenuSlimscrollWidth
    }).css("width", "100%");
    n.sidebarPushMenu && $.AdminLTE.pushMenu.activate(n.sidebarToggleSelector);
    n.enableBSToppltip && $("body").tooltip({selector: n.BSTooltipSelector, container: "body"});
    n.enableBoxWidget && $.AdminLTE.boxWidget.activate();
    n.enableFastclick && "undefined" != typeof FastClick && FastClick.attach(document.body);
    n.directChat.enable && $(document).on("click", n.directChat.contactToggleSelector, function () {
        var n = $(this).parents(".direct-chat").first();
        n.toggleClass("direct-chat-contacts-open")
    });
    $('.btn-group[data-toggle="btn-toggle"]').each(function () {
        var n = $(this);
        $(this).find(".btn").on("click", function (t) {
            n.find(".btn.active").removeClass("active");
            $(this).addClass("active");
            t.preventDefault()
        })
    })
}), function (n) {
    "use strict";
    n.fn.boxRefresh = function (t) {
        function u(n) {
            n.append(r);
            i.onLoadStart.call(n)
        }

        function f(n) {
            n.find(r).remove();
            i.onLoadDone.call(n)
        }

        var i = n.extend({
            trigger: ".refresh-btn", source: "", onLoadStart: function (n) {
                return n
            }, onLoadDone: function (n) {
                return n
            }
        }, t), r = n('<div class="overlay"><div class="fa fa-refresh fa-spin"><\/div><\/div>');
        return this.each(function () {
            if ("" === i.source) return void (window.console && window.console.log("Please specify a source first - boxRefresh()"));
            var t = n(this), r = t.find(i.trigger).first();
            r.on("click", function (n) {
                n.preventDefault();
                u(t);
                t.find(".box-body").load(i.source, function () {
                    f(t)
                })
            })
        })
    }
}(jQuery), function (n) {
    "use strict";
    n.fn.activateBox = function () {
        n.AdminLTE.boxWidget.activate(this)
    };
    n.fn.toggleBox = function () {
        var t = n(n.AdminLTE.boxWidget.selectors.collapse, this);
        n.AdminLTE.boxWidget.collapse(t)
    };
    n.fn.removeBox = function () {
        var t = n(n.AdminLTE.boxWidget.selectors.remove, this);
        n.AdminLTE.boxWidget.remove(t)
    }
}(jQuery), function (n) {
    "use strict";
    n.fn.todolist = function (t) {
        var i = n.extend({
            onCheck: function (n) {
                return n
            }, onUncheck: function (n) {
                return n
            }
        }, t);
        return this.each(function () {
            "undefined" != typeof n.fn.iCheck ? (n("input", this).on("ifChecked", function () {
                var t = n(this).parents("li").first();
                t.toggleClass("done");
                i.onCheck.call(t)
            }), n("input", this).on("ifUnchecked", function () {
                var t = n(this).parents("li").first();
                t.toggleClass("done");
                i.onUncheck.call(t)
            })) : n("input", this).on("change", function () {
                var t = n(this).parents("li").first();
                t.toggleClass("done");
                n("input", t).is(":checked") ? i.onCheck.call(t) : i.onUncheck.call(t)
            })
        })
    }
}(jQuery)