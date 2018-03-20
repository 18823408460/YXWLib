package com.uurobot.yxwlib.okhttp.yunwenTest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.uurobot.yxwlib.R;
import com.uurobot.yxwlib.okhttp.OkHttpUtils;
import com.uurobot.yxwlib.okhttp.yunwenTest.bean.Access_Token;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/19.
 */

public class YunWenActivity extends Activity {
        Access_Token access_token ;
        String baseUrl = String.format("%stoken/getToken?", Constant.HOSTNAME);
        String chatbaseUrl = String.format("%sservlet/ApiChat?", Constant.HOSTNAME);
        EditText editText ;
        private static final String TAG = "YunWenActivity";

        /**
         * data : null
         * message : 请求成功!
         * navigation : null
         * question : 唱一首刘德华的歌
         * robotAnswer : [{"aId":0,"ansCon":"啊给我一杯忘情水，让我一夜不流泪\u2026\u2026","answerType":-500,"answerVoicePath":"","cluid":"b3f97da4-3aee-4cd5-ad4c-0561766b484d","crawlers":[],"document":[],"fullTextSearch":0,"gusList":[],"gusWords":null,"hitQuestion":"","msgType":"text","relateLessList":[],"relateList":[],"relateListStartSelectIndex":0,"serviceType":"","thirdUrl":null}]
         * semantic : null
         * serviceType : text
         * status : 0
         * time : 2018-03-20 08:53:35
         * tspan : 2
         */

        private Object data;

        private String message;

        private Object navigation;

        private String question;

        private Object semantic;

        private String serviceType;

        private int status;

        private String time;

        private int tspan;

        private List<RobotAnswerBean> robotAnswer;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_yunwen);
                editText = findViewById(R.id.editText);
                StringBuilder stringBuilder = new StringBuilder();
                long time = System.currentTimeMillis();

