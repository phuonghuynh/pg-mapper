package net.pg.mapper.core.converter;

import net.pg.mapper.core.domain.MappingField;

import org.springframework.util.StringUtils;

public class MergeTextConverter implements DomainConverter {

   public String convert(MappingField mappingField, String sourceValue, Object... objects) {
      if (!StringUtils.hasText(sourceValue)) {
         return sourceValue;
      }
      String destValue = "";
      if ("left".equalsIgnoreCase(mappingField.getMappingType())) {
         destValue = leftMerge(mappingField, sourceValue, objects);
      }
      return destValue;
   }

   private String leftMerge(MappingField mappingField, String sourceValue, Object... objects) {
      int sourceLen = sourceValue.length();
      String pattern = mappingField.getPattern();
      int patternLen = pattern.length();
      return (sourceLen >= patternLen) ? sourceValue : pattern.substring(0, patternLen - sourceLen) + sourceValue;
   }
}
