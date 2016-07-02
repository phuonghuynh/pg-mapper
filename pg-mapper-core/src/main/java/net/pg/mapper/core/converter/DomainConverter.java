package net.pg.mapper.core.converter;

import net.pg.mapper.core.domain.MappingField;

public interface DomainConverter {

   String convert(MappingField mappingField, String sourceValue, Object... objects);

}
