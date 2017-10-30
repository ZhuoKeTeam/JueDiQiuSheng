package cc.zkteam.juediqiusheng.waterfall;

import java.util.List;

/**
 * Created by ztw on 2017/10/30.
 */

public class RefreshBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private CpOneBean cpOne;
        private CpTwoBean cpTwo;
        private CpThreeBean cpThree;
        private int id;
        private String title;
        private int campaignOne;
        private int campaignTwo;
        private int campaignThree;

        public CpOneBean getCpOne() {
            return cpOne;
        }

        public void setCpOne(CpOneBean cpOne) {
            this.cpOne = cpOne;
        }

        public CpTwoBean getCpTwo() {
            return cpTwo;
        }

        public void setCpTwo(CpTwoBean cpTwo) {
            this.cpTwo = cpTwo;
        }

        public CpThreeBean getCpThree() {
            return cpThree;
        }

        public void setCpThree(CpThreeBean cpThree) {
            this.cpThree = cpThree;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCampaignOne() {
            return campaignOne;
        }

        public void setCampaignOne(int campaignOne) {
            this.campaignOne = campaignOne;
        }

        public int getCampaignTwo() {
            return campaignTwo;
        }

        public void setCampaignTwo(int campaignTwo) {
            this.campaignTwo = campaignTwo;
        }

        public int getCampaignThree() {
            return campaignThree;
        }

        public void setCampaignThree(int campaignThree) {
            this.campaignThree = campaignThree;
        }

        public static class CpOneBean {

            private int id;
            private String title;
            private String imgUrl;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }
        }

        public static class CpTwoBean {

            private int id;
            private String title;
            private String imgUrl;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }
        }

        public static class CpThreeBean {
            private int id;
            private String title;
            private String imgUrl;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }
        }
    }
}
