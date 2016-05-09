package com.savvasdalkitsis.mondo.fakes;

import com.savvasdalkitsis.mondo.model.Response;
import com.savvasdalkitsis.mondo.model.authentication.AuthenticationData;
import com.savvasdalkitsis.mondo.usecase.authentication.AuthenticationUseCase;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.subjects.PublishSubject;

public class FakeAuthenticationUseCase implements AuthenticationUseCase {

    private final Map<AuthenticationData, PublishSubject<Response<Void>>> responses = new HashMap<>();

    public void emitSuccessfulResponseFor(AuthenticationData authenticationData) {
        emitItem(authenticationData, Response.success(null));
    }

    public void emitErrorResponseFor(AuthenticationData authenticationData) {
        emitItem(authenticationData, Response.error());
    }

    private void emitItem(AuthenticationData authenticationData, Response<Void> response) {
        PublishSubject<Response<Void>> publishSubject = responses.get(authenticationData);
        publishSubject.onNext(response);
        publishSubject.onCompleted();
    }

    @Override
    public Observable<Response<Void>> authenticate(AuthenticationData authenticationData) {
        return responses.get(authenticationData);
    }

    public void accepts(AuthenticationData authenticationData) {
        responses.put(authenticationData, PublishSubject.create());
    }
}