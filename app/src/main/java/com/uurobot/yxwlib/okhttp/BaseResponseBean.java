package com.uurobot.yxwlib.okhttp;

/**
 * Created by Administrator on 2017/10/13.
 */

public class BaseResponseBean {
        private String returnCode;
        private String message;

        public BaseResponseBean() {
        }

        public String getReturnCode() {
                return this.returnCode;
        }

        public void setReturnCode(String returnCode) {
                this.returnCode = returnCode;
        }

        public String getMessage() {
                return this.message;
        }

        public void setMessage(String message) {
                this.message = message;
        }


        @Override
        public String toString() {
                return "BaseResponseBean{" +
                        "returnCode='" + returnCode + '\'' +
                        ", message='" + message + '\'' +
                        '}';
        }
}

