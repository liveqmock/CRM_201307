package com.oasis.tmsv5.util.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

public class GMapHelper {
    static private Logger logger = Logger.getLogger(GMapHelper.class);

    /**
     * * 利用googlemap api 通过 HTTP 进行地址解析 *
     * 
     * @param address
     *            地址 *
     * @return HTTP状态代码,精确度（请参见精确度常数）,纬度,经度
     * 
     * 
     */

    public static String getLatlng(String address) {

        String ret = "";

        if (address != null && !address.equals("")) {

            try {
                address = URLEncoder.encode(address, "UTF-8");// 进行这一步是为了避免乱码

            } catch (UnsupportedEncodingException e1) {

                logger.error("转码失败", e1);

            }

            Object[] arr = new String[4];

            arr[0] = address;

            arr[1] = "xml";

            arr[2] = "false";

            arr[3] = "KEY";

            String url = MessageFormat.format("http://maps.google.com/maps/geo?q={0}&output={1}&sensor={2}&key={3}", arr);

            URL urlmy = null;

            try {

                urlmy = new URL(url);

                HttpURLConnection con = (HttpURLConnection) urlmy.openConnection();

                HttpURLConnection.setFollowRedirects(true);

                con.setInstanceFollowRedirects(false);

                con.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

                String s = "";

                StringBuffer sb = new StringBuffer("");

                while ((s = br.readLine()) != null) {

                    sb.append(s + "\r\n");

                }

                ret = "" + sb;

            } catch (MalformedURLException e) {

                logger.error("通过http方式获取地址信息失败", e);

            } catch (IOException e) {

                logger.error("文件读取失败", e);

            }

        }

        return ret;

    }

    public static String getRoute(String origin, String dest) {

        String ret = "";

        if (origin != null && !origin.equals("")) {

            try {
                origin = URLEncoder.encode(origin, "UTF-8");// 进行这一步是为了避免乱码
                dest = URLEncoder.encode(dest, "UTF-8");

            } catch (UnsupportedEncodingException e1) {

                logger.error("转码失败", e1);

            }

            Object[] arr = new String[5];

            arr[0] = origin;

            arr[1] = dest;

            arr[2] = "xml";

            arr[3] = "false";

            arr[4] = "KEY";

            String url = MessageFormat.format(
                    "http://maps.google.es/maps/api/directions/{2}?origin={0}&destination={1}&sensor={3}", arr);

            URL urlmy = null;

            try {

                urlmy = new URL(url);

                HttpURLConnection con = (HttpURLConnection) urlmy.openConnection();

                HttpURLConnection.setFollowRedirects(true);

                con.setInstanceFollowRedirects(false);

                con.connect();

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

                String s = "";

                StringBuffer sb = new StringBuffer("");

                while ((s = br.readLine()) != null) {

                    sb.append(s + "\r\n");

                }

                ret = "" + sb;

            } catch (MalformedURLException e) {

                logger.error("通过http方式获取地址信息失败", e);

            } catch (IOException e) {

                logger.error("文件读取失败", e);

            }

        }        
        return ret;

    }

    public static double getTotalLength(String origin, String dest) {
        String route = getRoute(origin, dest);
        System.out.print(route);
        XPathFactory factory = XPathFactory.newInstance();

        XPath xpath = factory.newXPath();

        Reader reader = new StringReader(route);

        InputSource inputSource = new InputSource(reader);

        Double dis = null;

        try {
            dis = (Double) xpath.evaluate("/DirectionsResponse/route/leg/distance/value", inputSource, XPathConstants.NUMBER);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return dis == null ? -1 : dis;

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(GMapHelper.getTotalLength("地球", "火星1") / 1000);

    }

}
