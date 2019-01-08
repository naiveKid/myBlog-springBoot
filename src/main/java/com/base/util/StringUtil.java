package com.base.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类（主要使用APACHE提供的字符串工具类）
 */
public class StringUtil {

    /**
     * 去空 如果是NULL则返回空字符
     *
     * @param str
     * @return
     */
    public static String delNull(String str) {
        if (str == null) {
            str = "";
        }
        return str;
    }

    /**
     * 空字符返回null对象
     *
     * @param str
     * @return
     */
    public static String returnNull(String str) {
        if (CommonUtil.isNull(str)) {
            return null;
        } else {
            return str;
        }
    }

    /**
     * 检查空字符串
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    /**
     * 清除空白字符
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        return StringUtils.trim(str);
    }

    /**
     * 取得字符串的缩写
     *
     * @param str
     * @param maxWidth
     * @return
     */
    public static String abbreviate(String str, int maxWidth) {
        return StringUtils.abbreviate(str, maxWidth);
    }

    /**
     * 劈分字符串
     *
     * @param str
     * @param separatorChars
     * @return
     */
    public static String[] split(String str, String separatorChars) {
        return StringUtils.split(str, separatorChars);
    }

    /**
     * 将数组合并为字符串
     *
     * @param array
     * @param separator
     * @return
     */
    public static String join(String[] array, String separator) {
        return StringUtils.join(array, separator);
    }

    /**
     * 截取字符串
     *
     * @param str
     * @param start
     * @return
     */
    public static String substring(String str, int start) {
        return StringUtils.substring(str, start);
    }

    /**
     * 截取字符串
     *
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static String substring(String str, int start, int end) {
        return StringUtils.substring(str, start, end);
    }

    /**
     * 字符串截取，超出部分添加省略号
     *
     * @param str
     * @param num
     * @return
     * @throws Exception
     */
    public static String cutString(String str, int num) {
        if (str != null && str.length() > num) {
            str = str.substring(0, num) + "..";
        }
        return str;
    }

    /**
     * 截取嵌套字符串
     *
     * @param str
     * @param open
     * @param close
     * @return
     */
    public static String substringBetween(String str, String open, String close) {
        return StringUtils.substringBetween(str, open, close);
    }

    /**
     * 判断两个字符串是否相等，有非空处理
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(String str1, String str2) {
        if (CommonUtil.isNull(str1) || CommonUtil.isNull(str2)) {
            return false;
        }
        return StringUtils.equals(str1, str2);
    }

    /**
     * 返回要查找的字符串所在位置，有非空处理
     *
     * @param str
     * @param searchStr
     * @return
     */
    public static int indexOf(String str, String searchStr) {
        return StringUtils.indexOf(str, searchStr);
    }

    /**
     * 将字符串中的首字母大写
     *
     * @param str
     * @return
     */
    public static String capitalize(String str) {
        return StringUtils.capitalize(str);
    }

    /**
     * 取得某字符串在另一字符串中出现的次数
     *
     * @param str
     * @param sub
     * @return
     */
    public static int countMatches(String str, String sub) {
        return StringUtils.countMatches(str, sub);
    }

    /**
     * 字符串str不足num位数的，在前面补齐"0"
     *
     * @param str 要处理的字符串
     * @param num 处理后的长度
     * @return
     */
    public static String addZero(String str, long num) {
        StringBuffer ss = new StringBuffer();
        while (ss.length() != num - str.length()) {
            ss.append("0");
        }
        ss.append(str);
        return ss.toString();
    }

    /**
     * 字符串str不足num位数的，在前面补齐"0"
     *
     * @param s   要处理的字符串
     * @param num 处理后的长度
     * @return
     */
    public static String addZero(int s, long num) {
        return StringUtil.addZero(String.valueOf(s), num);
    }

    /**
     * 生成一个随机的字符串（手机验证码）
     *
     * @param numberFlag 是否只生成数字
     * @param length     生成字符串长度
     * @return
     */
    public static String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890";
        int len = strTable.length();
        boolean bDone = true;
        while (bDone) {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        }
        return retStr;
    }

    /**
     * 隐藏部分字符串（用于在网站上显示用户信息时隐藏中间部分）
     *
     * @param info   原字符串
     * @param before 开始显示位数
     * @param after  结尾显示位数
     * @return
     */
    public static String hideString(String info, int before, int after) {
        String str1 = info.substring(0, before);
        String str2 = info.substring(before, info.length() - after);
        String str3 = info.substring(info.length() - after, info.length());

        info = str1;
        for (int i = 0; i < str2.length(); i++) {
            info = info + "*";
        }
        info = info + str3;

        return info;
    }

    /**
     * 从str字符串中提取中文字符，并保留英文字符
     *
     * @param str
     * @return
     */
    public static String getChineseStr(String str) {
        String rs = "";
        if (!CommonUtil.isNull(str)) {
            rs = str.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5]", "");
        }
        return rs;
    }

    /**
     * 产生一个随机的中文字符串
     *
     * @param length  字符串长度
     * @param num     是否包含数字
     * @param english 是否包含英文
     * @param chara   是否包含中文
     * @return
     */
    public static String getChineseCharStr(int length, boolean num, boolean english, boolean chara) {
        // 如果需要的字符串不包含数字、英文、汉字
        if (!num && !english && !chara) {
            return "Can you tell me what do you want?";
        }
        StringBuffer sb = new StringBuffer();
        String numDic = "1234567890";
        String engDic = "abcdefghijkmnpqrstuvwxyz";
        int hightPos, lowPos; // 定义高低位
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            List<String> args = new ArrayList<String>();
            String number = numDic.charAt(new Random().nextInt(numDic.length())) + "";// 生成一个数字
            String enCha = engDic.charAt(new Random().nextInt(engDic.length())) + "";// 生成一个英文字符
            // 生成一个中文
            hightPos = (176 + Math.abs(random.nextInt(39)));// 获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93)));// 获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            String zhCha = "";
            try {
                zhCha = new String(b, "GBk");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // 放到List里面，然后产生随机数去取
            if (num) {
                args.add(number);
            }
            if (english) {
                args.add(enCha);
            }
            if (chara) {
                args.add(zhCha);
            }
            sb.append(args.get((int) Math.floor(Math.random() * args.size())));
        }
        return sb.toString();
    }

    /**
     * 把list转换为一个用逗号分隔的字符串
     */
    public static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i) + ",");
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();
    }

    /**
     * 匹配是否包含数字
     *
     * @param str 可能为中文，也可能是-19162431.1254，不使用BigDecimal的话，变成-1.91624311254E7
     * @return
     * @author yutao
     * @date 2016年11月14日下午7:41:22
     */
    public static boolean isNumeric(String str) {
        // 该正则表达式可以匹配所有的数字 包括负数
        Pattern pattern = Pattern.compile("^[0-9]*$");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }

        Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
