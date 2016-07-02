package net.pg.mapper.core.converter;

import java.text.DecimalFormat;

import net.pg.mapper.core.domain.MappingField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DoubleConverter implements DomainConverter {

   private static final Log LOG = LogFactory.getLog(DoubleConverter.class);

   public String convert(MappingField mappingField, String sourceValue, Object... objects) {
      sourceValue = sourceValue.trim();
      String destValue = "0";
      DecimalFormat formatter = new DecimalFormat(mappingField.getPattern());
      try {
         destValue = formatter.format(Double.parseDouble(sourceValue));
      }
      catch (Exception e) {
         LOG.debug("Error in format sourceValue=" + sourceValue + " using pattern=" + mappingField.getPattern());
         destValue = formatter.format(0);
      }
      if (mappingField.getMaxLength() > 0) {
         destValue = destValue.substring(0, mappingField.getMaxLength());
      }
      return destValue;
   }

}
