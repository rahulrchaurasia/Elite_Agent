package account.rb.com.elite_agent.utility;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import account.rb.com.elite_agent.R;
import account.rb.com.elite_agent.login.loginActivity;

/**
 * Created by IN-RB on 09-02-2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public static final String CHANNEL_ID = "account.rb.com.elite_agent.eliteid";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        sendNotification(remoteMessage, remoteMessage.getData());
    }

    private void sendNotification(RemoteMessage remoteMessage, Map<String, String> data) {

//        int NOTIFICATION_ID = 0;
        int NOTIFICATION_ID = 0;

        NOTIFICATION_ID = (int) Math.round(Math.random() * 100);
        if (remoteMessage.getData().size() == 0) {
            Log.d(TAG, "Message Data Body Empty: ");
            return;
        }
        Log.d(TAG, remoteMessage.getData().get("notifyFlag"));
        Map<String, String> NotifyData = remoteMessage.getData();
        Intent intent;
        if (NotifyData.get("notifyFlag") == null) {
            return;
        } else {
            String type = NotifyData.get("notifyFlag");

            intent = new Intent(this, loginActivity.class);
            intent.putExtra(Constants.PUSH_NOTIFY, type);

        }


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) Math.round(Math.random() * 100), intent,
                PendingIntent.FLAG_CANCEL_CURRENT);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder notificationBuilder = null;

        notificationBuilder = new NotificationCompat.Builder(this,CHANNEL_ID);

        notificationBuilder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(NotifyData.get("title"))
                .setContentText(NotifyData.get("body"))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setTicker("Elite")
                .setPriority(Notification.PRIORITY_HIGH)
                .setWhen(System.currentTimeMillis())

                .setStyle(new NotificationCompat.BigTextStyle().bigText(NotifyData.get("body")))
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }
}
