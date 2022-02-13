package com.mah.statements.mappers;

import com.mah.statements.rest.dto.ViewStatementsRequest;
import com.mah.statements.service.model.ViewStatementsRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ViewStatementsRequestMapper {

    ViewStatementsRequestMapper INSTANCE = Mappers.getMapper(ViewStatementsRequestMapper.class);

    @Mapping(target = "userType", ignore = true)
    @Mapping(target = "fromDate", source = "dateRange.fromDate", dateFormat = "dd.MM.yyyy")
    @Mapping(target = "toDate", source = "dateRange.toDate", dateFormat = "dd.MM.yyyy")
    @Mapping(target = "fromAmount", source = "amountRange.fromAmount")
    @Mapping(target = "toAmount", source = "amountRange.toAmount")
    ViewStatementsRequestModel mapToDto(ViewStatementsRequest viewStatementsRequest);
}
