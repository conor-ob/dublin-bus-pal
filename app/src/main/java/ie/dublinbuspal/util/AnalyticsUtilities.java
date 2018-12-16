package ie.dublinbuspal.util;

//import com.crashlytics.android.answers.Answers;
//import com.crashlytics.android.answers.ContentViewEvent;

import java.util.Map;

public final class AnalyticsUtilities {

    private static final String SCREEN_VIEWED_EVENT_ID = "screen.view";
    private static final String SCREEN_VIEWED_EVENT_TYPE = "Screen Viewed";

    private AnalyticsUtilities() {
        throw new UnsupportedOperationException();
    }

    public static void logScreenViewedEvent(String screenName,
                                                        Map<String, String> customAttributes) {
//        ContentViewEvent contentViewEvent = new ContentViewEvent()
//                .putContentId(SCREEN_VIEWED_EVENT_ID)
//                .putContentType(SCREEN_VIEWED_EVENT_TYPE)
//                .putContentName(screenName);
//
//        for (Map.Entry<String, String> entry : customAttributes.entrySet()) {
//            contentViewEvent.putCustomAttribute(entry.getKey(), entry.getValue());
//        }
//
//        Answers.getInstance().logContentView(contentViewEvent);
    }

}
