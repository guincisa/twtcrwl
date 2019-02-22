package gisystems.it.monitoring.twtcrwl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class htgcrawl
{
	public static void main(String[] args) throws Exception 
	{

		DebLog.SetLevel(DebLog.LEV1);

		TwtMapper twtM = new TwtMapper("#bmw", TwtMapper.DAYS, 5, TwtMapper.HOURS, 2);
				
		Twitter twitter = TwtSetUp.init();
		
		Query query = new Query("#bmw");

		int numberOfTweets = 20000;
		int twtperpage = 20;
		long lastID = Long.MAX_VALUE;
		ArrayList<Status> tweets = new ArrayList<Status>();

		while (tweets.size () < numberOfTweets) {
			if (numberOfTweets - tweets.size() > twtperpage)
				query.setCount(twtperpage);
			else 
				query.setCount(numberOfTweets - tweets.size());
			try {
				QueryResult result = twitter.search(query);
				tweets.addAll(result.getTweets());
				System.out.println("Gathered " + tweets.size() + " tweets"+"\n");
				for (Status t: tweets) 
					if(t.getId() < lastID) 
						lastID = t.getId();

			}

			catch (TwitterException te) {
				System.out.println("Couldn't connect: " + te);
				break;
			}; 
			query.setMaxId(lastID-1);
		}

		for (int i = 0; i < tweets.size(); i++) {
			Status t = (Status) tweets.get(i);

			String user = t.getUser().getScreenName();
			int retw = t.getRetweetCount();
			String date = t.getCreatedAt().toString();
			String msg = t.getText();
//			if( t.getGeoLocation() == null){
//				System.out. println("no geoloc");
//			}
//			if (t.getWithheldInCountries()==null){
//				System.out. println("no withheld");
//
//			}
//			if (t.getPlace()==null){
//				System.out. println("no place");
//
//			}else{
//				System.out. println(" place " + t.getPlace().getName() + "  "+t.getPlace().getCountry());
//			}
			//String time = "";
			//if (loc!=null) {
			//Double lat = t.getGeoLocation().getLatitude();
			//Double lon = t.getGeoLocation().getLongitude();*/
			if (twtM.InsertStatus(t) == -1){
			}
			//System.out. println(i + " USER: " + user + " date " + date + " retw " + retw + " mess " + msg.substring(0, 20));
		} 
		DebLog.DLOG("retrieved " + tweets.size()+ " tweets", 1);
		//else 
		//System.out.println(i + " USER: " + user + " wrote: " + msg+"\n");
		DebLog.DebStop();

	}
}
