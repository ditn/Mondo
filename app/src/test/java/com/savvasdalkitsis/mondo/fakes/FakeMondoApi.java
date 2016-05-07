package com.savvasdalkitsis.mondo.fakes;

import com.savvasdalkitsis.mondo.model.balance.Balance;
import com.savvasdalkitsis.mondo.repository.MondoApi;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.Result;
import rx.Observable;
import rx.subjects.PublishSubject;

public class FakeMondoApi implements MondoApi {

    private PublishSubject<Result<Balance>> subject;

    @Override
    public Observable<Result<Balance>> getBalance() {
        subject = PublishSubject.create();
        return subject;
    }


    public void emitSuccess(Balance balance) {
        emitAndFinish(Result.response(Response.success(balance)));
    }

    public void emitError() {
        subject.onError(new IOException("Error during api call"));
    }

    public void emitErrorResponse() {
        emitAndFinish(Result.response(Response.error(500, ResponseBody.create(null, ""))));
    }

    private void emitAndFinish(Result<Balance> result) {
        subject.onNext(result);
        subject.onCompleted();
    }
}
