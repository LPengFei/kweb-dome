package com.cnksi.utils;

import java.text.DecimalFormat;

/**
 * k码，灵图编码与gps之间进行转换
 *
 * @ClassName: GpsKit
 * @author xyl
 * @date 2016年6月9日 下午3:16:34
 *
 */
public class GpsKit {
    public static class Gps {
        /**
         * 经度
         */
        private Double lng;
        /**
         * 纬度
         */
        private Double lat;

        public Gps() {
        }

        /**
         * @param lng
         *            经度
         * @param lat
         *            纬度
         */
        public Gps(Double lng, Double lat) {
            super();
            this.lng = lng;
            this.lat = lat;
        }

        /**
         * @param lng
         *            经度
         * @param lat
         *            纬度
         */
        public Gps(String lng, String lat) {
            super();
            this.lng = Double.valueOf(lng);
            this.lat = Double.valueOf(lat);
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        @Override
        public String toString() {
            return "Gps [lng=" + lng + ", lat=" + lat + "]";
        }
    }

    /**
     * K码与gps转换工具类
     *
     * @author xl
     * @date 2016-1-18
     */
    public static class KcodeUtil {

        private static char str[] = new char[34];
        private final static double eps = 1e-8;
        private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

        private static void init() {
            str[0] = '0';
            str[1] = '1';
            str[2] = '2';
            str[3] = '3';
            str[4] = '4';
            str[5] = '5';
            str[6] = '6';
            str[7] = '7';
            str[8] = '8';
            str[9] = '9';
            str[10] = 'a';
            str[11] = 'b';
            str[12] = 'c';
            str[13] = 'd';
            str[14] = 'e';
            str[15] = 'f';
            str[16] = 'g';
            str[17] = 'h';
            str[18] = 'i';
            str[19] = 'j';
            str[20] = 'k'; // no character L
            str[21] = 'm';
            str[22] = 'n'; // no character O
            str[23] = 'p';
            str[24] = 'q';
            str[25] = 'r';
            str[26] = 's';
            str[27] = 't';
            str[28] = 'u';
            str[29] = 'v';
            str[30] = 'w';
            str[31] = 'x';
            str[32] = 'y';
            str[33] = 'z';
        }

        /**
         * 判断经纬度是否符合要求
         *
         * @param lat
         * @param lon
         * @return
         */
        public static String judgementLatLng(double lat, double lon) {

            if (lon >= 140.0 + eps || lon <= 70.0 - eps)
                return "经度在70-140度之间...";
            if (lat >= 75.0 + eps || lat <= 5.0 - eps)
                return "纬度在5-75度之间...";
            return "";
        }

        /**
         * 经纬度转换成K码
         *
         * @param lat
         * @param lon
         * @return
         */
        public static String latLngConvertToKcode(double lat, double lon) {
            init();
            String ans = "";
            if (lon > 105.0 - eps && lat > 40.0 - eps)
                ans += '5';
            else if (lon > 105.0 - eps && lat <= 40.0 - eps)
                ans += '8';
            if (lon <= 105.0 - eps && lat > 40.0 - eps)
                ans += '6';
            else if (lon <= 105.0 - eps && lat <= 40.0 - eps)
                ans += '7';
            if (lon > 105.0 - eps)
                lon -= 105.0;
            else
                lon -= 70.0;
            if (lat > 40.0 - eps)
                lat -= 40.0;
            else
                lat -= 5.0;
            lon *= 3600 * 10;
            lat *= 3600 * 10;
            int longitude = (int) (lon + 0.5);
            int latitude = (int) (lat + 0.5);
            int idx = 0;
            while (idx < 4) {
                ans += str[longitude % 34];
                longitude /= 34;
                idx++;
            }
            idx = 0;
            while (idx < 4) {
                ans += str[latitude % 34];
                latitude /= 34;
                idx++;
            }
            return ans;
        }

        private static int find(char ch) {
            for (int i = 0; i < 34; i++)
                if (str[i] == ch)
                    return i;
            return -1;
        }

        /**
         * 判断K码是否正确
         *
         * @param kcode
         * @return
         */
        public static String judgementKcode(String kcode) {
            init();
            if (kcode == null || kcode.length() != 9)
                return "K码长度为9...";
            if (!(kcode.charAt(0) >= '5' && kcode.charAt(0) <= '8'))
                return "K码第一位为5-8...";
            for (int i = 1; i < kcode.length(); i++)
                if (find(kcode.charAt(i)) == -1)
                    return "K码第2-9位为数字和小写字母(除l和o)...";
            return "";
        }

