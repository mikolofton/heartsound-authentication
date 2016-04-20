import java.io.*;
import java.util.*;
import java.math.*;

public class HS_KeyGen {
	public static List<HeartSound> hsList = new ArrayList<HeartSound>();
	
	public static HeartSound generateHeartSound(File file) throws IOException {
		String fileName = file.getPath();
		String fileID = "HS" + file.getName().split(".txt")[0];

		FileInputStream hsFile = new FileInputStream(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(hsFile));

		String fftLine;
		List<Double> fileFFT = new ArrayList<Double>();

		reader.readLine(); // Read first line and do nothing to skip

		while ((fftLine = reader.readLine()) != null)   {
			String[] str = fftLine.split("\\s+-");
			
			double current = Double.parseDouble(str[1]);

			fileFFT.add(current);
		}

		reader.close();

		HeartSound hs = new HeartSound(fileID, fileFFT);

		return hs;

	}

	public static void hsOutput(List<HeartSound> hsl, String fdr) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter outputFile = new PrintWriter(fdr + ".txt", "UTF-8");

		for (HeartSound h : hsl) {
			outputFile.println(h.getId() + "," + h.getKey());
			System.out.println("Successfully generated: " + h.getId() + "," + h.getKey());
		}

		outputFile.close();
	}

	public static void main(String[] args) {
		String folder = args[0];
		File directory = new File("./" + folder + "/");
		File[] fileList = directory.listFiles(new FilenameFilter() {
	        public boolean accept(File dir, String name) {
	            return !name.equals(".DS_Store");
	    	}}
    	);
    	

		for (File f : fileList) {
			try {
				HeartSound hsa = generateHeartSound(f);
				hsList.add(hsa);
			}
			catch (IOException e) {
				System.out.println("Error:" + e.getMessage());
			}
		}


		try {
			hsOutput(hsList, folder);
		}
		catch (FileNotFoundException f) {
			f.getMessage();
		}
		catch (UnsupportedEncodingException g) {
			g.getMessage();
		}

	}

}

class HeartSound {
	private String id;
	private List<Double> fft = new ArrayList<Double>();
	private double sum;
	private double mean;
	private double rms;
	private double stdDev;

	public HeartSound(String hsID, List<Double> fftData) {
		id = hsID;
		fft = fftData;
		sum = getSum();
		mean = getMean();
		rms = getRMS();
		stdDev = getStdDev();
	}

	public String getId() {
		return id;
	}

	private double getSum() {
		double s = 0;
		for(Double i : fft) {
			s += i;
		}
		return s;
	}

	private double getMean() {
		double m = 0;
		double s = getSum();
		int l = fft.size();
		m = s/l;
		return m;
	}

	private double getRMS() {
		double rm = 0;
		double sqSum = 0;
		double sq = 0;
		int l = fft.size();
		for(Double i : fft) {
			sq = Math.pow(i, 2);
			sqSum += sq;
		}
		rm = Math.sqrt(sqSum/l);
		return rm;
	}

	private double getStdDev() {
		double sd = 0;
		double diff = 0;
		double v = 0;
		double m = getMean();
		int l = fft.size();
		for(Double i : fft) {
			diff = Math.pow(m - i, 2);
			v += diff;
		}

		sd = Math.sqrt(v/l);
		return sd;
	}

	public String getKey() {
		String concat = String.valueOf(mean) + String.valueOf(rms) + String.valueOf(stdDev);
		String key = concat.replace(".", "");
		return key;
	}

}