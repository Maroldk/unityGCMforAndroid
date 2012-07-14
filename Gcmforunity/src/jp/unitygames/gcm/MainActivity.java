package jp.unitygames.gcm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;

import com.google.android.gcm.GCMRegistrar;
import com.unity3d.player.UnityPlayer;

public class MainActivity {
	public static String gameObjectName;
	public static String sendID;
	public static String registrationID;
	public static String apiKey;

	public static void gcmRegistrar(final Activity activity) {
		GCMRegistrar.checkDevice(activity);
		GCMRegistrar.checkManifest(activity);
		String regId = GCMRegistrar.getRegistrationId(activity);
		if (regId.equals("")) {
			GCMRegistrar.register(activity, sendID);
		} else {
			UnityPlayer.UnitySendMessage(MainActivity.gameObjectName,
					"OnRegistered", regId);
		}
	}

	public static void sendGCM(final String message)
			throws ClientProtocolException, IOException {

		final String url = "https://android.googleapis.com/gcm/send";
		final String registrationId = MainActivity.registrationID;
		final String apiKey = MainActivity.apiKey;

		HttpPost req = new HttpPost(url);
		req.addHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		req.addHeader("Authorization", "key=" + apiKey);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("registration_id", registrationId));
		params.add(new BasicNameValuePair("collapse_key", "update"));
		params.add(new BasicNameValuePair("data.message", message));
		req.setEntity(new UrlEncodedFormEntity(params));

		new DefaultHttpClient().execute(req);
	}
}
