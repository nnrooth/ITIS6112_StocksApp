package utils;

import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;

public class TestLogger extends Activity{
	public void run(String words) throws IOException {
		String FILENAME = "log.test";
		
		FileOutputStream out = openFileOutput(FILENAME, Context.MODE_PRIVATE);
		out.write(words.getBytes());
		out.close();
	}
}
