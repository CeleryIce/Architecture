package cc.ylike.architecture.bean;

/**
 * Created by xsl on 2018/5/17.
 */

public class LoginBean {

    /**
     * msg : success
     * code : 200
     * data : {"personal":{"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHQiOjE1MjYzNzQ2MTY3MTcsIm1vYmlsZSI6IjEzMTI3NTc0NTI2IiwiaWF0IjoxNTI1NzY5ODE2NzE3fQ.X3evq1SPoA0hOR-d0qdKbcb33UuP0hqgBMJNkb0-4_s","sex":"1","avatar":"http://szeling-master.oss-cn-shenzhen.aliyuncs.com/yhy/member/personal/123123123 (2).jpg","nickname":null,"mobile":"13127574526","id":"0957ea0e5c3d400b9172bf543cda29c9"}}
     */

    private String msg;
    private int code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * personal : {"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHQiOjE1MjYzNzQ2MTY3MTcsIm1vYmlsZSI6IjEzMTI3NTc0NTI2IiwiaWF0IjoxNTI1NzY5ODE2NzE3fQ.X3evq1SPoA0hOR-d0qdKbcb33UuP0hqgBMJNkb0-4_s","sex":"1","avatar":"http://szeling-master.oss-cn-shenzhen.aliyuncs.com/yhy/member/personal/123123123 (2).jpg","nickname":null,"mobile":"13127574526","id":"0957ea0e5c3d400b9172bf543cda29c9"}
         */

        private PersonalBean personal;

        public PersonalBean getPersonal() {
            return personal;
        }

        public void setPersonal(PersonalBean personal) {
            this.personal = personal;
        }

        public static class PersonalBean {
            /**
             * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHQiOjE1MjYzNzQ2MTY3MTcsIm1vYmlsZSI6IjEzMTI3NTc0NTI2IiwiaWF0IjoxNTI1NzY5ODE2NzE3fQ.X3evq1SPoA0hOR-d0qdKbcb33UuP0hqgBMJNkb0-4_s
             * sex : 1
             * avatar : http://szeling-master.oss-cn-shenzhen.aliyuncs.com/yhy/member/personal/123123123 (2).jpg
             * nickname : null
             * mobile : 13127574526
             * id : 0957ea0e5c3d400b9172bf543cda29c9
             */

            private String token;
            private String sex;
            private String avatar;
            private String nickname;
            private String mobile;
            private String id;

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
