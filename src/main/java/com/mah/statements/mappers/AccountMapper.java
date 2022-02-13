package com.mah.statements.mappers;

import com.mah.statements.entity.Account;
import com.mah.statements.service.model.AccountModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountModel mapToDto(Account account);
}
