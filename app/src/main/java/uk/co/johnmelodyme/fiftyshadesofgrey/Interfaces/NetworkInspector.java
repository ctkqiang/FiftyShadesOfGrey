package uk.co.johnmelodyme.fiftyshadesofgrey.Interfaces;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetworkInspector
{
    public boolean checkConnection(Context context)
    {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE
        );

        if (connMgr != null)
        {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null)
            {
                /* connected to the internet or connected to the mobile provider's data plan */
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                {
                    return true;
                }
                else
                {
                    return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
                }
            }
        }
        return false;
    }

}
