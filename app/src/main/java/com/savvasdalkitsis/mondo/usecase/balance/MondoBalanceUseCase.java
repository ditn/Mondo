package com.savvasdalkitsis.mondo.usecase.balance;

import com.savvasdalkitsis.mondo.model.Response;
import com.savvasdalkitsis.mondo.model.balance.Balance;
import com.savvasdalkitsis.mondo.repository.MondoApi;
import com.savvasdalkitsis.mondo.usecase.BalanceUseCase;

import rx.Observable;

public class MondoBalanceUseCase implements BalanceUseCase {

    private MondoApi mondoApi;

    public MondoBalanceUseCase(MondoApi mondoApi) {
        this.mondoApi = mondoApi;
    }

    @Override
    public Observable<Response<Balance>> getBalance() {
        return mondoApi.getBalance()
                .map(balanceResult -> Response.success(balanceResult.response().body()))
                .onErrorResumeNext(Observable.just(Response.error()));
    }
}