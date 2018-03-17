package com.uurobot.yxwlib.lingju;

import java.lang.annotation.Target;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lingju.common.adapter.NetworkAdapter;
import com.lingju.common.adapter.PropertiesAccessAdapter;
import com.lingju.common.callback.ResponseCallBack;
import com.lingju.common.util.BaseCrawler;
import com.lingju.context.entity.AudioEntity;
import com.lingju.context.entity.Command;
import com.lingju.context.entity.base.IChatResult;
import com.lingju.robot.AndroidChatRobotBuilder;
import com.uurobot.yxwlib.alarm.Logger;

import android.app.Application;
import android.os.Handler;

/**
 * 鏋楄仛鑱婂ぉ宸ュ叿绫�
 * 
 * @author xiaowei
 *
 */
public class LinJuChatUtil implements ResponseCallBack, IChatUtil {
    private static final String TAG = "LinJuChatUtil";
    private String srcText;
    // 鏄惁姝ｅ湪闊充箰鎼滅储
    private volatile boolean mMusicSession = false;
    private volatile boolean mCancle = false;
    private IChatListener mListener;
    private LinJuLocation mLocationAdapter;

    /**
     * 鍙鐞嗗拰涓荤嚎绋嬬浉鍏崇殑閫昏緫.
     */
    private Handler mMainThreadHandler;

    public LinJuChatUtil(Application application, IChatListener listener,
            Handler handler) {
        mLocationAdapter = new LinJuLocation(
                application.getApplicationContext());
        AndroidChatRobotBuilder
                .create(application, "1b5344a1fb2b5ef1347eb152b55f0abd","7b7a61c639cc979674ced03cabddcb48")
                .setPropertiesAccessAdapter(propertiesAccessAdapter)
                .setLocationAdapter(mLocationAdapter)
                .setNetworkAdapter(networkAdapter).build();

        this.mListener = listener;
        this.mMainThreadHandler = handler;
    }

    private NetworkAdapter networkAdapter = new NetworkAdapter() {
        @Override
        public boolean isOnline() {
            return true;
        }

        @Override
        public NetType currentNetworkType() {
            return null;
        }
    };

