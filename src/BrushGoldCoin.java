public class BrushGoldCoin {

    private static final int SHUABAO = 0;
    private static final int DOUYIN = 1;
    private static final int KUAISHOU = 2;

    private static final String[] APPS = {
            // 刷宝
            "com.jm.video/.ui.main.MainActivity",
            // 抖音
            "com.ss.android.ugc.aweme.lite/com.ss.android.ugc.aweme.main.MainActivity",
            // 快手
            "com.kuaishou.nebula/com.yxcorp.gifshow.HomeActivity"
    };

    private static final String[] APP_NAME = {
            "刷宝",
            "抖音",
            "快手"
    };

    private static int count = 0;
    private static int curindex = 0;

    public static void main(String[] args) {
        while (true) {
            try {
                startApp();
                swipe();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 启动App
     */
    private static void startApp() {
        try {
            Runtime.getRuntime().exec("adb shell am start -n " + APPS[curindex]);
            System.out.println(APP_NAME[curindex]);
            Thread.sleep(getBaseTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上滑
     */
    private static void swipe() {
        int lastIndex = curindex;
        while (curindex == lastIndex) {
            try {
                Runtime.getRuntime().exec("adb shell input swipe 300 800 300 200");
                System.out.println("第" + (++count) + "个");
                double random = getRandomRange();
                random += getBaseTime();
                System.out.println("耗时 ：" + random);
                Thread.sleep((long) random);
                curindex = count / getIntervalCount() % APPS.length;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 得到基准时间
     * @return 每个App停留的基准时间，秒
     */
    private static int getBaseTime() {
        switch (curindex) {
            case SHUABAO:
                // 3秒
                return 3000;
            case DOUYIN:
            case KUAISHOU:
            default:
                // 5秒
                return 5000;
        }
    }

    /**
     * 得到随机数
     * @return 随机数的范围
     */
    private static double getRandomRange() {
        switch (curindex) {
            case SHUABAO:
            case DOUYIN:
            case KUAISHOU:
            default:
                // 1~5秒
                return Math.random() * 5 * 1000;
        }
    }

    /**
     * 得到每个App播放多少条视频后切换App
     * @return App切换需要播放的视频数
     */
    private static int getIntervalCount() {
        return 20;
    }
}
