package com.tarapaca.api.authentication.infrastructure.helpers;

import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.tarapaca.api.authentication.domain.model.TokenModel;
import com.tarapaca.api.authentication.infrastructure.repository.token.TokenData;
import com.tarapaca.api.generics.infrastructure.helpers.GenericEntityMapper;
import com.tarapaca.api.users.infrastructure.helpers.mappers.UserBasicMapper;
import com.tarapaca.api.users.infrastructure.helpers.mappers.UserMapper;

@Component
public class TokenMapper extends GenericEntityMapper<TokenModel, TokenData> {
    private final UserMapper userMapper;

    public TokenMapper(UserMapper userMapper, UserBasicMapper userBasicMapper) {
        super(TokenModel.class, TokenData.class, userBasicMapper);
        this.userMapper = userMapper;
    }

    @Override
    @Nullable
    public TokenModel toDomain(@Nullable TokenData source, @NonNull TokenModel target) {
        if (source == null) {
            return null;
        }
        super.toDomain(source, target);

        Optional.ofNullable(source.getToken()).ifPresent(target::setToken);
        Optional.ofNullable(source.getRefreshToken()).ifPresent(target::setRefreshToken);
        Optional.ofNullable(source.getType()).ifPresent(target::setType);
        Optional.ofNullable(source.getUser()).ifPresent(user -> target.setUser(userMapper.toDomain(user)));
        Optional.of(source.isRevoked()).ifPresent(target::setRevoked);

        return target;
    }

    @Override
    @Nullable
    public TokenData toData(@Nullable TokenModel source, @NonNull TokenData target) {
        if (source == null) {
            return null;
        }
        super.toData(source, target);

        Optional.ofNullable(source.getToken()).ifPresent(target::setToken);
        Optional.ofNullable(source.getRefreshToken()).ifPresent(target::setRefreshToken);
        Optional.ofNullable(source.getType()).ifPresent(target::setType);
        Optional.ofNullable(source.getUser()).ifPresent(user -> target.setUser(userMapper.toData(user)));
        Optional.of(source.isRevoked()).ifPresent(target::setRevoked);

        return target;
    }
}
