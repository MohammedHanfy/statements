package com.mah.statements.mappers;

import com.mah.statements.entity.Statement;
import com.mah.statements.service.model.StatementModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StatementMapper {

    StatementMapper INSTANCE = Mappers.getMapper(StatementMapper.class);

    @Mapping(source = "dateField", target = "dateField", dateFormat = "dd.MM.yyyy")
    StatementModel mapToDto(Statement statement);
}
