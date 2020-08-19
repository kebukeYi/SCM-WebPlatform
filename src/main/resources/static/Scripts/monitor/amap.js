var wrt = {};
wrt.mapClass = function (o) {
    this.id = o.id;
    this.map = new AMap.Map(this.id, { enableMapClick: false });          // 创建地图实例
    var point = new AMap.LngLat(118.1742702300, 39.6291521700);  // 创建点坐标
    this.map.setZoomAndCenter(15, point);                 // 初始化地图，设置中心点坐标和地图级别  
    this.tips = new AMap.InfoWindow({
        content: '',
        offset: new AMap.Pixel(0, -12)
    });
    //当前tips的marker
    this.tipsMarker = null;
    this.onTipsUpdated = o.onTipsUpdated;

    ////AMap.plugin(['AMap.ToolBar', 'AMap.Scale', 'AMap.OverView'],
    ////function () {
    ////    map.addControl(new AMap.ToolBar());

    ////    map.addControl(new AMap.Scale());

    ////    map.addControl(new AMap.OverView({ isOpen: true }));
    ////});
    var self = this;
    this.map.plugin(['AMap.ToolBar', 'AMap.Scale', "AMap.MapType"], function (a) {
        //地图类型切换
        var type = new AMap.MapType({
            defaultType: 0 //使用2D地图
        });

        self.map.addControl(new AMap.ToolBar());
        self.map.addControl(type);
        self.map.addControl(new AMap.Scale());
    });

    //this.map.enableScrollWheelZoom();
    //this.map.addControl(new BMap.NavigationControl());
    //this.map.addControl(new BMap.ScaleControl());
    //this.map.addControl(new BMap.OverviewMapControl());
    //this.map.addControl(new BMap.MapTypeControl());
};
wrt.mapClass.prototype = {
    addMarker: function (mOpt) {
        //{'position':{lng,lat},icon:'*.png',text:''}
        var size = new AMap.Size(19, 33);
        if (mOpt.size) {
            size = new AMap.Size(mOpt.size.w, mOpt.size.h);
        }
        var myIcon = new AMap.Icon({
            size: size,
            image: mOpt.icon,
            //imageOffset: new AMap.Pixel(10, 30) // 指定定位位置
        });

        // 创建标注对象并添加到地图
        var bdPoint = this.getPoint(mOpt.position);

        var marker = new AMap.Marker({
            map: this.map,
            position: bdPoint,
            icon: myIcon
        });

        if (mOpt.text) {
            marker.setLabel({
                offset: new AMap.Pixel(15, 30),
                content: mOpt.text
            });
        }

        marker.tipsContent = mOpt.tipsContent; //当前marker相关的tips内容
        marker.mapObj = this; //指定当前marker所属地图对象

        return marker;
    },
    updateMarker: function (m, mOpt) {
        if (mOpt.icon) {
            var myIcon = new AMap.Icon({
                size: new AMap.Size(19, 33),
                image: mOpt.icon,
                //imageOffset: new AMap.Pixel(10, 30) // 指定定位位置
            });
            m.setIcon(myIcon);
        }

        m.tipsContent = mOpt.tipsContent;
        m.setPosition(this.getPoint(mOpt.position));

        //当前地图tips是显示状态，且当前marker是tipsmarker,更新tips内容
        if (this.tips.getIsOpen() && m == this.tipsMarker) {
            this.updateTipsContent(m.tipsContent);
            this.tips.setPosition(m.getPosition());
            return true;  //表示当前marker需要且已经更新了tips内容
        }
        return false;//表示当前marker不需要更新tips内容
    },
    removeMarker: function (m) {
        if (m == this.tipsMarker) {
            if (this.tips.getIsOpen())
                this.tips.close();
            this.tipsMarker = null;
        }
        if (m != null)
            this.map.remove(m);
    },
    moveMarker: function (m, p1, p2, speed_para, speed_base) {
        var mPoint = this.getPoint(p2);
        var t = 5000 / speed_para;
        m.timer = setTimeout(function () {
            m.setPosition(mPoint);
            m.mapObj.tips.setPosition(mPoint); //设置tips位置
            m.mapObj.adjustView(mPoint);
            if (m.onMoveend) {
                m.onMoveend(m);
            }
        }, t);
    },
    stopMovingMarker: function (m) {
        if (m.timer)
            clearTimeout(m.timer);
    },
    //根据位置调整视角,将传入参数做为地图中心点
    adjustView: function (p) {
        var mPoint = this.getPoint(p);
        if (!this.map.getBounds().contains(mPoint)) {
            this.setCenter(mPoint);
        }
    },
    addMarkerClickEvent: function (m, callback) {

        m.on('click', function (e) {
            if (callback) {
                callback(e.target); //回调传当前marker
            }
            e.target.mapObj.showTips(e.target);
        });
    },
    addMarkerMovingEvent: function (m, callback) {
    },
    addMarkerMoveendEvent: function (m, callback) {
        m.onMoveend = callback;
    },
    getMarkerPosition: function (m) {
        return m.getPosition();
    },
    setCenter: function (p, z) {
        z = z || this.map.getZoom();
        this.map.setZoomAndCenter(z, this.getPoint(p));
    },
    //显示信息窗体
    showTips: function (m) {
        this.tipsMarker = m;
        this.updateTipsContent(m.tipsContent);
        this.tips.open(this.map, m.getPosition());
    },
    updateTipsContent: function (content) {
        this.tips.setContent(content);
        if (this.onTipsUpdated) {
            this.onTipsUpdated(this, this.tipsMarker.getPosition());
        }
    },
    //获取地址
    getAddress: function (p, callback) {
        if (this.myGeo) {
            this.myGeo.getAddress(this.getPoint(p), function (status, result) {
                if (status === 'complete' && result.info === 'OK') {
                    callback(result.regeocode.formattedAddress);
                }
            });
        }
        else {
            var self = this;
            self.map.plugin(["AMap.Geocoder"], function () {
                self.myGeo = new AMap.Geocoder({
                    radius: 1000,
                    extensions: "all"
                });
                self.myGeo.getAddress(self.getPoint(p), function (status, result) {
                    if (status === 'complete' && result.info === 'OK') {
                        callback(result.regeocode.formattedAddress);
                    }
                });
            });
        }

    },
    //经纬度转换函数
    getPoint: function (p) {
        return new AMap.LngLat(p.lng, p.lat);
    },
    //画线
    drawLine: function (ps) {
        var bPoints = [];

        for (var i = 0; i < ps.length; i++) {
            bPoints[i] = this.getPoint(ps[i]);
        }
        var polyline = new AMap.Polyline(
            {
                map: this.map,
                strokeColor: "#3366FF", // 线颜色
                strokeOpacity: 1, // 线透明度
                strokeWeight: 5, // 线宽
                strokeStyle: "solid", // 线样式
                strokeDasharray: [10, 5],// 补充线样式
                path: bPoints
            });
        return polyline;
    },
    //获取两点之间的距离，单位米
    getDistance: function (p1, p2) {
        return this.getPoint(p1).distance(this.getPoint(p2));
    },
    //清除地图上所有覆盖物
    clearOverlays: function () {
        this.map.clearMap();
    },
    getCenter: function () {
        return this.map.getCenter();
    },
    getZoom: function () {
        return this.map.getZoom();
    },
    enableDistance: function () {
        if (this.ruler) {
            this.ruler.turnOn();
        }
        else {
            var self = this;
            self.map.plugin(["AMap.RangingTool"], function () {
                self.ruler = new AMap.RangingTool(self.map);
                self.ruler.turnOn();
                AMap.event.addListener(self.ruler, "end", function (e) {
                    self.ruler.turnOff();
                });
            });
        }
    },
    addCircle: function (lng, lat, radius, name) {
        var circle = new AMap.Circle({
            center: new AMap.LngLat(lng, lat), // 圆心位置
            radius: radius,  //半径
            strokeColor: "#F33",  //线颜色
            strokeOpacity: 1,  //线透明度
            strokeWeight: 3,  //线粗细度
            fillColor: "#ee2200",  //填充颜色
            fillOpacity: 0.35 //填充透明度
        });
        this.map.add(circle);

        var text = new AMap.Text({
            text: name,
            textAlign: 'left', // 'left' 'right', 'center',
            cursor: 'pointer',
            style: {
                'border': 'solid 1px #0088ff'
            },
            position: [lng, lat]
        });
        text.setMap(this.map);

        //var marker = new AMap.Marker({
        //    position: new AMap.LngLat(lng, lat)
        //});
        //this.map.add(marker);
        //marker.setLabel({//label默认蓝框白底左上角显示，样式className为：amap-marker-label
        //    offset: new AMap.Pixel(20, 20),//修改label相对于maker的位置
        //    content: name
        //});

        return [circle, text];
    },
    showAdminArea: function (adcode, type) {
        var that = this;
        var polygons = [];
        //加载行政区划插件
        AMap.service('AMap.DistrictSearch', function () {
            var opts = {
                subdistrict: 1,   //返回下一级行政区
                extensions: 'all',  //返回行政区边界坐标组等具体信息
                level: 'city'  //查询行政级别为 市
            };
            //实例化DistrictSearch
            var district = new AMap.DistrictSearch(opts);
            district.setLevel(type);
            //行政区查询
            district.search('350104', function (status, result) {
                if (status === 'complete') {
                    var bounds = result.districtList[0].boundaries;
                    if (bounds) {
                        for (var i = 0, l = bounds.length; i < l; i++) {
                            //生成行政区划polygon
                            var polygon = new AMap.Polygon({
                                map: that.map,
                                strokeWeight: 1,
                                path: bounds[i],
                                fillOpacity: 0.7,
                                fillColor: '#CCF3FF',
                                strokeColor: '#CC66CC'
                            });
                            polygons.push(polygon);
                        }
                        //that.map.setFitView();//地图自适应
                    }
                }
            });
        });

        return polygons;
    },
    clearFigure: function (obj) {
        if (obj.type == 1)
            this.map.remove(obj.data);
        else if (obj.type == 0) {
            var polygons = obj.data;
            //清除地图上所有覆盖物
            for (var i = 0, l = polygons.length; i < l; i++) {
                polygons[i].setMap(null);
            }
        }
    }
};