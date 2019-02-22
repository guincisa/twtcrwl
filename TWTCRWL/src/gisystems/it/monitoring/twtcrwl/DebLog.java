package gisystems.it.monitoring.twtcrwl;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.google.common.base.Splitter;

public class DebLog {
	
//	DebLog.SetLevel(DebLog.LEV1);
//	DebLog.DLOG("lfdsahfshjflksahflsakfhlksfhlksfhslhlsakhflsfhldshflksfhlksafhlksafhlsakhflkjsahflksahflksahflkshflkdshflkdshflkdshflksfhs", 1);

	//todo buff in and dump later
	//
	
	//levels
	public static final String MUTE = "MUTE";
	public static final String LEV1 = "LEV1";
	public static final String LEV2 = "LEV2";
	public static final String LEV3 = "LEV3";
	
	static BufferedWriter writer;
	static boolean init = false;
	
	static int Level = 0;
	static int CTR = 1;
	static int SIZE = 70;
	public static void SetSize(int s){
		SIZE = s;
	}
	public static void SetLevel(String level){
		if ( level.compareTo("MUTE") == 0){
			Level = 0;
		}
		if ( level.compareTo("LEV1") == 0){
			Level = 1;
		}
		if ( level.compareTo("LEV2") == 0){
			Level = 2;
		}
		if ( level.compareTo("LEV3") == 0){
			Level = 3;
		}

	}
	
	static void DLOG(String S, int L){
		if (!init){
			DebInit();
			init = true;
		}
//		if (L <= Level){
//			DecimalFormat formatter = new DecimalFormat("0000000000");
//			String aFormatted = formatter.format(CTR);
//			
//			Date date = new Date();
//			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
//			System.out.println("+++\nTWTCRWL:" + aFormatted + " "+ dateFormat.format(date) + " LEV "+ L +"\n");
//			for(final String token :
//			    Splitter
//			        .fixedLength(SIZE)
//			        .split(S)){
//			    System.out.println(token);
//			}
//			System.out.println("---\n");
//			CTR++;
//		}
		if (L <= Level){
			try{

				DecimalFormat formatter = new DecimalFormat("0000000000");
				String aFormatted = formatter.format(CTR);
				
				Date date = new Date();
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
				writer.write("+++\nTWTCRWL:" + aFormatted + " "+ dateFormat.format(date) + " LEV "+ L +"\n\n");
				//Split only the part with more that SIZE
				String lines[] = S.split("\\r?\\n");
				for (int i = 0 ; i < lines.length ; i ++){
					if (lines[i].length()> SIZE){
						for(final String token :
						    Splitter
						        .fixedLength(SIZE)
						        .split(lines[i])){
							writer.write(token+"\n");
						}
					}else{
						writer.write(lines[i]+"\n");
					}
				}
				writer.write("---\n\n");
				CTR++;
			}
			catch (IOException e){
			}
		}

	}
	
	static public void DebInit() {
		try{
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("log.txt"), "utf-8"));
		}
		catch (IOException e){
		}
	}
	static public void DebStop() {
		try{
			writer.close();
		}
		catch (IOException e){
		}
	}

}
