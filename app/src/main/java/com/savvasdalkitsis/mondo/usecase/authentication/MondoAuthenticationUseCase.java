package com.savvasdalkitsis.mondo.usecase.authentication;

import com.savvasdalkitsis.mondo.model.Response;
import com.savvasdalkitsis.mondo.model.authentication.AuthenticationData;
import com.savvasdalkitsis.mondo.repository.CredentialsRepository;
import com.savvasdalkitsis.mondo.repository.MondoApi;

import rx.Observable;

public class MondoAuthenticationUseCase implements AuthenticationUseCase {
    private final MondoApi mondoApi;
    private final CredentialsRepository credentialsRepository;
    private final String clientId;
    private final String clientSecret;

    public MondoAuthenticationUseCase(MondoApi mondoApi,
                                      CredentialsRepository credentialsRepository,
                                      String clientId,
                                      String clientSecret) {
        this.mondoApi = mondoApi;
        this.credentialsRepository = credentialsRepository;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public Observable<Response<Void>> authenticate(AuthenticationData authenticationData) {
        return mondoApi.oAuthToken(clientId, clientSecret, authenticationData.getCode())
                .map(result -> result.response().body())
                .doOnNext(oAuth -> credentialsRepository.saveAccessToken(oAuth.getAccessToken()))
                .doOnNext(oAuth -> credentialsRepository.saveRefreshToken(oAuth.getRefreshToken()))
                .flatMap(token -> mondoApi.getAccounts())
                .map(result -> result.response().body())
                .doOnNext(accounts -> credentialsRepository.saveAccountId(accounts.getAccounts().get(0).getId()))
                .map(apiOAuthTokenResult -> Response.success(null));
    }
}