package com.example.narcis.zvonne;

import android.app.AlarmManager;
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
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.narcis.zvonne.obiecte.coman;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    private static long UPDATE_INTERVAL = 1*5*1000;  //default

    private static Timer timer = new Timer();






    @Override
    public void onCreate() {

        super.onCreate();
        _startService();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            Log.i("Notif", "start service");
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("comenzi");


            Query query = db.orderByChild("nume").equalTo(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    coman c = dataSnapshot.getValue(coman.class);
                    notif(c);
                    Log.i("Notif", "notificare");

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
    }


    private void notif(coman loc) {
        String a="";

        if (loc.getStatus() == 2) {
            a="Comanda a fost acceptata!";
        }
        if (loc.getStatus() == 3) {

            a="Comanda a intrat la bucatarie";
        }
        if (loc.getStatus() == 4) {

            a="Comada a plecat spre dumneavoastra";
        }
        if (loc.getStatus() == 5) {

            a="Comanda a ajuns la destinatie";
        }
        if (loc.getStatus() == 0) {

            a="Comanda a fost anulata";
        }

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notificationIntent.putExtra("fragment", "comenzi");


        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT );

        NotificationManager nm = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);


        Notification.Builder builder = new Notification.Builder(this);
        Resources res = this.getResources();
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.zvonne)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.zvonneicon))
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Zvonne App")
                .setContentText(a);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        Notification n = builder.build();

        nm.notify((int) System.currentTimeMillis(), n);

    }


    private void _startService()
    {
        timer.scheduleAtFixedRate(

                new TimerTask() {

                    public void run() {

                        doServiceWork();

                    }
                }, 1000,UPDATE_INTERVAL);
        Log.i(getClass().getSimpleName(), "FileScannerService Timer started....");
    }

    private void doServiceWork()
    {
        //do something wotever you want
        //like reading file or getting data from network
        try {
        }
        catch (Exception e) {
        }

    }

    private void _shutdownService()
    {
        if (timer != null) timer.cancel();
        Log.i(getClass().getSimpleName(), "Timer stopped...");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        _shutdownService();

        // if (MAIN_ACTIVITY != null)  Log.d(getClass().getSimpleName(), "FileScannerService stopped");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // TODO Auto-generated method stub
        Intent restartService = new Intent(getApplicationContext(),
                this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);

        //Restart the service once it has been killed android


        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +100, restartServicePI);

    }


}
