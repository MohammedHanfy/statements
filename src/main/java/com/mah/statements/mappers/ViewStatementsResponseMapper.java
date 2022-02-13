package com.mah.statements.mappers;

import com.mah.statements.rest.dto.ViewStatementsResponse;
import com.mah.statements.service.model.ViewStatementsResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ViewStatementsResponseMapper {

    ViewStatementsResponseMapper INSTANCE = Mappers.getMapper(ViewStatementsResponseMapper.class);

    ViewStatementsResponse map(ViewStatementsResponseModel viewStatementsResponseModel);
}
