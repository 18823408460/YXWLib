package com.uurobot.yxwlib.okhttp.yunwenTest.bean;

/**
 * Created by Administrator on 2018/3/19.
 */

public class Access_Token {
        private String message ;
        private long startime ;
        private int status ;
        private String access_token  ;

        public Access_Token(String message, long startime, int status, String access_token) {
                this.message = message;
                this.startime = startime;
                this.status = status;
                this.access_token = access_token;
        }

        public String getMessage() {
                return message;
        }

        public void setMessage(String message) {
                this.message = message;
        }

        public long getStartime() {
                return startime;
        }

        public void setStartime(long startime) {
                this.startime = startime;
        }

        @Override
        public String toString() {
                return "Access_Token{" +
                        "message='" + message + '\'' +
                        ", startime=" + startime +
                        ", status=" + status +
                        ", access_token='" + access_token + '\'' +
                        '}';
        }

        public Access_Token() {
        }

        public int getStatus() {
                return status;
        }

        public void setStatus(int status) {
                this.status = status;
        }

        public String getAccess_token() {
                return access_token;
        }

        public void setAccess_token(String access_token) {
                this.access_token = access_token;
        }
}
