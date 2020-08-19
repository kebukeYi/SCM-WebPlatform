var AMap_selectAdminArea_Interface = function (mapControl) {
    var selectAdminArea = new AMap_selectAdminArea(mapControl);
    mapControl.selectAdminArea = function (status) {
        if (status) {
            selectAdminArea.outDiv.show();
        }
        else {
            selectAdminArea.outDiv.hide();
            selectAdminArea.clearFigure();
            selectAdminArea.clear && selectAdminArea.clear();
        }
    };
    mapControl.showAdminArea = function (adcode, level, name, id, callback) {
        return selectAdminArea.showAdminArea(adcode, level, name, id, callback);
    }
}

var AMap_selectAdminArea = function (mapControl) {
    this.outDiv = $('<div></div>').addClass('AdminArea_Select').appendTo($(mapControl.m.target));
    this.figures = [];
    this.option = (mapControl.mapSetting.plugins && mapControl.mapSetting.plugins.RightToolBar && mapControl.mapSetting.plugins.RightToolBar.SelectAdminArea) || {};
    this.mapControl = mapControl;
    this.init();
};
AMap_selectAdminArea.prototype = {
    allData:{},
    init: function () {
        var _this = this;
        AMap.plugin('AMap.DistrictSearch', function () {
            _this.initBack();
        });
    },
    showAdminArea: function (adcode, level, name, id, callback) {
        var _this = this;
        if (_this.allData[adcode]) {
            var ids =_this.drawAdminArea(_this.allData[adcode].boundaries,id,name);
            callback && callback(ids);
        }
        else {
            _this.district.setLevel(level); //行政区级别
            _this.district.setExtensions('all');
            //按照adcode进行查询可以保证数据返回的唯一性
            _this.district.search(adcode, function (status, result) {
                if (status === 'complete') {
                    _this.allData[adcode] = result.districtList[0];
                    var ids = _this.drawAdminArea(_this.allData[adcode].boundaries,id,name);
                    callback && callback(ids);
                }
            });
        }
    },
    drawAdminArea: function (bounds, id, name) {
        var ids = [];
        var _this = this;
        if (bounds) {
            for (var i = 0, l = bounds.length; i < l; i++) {
                var newId = id + i;
                _this.mapControl.addPolygon(bounds[i], newId, name, { hideClearImg: true });
                ids.push(newId);
                name = '';
            }
            _this.mapControl.m.map.setFitView();//地图自适应
        }
        return ids;
    },
    initBack: function () {
        var _this = this;
        var select_Div = this.outDiv.css({
            'background-color': '#fff',
            'padding': '0 10px',
            'border': '1px solid silver',
            'box-shadow': '3px 4px 3px 0px silver',
            'position': 'absolute',
            'font-size': '12px',
            'right': '80px',
            'top': '48px',
            'display': 'none',
            'border-radius': '3px',
            'line-height': '36px'
        });
        var data = [{
            title: '省：',
            id: 'province',
            clear: function () {
                return $("[id='province']", select_Div).empty();
            }
        }, {
            title: '市：',
            id: 'city',
            clear: function () {
               return $("[id='city']", select_Div).empty();
            }
        }, {
            title: '区：',
            id: 'district',
            clear: function () {
                return $("[id='district']", select_Div).empty();
            }
        //}, {
        //    title: '街道：',
        //    id: 'street',
        //    clear: function () {
        //        return $("[id='street']", select_Div).empty();
        //    }
        }];
        $.each(data, function (i, n) {
            $('<span>' + n.title + '</span>').css({
                'padding-left': '5px'
            }).appendTo(select_Div);
            $('<select></select>')
                .attr('id', n.id)
                .css({ width: '100px' })
                .bind('change', function () {
                    _this.clearFigure();
                    _this.figures = [];
                    var opObj = $(this).find("option:selected");
                    _this.district.setLevel(opObj.attr('value')); //行政区级别
                    _this.district.setExtensions('all');
                    if (!opObj.attr('adcode')) {
                        getData({ 'level': opObj.attr('value') }, '');
                    }
                    else if (_this.allData[opObj.attr('adcode')]) {
                        getData(_this.allData[opObj.attr('adcode')], opObj);
                    }
                    else {
                        //行政区查询
                        //按照adcode进行查询可以保证数据返回的唯一性
                        _this.district.search(opObj.attr('adcode'), function (status, result) {
                            if (status === 'complete') {
                                _this.allData[opObj.attr('adcode')] = result.districtList[0];
                                getData(_this.allData[opObj.attr('adcode')], opObj);
                            }
                        });
                    }
                }).appendTo(select_Div);
        });
        _this.option.handler && $('<button>设置为围栏</button>')
            .css({ 'line-height':'14px', 'margin-left': '8px' })
            .appendTo(select_Div)
            .bind('click', function () {
                var v, t;
                t = '';
                $('select', select_Div).each(function (i, n) {
                    if (n.selectedIndex > 0) {
                        var select_option = n.options[n.selectedIndex];
                        v = $(select_option).attr("adcode") + '|' + $(select_option).attr("value");
                        t += select_option.text;
                    }
                });
                _this.option.handler(t, v);
            });
        //行政区划查询
        var opts = {
            subdistrict: 1,   //返回下一级行政区
            level: 'city',
            showbiz: false  //查询行政级别为 市
        };
        _this.district = new AMap.DistrictSearch(opts);
        var nextLevel = '';
        _this.district.search('中国', function (status, result) {
            if (status == 'complete') {
                getData(result.districtList[0], '');
            }
        });//country
        function clear(id) {
            var isClear = false;
            var nextObj = null;
            if (id == 'country')
            {
                return data[0].clear();
            }
            $.each(data, function (i, n) {
                if (isClear) {
                    var temp = n.clear();
                    if (!nextObj) {
                        nextObj = temp;
                    }
                }
                (n.id == id) && (isClear = true);
            });
            return nextObj ;
        };
        _this.clear = function () {
            $.each(data, function (i, n) {
                if (n.id != 'province') {
                    n.clear();
                }
            });
            var dl = $("[id='province']", select_Div)[0].options;
            if (dl.length > 0) {
                $.each(dl, function (i, n) {
                    if (n['selected']) {
                        n['selected'] = false;
                    }
                    if (i == 0) {
                        n['selected'] = true;
                    }
                });
            }
        };
        function getData(data, topObj) {
            var bounds = data.boundaries;
            if (bounds) {
                for (var i = 0, l = bounds.length; i < l; i++) {
                    var polygon = new AMap.Polygon({
                        map: _this.mapControl.m.map,
                        strokeWeight: 1,
                        strokeColor: '#CC66CC',
                        fillColor: '#CCF3FF',
                        fillOpacity: 0.5,
                        path: bounds[i]
                    });
                    _this.figures.push(polygon);
                }
                _this.mapControl.m.map.setFitView();//地图自适应
            }
            //清空下一级别的下拉列表
            var addSelectObj = clear(data.level);
            var subList = data.districtList;
            if (subList && addSelectObj && addSelectObj.length > 0) {
                var btemp = $('<option>--请选择--</option>').appendTo(addSelectObj);
                if (topObj) {
                    topObj.attr('topName') && btemp.attr('topName', topObj.attr('topName'));
                    topObj.attr('value') && btemp.attr('value', topObj.attr('value'));
                    topObj.attr('adcode') && btemp.attr('adcode', topObj.attr('adcode'));
                }
                else {
                    btemp.attr('value', 'province');
                }
                $.each(subList, function (i, n) {subList
                $('<option>' + n.name + '</option>').attr('topName', (topObj && topObj.attr('topName') ? (topObj.attr('topName') + '_') : '') + n.adcode).attr('value', n.level).attr('adcode', n.adcode).appendTo(addSelectObj);
                })
            }
        }
    },
    clearFigure: function () {
        var _this = this;
        for (var i = 0, l = _this.figures.length; i < l; i++) {
            _this.figures[i].setMap(null);
        }
    }
}