        private static double calculate(String strK5, int st, int ed) {
            int _34_ = 1;
            double ans = 0.0;
            for (int i = st; i <= ed; i++) {
                ans += find(strK5.charAt(i)) * _34_;
                _34_ *= 34;
            }
            ans /= 10;
            ans /= 3600;
            return ans;

        }

        /**
         * K码转换为经纬度
         *
         * @param strK5
         * @return
         */
        public static double[] kCodeConvertToLatLng(String strK5) {
            double ans[] = new double[2];
            ans[0] = calculate(strK5, 1, 4);
            ans[1] = calculate(strK5, 5, 8);
            if (strK5.charAt(0) == '5') {
                ans[0] += 105;
                ans[1] += 40;
            } else if (strK5.charAt(0) == '6') {
                ans[0] += 70;
                ans[1] += 40;
            } else if (strK5.charAt(0) == '7') {
                ans[0] += 70;
                ans[1] += 5;
            } else if (strK5.charAt(0) == '8') {
                ans[0] += 105;
                ans[1] += 5;
            }
            return ans;
        }

        /**
         * 中国正常坐标系GCJ02协议的坐标 ，转到 百度地图对应的 BD09LL协议坐标 坐标类型bd09ll（百度经纬度坐标，可以在百度系统产品直接使用）
         *
         * @param lat
         * @param lng
         */
        public static double[] convert_GCJ02_To_BD09LL(double lat, double lng) {

            double x = lng, y = lat;
            double[] latLng = new double[2];
            double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
            double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
            latLng[0] = z * Math.sin(theta) + 0.006;
            latLng[1] = z * Math.cos(theta) + 0.0065;
            return latLng;
        }

        /**
         * 百度地图对应的 BD09LL协议坐标，转到 中国正常坐标系GCJ02协议的坐标
         *
         * @param lat
         * @param lng
         */
        public static double[] convert_BD09LL_To_GCJ02(double lat, double lng) {
            double[] latLng = new double[2];
            double x = lng - 0.0065, y = lat - 0.006;
            double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
            double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
            latLng[0] = z * Math.sin(theta);
            latLng[1] = z * Math.cos(theta);
            return latLng;
        }

        /**
         * 验证k码，并将k码转换为经纬度(国标)，并将国标转换为百度经纬度
         *
         * @param record
         * @return [经度，纬度]
         */
        public static double[] judgeAndToLatLonBd(String kcode) {
            double[] retArr = null;
            String _kcode = kcode;
            if (notBlank(kcode)) {
                retArr = new double[2];
                String kcodeResult = "";
                try {
                    kcode = kcode.trim().replace("-", "");
                    kcodeResult = judgementKcode(kcode);
                    if ("".equals(kcodeResult)) {
                        retArr = kCodeConvertToLatLng(kcode);
                        // 国标转到百度坐标系
                        if (retArr != null && retArr.length > 0) {
                            retArr = convert_GCJ02_To_BD09LL(retArr[0], retArr[1]);
                        }
                    } else {
                        throw new RuntimeException(kcodeResult);
                    }
                } catch (Exception e) {
                    System.err.println(kcodeResult);
                    throw new RuntimeException("K码（" + _kcode + "）输入有误，请重新输入(注意数字1和字母l，数字0和字母o)!");
                }
            }
            return retArr;
        }

        static boolean notBlank(String str) {
            return str != null && !str.trim().isEmpty();
        }

        /**
         * 将k码转换为百度经纬度坐标
         *
         * @param kcode
         * @return
         */
        public static Gps toGpsBd(String kcode) {
            double[] lngLatArr = judgeAndToLatLonBd(kcode);
            if (lngLatArr != null && lngLatArr.length == 2) {
                return new Gps(lngLatArr[0], lngLatArr[1]);
            }
            return null;
        }

