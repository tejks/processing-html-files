package pl.smallgis.html_project_v2;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Main {

	private static Scanner in;
	private static Scanner ih;
	private static Scanner ii;
	private static Scanner iheader;
	private static Scanner ihtml;
	private static Scanner iimage;

	public static void main(String[] args) throws IOException {

		if (args.length != 1)
			throw new RuntimeException("File path is empty");
			
		String filePath = args[0];

		int startValue = filePath.lastIndexOf("\\");
		int endValue = filePath.lastIndexOf(".");
		String value = filePath.toString().substring(startValue, endValue);

		File file = new File(".\\..\\results\\" + value + ".html");

		if (file.exists()) {
			System.out.println("File exists");
		} else {

			String HTMLString = null;
			in = new Scanner(new File(filePath));

			HTMLString = in.nextLine();
			while (in.hasNextLine()) {
				HTMLString += in.nextLine() + "\r";

			}

			Document html = (Document) Jsoup.parse(HTMLString);

			String table = html.select("table table tr").toString();
			String tableHead = html.select("table table thead").toString();
			String caption = html.select("table table caption").toString();

			String oneLineHead = null;
			String allLinesHead = null;
			String fullHead = null;

			iheader = new Scanner(tableHead);

			int u = 0;

			while (iheader.hasNextLine()) {
				oneLineHead = iheader.nextLine();
				allLinesHead += oneLineHead + "\n";
				u++;
			}

			fullHead = allLinesHead.substring(4);

			String oneLineHtml = null;
			String allLinesHtml = null;
			String fullHtml = null;

			ih = new Scanner(table);

			for (int i = 0; i <= u - 2; i++) {
				oneLineHtml = ih.nextLine();
			}

			oneLineHtml = ih.nextLine();
			oneLineHtml = ih.nextLine();
			while (ih.hasNextLine()) {
				oneLineHtml = ih.nextLine();
				allLinesHtml += oneLineHtml.replace("bgcolor=\"#4040FF\"></td>", "bgcolor=\"#4040FF\"><img src=\"/sigw/sw_cubierto.png\" alt=\"sw_cubierto.png\" onmouseover=\" overlib(\"     \"); \" onmouseout=\" return nd();\"></td>") + "\n";
			}

			fullHtml = allLinesHtml.substring(4);
			
			String fullHtmlv2 = fullHtml;
			
			Document htmlv2 = (Document) Jsoup.parse(fullHtmlv2);
			
			String img = htmlv2.select("img").toString();

			String oneLineImg = null;
			String allLinesImg = null;
			String fullImg = null;

			ii = new Scanner(img.toString());

			while (ii.hasNextLine()) {
				oneLineImg = ii.nextLine();
				int start = oneLineImg.indexOf("overlib(");
				int end = oneLineImg.indexOf("onmouseout=\" return nd()");
				allLinesImg += " <td bgcolor=\"#4040FF\">"
						+ oneLineImg.substring(start + 9, end - 6).replaceAll(",", ".") + "</td>" + "\n";
			}

			fullImg = allLinesImg.substring(4);

			ihtml = new Scanner(fullHtml.toString());
			iimage = new Scanner(fullImg.toString());

			int endHtml = u - 9;
			int endImg = 7;
			String elementHtml = null;
			String elementImg = null;
			String fullElement = null;

			while (ihtml.hasNextLine() && iimage.hasNextLine()) {
				for (int z = 0; z <= endHtml; z++) {
					elementHtml = ihtml.nextLine();
					fullElement += elementHtml + "\n";

				}
				for (int x = 0; x <= endImg; x++) {
					elementImg = iimage.nextLine();
					fullElement += elementImg + "\n";
				}
				for (int y = 0; y <= 8; y++) {
					elementHtml = ihtml.nextLine();
				}
			}

			String readyHTML = fullElement.replace("<tr>", "</tr> \n <tr>").substring(9).trim() + "</tr>";

			String header = "<!DOCTYPE HTML>" + "\n" + "<html>" + "\n" + "<head>" + "\n"
					+ "<title>Synop report summary</title>" + "\n" + "</head>" + "\n" + "<body>" + "\n"
					+ "<table align=\"center\" border=0 cellspacing=1 bgcolor='#d0d0d0' width=\"95%\">" + "\n" + caption
					+ "\n";

			String footer = "\n" + "</table>" + "\n" + "</body>" + "\n" + "</html>";

			File folder = new File(".\\..\\results");

			if (folder.exists()) {
				PrintWriter save = new PrintWriter(".\\..\\results" + value + ".html");
				save.println(header + fullHead + readyHTML + footer);
				save.close();
				System.out.println("Successful");
			} else {
				Files.createDirectory(Paths.get(".\\..\\results"));
				PrintWriter save = new PrintWriter(".\\..\\results" + value + ".html");
				save.println(header + fullHead + readyHTML + footer);
				save.close();
				System.out.println("Successful");
			}

		}

	}

}
