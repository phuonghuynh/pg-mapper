package net.pg.mapper.core.domain;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import net.pg.mapper.core.converter.DomainConverter;

import org.springframework.util.StringUtils;

@XmlAccessorType(XmlAccessType.FIELD)
public class MappingField {

   @XmlAttribute
   private String name;

   @XmlElement
   private String pattern;

   @XmlElement
   private Integer minLength;

   @XmlElement
   private Integer maxLength;

   @XmlElement
   private String converterClass;

   @XmlElement
   private String delemiter;

   @XmlElement
   private String mappingType;

   @XmlAttribute
   private String directions;

   private transient DomainConverter converter;

   private transient List<Field> targetFields;

   public List<Field> getTargetFields() {
      if (targetFields == null) {
         targetFields = new ArrayList<Field>();
      }
      return targetFields;
   }

   public void setTargetFields(List<Field> targetFields) {
      this.targetFields = targetFields;
   }

   public void addTargetField(Field field) {
      getTargetFields().add(field);
   }

   public String getDirections() {
      return directions;
   }

   public void setDirections(String directions) {
      this.directions = directions;
   }

   public String getMappingType() {
      return mappingType;
   }

   public void setMappingType(String mappingType) {
      this.mappingType = mappingType;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPattern() {
      return pattern;
   }

   public void setPattern(String pattern) {
      this.pattern = pattern;
   }

   public Integer getMinLength() {
      return minLength;
   }

   public void setMinLength(Integer minLength) {
      this.minLength = minLength;
   }

   public Integer getMaxLength() {
      return maxLength;
   }

   public void setMaxLength(Integer maxLength) {
      this.maxLength = maxLength;
   }

   public String getConverterClass() {
      return converterClass;
   }

   public void setConverterClass(String converterClass) {
      this.converterClass = converterClass;
   }

   public String getDelemiter() {
      return delemiter;
   }

   public void setDelemiter(String delemiter) {
      this.delemiter = delemiter;
   }

   public DomainConverter getConverter() {
      return converter;
   }

   public void setConverter(DomainConverter converter) {
      this.converter = converter;
   }

   public MappingField clone() {
      MappingField mp = new MappingField();
      try {
         Field[] fields = MappingField.class.getDeclaredFields();
         for (Field field : fields) {
            Method getter = MappingField.class.getMethod("get" + StringUtils.capitalize(field.getName()));
            Method setter = MappingField.class.getMethod("set" + StringUtils.capitalize(field.getName()),
                  field.getDeclaringClass());
            setter.invoke(mp, getter.invoke(this));
         }
      }
      catch (Exception e) {
      }
      return mp;
   }
}
