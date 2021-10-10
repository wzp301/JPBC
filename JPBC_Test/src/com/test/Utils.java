package com.test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Utils {

    /**
     * 已经经过验证 , 为标准sha256加密
     *
     * @param data
     * @return hash
     */
    public static byte[] sha256(byte[] data) {
        MessageDigest messageDigest;
        byte[] hash = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            hash = messageDigest.digest(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }


    public static String bytesToHex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public static byte[] hexToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static byte[] xor(byte[] a, byte[] b) {
        byte[] result = new byte[Math.min(a.length, b.length)];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (((int) a[i]) ^ ((int) b[i]));
        }
        return result;
    }

//    // 把一个byte[] 分拆成每段256字节的数组List
//    public static ArrayList<byte[]> slice(byte[] msg) {
//
//        ArrayList<byte[]> list = new ArrayList<byte[]>();
//
//        // 待优化
//        // boxCount 表示分组数目
//        int boxCount = ((msg.length % SystemParameter.SIZE) == 0)
//                ? (msg.length / SystemParameter.SIZE) :
//                ((msg.length / SystemParameter.SIZE) + 1);
//
//        for (int i = 0; i < boxCount - 1; ++i) {
//            list.add(Arrays.copyOfRange(msg,
//                    i * SystemParameter.SIZE, (i + 1) * SystemParameter.SIZE));
//        }
//        list.add(Arrays.copyOfRange(msg,
//                (boxCount - 1) * SystemParameter.SIZE, msg.length));
//        return list;
//    }

    // 数组list组装成单个的byte[]
//    public static byte[] splice(ArrayList<byte[]> byteMessage) {
//
//        int boxCount = byteMessage.size();
//
//        // byteSum 表示总字节数大小
//        int byteSum = (SystemParameter.SIZE * (boxCount - 1)) +
//                byteMessage.get(boxCount - 1).length;
//        byte[] temp = new byte[byteSum];
//
//        for (int i = 0; i < boxCount - 1; ++i) {
//            for (int t = 0; t < SystemParameter.SIZE; ++t) {
//                temp[i * SystemParameter.SIZE + t] = byteMessage.get(i)[t];
//            }
//        }
//        for (int i = 0; i < byteMessage.get(boxCount - 1).length; ++i) {
//            temp[SystemParameter.SIZE * (boxCount - 1) + i] =
//                    byteMessage.get(boxCount - 1)[i];
//        }
//        return temp;
//    }


//    public static String toString(ArrayList<byte[]> byteMessage) {
//        return new String(splice(byteMessage));
//    }


//    public static void log(String msg, String text) {
//        System.out.println(msg + " : " + text);
//    }

    public static void log(String tag, String msg) {
        System.out.println(tag + " : " + msg);
    }

    public static void log(String text) {
        System.out.println(text);
    }

    public static InitTimer timer(String name) {
        return InitTimer.timer(name);
    }
}

// ref : https://zhuanlan.zhihu.com/p/25697634
// 统计加密操作消耗的时间
class InitTimer implements AutoCloseable {
    private final String name;
    private final long start;

    private InitTimer(String name) {
        this.name = name;
        this.start = System.currentTimeMillis();
        System.out.println("START: " + name);
    }

    public void close() {
        final long end = System.currentTimeMillis();
        System.out.println("");
        System.out.println(" DONE: " + name + " [" + (end - start) + " ms]");
    }

    public static InitTimer timer(String name) {
        return new InitTimer(name);
    }
}
