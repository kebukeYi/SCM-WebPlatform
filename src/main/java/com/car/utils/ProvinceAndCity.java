package com.car.utils;

import com.alibaba.fastjson.JSON;
import com.car.androidbean.AndroidMonitorTip;
import com.car.redis.JedisUtil6800;

import java.util.*;

/*
省市集合 四位数字表示 前两位为省 后两位为市
 */
public class ProvinceAndCity {

    public List<Map<String,String>> getcities (String id) {
        Map<String, Map<String, String>> ZOOM = new HashMap<>();
        Map<String, String> beijing = new HashMap<>();
        Map<String, String> shanghai = new HashMap<>();
        Map<String, String> tianjing = new HashMap<>();
        Map<String, String> chongqing = new HashMap<>();

        Map<String, String> heilongjiang = new HashMap<>();
        Map<String, String> jilin = new HashMap<>();
        Map<String, String> liaoning = new HashMap<>();
        Map<String, String> neimenggu = new HashMap<>();

        Map<String, String> hebei = new HashMap<>();
        Map<String, String> henan = new HashMap<>();
        Map<String, String> shandong = new HashMap<>();
        Map<String, String> shanxi = new HashMap<>();//山西

        Map<String, String> jiangsu = new HashMap<>();
        Map<String, String> anhui = new HashMap<>();
        Map<String, String> shanx = new HashMap<>();//陕西
        Map<String, String> ningxia = new HashMap<>();

        Map<String, String> gansu = new HashMap<>();
        Map<String, String> qinghai = new HashMap<>();
        Map<String, String> hubei = new HashMap<>();
        Map<String, String> hunan = new HashMap<>();

        Map<String, String> zhejiang = new HashMap<>();
        Map<String, String> jiangxi = new HashMap<>();
        Map<String, String> fujian = new HashMap<>();
        Map<String, String> guizhou = new HashMap<>();

        Map<String, String> sichuan = new HashMap<>();
        Map<String, String> guangdong = new HashMap<>();
        Map<String, String> guangxi = new HashMap<>();
        Map<String, String> yunnan = new HashMap<>();

        Map<String, String> hainan = new HashMap<>();
        Map<String, String> xinjiang = new HashMap<>();
        Map<String, String> xizang = new HashMap<>();
        Map<String, String> xianggang = new HashMap<>();
        Map<String, String> aomen = new HashMap<>();
        Map<String, String> taiwan = new HashMap<>();

        beijing.put("01", "北京");
        shanghai.put("01", "上海");
        tianjing.put("01", "天津");
        chongqing.put("01", "重庆");

        heilongjiang.put("01", "哈尔滨");
        heilongjiang.put("02", "齐齐哈尔");
        heilongjiang.put("03", "牡丹江");
        heilongjiang.put("04", "大庆");
        heilongjiang.put("05", "伊春");
        heilongjiang.put("06", "双鸭山");
        heilongjiang.put("07", "鹤岗");
        heilongjiang.put("08", "鸡西");
        heilongjiang.put("09", "佳木斯");
        heilongjiang.put("10", "七台河");
        heilongjiang.put("11", "黑河");
        heilongjiang.put("12", "绥化");
        heilongjiang.put("13", "大兴安岭");

        jilin.put("01", "长春");
        jilin.put("02", "延边");
        jilin.put("03", "吉林");
        jilin.put("04", "白山");
        jilin.put("05", "白城");
        jilin.put("06", "四平");
        jilin.put("07", "松原");
        jilin.put("08", "辽源");
        jilin.put("09", "大安");
        jilin.put("10", "通化");

        liaoning.put("01", "沈阳");
        liaoning.put("02", "瓦房店");
        liaoning.put("03", "大连");
        liaoning.put("04", "盘锦");
        liaoning.put("05", "葫芦岛");
        liaoning.put("06", "旅顺");
        liaoning.put("07", "本溪");
        liaoning.put("08", "抚顺");
        liaoning.put("09", "铁岭");
        liaoning.put("10", "辽阳");
        liaoning.put("11", "营口");
        liaoning.put("12", "阜新");
        liaoning.put("13", "朝阳");
        liaoning.put("14", "锦州");
        liaoning.put("15", "丹东");
        liaoning.put("16", "鞍山");

        neimenggu.put("01", "呼和浩特");
        neimenggu.put("02", "呼伦贝尔");
        neimenggu.put("03", "锡林浩特");
        neimenggu.put("04", "包头");
        neimenggu.put("05", "赤峰");
        neimenggu.put("06", "海拉尔");
        neimenggu.put("07", "乌海");
        neimenggu.put("08", "鄂尔多斯");
        neimenggu.put("09", "通辽");

        hebei.put("01", "石家庄");
        hebei.put("02", "唐山");
        hebei.put("03", "张家口");
        hebei.put("04", "廊坊");
        hebei.put("05", "邢台");
        hebei.put("06", "邯郸");
        hebei.put("07", "沧州");
        hebei.put("08", "衡水");
        hebei.put("09", "承德");
        hebei.put("10", "保定");
        hebei.put("11", "秦皇岛");

        henan.put("01", "郑州");
        henan.put("02", "开封");
        henan.put("03", "洛阳");
        henan.put("04", "平顶山");
        henan.put("05", "焦作");
        henan.put("06", "鹤壁");
        henan.put("07", "新乡");
        henan.put("08", "安阳");
        henan.put("09", "濮阳");
        henan.put("10", "许昌");
        henan.put("11", "漯河");
        henan.put("12", "三门峡");
        henan.put("13", "南阳");
        henan.put("14", "商丘");
        henan.put("15", "信阳");
        henan.put("16", "周口");
        henan.put("17", "驻马店");

        shandong.put("01", "济南");
        shandong.put("02", "威海");
        shandong.put("03", "胶南");
        shandong.put("04", "章丘");
        shandong.put("05", "莱州");
        shandong.put("06", "莱西");
        shandong.put("07", "蓬莱");
        shandong.put("08", "荣成");
        shandong.put("09", "胶州");
        shandong.put("10", "文登");
        shandong.put("12", "青岛");
        shandong.put("13", "淄博");
        shandong.put("14", "乳山");
        shandong.put("15", "即墨");
        shandong.put("16", "寿光");
        shandong.put("17", "招远");
        shandong.put("18", "平度");
        shandong.put("19", "曲阜");
        shandong.put("20", "临沂");
        shandong.put("21", "烟台");
        shandong.put("22", "枣庄");
        shandong.put("23", "聊城");
        shandong.put("24", "济宁");
        shandong.put("25", "菏泽");
        shandong.put("26", "泰安");
        shandong.put("27", "日照");
        shandong.put("28", "东营");
        shandong.put("29", "德州");
        shandong.put("30", "滨州");
        shandong.put("31", "莱芜");
        shandong.put("32", "潍坊");

        shanxi.put("01", "太原");
        shanxi.put("02", "阳泉");
        shanxi.put("03", "晋城");
        shanxi.put("04", "晋中");
        shanxi.put("05", "临汾");
        shanxi.put("06", "运城");
        shanxi.put("07", "长治");
        shanxi.put("08", "朔州");
        shanxi.put("09", "忻州");
        shanxi.put("10", "大同");
        shanxi.put("11", "吕梁");

        jiangsu.put("01", "南京");
        jiangsu.put("02", "包容");
        jiangsu.put("03", "苏州");
        jiangsu.put("04", "江阴");
        jiangsu.put("05", "金坛");
        jiangsu.put("06", "海门");
        jiangsu.put("07", "溧阳");
        jiangsu.put("08", "张家港");
        jiangsu.put("09", "吴江");
        jiangsu.put("10", "昆山");
        jiangsu.put("11", "南通");
        jiangsu.put("12", "太仓");
        jiangsu.put("13", "吴县");
        jiangsu.put("14", "徐州");
        jiangsu.put("15", "宜兴");
        jiangsu.put("16", "镇江");
        jiangsu.put("17", "华南");
        jiangsu.put("18", "常熟");
        jiangsu.put("19", "盐城");
        jiangsu.put("20", "泰州");
        jiangsu.put("21", "连云港");
        jiangsu.put("22", "无锡");
        jiangsu.put("23", "扬州");
        jiangsu.put("24", "常州");
        jiangsu.put("25", "宿迁");

        anhui.put("01", "合肥");
        anhui.put("02", "巢湖");
        anhui.put("03", "蚌埠");
        anhui.put("04", "安庆");
        anhui.put("05", "六安");
        anhui.put("06", "滁州");
        anhui.put("07", "马鞍山");
        anhui.put("08", "阜阳");
        anhui.put("09", "宣城");
        anhui.put("10", "铜陵");
        anhui.put("11", "淮北");
        anhui.put("12", "芜湖");
        anhui.put("13", "毫州");
        anhui.put("14", "宿州");
        anhui.put("15", "淮南");
        anhui.put("16", "池州");

        shanx.put("01", "西安");
        shanx.put("02", "韩城");
        shanx.put("03", "安康");
        shanx.put("04", "汉中");
        shanx.put("05", "宝鸡");
        shanx.put("06", "咸阳");
        shanx.put("07", "榆林");
        shanx.put("08", "渭南");
        shanx.put("09", "商洛");
        shanx.put("10", "铜川");
        shanx.put("11", "延安");

        ningxia.put("01", "银川");
        ningxia.put("02", "固原");
        ningxia.put("03", "中卫");
        ningxia.put("04", "石嘴山");
        ningxia.put("05", "吴忠");

        gansu.put("01", "兰州");
        gansu.put("02", "嘉峪关");
        gansu.put("03", "白银");
        gansu.put("04", "庆阳");
        gansu.put("05", "阳泉");
        gansu.put("06", "天水");
        gansu.put("07", "武威");
        gansu.put("08", "张掖");
        gansu.put("09", "甘南");
        gansu.put("10", "临夏");

        qinghai.put("01", "西宁");
        qinghai.put("02", "海北");
        qinghai.put("03", "海西");
        qinghai.put("04", "黄南");
        qinghai.put("05", "果洛");
        qinghai.put("06", "玉树");
        qinghai.put("07", "海东");
        qinghai.put("08", "海南");

        hubei.put("01", "武汉");
        hubei.put("02", "宜昌");
        hubei.put("03", "黄冈");
        hubei.put("04", "恩施");
        hubei.put("05", "荆州");
        hubei.put("06", "神农架");
        hubei.put("07", "十堰");
        hubei.put("08", "咸宁");
        hubei.put("09", "襄樊");
        hubei.put("10", "孝感");
        hubei.put("11", "随州");
        hubei.put("12", "黄石");
        hubei.put("13", "荆门");
        hubei.put("14", "鄂州");

        hunan.put("01", "长沙");
        hunan.put("02", "邵阳");
        hunan.put("03", "常德");
        hunan.put("04", "郴州");
        hunan.put("05", "吉首");
        hunan.put("06", "株洲");
        hunan.put("07", "娄底");
        hunan.put("08", "湘潭");
        hunan.put("09", "益阳");
        hunan.put("10", "永州");
        hunan.put("11", "岳阳");
        hunan.put("12", "衡阳");
        hunan.put("13", "怀化");
        hunan.put("14", "韶山");
        hunan.put("15", "张家界");

        zhejiang.put("01", "杭州");
        zhejiang.put("02", "临安");
        zhejiang.put("03", "义乌");
        zhejiang.put("04", "诸暨");
        zhejiang.put("05", "富阳");
        zhejiang.put("06", "湖州");
        zhejiang.put("07", "金华");
        zhejiang.put("08", "宁波");
        zhejiang.put("09", "丽水");
        zhejiang.put("10", "绍兴");
        zhejiang.put("11", "雁荡山");
        zhejiang.put("12", "衢州");
        zhejiang.put("13", "嘉兴");
        zhejiang.put("14", "台州");
        zhejiang.put("15", "舟山");
        zhejiang.put("16", "温州");

        jiangxi.put("01", "南昌");
        jiangxi.put("02", "萍乡");
        jiangxi.put("03", "九江");
        jiangxi.put("04", "上饶");
        jiangxi.put("05", "抚州");
        jiangxi.put("06", "吉安");
        jiangxi.put("07", "鹰潭");
        jiangxi.put("08", "宜春");
        jiangxi.put("09", "新余");
        jiangxi.put("10", "景德镇");
        jiangxi.put("11", "赣州");

        fujian.put("01", "福州");
        fujian.put("02", "厦门");
        fujian.put("03", "龙岩");
        fujian.put("04", "南平");
        fujian.put("05", "宁德");
        fujian.put("06", "莆田");
        fujian.put("07", "泉州");
        fujian.put("08", "三明");
        fujian.put("09", "漳州");

        guizhou.put("01", "贵阳");
        guizhou.put("02", "安顺");
        guizhou.put("03", "赤水");
        guizhou.put("04", "遵义");
        guizhou.put("05", "铜仁");
        guizhou.put("06", "六盘水");
        guizhou.put("07", "毕节");
        guizhou.put("08", "凯里");
        guizhou.put("09", "都匀");

        sichuan.put("01", "成都");
        sichuan.put("02", "泸州");
        sichuan.put("03", "内江");
        sichuan.put("04", "甘孜州");
        sichuan.put("05", "阿坝州");
        sichuan.put("06", "凉山州");
        sichuan.put("07", "巴中");
        sichuan.put("08", "广元");
        sichuan.put("09", "乐山");
        sichuan.put("10", "绵阳");
        sichuan.put("11", "德阳");
        sichuan.put("12", "攀枝花");
        sichuan.put("13", "雅安");
        sichuan.put("14", "自贡");
        sichuan.put("15", "宜宾");
        sichuan.put("16", "资阳");
        sichuan.put("17", "广安");
        sichuan.put("18", "遂宁");
        sichuan.put("19", "眉山");
        sichuan.put("20", "南充");

        guangdong.put("01", "广州");
        guangdong.put("02", "深圳");
        guangdong.put("03", "潮州");
        guangdong.put("04", "韶关");
        guangdong.put("05", "湛江");
        guangdong.put("06", "惠州");
        guangdong.put("07", "清远");
        guangdong.put("08", "东莞");
        guangdong.put("09", "江门");
        guangdong.put("10", "茂名");
        guangdong.put("11", "肇庆");
        guangdong.put("12", "汕尾");
        guangdong.put("13", "汕头");
        guangdong.put("14", "河源");
        guangdong.put("15", "揭阳");
        guangdong.put("16", "梅州");
        guangdong.put("17", "中山");
        guangdong.put("18", "徳庆");
        guangdong.put("19", "阳江");
        guangdong.put("20", "云浮");
        guangdong.put("21", "珠海");
        guangdong.put("22", "佛山");

        guangxi.put("01", "南宁");
        guangxi.put("02", "桂林");
        guangxi.put("03", "阳朔");
        guangxi.put("04", "柳州");
        guangxi.put("05", "梧州");
        guangxi.put("06", "玉林");
        guangxi.put("07", "桂平");
        guangxi.put("08", "贺州");
        guangxi.put("09", "钦州");
        guangxi.put("10", "贵港");
        guangxi.put("11", "防城港");
        guangxi.put("12", "百色");
        guangxi.put("13", "北海");
        guangxi.put("14", "河池");
        guangxi.put("15", "来宾");
        guangxi.put("16", "崇左");

        yunnan.put("01", "昆明");
        yunnan.put("02", "保山");
        yunnan.put("03", "楚雄");
        yunnan.put("04", "德宏");
        yunnan.put("05", "红河");
        yunnan.put("06", "临沧");
        yunnan.put("07", "怒江");
        yunnan.put("08", "曲靖");
        yunnan.put("09", "思茅");
        yunnan.put("10", "文山");
        yunnan.put("11", "玉溪");
        yunnan.put("12", "昭通");
        yunnan.put("13", "丽江");
        yunnan.put("14", "大理");

        hainan.put("01", "海口");
        hainan.put("02", "三亚");
        hainan.put("03", "儋州");
        hainan.put("04", "琼山");
        hainan.put("05", "通什");
        hainan.put("06", "文昌");

        xinjiang.put("01", "乌鲁木齐");
        xinjiang.put("02", "阿勒泰");
        xinjiang.put("03", "阿克苏");
        xinjiang.put("04", "昌吉");
        xinjiang.put("05", "哈密");
        xinjiang.put("06", "和田");
        xinjiang.put("07", "喀什");
        xinjiang.put("08", "克拉玛依");
        xinjiang.put("09", "石河子");
        xinjiang.put("10", "塔城");
        xinjiang.put("11", "库尔勒");
        xinjiang.put("12", "吐鲁番");
        xinjiang.put("13", "伊宁");

        xizang.put("01", "西藏");
        xizang.put("02", "昌都地区");
        xizang.put("03", "山南地区");
        xizang.put("04", "阿里地区");
        xizang.put("05", "那曲地区");
        xizang.put("06", "日喀则地区");
        xizang.put("07", "林芝地区");

        xianggang.put("01", "香港");
        xianggang.put("02", "中西区");
        xianggang.put("03", "东区");
        xianggang.put("04", "南区");
        xianggang.put("05", "湾仔区");
        xianggang.put("06", "九龙城区");
        xianggang.put("07", "观塘区");
        xianggang.put("08", "深水埗区");
        xianggang.put("09", "黄大仙区");
        xianggang.put("10", "油尖旺区");
        xianggang.put("11", "离岛区");
        xianggang.put("12", "葵青区");
        xianggang.put("13", "北区");
        xianggang.put("14", "西贡区");
        xianggang.put("15", "沙田区");
        xianggang.put("16", "大埔区");
        xianggang.put("17", "荃湾区");
        xianggang.put("18", "屯门区");
        xianggang.put("19", "元朗区");

        aomen.put("01", "澳门");
        aomen.put("02", "花地玛堂区");
        aomen.put("03", "圣安多尼堂区");
        aomen.put("04", "大堂区");
        aomen.put("05", "望德堂区");
        aomen.put("06", "风顺堂区");
        aomen.put("07", "嘉模堂区");
        aomen.put("08", "圣方济各堂区");

        taiwan.put("01", "台湾");
        taiwan.put("02", "基隆");
        taiwan.put("03", "嘉义");
        taiwan.put("04", "新竹");
        taiwan.put("05", "中华台北");
        taiwan.put("06", "台中");
        taiwan.put("07", "台南");
        taiwan.put("08", "新北");
        taiwan.put("09", "高雄");
        taiwan.put("10", "桃园");

        ZOOM.put("01", beijing);
        ZOOM.put("02", shanghai);
        ZOOM.put("03", tianjing);
        ZOOM.put("04", chongqing);
        ZOOM.put("05", heilongjiang);
        ZOOM.put("06", jilin);
        ZOOM.put("07", liaoning);
        ZOOM.put("08", neimenggu);
        ZOOM.put("09", hebei);
        ZOOM.put("10", henan);
        ZOOM.put("11", shandong);
        ZOOM.put("12", shanxi);
        ZOOM.put("13", jiangsu);
        ZOOM.put("14", anhui);
        ZOOM.put("15", shanx);
        ZOOM.put("16", ningxia);
        ZOOM.put("17", gansu);
        ZOOM.put("18", qinghai);
        ZOOM.put("19", hubei);
        ZOOM.put("20", hunan);
        ZOOM.put("21", zhejiang);
        ZOOM.put("22", jiangxi);
        ZOOM.put("23", fujian);
        ZOOM.put("24", guizhou);
        ZOOM.put("25", sichuan);
        ZOOM.put("26", guangdong);
        ZOOM.put("27", guangxi);
        ZOOM.put("28", yunnan);
        ZOOM.put("29", hainan);
        ZOOM.put("30", xinjiang);
        ZOOM.put("31", xizang);
        ZOOM.put("32", xianggang);
        ZOOM.put("33", aomen);
        ZOOM.put("34", taiwan);

        List<Map<String,String>> res = new ArrayList<>();
        Map <String,String> temp =new HashMap<>();
        temp = ZOOM.get(id);

        for (Map.Entry<String, String> entry : temp.entrySet()) {
            Map<String,String> t =new HashMap<>();
            t.put("id",id+entry.getKey());
            t.put("name",entry.getValue());
            res.add(t);
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        return  res;
    }
      public List<Map<String,String>> getProvince(){
          Map <String,String> Province =new HashMap<>();
          Province.put("01","北京");
          Province.put("02","上海");
          Province.put("03","天津");
          Province.put("04","重庆");
          Province.put("05","黑龙江");
          Province.put("06","吉林");
          Province.put("07","辽宁");
          Province.put("08","内蒙古");
          Province.put("09","河北");
          Province.put("10","河南");
          Province.put("11","山东");
          Province.put("12","山西");
          Province.put("13","江苏");
          Province.put("14","安徽");
          Province.put("15","陕西");
          Province.put("16","宁夏");
          Province.put("17","甘肃");
          Province.put("18","青海");
          Province.put("19","湖北");
          Province.put("20","湖南");
          Province.put("21","浙江");
          Province.put("22","江西");
          Province.put("23","福建");
          Province.put("24","贵州");
          Province.put("25","四川");
          Province.put("26","广东");
          Province.put("27","广西");
          Province.put("28","云南");
          Province.put("29","海南");
          Province.put("30","新疆");
          Province.put("31","西藏");
          Province.put("32","香港");
          Province.put("33","澳门");
          Province.put("34","台湾");

          List<Map<String,String>> res = new ArrayList<>();
          for (Map.Entry<String, String> entry : Province.entrySet()) {
              Map<String,String> t =new HashMap<>();
              t.put("id",entry.getKey());
              t.put("name",entry.getValue());
              res.add(t);
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
          }
          return res;
    }
}
