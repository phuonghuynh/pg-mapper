package net.pg.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.stream.StreamSource;

import net.pg.mapper.callback.DomainMapperCallback;
import net.pg.mapper.core.ann.DomainMapping;
import net.pg.mapper.core.converter.DomainConverter;
import net.pg.mapper.core.domain.MappingField;
import net.pg.mapper.core.domain.MappingRule;
import net.pg.mapper.exception.DomainCreationException;
import net.pg.mapper.exception.InvalidFormatMapperException;
import net.pg.mapper.util.ClassUtils;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class DomainMapper {

   private static final Log LOG = LogFactory.getLog(DomainMapper.class);

   private DomainMapperCallback domainCallback;

   private Map<String, List<MappingField>> mappingFields = new HashMap<String, List<MappingField>>();

   public DomainMapper(Resource ruleFile, Jaxb2Marshaller jaxbMarshaller) {
      try {
         StreamSource ruleInputStream = new StreamSource(ruleFile.getInputStream());
         LOG.debug(ruleFile.getFile().getAbsolutePath());
         MappingRule mappingRule = (MappingRule) jaxbMarshaller.unmarshal(ruleInputStream);
         List<MappingField> fields = mappingRule.getMappingFields();
         for (MappingField mappingField : fields) {
            List<MappingField> list = mappingFields.get(mappingField.getName());
            if (list != null) {
               list.add(mappingField);
            }
            else {
               list = new ArrayList<MappingField>();
               list.add(mappingField);
               mappingFields.put(mappingField.getName(), list);
            }
            LOG.debug("Loading MappingField entry: name=[" + mappingField.getName() + "]");
         }
      }
      catch (Exception e) {
         LOG.error("Load Mapping Rule failed", e);
         throw new InvalidFormatMapperException("Invalid Mapping Rule file", e);
      }
   }

   public void map(final String direction, final Object sourceObject, final Object target) {
      Object object = domainCallback.createMe(sourceObject);// tryJson(jsonString);
      if (object == null) {
         throw new DomainCreationException("Error - invalid json string: " + sourceObject);
      }

      Class<?> targetClass = target.getClass();
      ReflectionUtils.doWithFields(targetClass, new ReflectionUtils.FieldCallback() {
         public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
            DomainMapping domainAnnotation = field.getAnnotation(DomainMapping.class);
            if (domainAnnotation == null) {
               return;
            }
            String mappingName = domainAnnotation.name();
            if (!StringUtils.hasText(mappingName)) {
               mappingName = field.getName();
            }
            List<MappingField> list = mappingFields.get(mappingName);
            if (list == null) {
               LOG.debug("Ingmore map field: [" + field.getName() + "] due to no mappingId found");
               return;
            }
            for (MappingField mappingField : list) {
               if (ClassUtils.csvHasText(mappingField.getDirections(), direction)) {
                  acceptField(field, mappingField, direction);
               }
            }
         }
      });
      doMapping(object, target);
   }

   private void doMapping(Object object, Object target) {
      JXPathContext xpathContext = JXPathContext.newContext(object);
      Set<String> mappingFieldIds = mappingFields.keySet();
      for (String id : mappingFieldIds) {
         List<MappingField> list = mappingFields.get(id);
         for (MappingField mappingField : list) {
            try {
               justdoit(mappingField, xpathContext, target);
               mappingField.setTargetFields(null);
            }
            catch (Exception e) {
               LOG.debug("Can not convert field: " + mappingField.getName());
            }
         }
      }
   }

   private void acceptField(Field field, MappingField mappingField, String direction) {
      DomainMapping domainAnnotation = field.getAnnotation(DomainMapping.class);
      String[] directions = domainAnnotation.directions();
      for (String direct : directions) {
         // check for if there is any direction defined in Annotation
         if (direction.equals(direct)) {
            mappingField.addTargetField(field);
            break;
         }
      }
      // accept if there is no direction from annotation
      if (domainAnnotation.directions().length == 0 && mappingField.getTargetFields().size() == 0) {
         mappingField.addTargetField(field);
      }
   }

   private void justdoit(MappingField mappingField, JXPathContext xpathContext, Object target)
         throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException,
         NoSuchMethodException, InstantiationException, ClassNotFoundException {
      List<Field> targetFields = mappingField.getTargetFields();
      List<Field> proceedFields = new ArrayList<Field>();
      for (Field targetField : targetFields) {
         DomainMapping domainAnnotation = targetField.getAnnotation(DomainMapping.class);
         String jsonXpath = domainAnnotation.xpath();
         String sourceValue = (String) xpathContext.getValue(jsonXpath);

         DomainConverter converter = getConverter(mappingField);
         String targetValue = (converter == null) ? sourceValue : converter.convert(mappingField, sourceValue);
         String targetFieldName = targetField.getName();
         Method setter = target.getClass().getMethod("set" + StringUtils.capitalize(targetFieldName),
               targetField.getType());
         String log = MessageFormat.format(
               "Converted from [sourceField={0}], [sourceValue={1}] TO [destField={2}], [destValue={3}]", new Object[] {
                     jsonXpath, sourceValue, targetFieldName, targetValue });
         LOG.debug(log);
         setter.invoke(target, targetValue);
         proceedFields.add(targetField);
      }

      // remove proceed field
      if (proceedFields.size() > 0) {
         targetFields.removeAll(proceedFields);
      }
   }

   private DomainConverter getConverter(MappingField mappingField) throws InstantiationException,
         IllegalAccessException, ClassNotFoundException {
      String converterClass = mappingField.getConverterClass();
      if (!StringUtils.hasText(converterClass)) {
         return null;
      }
      DomainConverter converter = mappingField.getConverter();
      if (converter == null) {
         converter = (DomainConverter) Class.forName(converterClass).newInstance();
         mappingField.setConverter(converter);
      }
      return converter;
   }

   public void setDomainCallback(DomainMapperCallback domainCallback) {
      this.domainCallback = domainCallback;
   }
}
