package com.uurobot.yxwlib.okhttp.yunwenTest.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */

public class ResultData {

        /**
         * data : null
         * message : 请求成功!
         * navigation : null
         * question : 你叫什么名字
         * robotAnswer : [{"aId":0,"ansCon":"我叫基本库o(∩_∩)o 我是基本库智能客服，可以回答你有关的业务问题喔！","answerType":-10,"answerVoicePath":"","cluid":"a5484081-e123-4291-9ed5-8ed2e9463a45","crawlers":[],"document":[],"fullTextSearch":0,"gusList":[],"gusWords":null,"hitQuestion":"","msgType":"text","relateLessList":[],"relateList":[],"relateListStartSelectIndex":0,"serviceType":"","thirdUrl":null}]
         * semantic : null
         * serviceType : text
         * status : 0
         * time : 2018-03-20 10:01:17
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
                 * ansCon : 我叫基本库o(∩_∩)o 我是基本库智能客服，可以回答你有关的业务问题喔！
                 * answerType : -10
                 * answerVoicePath :
                 * cluid : a5484081-e123-4291-9ed5-8ed2e9463a45
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
