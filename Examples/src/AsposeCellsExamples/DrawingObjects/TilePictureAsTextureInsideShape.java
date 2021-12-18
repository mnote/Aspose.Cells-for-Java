package AsposeCellsExamples.DrawingObjects;

import com.aspose.cells.*;
import AsposeCellsExamples.Utils;

import java.io.File;

public class TilePictureAsTextureInsideShape {

	static String srcDir = Utils.Get_SourceDirectory();
	static String outDir = Utils.Get_OutputDirectory();

	public static void main(String[] args) throws Exception {

		com.aspose.cells.License license = new com.aspose.cells.License();

		//license.setLicense("Aspose.Cells.Java.lic");

		System.out.println("Aspose.Cells for Java Version: " + CellsHelper.getVersion());

		// Load sample Excel file
		Workbook wb = new Workbook(srcDir + "sampleTextureFill_IsTiling.xlsx");

		// Access first worksheet
		Worksheet ws = wb.getWorksheets().get(0);

		// Access first shape inside the worksheet
		Shape sh = ws.getShapes().get(0);

		// Tile Picture as a Texture inside the Shape
		sh.getFill().getTextureFill().setTiling(true);

		if(!new File(outDir).exists()) {
			new File(outDir).mkdir();
		}

		// Save the output Excel file
		wb.save(outDir + "outputTextureFill_IsTiling.xlsx");

		System.out.println(new File(outDir, "outputTextureFill_IsTiling.xlsx"));

		// Print the message
		System.out.println("TilePictureAsTextureInsideShape executed successfully.");
	}
}
