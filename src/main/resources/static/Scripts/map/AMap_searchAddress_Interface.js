var AMap_searchAddress_Interface = function (mapControl) {
    var searchAddress = new AMap_searchAddress(mapControl);
    mapControl.searchAddress = function (status) {
        if (status) {
            searchAddress.outDiv.show();
        }
        else {
            searchAddress.outDiv.hide();
            searchAddress.clearFigure();
        }
    };
}

var AMap_searchAddress = function (mapControl) {
    this.outDiv = $('<div></div>').addClass('Address_Search').appendTo($(mapControl.m.target));
    this.figures = {}; //图形
    this.mapControl = mapControl;
    this.init();
};
AMap_searchAddress.prototype = {
    init: function () {
        var _this = this;
        AMap.plugin('AMap.PlaceSearch', function () {
            AMap.plugin('AMap.Autocomplete', function () {
                AMap.plugin('AMap.DistrictSearch', function () {
                    _this.initBack();
                });
            });
        });
    },
    initBack: function () {
        var _this = this;
        var opts = {
            subdistrict: 1, // 返回下一级行政区
            extensions: 'all', // 返回行政区边界坐标组等具体信息
            level: 'city' // 查询行政级别为 市
        };
        var district = new AMap.DistrictSearch(opts);
        var outDiv = _this.outDiv.css({
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
        var searchDiv = $('<div></div>').appendTo(outDiv);
        $('<span>省：</span>').css({
            'padding-left': '5px'
        }).appendTo(searchDiv);
        var provinceSelect = $('<select></select>').css({ width: '100px' }).appendTo(searchDiv).bind('change', function () {
            var obj = this;
            var option = obj[obj.options.selectedIndex];
            var arrTemp = option.value.split('|');
            var level = arrTemp[0]; // 行政级别
            var citycode = arrTemp[1]; // 城市编码
            var keyword = option.text; // 关键字
            district.setLevel(level); // 行政区级别
            // 行政区查询
            citySelect.empty();
            district.search(keyword, function (status, result) {
                var list = result.districtList || [];
                var subList = [];
                if (list.length >= 1) {
                    subList = list[0].districtList;
                }
                if (subList) {
                    var l = subList.length;
                    if (l == 1) {
                        $('<option value="' + list[0].name
											+ '">所有城区</option>')
											.appendTo(citySelect);
                    }
                    for (var i = 0, l = subList.length; i < l; i++) {
                        $('<option value="' + subList[i].name
											+ '">' + subList[i].name
											+ '</option>').appendTo(citySelect);
                    }
                }
                autoSearch();
            });
        });
        $('<span>市：</span>').css({
            'padding-left': '5px'
        }).appendTo(searchDiv);
        var citySelect = $('<select></select>').css({ width: '100px' }).appendTo(searchDiv).bind('change', autoSearch);
        var provinceList = ['所有省市', '北京市', '天津市', '河北省', '山西省',
							'内蒙古自治区', '辽宁省', '吉林省', '黑龙江省', '上海市', '江苏省',
							'浙江省', '安徽省', '福建省', '江西省', '山东省', '河南省', '湖北省',
							'湖南省', '广东省', '广西壮族自治区', '海南省', '重庆市', '四川省',
							'贵州省', '云南省', '西藏自治区', '陕西省', '甘肃省', '青海省',
							'宁夏回族自治区', '新疆维吾尔自治区', '台灣', '香港特别行政区', '澳门特别行政区'];
        for (var i = 0, l = provinceList.length; i < l; i++) {
            $('<option value="' + provinceList[i] + '">'
								+ provinceList[i] + '</option>')
								.appendTo(provinceSelect);
        }
        $('<span>关键字：</span>').css({
            'padding-left': '5px'
        }).appendTo(searchDiv);
        var keyword = $('<input />').attr('id','key_search_keyword').attr('name', 'keyword').css({
							    'width': '120px',
							    'height': '20px'
							}).appendTo(searchDiv);
        var lastKeyword = '';
        var btn = $('<img />').attr('src',
							Common_Tools.Css + 'icons/search.png').css({
							    'vertical-align': 'middle',
							    'width': '15px',
							    'heigth': ' 15px',
							    'position': 'absolute',
							    'right': '15px',
							    'top': '11px',
							    'cursor': 'pointer'
							}).appendTo(searchDiv).bind('click', function () {
							    autoSearch(true);
							});
        var result = $('<div></div>').css({
            'max-height': '300px',
            'line-height': '20px'
        }).appendTo(outDiv);
        var result_data = [];
        function autoSearch(go) {
            var keys = keyword.val();
            // 加载输入提示插件
            var autoOptions = {
                city: citySelect.select().val() || provinceSelect.select().val()
            };
            var auto = new AMap.Autocomplete(autoOptions);
            // 查询成功时返回查询结果
            if (keys && $.trim(keys).length > 0) {
                if (lastKeyword != keys || go) {
                    lastKeyword = keys;
                    auto.search(keys, function (status, result) {
                        result_data = result.tips
                        autocomplete_CallBack();
                    });
                }
            } else {
                if (lastKeyword != keys) {
                    lastKeyword = keys;
                }
                result.hide();
            }
        };
        function selectResult(da) {
            if (!da)
                return;
            lastKeyword = da.name;
            keyword.val(da.name);
            result.hide();
            // 根据选择的输入提示关键字查询
            var msearch = new AMap.PlaceSearch({
                city: da.adcode,
                pageSize: 50
            }); // 构造地点查询类
            msearch.search(da.name, placeSearch_CallBack); // 关键字查询查询
        };
        function placeSearch_CallBack(status, re) {
            if (status === 'complete' && re.info === 'OK') {
                _this.clearFigure();
                if (re.poiList.pois.length != 0) {
                    $.each(re.poiList.pois, function (i, n) {
                        _this.addMarker({
                            lng: n.location.lng,
                            lat: n.location.lat
                        }, "search_point_" + n.id, (i + 1)
													+ '.' + n.name, {
													    noSetCenter: true
													}, {
													    content: Common_Tools
														.getPOIContent({
														    name: n.name,
														    tel: n.tel,
														    address: n.address,
														    type: n.type,
														    i: i
														})
													});

                    });
                    _this.setFitView();
                } else {
                    Ext.Msg.alert("提示", "没有符合条件的相关记录!");
                }
            }
        }
        // 输出输入提示结果的回调函数
        function autocomplete_CallBack() {
            result.html('');
            if (result_data && result_data.length > 0) {
                $.each(result_data, function (i, n) {
                    result.append($('<div>' + n.name
										+ '<span style="color:#C1C1C1;">'
										+ n.district + '</span></div>').css({
										    'font-size': '13px',
										    'cursor': 'pointer',
										    'padding': '5px 5px 5px 5px'
										}).attr('id', 'divid' + i).bind(
										'mouseover', function () {
										    $(this).css({
										        'background': '#CAE1FF'
										    })
										}).bind('mouseout', function () {
										    $(this).css({
										        'background': ''
										    })
										}).bind('click', function () {
										    selectResult(n);
										}));
                });
            } else {
                result.append($('<div> π__π 亲,人家找不到结果!<br />要不试试：<br />1.请确保所有字词拼写正确<br />2.尝试不同的关键字<br />3.尝试更宽泛的关键字</div>'));
            }
            result.curSelect = -1;
            result.show();
        }
        var keywordDownUp = function () {
            var key = window.event.keyCode;
            var cur = result.curSelect;
            var child = result.children();
            if (key === 40) {// down
                if (cur + 1 < child.length) {
                    if (child[cur]) {
                        $(child[cur]).css({
                            background: ''
                        });
                    }
                    result.curSelect = cur + 1;
                    $(child[cur + 1]).css({
                        background: '#CAE1FF'
                    });
                    result.show();
                }
            } else if (key === 38) {// up
                if (cur - 1 >= 0) {
                    if (child[cur]) {
                        $(child[cur]).css({
                            background: ''
                        });
                    }
                    result.curSelect = cur - 1;
                    $(child[cur - 1]).css({
                        background: '#CAE1FF'
                    });
                    result.show();
                }
            } else if (key === 13) {
                if (result && result['curSelect'] !== -1) {
                    selectResult(result_data[cur]);
                }
            } else {
                autoSearch();
            }
        };
        keyword.bind('keyup', keywordDownUp);
        function immediately() {
            var element = document.getElementById("key_search_keyword");
            if ("\v" == "v") {// 判断IE
                element.onpropertychange = autoSearch;
            } else {// 非IE
                element.addEventListener("input", autoSearch, false);
            }
        }
        immediately();
    },
    addMarker: function (position, id, name, markerOption, infoWindowOption) {
        var _this = this;
        _this.mapControl.m.addMarkerCommon(
        _this.figures,
        position,
        id,
        name,
         markerOption,
         infoWindowOption);
    },
    setFitView: function () {
        var _this = this;
        if (!$.isEmptyObject(_this.figures)) {
            var allFigures = [];
            $.each(_this.figures, function (i, t) {
                allFigures.push(t.obj);
            });
            _this.mapControl.m.setFitViewByObjs(allFigures);
        }
    },
    clearFigure: function (id) {
        this.mapControl.m.clear(id, this.figures);
    }
}