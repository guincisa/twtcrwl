package gisystems.it.monitoring.twtcrwl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTimeUtils;

import twitter4j.Status;

public class TwtMapper {
	
	public static final String MINUTES = "Minutes";
	public static final String HOURS = "Hours";
	public static final String DAYS = "Days";
	public static final String WEEKS = "Weeks";
	public static final String MONTHS = "Months";
	
	private static LocalDateTime Now;
	private static LocalDateTime StartTime;
	private static long StartTimeStamp;
	private static long NowStamp;
	private static int Intervals;
	private static long IntervalSec;
	private static long PeriodSecs; //Total back time in sec
	private static boolean getSec_done = false;

	//<intervalIndex, numOfTweets>
	//https://examples.javacodegeeks.com/java-basics/java-map-example/
	private Map<Integer, Integer> TwtSet = new HashMap<Integer, Integer>();
	
	//
	private Integer getInterval(Date timestamp){
	    
		long seconds = timestamp.getTime();
		
		DebLog.DLOG("StartTimeStamp " + StartTimeStamp +
				" seconds " + seconds, 1);
		
		if ( StartTimeStamp > seconds){
			DebLog.DLOG("too old",1);
			return -1;
		}

		long delta = seconds -  StartTimeStamp;
				
		Integer res = new Integer(0);
		
		res = (int) Math.floor( ((double)delta)/PeriodSecs * Intervals );
		
		return res;
	}
	private Date getStartDateInterval(int i){
		//returns the start timestamp of that intervalIndex
		return null;
	}
	private long getSec(String period, int periodNum){
		if (getSec_done)
			return PeriodSecs;
		PeriodSecs = -1;
		if (period.compareTo(MINUTES) == 0){
			PeriodSecs =  (long)(60 * periodNum);
		}
		if (period.compareTo(HOURS) == 0){
			PeriodSecs =  (long)(60 * 60 * periodNum);
		}
		if (period.compareTo(DAYS) == 0){
			PeriodSecs =  (long)(24 * 60 * 60 * periodNum);
		}
		if (period.compareTo(WEEKS) == 0){
			PeriodSecs =  (long)(7 * 24 * 60 * 60 * periodNum);
		}
		if (period.compareTo(MONTHS) == 0){
			PeriodSecs =  (long)(30 * 7 * 24 * 60 * 60 * periodNum);
		}
		getSec_done = true;
		return PeriodSecs;

	}
	private long getSecInter(String interval, int intervalNul){
		if (interval.compareTo(MINUTES) == 0){
			return (long)(60 * intervalNul);
		}
		if (interval.compareTo(HOURS) == 0){
			return (long)(60 * 60 * intervalNul);
		}
		if (interval.compareTo(DAYS) == 0){
			return (long)(24 * 60 * 60 * intervalNul);
		}
		if (interval.compareTo(WEEKS) == 0){
			return (long)(7 * 24 * 60 * 60 * intervalNul);
		}
		if (interval.compareTo(MONTHS) == 0){
			return (long)(30 * 7 * 24 * 60 * 60 * intervalNul);
		}
		return (long) -1;

	}

	
	//map with timestamp and number of twts
	private String hashtag;
	public TwtMapper(String hashtag, String period, int periodNum, String interval, int intervalNum){
		this.hashtag = hashtag;
		
		//Set times
		Now = LocalDateTime.now();
		StartTime = Now.minusSeconds(getSec(period, periodNum));
		IntervalSec = getSecInter(interval,intervalNum);
		Intervals = (int)(PeriodSecs / IntervalSec);
		NowStamp = Date.from(Now.atZone(ZoneId.systemDefault()).toInstant()).getTime();
		StartTimeStamp = NowStamp - PeriodSecs;
		//DebLog.DLOG(" period " + (Date.from(Now.atZone(ZoneId.systemDefault()).toInstant()).getTime() - Date.from(StartTime.atZone(ZoneId.systemDefault()).toInstant()).getTime()),1);
		DebLog.DLOG(	"NowStamp " + NowStamp + 
						"\nStartTimeStamp "+ StartTimeStamp + 
						"\nPeriodSec "+ PeriodSecs + 
						"\nIntervalSec " + IntervalSec +
						"\nIntervals " + PeriodSecs/IntervalSec , 1);

		////////////////////
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        String firstformatDateTime = Now.format(formatter);
//        String secondformatDateTime = StartTime.format(formatter);
//		DebLog.DLOG(firstformatDateTime, 1);
//		DebLog.DLOG(secondformatDateTime, 1);
		////////////////////
		

		//number of intervals = getSec / interval_in_sec 

//		long secs = (date.getTime() - this.startDate.getTime()) / 1000;
//		int hours = (int)(secs / 3600);    
//		secs = secs % 3600;
//		int mins = secs / 60;
//		secs = secs % 60;
		return;
	}
	
	public int  InsertStatus(Status status){
		//
		Integer ii = getInterval(status.getCreatedAt());
		DebLog.DLOG("Inserted " + status.getCreatedAt() + " pos " + ii, 1);
		if (ii == -1){
			return -1;
		}
		Integer val = (Integer) TwtSet.get(ii);
		TwtSet.put(ii, val);
		return 0; // -1 if out of range
		
	}
	

}
