package com.savvasdalkitsis.mondo.test.ui.actors.arrangements;

import android.content.Context;
import android.support.annotation.RawRes;

import com.savvasdalkitsis.mondo.test.data.TestAccountId;
import com.savvasdalkitsis.mondo.test.data.TestCurrency;
import com.savvasdalkitsis.mondo.test.data.TestResponses;
import com.savvasdalkitsis.mondo.test.ui.server.MatchingDispatcher;

import org.hamcrest.Matcher;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import static com.savvasdalkitsis.mondo.test.matchers.RecordedRequestMatchers.anywhere;
import static com.savvasdalkitsis.mondo.test.matchers.RecordedRequestMatchers.withPath;
import static com.savvasdalkitsis.mondo.test.util.RawUtils.fromRaw;

public class UserArrangements {

    private MatchingDispatcher dispatcher;
    private Context instrumentationContext;

    public UserArrangements(MatchingDispatcher dispatcher, Context instrumentationContext) {
        this.dispatcher = dispatcher;
        this.instrumentationContext = instrumentationContext;
    }

    public void hasBalance(double balance, TestCurrency currency) {
        respond(withPath("/balance?account_id="+ TestAccountId.TEST_ACCOUNT_ID),
                TestResponses.balance(balance, currency));
    }

    public void hasSpentToday(double amount, TestCurrency currency) {
        respond(withPath("/balance?account_id="+ TestAccountId.TEST_ACCOUNT_ID),
                TestResponses.spentToday(amount, currency));
    }

    public void hasTransactions(@RawRes int rawId) {
        respond(withPath("/transactions?expand[]=merchant&account_id="+ TestAccountId.TEST_ACCOUNT_ID),
                fromRaw(rawId, instrumentationContext.getResources()));
    }

    public void isNotAuthenticated() {
        respond(anywhere(), new MockResponse().setResponseCode(401));
    }

    private void respond(Matcher<RecordedRequest> requestMatcher, String body) {
        respond(requestMatcher, new MockResponse()
                .setResponseCode(200)
                .setBody(body));
    }

    private void respond(Matcher<RecordedRequest> requestMatcher, MockResponse response) {
        dispatcher.matchRequest(requestMatcher, response);
    }

}
