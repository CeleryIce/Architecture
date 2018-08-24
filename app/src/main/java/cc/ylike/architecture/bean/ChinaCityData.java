package cc.ylike.architecture.bean;

import java.util.List;

public class ChinaCityData {

    /**
     * name : 江西省
     * code : 3640000
     * cityList : [{"name":"赣州市","code":"110101","cityList":[{"name":"于都县","code":"110101"}]}]
     */

    private String name;
    private String code;
    private List<CityListBeanX> cityList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<CityListBeanX> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityListBeanX> cityList) {
        this.cityList = cityList;
    }

    public static class CityListBeanX {
        /**
         * name : 赣州市
         * code : 110101
         * cityList : [{"name":"于都县","code":"110101"}]
         */

        private String name;
        private String code;
        private List<CityListBean> cityList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<CityListBean> getCityList() {
            return cityList;
        }

        public void setCityList(List<CityListBean> cityList) {
            this.cityList = cityList;
        }

        public static class CityListBean {
            /**
             * name : 于都县
             * code : 110101
             */

            private String name;
            private String code;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }
        }
    }
}
