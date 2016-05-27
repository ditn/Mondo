/**
 * Copyright (C) 2016 Savvas Dalkitsis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.savvasdalkitsis.mondo.model.balance;

import com.savvasdalkitsis.mondo.model.money.Money;
import com.savvasdalkitsis.mondo.repository.model.ApiBalance;

import rx.functions.Func1;

public class ApiBalanceToBalanceMapper implements Func1<ApiBalance, Balance> {

    @Override
    public Balance call(ApiBalance apiBalance) {
        String currency = apiBalance.getCurrency();
        return Balance.<Balance>builder()
                .balance(Money.builder().wholeValue(apiBalance.getBalance()).currency(currency).build())
                .spentToday(Money.builder()
                        .wholeValue(Math.abs(apiBalance.getSpendToday()))
                        .expense(apiBalance.getSpendToday() < 0)
                        .currency(currency)
                        .build())
                .build();
    }
}
