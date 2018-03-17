package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

// 求 x 的 y 次幂(次方)   pow(x,y);

public class MyDaoGenerator {

    private static double srcWidth = 1921;

    private static double srcHeight = 1401;

//    public static void main(String args[]) throws Exception {
//        //第一个参数是数据库版本号，第二个参数是包的根目录的包
//        Schema schema = new Schema(3, "greendao");
//        Entity box = schema.addEntity("People");
//        box.addIdProperty();
//        box.addStringProperty("name");
//        box.addStringProperty("sex");
//
//
//        Entity animal = schema.addEntity("Animal");
//        animal.addIdProperty();
//        animal.addStringProperty("name");
//        animal.addStringProperty("sex");
//
//
//        //第二个参数是src-gen文件夹相对路径
//        String path = "../YXWLib/MyDaoGenerator/src/";
//            new DaoGenerator().generateAll(schema, path);
//    }


    public static void main(String[] args) {
//            int sample = computeSize() ;
//            System.out.println("sample="+sample);

        testTools();
    }


    private static  void testTools(){
        Tools.getInstance();
        Tools.getInstance();
        Tools.getInstance();
        Tools.getInstance();
    }

    private static  int computeSize() {

        // 变成偶数
        srcWidth = srcWidth % 2 == 1 ? srcWidth + 1 : srcWidth;
        srcHeight = srcHeight % 2 == 1 ? srcHeight + 1 : srcHeight;

        System.out.println("srcWidth="+srcWidth);
        System.out.println("srcHeight="+srcHeight);

        //是否超出最长的边界。。。
        int longSide = (int) Math.max(srcWidth, srcHeight);
        int shortSide = (int) Math.min(srcWidth, srcHeight);
        System.out.println("longSide="+longSide);
        System.out.println("shortSide="+shortSide);


        float scale = ((float) shortSide / longSide);
        System.out.println("scale="+scale);


        if (scale <= 1 && scale > 0.5625) {
            System.out.println("111111111111111");
            if (longSide < 1664) {
                return 1;
            } else if (longSide >= 1664 && longSide < 4990) {
                return 2;
            } else if (longSide > 4990 && longSide < 10240) {
                return 4;
            } else {
                return longSide / 1280 == 0 ? 1 : longSide / 1280;
            }
        } else if (scale <= 0.5625 && scale > 0.5) {
            System.out.println("2222222222222222222");
            return longSide / 1280 == 0 ? 1 : longSide / 1280;
        } else {
            System.out.println("333333333333333333333");
            return (int) Math.ceil(longSide / (1280.0 / scale));
        }
    }
}
