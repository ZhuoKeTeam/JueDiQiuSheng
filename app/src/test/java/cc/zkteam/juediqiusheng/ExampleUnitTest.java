package cc.zkteam.juediqiusheng;

import com.bro.adlib.statisticsAndLogsTypeTwo.ProxyFactory;
import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.comm.util.AdError;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        ProxyFactory.getsInstance().createProxyObj(UnifiedBannerADListener.class, new UnifiedBannerADListener() {

            @Override
            public void onNoAD(AdError adError) {

            }

            @Override
            public void onADReceive() {

            }

            @Override
            public void onADExposure() {

            }

            @Override
            public void onADClosed() {
                System.out.println("Close");
            }

            @Override
            public void onADClicked() {

            }

            @Override
            public void onADLeftApplication() {

            }

            @Override
            public void onADOpenOverlay() {

            }

            @Override
            public void onADCloseOverlay() {

            }
        }).onADClosed();
    }
}