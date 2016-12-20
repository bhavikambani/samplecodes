package com.sapient.usecases.uc1_ExternalSort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * The Class ExternalSortImpl implements the usecase 1 of MergeSort problem.
 * 
 * @author Bhavik Ambani
 */
@SuppressWarnings("rawtypes")
public class ExternalSortImpl {

	/** The outputfile. */
	static String outputfile;

	/**
	 * Sort file.
	 *
	 * @param file
	 *            the file
	 */
	static void sortFile(File file) {
		try {
			BufferedReader fbr = new BufferedReader(new FileReader(file));
			List<Integer> list = new ArrayList<Integer>();

			String line = "";
			while ((line = fbr.readLine()) != null) {
				list.add(Integer.parseInt(line));
			}
			Collections.sort(list);
			writeData(file, list);
			fbr.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Merge sorted files.
	 *
	 * @param files
	 *            the files
	 * @param outputfile
	 *            the outputfile
	 * @param cmp
	 *            the cmp
	 * @return the int
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static int mergeSortedFiles(List<File> files, File outputfile, final Comparator<String> cmp)
			throws IOException {
		PriorityQueue<BinaryFileBuffer> pq = new PriorityQueue<BinaryFileBuffer>(11,
				new Comparator<BinaryFileBuffer>() {
					public int compare(BinaryFileBuffer i, BinaryFileBuffer j) {
						return cmp.compare(i.peek(), j.peek());
					}
				});
		for (File f : files) {
			BinaryFileBuffer bfb = new BinaryFileBuffer(f);
			pq.add(bfb);
		}
		BufferedWriter fbw = new BufferedWriter(new FileWriter(outputfile));
		int rowcounter = 0;
		try {
			while (pq.size() > 0) {
				BinaryFileBuffer bfb = pq.poll();
				String r = bfb.pop();
				fbw.write(r);
				fbw.newLine();
				++rowcounter;
				if (bfb.empty()) {
					bfb.fbr.close();
				} else {
					pq.add(bfb);
				}
			}
		} finally {
			fbw.close();
			for (BinaryFileBuffer bfb : pq)
				bfb.close();
		}
		return rowcounter;
	}

	/**
	 * Write data.
	 *
	 * @param file
	 *            the file
	 * @param list
	 *            the list
	 */
	private static void writeData(File file, List<Integer> list) {
		try {
			BufferedWriter fbw = new BufferedWriter(new FileWriter(file));
			for (Integer data : list) {
				fbw.write(data);
			}
			fbw.close();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {

		}

	}

	/**
	 * Gets the files info.
	 *
	 * @param path
	 *            the path
	 * @return the files info
	 */
	public void getFilesInfo(String path) {

		List<File> list = new ArrayList<File>();

		File file = new File(path);
		File files[] = file.listFiles();
		for (File fl : files) {
			sortFile(fl);
			list.add(fl);

		}
		Comparator<String> comparator = new Comparator<String>() {
			public int compare(String r1, String r2) {
				return r1.compareTo(r2);
			}
		};

		try {
			mergeSortedFiles(list, new File(outputfile), comparator);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		System.out.println("Please enter the folder path:");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		outputfile = "";
		ExternalSortImpl test = new ExternalSortImpl();
		try {
			test.getFilesInfo(br.readLine());
			System.out.println("Please enter the destination folder path:");
			outputfile = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

class BinaryFileBuffer<T> {
	public static int BUFFERSIZE = 2048;
	public BufferedReader fbr;
	public File originalfile;
	private String cache;
	private boolean empty;

	public BinaryFileBuffer(File f) throws IOException {
		originalfile = f;
		fbr = new BufferedReader(new FileReader(f), BUFFERSIZE);
		reload();
	}

	public boolean empty() {
		return empty;
	}

	private void reload() throws IOException {
		try {
			if ((this.cache = fbr.readLine()) == null) {
				empty = true;
				cache = null;
			} else {
				empty = false;
			}
		} catch (EOFException oef) {
			empty = true;
			cache = null;
		}
	}

	public void close() throws IOException {
		fbr.close();
	}

	public String peek() {
		if (empty())
			return null;
		return cache.toString();
	}

	public String pop() throws IOException {
		String answer = peek();
		reload();
		return answer;
	}
}