package com.example.narcis.zvonne;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MyService extends Service {


    private boolean bool=false;

    public MyService() {
        Log.i("Notif","contructor");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("Notif","ibind error");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Notif","start service");
        DatabaseReference db=FirebaseDatabase.getInstance().getReference().child("Zvonne").child("comenzi");
        Query query=db.orderByChild("nume").equalTo(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                notif();
                Log.i("Notif","notificare");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void notif() {
        if (bool) {

            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.putExtra("fragment", "comenzi");
            PendingIntent contentIntent = PendingIntent.getActivity(this,
                    2, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            Intent resultIntent = new Intent(this, MainActivity.class);
            resultIntent.setAction(Intent.ACTION_MAIN);
            resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);

            NotificationManager nm = (NotificationManager) this
                    .getSystemService(Context.NOTIFICATION_SERVICE);


            Notification.Builder builder = new Notification.Builder(this);
            Resources res = this.getResources();
            builder.setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.zvonne)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.zvonne))
                    .setTicker("Ticker")
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setContentTitle("Zvonne App")
                    .setContentText("O actualizare noua");
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(alarmSound);
            Notification n = builder.build();

            nm.notify(1, n);
        }
        else bool=true;
    }
}
