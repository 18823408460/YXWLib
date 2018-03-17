package pl.surecase.eu;

/**
 * Created by Administrator on 2017/10/18.
 */

public class Tools {

        private static int count ;
        private Tools(){
                System.out.print("constrct----------"+count++ +"\n");
        }
        public static Tools getInstance(){
                return new Tools();
        }
}