    private PropertiesAccessAdapter propertiesAccessAdapter = new PropertiesAccessAdapter() {
        @Override
        public void saveUserName(String s) {
            // 鎸佷箙鍖栫敤鎴蜂负鑷繁璁剧疆鐨勫悕瀛�
            System.out.println("saveUserName>>" + s);
        }

        @Override
        public String getUserName() {
            // 鑾峰彇鐢ㄦ埛涓鸿嚜宸辫缃殑鍚嶅瓧
            return "涓讳汉";
        }

        @Override
        public void saveRobotName(String s) {
            // 淇濆瓨鐢ㄦ埛璁剧疆鐨勬満鍣ㄤ汉鍚嶅瓧
        }

        @Override
        public String getRobotName() {
            // 鑾峰彇鐢ㄦ埛涓烘満鍣ㄤ汉璁剧疆鐨勫悕瀛�
            return "浼樺弸";
        }

        @Override
        public void saveGender(int i) {
            // 淇濆瓨鐢ㄦ埛璁剧疆鐨勬満鍣ㄤ汉鐨勬�у埆
        }

        @Override
        public String getGender() {
            // 鑾峰彇鐢ㄦ埛璁剧疆鐨勬満鍣ㄤ汉鐨勬�у埆
            return "鐖蜂滑";
        }

        public Date getBirthday() {
            Date sqlDate = null;
            SimpleDateFormat bartDateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            String dateStringToParse = "2015-7-12";
            java.util.Date date;
            try {
                date = bartDateFormat.parse(dateStringToParse);
                sqlDate = new Date(date.getTime());
                System.out.println(sqlDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // 鑾峰彇鐢ㄦ埛璁剧疆鐨勬満鍣ㄤ汉鐨勫嚭鐢熷勾鏈堟棩
            return sqlDate;
        }

        @Override
        public void saveParent(String s) {
            // 淇濆瓨鐢ㄦ埛璁剧疆鐨勬満鍣ㄤ汉鐨勭埗姣�
        }

        @Override
        public String getParent() {
            // 鑾峰彇鐢ㄦ埛璁剧疆鐨勬満鍣ㄤ汉鐨勭埗姣�,鏆備笉鏀寔
            return null;
        }

        @Override
        public void saveFather(String s) {

        }

        @Override
        public void saveMother(String s) {

        }

        @Override
        public String getFather() {
            return "搴峰姏浼樿摑";
        }

        @Override
        public String getMother() {
            return "鍦ｆ瘝鐜涘埄浜�";
        }

        @Override
        public String getWeight() {
            return "10鍗冨厠";
        }

        @Override
        public String getHeight() {
            return "128鍘樼背";
        }

        @Override
        public String getMaker() {
            return "娣卞湷搴峰姏浼樿摑鏈哄櫒浜烘湁闄愬叕鍙�";
        }

        @Override
        public String getBirthplace() {
            return "澶╁浗";
        }

        @Override
        public String getIntroduce() {
            return "浣犵寽";
        }

        @Override
        public void saveBirthday(Date arg0) {
            // TODO Auto-generated method stub

        }
    };
    private BaseCrawler crawler = new BaseCrawler();

    @Override
    public void onResult(final IChatResult result) {
        Logger.d(TAG, "linju-onResult---getMotions=" + result.cmd().getMotions());
        Logger.d(TAG,
                "onResult>>text=" + result.getText() + ",cmd:"
                        + result.cmd().toJsonString() + "-result:"
                        + result.cmd().getRtext());
        String answer = "";
        
        
        try {
			JSONObject json = new JSONObject(result.cmd().toJsonString());
			 String data =  json.optString("actions");
		    	if(data!=null){
		    		  JSONObject row = null;
		    		 JSONArray jsonA = new JSONArray(data);
		    		 for(int i=0;i < jsonA.length() ; i++){
		    			 row = jsonA.getJSONObject(i);
		    			
		    			 String target= row.optString("target");
//		    			 if(target!=null){
//		    				 JSONObject targetJ = new JSONObject(target);
//		    				 String id = targetJ.optString("id");
//		    				 if(id.equals("300")){
//		    					 String control = targetJ.optString("control");
//			    				 Logger.d(TAG, "id=========="+id +"      list="  +result.getMusicList());
////			    				 list = result.getMusicList();
//		    				 } 
//		    			 }
		    			 
		    	          JSONObject targetObject = new JSONObject(target);
                          if(!targetObject.isNull("object")){
                              String object = targetObject.getString("object");
                              JSONArray objectArray = new JSONArray(object);
                              if(objectArray!=null){
                                  for(i=0;i<objectArray.length();i++){
                                      if(!objectArray.getJSONObject(i).isNull("musicId")){
                                          String musicId = objectArray.getJSONObject(i).getString("musicId");
                                          Logger.i(TAG,  "musicId :"+musicId);
                                          //将musicId添加到音乐列表
                                          AudioEntity audioEntity = new AudioEntity();
                                          audioEntity.setMusicId(musicId);
                                          
//                                          super.currentResult.getMusicList().add(audioEntity);
                                          //获取音乐播放地址，并播放音乐,请另起线程执行
                                          String palyUrl = new BaseCrawler().getMusicUri(musicId);
                                          Logger.d(TAG, "      list="  +result.getMusicList());
                                          Logger.i(TAG,  "palyUrl :"+palyUrl);
                                      }
                                  }

                              }
                          }
						  
		    			 
		    			 
		    			
		    		 }
		    		 Logger.d(TAG, "data="+data);
		    	}else{
		    		 Logger.e(TAG, "data=  error");
		    	}
		} catch (JSONException e) {
			Logger.e(TAG, "errrr=");
			e.printStackTrace();
		}
       
        
        if (!mCancle) {
            if (result.getStatus() != IChatResult.Status.SUCCESS) {
                Logger.d(TAG, "resutl = " + result.getStatus().toString());
                answer = result.getText();
//                if (result.getStatus() == IChatResult.Status.NETWORK_ERROR) {
//                    answer = result.getText();
//                } else {
//                    answer = srcText + ",杩欎釜闂鎴戜笉鎳�";
//                }
                mListener.onResponseResult(false, answer, null);
            } else {
            
            }
        }

    }

    // 闊充箰鍒楄〃
    private List<AudioEntity> list = null;
    // 褰撳墠姝ｅ湪鎾斁鐨刬tem
    private int currentItem = 0;

    /**
     * 鑾峰彇闊充箰鍒楄〃
     * 
     * @param result
     */
    private void getMusicInfo(final IChatResult result) {
        Logger.e(TAG, "getMusicInfo  鏀跺埌鐏佃仛闊充箰鎾斁鍒楄〃锛屽紑濮嬫挱鏀鹃煶涔�");
//        new Thread(new Runnable() {
//            public void run() {
                list = result.getMusicList();
                playMusic(0);
//            }
//        },"getMusicInfo").start();
    }

    /**
     * 鎾斁闊充箰
     * 
     * @param item
     */
    private void playMusic(int item) {
        if (list != null && list.size() > 0) {
            switch (item) {
                case 0:// 鎾斁姝屾洸
                    currentItem = 0;
                    break;
                case 1:// 涓婁竴鏇�
                    currentItem--;
                    if (currentItem < 0) {
                        currentItem = list.size() - 1;
                    }
                    break;
                case 2:// 涓嬩竴鏇�
                    currentItem++;
                    if (currentItem >= (list.size() - 1)) {
                        currentItem = 0;
                    }
                    break;
                default:
                    break;
            }
            String musicId = list.get(currentItem).getMusicId();
            String url = crawler.getMusicUri(musicId);
            Logger.d(TAG, "music==" + url);
        }
    }

    /**
     * 鏍规嵁闂鍘昏幏鍙栫浉搴旂殑绛旀
     * 
     * @param question
     */
    @Override
    public void onChat(String question) {
        Logger.d(TAG, "question=" + question);
        srcText = question;
        mCancle = false;
        AndroidChatRobotBuilder build = AndroidChatRobotBuilder.get();
        mMusicSession = false;
        if (build != null && build.isRobotCreated()) {
            build.robot().setProcessTimeout(5 * 1000);
            build.robot().process(question, this);
        }else{
        	  Logger.d(TAG, "question=  error " );
        }
    }

    /**
     * 鍙栨秷鎵ц褰撳墠鐨勫洖璋冪粨鏋�
     */
    @Override
    public void onCancle() {
        this.mCancle = true;
        if (mMusicSession && crawler != null) {
            // crawler.stopGetMusicUri();
        }
    }

}
