package net.pg.mapper.core.converter;

import net.pg.mapper.core.domain.MappingField;

import org.springframework.util.StringUtils;

public class TextSplitterConverter implements DomainConverter {

   public String convert(MappingField mappingField, String sourceValue, Object... objects) {
      if (!StringUtils.hasText(sourceValue)) {
         return sourceValue;
      }
      StringBuilder destValueBuilder = new StringBuilder();
      String[] subStrings = sourceValue.split(mappingField.getPattern());
      for (int i = 0; i < subStrings.length; i++) {
         destValueBuilder.append(subStrings[i]).append(i == subStrings.length - 1 ? "" : mappingField.getDelemiter());
      }

      String destValue = destValueBuilder.toString();
      if (mappingField.getMaxLength() > 0) {
         destValue = destValueBuilder.substring(0, mappingField.getMaxLength());
      }
      return destValue;
   }
}
