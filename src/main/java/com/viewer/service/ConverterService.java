package com.viewer.service;

import java.io.File;

import org.springframework.stereotype.Service;

/*import com.aspose.words.*;
import  com.aspose.cells.Workbook;
import com.groupdocs.conversion.Converter;
import com.groupdocs.conversion.filetypes.FileType;
import com.groupdocs.conversion.options.convert.ConvertOptions;*/
import java.io.IOException;

import org.jodconverter.local.JodConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import org.jodconverter.core.office.*;


@Service
public class ConverterService {
	


	
    public static File convertFiles(String inputFile, String outputFile) throws IOException, OfficeException {
    	
        File inputDocxFile = new File(inputFile);
        File outputOdtFile = new File(outputFile);


    	final LocalOfficeManager officeManager = LocalOfficeManager.install(); 
    	try {

    	    // Start an office process and connect to the started instance (on port 2002).
    	    officeManager.start();

    	    // Convert
    	    JodConverter
    	             .convert(inputDocxFile)
    	             .to(outputOdtFile)
    	             .execute();
    	    
            return outputOdtFile;

    	}finally {
    	    // Stop the office process
    	    OfficeUtils.stopQuietly(officeManager);

    	} 
    	

    }
	
	
}
