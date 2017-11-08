package cc.zkteam.juediqiusheng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangQing on 2017/11/7.
 */

public class TestData {

    public static List getTestPics() {
        List<String> images = new ArrayList<>();
        images.add("http://h.hiphotos.baidu.com/image/h%3D300/sign=7e74fc06cacec3fd943ea175e689d4b6/1f178a82b9014a9031654777a0773912b21beea5.jpg");
        images.add("http://e.hiphotos.baidu.com/image/h%3D300/sign=37c9dcf34236acaf46e090fc4cd88d03/18d8bc3eb13533fa7ead0ce4a1d3fd1f40345be5.jpg");
        images.add("http://g.hiphotos.baidu.com/image/h%3D300/sign=31102bed8382b90122adc533438fa97e/4e4a20a4462309f76ebeadd87b0e0cf3d6cad623.jpg");
        images.add("http://h.hiphotos.baidu.com/image/h%3D300/sign=fe9e83d388025aafcc3278cbcbecab8d/f3d3572c11dfa9eca59e930468d0f703918fc175.jpg");
        images.add("http://a.hiphotos.baidu.com/image/h%3D300/sign=7985ff456581800a71e58f0e813733d6/d50735fae6cd7b89368c3c49062442a7d8330e13.jpg");
        images.add("http://e.hiphotos.baidu.com/image/h%3D300/sign=00a3c3e533292df588c3aa158c305ce2/9345d688d43f8794308f4ee4db1b0ef41ad53a83.jpg");
        images.add("http://d.hiphotos.baidu.com/image/h%3D300/sign=da030b7570ec54e75eec1c1e89399bfd/314e251f95cad1c85cba7a6c743e6709c93d517c.jpg");
        return images;
    }

    public static List getDefaultTextData() {
        String[] list= new String[] {
                "one", "two", "three", "four", "five",
        };

        List<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            arrayList.add(list[i % 5] + "-" + i);
        }
        return arrayList;
    }
}
