using UnityEngine;

//適当なゲームオブジェクトにアタッチする	
public class GCM_Manager : MonoBehaviour
{
	
	public string sendID = "ここにsendID";
	public string apiKey = "ここにapiKey";

	private	AndroidJavaClass clazz;
	
	void Start ()
	{
		AndroidJavaClass unityPlayer = new AndroidJavaClass ("com.unity3d.player.UnityPlayer");
		AndroidJavaObject currentActivity = unityPlayer.GetStatic<AndroidJavaObject> ("currentActivity");
		clazz = new AndroidJavaClass ("jp.unitygames.gcmforunity.MainActivity");
		
		//sendIdをセット
		clazz.SetStatic<string> ("sendID", sendID);
		//apiKeyをセット
		clazz.SetStatic<string> ("apiKey", apiKey);
		//UnitySendMessageで送信先に利用されるゲームオブジェクト名
		clazz.SetStatic<string>("gameObjectName",name);

		//登録
		clazz.CallStatic ("gcmRegistrar", currentActivity);
	}
	


	private string registerd, unregistered, error, message;
	
	//GUI
	void OnGUI ()
	{
		GUILayout.Label ("registerd: " + registerd);
		GUILayout.Label ("unregistered: " + unregistered);
		GUILayout.Label ("error: " + error);
		GUILayout.Label ("message: " + message);

		if (GUILayout.Button ("Send")) {
			//メッセージを送信したい場合に呼び出す
			clazz.CallStatic ("sendGCM", "Unity!!");
		}
	}
	

	//以下、UnitySendMessageから呼び出されるメソッドの方々

	public void OnRegistered (string reg)
	{
		registerd = reg;
		//registrationIDを設定する。必須
		clazz.SetStatic<string> ("registrationID", reg);
	}

	public void OnUnregistered (string unreg)
	{
		unregistered = unreg;
	}
	
	public void OnError (string err)
	{
		error = err;
	}
	
	public void OnMessage (string msg)
	{
		message = msg;
	}
}
