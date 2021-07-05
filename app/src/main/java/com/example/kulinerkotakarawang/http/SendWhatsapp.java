package com.example.kulinerkotakarawang.http;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class SendWhatsapp {

    public static void whatsapp(Activity activity, String phone, String message) {
        try{
            Uri uri = Uri.parse("https://wa.me/"+phone+"?text="+message);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.whatsapp");
            activity.startActivity(intent);
        }
        catch(Exception e)
        {
            Toast.makeText(activity,"Error/n"+ e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
}
