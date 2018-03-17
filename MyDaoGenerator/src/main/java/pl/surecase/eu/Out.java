package pl.surecase.eu;

/**
 * Created by Administrator on 2017/12/5.
 */

public class Out {
        private static int privateX = 0 ;
        private int x= 0 ;

        public Out(){
                System.out.println("Out construct");
        }

        public static class  StaticInner{
                public StaticInner(){
                        privateX = 1 ;
                        System.out.println("StaticInner construct="+privateX);
                }
        }

        public  class  Inner{
                public Inner(){
                        x = 0;
                        privateX = 21 ;
                        System.out.println("Inner construct="+privateX);
                }
        }
}