//                stringBuilder.append(baseUrl)
//                        .append("token=" + RobotBase62.base62Encode(getToken(time).getBytes()))
//                        .append("&")
//                        .append("sourceId="+Constant.SOURCEID)
//                        .append("&")
//                        .append("s=ss")
//                        .append("&")
//                        .append("timestamp="+time)
//                        .append("&")
//                        .append("clientId=" + Constant.CLIENDID);
                stringBuilder.append(baseUrl)
                        .append("appId=" + Constant.APPID)
                        .append("&")
                        .append("secret="+Constant.SECRET);

                Log.e(TAG, "onCreate: url==" + stringBuilder.toString());
                OkHttpUtils.getInstance().getAsyn(this, TAG, stringBuilder.toString(), new OkHttpUtils.OkCallBack() {
                        @Override
                        public void onBefore(Request var1, Object var2) {
                                Log.e(TAG, "onBefore: ");
                        }

                        @Override
                        public void onAfter(Object var1) {
                                Log.e(TAG, "onAfter: ");
                        }

                        @Override
                        public void onError(Call var1, Response var2, Exception var3, Object var4) {
                                Log.e(TAG, "onError: "+var2);
                                Log.e(TAG, "onError: "+var3);
                        }

                        @Override
                        public void onResponse(Object var1, Object var2) {
                                Log.e(TAG, "onResponse: " + var1);
                                Gson gson = new Gson();
                                access_token = gson.fromJson((String) var1, Access_Token.class);
                                Log.e(TAG, "onResponse: "+access_token );
                                String q = "";
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append(chatbaseUrl)
                                        .append("access_token=" + access_token.getAccess_token())
                                        .append("&")
                                        .append("sourceId="+Constant.SOURCEID)
                                        .append("&")
                                        .append("s="+Constant.ROBAT_ONLIONING)
                                        .append("&")
//                                        .append("question="+q)
//                                        .append("&")
                                        .append("clientId=" + Constant.CLIENDID);
                               OkHttpUtils.getInstance().getAsyn(YunWenActivity.this, TAG, stringBuilder.toString(), new OkHttpUtils.OkCallBack() {
                                        @Override
                                        public void onBefore(Request var1, Object var2) {
                                                Log.e(TAG, "onBefore: ");
                                        }

                                        @Override
                                        public void onAfter(Object var1) {
                                                Log.e(TAG, "onAfter: ");
                                        }

                                        @Override
                                        public void onError(Call var1, Response var2, Exception var3, Object var4) {
                                                Log.e(TAG, "onError: "+var2);
                                                Log.e(TAG, "onError: "+var3);
                                        }

                                        @Override
                                        public void onResponse(Object var1, Object var2) {
                                                Log.e(TAG, "onResponse: " + var1);
                                        }
                                });
                        }
                });
        }


        public void send(View view) throws UnsupportedEncodingException {
                String q = URLEncoder.encode(editText.getText().toString(),"utf-8");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(chatbaseUrl)
                        .append("access_token=" + access_token.getAccess_token())
                        .append("&")
                        .append("sourceId="+Constant.SOURCEID)
                        .append("&")
                        .append("s="+Constant.QUESTION)
                        .append("&")
                        .append("question="+q)
                        .append("&")
                        .append("clientId=" + Constant.CLIENDID);
                Log.e(TAG, "send: url==" + stringBuilder.toString());
                OkHttpUtils.getInstance().getAsyn(this, TAG, stringBuilder.toString(), new OkHttpUtils.OkCallBack() {
                        @Override
                        public void onBefore(Request var1, Object var2) {
                                Log.e(TAG, "onBefore: ");
                        }

                        @Override
                        public void onAfter(Object var1) {
                                Log.e(TAG, "onAfter: ");
                        }

                        @Override
                        public void onError(Call var1, Response var2, Exception var3, Object var4) {
                                Log.e(TAG, "onError: "+var2);
                                Log.e(TAG, "onError: "+var3);
                        }

                        @Override
                        public void onResponse(Object var1, Object var2) {
                                Log.e(TAG, "onResponse: " + var1);
                        }
                });
        }

        private String getToken(long times) {
                String cliendid =Constant.CLIENDID;
                int nonce = new Random().nextInt(10);

                String source = cliendid + times + nonce;
                Log.e(TAG, "getToken: "+source );
                return source;
        }

        public Object getData() {
                return data;
        }

        public void setData(Object data) {
                this.data = data;
        }

        public String getMessage() {
                return message;
        }

        public void setMessage(String message) {
                this.message = message;
        }

        public Object getNavigation() {
                return navigation;
        }

        public void setNavigation(Object navigation) {
                this.navigation = navigation;
        }

        public String getQuestion() {
                return question;
        }

        public void setQuestion(String question) {
                this.question = question;
        }

        public Object getSemantic() {
                return semantic;
        }

        public void setSemantic(Object semantic) {
                this.semantic = semantic;
        }

        public String getServiceType() {
                return serviceType;
        }

        public void setServiceType(String serviceType) {
                this.serviceType = serviceType;
        }

        public int getStatus() {
                return status;
        }

        public void setStatus(int status) {
                this.status = status;
        }

        public String getTime() {
                return time;
        }

        public void setTime(String time) {
                this.time = time;
        }

        public int getTspan() {
                return tspan;
        }

        public void setTspan(int tspan) {
                this.tspan = tspan;
        }

        public List<RobotAnswerBean> getRobotAnswer() {
                return robotAnswer;
        }

        public void setRobotAnswer(List<RobotAnswerBean> robotAnswer) {
                this.robotAnswer = robotAnswer;
        }

        public static class RobotAnswerBean {

                /**
                 * aId : 0
                 * ansCon : 啊给我一杯忘情水，让我一夜不流泪……
                 * answerType : -500
                 * answerVoicePath :
                 * cluid : b3f97da4-3aee-4cd5-ad4c-0561766b484d
                 * crawlers : []
                 * document : []
                 * fullTextSearch : 0
                 * gusList : []
                 * gusWords : null
                 * hitQuestion :
                 * msgType : text
                 * relateLessList : []
                 * relateList : []
                 * relateListStartSelectIndex : 0
                 * serviceType :
                 * thirdUrl : null
                 */

                private int aId;

                private String ansCon;

                private int answerType;

                private String answerVoicePath;

                private String cluid;

                private int fullTextSearch;

                private Object gusWords;

                private String hitQuestion;

                private String msgType;

                private int relateListStartSelectIndex;

                private String serviceType;

                private Object thirdUrl;

                private List<?> crawlers;

                private List<?> document;

                private List<?> gusList;

                private List<?> relateLessList;

                private List<?> relateList;

                public int getAId() {
                        return aId;
                }

                public void setAId(int aId) {
                        this.aId = aId;
                }

                public String getAnsCon() {
                        return ansCon;
                }

                public void setAnsCon(String ansCon) {
                        this.ansCon = ansCon;
                }

                public int getAnswerType() {
                        return answerType;
                }

                public void setAnswerType(int answerType) {
                        this.answerType = answerType;
                }

                public String getAnswerVoicePath() {
                        return answerVoicePath;
                }

                public void setAnswerVoicePath(String answerVoicePath) {
                        this.answerVoicePath = answerVoicePath;
                }

                public String getCluid() {
                        return cluid;
                }

                public void setCluid(String cluid) {
                        this.cluid = cluid;
                }

                public int getFullTextSearch() {
                        return fullTextSearch;
                }

                public void setFullTextSearch(int fullTextSearch) {
                        this.fullTextSearch = fullTextSearch;
                }

                public Object getGusWords() {
                        return gusWords;
                }

                public void setGusWords(Object gusWords) {
                        this.gusWords = gusWords;
                }

                public String getHitQuestion() {
                        return hitQuestion;
                }

                public void setHitQuestion(String hitQuestion) {
                        this.hitQuestion = hitQuestion;
                }

                public String getMsgType() {
                        return msgType;
                }

                public void setMsgType(String msgType) {
                        this.msgType = msgType;
                }

                public int getRelateListStartSelectIndex() {
                        return relateListStartSelectIndex;
                }

                public void setRelateListStartSelectIndex(int relateListStartSelectIndex) {
                        this.relateListStartSelectIndex = relateListStartSelectIndex;
                }

                public String getServiceType() {
                        return serviceType;
                }

                public void setServiceType(String serviceType) {
                        this.serviceType = serviceType;
                }

                public Object getThirdUrl() {
                        return thirdUrl;
                }

                public void setThirdUrl(Object thirdUrl) {
                        this.thirdUrl = thirdUrl;
                }

                public List<?> getCrawlers() {
                        return crawlers;
                }

                public void setCrawlers(List<?> crawlers) {
                        this.crawlers = crawlers;
                }

                public List<?> getDocument() {
                        return document;
                }

                public void setDocument(List<?> document) {
                        this.document = document;
                }

                public List<?> getGusList() {
                        return gusList;
                }

                public void setGusList(List<?> gusList) {
                        this.gusList = gusList;
                }

                public List<?> getRelateLessList() {
                        return relateLessList;
                }

                public void setRelateLessList(List<?> relateLessList) {
                        this.relateLessList = relateLessList;
                }

                public List<?> getRelateList() {
                        return relateList;
                }

                public void setRelateList(List<?> relateList) {
                        this.relateList = relateList;
                }
        }
}
