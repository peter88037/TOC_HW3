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
   // System.out.println(json.toString());
    int i;
   // String str1=new String();
  //  String str2="文山區";
  //  String str3="辛亥路";
  //  String str4=new String();
    
    
   
    
    
    Pattern pattern1=Pattern.compile(args[1]);
    Pattern pattern2=Pattern.compile(args[2]);
  //  Pattern pattern3=Pattern.compile(args[3]);
    

    
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
			    	//	if(matcher3.find())
			    	//	{
						//    System.out.println(json.getJSONObject(i).getInt("交易年月"));
						    
						    TotalMoney=TotalMoney+json.getJSONObject(i).getInt("總價元");
						    NumOfData++;
			    	//	}
		    		}
		    	}
    	}
 
    }
    
    System.out.println(TotalMoney/NumOfData);
  }
}

