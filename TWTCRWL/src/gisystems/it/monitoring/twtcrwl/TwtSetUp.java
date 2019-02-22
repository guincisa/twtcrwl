package gisystems.it.monitoring.twtcrwl;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class  TwtSetUp {

	static Twitter init(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("Y3amDi4voQ9Wr1xDtswUCh8vi")
		.setOAuthConsumerSecret("rxelbV2krqHe9jvqv6jf2gS4E3xMsJYHVOtc1QyY8gYxAcRgDQ")
		.setOAuthAccessToken("40109376-Wz24XYWvg00QitESWYuDVLr124QssQ39ho15d513w")
		.setOAuthAccessTokenSecret("bDH5Bg7swuns3ss5d1g3R2gnu11mYnZgK6LuduAZwEYn9");
		cb.setUser("guincisa");
		cb.setPassword("gugli67.");
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		return twitter;

	}
	static Twitter init(String CK, String CS, String OAAT, String OAATS, String User, String Passwd){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(CK)
		.setOAuthConsumerSecret(CS)
		.setOAuthAccessToken(OAAT)
		.setOAuthAccessTokenSecret(OAATS);
		cb.setUser(User);
		cb.setPassword(Passwd);
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		return twitter;

	}


}
