package net.pg.mapper.factory;

import net.pg.mapper.DomainMapper;
import net.pg.mapper.callback.DomainMapperCallback;
import net.pg.mapper.core.domain.MappingRule;

import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public class DomainMapperFactory {

   public static DomainMapper createDomainMapper(Resource ruleFile, DomainMapperCallback domainCallback) {
      Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
      jaxb2Marshaller.setClassesToBeBound(new Class<?>[] { MappingRule.class });
      DomainMapper domainMapper = new DomainMapper(ruleFile, jaxb2Marshaller);
      domainMapper.setDomainCallback(domainCallback);
      return domainMapper;
   }

}
