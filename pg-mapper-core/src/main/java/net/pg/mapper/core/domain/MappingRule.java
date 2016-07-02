package net.pg.mapper.core.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mappingRule")
@XmlAccessorType(XmlAccessType.FIELD)
public class MappingRule {

   @XmlElement(name = "mappingField")
   private List<MappingField> mappingFields;

   public List<MappingField> getMappingFields() {
      return mappingFields;
   }

   public void setMappingFields(List<MappingField> mappingFields) {
      this.mappingFields = mappingFields;
   }
}
