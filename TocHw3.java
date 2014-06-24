/* 資訊104 F74004020 呂則慷 HW3
   簡單說明：一開始把URL中的資訊抓下來，並形成json的格式，運用RE，首先找"鄉鎮市區"這欄有沒有使用者所輸入的地區，例：文山區
   若是有的話，再往下搜尋"土地區段位置或建物區門牌"，看有無使用者輸入的路段在他的部分字串中，若有的話，最後判斷輸入的
   "交易年月"，因為必須是所有at/after該year的資訊都要有，故有用到integer比較大小，最後將符合的部分之"總價元"相加取平均，
   就得到了最後的結果。
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONArray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;;

public class TocHw3 {
    private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }
public static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONArray json = new JSONArray(jsonText);
      return json;
    } finally {
      is.close();
    }
  }

public static void main(String[] args) throws IOException, JSONException {
    JSONArray json = readJsonFromUrl(args[0]);
    Pattern pattern1=Pattern.compile(args[1]);
    Pattern pattern2=Pattern.compile(args[2]);
    int i;
    int TotalMoney=0,NumOfData=0;
    int A=json.length();
    int test = Integer.parseInt(args[3]+"00");
    for(i=0;i<A-1;i++)
    {
    	Matcher matcher1=pattern1.matcher(json.getJSONObject(i).getString("鄉鎮市區"));
    	if(matcher1.find())
    	{
		    	Matcher matcher2=pattern2.matcher(json.getJSONObject(i).getString("土地區段位置或建物區門牌"));
		    	if(matcher2.find())
		    	{
		    		if(json.getJSONObject(i).getInt("交易年月")>=test)
		    		{
						    TotalMoney=TotalMoney+json.getJSONObject(i).getInt("總價元");
						    NumOfData++;
		    		}
		    	}
    	}
    }
    System.out.println(TotalMoney/NumOfData);
  }
}

