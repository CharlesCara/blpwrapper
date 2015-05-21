package org.findata.blpwrapper;

import com.bloomberglp.blpapi.*;

import java.util.logging.Logger;

public class BeqsDataResult extends DataResult {
  private String[] fields;
  private String[] securities;
  private String[] data_types;
  private String[][] result_data;

  public BeqsDataResult(String[] argSecurities, String[] argFields) {
    // With BEQS we do not know the size of the result data until
    // we process response, so we cannot pre initialise the output 
    
  }

  public String[][] getData() {
    return(result_data);
  }

  public String[] getColumnNames() {
    return(fields);
  }

  public String[] getRowNames() {
    return(securities);
  }

  public String[] getDataTypes() {
    return(data_types);
  }

  public void processResponse(Element response, Logger logger, boolean throwInvalidTickerError) throws WrapperException {
    Element securityDataArray = response.getElement("securityData");
    int numItems = securityDataArray.numValues();
    if (numItems == 0 ){
      logger.info("No securities in response");
      securities = new String[0];
      fields = new String[0];
      data_types = new String[0];
      result_data = new String[0][0];
      return;
    }
      
    securities = new String[numItems];
    
    // Use the number of fields in the first security to size the results_data array
    Element secData = securityDataArray.getValueAsElement(0);
    Element fldData = secData.getElement("fieldData");
    int numCols = fldData.numValues();
    fields = new String[numCols];

    data_types = new String[fields.length];
    // Because we may get data type info out of order, need to
    // initialize array at start with a default value.
    for (int i = 0; i < fields.length; i++) {
      // Call this "NOT_APPLICABLE" since "NA" causes problems in R.
      data_types[i] = "NOT_APPLICABLE";
    }

    result_data = new String[securities.length][fields.length];
    
    
    for (int i = 0; i < numItems; i++) {
      Element securityData = securityDataArray.getValueAsElement(i);
      Element fieldData = securityData.getElement("fieldData");
      int seq = securityData.getElementAsInt32("sequenceNumber");

      processSecurityError(securityData, logger, throwInvalidTickerError);
      processFieldExceptions(securityData, logger);

      int field_data_counter = 0;
      for (int j = 0; j < fields.length; j++) { 
        String field_name = fields[j];

        if (field_data_counter < fieldData.numElements()) {
          logger.finest("i = " + i + "\n" + "seq = " + seq + "\n" + "j = " + j + "\n" + "field_data_counter = " + field_data_counter);
          Element field = fieldData.getElement(field_data_counter);
          if (field.name().toString().equals(field_name)) {
            // Raise an error if we're trying to read SEQUENCE data.
            // Store the data type for later (if it hasn't already been stored).
            if (data_types[j].equals("NOT_APPLICABLE")) {
              if (field.datatype().intValue() == Schema.Datatype.Constants.SEQUENCE) {
                throw new WrapperException("reference data request cannot handle SEQUENCE data in field " + field.name().toString());
              }
              String data_type = field.datatype().toString();
              if (!data_type.equals("NA")) {
                logger.finest("Setting field data type to " + data_type);
                data_types[j] = data_type;
              }
            } else {
              logger.finest("Field data type is " + data_types[j]);
            }

            String value = field.getValueAsString();

            logger.finest("Setting field value to " + value);
            field_data_counter++;

            if (value.equals("-2.4245362661989844E-14")) {
              logger.info("Numeric of -2.4245362661989844E-14 encountered. Not a real value. Will be left NULL.");
            } else {
              result_data[seq][j] = value;
            }
          } else {
            logger.finest("Skipping field.");
          }
        }

      } 
    }
  }
}
