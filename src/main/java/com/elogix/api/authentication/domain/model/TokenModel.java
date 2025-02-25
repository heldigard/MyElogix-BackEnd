package com.elogix.api.authentication.domain.model;

import java.util.Arrays;
import java.util.Objects;

import com.elogix.api.generics.domain.model.GenericEntity;
import com.elogix.api.users.domain.model.UserModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
public class TokenModel extends GenericEntity {
    private byte[] token;
    private byte[] refreshToken;
    private ETokenType type;
    @Builder.Default
    private boolean isRevoked = false;
    private UserModel user;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o)) // Añadir verificación de igualdad con la clase padre
            return false;
        TokenModel that = (TokenModel) o;
        return isRevoked == that.isRevoked &&
                Arrays.equals(token, that.token) &&
                Arrays.equals(refreshToken, that.refreshToken) &&
                type == that.type &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(), // Añadir llamada al hashCode padre
                type,
                isRevoked,
                user,
                Arrays.hashCode(token),
                Arrays.hashCode(refreshToken));
    }

    public TokenModel() {
        super();
    }

    @Override
    public String toString() {
        return "TokenModel(super=" + super.toString() +
                ", token=[PROTECTED]" +
                ", refreshToken=[PROTECTED]" +
                ", type=" + type +
                ", isRevoked=" + isRevoked +
                ", user=" + user + ")";
    }
}
