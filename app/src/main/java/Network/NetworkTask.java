package Network;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;

import static common.Scan.BR_ItemList;
import static common.Scan.BR_ShopList;
import static common.Scan.HTTP_ACTION_ITEMLIST;
import static common.Scan.HTTP_ACTION_SHOPLIST;
import static common.Scan.HTTP_RESPONSE_FAIL;
import static common.Scan.HTTP_RESPONSE_OK;
import static common.Scan.KEY_ItemList;
import static common.Scan.KEY_ShopList;
import static common.Scan.selectedUrl;

/**
 * Created by Administrator on 2017-04-15.
 */

public class NetworkTask extends AsyncTask<Map<String, String>, Integer, String> {
    private Context context;
    private int statusCode;
    private String funcURL;
    private int action;

    public NetworkTask(Context context, int action, String funcURL){
        this.context = context;
        this.funcURL = funcURL;
        this.action = action;
    }

    @Override
    protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터
        HttpClient.Builder http;

        http = new HttpClient.Builder("POST", selectedUrl+funcURL);
        Log.d("HTTP", "****HTTP Request : " + selectedUrl+funcURL);
        http.addAllParameters(maps[0]);


//Http 요청 전송
        HttpClient post = http.create();
        post.request();

// 응답 상태코드 가져오기
        statusCode = post.getHttpStatusCode();
        Log.d("HTTP", "Result from server : " + statusCode);
// 응답 본문 가져오기
        String body = "";
        if(statusCode == 200) {
            body = post.getBody();
            return body;
        }else if(statusCode == -10){
            Toast.makeText(context, "서버 응답이 없습니다.", Toast.LENGTH_SHORT).show();
        }
        return HTTP_RESPONSE_FAIL;
    }

    @Override
    protected void onPostExecute(String s) {
        if(s != null) {
            if(s.equals(HTTP_RESPONSE_OK)) //등록을 하여 OK를 받는경우
                Toast.makeText(context, "요청이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            else { //특정 목록을 가져오는 경우
                Intent i = new Intent();
                if(action == HTTP_ACTION_SHOPLIST) {
                    i.putExtra(KEY_ShopList, s);
                    i.setAction(BR_ShopList);
                }else if(action == HTTP_ACTION_ITEMLIST){
                    i.putExtra(KEY_ItemList, s);
                    i.setAction(BR_ItemList);
                }
                context.sendBroadcast(i);
            }
            if(s != null)
            Log.d("HTTP", "Response from server : " + s);
        }
        else {
            Toast.makeText(context, "서버 프로토콜에 문제가있습니다.", Toast.LENGTH_SHORT).show();
            Log.e("HTTP", "Response error");
        }

    }

}