        /**
         * 将k码转换为普通经纬度坐标
         * @param kcode
         * @return
         */
        public static Gps toGps(String kcode){
            kcode = kcode.trim().replace("-", "");
            String kcodeResult = judgementKcode(kcode);
            if (kcodeResult == null && !kcodeResult.trim().isEmpty()){
                System.out.println(kcodeResult);
                throw new RuntimeException("K码（" + kcode + "）输入有误，请重新输入(注意数字1和字母l，数字0和字母o)!");
            }

            double[] lngLatArr = kCodeConvertToLatLng(kcode);
            if (lngLatArr != null && lngLatArr.length == 2) {
                return new Gps(lngLatArr[0], lngLatArr[1]);
            }
            return null;
        }

        /**
         * gps 转换为k码
         *
         * @return String 返回类型
         * @date 2016年6月6日 上午11:30:09
         */
        public static String toKcode(String lng, String lat) {
            return toKcode(new Gps(lng, lat));
        }

        /**
         * gps 转换为k码
         *
         * @param gps
         * @return String 返回类型
         * @date 2016年6月6日 上午11:30:09
         */
        public static String toKcode(Gps gps) {
            String _kcode = latLngConvertToKcode(gps.getLat(), gps.getLng());
            String kcode = String.format("%s-%s-%s", _kcode.substring(0, 3), _kcode.substring(3, 6), _kcode.substring(6, 9));
            return kcode;
        }

    }

    /**
     * 灵图编码与gps转换工具类
     *
     * @author xl
     * @date 2016-1-18
     */
    public static class LingtuUtil {
        /**
         * 灵图编码转换为经纬度
         *
         * @param lingtuCode
         * @return
         */
		/*
		 * public static Gps toGps(String lingtuCode) { lingtuCode = lingtuCode.replace("-", "");
		 *
		 * lingtuCheck(lingtuCode);
		 *
		 * // 编码转换 char[] charArr = lingtuCode.toCharArray(); System.out.println(Arrays.toString(charArr)); int[] intArr = new int[charArr.length]; for (int i = 0; i < intArr.length; i++) { intArr[i] = toInt(charArr[i]); }
		 *
		 * double lng = intArr[11] < 5 ? 100 : 0; lng += intArr[11] * 10 + intArr[6] + intArr[7] * 0.1 + intArr[2] * 0.01 + intArr[5] * 0.001 + intArr[8] * 0.0001;
		 *
		 * double lat = intArr[9] * 10 + intArr[1] + intArr[10] * 0.1 + intArr[4] * 0.01 + intArr[0] * 0.001 + intArr[3] * 0.0001;
		 *
		 * return new Gps(lng, lat); }
		 */

        public static void lingtuCheck(String lingtuCode) {
            if (lingtuCode == null || lingtuCode.length() == 0) {
                return;
            }

            boolean flag = lingtuCode.length() == 12;
            if (flag) {
                // flag = KStrKit.isNumber(lingtuCode);
            }

            if (flag == false) {
                throw new RuntimeException("灵图编码错误");
            }
        }

        public static Gps ltm2Gps(String ltbm) {
            String latitude = "", longitude = "";
            if (ltbm.length() != 14) {
                throw new RuntimeException("灵图编码错误土");
            }
            if (ltbm.charAt(13) < '5') {
                longitude = "1" + ltbm.charAt(13);
            } else {
                longitude = String.valueOf(ltbm.charAt(13));
            }

            longitude = longitude + ltbm.charAt(7) + ".";
            longitude = longitude + ltbm.charAt(8);
            longitude = longitude + ltbm.charAt(2);
            longitude = longitude + ltbm.charAt(6);
            longitude = longitude + ltbm.charAt(10);

            latitude = String.valueOf(ltbm.charAt(11));
            latitude = latitude + ltbm.charAt(1) + ".";
            latitude = latitude + ltbm.charAt(12);
            latitude = latitude + ltbm.charAt(5);
            latitude = latitude + ltbm.charAt(0);
            latitude = latitude + ltbm.charAt(3);

            if ("".equalsIgnoreCase(longitude) || "".equalsIgnoreCase(latitude) || ltbm.charAt(4) != '-' || ltbm.charAt(9) != '-') {
                throw new RuntimeException("[灵图编码] 格式错误");
            } else {
                System.out.println("longitude->" + longitude + " latitude->" + latitude);
            }

            return new Gps(Double.parseDouble(longitude), Double.parseDouble(latitude));
        }

