var AMap_geocoder_Interface = function (mapControl) {
    var tempGeocoder = new AMap_geocoder(mapControl.m);
    mapControl.getAddress = function (lngLat, callback) {
        tempGeocoder.getAddress(lngLat, callback);
    }
};
var AMap_geocoder = function (map_Plus) {
    var _this = this;
    _this.Address = {};
    (function () {
        AMap.plugin('AMap.Geocoder', function () {
            _this.Geocoder = new AMap.Geocoder();
        });
    })();
};
AMap_geocoder.prototype = {
    getAddress: function (lngLat, callback) {
        var _this = this;
        var lon0 = lngLat.lng;
        var lat0 = lngLat.lat; 
        if (!lon0 || !lat0) {
            callback && callback('--');
            return;
        }
        if (_this.Address[lon0]) {
            if (_this.Address[lon0][lat0]) {
                if (_this.Address[lon0][lat0]['isGetOver']) {
                    callback && callback(_this.Address[lon0][lat0]['data']);
                    return;
                }
                else {
                    function getAddress() {
                        if (_this.Address[lon0][lat0]['isGetOver']) {
                            callback && callback(_this.Address[lon0][lat0]['data']);
                        }
                        else {
                            setTimeout(getAddress, 500);
                        }
                    }
                    setTimeout(getAddress, 500);
                    return;
                }
            }
            else {
                _this.Address[lon0][lat0] = { data: '', isGetOver: false };
            }
        }
        else {
            _this.Address[lon0] = {};
            _this.Address[lon0][lat0] = { data: '', isGetOver: false };
        }
        (function get() {
            _this.Geocoder.getAddress(new AMap.LngLat(lon0, lat0),
						function (status, result) {
						    var data = '--';
						    if (status === 'complete' && result.info === 'OK') {
						        if (result.regeocode
										&& result.regeocode.formattedAddress) {
						            data = result.regeocode.formattedAddress;
						        }
						        _this.Address[lon0][lat0] = { data: data, isGetOver: true };
						        callback && callback(data);
						    }
						    else {
						        setTimeout(get, 1000);
						    }
						});
        })();
    }
};
