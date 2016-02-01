import java.util.ArrayList;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;




public class WolframAlpha {

	// PUT YOUR APPID HERE: WHY4KR-EKKYY52TJA
	private static String appid = "WHY4KR-EKKYY52TJA";    
	private ArrayList<String> output_text;
	// The WAEngine is a factory for creating WAQuery objects,
	// and it also used to perform those queries. You can set properties of
	// the WAEngine (such as the desired API output format types) that will
	// be inherited by all WAQuery objects created from it.
	WAEngine engine = new WAEngine();

	public WolframAlpha(){

		// These properties will be set in all the WAQuery objects created from this WAEngine.
		engine.setAppID(appid);
		engine.addFormat("plaintext");
		output_text = new ArrayList<String>();
	}

	public void Query(String input){
		// Create the query.
		WAQuery query = engine.createQuery();

		// Set properties of the query.
		query.setInput(input);


		try {
			// Query Url 
			int i = 0;
			System.out.println("Query URL:");
			System.out.println(engine.toURL(query)+"&podstate=Result__Step-by-step+solution");
			System.out.println("");
			WAQuery correctQuery = engine.createQueryFromURL(engine.toURL(query)+"&podstate=Result__Step-by-step+solution"); 


			// This sends the URL to the Wolfram|Alpha server, gets the XML result
			// and parses it into an object hierarchy held by the WAQueryResult object.
			WAQueryResult queryResult = engine.performQuery(correctQuery);

			if (queryResult.isError()) {
				System.out.println("Query error");
				System.out.println("  error code: " + queryResult.getErrorCode());
				System.out.println("  error message: " + queryResult.getErrorMessage());
			} else if (!queryResult.isSuccess()) {
				System.out.println("Query was not understood; no results available.");
			} else {
				System.out.println("Successful query. Pods follow:\n");
				for (WAPod pod : queryResult.getPods()) {
					if (!pod.isError()) {
						System.out.println(pod.getTitle());
						System.out.println("------------");
						for (WASubpod subpod : pod.getSubpods()) {
							for (Object element : subpod.getContents()) {
								if (element instanceof WAPlainText) {
									//TESTING OUT THE OUTPUT and STORING IT
									System.out.println("-------------------LINE "+i+"------------------");
									output_text.add(((WAPlainText) element).getText());
									System.out.println(((WAPlainText) element).getText());
									System.out.println("");
									i++;
								}
							}
						}
						System.out.println("");
					}
				}
			}
		} catch (WAException e) {
			e.printStackTrace();
		}
		System.out.println("---------------------------------------------------------------------");
		for(int i = 0; i < output_text.size(); i++){
			//for(String text : output_text){}
			//System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println(output_text.get(i));
		}
		for(int i = 0; i < 5; i++)
			System.out.println("");
	}
	
	public ArrayList<String> getOutputText(){
		return output_text;
	}

	public static void main(String[] args) {

		//WolframAlpha test = new WolframAlpha();
		//test.Query("4 x+4 x where x=3");


	}

}
