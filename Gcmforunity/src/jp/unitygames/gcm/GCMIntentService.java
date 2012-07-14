package jp.unitygames.gcm;

import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.GCMBaseIntentService;
import com.unity3d.player.UnityPlayer;

public class GCMIntentService extends GCMBaseIntentService {

	public GCMIntentService() {
		super(MainActivity.sendID);
	}

	@Override
	public void onRegistered(Context context, String registrationId) {
		UnityPlayer.UnitySendMessage(MainActivity.gameObjectName,
				"OnRegistered", registrationId);
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		UnityPlayer.UnitySendMessage(MainActivity.gameObjectName,
				"OnUnregistered", "Unregistered");
	}

	@Override
	public void onError(Context context, String errorId) {
		UnityPlayer.UnitySendMessage(MainActivity.gameObjectName, "OnError",
				errorId);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		UnityPlayer.UnitySendMessage(MainActivity.gameObjectName, "OnMessage",
				intent.getStringExtra("message"));
	}
}