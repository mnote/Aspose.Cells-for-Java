package AsposeCellsExamples.Xlsx;

import AsposeCellsExamples.Utils;
import com.aspose.cells.*;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class ConvertXlsToXlsxWorksheet {

	public static void main(String[] args) throws Exception {

		com.aspose.cells.License license = new com.aspose.cells.License();
		//license.setLicense("Aspose.Cells.Java.lic");

		// The path to the documents directory.
		String dataDir = Utils.getSharedDataDir(ConvertXlsToXlsxWorksheet.class) + "xlsx/";

		byte[] data = FileUtils.readFileToByteArray(new File(dataDir, "Test.xls"));

		// ExStart:1
		// Load sample source file
		Workbook wb = new Workbook(new ByteArrayInputStream(data));

		ByteArrayOutputStream out = new ByteArrayOutputStream(1024 * 1024);

		wb.save(out, SaveFormat.XLSX);

		FileUtils.writeByteArrayToFile(new File(dataDir, "Test.xlsx"), out.toByteArray());

		// Print message
		System.out.println("Convert completed successfully");

	}
}