        /**
         * 经纬度转灵图编码
         *
         * @param longitudeStr
         * @param latitudeStr
         * @return
         */
		/*public static String gps2ltm(String longitudeStr, String latitudeStr) {
			String ltbm = "";
			if (longitudeStr == null || longitudeStr.length() == 0 || latitudeStr == null || latitudeStr.length() == 0) {
				return ltbm;
			}
			double longitude, latitude;
			String str1 = "", str2 = "";
			longitude = Double.parseDouble(longitudeStr);
			latitude = Double.parseDouble(latitudeStr);
			if (longitude > 180 || longitude < 0) {
				return ltbm;
			}
			if (latitude > 90 || latitude < 0) {
				return ltbm;
			} else {
				if (longitude >= 100)
					longitude -= 100;
				longitude *= 10000;
				latitude *= 10000;
				str1 = String.valueOf(longitude);
				str2 = String.valueOf(latitude);
				if (str2.length() < 6)
					str2 = "0" + str2;
				ltbm = String.valueOf(str2.charAt(4));
				ltbm += str2.charAt(1);
				ltbm += str1.charAt(3);
				ltbm += str2.charAt(5);
				ltbm += "-";
				ltbm += str2.charAt(3);
				ltbm += str1.charAt(4);
				ltbm += str1.charAt(1);
				ltbm += str1.charAt(2);
				ltbm += "-";
				ltbm += str1.charAt(5);
				ltbm += str2.charAt(0);
				ltbm += str2.charAt(2);
				ltbm += str1.charAt(0);
			}
			return ltbm;
		}*/

        public static String latLng2Ltm(String latStr, String lngStr) {
            StringBuilder ltbm = new StringBuilder("");
            double dlongitude = Double.parseDouble(lngStr);
            double llatitude = Double.parseDouble(latStr);
            if(dlongitude < 180.0D && dlongitude > 0.0D && llatitude < 90.0D && llatitude > 0.0D) {
                String longitude = (new DecimalFormat("000.000000")).format(dlongitude);
                String latitude = (new DecimalFormat("00.000000")).format(llatitude);
                ltbm.append(latitude.substring(5, 6)).append(latitude.substring(1, 2)).append(longitude.substring(5, 6)).append(latitude.substring(6, 7));
                ltbm.append("-");
                ltbm.append(latitude.substring(4, 5)).append(longitude.substring(6, 7)).append(longitude.substring(2, 3)).append(longitude.substring(4, 5));
                ltbm.append("-");
                ltbm.append(longitude.substring(7, 8)).append(latitude.substring(0, 1)).append(latitude.substring(3, 4)).append(longitude.substring(1, 2));
            }

            return ltbm.toString();
        }


    }
    public static class MapUtils {
        //private static double EARTH_RADIUS = 6378.137;
        private static double EARTH_RADIUS = 6371.393;
        private static double rad(double d)
        {
            return d * Math.PI / 180.0;
        }

        /**
         * 计算两个经纬度之间的距离（m）
         * @param lat1
         * @param lng1
         * @param lat2
         * @param lng2
         *
         * @return
         */
        public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
        {
            double radLat1 = rad(lat1);
            double radLat2 = rad(lat2);
            double a = radLat1 - radLat2;
            double b = rad(lng1) - rad(lng2);
            double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                    Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
            s = s * EARTH_RADIUS;
            s = Math.round(s * 1000);
            return s;
        }
    }

    @SuppressWarnings("unused")
    private static int toInt(char c) {
        return Integer.parseInt(Character.toString(c));
    }

    public static void main(String[] args) {
        // System.out.println(Arrays.toString(GpsUtil.KcodeUtil.judgeAndToLatLon("7hh-yxz-49q")));
        // System.out.println(GpsUtil.KcodeUtil.judgeAndToGps("7hh-yxz-49q"));
//		System.out.println(LingtuUtil.ltm2Gps("8580-0964-8224"));

        System.out.println("GPS->KCODE:" + GpsKit.KcodeUtil.toKcode(new Gps(103.765568, 29.552106)));

//		System.out.println("ltbm->" + LingtuUtil.latLng2Ltm("29.552106", "103.765568"));

        System.out.println(KcodeUtil.toGps("7sh-xwc-kgn"));
        System.out.println(KcodeUtil.toGpsBd("7sh-xwc-kgn"));
    }

}
