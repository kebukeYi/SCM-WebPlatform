$(function () {
    //激活状态
    var options = {
        elements: {
            arc: {}
        }
    };
    var ctx = $("#pieChart");
    var data = {
        labels: [
            "在线",
            "离线",
            "未激活"
        ],
        datasets: [
            {
                data: [0, 0, 0],
                backgroundColor: [
                    "#36A2EB",
                    "#FF6384",
                    "#FFCE56"
                ],
                hoverBackgroundColor: [
                    "#36A2EB",
                    "#FF6384",
                    "#FFCE56"
                ]
            }]
    };
    // For a pie chart
    var myPieChart = new Chart(ctx, {
        type: 'pie',
        data: data,
        options: options
    });

    /*
    全局 接口访问地址 IP ：port
     */
    var URL = GlobalConfig.IPSSAddress;

    $.updateSever({
        'url': URL + '/api/Monitor/GetStat',
        'crossDomain': 'true',
        'xhrFields': {
            'withCredentials': 'true'
        },
        'type': 'get',
        'success': function (data) {
            myPieChart.data.datasets[0].data = [data.Online, data.Offline, data.Unknown];
            myPieChart.update();
        }
    });

    //报警统计   GetDailyAlarm
    $.updateSever({
        'url': URL + '/api/device/GetDailyAlarm',
        'crossDomain': 'true',
        'xhrFields': {
            'withCredentials': 'true'
        },
        'type': 'get',
        'success': function (data) {
            if (data) {
                $('.police #preloader_5').css({'display': 'none'})
            }
            var g = 0;
            var labels = [];
            var dataL = [];
            //默认一个数组
            var police = [
                {'date': getDaysAgo(1), 'count': 0},
                {'date': getDaysAgo(2), 'count': 0},
                {'date': getDaysAgo(3), 'count': 0},
                {'date': getDaysAgo(4), 'count': 0},
                {'date': getDaysAgo(5), 'count': 0}
            ];
            //使用默认数组和得到的数据进行比较，改变数组中的默认值
            for (var i = 0; i < police.length; i++) {
                var getDay = police[i].date.split("-");
                getDay = getDay[0] + "-0" + getDay[1];
                for (var j = 0; j < data.length; j++) {
                    var date = data[j].AlarmDate.split("T")[0].split("-");
                    if ((date[1] + "-" + date[2]) == police[i].date) {
                        police[i].count = data[j].Count;
                        break;
                    }
                    ;
                    if ((date[1] + "-" + date[2]) == (0 + police[i].date)) {
                        police[i].count = data[j].Count;
                        break;
                    }
                    ;
                    if ((date[1] + "-" + date[2]) == (0 + getDay)) {
                        police[i].count = data[j].Count;
                        break;
                    }
                    ;
                    if ((date[1] + "-" + date[2]) == (getDay)) {
                        police[i].count = data[j].Count;
                        break;
                    }
                }
            }
            ;
            for (var h = 0; h < police.length; h++) {
                if (police[h].count == 0) {
                    ++g;
                }
                labels.unshift(police[h].date);
                dataL.unshift(police[h].count);
            }
            var lineCtx = $('#lineChart');
            var lineData = {
                labels: labels,
                datasets: [
                    {
                        label: "设备数",
                        fill: false,
                        lineTension: 0.1,
                        backgroundColor: "rgba(255,105,108,0.8)",
                        borderColor: "rgba(255,105,108,0.8)",
                        borderCapStyle: 'butt',
                        borderDash: [],
                        borderDashOffset: 0.0,
                        borderJoinStyle: 'miter',
                        pointBorderColor: "rgba(75,192,192,1)",
                        pointBackgroundColor: "#fff",
                        pointBorderWidth: 1,
                        pointHoverRadius: 5,
                        pointHoverBackgroundColor: "rgba(255,105,108,0.8)",
                        pointHoverBorderColor: "rgba(220,220,220,1)",
                        pointHoverBorderWidth: 2,
                        pointRadius: 1,
                        pointHitRadius: 10,
                        data: dataL,
                        spanGaps: false,

                    }
                ]
            };
            var myLineChart = new Chart(lineCtx, {
                type: 'line',
                data: lineData,
                options: {
                    //控制折线图的上面标题
                    legend: {
                        display: false,
                        //position: 'top',
                        //labels: {
                        //    boxWidth: 50,
                        //    fontColor: "#000"
                        //}
                    },
                    scales: {
                        xAxes: [{
                            scaleLabel: {
                                display: true,
                                labelString: "日期",
                                fontColor: "red"
                            },
                        }],
                        yAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: "台数",
                                fontColor: "red"
                            },
                            gridLines: {
                                display: false
                            },
                        }],
                    }
                }
            });
            //利用g值和数组的长度判断count值是否都为0，来改变折线图的纵坐标
            if (g == police.length) {
                myLineChart.options.scales.yAxes[0].ticks.min = 0;
                myLineChart.options.scales.yAxes[0].ticks.stepSize = 500;
                myLineChart.update();
            }
        }
    });

    function getDaysAgo(days) {
        var date = new Date();
        date.setDate(date.getDate() - days);
        return (date.getMonth() + 1) + '-' + date.getDate();
    }

    //分布统计
    //device/GetDistribution      AdCode:44 Count:0
    $.updateSever({
        'url': URL + '/api/device/GetDistribution',
        'crossDomain': 'true',
        'xhrFields': {
            'withCredentials': 'true'
        },
        'type': 'get',
        'success': function (dataList) {
            if (dataList) {
                $('.distribute #preloader_5').css({'display': 'none'})
            }
            var _thisObj = $('#RegionMap');
            var rank = 10;
            var addRank = function (item) {
                $('#MapControl').find('ul.list1').append('<li name="' + item.key + '"><div class="mapInfo"><span>' + chinaMapConfig.names[item.key] + '</span><b>' + item.value + '</b></div></li>');
                rank--;
                if (rank == 0) {
                    addRank = null;
                }
            };
            var list = {
                "11": "beijing",
                "12": "tianjin",
                "13": "hebei",
                "14": "shanxi",
                "15": "neimongol",
                "21": "liaoning",
                "22": "jilin",
                "23": "heilongjiang",
                "31": "shanghai",
                "32": "jiangsu",
                "33": "zhejiang",
                "34": "anhui",
                "35": "fujian",
                "36": "jiangxi",
                "37": "shandong",
                "41": "henan",
                "42": "hubei",
                "43": "hunan",
                "44": "guangdong",
                "45": "guangxi",
                "46": "hainan",
                "50": "chongqing",
                "51": "sichuan",
                "52": "guizhou",
                "53": "yunnan",
                "54": "xizang",
                "61": "shaanxi",
                "62": "gansu",
                "63": "qinghai",
                "64": "ningxia",
                "65": "xinjiang",
                "71": "taiwan",
                "81": "hongkong",
                "82": "macau"
            };
            var obj = {};
            $.each(dataList, function (i, n) {
                var key = list[n.AdCode];
                delete list[n.AdCode];
                obj[key] = {
                    "value": n.Count + "台",
                    "stateInitColor": "7",
                    "key": key,
                    "code": n.AdCode
                };
                addRank && addRank(obj[key]);
            });

            // $.each(list, function (i, n) {
            //     obj[n] = {
            //         "value": "0台",
            //         "stateInitColor": "7",
            //         "key": n,
            //         "code": i
            //     };
            //     addRank && addRank(obj[n]);
            // });

            var mapObj_1 = {};
            var stateColorList = ['003399', '0058B0', '0071E1', '1C8DFF', '51A8FF', '82C0FF', 'AAD5ee', 'AAD5FF'];
            _thisObj.SVGMap({
                external: mapObj_1,
                mapName: 'china',
                mapWidth: 350,
                mapHeight: 350,
                stateData: obj,
                stateTipHtml: function (mapData, obj) {
                    var _value = mapData[obj.id].value;
                    var _idx = mapData[obj.id].index;
                    var active = '';
                    _idx < 35 ? active = 'active' : active = '';
                    //添加地图上的图示
                    var tipStr = '<div class="mapInfo"><span>' + obj.name + '</span><b>' + _value + '</b></div>';
                    return tipStr;
                }//,
                //clickCallback: function (stateData, obj) {
                //    location.href = "/Monitor/TerminalMap?key=" + stateData[obj.id].code;
                //}
            });
            $('#MapControl li').hover(function () {
                var thisName = $(this).attr('name');
                var thisHtml = $(this).html();
                $('#MapControl li').removeClass('select');
                $(this).addClass('select');
                $(document.body).append('<div id="StateTip"></div');
                $('#StateTip').css({
                    left: $(mapObj_1[thisName].node).offset().left - 50,
                    top: $(mapObj_1[thisName].node).offset().top - 40
                }).html(thisHtml).show();
                mapObj_1[thisName].attr({
                    fill: '#E99A4D'
                });
            }, function () {
                var thisName = $(this).attr('name');
                $('#StateTip').remove();
                $('#MapControl li').removeClass('select');
                mapObj_1[$(this).attr('name')].attr({
                    fill: "#" + stateColorList[obj[$(this).attr('name')].stateInitColor]
                });
            });
        }
    });

    //$('#MapColor').show();
    //封装柱状图方法
    var bar = function (ctr, data, lab) {
        var g = 0;
        var ctx = document.getElementById(ctr).getContext('2d');
        var myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['<1天', "1天", "2天", "3天", "4天", "5天", '6天', '>=7天'],
                datasets: [{
                    label: lab ? lab : "离线数",
                    data: data,
                    backgroundColor: 'rgba(255 ,130 ,71,0.5)',
                    borderWidth: 1
                }],
                borderSkipped: 'left'
            },
            options: {
                legend: {
                    display: false,
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
        $.each(data, function (v, i) {
            if (i == 0) {
                g++
            }
        });
        if (g == data.length) {
            myChart.options.scales.yAxes[0].ticks.stepSize = 500;
            myChart.update();
        }
    };

    //有线统计
    $.updateSever({
        'url': URL + '/api/Report/ShowWiredOffline',
        'crossDomain': 'true',
        'xhrFields': {
            'withCredentials': 'true'
        },
        'type': 'post',
        'success': function (line) {
            if (line) {
                $('.barLine #preloader_5').css({'display': 'none'})
            }
            $('.barLine span').html(line.total || 0)
            line.data = line.data || [0, 0, 0, 0, 0, 0, 0, 0]
            bar('barLine', line.data);

        }
    });

    //无线统计
    $.updateSever({
        'url': URL + '/api/Report/ShowWirelessOffline',
        'crossDomain': 'true',
        'xhrFields': {
            'withCredentials': 'true'
        },
        'type': 'post',
        'success': function (line) {
            if (line) {
                $('.offLine #preloader_5').css({'display': 'none'})
            }
            $('.offLine span').html(line.total || 0)
            line.data = line.data || [0, 0, 0, 0, 0, 0, 0, 0]
            bar('offLine', line.data)
        }
    });

    //无线上线统计
    $.updateSever({
        'url': URL + '/api/Report/ShowWirelessOnline',
        'crossDomain': 'true',
        'xhrFields': {
            'withCredentials': 'true'
        },
        'type': 'post',
        'success': function (line) {
            if (line) {
                $('.onLine #preloader_5').css({'display': 'none'})
            }
            $('.onLine span').html(line.total || 0)
            line.data = line.data || [0, 0, 0, 0, 0, 0, 0, 0]
            bar('onLine', line.data, "在线数")
        }
    });

    //增量统计
    $.updateSever({
        'url': URL + '/api/device/GetIncrementModel',
        'crossDomain': 'true',
        'xhrFields': {
            'withCredentials': 'true'
        },
        'type': 'get',
        'success': function (data) {
            if (data) {
                $('.increment #preloader_5').css({'display': 'none'})
            }
            var g = 0;
            var labels = [];
            var dataL = [];
            //默认一个数组
            var police = [
                {'date': getMonthsAgo(0), 'count': 0},
                {'date': getMonthsAgo(1), 'count': 0},
                {'date': getMonthsAgo(2), 'count': 0},
                {'date': getMonthsAgo(3), 'count': 0},
                {'date': getMonthsAgo(4), 'count': 0},
                {'date': getMonthsAgo(5), 'count': 0}
            ];
            //使用默认数组和得到的数据进行比较，改变数组中的默认值
            for (var i = 0; i < police.length; i++) {
                var getDay = police[i].date.split("-");
                getDay = getDay[1].length == 1 ? getDay[0] + "-0" + getDay[1] : police[i].date;
                for (var j = 0; j < data.length; j++) {
                    var date = data[j].Date;
                    if (date == getDay.replace(/-/, "")) {
                        police[i].count = data[j].Count;
                        break;
                    }
                    ;
                }
            }
            ;
            for (var h = 0; h < police.length; h++) {
                if (police[h].count == 0) {
                    ++g;
                }
                labels.unshift(police[h].date);
                dataL.unshift(police[h].count);
            }
            var lineCtx = $('#incrementChart');
            var lineData = {
                labels: labels,
                datasets: [
                    {
                        label: "设备数",
                        fill: false,
                        lineTension: 0.1,
                        backgroundColor: "rgba(255,105,108,0.8)",
                        borderColor: "rgba(255,105,108,0.8)",
                        borderCapStyle: 'butt',
                        borderDash: [],
                        borderDashOffset: 0.0,
                        borderJoinStyle: 'miter',
                        pointBorderColor: "rgba(75,192,192,1)",
                        pointBackgroundColor: "#fff",
                        pointBorderWidth: 1,
                        pointHoverRadius: 5,
                        pointHoverBackgroundColor: "rgba(255,105,108,0.8)",
                        pointHoverBorderColor: "rgba(220,220,220,1)",
                        pointHoverBorderWidth: 2,
                        pointRadius: 1,
                        pointHitRadius: 10,
                        data: dataL,
                        spanGaps: false,

                    }
                ]
            };
            var myLineChart = new Chart(lineCtx, {
                type: 'line',
                data: lineData,
                options: {
                    //控制折线图的上面标题
                    legend: {
                        display: false,
                        //position: 'top',
                        //labels: {
                        //    boxWidth: 50,
                        //    fontColor: "#000"
                        //}
                    },
                    scales: {
                        xAxes: [{
                            scaleLabel: {
                                display: true,
                                labelString: "日期",
                                fontColor: "red"
                            },
                        }],
                        yAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: "台数",
                                fontColor: "red"
                            },
                            gridLines: {
                                display: false
                            },
                        }],
                    }
                }
            });
            //利用g值和数组的长度判断count值是否都为0，来改变折线图的纵坐标
            if (g == police.length) {
                myLineChart.options.scales.yAxes[0].ticks.min = 0;
                myLineChart.options.scales.yAxes[0].ticks.stepSize = 500;
                myLineChart.update();
            }
        }
    });

    //用户增量统计
    $.updateSever({
        'url': URL + '/api/user/GetIncrementModel',
        'crossDomain': 'true',
        'xhrFields': {
            'withCredentials': 'true'
        },
        'type': 'get',
        'success': function (data) {
            if (data) {
                $('.userIncrement #preloader_5').css({'display': 'none'})
            }
            var g = 0;
            var labels = [];
            var dataL = [];
            //默认一个数组
            var police = [
                {'date': getMonthsAgo(0), 'count': 0},
                {'date': getMonthsAgo(1), 'count': 0},
                {'date': getMonthsAgo(2), 'count': 0},
                {'date': getMonthsAgo(3), 'count': 0},
                {'date': getMonthsAgo(4), 'count': 0},
                {'date': getMonthsAgo(5), 'count': 0}
            ];
            //使用默认数组和得到的数据进行比较，改变数组中的默认值
            for (var i = 0; i < police.length; i++) {
                var getDay = police[i].date.split("-");
                getDay = getDay[1].length == 1 ? getDay[0] + "-0" + getDay[1] : police[i].date;
                for (var j = 0; j < data.length; j++) {
                    var date = data[j].Date;
                    if (date == getDay.replace(/-/, "")) {
                        police[i].count = data[j].Count;
                        break;
                    }
                    ;
                }
            }
            ;
            for (var h = 0; h < police.length; h++) {
                if (police[h].count == 0) {
                    ++g;
                }
                labels.unshift(police[h].date);
                dataL.unshift(police[h].count);
            }
            var lineCtx = $('#userIncrementChart');
            var lineData = {
                labels: labels,
                datasets: [
                    {
                        label: "用户数",
                        fill: false,
                        lineTension: 0.1,
                        backgroundColor: "rgba(255,105,108,0.8)",
                        borderColor: "rgba(255,105,108,0.8)",
                        borderCapStyle: 'butt',
                        borderDash: [],
                        borderDashOffset: 0.0,
                        borderJoinStyle: 'miter',
                        pointBorderColor: "rgba(75,192,192,1)",
                        pointBackgroundColor: "#fff",
                        pointBorderWidth: 1,
                        pointHoverRadius: 5,
                        pointHoverBackgroundColor: "rgba(255,105,108,0.8)",
                        pointHoverBorderColor: "rgba(220,220,220,1)",
                        pointHoverBorderWidth: 2,
                        pointRadius: 1,
                        pointHitRadius: 10,
                        data: dataL,
                        spanGaps: false,

                    }
                ]
            };
            var myLineChart = new Chart(lineCtx, {
                type: 'line',
                data: lineData,
                options: {
                    //控制折线图的上面标题
                    legend: {
                        display: false,
                        //position: 'top',
                        //labels: {
                        //    boxWidth: 50,
                        //    fontColor: "#000"
                        //}
                    },
                    scales: {
                        xAxes: [{
                            scaleLabel: {
                                display: true,
                                labelString: "日期",
                                fontColor: "red"
                            },
                        }],
                        yAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: "用户数",
                                fontColor: "red"
                            },
                            gridLines: {
                                display: false
                            },
                        }],
                    }
                }
            });
            //利用g值和数组的长度判断count值是否都为0，来改变折线图的纵坐标
            if (g == police.length) {
                myLineChart.options.scales.yAxes[0].ticks.min = 0;
                myLineChart.options.scales.yAxes[0].ticks.stepSize = 500;
                myLineChart.update();
            }
        }
    });

    function getMonthsAgo(months) {
        var date = new Date();
        date.setMonth(date.getMonth() - months + 1);
        return date.getFullYear() + '-' + date.getMonth();
    }

    //到期统计
    $.updateSever({
        'url': URL + '/api/device/GetExpireDate',
        'crossDomain': 'true',
        'xhrFields': {
            'withCredentials': 'true'
        },
        'type': 'get',
        'success': function (data) {
            if (data) {
                $('.expireDate #preloader_5').css({'display': 'none'})
            }
            var g = 0;
            var labels = [];
            var dataL = [];
            //默认一个数组
            var police = [
                {'date': '60', 'count': 0},
                {'date': '50', 'count': 0},
                {'date': '40', 'count': 0},
                {'date': '30', 'count': 0},
                {'date': '20', 'count': 0},
                {'date': '10', 'count': 0}
            ];
            //使用默认数组和得到的数据进行比较，改变数组中的默认值
            for (var i = 0; i < police.length; i++) {
                for (var j = 0; j < data.length; j++) {
                    var date = data[j].Date;
                    if (date === police[i].date) {
                        police[i].date = '<' + police[i].date;
                        police[i].count = data[j].Count;
                        break;
                    }
                    ;
                }
            }
            ;
            for (var h = 0; h < police.length; h++) {
                if (police[h].count === 0) {
                    ++g;
                }
                labels.unshift(police[h].date);
                dataL.unshift(police[h].count);
            }
            var lineCtx = $('#expireDateChart');
            var lineData = {
                labels: labels,
                datasets: [
                    {
                        label: "台数",
                        fill: false,
                        lineTension: 0.1,
                        backgroundColor: "rgba(255,105,108,0.8)",
                        borderColor: "rgba(255,105,108,0.8)",
                        borderCapStyle: 'butt',
                        borderDash: [],
                        borderDashOffset: 0.0,
                        borderJoinStyle: 'miter',
                        pointBorderColor: "rgba(75,192,192,1)",
                        pointBackgroundColor: "#fff",
                        pointBorderWidth: 1,
                        pointHoverRadius: 5,
                        pointHoverBackgroundColor: "rgba(255,105,108,0.8)",
                        pointHoverBorderColor: "rgba(220,220,220,1)",
                        pointHoverBorderWidth: 2,
                        pointRadius: 1,
                        pointHitRadius: 10,
                        data: dataL,
                        spanGaps: false,

                    }
                ]
            };
            var myLineChart = new Chart(lineCtx, {
                type: 'line',
                data: lineData,
                options: {
                    //控制折线图的上面标题
                    legend: {
                        display: false,
                    },
                    scales: {
                        xAxes: [{
                            scaleLabel: {
                                display: true,
                                labelString: "天数",
                                fontColor: "red"
                            },
                        }],
                        yAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: "设备数",
                                fontColor: "red"
                            },
                            gridLines: {
                                display: false
                            },
                        }],
                    }
                }
            });
            //利用g值和数组的长度判断count值是否都为0，来改变折线图的纵坐标
            if (g == police.length) {
                myLineChart.options.scales.yAxes[0].ticks.min = 0;
                myLineChart.options.scales.yAxes[0].ticks.stepSize = 500;
                myLineChart.update();
            }
        }
    });
});